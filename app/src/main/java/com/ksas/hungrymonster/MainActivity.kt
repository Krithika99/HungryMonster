package com.ksas.hungrymonster
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.ksas.hungrymonster.ui.theme.HungryMonsterTheme
import com.ksas.hungrymonster.viewmodel.MealViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import com.ksas.hungrymonster.AppNavigator
import com.ksas.hungrymonster.MealByCategoryScreen
import com.ksas.hungrymonster.api.MealCategory
import com.ksas.hungrymonster.api.SearchedMeal
import com.ksas.hungrymonster.myCustomFont
import com.ksas.hungrymonster.screen.SearchedMealScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HungryMonsterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AppNavigator()
                }
            }
        }
    }
}

@OptIn(
    ExperimentalMaterialApi::class
)
@Composable
fun HomeScreen(navController: NavHostController) {
    val viewModel: MealViewModel = viewModel()
    val meals = viewModel.mutableCategory.value
    val randomMeal = viewModel.mutableRandomMeal.value
    val searchedFood = remember {
        mutableStateOf(TextFieldValue())
    }
    val mutableSearchedMeal = remember {
        mutableStateOf("")
    }

    val mutableSearchedMealList: MutableState<List<SearchedMeal>> = remember {

        mutableStateOf(emptyList())
    }

    val mutableMealByCategory: MutableState<List<MealCategory>> = remember {
        mutableStateOf(emptyList())
    }



    Column {
        Box(
            modifier = Modifier
                .height(150.dp)
                .background(color = MaterialTheme.colors.onBackground)
        ) {
            Column(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colors.secondary)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    TextField(
                        value = searchedFood.value,
                        onValueChange = {
                            searchedFood.value = (it)
                            mutableSearchedMeal.value = (it.text)
                        },
                        placeholder = {
                            Text(
                                text = "Search any dish ",
                                color = Color.Black
                            )
                        },
                        modifier = Modifier
                            .background(color = Color.White),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "SearchIcon"
                            )
                        },
                        singleLine = true,
                        shape = CircleShape,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            navController.navigate("$SearchedMealScreen/${mutableSearchedMeal.value}")

                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                    )
                    {
                        Text(
                            text = "Find",
                            style = TextStyle(color = Color.Black),
                            fontFamily = myCustomFont,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

// Random Dish
        Box(
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Random Dish",
                    modifier = Modifier.padding(top = 5.dp),
                    style = TextStyle(
                        color = MaterialTheme.colors.background,
                        fontSize = 28.sp,
                        fontFamily = myCustomFont,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Start
                )
                LazyColumn {
                    items(randomMeal) { meal ->
                        Card(
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth()
                                .padding(5.dp),
                            border = BorderStroke(2.dp, Color.LightGray),
                            onClick = {
                                navController.navigate("${com.ksas.hungrymonster.MealDetailScreen}/${meal.foodName}")
                            }
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(meal.foodImageUrl),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                            Text(
                                text = meal.foodName,
                                textAlign = TextAlign.Justify,
                                color = Color.White,
                                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(top = 160.dp),
                                fontFamily = myCustomFont

                            )

                        }
                    }
                }
            }
        }

//Category box
        Box {
            Column {
                Text(
                    text = "Categories",
                    modifier = Modifier,
                    style = TextStyle(
                        color = MaterialTheme.colors.onSurface,
                        fontSize = 28.sp,
                        fontFamily = myCustomFont,
                        fontWeight = FontWeight.Bold
                    )
                )

                LazyVerticalGrid(columns = GridCells.Adaptive(128.dp), modifier = Modifier) {
                    items(meals) { food ->
                        Card(modifier = Modifier.clickable {},
                            onClick = {
                                navController.navigate("$MealByCategoryScreen/${food.foodName}")
                                Log.d(
                                    "MealByCategory",
                                    mutableMealByCategory.value.toString() + " " + food.foodName
                                )
                            }
                        ) {
                            Column(
                                modifier = Modifier,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(food.imageURL),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .align(alignment = Alignment.CenterHorizontally)
                                        .size(78.dp)
                                )
                                Spacer(modifier = Modifier.height(9.dp))
                                Text(
                                    text = food.foodName,
                                    fontFamily = myCustomFont,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 18.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}