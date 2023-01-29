@file:OptIn(ExperimentalMaterial3Api::class)

package com.nishitnagar.simplibalance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.nishitnagar.simplibalance.data.ValueRatioEntity
import com.nishitnagar.simplibalance.utils.decimalFormat
import com.nishitnagar.simplibalance.viewmodel.ValueRatioViewModel
import com.nishitnagar.simplibalance.viewmodel.ValueRatioViewModelProvider

@Preview(showBackground = true)
@Composable
fun ValueSetter(
    @PreviewParameter(ValueRatioViewModelProvider::class) valueRatioViewModel: ValueRatioViewModel
) {
    val valueRatios = valueRatioViewModel.valueRatiosFlow.collectAsState(initial = listOf())

    Surface(modifier = Modifier.wrapContentSize(), color = MaterialTheme.colorScheme.secondaryContainer) {
        LazyColumn(
            modifier = Modifier.wrapContentSize()
        ) {
            items(valueRatios.value.size) { index ->
                ValueSetterRow(item = valueRatios.value[index], valueRatioViewModel = valueRatioViewModel)
            }
        }
    }
}

@Composable
fun ValueSetterRow(item: ValueRatioEntity, valueRatioViewModel: ValueRatioViewModel) {
    Column(modifier = Modifier.padding(horizontal = 8.dp)) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            ValueSetterTextField(
                value = item.buyIn,
                label = "Buy Ins",
                modifier = Modifier.weight(1f),
                onValueChange = {
                    item.buyIn = it.toDoubleOrNull()
                    valueRatioViewModel.update(item)
                })
            Text(
                text = "=", modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .wrapContentWidth()
            )
            ValueSetterTextField(value = item.chip, label = "Chips", modifier = Modifier.weight(1f), onValueChange = {
                item.chip = it.toDoubleOrNull()
                valueRatioViewModel.update(item)
            })
            Text(
                text = "=", modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .wrapContentWidth()
            )
            ValueSetterTextField(value = item.money, label = "Money", modifier = Modifier.weight(1f), onValueChange = {
                item.money = it.toDoubleOrNull()
                valueRatioViewModel.update(item)
            })
        }

        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(Color.Gray)
                .alpha(0.5f)
        )
    }
}

@Composable
fun ValueSetterTextField(
    value: Double?,
    label: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    var state by remember { mutableStateOf(TextFieldValue(decimalFormat.format(value))) }

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
        },
        singleLine = true
    )
}