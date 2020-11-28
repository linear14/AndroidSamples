package com.dongldh.swipeviewexample

data class Donate(
    val user: User,
    val book: Book
) {}

data class User(
    val name: String,
    val job: String,
    val profile: Int
) {}

data class Book(
    val title: String,
    val author: String,
    val publisher: String
) {}