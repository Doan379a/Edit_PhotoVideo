package com.example.editphotovideo.utils

import kotlin.random.Random

object ColorUtils {
     fun generateRandomColor(): String {
        val random = Random
        return String.format(
            "#%02x%02x%02x",
            random.nextInt(256),
            random.nextInt(256),
            random.nextInt(256)
        )
    }
}