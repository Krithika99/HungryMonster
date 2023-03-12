package com.ksas.hungrymonster


import MealDetailScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ksas.hungrymonster.screen.MealByCategoryDescriptionScreen
import com.ksas.hungrymonster.screen.MealCategoryScreen
import com.ksas.hungrymonster.screen.SearchedMealScreen

const val MealByCategoryScreen = "MealByCategoryScreen"
const val MealByCategoryDescription = "MealByCategoryDescription"
const val SearchMealMoreThanOne = "SearchMealMoreThanOne"
const val MealDetailScreen = "MealDetailScreen"
const val FOOD_NAME = "foodName"

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home_screen") {

        composable("home_screen") {
            HomeScreen(navController)
        }
        composable(
            "$MealByCategoryScreen/{$FOOD_NAME}",
            arguments = listOf(
                navArgument(FOOD_NAME) { type = NavType.StringType })

        ) { navBackStackEntry ->
            val foodName = navBackStackEntry.arguments?.getString(FOOD_NAME)

            if (foodName != null) {
                MealCategoryScreen(navController, foodName)
            }

        }
        composable("$MealByCategoryDescription/{$FOOD_NAME}",
            arguments = listOf(
                navArgument(FOOD_NAME) { type = NavType.StringType }
            )) { navBackStackEntry ->
            val foodName = navBackStackEntry.arguments?.getString(FOOD_NAME)
            if (foodName != null)
                MealByCategoryDescriptionScreen(navController, foodName = foodName)
        }

        composable("$SearchedMealScreen/{$FOOD_NAME}", arguments = listOf(
            navArgument(FOOD_NAME) { type = NavType.StringType }
        )) { navBackStackEntry ->
            val foodName = navBackStackEntry.arguments?.getString(FOOD_NAME)
            if (foodName != null)
                SearchedMealScreen(navController, foodName = foodName)

        }


        composable("$MealDetailScreen/{$FOOD_NAME}", arguments = listOf(
            navArgument(FOOD_NAME) { type = NavType.StringType }

        )) { navBackStackEntry ->
            val foodName = navBackStackEntry.arguments?.getString(FOOD_NAME)
            if (foodName != null)
                MealDetailScreen(navController, foodName)

        }

    }
}