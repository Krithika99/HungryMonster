package com.ksas.hungrymonster.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import com.ksas.hungrymonster.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.ksas.hungrymonster.SearchMealMoreThanOne
import com.ksas.hungrymonster.api.SearchedMeal
import com.ksas.hungrymonster.myCustomFont
import com.ksas.hungrymonster.viewmodel.MealViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("CoroutineCreationDuring Composition", "CoroutineCreationDuringComposition")

@Composable
fun MealByCategoryDescriptionScreen(navController: NavHostController, foodName: String) {
    val viewModel: MealViewModel = viewModel()

    val mutableSearchedMealList: MutableState<List<SearchedMeal>> = remember {
        mutableStateOf(emptyList())
    }

    val scope = CoroutineScope(Dispatchers.Main)
    scope.launch {
        val searchMealByCategory =
            viewModel.getSearchedMeal(foodName)
        mutableSearchedMealList.value = searchMealByCategory
    }


    if (mutableSearchedMealList.value.size > 1) {
        LazyColumn {
            items(mutableSearchedMealList.value) { meals ->
                Card(modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .padding(10.dp),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(2.dp, Color.LightGray),
                    onClick = {
                        navController.navigate("$SearchMealMoreThanOne/${meals.foodName}")
                    }

                ) {
                    Image(
                        painter = rememberAsyncImagePainter(meals.foodImageUrl),
                        contentDescription = "",

                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Text(

                        text = meals.foodName,
                        textAlign = TextAlign.Justify,
                        color = Color.White,
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    )


                }
            }
        }
    } else {

        Box(modifier = Modifier) {
            LazyColumn {
                items(mutableSearchedMealList.value) { meals ->
                    Card(
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                            .padding(10.dp),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(2.dp, Color.LightGray)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(meals.foodImageUrl),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Text(
                            text = meals.foodName,
                            textAlign = TextAlign.Justify,
                            color = Color.White,
                            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(top = 150.dp),
                            fontFamily = myCustomFont
                        )

                    }

                    Row(modifier = Modifier.padding(start = 70.dp)) {
                        Icon(
                            painter = painterResource(id = R.drawable.location),
                            contentDescription = "",
                            tint = MaterialTheme.colors.onSurface,
                            modifier = Modifier.size(30.dp)
                        )
                        Text(
                            text = meals.foodArea,
                            fontFamily = myCustomFont,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(color = MaterialTheme.colors.onSurface)
                        )
                        Spacer(modifier = Modifier.width(30.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.category),
                            contentDescription = "",
                            tint = MaterialTheme.colors.onSurface,
                            modifier = Modifier.size(30.dp)
                        )
                        Text(
                            text = meals.foodCategory,
                            fontFamily = myCustomFont,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(
                                color = MaterialTheme.colors.onSurface
                            )
                        )

                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(modifier = Modifier.padding(10.dp)) {
                        Column {
                            Text(
                                text = "Instructions",
                                fontFamily = myCustomFont,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                style = TextStyle(
                                    color = MaterialTheme.colors.onSurface
                                )
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = meals.foodRecipes,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = myCustomFont,
                                fontSize = 18.sp
                            )

                        }
                    }

                }
            }
        }
    }
}




