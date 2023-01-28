@file:OptIn(ExperimentalMaterial3Api::class)

package com.nishitnagar.simplibalance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.nishitnagar.simplibalance.data.PlayerBalanceEntity
import com.nishitnagar.simplibalance.model.PopupStates
import com.nishitnagar.simplibalance.ui.theme.SimplibalanceTheme
import com.nishitnagar.simplibalance.viewmodel.PlayerBalanceViewModel
import com.nishitnagar.simplibalance.viewmodel.PlayerBalanceViewModelProvider
import com.nishitnagar.simplibalance.viewmodel.PlayerViewModelInterface
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokerInputActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val playerBalanceViewModel: PlayerBalanceViewModel by viewModels()
        setContent {
            BalanceInputScreen(playerBalanceViewModel)
        }
    }
}

@Preview
@Composable
fun BalanceInputScreen(
    @PreviewParameter(PlayerBalanceViewModelProvider::class) playerBalanceViewModel: PlayerViewModelInterface
) {
    SimplibalanceTheme {
        val popupState = remember { mutableStateOf(PopupStates.CLOSE) }
        Scaffold(topBar = { AppBar() },
            content = { MainContent(modifier = Modifier.padding(it), playerBalanceViewModel) },
            bottomBar = { BottomBar() },
            floatingActionButton = { AddPlayerFloatingActionButton(popupState) })
        ControlPopup(popupState = popupState, playerBalanceViewModel = playerBalanceViewModel)
    }
}

@Composable
fun ControlPopup(popupState: MutableState<PopupStates>, playerBalanceViewModel: PlayerViewModelInterface) {
    when (popupState.value) {
        PopupStates.OPEN -> {
            AddPlayerPopup(onCLickDismiss = { popupState.value = PopupStates.CLOSE }, onClickSave = {
                playerBalanceViewModel.insert(PlayerBalanceEntity(name = it))
                popupState.value = PopupStates.CLOSE
            })
        }
        PopupStates.CLOSE -> {
            /* Will just dismiss popup */
        }
    }
}

@Composable
fun AddPlayerFloatingActionButton(popupState: MutableState<PopupStates>) {
    FloatingActionButton(onClick = { popupState.value = PopupStates.OPEN }) {
        Icon(Icons.Default.Add, contentDescription = "Add Player")
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier, playerBalanceViewModel: PlayerViewModelInterface) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        PlayerBalanceColumn(modifier = modifier, playerBalanceViewModel = playerBalanceViewModel)
    }
}

@Composable
fun AppBar() {
    TopAppBar(title = { Text(text = "SimpliBalance") },
        navigationIcon = { IconButton(onClick = {}) { Icon(Icons.Outlined.Menu, "backIcon") } },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
        )
    )
}