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
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.nishitnagar.simplibalance.ui.theme.SimplibalanceTheme
import com.nishitnagar.simplibalance.viewmodel.PlayerBalanceViewModel
import com.nishitnagar.simplibalance.viewmodel.PlayerBalanceViewModelProvider
import com.nishitnagar.simplibalance.viewmodel.PlayerViewModelInterface
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokerOutputActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val playerBalanceViewModel: PlayerBalanceViewModel by viewModels()
        setContent {
            BalanceOutputScreen(playerBalanceViewModel)
        }
    }
}

@Preview
@Composable
fun BalanceOutputScreen(
    @PreviewParameter(PlayerBalanceViewModelProvider::class) playerBalanceViewModel: PlayerViewModelInterface
) {
    SimplibalanceTheme {
        Scaffold(
            topBar = {
                AppBar(navigationIcon = { IconButton(onClick = {}) { Icon(Icons.Outlined.Menu, "Menu") } })
            },
            content = {
                OutputContent(
                    modifier = Modifier.padding(it),
                    playerBalanceViewModel = playerBalanceViewModel
                )
            })
    }
}

@Composable
fun OutputContent(modifier: Modifier = Modifier, playerBalanceViewModel: PlayerViewModelInterface) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(modifier = modifier.fillMaxSize()) {
            PlayerBalanceColumn(playerBalanceViewModel = playerBalanceViewModel)
        }
    }
}