package com.ksas.hungrymonster.screen

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.ksas.hungrymonster.R
import com.ksas.hungrymonster.api.SearchedMeal
import com.ksas.hungrymonster.myCustomFont
import com.ksas.hungrymonster.viewmodel.MealViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val SearchedMealScreen = "SearchedMealScreen"

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SearchedMealScreen(navController: NavHostController, foodName: String) {
    val shape = RoundedCornerShape(22.dp)
    val viewModel: MealViewModel = viewModel()
    val mutableSearchedMealList: MutableState<List<SearchedMeal>> = remember {
        mutableStateOf(emptyList())
    }
    val scope = CoroutineScope(Dispatchers.IO)
    scope.launch {
        val searchedMealList =
            viewModel.getSearchedMeal(foodName)
        mutableSearchedMealList.value = searchedMealList
    }
    if (mutableSearchedMealList.value == null) {
        Log.d("Empty", "Not found!!!")
        Box(
            modifier = Modifier
                .background(color = MaterialTheme.colors.background)
                .fillMaxSize()
                .wrapContentSize(align = Alignment.Center)
        ) {
            Text(
                text = "Sorry Dish not found!!",
                fontFamily = myCustomFont,
                fontWeight = FontWeight.Bold,
                modifier = Modifier,
                style = TextStyle(color = MaterialTheme.colors.onError)
            )
        }

    } else if (mutableSearchedMealList.value.size > 1) {
        Box() {
            LazyColumn {
                items(mutableSearchedMealList.value) { meals ->
                    Card(
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                            .padding(10.dp),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(2.dp, Color.LightGray),
                        onClick = {
                            navController.navigate("${com.ksas.hungrymonster.MealDetailScreen}/${meals.foodName}")
                        }
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(meals.foodImageUrl),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
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

                }
            }
        }
    } else {
        Column {

            LazyColumn {
                items(mutableSearchedMealList.value) { meals ->
                    if (meals.foodName == foodName) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = meals.foodName,
                                fontFamily = myCustomFont,
                                fontSize = 30.sp,
                                textAlign = TextAlign.Center
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = meals.foodArea,
                                    fontFamily = myCustomFont,
                                    fontSize = 18.sp
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(text = "|")
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = meals.foodCategory,
                                    fontFamily = myCustomFont,
                                    fontSize = 18.sp
                                )
                            }


                        }
                        val context = LocalContext.current
                        val youtubeIntent =
                            remember {
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(meals.foodYoutubeLink)
                                )
                            }
                        val sourceIntent = remember {
                            Intent(Intent.ACTION_VIEW, Uri.parse(meals.strSource))
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Column(modifier = Modifier.padding(top = 10.dp)) {
                                ExtendedFloatingActionButton(
                                    text = { Text(text = "Youtube", fontFamily = myCustomFont) },
                                    onClick = { context.startActivity(youtubeIntent) },
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.youtube),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .size(28.dp)

                                        )
                                    },
                                    elevation = FloatingActionButtonDefaults.elevation(8.dp),
                                    backgroundColor = Color(color = 0xFFACA8B3),
                                    modifier = Modifier
                                        .width(150.dp)
                                        .padding(bottom = 5.dp),
                                    shape = RectangleShape
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                ExtendedFloatingActionButton(
                                    text = { Text(text = "Source", fontFamily = myCustomFont) },
                                    onClick = { context.startActivity(sourceIntent) },
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.link),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .size(28.dp)

                                        )
                                    },
                                    elevation = FloatingActionButtonDefaults.elevation(8.dp),
                                    backgroundColor = Color(color = 0xFFB5CF8B),
                                    modifier = Modifier
                                        .width(130.dp)
                                        .padding(bottom = 5.dp),
                                    shape = RectangleShape
                                )

                            }
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Image(
                                    painter = rememberAsyncImagePainter(meals.foodImageUrl),
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(250.dp)
                                        .align(alignment = Alignment.End)
                                        .padding(start = 0.dp)

                                )
                            }

                        } //row
                        Card(modifier = Modifier.clip(shape = shape)) {
                            Box(
                                modifier = Modifier
                                    .background(color = MaterialTheme.colors.secondaryVariant)
                                    .fillMaxWidth()

                            ) {
                                Column(modifier = Modifier) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 10.dp)
                                    ) {
                                        Text(
                                            text = "Ingredients",
                                            fontFamily = myCustomFont,
                                            fontSize = 30.sp
                                        )
                                    }

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        Column {
                                            if (!meals.strIngredient1.isNullOrEmpty())
                                                Text(
                                                    text = meals.strIngredient1 + " :",
                                                    fontFamily = myCustomFont,
                                                    fontSize = 18.sp
                                                )
                                            if (!meals.strIngredient2.isNullOrEmpty())
                                                Text(
                                                    text = meals.strIngredient2 + " :",
                                                    fontFamily = myCustomFont,
                                                    fontSize = 18.sp
                                                )
                                            if (!meals.strIngredient3.isNullOrEmpty())
                                                Text(
                                                    text = meals.strIngredient3 + " :",
                                                    fontFamily = myCustomFont,
                                                    fontSize = 18.sp
                                                )
                                            if (!meals.strIngredient4.isNullOrEmpty())
                                                Text(
                                                    text = meals.strIngredient4 + " :",
                                                    fontFamily = myCustomFont,
                                                    fontSize = 18.sp
                                                )
                                            if (!meals.strIngredient5.isNullOrEmpty())
                                                Text(
                                                    text = meals.strIngredient5 + " :",
                                                    fontFamily = myCustomFont,
                                                    fontSize = 18.sp
                                                )
                                            if (!meals.strIngredient6.isNullOrEmpty())
                                                Text(
                                                    text = meals.strIngredient6 + " :",
                                                    fontFamily = myCustomFont,
                                                    fontSize = 18.sp
                                                )
                                            if (!meals.strIngredient7.isNullOrEmpty())
                                                Text(
                                                    text = meals.strIngredient7 + " :",
                                                    fontFamily = myCustomFont,
                                                    fontSize = 18.sp
                                                )
                                            if (!meals.strIngredient8.isNullOrEmpty())
                                                Text(
                                                    text = meals.strIngredient8 + " :",
                                                    fontFamily = myCustomFont,
                                                    fontSize = 18.sp
                                                )
                                            if (!meals.strIngredient9.isNullOrEmpty())
                                                Text(
                                                    text = meals.strIngredient9 + " :",
                                                    fontFamily = myCustomFont,
                                                    fontSize = 18.sp
                                                )
                                            if (!meals.strIngredient10.isNullOrEmpty())
                                                Text(
                                                    text = meals.strIngredient10 + " :",
                                                    fontFamily = myCustomFont,
                                                    fontSize = 18.sp
                                                )
                                            if (!meals.strIngredient11.isNullOrEmpty())
                                                meals.strIngredient11?.let {
                                                    Text(
                                                        text = "$it :",
                                                        fontFamily = myCustomFont,
                                                        fontSize = 18.sp
                                                    )
                                                }
                                            if (!meals.strIngredient12.isNullOrEmpty())
                                                meals.strIngredient12?.let {
                                                    Text(
                                                        text = "$it :",
                                                        fontFamily = myCustomFont,
                                                        fontSize = 18.sp
                                                    )
                                                }
                                            if (!meals.strIngredient13.isNullOrEmpty())
                                                meals.strIngredient13?.let {
                                                    Text(
                                                        text = "$it :",
                                                        fontFamily = myCustomFont,
                                                        fontSize = 18.sp
                                                    )
                                                }
                                            if (!meals.strIngredient14.isNullOrEmpty())
                                                meals.strIngredient14?.let {
                                                    Text(
                                                        text = "$it :",
                                                        fontFamily = myCustomFont,
                                                        fontSize = 18.sp
                                                    )
                                                }
                                            if (!meals.strIngredient15.isNullOrEmpty())
                                                meals.strIngredient15?.let {
                                                    Text(
                                                        text = "$it :",
                                                        fontFamily = myCustomFont,
                                                        fontSize = 18.sp
                                                    )
                                                }
                                            if (!meals.strIngredient16.isNullOrEmpty())
                                                meals.strIngredient16?.let {
                                                    Text(
                                                        text = "$it :",
                                                        fontFamily = myCustomFont,
                                                        fontSize = 18.sp
                                                    )
                                                }
                                            if (!meals.strIngredient17.isNullOrEmpty())
                                                meals.strIngredient17?.let {
                                                    Text(
                                                        text = "$it :",
                                                        fontFamily = myCustomFont,
                                                        fontSize = 18.sp
                                                    )
                                                }
                                            if (!meals.strIngredient18.isNullOrEmpty())
                                                meals.strIngredient18?.let {
                                                    Text(
                                                        text = "$it :",
                                                        fontFamily = myCustomFont,
                                                        fontSize = 18.sp
                                                    )
                                                }
                                            if (!meals.strIngredient19.isNullOrEmpty())
                                                meals.strIngredient19?.let {
                                                    Text(
                                                        text = "$it :",
                                                        fontFamily = myCustomFont,
                                                        fontSize = 18.sp
                                                    )
                                                }
                                            if (!meals.strIngredient20.isNullOrEmpty())
                                                meals.strIngredient20?.let {
                                                    Text(
                                                        text = "$it :",
                                                        fontFamily = myCustomFont,
                                                        fontSize = 18.sp
                                                    )
                                                }

                                        }

                                        Column {
                                            if (!meals.strMeasure1.isNullOrEmpty() && meals.strMeasure1 != " ")
                                                Text(
                                                    text = meals.strMeasure1,
                                                    fontFamily = myCustomFont,
                                                    fontSize = 18.sp
                                                )
                                            if (!meals.strMeasure2.isNullOrEmpty() && meals.strMeasure2 != " ")
                                                Text(
                                                    text = meals.strMeasure2,
                                                    fontFamily = myCustomFont,
                                                    fontSize = 18.sp
                                                )
                                            if (!meals.strMeasure3.isNullOrEmpty() && meals.strMeasure3 != " ")
                                                Text(
                                                    text = meals.strMeasure3,
                                                    fontFamily = myCustomFont,
                                                    fontSize = 18.sp
                                                )
                                            if (!meals.strMeasure4.isNullOrEmpty() && meals.strMeasure4 != " ")
                                                Text(
                                                    text = meals.strMeasure4,
                                                    fontFamily = myCustomFont,
                                                    fontSize = 18.sp
                                                )
                                            if (!meals.strMeasure5.isNullOrEmpty() && meals.strMeasure5 != " ")
                                                Text(
                                                    text = meals.strMeasure5,
                                                    fontFamily = myCustomFont,
                                                    fontSize = 18.sp
                                                )
                                            if (!meals.strMeasure6.isNullOrEmpty() && meals.strMeasure6 != " ")
                                                Text(
                                                    text = meals.strMeasure6,
                                                    fontFamily = myCustomFont,
                                                    fontSize = 18.sp
                                                )
                                            if (!meals.strMeasure7.isNullOrEmpty() && meals.strMeasure7 != " ")
                                                Text(
                                                    text = meals.strMeasure7,
                                                    fontFamily = myCustomFont,
                                                    fontSize = 18.sp
                                                )
                                            if (!meals.strMeasure8.isNullOrEmpty() && meals.strMeasure8 != " ")
                                                Text(
                                                    text = meals.strMeasure8,
                                                    fontFamily = myCustomFont,
                                                    fontSize = 18.sp
                                                )
                                            if (!meals.strMeasure9.isNullOrEmpty() && meals.strMeasure9 != " ")
                                                Log.d("measure9", "is null")
                                            Text(
                                                text = meals.strMeasure9,
                                                fontFamily = myCustomFont,
                                                fontSize = 18.sp
                                            )
                                            if (!meals.strMeasure10.isNullOrEmpty() && meals.strMeasure10 != " ")
                                                Text(
                                                    text = meals.strMeasure10,
                                                    fontFamily = myCustomFont,
                                                    fontSize = 18.sp
                                                )
                                            if (!meals.strMeasure11.isNullOrEmpty() && meals.strMeasure11 != " ")
                                                meals.strMeasure11?.let {
                                                    Text(
                                                        text = it,
                                                        fontFamily = myCustomFont,
                                                        fontSize = 18.sp
                                                    )
                                                }
                                            if (!meals.strMeasure12.isNullOrEmpty() && meals.strMeasure12 != " ")
                                                meals.strMeasure12?.let {
                                                    Text(
                                                        text = it,
                                                        fontFamily = myCustomFont,
                                                        fontSize = 18.sp
                                                    )
                                                }
                                            if (!meals.strMeasure13.isNullOrEmpty() && meals.strMeasure13 != " ")
                                                meals.strMeasure13?.let {
                                                    Text(
                                                        text = it,
                                                        fontFamily = myCustomFont,
                                                        fontSize = 18.sp
                                                    )
                                                }
                                            if (!meals.strMeasure14.isNullOrEmpty() && meals.strMeasure14 != " ")
                                                meals.strMeasure14?.let {
                                                    Text(
                                                        text = it,
                                                        fontFamily = myCustomFont,
                                                        fontSize = 18.sp
                                                    )
                                                }
                                            if (!meals.strMeasure15.isNullOrEmpty() && meals.strMeasure15 != " ")
                                                meals.strMeasure15?.let {
                                                    Log.d("measure15", "blank")
                                                    Text(
                                                        text = it,
                                                        fontFamily = myCustomFont,
                                                        fontSize = 18.sp
                                                    )
                                                }
                                            if (!meals.strMeasure16.isNullOrEmpty() && meals.strMeasure16 != " ")
                                                Log.d("measure16", "blank")
                                            meals.strMeasure16?.let {
                                                Text(
                                                    text = it,
                                                    fontFamily = myCustomFont,
                                                    fontSize = 18.sp
                                                )
                                            }
                                            if (!meals.strMeasure17.isNullOrEmpty() && meals.strMeasure17 != " ")
                                                meals.strMeasure17?.let {
                                                    Text(
                                                        text = it,
                                                        fontFamily = myCustomFont,
                                                        fontSize = 18.sp
                                                    )
                                                }
                                            if (!meals.strMeasure18.isNullOrEmpty() && meals.strMeasure18 != " ")
                                                meals.strMeasure18?.let {
                                                    Text(
                                                        text = it,
                                                        fontFamily = myCustomFont,
                                                        fontSize = 18.sp
                                                    )
                                                }
                                            if (!meals.strMeasure19.isNullOrEmpty() && meals.strMeasure19 != " ")
                                                meals.strMeasure19?.let {
                                                    Text(
                                                        text = it,
                                                        fontFamily = myCustomFont,
                                                        fontSize = 18.sp
                                                    )
                                                }
                                            if (!meals.strMeasure20.isNullOrEmpty() && meals.strMeasure20 != " ")
                                                meals.strMeasure20?.let {
                                                    Text(
                                                        text = it,
                                                        fontFamily = myCustomFont,
                                                        fontSize = 18.sp
                                                    )
                                                }


                                        }

                                    }
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 10.dp, bottom = 10.dp)
                                    ) {
                                        Text(
                                            text = "Instruction",
                                            fontFamily = myCustomFont,
                                            fontSize = 30.sp
                                        )

                                        Text(
                                            text = meals.foodRecipes,
                                            fontFamily = myCustomFont,
                                            fontSize = 19.sp
                                        )
                                    }

                                }


                            }
                        }//card
                    }
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "Sorry Dish Not Found!",
                            fontFamily = myCustomFont,
                            fontSize = 30.sp,
                            color = MaterialTheme.colors.onError
                        )
                    }
                }//items
            }
        }
    }
}