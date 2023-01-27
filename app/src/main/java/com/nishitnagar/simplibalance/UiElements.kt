package com.nishitnagar.simplibalance

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.nishitnagar.simplibalance.data.PlayerBalanceEntity
import com.nishitnagar.simplibalance.data.PlayerBalanceEntityProvider

@Preview(showBackground = true)
@Composable
fun PlayerBalanceRow(@PreviewParameter(PlayerBalanceEntityProvider::class) item: PlayerBalanceEntity) {
    Row(modifier = Modifier.wrapContentHeight(), verticalAlignment = Alignment.CenterVertically) {
        NameText(name = item.name, modifier = Modifier.weight(1f))

        BalanceTextField(value = item.initialValue,
            label = "Initial Value",
            modifier = Modifier.weight(1f),
            onValueChange = { item.initialValue = it.toDouble() })

        BalanceTextField(value = item.finalValue,
            label = "Final Value",
            modifier = Modifier.weight(1f),
            onValueChange = { item.finalValue = it.toDouble() })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BalanceTextField(value: Double, label: String, modifier: Modifier = Modifier, onValueChange: (String) -> Unit) {
    var state by remember { mutableStateOf(TextFieldValue(value.toString())) }
    OutlinedTextField(value = state,
        modifier = modifier
            .fillMaxHeight()
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    val text = state.text
                    state = state.copy(selection = TextRange(0, text.length))
                }
            },
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = {
            state = it
            onValueChange(state.text)
        })
}

@Composable
fun NameText(name: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxHeight(), contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = name
        )
    }
}