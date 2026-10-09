package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.AiToolRepository
import com.example.data.repository.AnalyticsRepository
import com.example.data.repository.LinuxCommandRepository
import com.example.ui.theme.BharatGreen
import com.example.ui.theme.BharatGreenLight
import com.example.ui.theme.BharatNavy
import com.example.ui.theme.BharatNavyLight
import com.example.ui.theme.BharatSaffron
import com.example.ui.theme.BharatSaffronDark
import com.example.ui.theme.BharatSaffronLight

@Composable
fun HomeScreen(
    onNavigateToAiTools: () -> Unit,
    onNavigateToLinux: () -> Unit,
    onNavigateToSoftware: () -> Unit,
    onNavigateToAdmin: () -> Unit,
    onRunTerminalCmd: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val analytics by AnalyticsRepository.analytics.collectAsState()
    val broadcastMsg by AnalyticsRepository.adminBroadcastMessage.collectAsState()
    val currentUser by AnalyticsRepository.currentUser.collectAsState()

    fun copyText(text: String, label: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
        clipboard?.setPrimaryClip(ClipData.newPlainText(label, text))
        Toast.makeText(context, "$label कॉपी कर लिया गया!", Toast.LENGTH_SHORT).show()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(modifier = Modifier.height(4.dp)) }

        // Live Admin Broadcast Announcement (if present)
        if (broadcastMsg.isNotBlank()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = BharatSaffronLight.copy(alpha = 0.5f))
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Campaign,
                            contentDescription = "Announcement",
                            tint = BharatSaffronDark,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = broadcastMsg,
                            color = BharatSaffronDark,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }

        // Hero Welcome Card for Indian Developers & Students
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("hero_welcome_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    BharatNavy,
                                    BharatNavy.copy(alpha = 0.85f),
                                    BharatSaffronDark.copy(alpha = 0.9f)
                                )
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color.White.copy(alpha = 0.2f))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "🇮🇳 भारत टेक हब (Bharat Tech Hub)",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            // Current User Profile Badge
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.White.copy(alpha = 0.2f))
                                    .clickable { onNavigateToAdmin() }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Profile",
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = currentUser.name.split(" ").first(),
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "भारतीय AI टूल्स, लिनक्स कोड्स व सॉफ्टवेयर का संपूर्ण संग्रह",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 26.sp
                        )
                        Text(
                            text = "सरल हिंदी व अंग्रेजी में समझें कि कौन-सा AI व लिनक्स टूल किस काम आता है और क्यों उपयोगी है।",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 13.sp,
                            lineHeight = 18.sp,
                            modifier = Modifier.padding(top = 6.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Live User Trust Counter
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Black.copy(alpha = 0.25f))
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.CloudDownload,
                                    contentDescription = "Downloads",
                                    tint = BharatSaffronLight,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "${analytics.totalDownloads}+ डाउनलोड्स",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Rating",
                                    tint = Color(0xFFFFD700),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${analytics.averageRating} ★ (${analytics.totalReviews}+ रिव्यू)",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }

        // Quick Category Navigation Tiles
        item {
            Text(
                text = "प्रमुख अनुभाग (Quick Features)",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                QuickNavCard(
                    title = "🇮🇳 भारत AI टूल्स",
                    subtitle = "सर्वम, कृत्रिम, भाषिणी",
                    icon = Icons.Default.AutoAwesome,
                    color = BharatSaffronDark,
                    onClick = onNavigateToAiTools
                )
                QuickNavCard(
                    title = "🐧 लिनक्स कोड्स",
                    subtitle = "सरल हिंदी व्याख्या सहित",
                    icon = Icons.Default.Code,
                    color = BharatNavy,
                    onClick = onNavigateToLinux
                )
                QuickNavCard(
                    title = "💻 सॉफ्टवेयर LS",
                    subtitle = "डॉकर, Nmap, डेटाबेस",
                    icon = Icons.Default.Layers,
                    color = BharatGreen,
                    onClick = onNavigateToSoftware
                )
                QuickNavCard(
                    title = "🛡️ एडमिन पैनल",
                    subtitle = "लाइव डाउनलोड व रेटिंग",
                    icon = Icons.Default.Security,
                    color = Color(0xFF6A1B9A),
                    onClick = onNavigateToAdmin
                )
            }
        }

        // Trending Made in India AI Spotlight
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(BharatSaffronLight),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "🇮🇳", fontSize = 18.sp)
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Sarvam AI (सर्वम AI)",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = "बेंगलुरु, भारत • स्वदेशी भाषा AI",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(BharatGreenLight)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "ट्रेंडिंग AI",
                                color = BharatGreen,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "सरल हिंदी में समझें:",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        color = BharatSaffronDark
                    )
                    Text(
                        text = "यह भारत का अपना AI है जो हिंदी, तमिल, तेलुगु, बंगाली आदि में बिल्कुल असली इंसानों की तरह आवाज निकालता है। बैंक, कस्टमर केयर और ऐप्स में वॉयस बॉट बनाने के लिए यह सबसे बेहतरीन टूल है।",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 18.sp,
                        modifier = Modifier.padding(top = 2.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "प्राइस: ₹0 मुफ्त ट्रायल | ₹0.15 / 1k टोकन",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = BharatNavy
                        )
                        Button(
                            onClick = onNavigateToAiTools,
                            colors = ButtonDefaults.buttonColors(containerColor = BharatNavy),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("सभी AI देखें", fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // Daily Linux Command with 1-tap Copy & Try
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "💡 आज का उपयोगी लिनक्स कोड (Daily Tip)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = BharatNavy
                        )
                        IconButton(
                            onClick = { copyText("ls -laSh", "ls कमांड") },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Copy",
                                tint = BharatNavy,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF1E293B))
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "$ ls -laSh /var/log",
                            color = Color(0xFF38BDF8),
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Text(
                        text = "यह क्या करता है: फोल्डर की सभी फाइल्स को उनके साइज के हिसाब से सबसे बड़ी फाइल पहले दिखाते हुए क्रमबद्ध करता है। सर्वर में स्पेस खाली करने के लिए बहुत काम आता है।",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 17.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

@Composable
fun QuickNavCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.08f)),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.25f))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = color,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = color
            )
            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1
            )
        }
    }
}
