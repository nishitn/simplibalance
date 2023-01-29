package com.nishitnagar.simplibalance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nishitnagar.simplibalance.model.CustomError
import com.nishitnagar.simplibalance.model.PopupState
import com.nishitnagar.simplibalance.viewmodel.PlayerViewModelInterface

@Composable
fun BottomBar(playerBalanceViewModel: PlayerViewModelInterface) {
    val removeAllPopupState = remember { mutableStateOf(PopupState.CLOSE) }
    val settlePopupState = remember { mutableStateOf(PopupState.CLOSE) }
    val possibleErrorsState = remember { mutableStateOf(listOf<CustomError>()) }

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
                    removeAllPopupState.value = PopupState.OPEN
                }, modifier = Modifier.weight(1f)
            ) {
                Text(text = "Remove All")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    possibleErrorsState.value = playerBalanceViewModel.getPossibleSettlementErrors()
                    settlePopupState.value = PopupState.OPEN
                }, modifier = Modifier.weight(2f)
            ) {
                Text(text = "Balance")
            }
        }
    }

    ControlDeleteAll(popupState = removeAllPopupState, playerBalanceViewModel = playerBalanceViewModel)
    ControlStartSettlement(popupState = settlePopupState, possibleErrorsState = possibleErrorsState)
}