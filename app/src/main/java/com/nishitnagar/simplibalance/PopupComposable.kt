package com.nishitnagar.simplibalance

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.android.awaitFrame

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AddPlayerPopup(
    onClickSave: (String) -> Unit, onDismiss: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    var state by remember { mutableStateOf(TextFieldValue("")) }

    ConfirmationPopup(title = { Text("Add Player") }, text = {
        TextField(value = state,
            onValueChange = { state = it },
            modifier = Modifier.focusRequester(focusRequester),
            placeholder = { Text("Player Name") },
            keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Sentences)
        )
    }, onConfirm = { onClickSave(state.text) }, onDismiss = onDismiss, confirmButtonText = "Add Player"
    )

    val keyboard = LocalSoftwareKeyboardController.current
    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()
        awaitFrame()
        keyboard?.show()
    }
}

@Composable
fun ConfirmationPopup(
    title: @Composable () -> Unit,
    text: @Composable () -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    confirmButtonText: String = "Confirm",
    dismissButtonText: String = "Cancel"
) {
    AlertDialog(title = title, text = text, onDismissRequest = onDismiss, confirmButton = {
        Button(onClick = onConfirm) {
            Text(confirmButtonText)
        }
    }, dismissButton = {
        TextButton(onClick = onDismiss) {
            Text(dismissButtonText)
        }
    })
}