package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AiOrigin
import com.example.data.model.AiTool
import com.example.data.repository.AiToolRepository
import com.example.ui.theme.BharatBgLight
import com.example.ui.theme.BharatGreen
import com.example.ui.theme.BharatGreenLight
import com.example.ui.theme.BharatNavy
import com.example.ui.theme.BharatNavyLight
import com.example.ui.theme.BharatSaffron
import com.example.ui.theme.BharatSaffronDark
import com.example.ui.theme.BharatSaffronLight

@Composable
fun AiToolsScreen(
    onNavigateToConnected: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("सभी (All)") }
    var expandedToolId by remember { mutableStateOf<String?>(null) }

    val tabTitles = listOf("सभी AI टूल्स", "🇮🇳 भारत निर्मित AI", "🌐 ग्लोबल पेड AI")

    val filteredTools = remember(selectedTabIndex, searchQuery, selectedCategory) {
        AiToolRepository.tools.filter { tool ->
            val matchesTab = when (selectedTabIndex) {
                1 -> tool.origin == AiOrigin.INDIA
                2 -> tool.origin == AiOrigin.GLOBAL
                else -> true
            }
            val matchesCategory = (selectedCategory == "सभी (All)" || tool.category == selectedCategory)
            val matchesSearch = searchQuery.isBlank() ||
                tool.name.contains(searchQuery, ignoreCase = true) ||
                tool.tagline.contains(searchQuery, ignoreCase = true) ||
                tool.whatItsForHindi.contains(searchQuery, ignoreCase = true) ||
                tool.whatItsFor.contains(searchQuery, ignoreCase = true) ||
                tool.pricingTiers.any { it.price.contains(searchQuery, ignoreCase = true) }
            matchesTab && matchesCategory && matchesSearch
        }
    }

    fun copyInfo(text: String, label: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
        clipboard?.setPrimaryClip(ClipData.newPlainText(label, text))
        Toast.makeText(context, "$label कॉपी कर लिया गया!", Toast.LENGTH_SHORT).show()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Tab Row
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = BharatSaffronDark,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = BharatSaffronDark,
                    height = 3.dp
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontSize = 12.sp,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedTabIndex == index) BharatSaffronDark else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("AI टूल्स खोजें (उदा. Sarvam, Krutrim, ₹, ChatGPT)...", fontSize = 12.sp) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = BharatNavy
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Categories Horizontal Scroll
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            AiToolRepository.categories.forEach { category ->
                val isSelected = selectedCategory == category
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedCategory = category },
                    label = { Text(text = category, fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = BharatNavyLight,
                        selectedLabelColor = BharatNavy
                    )
                )
            }
        }

        // Tools List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredTools, key = { it.id }) { tool ->
                val isExpanded = expandedToolId == tool.id

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        // Title Header
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { expandedToolId = if (isExpanded) null else tool.id },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = tool.name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 17.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(
                                                if (tool.origin == AiOrigin.INDIA) BharatSaffronLight else BharatNavyLight
                                            )
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = tool.origin.badge,
                                            color = if (tool.origin == AiOrigin.INDIA) BharatSaffronDark else BharatNavy,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                Text(
                                    text = tool.tagline,
                                    fontSize = 12.sp,
                                    color = BharatNavy,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }
                            Icon(
                                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = "Expand",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // Hindi Summary Box ("सरल हिंदी में समझें")
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(BharatBgLight)
                                .padding(10.dp)
                        ) {
                            Column {
                                Text(
                                    text = "💡 सरल हिंदी में समझें:",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    color = BharatSaffronDark
                                )
                                Text(
                                    text = tool.whatItsForHindi,
                                    fontSize = 12.sp,
                                    lineHeight = 17.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }
                        }

                        // Pricing Pill
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(BharatGreenLight)
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "💰 प्राइसिंग (Pricing):",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = BharatGreen
                                )
                                Text(
                                    text = tool.pricingTiers.firstOrNull()?.price ?: "मुफ्त / पेड",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = BharatGreen
                                )
                            }
                        }

                        // Expanded Deep Dive
                        AnimatedVisibility(
                            visible = isExpanded,
                            enter = expandVertically(),
                            exit = shrinkVertically()
                        ) {
                            Column(modifier = Modifier.padding(top = 12.dp)) {
                                Text(
                                    text = "विस्तृत प्लान व दरें (All Pricing Tiers):",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = BharatNavy
                                )
                                tool.pricingTiers.forEach { tier ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 4.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "• ${tier.name}",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                        Text(
                                            text = tier.price,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = BharatSaffronDark
                                        )
                                    }
                                    Text(
                                        text = "   ${tier.description}",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "मुख्य फीचर्स (Key Features):",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = BharatNavy
                                )
                                tool.keyFeatures.forEach { feat ->
                                    Text(
                                        text = "✓ $feat",
                                        fontSize = 12.sp,
                                        modifier = Modifier.padding(top = 2.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.height(12.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Button(
                                        onClick = {
                                            try {
                                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(tool.officialSite))
                                                context.startActivity(intent)
                                            } catch (_: Exception) {
                                                copyInfo(tool.officialSite, "वेबसाइट लिंक")
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = BharatNavy),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.OpenInBrowser,
                                            contentDescription = "Website",
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("ऑफिशियल वेबसाइट", fontSize = 11.sp)
                                    }

                                    OutlinedButton(
                                        onClick = {
                                            copyInfo(
                                                "${tool.name}\n${tool.whatItsForHindi}\nवेबसाइट: ${tool.officialSite}",
                                                tool.name
                                            )
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ContentCopy,
                                            contentDescription = "Copy",
                                            modifier = Modifier.size(16.dp)
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
