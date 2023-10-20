package com.example.midterm_aram_azatyan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.midterm_aram_azatyan.ui.theme.Midterm_Aram_AzatyanTheme
import kotlin.math.absoluteValue
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NumberGuessingGame()
        }
    }
}

@Composable
fun NumberGuessingGame() {
    val random = Random.Default

    var numGames by remember { mutableStateOf(0) }
    val minRange by remember { mutableStateOf(1) }
    val maxRange by remember { mutableStateOf(100) }
    var sliderValue by remember { mutableStateOf((minRange + maxRange) / 2) }
    var generatedValue by remember { mutableStateOf(random.nextInt(1, 101)) }
    var guessedNumber by remember { mutableStateOf(sliderValue) }
    var feedback by remember { mutableStateOf("") }
    var score by remember { mutableStateOf(0) }
    var isGameDone by remember { mutableStateOf(false) }
    var finalMessage by remember { mutableStateOf("") }

    fun processGuessedValue(guess: Int) {
        val difference = generatedValue - guessedNumber

        if (difference.absoluteValue <= 3) {
            score += 5
            feedback = "Perfect! You scored 5 points."
        } else if (difference.absoluteValue <= 8) {
            score += 1
            feedback = "Almost there... You scored 1 point"
        } else {
            feedback = "Try again to get closer"
        }

        sliderValue = (minRange + maxRange) / 2

        numGames += 1

        if (numGames >= 5) {
            finalMessage = "Your total score is: $score"
            isGameDone = true
        } else {
            generatedValue = random.nextInt(1, 101)
        }



    }

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Bull's Eye Game", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Move the slider as close as you can to: $generatedValue", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Slider (
            value = sliderValue.toFloat(),
            onValueChange = {newValue ->
                sliderValue = newValue.toInt()
                guessedNumber = sliderValue },
            valueRange = minRange.toFloat() .. maxRange.toFloat(),
            steps = 100,
            enabled = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button (
            enabled = !isGameDone,
            onClick = {
                processGuessedValue(guessedNumber)
            },
            modifier = Modifier.padding(0.dp)
        ) {
            Text("Hit Me!", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Your Score: $score")

        Spacer(modifier = Modifier.height(32.dp))

        Text(feedback, color = Color.DarkGray)

        Spacer(modifier = Modifier.height(32.dp))

        Text(finalMessage, color = Color.Black)

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Midterm_Aram_AzatyanTheme {
        NumberGuessingGame()
    }
}