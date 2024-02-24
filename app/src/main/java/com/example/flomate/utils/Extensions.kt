package com.example.flomate.utils

fun String.isValidEmail(): Boolean {
    val emailRegex = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}".toRegex()
    return emailRegex.matches(this)
}