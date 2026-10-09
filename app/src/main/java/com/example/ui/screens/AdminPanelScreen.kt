package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserRole
import com.example.data.repository.AnalyticsRepository
import com.example.ui.theme.BharatBgLight
import com.example.ui.theme.BharatGreen
import com.example.ui.theme.BharatGreenLight
import com.example.ui.theme.BharatNavy
import com.example.ui.theme.BharatNavyLight
import com.example.ui.theme.BharatSaffron
import com.example.ui.theme.BharatSaffronDark
import com.example.ui.theme.BharatSaffronLight

@Composable
fun AdminPanelScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val currentUser by AnalyticsRepository.currentUser.collectAsState()
    val analytics by AnalyticsRepository.analytics.collectAsState()
    val reviews by AnalyticsRepository.reviews.collectAsState()

    var adminPasscode by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf(false) }

    // User review submission form state
    var showReviewDialog by remember { mutableStateOf(false) }
    var reviewerName by remember { mutableStateOf("") }
    var reviewerCity by remember { mutableStateOf("") }
    var reviewRating by remember { mutableIntStateOf(5) }
    var reviewComment by remember { mutableStateOf("") }

    // Admin announcement state
    var newBroadcastText by remember { mutableStateOf("") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(modifier = Modifier.height(4.dp)) }

        // Top Login / User Identity Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("auth_card"),
                shape = RoundedCornerShape(16.dp),
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
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(if (currentUser.role == UserRole.ADMIN) Color(0xFF6A1B9A) else BharatNavy),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = currentUser.avatarInitials,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = currentUser.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = currentUser.email,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        // Role Badge
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(
                                    if (currentUser.role == UserRole.ADMIN)
                                        Color(0xFFEDE7F6)
                                    else
                                        BharatNavyLight
                                )
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = if (currentUser.role == UserRole.ADMIN) "🛡️ मुख्य एडमिन (Admin)" else "👤 यूजर (User)",
                                color = if (currentUser.role == UserRole.ADMIN) Color(0xFF6A1B9A) else BharatNavy,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Admin Login Input (Visible if not logged in as Admin)
                    if (currentUser.role != UserRole.ADMIN) {
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "एडमिन पैनल लॉगिन (Admin Access Login):",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = adminPasscode,
                                onValueChange = {
                                    adminPasscode = it
                                    loginError = false
                                },
                                modifier = Modifier.weight(1f),
                                placeholder = { Text("पासकोड डालें (उदा. admin123)", fontSize = 12.sp) },
                                singleLine = true,
                                visualTransformation = PasswordVisualTransformation(),
                                isError = loginError
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = {
                                    val success = AnalyticsRepository.loginAsAdmin(adminPasscode)
                                    if (success) {
                                        Toast.makeText(context, "एडमिन लॉगिन सफल!", Toast.LENGTH_SHORT).show()
                                        adminPasscode = ""
                                    } else {
                                        loginError = true
                                        Toast.makeText(context, "गलत पासकोड! (admin123 का उपयोग करें)", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A))
                            ) {
                                Text("लॉगिन", fontSize = 12.sp)
                            }
                        }

                        // 1-Tap Quick Demo Admin Login
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = "👉 1-क्लिक एडमिन डेमो लॉगिन",
                                color = Color(0xFF6A1B9A),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.clickable {
                                    AnalyticsRepository.loginAsAdmin("admin123")
                                    Toast.makeText(context, "एडमिन मोड सक्रिय!", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    } else {
                        // Logout option for Admin
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedButton(
                            onClick = {
                                AnalyticsRepository.logout()
                                Toast.makeText(context, "एडमिन से लॉगआउट किया गया", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("यूजर मोड में स्विच करें (Switch to User)", fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // Real-Time Analytics Dashboard (Downloads & Active Users Monitoring)
        item {
            Text(
                text = "📊 लाइव ऐप मॉनिटरिंग डैशबोर्ड (Live Telemetry)",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Stat Cards Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Downloads Metric
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = BharatNavyLight)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CloudDownload,
                                contentDescription = "Downloads",
                                tint = BharatNavy,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "कुल डाउनलोड्स",
                                fontSize = 11.sp,
                                color = BharatNavy,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Text(
                            text = "${analytics.totalDownloads}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = BharatNavy,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Text(
                            text = "+${analytics.todayDownloads} आज के नए",
                            fontSize = 10.sp,
                            color = BharatGreen,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Live Active Users Metric
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = BharatGreenLight)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(BharatGreen)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "अभी एक्टिव यूजर्स",
                                fontSize = 11.sp,
                                color = BharatGreen,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Text(
                            text = "${analytics.activeUsersNow}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = BharatGreen,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Text(
                            text = "🟢 लाइव ऑनलाइन भारत भर में",
                            fontSize = 10.sp,
                            color = BharatGreen
                        )
                    }
                }
            }
        }

        // City Breakdown in India (Bengaluru, Delhi NCR, Mumbai, Hyderabad, etc.)
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
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Cities",
                                tint = BharatSaffronDark,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "भारतीय शहरों में उपयोग (Active by City)",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                        Text(
                            text = "लाइव डेटा",
                            fontSize = 10.sp,
                            color = BharatGreen,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    analytics.topCities.forEach { city ->
                        Column(modifier = Modifier.padding(vertical = 4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = city.cityName, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                                Text(
                                    text = "${city.activeUsers} यूजर्स (${(city.percentage * 100).toInt()}%)",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            LinearProgressIndicator(
                                progress = { city.percentage },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = BharatNavy,
                                trackColor = BharatNavyLight
                            )
                        }
                    }
                }
            }
        }

        // Admin Broadcast Control (Only Admin can change)
        if (currentUser.role == UserRole.ADMIN) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF6A1B9A).copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Campaign,
                                contentDescription = "Broadcast",
                                tint = Color(0xFF6A1B9A)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "एडमिन लाइव ब्रॉडकास्ट संदेश भेजें",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color(0xFF6A1B9A)
                            )
                        }
                        Text(
                            text = "यह संदेश सभी यूजर्स को होम स्क्रीन पर तुरंत दिखाई देगा।",
                            fontSize = 11.sp,
                            color = Color(0xFF4A148C),
                            modifier = Modifier.padding(top = 2.dp)
                        )

                        OutlinedTextField(
                            value = newBroadcastText,
                            onValueChange = { newBroadcastText = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            placeholder = { Text("नया अनाउंसमेंट लिखें...") },
                            singleLine = true
                        )

                        Button(
                            onClick = {
                                if (newBroadcastText.isNotBlank()) {
                                    AnalyticsRepository.updateBroadcast(newBroadcastText)
                                    Toast.makeText(context, "अनाउंसमेंट अपडेट कर दिया गया!", Toast.LENGTH_SHORT).show()
                                    newBroadcastText = ""
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        ) {
                            Text("लाइव ब्रॉडकास्ट करें")
                        }
                    }
                }
            }
        }

        // Ratings & User Reviews Section
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
                        Column {
                            Text(
                                text = "⭐ यूजर्स रेटिंग व रिव्यूज (Ratings & Reviews)",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            Text(
                                text = "औसत रेटिंग: ${analytics.averageRating} / 5.0 (${analytics.totalReviews}+ रेटिंग्स)",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Button(
                            onClick = { showReviewDialog = !showReviewDialog },
                            colors = ButtonDefaults.buttonColors(containerColor = BharatSaffronDark)
                        ) {
                            Text("रिव्यू दें", fontSize = 12.sp)
                        }
                    }

                    // Form to Submit a Review
                    AnimatedVisibility(visible = showReviewDialog) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(BharatBgLight)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "अपना रिव्यू व रेटिंग लिखें:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = BharatNavy
                            )

                            // Star Selector
                            Row(
                                modifier = Modifier.padding(vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                (1..5).forEach { starIndex ->
                                    IconButton(
                                        onClick = { reviewRating = starIndex },
                                        modifier = Modifier.size(36.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (starIndex <= reviewRating) Icons.Default.Star else Icons.Default.StarBorder,
                                            contentDescription = "$starIndex Star",
                                            tint = Color(0xFFFFB300),
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                }
                                Text(
                                    text = "$reviewRating स्टार",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = BharatNavy
                                )
                            }

                            OutlinedTextField(
                                value = reviewerName,
                                onValueChange = { reviewerName = it },
                                label = { Text("आपका नाम (Your Name)") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            OutlinedTextField(
                                value = reviewerCity,
                                onValueChange = { reviewerCity = it },
                                label = { Text("शहर (City - उदा. Mumbai, Patna)") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            OutlinedTextField(
                                value = reviewComment,
                                onValueChange = { reviewComment = it },
                                label = { Text("आपकी राय (Comment / Feedback)") },
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 3
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                            Button(
                                onClick = {
                                    if (reviewComment.isNotBlank()) {
                                        AnalyticsRepository.submitReview(
                                            name = reviewerName,
                                            city = reviewerCity,
                                            rating = reviewRating,
                                            comment = reviewComment
                                        )
                                        Toast.makeText(context, "धन्यवाद! आपका रिव्यू दर्ज हो गया।", Toast.LENGTH_SHORT).show()
                                        showReviewDialog = false
                                        reviewerName = ""
                                        reviewerCity = ""
                                        reviewComment = ""
                                    } else {
                                        Toast.makeText(context, "कृपया अपना कमेंट लिखें", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = BharatGreen),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("रिव्यू सबमिट करें (Submit Review)")
                            }
                        }
                    }

                    // Reviews Feed
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "हालिया यूजर्स फीडबैक (Recent User Reviews):",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    reviews.take(6).forEach { rev ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                                .padding(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(text = rev.userName, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Text(text = rev.userCity, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                                Row {
                                    repeat(rev.rating) {
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = "Star",
                                            tint = Color(0xFFFFB300),
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                            }
                            Text(
                                text = rev.comment,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(top = 4.dp),
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}
