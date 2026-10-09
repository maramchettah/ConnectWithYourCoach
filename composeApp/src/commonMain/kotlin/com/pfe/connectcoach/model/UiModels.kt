package com.pfe.connectcoach.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.People
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

enum class AppTab(val label: String, val icon: ImageVector) {
    Home("Home", Icons.Rounded.Home),
    Coach("My Coach", Icons.Rounded.People),
    Assistant("AI Assistant", Icons.Rounded.AutoAwesome),
    Progress("Progress", Icons.Rounded.BarChart),
    Profile("Profile", Icons.Rounded.Person),
}

data class ClientHomeState(
    val name: String,
    val workoutTitle: String,
    val workoutMeta: String,
    val workoutProgress: Float,
    val calories: Int,
    val caloriesGoal: Int,
    val protein: Int,
    val proteinGoal: Int,
)

/** Colors used to draw a coach's illustrated avatar (replace with a photo URL later). */
data class AvatarStyle(val bg: Color, val skin: Color, val hair: Color, val shirt: Color)

data class CoachState(
    val id: Int,
    val name: String,
    val specialization: String,
    val badge: String,
    val rating: Double,
    val bio: String,
    val availability: String,
    val workoutPlan: String,
    val nutritionPlan: String,
    val weightChange: String,
    val workoutsDone: Int,
    val streakDays: Int,
    val trend: List<Float>,
    val avatar: AvatarStyle,
) {
    val firstName: String get() = name.removePrefix("Coach ").substringBefore(' ')
}

data class ChatMessage(val text: String, val fromUser: Boolean, val tag: String? = null)

/** Sample data so the UI renders without any backend. Replace with your ViewModels. */
object SampleData {
    val home = ClientHomeState(
        name = "Sara",
        workoutTitle = "Upper Body Strength",
        workoutMeta = "45 min · 6 exercises",
        workoutProgress = 0.60f,
        calories = 1240, caloriesGoal = 1900,
        protein = 68, proteinGoal = 110,
    )

    private val trend = listOf(0.2f, 0.28f, 0.25f, 0.45f, 0.5f, 0.7f, 0.85f)

    private fun coach(
        id: Int, name: String, spec: String, bio: String, availability: String,
        workout: String, nutrition: String, weight: String, workouts: Int, streak: Int,
        rating: Double, avatar: AvatarStyle,
    ) = CoachState(
        id, name, spec, "Certified Pro Coach", rating, bio, availability,
        workout, nutrition, weight, workouts, streak, trend, avatar,
    )

    val coaches = listOf(
        coach(1, "Coach Yasmine B.", "Strength & Nutrition Coach",
            "Helping you build lasting strength with practical training and simple, balanced nutrition.",
            "Available now · replies within 1 hour",
            "4 days / week\nUpper · Lower split", "1,900 kcal / day\n110 g protein",
            "−2.4 kg", 12, 5, 4.9,
            AvatarStyle(Color(0xFFF4B942), Color(0xFFE8B48A), Color(0xFF3A2418), Color(0xFF2A5446))),
        coach(2, "Coach Karim L.", "Cardio & Endurance Coach",
            "Running plans and conditioning that fit a busy week, with steady, measurable progress.",
            "Available today · 9:00–17:00",
            "5 days / week\nRun · Intervals", "2,200 kcal / day\n120 g protein",
            "−1.8 kg", 15, 7, 4.8,
            AvatarStyle(Color(0xFF8FC95B), Color(0xFF9A6A47), Color(0xFF1E1410), Color(0xFFF2611D))),
        coach(3, "Coach Lina M.", "Yoga & Mobility Coach",
            "Gentle, effective routines for flexibility, posture and recovery.",
            "Available now · replies within 2 hours",
            "3 days / week\nFlow · Mobility", "1,800 kcal / day\n95 g protein",
            "−1.2 kg", 10, 4, 4.9,
            AvatarStyle(Color(0xFFF29A6B), Color(0xFFF3CFAE), Color(0xFF7A3E1D), Color(0xFF7BA328))),
        coach(4, "Coach Omar S.", "Weight Loss Coach",
            "Sustainable fat loss through smart training and meal habits you can keep.",
            "Away · back tomorrow 9:00",
            "4 days / week\nFull body + walks", "1,700 kcal / day\n130 g protein",
            "−3.1 kg", 14, 6, 4.7,
            AvatarStyle(Color(0xFF5FB3A1), Color(0xFFC58C62), Color(0xFF2B1B12), Color(0xFFF4B942))),
        coach(5, "Coach Nadia H.", "Pilates & Recovery Coach",
            "Core strength, balance and injury-safe progressions for every level.",
            "Available today · 10:00–18:00",
            "3 days / week\nPilates · Core", "1,850 kcal / day\n100 g protein",
            "−1.5 kg", 9, 3, 4.8,
            AvatarStyle(Color(0xFFE86F8A), Color(0xFFD9A07A), Color(0xFF8E3B5A), Color(0xFF2A5446))),
    )

    val chat = listOf(
        ChatMessage("Hi Sara! I can help with workouts, meals and recovery. What would you like to know?", false),
        ChatMessage("What should I eat before my workout?", true),
        ChatMessage(
            "Great question! 60–90 minutes before training, aim for:\n" +
                    "• Slow carbs — oats, a banana or wholegrain toast\n" +
                    "• Lean protein — Greek yogurt or eggs\n" +
                    "• Water — 300–500 ml\n\n" +
                    "Training in under 30 minutes? Keep it light, like a banana.",
            false, tag = "Tailored to today's Upper Body session",
        ),
    )
}