package com.pfe.connectcoach.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.People
import androidx.compose.material.icons.rounded.Person
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

data class CoachState(
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
)

data class ChatMessage(val text: String, val fromUser: Boolean, val tag: String? = null)

/** Sample data so the UI renders without any backend. Replace with your ViewModels. */
object SampleData {
    val home = ClientHomeState(
        name = "Sara",
        workoutTitle = "Upper Body Strength",
        workoutMeta = "45 min · 6 exercises · set by Coach Yasmine",
        workoutProgress = 0.60f,
        calories = 1240, caloriesGoal = 1900,
        protein = 68, proteinGoal = 110,
    )
    val coach = CoachState(
        name = "Coach Yasmine B.",
        specialization = "Strength & Nutrition Coach",
        badge = "Certified Pro Coach",
        rating = 4.9,
        bio = "Helping you build lasting strength with practical training and simple, balanced nutrition.",
        availability = "Available now · replies within 1 hour",
        workoutPlan = "4 days / week\nUpper · Lower split",
        nutritionPlan = "1,900 kcal / day\n110 g protein",
        weightChange = "−2.4 kg", workoutsDone = 12, streakDays = 5,
        trend = listOf(0.2f, 0.28f, 0.25f, 0.45f, 0.5f, 0.7f, 0.85f),
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
