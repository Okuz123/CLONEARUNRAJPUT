package com.example.data.repository

import com.example.data.model.AppAnalytics
import com.example.data.model.UserProfile
import com.example.data.model.UserReview
import com.example.data.model.UserRole
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object AnalyticsRepository {

    private val initialReviews = listOf(
        UserReview(
            id = "rev_1",
            userName = "Rohit Sharma",
            userCity = "Bengaluru, Karnataka",
            rating = 5,
            date = "Oct 9, 2026",
            comment = "Sarvam AI और Krutrim की जानकारी हिंदी और अंग्रेजी दोनों में इतनी साफ है कि कॉलेज प्रोजेक्ट में बहुत मदद मिली! 5 Star app!"
        ),
        UserReview(
            id = "rev_2",
            userName = "Priya Patel",
            userCity = "Ahmedabad, Gujarat",
            rating = 5,
            date = "Oct 8, 2026",
            comment = "Linux के सारे कोड्स और डॉकर का कनेक्शन समझना बहुत आसान हो गया। भारतीय स्टार्टअप्स के लिए गर्व की बात है।"
        ),
        UserReview(
            id = "rev_3",
            userName = "Aman Verma",
            userCity = "New Delhi",
            rating = 5,
            date = "Oct 7, 2026",
            comment = "Finally an Indian tech app that isn't confusing. All AI tool pricing in INR (₹) is super helpful."
        ),
        UserReview(
            id = "rev_4",
            userName = "Sneha Mukherjee",
            userCity = "Kolkata, WB",
            rating = 4,
            date = "Oct 6, 2026",
            comment = "Great cheatsheets and clean design. Love the Hindi explanations for complex tools."
        ),
        UserReview(
            id = "rev_5",
            userName = "Karthik Raja",
            userCity = "Chennai, TN",
            rating = 5,
            date = "Oct 5, 2026",
            comment = "Admin panel feature is cool. Tracking live downloads and active user telemetry from India."
        )
    )

    private val _reviews = MutableStateFlow(initialReviews)
    val reviews: StateFlow<List<UserReview>> = _reviews.asStateFlow()

    private val _analytics = MutableStateFlow(AppAnalytics())
    val analytics: StateFlow<AppAnalytics> = _analytics.asStateFlow()

    private val _currentUser = MutableStateFlow(
        UserProfile(
            id = "usr_guest",
            name = "भारतीय डेवलपर (Guest)",
            email = "developer@bharat.in",
            role = UserRole.STUDENT,
            avatarInitials = "BD"
        )
    )
    val currentUser: StateFlow<UserProfile> = _currentUser.asStateFlow()

    private val _adminBroadcastMessage = MutableStateFlow("🇮🇳 स्वागत है! भारत के सर्वश्रेष्ठ AI टूल्स व लिनक्स डायरेक्टरी में आपका स्वागत है।")
    val adminBroadcastMessage: StateFlow<String> = _adminBroadcastMessage.asStateFlow()

    fun loginAsUser(name: String, email: String) {
        val initials = name.trim().take(2).uppercase().ifEmpty { "IN" }
        _currentUser.value = UserProfile(
            id = "usr_${System.currentTimeMillis()}",
            name = name,
            email = email,
            role = UserRole.STUDENT,
            avatarInitials = initials
        )
    }

    fun loginAsAdmin(passcode: String): Boolean {
        if (passcode == "admin123" || passcode == "bharat2026" || passcode == "admin") {
            _currentUser.value = UserProfile(
                id = "admin_master",
                name = "चीफ एडमिन (Chief Admin)",
                email = "admin@bharattech.in",
                role = UserRole.ADMIN,
                avatarInitials = "AD"
            )
            return true
        }
        return false
    }

    fun logout() {
        _currentUser.value = UserProfile(
            id = "usr_guest",
            name = "भारतीय डेवलपर (Guest)",
            email = "developer@bharat.in",
            role = UserRole.STUDENT,
            avatarInitials = "BD"
        )
    }

    fun submitReview(name: String, city: String, rating: Int, comment: String) {
        val newReview = UserReview(
            id = "rev_${System.currentTimeMillis()}",
            userName = name.ifBlank { "सत्यापित यूजर (Verified User)" },
            userCity = city.ifBlank { "India" },
            rating = rating.coerceIn(1, 5),
            date = "Just now",
            comment = comment
        )
        val updated = listOf(newReview) + _reviews.value
        _reviews.value = updated

        // Recalculate analytics
        val avg = updated.map { it.rating }.average().toFloat()
        val curAnalytics = _analytics.value
        _analytics.value = curAnalytics.copy(
            averageRating = (Math.round(avg * 10) / 10.0).toFloat(),
            totalReviews = updated.size,
            totalDownloads = curAnalytics.totalDownloads + 1
        )
    }

    fun updateBroadcast(message: String) {
        _adminBroadcastMessage.value = message
    }
}
