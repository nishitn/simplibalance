package com.nishitnagar.simplibalance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun BottomBar() {
    Row(modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)) {
        Spacer(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .height(1.dp)
                .alpha(0.5f)
                .background(Color.Gray)
        )

        OutlinedButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
            Text(text = "Remove All")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = { /*TODO*/ }, modifier = Modifier.weight(2f)) {
            Text(text = "Balance")
        }
    }
}