package com.pfe.connectcoach.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Dp
import com.pfe.connectcoach.model.AvatarStyle

/** Simple illustrated avatar drawn with Canvas. Swap for an image loader when you have real photos. */
@Composable
fun CoachAvatar(style: AvatarStyle, size: Dp, modifier: Modifier = Modifier) {
    Canvas(modifier.size(size).clip(CircleShape)) {
        val w = this.size.width
        drawRect(style.bg)
        drawOval(style.shirt, Offset(w * 0.12f, w * 0.68f), Size(w * 0.76f, w * 0.6f))   // shoulders
        drawRect(style.skin, Offset(w * 0.43f, w * 0.5f), Size(w * 0.14f, w * 0.22f))    // neck
        drawCircle(style.skin, w * 0.21f, Offset(w * 0.5f, w * 0.4f))                    // head
        drawArc(style.hair, 180f, 180f, true, Offset(w * 0.275f, w * 0.175f), Size(w * 0.45f, w * 0.45f)) // hair
    }
}