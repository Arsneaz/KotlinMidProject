package com.example.kotlinmidproject.model

data class PersonJson(
    val data: List<Data>,
)

data class Data(
    val avatar: String,
    val email: String,
    val first_name: String,
    val id: Int,
    val last_name: String
)