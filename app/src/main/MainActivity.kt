package com.example.mcpclient

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.atomic.AtomicInteger
import kotlinx.coroutines.launch

/**
 * MainActivity fetches schedules from the MCP server at startup and
 * schedules local alarms for each upcoming run time.  When the alarms
 * trigger, NotificationReceiver displays a local notification to the user.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configure Retrofit and Moshi
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val retrofit = Retrofit.Builder()
            // Use 10.0.2.2 to reach the host machine from the Android emulator
            .baseUrl("http://10.0.2.2:8000")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        val api = retrofit.create(ApiService::class.java)

        // Fetch schedules and schedule alarms
        lifecycleScope.launch {
            try {
                val schedules = api.listSchedules()
                scheduleLocalNotifications(schedules)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * For each schedule returned from the server, parse its next run times and
     * schedule local alarms via the AlarmManager.  Each alarm triggers
     * NotificationReceiver with the selected message template.
     */
    private fun scheduleLocalNotifications(schedules: List<ScheduleEntry>) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val requestCodeGenerator = AtomicInteger(0)
        val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        for (schedule in schedules) {
            val message = schedule.schema.content.templates.firstOrNull() ?: continue
            for (run in schedule.next_runs) {
                try {
                    val dateTime = OffsetDateTime.parse(run, formatter)
                    val epochMillis = dateTime.toInstant().toEpochMilli()
                    val intent = Intent(this, NotificationReceiver::class.java).apply {
                        putExtra(NotificationReceiver.EXTRA_MESSAGE, message)
                    }
                    val requestCode = requestCodeGenerator.incrementAndGet()
                    val pendingIntent = PendingIntent.getBroadcast(
                        this,
                        requestCode,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )
                    // Use setExactAndAllowWhileIdle for precise delivery even in Doze mode
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        epochMillis,
                        pendingIntent
                    )
                } catch (ignored: Exception) {
                    // Skip invalid date formats
                }
            }
        }
    }
}