package com.nishitnagar.simplibalance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.nishitnagar.simplibalance.model.SettlementEntity
import com.nishitnagar.simplibalance.model.SettlementEntityProvider
import com.nishitnagar.simplibalance.utils.decimalFormat
import com.nishitnagar.simplibalance.viewmodel.PlayerViewModelInterface

@Composable
fun SettlementColumn(playerBalanceViewModel: PlayerViewModelInterface) {
    val settlementBalances = playerBalanceViewModel.settlementEntityFlow.collectAsState(initial = listOf())

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(settlementBalances.value.size) { index ->
            SettlementRow(item = settlementBalances.value[index])
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettlementRow(
    @PreviewParameter(SettlementEntityProvider::class) item: SettlementEntity,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(all = 16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = item.payerName, modifier = Modifier.weight(1f))
            Icon(
                Icons.Outlined.ArrowForward,
                contentDescription = "Pays To",
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Text(text = item.payeeName, modifier = Modifier.weight(1f))
            Text(text = "=", modifier = Modifier.padding(horizontal = 16.dp))
            Text(text = decimalFormat.format(item.amount), modifier = Modifier.weight(1f))
        }
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth(0.95f)
                .alpha(0.5f)
                .background(Color.Gray)
        )
    }
}
