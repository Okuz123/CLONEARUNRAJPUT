package com.example.ui.screens

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.CyberSoundSynthesizer
import com.example.data.model.NodeType
import com.example.data.model.TechConnection
import com.example.data.repository.ConnectionsRepository
import com.example.ui.theme.AmberNeon
import com.example.ui.theme.CyberBlack
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberDark
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.LocalCyberThemeMode
import com.example.ui.theme.TextCyberBright
import com.example.ui.theme.TextCyberDim

@Composable
fun ConnectedMatrixScreen(
    initialFilterTarget: String? = null,
    onNavigateToItem: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val themeMode = LocalCyberThemeMode.current
    val primaryColor = themeMode.primaryColor

    var searchQuery by remember { mutableStateOf(initialFilterTarget ?: "") }
    var selectedTypeFilter by remember { mutableStateOf("All") }

    val filterTypes = listOf(
        "All",
        "Linux <-> Software",
        "Software <-> AI Tools",
        "Linux <-> AI Tools",
        "Security Recon Chains"
    )

    val filteredConnections = remember(searchQuery, selectedTypeFilter) {
        ConnectionsRepository.connections.filter { conn ->
            val matchesSearch = searchQuery.isBlank() ||
                conn.sourceName.contains(searchQuery, ignoreCase = true) ||
                conn.targetName.contains(searchQuery, ignoreCase = true) ||
                conn.sourceId.contains(searchQuery, ignoreCase = true) ||
                conn.targetId.contains(searchQuery, ignoreCase = true) ||
                conn.relationship.contains(searchQuery, ignoreCase = true) ||
                conn.description.contains(searchQuery, ignoreCase = true)

            val matchesType = when (selectedTypeFilter) {
                "Linux <-> Software" ->
                    (conn.sourceType == NodeType.LINUX_COMMAND && conn.targetType == NodeType.SOFTWARE) ||
                    (conn.sourceType == NodeType.SOFTWARE && conn.targetType == NodeType.LINUX_COMMAND)
                "Software <-> AI Tools" ->
                    (conn.sourceType == NodeType.SOFTWARE && conn.targetType == NodeType.AI_TOOL) ||
                    (conn.sourceType == NodeType.AI_TOOL && conn.targetType == NodeType.SOFTWARE)
                "Linux <-> AI Tools" ->
                    (conn.sourceType == NodeType.LINUX_COMMAND && conn.targetType == NodeType.AI_TOOL) ||
                    (conn.sourceType == NodeType.AI_TOOL && conn.targetType == NodeType.LINUX_COMMAND)
                "Security Recon Chains" ->
                    conn.relationship.contains("Security", ignoreCase = true) ||
                    conn.relationship.contains("Packet", ignoreCase = true) ||
                    conn.relationship.contains("Audit", ignoreCase = true)
                else -> true
            }

            matchesSearch && matchesType
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CyberBlack)
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        // Search Filter
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                CyberSoundSynthesizer.playKeyClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp)
                .testTag("connections_search_field"),
            placeholder = {
                Text(
                    text = "Search connection matrix (e.g. Docker, Sarvam, Nmap)...",
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

        // Filter Type Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(bottom = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            filterTypes.forEach { typeLabel ->
                val isSelected = selectedTypeFilter == typeLabel
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        CyberSoundSynthesizer.playKeyClick()
                        selectedTypeFilter = typeLabel
                    },
                    label = {
                        Text(
                            text = typeLabel,
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
                    modifier = Modifier.testTag("filter_chip_$typeLabel")
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
                text = "ACTIVE LINKS: ${filteredConnections.size} MATRIX CHANNELS",
                color = primaryColor,
                fontFamily = FontFamily.Monospace,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "INTERCONNECTED TECH STACK",
                color = AmberNeon,
                fontFamily = FontFamily.Monospace,
                fontSize = 10.sp
            )
        }

        // Connections List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredConnections) { conn ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, CyberBorder, RoundedCornerShape(6.dp))
                        .testTag("connection_card_${conn.sourceId}_${conn.targetId}"),
                    colors = CardDefaults.cardColors(containerColor = CyberDark)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        // Connection Flow Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Source Node
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(CyberSurface)
                                    .border(1.dp, primaryColor.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
                                    .clickable { onNavigateToItem(conn.sourceId) }
                                    .padding(horizontal = 8.dp, vertical = 6.dp)
                            ) {
                                Column {
                                    Text(
                                        text = getNodeTypeLabel(conn.sourceType),
                                        color = TextCyberDim,
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 8.sp
                                    )
                                    Text(
                                        text = conn.sourceName,
                                        color = TextCyberBright,
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp,
                                        maxLines = 1
                                    )
                                }
                            }

                            // Flow Arrow & Relationship Pill
                            Column(
                                modifier = Modifier.padding(horizontal = 6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = "Connects to",
                                    tint = primaryColor,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = conn.relationship,
                                    color = AmberNeon,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1
                                )
                            }

                            // Target Node
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(CyberSurface)
                                    .border(1.dp, themeMode.accentColor.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
                                    .clickable { onNavigateToItem(conn.targetId) }
                                    .padding(horizontal = 8.dp, vertical = 6.dp)
                            ) {
                                Column {
                                    Text(
                                        text = getNodeTypeLabel(conn.targetType),
                                        color = TextCyberDim,
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 8.sp
                                    )
                                    Text(
                                        text = conn.targetName,
                                        color = TextCyberBright,
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp,
                                        maxLines = 1
                                    )
                                }
                            }
                        }

                        // Detailed explanation of how they are connected
                        Text(
                            text = conn.description,
                            color = TextCyberDim,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp,
                            lineHeight = 15.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

private fun getNodeTypeLabel(type: NodeType): String {
    return when (type) {
        NodeType.LINUX_COMMAND -> "LINUX CMD"
        NodeType.SOFTWARE -> "SOFTWARE"
        NodeType.AI_TOOL -> "AI TOOL"
    }
}
