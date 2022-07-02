package com.example.homework_recyclerview

import androidx.annotation.DrawableRes
import com.example.homework_recyclerview.domain.repository.Parent

data class Add(
    val text: String,
    @DrawableRes val flag: Int
): Parent
