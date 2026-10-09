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
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.data.model.SoftwareItem
import com.example.data.repository.SoftwareRepository
import com.example.ui.theme.AmberNeon
import com.example.ui.theme.CyberBlack
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberDark
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.LocalCyberThemeMode
import com.example.ui.theme.TextCyberBright
import com.example.ui.theme.TextCyberDim

@Composable
fun SoftwareCatalogScreen(
    onRunInTerminal: (String) -> Unit,
    onNavigateToConnected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val themeMode = LocalCyberThemeMode.current
    val primaryColor = themeMode.primaryColor
    val context = LocalContext.current

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    var expandedSoftwareId by remember { mutableStateOf<String?>(null) }

    val filteredSoftwares = remember(searchQuery, selectedCategory) {
        SoftwareRepository.softwares.filter { sw ->
            val matchesCategory = (selectedCategory == "All" || sw.category == selectedCategory)
            val matchesSearch = searchQuery.isBlank() ||
                sw.name.contains(searchQuery, ignoreCase = true) ||
                sw.summary.contains(searchQuery, ignoreCase = true) ||
                sw.whatItsFor.contains(searchQuery, ignoreCase = true) ||
                sw.installCommand.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }
    }

    fun copyToClipboard(text: String, label: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard?.setPrimaryClip(clip)
        CyberSoundSynthesizer.playSuccess()
        Toast.makeText(context, "Copied: $text", Toast.LENGTH_SHORT).show()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CyberBlack)
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                CyberSoundSynthesizer.playKeyClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp)
                .testTag("software_search_field"),
            placeholder = {
                Text(
                    text = "Search software inventory (e.g. Nmap, Docker, Wireshark)...",
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

        // Categories scroll
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(bottom = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            SoftwareRepository.categories.forEach { category ->
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
                            fontSize = 11.sp,
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
                    modifier = Modifier.testTag("software_category_$category")
                )
            }
        }

        // Header statistics
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "INDEXED: ${filteredSoftwares.size} SOFTWARE PACKAGES",
                color = primaryColor,
                fontFamily = FontFamily.Monospace,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "SOFTWARE_LS --VERBOSE",
                color = TextCyberDim,
                fontFamily = FontFamily.Monospace,
                fontSize = 10.sp
            )
        }

        // Software List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredSoftwares, key = { it.id }) { sw ->
                val isExpanded = expandedSoftwareId == sw.id

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = if (isExpanded) primaryColor else CyberBorder,
                            shape = RoundedCornerShape(6.dp)
                        )
                        .testTag("software_card_${sw.id}"),
                    colors = CardDefaults.cardColors(containerColor = CyberDark)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        // Title row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    CyberSoundSynthesizer.playKeyClick()
                                    expandedSoftwareId = if (isExpanded) null else sw.id
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = sw.name,
                                        color = TextCyberBright,
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(3.dp))
                                            .background(CyberSurface)
                                            .border(1.dp, CyberBorder, RoundedCornerShape(3.dp))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = sw.category,
                                            color = primaryColor,
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 9.sp
                                        )
                                    }
                                }

                                Text(
                                    text = "LICENSE: ${sw.licenseType}",
                                    color = if (sw.isCommercialPaid) AmberNeon else TextCyberDim,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }

                            Icon(
                                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = "Toggle",
                                tint = primaryColor
                            )
                        }

                        // Summary
                        Text(
                            text = sw.summary,
                            color = TextCyberDim,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        // Install Command Box
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(CyberSurface)
                                .border(1.dp, CyberBorder, RoundedCornerShape(4.dp))
                                .padding(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "$ ${sw.installCommand}",
                                    color = primaryColor,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 11.sp,
                                    modifier = Modifier.weight(1f)
                                )

                                IconButton(
                                    onClick = { copyToClipboard(sw.installCommand, sw.name) },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ContentCopy,
                                        contentDescription = "Copy Install Command",
                                        tint = primaryColor,
                                        modifier = Modifier.size(16.dp)
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
                                    text = "WHAT THIS SOFTWARE IS USED FOR:",
                                    color = primaryColor,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                                Text(
                                    text = sw.whatItsFor,
                                    color = TextCyberBright,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 11.sp,
                                    lineHeight = 16.sp,
                                    modifier = Modifier.padding(start = 4.dp, top = 2.dp)
                                )

                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "HOW IT WORKS INTERNALLY:",
                                    color = primaryColor,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                                Text(
                                    text = sw.howItWorks,
                                    color = TextCyberDim,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 11.sp,
                                    lineHeight = 16.sp,
                                    modifier = Modifier.padding(start = 4.dp, top = 2.dp)
                                )

                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "KEY COMMANDS & USAGE:",
                                    color = primaryColor,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                                sw.keyCommands.forEach { cmdExample ->
                                    Text(
                                        text = "• $cmdExample",
                                        color = TextCyberBright,
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 11.sp,
                                        modifier = Modifier.padding(start = 4.dp, top = 2.dp)
                                    )
                                }

                                // Connected Ecosystem
                                if (sw.connectedTools.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "CONNECTED ECOSYSTEM & TOOLS:",
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
                                        sw.connectedTools.forEach { toolId ->
                                            AssistChip(
                                                onClick = { onNavigateToConnected(toolId) },
                                                label = {
                                                    Text(
                                                        text = "🔗 $toolId",
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

                                // Actions
                                Spacer(modifier = Modifier.height(10.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    OutlinedButton(
                                        onClick = { onRunInTerminal("software ${sw.name.lowercase().split(" ").first()}") },
                                        modifier = Modifier
                                            .weight(1f)
                                            .testTag("btn_inspect_sw_${sw.id}"),
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
                                        onClick = { copyToClipboard(sw.installCommand, sw.name) },
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = TextCyberBright),
                                        border = ButtonDefaults.outlinedButtonBorder.copy(brush = androidx.compose.ui.graphics.SolidColor(CyberBorder))
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ContentCopy,
                                            contentDescription = "Copy Install",
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "INSTALL CMD",
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
