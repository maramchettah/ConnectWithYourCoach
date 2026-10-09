package com.pfe.connectcoach.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pfe.connectcoach.model.CoachState
import com.pfe.connectcoach.ui.components.*
import com.pfe.connectcoach.ui.theme.AppColors

/** UI only: "Message Coach" just calls [onMessageCoach]; wire your chat feature there. */
@Composable
fun CoachScreen(state: CoachState, onMessageCoach: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxSize().statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp).padding(top = 12.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("My Coach", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.weight(1f))
            IconBubble(Icons.Rounded.Notifications, "Notifications", badge = true)
        }

        // Coach hero card
        Box(Modifier.fillMaxWidth().clip(RoundedCornerShape(32.dp)).background(AppColors.SageLight)) {
            Canvas(Modifier.matchParentSize()) {
                drawCircle(AppColors.Sage.copy(alpha = 0.8f), 100.dp.toPx(), Offset(0f, 0f))
                drawCircle(AppColors.Sand, 85.dp.toPx(), Offset(size.width, size.height))
            }
            Column(Modifier.fillMaxWidth().padding(22.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Box(Modifier.border(5.dp, AppColors.Cream, CircleShape).padding(5.dp)) {
                    CoachAvatar(state.avatar, 86.dp)
                }
                Spacer(Modifier.height(12.dp))
                Text(state.name, fontSize = 23.sp, fontWeight = FontWeight.ExtraBold)
                Text(state.specialization, fontSize = 14.sp, color = AppColors.Muted)
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Pill(state.badge, Icons.Rounded.Verified, AppColors.Forest, AppColors.Cream, AppColors.Sage)
                    Pill(state.rating.toString(), Icons.Rounded.Star, Color.White, AppColors.Forest, AppColors.Coral)
                }
                Spacer(Modifier.height(14.dp))
                Text(state.bio, fontSize = 13.5.sp, lineHeight = 20.sp, textAlign = TextAlign.Center)
                Spacer(Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(9.dp).clip(CircleShape).background(AppColors.Online))
                    Spacer(Modifier.width(8.dp))
                    Text(state.availability, fontSize = 12.5.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }

        CoralButton(
            "Message Coach", Modifier.fillMaxWidth(), height = 58.dp,
            icon = Icons.Rounded.ChatBubbleOutline, onClick = onMessageCoach,
        )

        SectionTitle("Your Current Plan")
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            PlanCard("Workout", state.workoutPlan, Icons.Rounded.FitnessCenter, AppColors.SageLight, Modifier.weight(1f))
            PlanCard("Nutrition", state.nutritionPlan, Icons.Rounded.Restaurant, AppColors.Sand, Modifier.weight(1f))
        }

        SectionTitle("Recent Progress", "Last 4 weeks")
        AppCard {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                Stat(state.weightChange, "Weight")
                Stat(state.workoutsDone.toString(), "Workouts")
                Stat("${state.streakDays} days", "Streak")
            }
            Spacer(Modifier.height(10.dp))
            Canvas(Modifier.fillMaxWidth().height(44.dp)) {
                val path = Path()
                state.trend.forEachIndexed { i, v ->
                    val x = size.width * i / (state.trend.size - 1)
                    val y = size.height * (1f - v)
                    if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
                }
                drawPath(path, AppColors.Olive, style = Stroke(3.5.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round))
            }
        }
    }
}

@Composable
private fun Pill(text: String, icon: ImageVector, bg: Color, fg: Color, iconTint: Color) {
    Row(
        Modifier.clip(CircleShape).background(bg).padding(horizontal = 12.dp, vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(icon, null, tint = iconTint, modifier = Modifier.size(14.dp))
        Spacer(Modifier.width(6.dp))
        Text(text, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = fg)
    }
}

@Composable
private fun PlanCard(title: String, detail: String, icon: ImageVector, tint: Color, modifier: Modifier) {
    AppCard(modifier) {
        Box(Modifier.size(40.dp).clip(RoundedCornerShape(14.dp)).background(tint), contentAlignment = Alignment.Center) {
            Icon(icon, null, tint = AppColors.Forest, modifier = Modifier.size(22.dp))
        }
        Spacer(Modifier.height(10.dp))
        Text(title, fontWeight = FontWeight.Bold, fontSize = 14.5.sp)
        Text(detail, fontSize = 12.5.sp, color = AppColors.Muted, lineHeight = 18.sp)
    }
}

@Composable
private fun Stat(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
        Text(label, fontSize = 12.5.sp, color = AppColors.Muted)
    }
}