package com.example.mcpclient

import retrofit2.http.GET

/**
 * Retrofit interface for communicating with the MCP server.
 */
interface ApiService {
    /**
     * Retrieve all schedules.  The server returns a list of ScheduleEntry
     * objects containing the upcoming run times and message templates.
     */
    @GET("/schedule")
    suspend fun listSchedules(): List<ScheduleEntry>
}