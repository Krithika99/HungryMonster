package com.ksas.hungrymonster.api

import com.ksas.hungrymonster.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class FoodApi {

    private var api: MealServiceApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(MealServiceApi::class.java)
    }

    suspend fun getCategories(): CategoryList {
        return api.getCategories()
    }

    suspend fun getSearchedMeal(searchedMeal: String): SearchedMealList {
        return api.getSearchedMeal(searchedMeal)
    }

    suspend fun getRandomMeal(): RandomMealList {
        return api.getRandomMeal()
    }

    suspend fun getMealByCategory(category: String): MealByCategoryList {
        return api.getMealByCategory(category)
    }


    interface MealServiceApi {

        @GET("categories.php")
        suspend fun getCategories(): CategoryList

        @GET("search.php?")
        suspend fun getSearchedMeal(@Query("s") searchedMeal: String): SearchedMealList

        @GET("random.php")
        suspend fun getRandomMeal(): RandomMealList

        @GET("filter.php?")
        suspend fun getMealByCategory(@Query("c") category: String): MealByCategoryList
    }


}