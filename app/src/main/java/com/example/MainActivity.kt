package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.AnalyticsRepository
import com.example.ui.screens.AdminPanelScreen
import com.example.ui.screens.AiToolsScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LinuxCommandsScreen
import com.example.ui.screens.SoftwareCatalogScreen
import com.example.ui.theme.BharatNavy
import com.example.ui.theme.BharatNavyLight
import com.example.ui.theme.BharatSaffron
import com.example.ui.theme.BharatSaffronDark
import com.example.ui.theme.BharatTechTheme
import com.example.viewmodel.BharatTab
import com.example.viewmodel.CyberViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: CyberViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val uiState by viewModel.uiState.collectAsState()
            val currentUser by AnalyticsRepository.currentUser.collectAsState()

            BharatTechTheme {
                if (uiState.currentTab != BharatTab.HOME) {
                    BackHandler {
                        viewModel.selectTab(BharatTab.HOME)
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets.safeDrawing,
                    topBar = {
                        BharatTopAppBar(
                            userName = currentUser.name,
                            avatarInitials = currentUser.avatarInitials,
                            onProfileClick = { viewModel.selectTab(BharatTab.ADMIN_PANEL) }
                        )
                    },
                    bottomBar = {
                        BharatBottomNavBar(
                            currentTab = uiState.currentTab,
                            onTabSelected = { viewModel.selectTab(it) }
                        )
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        when (uiState.currentTab) {
                            BharatTab.HOME -> {
                                HomeScreen(
                                    onNavigateToAiTools = { viewModel.selectTab(BharatTab.AI_TOOLS) },
                                    onNavigateToLinux = { viewModel.selectTab(BharatTab.LINUX_CODES) },
                                    onNavigateToSoftware = { viewModel.selectTab(BharatTab.SOFTWARE_LS) },
                                    onNavigateToAdmin = { viewModel.selectTab(BharatTab.ADMIN_PANEL) },
                                    onRunTerminalCmd = { cmd -> viewModel.triggerTerminalCommand(cmd) }
                                )
                            }
                            BharatTab.AI_TOOLS -> {
                                AiToolsScreen(
                                    onNavigateToConnected = { toolId ->
                                        viewModel.navigateToConnectedMatrixWithFilter(toolId)
                                    }
                                )
                            }
                            BharatTab.LINUX_CODES -> {
                                LinuxCommandsScreen(
                                    onNavigateToConnected = { toolId ->
                                        viewModel.navigateToConnectedMatrixWithFilter(toolId)
                                    }
                                )
                            }
                            BharatTab.SOFTWARE_LS -> {
                                SoftwareCatalogScreen(
                                    onNavigateToConnected = { toolId ->
                                        viewModel.navigateToConnectedMatrixWithFilter(toolId)
                                    }
                                )
                            }
                            BharatTab.ADMIN_PANEL -> {
                                AdminPanelScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BharatTopAppBar(
    userName: String,
    avatarInitials: String,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .testTag("bharat_top_bar")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(BharatSaffronDark),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🇮🇳",
                        fontSize = 18.sp
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "भारत टेक हब",
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Bharat Tech Hub • AI & Codes",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Right side User Avatar Pill
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(BharatNavyLight)
                    .clickable { onProfileClick() }
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "User Profile",
                    tint = BharatNavy,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = avatarInitials,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = BharatNavy
                )
            }
        }
    }
}

@Composable
fun BharatBottomNavBar(
    currentTab: BharatTab,
    onTabSelected: (BharatTab) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .testTag("bharat_bottom_nav"),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp
    ) {
        val navItems = listOf(
            Triple(BharatTab.HOME, "होम", Icons.Default.Home),
            Triple(BharatTab.AI_TOOLS, "AI टूल्स", Icons.Default.AutoAwesome),
            Triple(BharatTab.LINUX_CODES, "लिनक्स", Icons.Default.Code),
            Triple(BharatTab.SOFTWARE_LS, "सॉफ्टवेयर", Icons.Default.Layers),
            Triple(BharatTab.ADMIN_PANEL, "एडमिन", Icons.Default.Security)
        )

        navItems.forEach { (tab, label, icon) ->
            val isSelected = currentTab == tab
            NavigationBarItem(
                selected = isSelected,
                onClick = { onTabSelected(tab) },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        modifier = Modifier.size(20.dp)
                    )
                },
                label = {
                    Text(
                        text = label,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = BharatSaffronDark,
                    selectedTextColor = BharatSaffronDark,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = BharatSaffronDark.copy(alpha = 0.12f)
                ),
                modifier = Modifier.testTag("nav_${tab.name.lowercase()}")
            )
        }
    }
}
