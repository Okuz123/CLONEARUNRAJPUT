package com.example.data.model

data class FlagOption(
    val flag: String,
    val description: String
)

data class LinuxCommand(
    val id: String,
    val name: String,
    val category: String,
    val summary: String,
    val summaryHindi: String, // हिंदी में विवरण
    val syntax: String,
    val flags: List<FlagOption>,
    val example: String,
    val explanation: String,
    val explanationHindi: String, // हिंदी में उपयोग
    val requiresRoot: Boolean = false,
    val simulatedOutput: String,
    val connectedTools: List<String> = emptyList()
)

enum class AiOrigin(val label: String, val badge: String) {
    INDIA("Made in India", "🇮🇳 भारत निर्मित"),
    GLOBAL("Global AI", "🌐 ग्लोबल AI")
}

data class PricingTier(
    val name: String,
    val price: String, // ₹ in INR or $ in USD
    val description: String,
    val isPaid: Boolean
)

data class AiTool(
    val id: String,
    val name: String,
    val origin: AiOrigin,
    val category: String,
    val tagline: String,
    val description: String,
    val whatItsFor: String,
    val whatItsForHindi: String, // हिंदी में समझें (क्यों और कैसे इस्तेमाल करें)
    val pricingTiers: List<PricingTier>,
    val keyFeatures: List<String>,
    val apiAvailable: Boolean,
    val connectedSoftware: List<String>,
    val officialSite: String
)

data class SoftwareItem(
    val id: String,
    val name: String,
    val category: String,
    val summary: String,
    val whatItsFor: String,
    val whatItsForHindi: String,
    val howItWorks: String,
    val installCommand: String,
    val licenseType: String,
    val isCommercialPaid: Boolean,
    val keyCommands: List<String>,
    val connectedTools: List<String>,
    val docsUrl: String
)

enum class NodeType {
    LINUX_COMMAND,
    SOFTWARE,
    AI_TOOL
}

data class TechConnection(
    val sourceId: String,
    val sourceName: String,
    val sourceType: NodeType,
    val targetId: String,
    val targetName: String,
    val targetType: NodeType,
    val relationship: String,
    val description: String,
    val descriptionHindi: String = ""
)

// User & Admin System Models
enum class UserRole {
    GUEST,
    STUDENT,
    ADMIN
}

data class UserProfile(
    val id: String,
    val name: String,
    val email: String,
    val role: UserRole,
    val avatarInitials: String = "BT"
)

data class UserReview(
    val id: String,
    val userName: String,
    val userCity: String, // e.g. Bengaluru, New Delhi, Mumbai, Patna, Jaipur
    val rating: Int, // 1 to 5 stars
    val date: String,
    val comment: String
)

data class CityMetric(
    val cityName: String,
    val activeUsers: Int,
    val percentage: Float
)

data class AppAnalytics(
    val totalDownloads: Int = 18450,
    val todayDownloads: Int = 412,
    val activeUsersNow: Int = 2390,
    val averageRating: Float = 4.8f,
    val totalReviews: Int = 1240,
    val topCities: List<CityMetric> = listOf(
        CityMetric("Bengaluru", 4820, 0.26f),
        CityMetric("Delhi NCR", 3910, 0.21f),
        CityMetric("Mumbai & Pune", 3450, 0.19f),
        CityMetric("Hyderabad", 2640, 0.14f),
        CityMetric("Chennai", 1850, 0.10f),
        CityMetric("Kolkata & Other Cities", 1780, 0.10f)
    )
)
