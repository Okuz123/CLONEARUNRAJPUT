package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Terminal
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.LinuxCommand
import com.example.data.repository.LinuxCommandRepository
import com.example.ui.theme.BharatBgLight
import com.example.ui.theme.BharatNavy
import com.example.ui.theme.BharatNavyLight
import com.example.ui.theme.BharatSaffronDark
import com.example.ui.theme.BharatSaffronLight

@Composable
fun LinuxCommandsScreen(
    onNavigateToConnected: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("सभी (All)") }
    var expandedCommandId by remember { mutableStateOf<String?>(null) }

    // Live Simulator state
    var simulatedCommandName by remember { mutableStateOf("ls") }
    var simulatedOutputText by remember { mutableStateOf(LinuxCommandRepository.commands.first().simulatedOutput) }

    val filteredCommands = remember(searchQuery, selectedCategory) {
        LinuxCommandRepository.commands.filter { cmd ->
            val matchesCategory = (selectedCategory == "सभी (All)" || cmd.category == selectedCategory)
            val matchesSearch = searchQuery.isBlank() ||
                cmd.name.contains(searchQuery, ignoreCase = true) ||
                cmd.summaryHindi.contains(searchQuery, ignoreCase = true) ||
                cmd.summary.contains(searchQuery, ignoreCase = true) ||
                cmd.example.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }
    }

    fun copyText(text: String, label: String) {
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
        // Search Input
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("लिनक्स कमांड खोजें (उदा. ls, grep, curl, chmod)...", fontSize = 12.sp) },
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

        // Categories Scroll
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            LinuxCommandRepository.categories.forEach { category ->
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

        // Live Interactive Terminal Sandbox Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Terminal,
                            contentDescription = "Console",
                            tint = Color(0xFF38BDF8),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "लाइव लिनक्स कंसोल सिमुलेटर (Linux Console)",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "guest@linux:~$ $simulatedCommandName",
                        color = Color(0xFF4ADE80),
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFF020617))
                        .padding(8.dp)
                ) {
                    Text(
                        text = simulatedOutputText,
                        color = Color(0xFFE2E8F0),
                        fontFamily = FontFamily.Monospace,
                        fontSize = 10.sp,
                        lineHeight = 14.sp
                    )
                }
            }
        }

        // Commands List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 4.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(filteredCommands, key = { it.id }) { cmd ->
                val isExpanded = expandedCommandId == cmd.id

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        // Title row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { expandedCommandId = if (isExpanded) null else cmd.id },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(BharatNavyLight)
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = "$ ${cmd.name}",
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = BharatNavy
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = cmd.category,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Icon(
                                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = "Expand",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // Summary
                        Text(
                            text = cmd.summaryHindi,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(top = 8.dp)
                        )

                        // Practical Code Example Box
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(BharatBgLight)
                                .padding(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = cmd.example,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = BharatNavy,
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(
                                    onClick = { copyText(cmd.example, cmd.name) },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ContentCopy,
                                        contentDescription = "Copy",
                                        tint = BharatNavy,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }

                        // Expanded Details
                        AnimatedVisibility(
                            visible = isExpanded,
                            enter = expandVertically(),
                            exit = shrinkVertically()
                        ) {
                            Column(modifier = Modifier.padding(top = 10.dp)) {
                                Text(
                                    text = "💡 सरल हिंदी में समझें:",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = BharatSaffronDark
                                )
                                Text(
                                    text = cmd.explanationHindi,
                                    fontSize = 12.sp,
                                    lineHeight = 17.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.padding(top = 2.dp)
                                )

                                if (cmd.flags.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = "ऑप्शंस व फ्लैग्स (Options & Flags):",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = BharatNavy
                                    )
                                    cmd.flags.forEach { flag ->
                                        Row(modifier = Modifier.padding(top = 3.dp)) {
                                            Text(
                                                text = "${flag.flag}: ",
                                                fontFamily = FontFamily.Monospace,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 12.sp,
                                                color = BharatNavy
                                            )
                                            Text(
                                                text = flag.description,
                                                fontSize = 12.sp,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))
                                Button(
                                    onClick = {
                                        simulatedCommandName = cmd.name
                                        simulatedOutputText = cmd.simulatedOutput
                                        Toast.makeText(context, "${cmd.name} कंसोल में चलाया गया!", Toast.LENGTH_SHORT).show()
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = BharatNavy),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = "Run",
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("कंसोल में चलाकर आउटपुट देखें (Test Output)")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
