package com.example.homework_recyclerview

import androidx.annotation.DrawableRes
import com.example.homework_recyclerview.domain.repository.Parent

data class Currency(
    var text: Int,
    val type: String,
    @DrawableRes val flag: Int,
    val course: Int
): Parent