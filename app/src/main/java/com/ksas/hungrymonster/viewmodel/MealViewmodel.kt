package com.ksas.hungrymonster.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ksas.hungrymonster.api.*
import com.ksas.hungrymonster.repository.MealRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MealViewModel(private val mealRepo: MealRepository = MealRepository()) : ViewModel() {
     val mutableCategory: MutableState<List<Category>> = mutableStateOf(emptyList())
     val mutableRandomMeal: MutableState<List<RandomMeal>> = mutableStateOf(emptyList())


    private val job = Job()

    init {
        val scope = CoroutineScope(job + Dispatchers.IO)
        scope.launch {
            //category
            val mealCategories = getCategories()
            mutableCategory.value = mealCategories

            //Random meal
            val randomMeal = getRandomMeal()
            mutableRandomMeal.value = randomMeal

        }
    }


    suspend fun getCategories(): List<Category> {
        return mealRepo.getCategories().categories
    }

    suspend fun getSearchedMeal(searchedMeal: String): List<SearchedMeal> {
        return mealRepo.getSearchedMeal(searchedMeal).searchedMeals
    }

    suspend fun getRandomMeal(): List<RandomMeal> {
        return mealRepo.getRandomMeal().randomMeal
    }

    suspend fun getMealByCategory(category: String): List<MealCategory> {
        return mealRepo.getMealByCategory(category).meals
    }

}