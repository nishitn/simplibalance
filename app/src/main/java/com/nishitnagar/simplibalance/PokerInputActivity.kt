@file:OptIn(ExperimentalMaterial3Api::class)

package com.nishitnagar.simplibalance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.nishitnagar.simplibalance.data.PlayerBalanceEntity
import com.nishitnagar.simplibalance.model.AddPlayerPopupState
import com.nishitnagar.simplibalance.ui.theme.SimplibalanceTheme
import com.nishitnagar.simplibalance.viewmodel.PlayerBalanceViewModel
import com.nishitnagar.simplibalance.viewmodel.PlayerViewModelInterface
import com.nishitnagar.simplibalance.viewmodel.ValueRatioViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokerInputActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val playerBalanceViewModel: PlayerBalanceViewModel by viewModels()
        val valueRatioViewModel: ValueRatioViewModel by viewModels()

        setContent {
            BalanceInputScreen(playerBalanceViewModel, valueRatioViewModel)
        }
    }
}

@Composable
fun BalanceInputScreen(
    playerBalanceViewModel: PlayerViewModelInterface, valueRatioViewModel: ValueRatioViewModel
) {
    SimplibalanceTheme {
        val addPlayerPopupState = remember { mutableStateOf(AddPlayerPopupState.CLOSE) }
        Scaffold(topBar = {
            AppBar(navigationIcon = { IconButton(onClick = {}) { Icon(Icons.Outlined.Menu, "Menu") } })
        },
            content = {
                MainContent(
                    modifier = Modifier.padding(it),
                    playerBalanceViewModel = playerBalanceViewModel,
                    valueRatioViewModel = valueRatioViewModel
                )
            },
            bottomBar = { BottomBar(playerBalanceViewModel = playerBalanceViewModel) },
            floatingActionButton = { AddPlayerFloatingActionButton(addPlayerPopupState) })
        ControlPopup(addPlayerPopupState = addPlayerPopupState, playerBalanceViewModel = playerBalanceViewModel)
    }
}

@Composable
fun ControlPopup(
    addPlayerPopupState: MutableState<AddPlayerPopupState>, playerBalanceViewModel: PlayerViewModelInterface
) {
    when (addPlayerPopupState.value) {
        AddPlayerPopupState.OPEN -> {
            AddPlayerPopup(onCLickDismiss = { addPlayerPopupState.value = AddPlayerPopupState.CLOSE }, onClickSave = {
                playerBalanceViewModel.insert(PlayerBalanceEntity(name = it))
                addPlayerPopupState.value = AddPlayerPopupState.CLOSE
            })
        }
        AddPlayerPopupState.CLOSE -> {
            /* Will just dismiss popup */
        }
    }
}

@Composable
fun AddPlayerFloatingActionButton(popupState: MutableState<AddPlayerPopupState>) {
    FloatingActionButton(onClick = { popupState.value = AddPlayerPopupState.OPEN }) {
        Icon(Icons.Default.Add, contentDescription = "Add Player")
    }
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    playerBalanceViewModel: PlayerViewModelInterface,
    valueRatioViewModel: ValueRatioViewModel
) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(modifier = modifier.fillMaxSize()) {
            ValueSetter(valueRatioViewModel = valueRatioViewModel)
            PlayerBalanceColumn(playerBalanceViewModel = playerBalanceViewModel)
        }
    }
}

@Composable
fun AppBar(navigationIcon: @Composable () -> Unit) {
    TopAppBar(
        title = { Text(text = "SimpliBalance") },
        navigationIcon = navigationIcon,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
        )
    )
}