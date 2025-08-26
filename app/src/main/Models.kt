package com.example.mcpclient

/**
 * Data models matching the JSON returned by the MCP server.  Moshi or
 * another JSON converter will map the JSON fields to these Kotlin data
 * classes automatically.
 */
data class ScheduleEntry(
    val id: String,
    val schema: Schema,
    val jobs: List<String> = emptyList(),
    val next_runs: List<String> = emptyList()
)

data class Schema(
    val version: String,
    val timezone: String,
    val cadence: Cadence,
    val content: Content,
    val channels: List<String>
)

data class Cadence(
    val type: String,
    val days_of_week: List<String> = emptyList(),
    val times_local: List<String> = emptyList()
)

data class Content(
    val strategy: String,
    val templates: List<String>
)