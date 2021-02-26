package com.example.androiddevchallenge.data

import com.example.androiddevchallenge.R


data class Dog(
    val dogName: String,
    val dogOneImage: Int,
    val dogTwoImage: Int,
    val dogDesc: Int
)

fun getListOfDogs(): List<Dog> {
    return listOf(
        Dog("Shepard", R.drawable.dog_one_part_one, R.drawable.dog_one_part_two, R.string.dummy),
        Dog("Shepard", R.drawable.dog_two_one, R.drawable.dog_two_two, R.string.dummy),
        Dog("Doggy",R.drawable.dog_three_one, R.drawable.dog_three_two, R.string.dummy),
        Dog("Something",R.drawable.dog_four_one, R.drawable.dog_four_two, R.string.dummy),
        Dog("Good dog",R.drawable.dog_five_one, R.drawable.dog_five_two, R.string.dummy),
    )
}