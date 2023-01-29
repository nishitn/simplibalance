@file:OptIn(ExperimentalMaterial3Api::class)

package com.nishitnagar.simplibalance

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.nishitnagar.simplibalance.model.DismissActivityState
import com.nishitnagar.simplibalance.ui.theme.SimplibalanceTheme
import com.nishitnagar.simplibalance.viewmodel.PlayerBalanceViewModel
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

@Composable
fun BalanceOutputScreen(playerBalanceViewModel: PlayerViewModelInterface) {
    val dismissActivityState = remember { mutableStateOf(DismissActivityState.KEEP) }
    SimplibalanceTheme {
        Scaffold(topBar = {
            AppBar(navigationIcon = {
                IconButton(onClick = {
                    dismissActivityState.value = DismissActivityState.DISMISS
                }) { Icon(Icons.Outlined.ArrowBack, "Back") }
            })
        }, content = {
            OutputContent(
                modifier = Modifier.padding(it), playerBalanceViewModel = playerBalanceViewModel
            )
        })
    }

    DismissActivity(dismissActivityState = dismissActivityState)
}

@Composable
fun OutputContent(modifier: Modifier = Modifier, playerBalanceViewModel: PlayerViewModelInterface) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(modifier = modifier.fillMaxSize()) {
            SettlementColumn(playerBalanceViewModel = playerBalanceViewModel)
        }
    }
}

@Composable
fun DismissActivity(dismissActivityState: MutableState<DismissActivityState>) {
    when (dismissActivityState.value) {
        DismissActivityState.KEEP -> {
            /* Do Nothing */
        }
        DismissActivityState.DISMISS -> {
            val activity = LocalContext.current as Activity
            activity.finish()
        }
    }
}