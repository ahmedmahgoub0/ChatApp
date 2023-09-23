package com.example.chatapp.model

import com.example.chatapp.R

data class Category(
    val id: Int? = null,
    val name: String? = null,
    val imageId: Int? = null
) {
    companion object {
        fun getCategoriesList(): List<Category> {
            return listOf<Category>(
                Category(0, "Sports", R.drawable.sports),
                Category(0, "Movies", R.drawable.movies),
                Category(0, "Music", R.drawable.music),
            )
        }
    }
}
