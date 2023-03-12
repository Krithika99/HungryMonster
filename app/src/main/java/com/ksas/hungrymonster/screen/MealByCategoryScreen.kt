package com.ksas.hungrymonster.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.ksas.hungrymonster.MealDetailScreen
import com.ksas.hungrymonster.api.MealCategory
import com.ksas.hungrymonster.myCustomFont
import com.ksas.hungrymonster.viewmodel.MealViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MealCategoryScreen(navController: NavHostController, foodName: String) {
    val mutableMealByCategory: MutableState<List<MealCategory>> = remember {
        mutableStateOf(emptyList())
    }

    val viewModel: MealViewModel = viewModel()
    val scope = CoroutineScope(Dispatchers.IO)
    scope.launch {
        val mealByCategory = viewModel.getMealByCategory(foodName)
        mutableMealByCategory.value = mealByCategory
    }

    Column {
        Text(
            text = foodName,
            fontFamily = myCustomFont,
            textAlign = TextAlign.Start,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                color = MaterialTheme.colors.onSurface
            )
        )
        LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 128.dp)) {
            items(mutableMealByCategory.value) { meals ->
                Card(
                    modifier = Modifier.padding(8.dp),
                    shape = RoundedCornerShape(20.dp),
                    onClick = {
                        navController.navigate("${MealDetailScreen}/${meals.foodName}")
                    }) {
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = meals.foodImageUrl),
                            contentDescription = "",
                            modifier = Modifier
                                .align(alignment = Alignment.CenterHorizontally)
                                .size(88.dp)
                        )
                        Spacer(modifier = Modifier.height(9.dp))
                        Text(
                            text = meals.foodName, modifier = Modifier, fontFamily = myCustomFont,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )

                    }
                }
            }
        }
    }
}