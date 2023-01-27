package com.nishitnagar.simplibalance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.nishitnagar.simplibalance.data.PlayerBalanceEntity
import com.nishitnagar.simplibalance.ui.theme.SimplibalanceTheme
import com.nishitnagar.simplibalance.viewmodel.PlayerBalanceViewModel
import com.nishitnagar.simplibalance.viewmodel.PlayerBalanceViewModelProvider
import com.nishitnagar.simplibalance.viewmodel.PlayerViewModelInterface
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val playerBalanceViewModel: PlayerBalanceViewModel by viewModels()

        setContent {
            BalanceInputScreen(playerBalanceViewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun BalanceInputScreen(
    @PreviewParameter(PlayerBalanceViewModelProvider::class) playerBalanceViewModel: PlayerViewModelInterface
) {
    SimplibalanceTheme {
        Scaffold(topBar = { AppBar() },
            content = { MainContent(modifier = Modifier.padding(it), playerBalanceViewModel) })
    }
}

@Composable
fun MainContent(modifier: Modifier, playerBalanceViewModel: PlayerViewModelInterface) {
    val playerBalances = playerBalanceViewModel.playerBalancesFlow.collectAsState(initial = listOf())

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        PlayerBalanceColumn(
            playerBalances = playerBalances, modifier = modifier, playerBalanceViewModel = playerBalanceViewModel
        )
    }
}

@Composable
fun PlayerBalanceColumn(
    playerBalances: State<List<PlayerBalanceEntity>>,
    modifier: Modifier = Modifier,
    playerBalanceViewModel: PlayerViewModelInterface,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(playerBalances.value.size) { index ->
            PlayerBalanceRow(item = playerBalances.value[index])
        }
        item {
            Button(onClick = { playerBalanceViewModel.insert(PlayerBalanceEntity(name = "name1")) }) {
                Text("Add Player")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = false)
@Composable
fun AppBar() {
    TopAppBar(title = {
        Text(text = "SimpliBalance")
    }, navigationIcon = {
        IconButton(onClick = {}) {
            Icon(Icons.Filled.ArrowBack, "backIcon", tint = MaterialTheme.colorScheme.onPrimary)
        }
    }, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary, titleContentColor = MaterialTheme.colorScheme.onPrimary
    )
    )
}