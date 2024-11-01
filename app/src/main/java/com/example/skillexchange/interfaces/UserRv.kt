package com.example.skillexchange.interfaces

data class UserRv(
    val description: String = "",
    val name: String = "",
    val age: String = "",
    val newSkills: List<String> = emptyList(),
    val mySkills: List<String> = emptyList()
)
