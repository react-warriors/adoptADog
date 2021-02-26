package com.example.androiddevchallenge

import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.challenge.adoptdoggy.ui.theme.darkOrange

@ExperimentalAnimationApi
@Composable
fun FullDetails(dogName: String?, dogOneId: Int?, dogTwoId: Int?, dogDesc: Int?) {
    val context = LocalContext.current

    var showFirstDogImage by remember { mutableStateOf(true) }

    Column(Modifier.fillMaxSize()) {
        TopBar(
            dogName = dogName,
            firstDogImage = dogOneId,
            secondDogImage = dogTwoId,
            firstDogClick = { showFirstDogImage = true },
            secondDogClick = { showFirstDogImage = false })
        DogImage(showFirstDogImage, dogOneId, dogTwoId, onClick = {
            Toast.makeText(context, "Thanks for adopting!", Toast.LENGTH_SHORT).show()
        })
        Text(
            text = stringResource(id = dogDesc!!),
            Modifier
                .fillMaxSize()
                .padding(12.dp)
                .verticalScroll(rememberScrollState())
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun DogImage(showFirstDogImage: Boolean, dogOneId: Int?, dogTwoId: Int?, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStartPercent = 100, topEndPercent = 100
                )
            )

    ) {

        Box(contentAlignment = Alignment.BottomEnd) {
            Crossfade(targetState = showFirstDogImage) { showFirst ->
                if (showFirst) {
                    Image(
                        painter = painterResource(id = dogOneId!!),
                        contentDescription = "One",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                            .border(2.dp, color = darkOrange)
                    )
                } else {
                    Image(
                        painter = painterResource(id = dogTwoId!!),
                        contentDescription = "Two",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                    )
                }
            }

            TextButton(onClick = {onClick()},
                modifier = Modifier
                    .clip(RoundedCornerShape(20))
                    .background(color = darkOrange)) {
                Text(text = "+ Adopt Now", style = TextStyle(color = Color.White))
            }
        }

        /*AnimatedVisibility(visible = showFirstDogImage.not()) {
            Image(
                painter = painterResource(id = R.drawable.dog_one_part_one),
                contentDescription = "One",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            )
        }

        AnimatedVisibility(visible = showFirstDogImage) {
            Image(
                painter = painterResource(id = R.drawable.dog_one_part_two),
                contentDescription = "Two",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            )
        }*/
    }
}

@Composable
private fun TopBar(
    dogName: String?,
    firstDogImage: Int?,
    secondDogImage: Int?,
    firstDogClick: () -> Unit, secondDogClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = dogName!!,
            textAlign = TextAlign.Center,
            style = TextStyle(color = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .background(color = darkOrange)
                .padding(16.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = firstDogImage!!),
                contentDescription = "One",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(64.dp)
                    .height(64.dp)
                    .clip(CircleShape)
                    .clickable {
                        firstDogClick()
                    }
            )

            Image(
                painter = painterResource(id = secondDogImage!!),
                contentDescription = "Two",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(64.dp)
                    .height(64.dp)
                    .clip(CircleShape)
                    .clickable {
                        secondDogClick()
                    }
            )
        }
    }
}