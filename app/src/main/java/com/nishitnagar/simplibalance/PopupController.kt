package com.nishitnagar.simplibalance

import android.content.Intent
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import com.nishitnagar.simplibalance.data.PlayerBalanceEntity
import com.nishitnagar.simplibalance.model.CustomError
import com.nishitnagar.simplibalance.model.ErrorTypes
import com.nishitnagar.simplibalance.model.PopupState
import com.nishitnagar.simplibalance.viewmodel.PlayerViewModelInterface

@Composable
fun ControlAddPlayerPopup(
    popupState: MutableState<PopupState>, playerBalanceViewModel: PlayerViewModelInterface
) {
    when (popupState.value) {
        PopupState.OPEN -> {
            AddPlayerPopup(onCLickDismiss = { popupState.value = PopupState.CLOSE }, onClickSave = {
                playerBalanceViewModel.insert(PlayerBalanceEntity(name = it))
                popupState.value = PopupState.CLOSE
            })
        }
        PopupState.CLOSE -> {
            /* Will just dismiss popup */
        }
    }
}

@Composable
fun ControlDeleteAll(
    popupState: MutableState<PopupState>, playerBalanceViewModel: PlayerViewModelInterface
) {
    when (popupState.value) {
        PopupState.OPEN -> {
            ConfirmationPopup(title = { Text("Confirm Remove All") },
                text = { Text(text = "Are you sure you want to remove all players?") },
                onConfirm = {
                    playerBalanceViewModel.deleteAll()
                    popupState.value = PopupState.CLOSE
                },
                onDismiss = { popupState.value = PopupState.CLOSE })
        }
        PopupState.CLOSE -> {
            /* Dismiss Popup only */
        }
    }
}

@Composable
fun ControlDeletePlayer(
    item: PlayerBalanceEntity, popupState: MutableState<PopupState>, playerBalanceViewModel: PlayerViewModelInterface
) {
    when (popupState.value) {
        PopupState.OPEN -> {
            ConfirmationPopup(title = { Text("Confirm Remove Player") },
                text = { Text(text = "Are you sure you want to remove player: ${item.name}?") },
                onConfirm = {
                    playerBalanceViewModel.delete(item)
                    popupState.value = PopupState.CLOSE
                },
                onDismiss = { popupState.value = PopupState.CLOSE })
        }
        PopupState.CLOSE -> {
            /* Dismiss Popup only */
        }
    }
}

@Composable
fun ControlStartSettlement(popupState: MutableState<PopupState>, possibleErrorsState: MutableState<List<CustomError>>) {
    when (popupState.value) {
        PopupState.OPEN -> {
            val errors = possibleErrorsState.value
            val message = errors.joinToString(separator = "\n") { "\u2022 ${it.description}" }
            val context = LocalContext.current

            if (errors.any { it.type == ErrorTypes.BLOCKING }) {
                AlertDialog(title = { Text("Input Error") },
                    text = { Text(text = message) },
                    onDismissRequest = { popupState.value = PopupState.CLOSE },
                    confirmButton = {
                        TextButton(onClick = { popupState.value = PopupState.CLOSE }) {
                            Text("OK")
                        }
                    })
            } else if (errors.isNotEmpty()) {
                ConfirmationPopup(title = { Text("Money mismatch") },
                    text = { Text(text = "$message\n\n Do you want to still try to balance?") },
                    onConfirm = {
                        context.startActivity(Intent(context, PokerOutputActivity::class.java))
                        popupState.value = PopupState.CLOSE
                    },
                    onDismiss = { popupState.value = PopupState.CLOSE },
                    confirmButtonText = "Yes Try"
                )
            } else {
                context.startActivity(Intent(context, PokerOutputActivity::class.java))
                popupState.value = PopupState.CLOSE
            }
        }
        PopupState.CLOSE -> {
            /* Dismiss Popup only */
        }
    }
}