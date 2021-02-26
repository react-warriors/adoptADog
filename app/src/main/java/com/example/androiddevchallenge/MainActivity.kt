/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.challenge.adoptdoggy.ui.theme.*
import com.example.androiddevchallenge.data.getListOfDogs

class MainActivity : AppCompatActivity() {

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            AdoptDoggyTheme {
                val navController = rememberNavController()

                // A surface container using the 'background' color from the theme
                Surface(color = background) {
                    ScreenNavigation(navController)
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun ScreenNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "Home") {
        composable("Home") {
            DogListWithHeader(navController)
        }

        composable(
            "Details/{dogName}/{dogOneId}/{dogTwoId}/{dogDesc}",
            arguments = listOf(navArgument("dogOneId") {
                type = NavType.IntType
            }, navArgument("dogTwoId") {
                type = NavType.IntType
            }, navArgument("dogDesc") {
                type = NavType.IntType
            }, navArgument("dogName") {
                type = NavType.StringType
            })
        ) { backstackEntry ->
            val arguments = backstackEntry.arguments
            FullDetails(
                arguments?.getString("dogName"),
                arguments?.getInt("dogOneId"),
                arguments?.getInt("dogTwoId"),
                arguments?.getInt("dogDesc"),
            )
        }
    }
}

@ExperimentalAnimationApi

@Composable
fun DogListWithHeader(navController: NavHostController) {
    val listOfItems = getListOfDogs()

    Column {
        Text(
            text = "Doggy Store",
            style = TextStyle(color = Color.White),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStartPercent = 20, bottomEndPercent = 20))
                .background(darkOrange)
                .padding(16.dp)
        )

        LazyColumn {
            items(listOfItems) {
                DogInfo(it.dogOneImage, it.dogTwoImage, it.dogDesc) {
                    navController.navigate("Details/${it.dogName}/${it.dogOneImage}/${it.dogTwoImage}/${it.dogDesc}")
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun DogInfo(firstImage: Int, secondImage: Int, dogDesc: Int, navFunc: () -> Unit) {

    var showFewLineDesc by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = showFewLineDesc)
    val thumbnailSize by transition.animateDp { descVisible ->
        if (descVisible) 120.dp else 64.dp
    }
    val thumbnailCorners by transition.animateDp { descVisible ->
        if (descVisible) 0.dp else 32.dp
    }
    val cardRoundCorners by transition.animateInt { descVisible ->
        if (descVisible) 8 else 32
    }
    val descMaxLines by transition.animateInt { descVisible ->
        if (descVisible) 4 else 2
    }

    val textColor by transition.animateColor { descVisible ->
        if (descVisible) Color.White else Color.Black
    }

    val gradientMidColor by transition.animateColor { descVisible ->
        if (descVisible) lightOrange else veryLightOrange
    }

    val gradientEndColor by transition.animateColor { descVisible ->
        if (descVisible) darkOrange else lightOrange
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(26.dp)
            .clip(
                RoundedCornerShape(
                    bottomStartPercent = cardRoundCorners,
                    bottomEndPercent = cardRoundCorners
                )
            )
            .clickable {
                showFewLineDesc = showFewLineDesc.not()
            }
            .background(
                Brush.verticalGradient(
                    listOf(Color.Transparent, gradientMidColor, gradientEndColor)
                )
            ).border(width = 1.dp, color = gradientEndColor)
    ) {

        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            DogThumbnail(
                firstImage, "One", Modifier
                    .weight(1f)
                    .width(thumbnailSize)
                    .height(thumbnailSize)
                    .clip(
                        RoundedCornerShape(
                            topStart = thumbnailCorners,
                            topEnd = thumbnailCorners
                        )
                    )
            )

            AnimatedVisibility(visible = showFewLineDesc.not()) {
                Spacer(modifier = Modifier.padding(end = 48.dp))
            }

            DogThumbnail(
                secondImage, "Two", Modifier
                    .weight(1f)
                    .width(thumbnailSize)
                    .height(thumbnailSize)
                    .clip(
                        RoundedCornerShape(
                            topStart = thumbnailCorners,
                            topEnd = thumbnailCorners
                        )
                    )
            )
        }

        Text(
            text = stringResource(id = dogDesc),
            textAlign = TextAlign.Center,
            maxLines = descMaxLines,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 34.dp)
        )

        IconButton(
            onClick = { navFunc() },
            modifier = Modifier
                .padding(8.dp)
                .clip(CircleShape)
                .background(color = gradientMidColor)
        ) {
            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Next")
        }

        AnimatedVisibility(visible = showFewLineDesc) {
            Text(
                text = "Move to next screen to see more details",
                style = TextStyle(fontSize = 12.sp, color = textColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun DogThumbnail(id: Int, contentDesc: String, modifier: Modifier) {
    Image(
        painter = painterResource(id = id), contentDescription = contentDesc,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}
