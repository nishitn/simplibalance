package com.nishitnagar.simplibalance

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.nishitnagar.simplibalance.model.RemoveAllPlayerPopupState
import com.nishitnagar.simplibalance.viewmodel.PlayerBalanceViewModelProvider
import com.nishitnagar.simplibalance.viewmodel.PlayerViewModelInterface

@Preview(showBackground = true)
@Composable
fun BottomBar(
    @PreviewParameter(PlayerBalanceViewModelProvider::class) playerBalanceViewModel: PlayerViewModelInterface
) {
    val removeAllPopupState = remember { mutableStateOf(RemoveAllPlayerPopupState.CLOSE) }
    val context = LocalContext.current

    Column {
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .alpha(0.5f)
                .background(Color.Gray)
        )

        Row(modifier = Modifier.padding(all = 8.dp)) {
            OutlinedButton(
                onClick = {
                    removeAllPopupState.value = RemoveAllPlayerPopupState.OPEN
                }, modifier = Modifier.weight(1f)
            ) {
                Text(text = "Remove All")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { context.startActivity(Intent(context, PokerOutputActivity::class.java)) },
                modifier = Modifier.weight(2f)
            ) {
                Text(text = "Balance")
            }
        }
    }
    ControlDeleteAll(removeAllPlayerPopupState = removeAllPopupState, playerBalanceViewModel = playerBalanceViewModel)
}