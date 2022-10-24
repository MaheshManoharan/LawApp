package com.mahidev.sqlitedemo

data class Question(
    val type: String,
    val code: String,
    val subcode: String,
    val shortDesc: String,
    val fullDesc: String,
    val path: String,
)
