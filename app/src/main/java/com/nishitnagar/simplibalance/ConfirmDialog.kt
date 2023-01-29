package com.nishitnagar.simplibalance

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.nishitnagar.simplibalance.data.PlayerBalanceEntity
import com.nishitnagar.simplibalance.model.RemoveAllPlayerPopupState
import com.nishitnagar.simplibalance.model.RemovePlayerPopupState
import com.nishitnagar.simplibalance.viewmodel.PlayerViewModelInterface

@Composable
fun ControlDeleteAll(
    removeAllPlayerPopupState: MutableState<RemoveAllPlayerPopupState>, playerBalanceViewModel: PlayerViewModelInterface
) {
    when (removeAllPlayerPopupState.value) {
        RemoveAllPlayerPopupState.OPEN -> {
            ConfirmationPopup(title = { Text("Confirm Remove All") },
                text = { Text(text = "Are you sure you want to remove all players?") },
                onConfirm = {
                    playerBalanceViewModel.deleteAll()
                    removeAllPlayerPopupState.value = RemoveAllPlayerPopupState.CLOSE
                },
                onDismiss = { removeAllPlayerPopupState.value = RemoveAllPlayerPopupState.CLOSE })
        }
        RemoveAllPlayerPopupState.CLOSE -> {
            /* Dismiss Popup only */
        }
    }
}

@Composable
fun ControlDeletePlayer(
    item: PlayerBalanceEntity,
    removePlayerPopupState: MutableState<RemovePlayerPopupState>,
    playerBalanceViewModel: PlayerViewModelInterface
) {
    when (removePlayerPopupState.value) {
        RemovePlayerPopupState.OPEN -> {
            ConfirmationPopup(title = { Text("Confirm Remove Player") },
                text = { Text(text = "Are you sure you want to remove player: ${item.name}?") },
                onConfirm = {
                    playerBalanceViewModel.delete(item)
                    removePlayerPopupState.value = RemovePlayerPopupState.CLOSE
                },
                onDismiss = { removePlayerPopupState.value = RemovePlayerPopupState.CLOSE })
        }
        RemovePlayerPopupState.CLOSE -> {
            /* Dismiss Popup only */
        }
    }
}

@Composable
fun ConfirmationPopup(
    title: @Composable () -> Unit, text: @Composable () -> Unit, onConfirm: () -> Unit, onDismiss: () -> Unit
) {
    AlertDialog(title = title, text = text, onDismissRequest = onDismiss, confirmButton = {
        Button(onClick = onConfirm) {
            Text("Confirm")
        }
    }, dismissButton = {
        TextButton(onClick = onDismiss) {
            Text("Cancel")
        }
    })
}