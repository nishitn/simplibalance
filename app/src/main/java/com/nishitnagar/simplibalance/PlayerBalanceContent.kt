@file:OptIn(ExperimentalMaterial3Api::class)

package com.nishitnagar.simplibalance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.nishitnagar.simplibalance.data.PlayerBalanceEntity
import com.nishitnagar.simplibalance.data.PlayerBalanceEntityProvider
import com.nishitnagar.simplibalance.viewmodel.PlayerViewModelInterface

@Composable
fun PlayerBalanceColumn(
    modifier: Modifier = Modifier, playerBalanceViewModel: PlayerViewModelInterface
) {
    val playerBalances = playerBalanceViewModel.playerBalancesFlow.collectAsState(initial = listOf())

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(playerBalances.value.size) { index ->
            PlayerBalanceRow(item = playerBalances.value[index], playerBalanceViewModel = playerBalanceViewModel)
        }
    }
}

@Composable
fun PlayerBalanceRow(
    @PreviewParameter(PlayerBalanceEntityProvider::class) item: PlayerBalanceEntity,
    playerBalanceViewModel: PlayerViewModelInterface
) {
    Column(
        modifier = Modifier.padding(all = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = item.name,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                style = MaterialTheme.typography.bodyLarge
            )
            BuyInTextField(value = item.buyIns, onValueChange = {
                item.buyIns = if (it.toDoubleOrNull() == null) 0.0 else it.toDouble()
                playerBalanceViewModel.update(item)
            })
        }
        Row {
            BalanceTextField(value = item.initialValue,
                label = "Initial Value",
                modifier = Modifier.weight(1f),
                onValueChange = {
                    item.initialValue = if (it.toDoubleOrNull() == null) 0.0 else it.toDouble()
                    playerBalanceViewModel.update(item)
                })

            Spacer(modifier = Modifier.width(8.dp))

            BalanceTextField(value = item.finalValue,
                label = "Final Value",
                modifier = Modifier.weight(1f),
                onValueChange = {
                    item.finalValue = if (it.toDoubleOrNull() == null) 0.0 else it.toDouble()
                    playerBalanceViewModel.update(item)
                })
        }
        Spacer(
            modifier = Modifier
                .padding(top = 8.dp)
                .height(1.dp)
                .fillMaxWidth(0.98f)
                .alpha(0.5f)
                .align(Alignment.CenterHorizontally)
                .background(Color.Gray)
        )
    }
}

@Composable
fun BuyInTextField(value: Double, onValueChange: (String) -> Unit) {
    var buyInValue by remember { mutableStateOf(TextFieldValue(String.format("%.00f", value))) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        TextButton(onClick = {
            val buyIn = buyInValue.text.toDoubleOrNull()
            if (buyIn != null) buyInValue = TextFieldValue(String.format("%.00f", buyInValue.text.toDouble() - 1))
            onValueChange(buyInValue.text)
        }) {
            Text("-")
        }

        Text(text = "Buy Ins: ", style = MaterialTheme.typography.labelMedium)

        BasicTextField(
            value = buyInValue, onValueChange = {
                buyInValue = it
                onValueChange(buyInValue.text)
            }, modifier = Modifier.width(IntrinsicSize.Min), textStyle = MaterialTheme.typography.labelLarge.copy(
                color = if (buyInValue.text.toDoubleOrNull() == null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onBackground
            )
        )

        TextButton(onClick = {
            val buyIn = buyInValue.text.toDoubleOrNull()
            if (buyIn != null) buyInValue = TextFieldValue(String.format("%.00f", buyInValue.text.toDouble() + 1))
            onValueChange(buyInValue.text)
        }) {
            Text("+")
        }
    }
}

@Composable
fun BalanceTextField(value: Double, label: String, modifier: Modifier = Modifier, onValueChange: (String) -> Unit) {
    var state by remember { mutableStateOf(TextFieldValue(value.toString())) }
    OutlinedTextField(value = state,
        modifier = modifier.onFocusChanged { focusState ->
            if (focusState.isFocused) {
                val text = state.text
                state = state.copy(selection = TextRange(0, text.length))
            }
        },
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        trailingIcon = {
            if (state.text.toDoubleOrNull() == null) {
                Icon(Icons.Filled.Warning, "error", tint = MaterialTheme.colorScheme.error)
            }
        },
        onValueChange = {
            state = it
            onValueChange(state.text)
        })
}