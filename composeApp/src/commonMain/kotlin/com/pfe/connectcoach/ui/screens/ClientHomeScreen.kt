package com.pfe.connectcoach.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChatBubbleOutline
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pfe.connectcoach.model.ClientHomeState
import com.pfe.connectcoach.ui.components.*
import com.pfe.connectcoach.ui.theme.AppColors

private val categories = listOf("Workout", "Nutrition", "Progress", "AI Assistant")

@Composable
fun ClientHomeScreen(
    state: ClientHomeState,
    onViewWorkout: () -> Unit,
    onCategoryClick: (String) -> Unit,
    onNotifications: () -> Unit = {},
    onMessages: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var category by remember { mutableStateOf(categories.first()) }

    Column(
        modifier.fillMaxSize().statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp).padding(top = 12.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(48.dp).clip(CircleShape).background(AppColors.Sage),
                contentAlignment = Alignment.Center,
            ) { Text(state.name.take(1), fontWeight = FontWeight.ExtraBold, fontSize = 19.sp) }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text("Welcome back", fontSize = 12.5.sp, color = AppColors.Muted)
                Text("Good morning, ${state.name}", fontSize = 19.sp, fontWeight = FontWeight.ExtraBold)
            }
            IconBubble(Icons.Rounded.Notifications, "Notifications", badge = true, onClick = onNotifications)
            Spacer(Modifier.width(8.dp))
            IconBubble(Icons.Rounded.ChatBubbleOutline, "Messages", onClick = onMessages)
        }

        // Welcome card
        Box(Modifier.fillMaxWidth().height(180.dp).clip(RoundedCornerShape(30.dp)).background(AppColors.Forest)) {
            Canvas(Modifier.matchParentSize()) {
                drawCircle(AppColors.Olive.copy(alpha = 0.7f), 100.dp.toPx(), Offset(size.width - 10.dp.toPx(), 0f))
                drawCircle(AppColors.Sage.copy(alpha = 0.5f), 70.dp.toPx(), Offset(size.width - 50.dp.toPx(), size.height))
            }
            Text(
                "Your fitness journey starts here",
                Modifier.padding(22.dp).fillMaxWidth(0.65f),
                color = AppColors.Cream, fontSize = 26.sp, lineHeight = 29.sp, fontWeight = FontWeight.ExtraBold,
            )
            CoralButton(
                "View Today's Workout", Modifier.align(Alignment.BottomStart).padding(22.dp),
                height = 44.dp, onClick = onViewWorkout,
            )
        }

        // Categories
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(categories) { c -> PillChip(c, selected = c == category) { category = c; onCategoryClick(c) } }
        }

        // Today's progress
        SectionTitle("Today's Progress", "Details")
        AppCard {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                ProgressRing(state.workoutProgress, AppColors.Forest, "Workout")
                ProgressRing(state.calories / state.caloriesGoal.toFloat(), AppColors.Olive, "Calories")
                ProgressRing(state.protein / state.proteinGoal.toFloat(), AppColors.Coral, "Protein")
            }
        }

        // Workout summary
        AppCard {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier.size(48.dp).clip(RoundedCornerShape(16.dp)).background(AppColors.SageLight),
                    contentAlignment = Alignment.Center,
                ) { androidx.compose.material3.Icon(Icons.Rounded.FitnessCenter, null, tint = AppColors.Forest) }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(state.workoutTitle, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Text(state.workoutMeta, fontSize = 12.5.sp, color = AppColors.Muted)
                }
                Text("${(state.workoutProgress * 100).toInt()}%", fontWeight = FontWeight.ExtraBold, fontSize = 17.sp)
            }
            Spacer(Modifier.height(12.dp))
            LinearBar(state.workoutProgress, AppColors.Forest)
        }

        // Nutrition summary
        AppCard {
            Row {
                Text("Nutrition", fontWeight = FontWeight.Bold, fontSize = 15.sp, modifier = Modifier.weight(1f))
                Text("Daily goal", fontSize = 12.5.sp, color = AppColors.Muted)
            }
            Spacer(Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                MacroColumn("${state.calories} / ${state.caloriesGoal} kcal",
                    state.calories / state.caloriesGoal.toFloat(), AppColors.Olive, Modifier.weight(1f))
                MacroColumn("${state.protein} / ${state.proteinGoal} g protein",
                    state.protein / state.proteinGoal.toFloat(), AppColors.Coral, Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun MacroColumn(label: String, progress: Float, color: androidx.compose.ui.graphics.Color, modifier: Modifier) {
    Column(modifier) {
        Text(label, fontSize = 12.5.sp, color = AppColors.Muted)
        Spacer(Modifier.height(6.dp))
        LinearBar(progress, color)
    }
}
