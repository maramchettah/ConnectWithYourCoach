package com.pfe.connectcoach.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pfe.connectcoach.model.ChatMessage
import com.pfe.connectcoach.ui.components.PillChip
import com.pfe.connectcoach.ui.theme.AppColors

private val suggestions = listOf("Workout ideas", "Nutrition advice", "Recovery", "My progress")

/**
 * Chat UI only. No AI call is made here: [onSend] receives the text and the caller decides
 * what to do with it (add a message, call a service, ...).
 */
@Composable
fun AssistantScreen(
    messages: List<ChatMessage>,
    onSend: (String) -> Unit,
    onMic: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var input by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    LaunchedEffect(messages.size) { if (messages.isNotEmpty()) listState.animateScrollToItem(messages.lastIndex) }

    fun submit(text: String) {
        val t = text.trim()
        if (t.isNotEmpty()) { onSend(t); input = "" }
    }

    Column(modifier.fillMaxSize().statusBarsPadding().imePadding().padding(top = 12.dp)) {
        // Header
        Row(Modifier.padding(horizontal = 20.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(56.dp).clip(RoundedCornerShape(20.dp)).background(AppColors.Forest),
                contentAlignment = Alignment.Center,
            ) { Icon(Icons.Rounded.AutoAwesome, null, tint = Color(0xFFE8704A), modifier = Modifier.size(28.dp)) }
            Spacer(Modifier.width(14.dp))
            Column {
                Text("AI Assistant", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
                Text("Your personal fitness & nutrition guide", fontSize = 12.5.sp, color = AppColors.Muted)
            }
        }
        Row(Modifier.padding(horizontal = 20.dp, vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(8.dp).clip(CircleShape).background(AppColors.Online))
            Spacer(Modifier.width(6.dp))
            Text("Online · knows your plan and goals", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = AppColors.Muted)
        }

        // Conversation
        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f).fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) { items(messages) { MessageBubble(it) } }

        // Suggestions
        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) { items(suggestions) { s -> PillChip(s) { submit(s) } } }

        // Input bar
        Surface(
            Modifier.padding(horizontal = 20.dp, vertical = 8.dp).fillMaxWidth().height(60.dp),
            shape = CircleShape, color = Color.White, shadowElevation = 4.dp,
        ) {
            Row(Modifier.padding(start = 12.dp, end = 7.dp), verticalAlignment = Alignment.CenterVertically) {
                TextField(
                    value = input, onValueChange = { input = it },
                    placeholder = { Text("Ask your assistant…", color = AppColors.Muted) },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent,
                    ),
                )
                IconButton(onClick = onMic) { Icon(Icons.Rounded.Mic, "Voice input", tint = AppColors.Forest) }
                Box(
                    Modifier.size(46.dp).clip(CircleShape).background(AppColors.Coral),
                    contentAlignment = Alignment.Center,
                ) {
                    IconButton(onClick = { submit(input) }) {
                        Icon(Icons.AutoMirrored.Rounded.Send, "Send", tint = Color.White, modifier = Modifier.size(20.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun MessageBubble(m: ChatMessage) {
    val shape = if (m.fromUser) RoundedCornerShape(22.dp, 22.dp, 6.dp, 22.dp) else RoundedCornerShape(22.dp, 22.dp, 22.dp, 6.dp)
    val bg = when {
        m.fromUser -> AppColors.Forest
        m.tag != null -> Color.White
        else -> AppColors.Beige
    }
    Box(Modifier.fillMaxWidth(), contentAlignment = if (m.fromUser) Alignment.CenterEnd else Alignment.CenterStart) {
        Surface(
            Modifier.widthIn(max = 320.dp), shape = shape, color = bg,
            shadowElevation = if (m.tag != null) 3.dp else 0.dp,
        ) {
            Column(Modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
                Text(
                    m.text, fontSize = 14.sp, lineHeight = 20.sp,
                    color = if (m.fromUser) AppColors.Cream else AppColors.Forest,
                    fontWeight = if (m.fromUser) FontWeight.SemiBold else FontWeight.Normal,
                )
                if (m.tag != null) {
                    Spacer(Modifier.height(10.dp))
                    Box(Modifier.clip(CircleShape).background(AppColors.SageLight).padding(horizontal = 12.dp, vertical = 6.dp)) {
                        Text(m.tag, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
