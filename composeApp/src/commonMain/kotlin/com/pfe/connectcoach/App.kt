package com.pfe.connectcoach

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pfe.connectcoach.model.AppTab
import com.pfe.connectcoach.model.ChatMessage
import com.pfe.connectcoach.model.SampleData
import com.pfe.connectcoach.ui.components.BottomNavBar
import com.pfe.connectcoach.ui.screens.AssistantScreen
import com.pfe.connectcoach.ui.screens.ClientHomeScreen
import com.pfe.connectcoach.ui.screens.CoachScreen
import com.pfe.connectcoach.ui.theme.AppColors
import com.pfe.connectcoach.ui.theme.ConnectCoachTheme

@Composable
fun App() {
    ConnectCoachTheme {
        var tab by remember { mutableStateOf(AppTab.Home) }
        val chat = remember { mutableStateListOf<ChatMessage>().apply { addAll(SampleData.chat) } }

        Scaffold(
            containerColor = AppColors.Cream,
            contentWindowInsets = WindowInsets(0),
            bottomBar = { BottomNavBar(tab) { tab = it } },
        ) { padding ->
            Box(Modifier.padding(padding).fillMaxSize()) {
                when (tab) {
                    AppTab.Home -> ClientHomeScreen(
                        state = SampleData.home,
                        onViewWorkout = { /* TODO: navigate to workout details */ },
                        onCategoryClick = { if (it == "AI Assistant") tab = AppTab.Assistant },
                    )
                    AppTab.Coach -> CoachScreen(
                        state = SampleData.coach,
                        onMessageCoach = { /* TODO: open chat with coach */ },
                    )
                    AppTab.Assistant -> AssistantScreen(
                        messages = chat,
                        onSend = { chat += ChatMessage(it, fromUser = true) }, // UI only: no AI reply wired
                    )
                    else -> Box(Modifier.fillMaxSize(), Alignment.Center) { Text(tab.label) }
                }
            }
        }
    }
}
