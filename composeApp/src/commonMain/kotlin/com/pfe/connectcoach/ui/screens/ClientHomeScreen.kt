package com.pfe.connectcoach.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.ChatBubbleOutline
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pfe.connectcoach.model.ClientHomeState
import com.pfe.connectcoach.model.CoachState
import com.pfe.connectcoach.ui.components.*
import com.pfe.connectcoach.ui.theme.AppColors

private val categories = listOf("Workout", "Nutrition", "Progress", "AI Assistant")

@Composable
fun ClientHomeScreen(
    state: ClientHomeState,
    coaches: List<CoachState>,
    selectedCoachId: Int,
    onSelectCoach: (CoachState) -> Unit,
    onOpenCoach: () -> Unit,
    onViewWorkout: () -> Unit,
    onCategoryClick: (String) -> Unit,
    onNotifications: () -> Unit = {},
    onMessages: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var category by remember { mutableStateOf(categories.first()) }
    val selected = coaches.firstOrNull { it.id == selectedCoachId } ?: coaches.first()

    Column(
        modifier.fillMaxSize().statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp).padding(top = 12.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text("Good morning, ${state.name}!", fontSize = 21.sp, fontWeight = FontWeight.ExtraBold)
                Text("Pick your coach and keep moving", fontSize = 12.5.sp, color = AppColors.Muted)
            }
            IconBubble(Icons.Rounded.Notifications, "Notifications", badge = true, onClick = onNotifications)
            Spacer(Modifier.width(8.dp))
            IconBubble(Icons.Rounded.ChatBubbleOutline, "Messages", onClick = onMessages)
        }

        // Welcome card
        Box(Modifier.fillMaxWidth().height(180.dp).clip(RoundedCornerShape(30.dp)).background(AppColors.Forest)) {
            Canvas(Modifier.matchParentSize()) {
                drawCircle(AppColors.Olive.copy(alpha = 0.85f), 100.dp.toPx(), Offset(size.width - 10.dp.toPx(), 0f))
                drawCircle(AppColors.Sage.copy(alpha = 0.7f), 70.dp.toPx(), Offset(size.width - 50.dp.toPx(), size.height))
                drawCircle(AppColors.Coral.copy(alpha = 0.9f), 22.dp.toPx(), Offset(size.width - 40.dp.toPx(), 60.dp.toPx()))
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

        // ---- Coaches (choose one) ----
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Your Coaches", fontSize = 17.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text("Find a coach", fontSize = 12.5.sp, fontWeight = FontWeight.Bold, color = AppColors.Coral)
        }
        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            items(coaches, key = { it.id }) { c ->
                val on = c.id == selectedCoachId
                Column(
                    Modifier.width(78.dp).clip(RoundedCornerShape(18.dp)).clickable { onSelectCoach(c) },
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Box(
                        Modifier.size(74.dp)
                            .border(3.dp, if (on) AppColors.Coral else Color.Transparent, CircleShape)
                            .padding(7.dp),
                    ) {
                        CoachAvatar(c.avatar, 60.dp)
                        if (on) Box(
                            Modifier.align(Alignment.BottomEnd).size(22.dp).clip(CircleShape)
                                .background(AppColors.Coral),
                            contentAlignment = Alignment.Center,
                        ) { Icon(Icons.Rounded.Check, null, tint = Color.White, modifier = Modifier.size(14.dp)) }
                    }
                    Spacer(Modifier.height(4.dp))
                    Text(
                        c.firstName, fontSize = 12.5.sp, maxLines = 1, overflow = TextOverflow.Ellipsis,
                        fontWeight = if (on) FontWeight.ExtraBold else FontWeight.SemiBold,
                    )
                }
            }
        }
        AppCard(Modifier.fillMaxWidth()) {
            Row(
                Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).clickable(onClick = onOpenCoach),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CoachAvatar(selected.avatar, 46.dp)
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text("Chosen coach", fontSize = 11.sp, color = AppColors.Muted)
                    Text(selected.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Text(selected.specialization, fontSize = 12.5.sp, color = AppColors.Muted)
                }
                Text("View profile", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = AppColors.Coral)
                Icon(Icons.AutoMirrored.Rounded.ArrowForward, null, tint = AppColors.Coral, modifier = Modifier.size(18.dp))
            }
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
                ) { Icon(Icons.Rounded.FitnessCenter, null, tint = AppColors.Forest) }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(state.workoutTitle, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Text("${state.workoutMeta} · set by ${selected.firstName}", fontSize = 12.5.sp, color = AppColors.Muted)
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
private fun MacroColumn(label: String, progress: Float, color: Color, modifier: Modifier) {
    Column(modifier) {
        Text(label, fontSize = 12.5.sp, color = AppColors.Muted)
        Spacer(Modifier.height(6.dp))
        LinearBar(progress, color)
    }
}