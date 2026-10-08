package com.pfe.connectcoach.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pfe.connectcoach.model.AppTab
import com.pfe.connectcoach.ui.theme.AppColors

@Composable
fun AppCard(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        shadowElevation = 3.dp,
    ) { Column(Modifier.padding(16.dp), content = content) }
}

@Composable
fun IconBubble(icon: ImageVector, description: String, badge: Boolean = false, onClick: () -> Unit = {}) {
    Box(
        Modifier.size(44.dp).clip(CircleShape).background(AppColors.Beige).clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(icon, description, tint = AppColors.Forest, modifier = Modifier.size(22.dp))
        if (badge) Box(
            Modifier.align(Alignment.TopEnd).padding(10.dp).size(8.dp).clip(CircleShape).background(AppColors.Coral)
        )
    }
}

@Composable
fun CoralButton(
    text: String,
    modifier: Modifier = Modifier,
    height: Dp = 48.dp,
    icon: ImageVector? = null,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(height),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = AppColors.Coral, contentColor = Color.White),
        contentPadding = PaddingValues(horizontal = 20.dp),
    ) {
        if (icon != null) {
            Icon(icon, null, Modifier.size(20.dp))
            Spacer(Modifier.width(8.dp))
        }
        Text(text, fontWeight = FontWeight.Bold, fontSize = 15.sp)
    }
}

@Composable
fun PillChip(text: String, selected: Boolean = false, onClick: () -> Unit = {}) {
    Box(
        Modifier.clip(CircleShape)
            .background(if (selected) AppColors.Forest else AppColors.Beige)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(
            text, fontSize = 13.sp, fontWeight = FontWeight.SemiBold,
            color = if (selected) AppColors.Cream else AppColors.Forest,
        )
    }
}

@Composable
fun SectionTitle(title: String, action: String? = null) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(title, fontSize = 17.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        if (action != null) Text(action, fontSize = 12.5.sp, fontWeight = FontWeight.SemiBold, color = AppColors.Muted)
    }
}

@Composable
fun LinearBar(progress: Float, color: Color, modifier: Modifier = Modifier) {
    Box(modifier.fillMaxWidth().height(8.dp).clip(CircleShape).background(AppColors.Track)) {
        Box(Modifier.fillMaxWidth(progress.coerceIn(0f, 1f)).fillMaxHeight().clip(CircleShape).background(color))
    }
}

@Composable
fun ProgressRing(progress: Float, color: Color, caption: String, size: Dp = 76.dp) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(Modifier.size(size), contentAlignment = Alignment.Center) {
            Canvas(Modifier.matchParentSize()) {
                val stroke = 8.dp.toPx()
                val arcSize = Size(this.size.width - stroke, this.size.height - stroke)
                val topLeft = Offset(stroke / 2, stroke / 2)
                drawArc(AppColors.Track, -90f, 360f, false, topLeft, arcSize, style = Stroke(stroke))
                drawArc(color, -90f, 360f * progress.coerceIn(0f, 1f), false, topLeft, arcSize,
                    style = Stroke(stroke, cap = StrokeCap.Round))
            }
            Text("${(progress * 100).toInt()}%", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
        }
        Spacer(Modifier.height(6.dp))
        Text(caption, fontSize = 12.5.sp, fontWeight = FontWeight.SemiBold, color = AppColors.Muted)
    }
}

@Composable
fun BottomNavBar(selected: AppTab, onSelect: (AppTab) -> Unit) {
    Surface(
        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        color = Color.White,
        shadowElevation = 12.dp,
    ) {
        Row(
            Modifier.fillMaxWidth().navigationBarsPadding().padding(horizontal = 8.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            AppTab.entries.forEach { tab ->
                val on = tab == selected
                val tint = if (on) AppColors.Forest else AppColors.Muted
                Column(
                    Modifier.clip(RoundedCornerShape(16.dp)).clickable { onSelect(tab) }.padding(horizontal = 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Box(
                        Modifier.size(48.dp, 30.dp).clip(CircleShape)
                            .background(if (on) AppColors.SageLight else Color.Transparent),
                        contentAlignment = Alignment.Center,
                    ) { Icon(tab.icon, tab.label, tint = tint, modifier = Modifier.size(22.dp)) }
                    Text(tab.label, fontSize = 10.5.sp, fontWeight = FontWeight.SemiBold, color = tint)
                }
            }
        }
    }
}
