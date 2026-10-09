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
    val syntax: String,
    val flags: List<FlagOption>,
    val example: String,
    val explanation: String,
    val requiresRoot: Boolean = false,
    val simulatedOutput: String,
    val connectedTools: List<String> = emptyList()
)

enum class AiOrigin(val label: String, val badge: String) {
    INDIA("India AI Innovation", "🇮🇳 INDIA"),
    GLOBAL("Global Leading AI", "🌐 GLOBAL")
}

data class PricingTier(
    val name: String,
    val price: String, // e.g. "₹999 / mo" or "$20 / mo"
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
    val howItWorks: String,
    val installCommand: String,
    val licenseType: String, // e.g. "Open Source (GPL v2)", "Freemium / Commercial Pro"
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
    val relationship: String, // e.g. "Runtimes / Hosted On", "Automated By", "API Integration", "Alternative To", "Security Analysis"
    val description: String
)
