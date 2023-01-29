package com.nishitnagar.simplibalance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.nishitnagar.simplibalance.ui.theme.SimplibalanceTheme
import kotlinx.coroutines.android.awaitFrame

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AddPlayerPopup(
    onClickSave: (String) -> Unit, onCLickDismiss: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    var state by remember { mutableStateOf(TextFieldValue("")) }

    Dialog(onDismissRequest = onCLickDismiss) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth(0.95f)
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(PaddingValues(horizontal = 16.dp, vertical = 16.dp))
        ) {
            TextField(
                value = state,
                onValueChange = { state = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                placeholder = { Text("Add Player Name") },
                keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Sentences)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PaddingValues(top = 16.dp))
            ) {
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = onCLickDismiss, modifier = Modifier.weight(1f)) {
                    Text(text = "Cancel")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { onClickSave(state.text) }, modifier = Modifier.weight(1f)) {
                    Text(text = "Save")
                }
            }
        }
    }

    val keyboard = LocalSoftwareKeyboardController.current
    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()
        awaitFrame()
        keyboard?.show()
    }
}

@Preview
@Composable
fun PreviewAddPlayerPopup() {
    SimplibalanceTheme {
        AddPlayerPopup(onClickSave = {}, onCLickDismiss = {})
    }
}