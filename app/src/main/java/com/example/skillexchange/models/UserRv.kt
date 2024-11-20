package com.example.skillexchange.models

import java.io.Serializable

data class UserRv(
    val description: String = "",
    val userEmail: String = "",
    val name: String = "",
    val age: String = "",
    val photoUrl: String = "",
    val newSkills: List<String> = emptyList(),
    val mySkills: List<String> = emptyList(),
    val userId: String = ""
): Serializable
