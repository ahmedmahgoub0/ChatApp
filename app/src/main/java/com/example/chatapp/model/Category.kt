package com.example.chatapp.model

import com.example.chatapp.R

data class Category(
    val id: String? = null,
    val name: String? = null,
    val imageId: Int? = null
) {
    companion object {
        fun getCategoriesList(): List<Category> {
            return listOf<Category>(
                Category("0", "Sports", R.drawable.sports),
                Category("1", "Movies", R.drawable.movies),
                Category("2", "Music", R.drawable.music),
            )
        }

        fun getImageByCategoryId(categoryId: Int): Int {
            return when (categoryId) {
                0 -> R.drawable.sports
                1 -> R.drawable.movies
                else -> {
                    R.drawable.music
                }
            }
        }
    }
}
