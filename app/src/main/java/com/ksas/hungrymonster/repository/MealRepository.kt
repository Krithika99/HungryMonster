package com.ksas.hungrymonster.repository

import com.ksas.hungrymonster.api.*

class MealRepository(private val mealApi: FoodApi = FoodApi()) {
    suspend fun getCategories(): CategoryList {
        return mealApi.getCategories()
    }

    suspend fun getSearchedMeal(searchedMeal: String): SearchedMealList {
        return mealApi.getSearchedMeal(searchedMeal)
    }

    suspend fun getRandomMeal(): RandomMealList {
        return mealApi.getRandomMeal()
    }

    suspend fun getMealByCategory(category: String): MealByCategoryList {
        return mealApi.getMealByCategory(category)
    }
}