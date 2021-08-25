package com.example.bulletnewsoriginal.model

import androidx.room.Embedded

data class Source(
    @Embedded val id: Any?,
    val name: String?
)