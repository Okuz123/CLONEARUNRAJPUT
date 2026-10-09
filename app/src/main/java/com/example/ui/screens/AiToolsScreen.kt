package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.CyberSoundSynthesizer
import com.example.data.model.AiOrigin
import com.example.data.model.AiTool
import com.example.data.repository.AiToolRepository
import com.example.ui.theme.AmberNeon
import com.example.ui.theme.CyberBlack
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberDark
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.CyberSurfaceVariant
import com.example.ui.theme.LocalCyberThemeMode
import com.example.ui.theme.TextCyberBright
import com.example.ui.theme.TextCyberDim

@Composable
fun AiToolsScreen(
    onRunInTerminal: (String) -> Unit,
    onNavigateToConnected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val themeMode = LocalCyberThemeMode.current
    val primaryColor = themeMode.primaryColor
    val context = LocalContext.current

    var selectedTabIndex by remember { mutableIntStateOf(0) } // 0: All, 1: India AI, 2: Global Paid
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    var expandedToolId by remember { mutableStateOf<String?>(null) }

    val tabTitles = listOf("ALL AI DECK", "🇮🇳 INDIA AI INNOVATIONS", "🌐 GLOBAL PAID AI")

    val filteredTools = remember(selectedTabIndex, searchQuery, selectedCategory) {
        AiToolRepository.tools.filter { tool ->
            val matchesTab = when (selectedTabIndex) {
                1 -> tool.origin == AiOrigin.INDIA
                2 -> tool.origin == AiOrigin.GLOBAL
                else -> true
            }
            val matchesCategory = (selectedCategory == "All" || tool.category == selectedCategory)
            val matchesSearch = searchQuery.isBlank() ||
                tool.name.contains(searchQuery, ignoreCase = true) ||
                tool.tagline.contains(searchQuery, ignoreCase = true) ||
                tool.description.contains(searchQuery, ignoreCase = true) ||
                tool.whatItsFor.contains(searchQuery, ignoreCase = true) ||
                tool.pricingTiers.any { it.price.contains(searchQuery, ignoreCase = true) }
            matchesTab && matchesCategory && matchesSearch
        }
    }

    fun copyToClipboard(text: String, label: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard?.setPrimaryClip(clip)
        CyberSoundSynthesizer.playSuccess()
        Toast.makeText(context, "Copied $label info", Toast.LENGTH_SHORT).show()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CyberBlack)
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        // Tab Row
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = CyberDark,
            contentColor = primaryColor,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = primaryColor,
                    height = 2.dp
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, CyberBorder, RoundedCornerShape(4.dp))
                .clip(RoundedCornerShape(4.dp))
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        CyberSoundSynthesizer.playKeyClick()
                        selectedTabIndex = index
                    },
                    text = {
                        Text(
                            text = title,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedTabIndex == index) primaryColor else TextCyberDim
                        )
                    },
                    modifier = Modifier.testTag("ai_tab_$index")
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Search Input
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                CyberSoundSynthesizer.playKeyClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp)
                .testTag("ai_search_field"),
            placeholder = {
                Text(
                    text = "Search AI tools (e.g. Sarvam, Krutrim, ₹, ChatGPT)...",
                    color = TextCyberDim,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = primaryColor
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear",
                            tint = TextCyberDim
                        )
                    }
                }
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = primaryColor,
                unfocusedBorderColor = CyberBorder,
                focusedTextColor = TextCyberBright,
                unfocusedTextColor = TextCyberBright,
                focusedContainerColor = CyberSurface,
                unfocusedContainerColor = CyberSurface
            ),
            textStyle = androidx.compose.ui.text.TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 13.sp
            )
        )

        // Subcategory Filter Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(bottom = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            AiToolRepository.categories.forEach { category ->
                val isSelected = selectedCategory == category
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        CyberSoundSynthesizer.playKeyClick()
                        selectedCategory = category
                    },
                    label = {
                        Text(
                            text = category,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 10.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = primaryColor.copy(alpha = 0.2f),
                        selectedLabelColor = primaryColor,
                        containerColor = CyberDark,
                        labelColor = TextCyberDim
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        borderColor = if (isSelected) primaryColor else CyberBorder,
                        borderWidth = 1.dp,
                        enabled = true,
                        selected = isSelected
                    ),
                    modifier = Modifier.testTag("ai_category_$category")
                )
            }
        }

        // Statistics row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "INDEXED: ${filteredTools.size} AI PLATFORMS",
                color = primaryColor,
                fontFamily = FontFamily.Monospace,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "PAID & SOVEREIGN TIERS INCLUDED",
                color = AmberNeon,
                fontFamily = FontFamily.Monospace,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium
            )
        }

        // Tools List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredTools, key = { it.id }) { tool ->
                val isExpanded = expandedToolId == tool.id

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = if (isExpanded) primaryColor else CyberBorder,
                            shape = RoundedCornerShape(6.dp)
                        )
                        .testTag("ai_tool_card_${tool.id}"),
                    colors = CardDefaults.cardColors(containerColor = CyberDark)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        // Title row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    CyberSoundSynthesizer.playKeyClick()
                                    expandedToolId = if (isExpanded) null else tool.id
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = tool.name,
                                        color = TextCyberBright,
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(3.dp))
                                            .background(
                                                if (tool.origin == AiOrigin.INDIA)
                                                    AmberNeon.copy(alpha = 0.2f)
                                                else
                                                    primaryColor.copy(alpha = 0.15f)
                                            )
                                            .border(
                                                1.dp,
                                                if (tool.origin == AiOrigin.INDIA) AmberNeon else primaryColor,
                                                RoundedCornerShape(3.dp)
                                            )
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = tool.origin.badge,
                                            color = if (tool.origin == AiOrigin.INDIA) AmberNeon else primaryColor,
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }

                                Text(
                                    text = tool.tagline,
                                    color = primaryColor,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 11.sp,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }

                            Icon(
                                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = "Toggle",
                                tint = primaryColor
                            )
                        }

                        // Category & API Tag
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "CATEGORY: ${tool.category}",
                                color = TextCyberDim,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 10.sp
                            )
                            Text(
                                text = if (tool.apiAvailable) "[REST API ACTIVE]" else "[WEB / APP PORTAL]",
                                color = if (tool.apiAvailable) primaryColor else TextCyberDim,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // Pricing Highlights Box (Addressing user's explicit requirement: "ai tools paid, all")
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(CyberSurface)
                                .border(1.dp, AmberNeon.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
                                .padding(8.dp)
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.MonetizationOn,
                                        contentDescription = "Pricing",
                                        tint = AmberNeon,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "PAID & SUBSCRIPTION TIERS:",
                                        color = AmberNeon,
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )
                                }

                                tool.pricingTiers.forEach { tier ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 4.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Text(
                                            text = "• ${tier.name}:",
                                            color = TextCyberBright,
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Medium,
                                            modifier = Modifier.weight(1f)
                                        )
                                        Text(
                                            text = tier.price,
                                            color = if (tier.isPaid) AmberNeon else primaryColor,
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Text(
                                        text = "   ${tier.description}",
                                        color = TextCyberDim,
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }

                        // Expanded View
                        AnimatedVisibility(
                            visible = isExpanded,
                            enter = expandVertically(),
                            exit = shrinkVertically()
                        ) {
                            Column(modifier = Modifier.padding(top = 10.dp)) {
                                Text(
                                    text = "WHAT THIS AI TOOL IS USED FOR:",
                                    color = primaryColor,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                                Text(
                                    text = tool.whatItsFor,
                                    color = TextCyberBright,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 11.sp,
                                    lineHeight = 16.sp,
                                    modifier = Modifier.padding(start = 4.dp, top = 2.dp)
                                )

                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "DETAILED OVERVIEW:",
                                    color = primaryColor,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                                Text(
                                    text = tool.description,
                                    color = TextCyberDim,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 11.sp,
                                    modifier = Modifier.padding(start = 4.dp, top = 2.dp)
                                )

                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "CORE CAPABILITIES & ARCHITECTURE:",
                                    color = primaryColor,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                                tool.keyFeatures.forEach { feature ->
                                    Text(
                                        text = "⚡ $feature",
                                        color = TextCyberBright,
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 11.sp,
                                        modifier = Modifier.padding(start = 4.dp, top = 2.dp)
                                    )
                                }

                                // Connected Software Tags
                                if (tool.connectedSoftware.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "CONNECTED ECOSYSTEM & SOFTWARE:",
                                        color = primaryColor,
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .horizontalScroll(rememberScrollState())
                                            .padding(top = 4.dp),
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        tool.connectedSoftware.forEach { swId ->
                                            AssistChip(
                                                onClick = { onNavigateToConnected(swId) },
                                                label = {
                                                    Text(
                                                        text = "🔗 $swId",
                                                        fontFamily = FontFamily.Monospace,
                                                        fontSize = 10.sp,
                                                        color = primaryColor
                                                    )
                                                },
                                                colors = AssistChipDefaults.assistChipColors(containerColor = CyberSurface),
                                                border = AssistChipDefaults.assistChipBorder(
                                                    borderColor = primaryColor.copy(alpha = 0.4f),
                                                    borderWidth = 1.dp,
                                                    enabled = true
                                                )
                                            )
                                        }
                                    }
                                }

                                // Action Buttons
                                Spacer(modifier = Modifier.height(10.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    OutlinedButton(
                                        onClick = { onRunInTerminal("ai ${tool.name.lowercase().split(" ").first()}") },
                                        modifier = Modifier
                                            .weight(1f)
                                            .testTag("btn_inspect_ai_${tool.id}"),
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = primaryColor),
                                        border = ButtonDefaults.outlinedButtonBorder.copy(brush = androidx.compose.ui.graphics.SolidColor(primaryColor))
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Terminal,
                                            contentDescription = "Inspect in CLI",
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "CLI PROBE",
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }

                                    OutlinedButton(
                                        onClick = {
                                            val copySummary = """
AI Tool: ${tool.name} (${tool.origin.badge})
Category: ${tool.category}
What it is for: ${tool.whatItsFor}
Pricing:
${tool.pricingTiers.joinToString("\n") { "• ${it.name}: ${it.price}" }}
Official: ${tool.officialSite}
                                            """.trimIndent()
                                            copyToClipboard(copySummary, tool.name)
                                        },
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = TextCyberBright),
                                        border = ButtonDefaults.outlinedButtonBorder.copy(brush = androidx.compose.ui.graphics.SolidColor(CyberBorder))
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ContentCopy,
                                            contentDescription = "Copy",
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "COPY SPECS",
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 11.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
