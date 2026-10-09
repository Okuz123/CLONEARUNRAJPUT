package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.CyberDisclaimerBadge
import com.example.ui.components.CyberScanlineOverlay
import com.example.ui.components.CyberTopBar
import com.example.ui.components.MatrixRainAnimation
import com.example.ui.screens.AiToolsScreen
import com.example.ui.screens.ConnectedMatrixScreen
import com.example.ui.screens.LinuxCommandsScreen
import com.example.ui.screens.SoftwareCatalogScreen
import com.example.ui.screens.TerminalScreen
import com.example.ui.theme.CyberBlack
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberDark
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.HackMatrixTheme
import com.example.ui.theme.TextCyberDim
import com.example.viewmodel.CyberTab
import com.example.viewmodel.CyberViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: CyberViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val uiState by viewModel.uiState.collectAsState()

            HackMatrixTheme(themeMode = uiState.themeMode) {
                // BackHandler returns to Terminal screen first if on another tab
                if (uiState.currentTab != CyberTab.TERMINAL) {
                    BackHandler {
                        viewModel.selectTab(CyberTab.TERMINAL)
                    }
                }

                Box(modifier = Modifier.fillMaxSize().background(CyberBlack)) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = CyberBlack,
                        topBar = {
                            Column {
                                CyberTopBar(
                                    soundEnabled = uiState.isSoundEnabled,
                                    onToggleSound = { viewModel.toggleSound() },
                                    scanlinesEnabled = uiState.isScanlinesEnabled,
                                    onToggleScanlines = { viewModel.toggleScanlines() },
                                    onLaunchMatrixRain = { viewModel.setMatrixRainFullScreen(true) },
                                    currentTheme = uiState.themeMode,
                                    onSelectTheme = { viewModel.setThemeMode(it) }
                                )
                                CyberDisclaimerBadge(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        },
                        bottomBar = {
                            CyberBottomNav(
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
                                CyberTab.TERMINAL -> {
                                    TerminalScreen(
                                        onTriggerMatrixRain = { viewModel.setMatrixRainFullScreen(true) },
                                        externalCommandToRun = uiState.pendingTerminalCommand,
                                        onExternalCommandHandled = { viewModel.clearPendingTerminalCommand() }
                                    )
                                }
                                CyberTab.LINUX_CODES -> {
                                    LinuxCommandsScreen(
                                        onRunInTerminal = { cmd -> viewModel.triggerTerminalCommand(cmd) },
                                        onNavigateToConnected = { targetId -> viewModel.navigateToConnectedMatrixWithFilter(targetId) }
                                    )
                                }
                                CyberTab.AI_TOOLS -> {
                                    AiToolsScreen(
                                        onRunInTerminal = { cmd -> viewModel.triggerTerminalCommand(cmd) },
                                        onNavigateToConnected = { targetId -> viewModel.navigateToConnectedMatrixWithFilter(targetId) }
                                    )
                                }
                                CyberTab.SOFTWARE_LS -> {
                                    SoftwareCatalogScreen(
                                        onRunInTerminal = { cmd -> viewModel.triggerTerminalCommand(cmd) },
                                        onNavigateToConnected = { targetId -> viewModel.navigateToConnectedMatrixWithFilter(targetId) }
                                    )
                                }
                                CyberTab.CONNECTED_MATRIX -> {
                                    ConnectedMatrixScreen(
                                        initialFilterTarget = uiState.pendingMatrixFilterTarget,
                                        onNavigateToItem = { targetId -> viewModel.navigateToConnectedMatrixWithFilter(targetId) }
                                    )
                                }
                            }
                        }
                    }

                    // CRT Scanlines Overlay
                    CyberScanlineOverlay(
                        enabled = uiState.isScanlinesEnabled,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Full-screen Matrix Rain Modal
                    AnimatedVisibility(
                        visible = uiState.isMatrixRainFullScreen,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        MatrixRainAnimation(
                            isFullScreen = true,
                            onDismiss = { viewModel.setMatrixRainFullScreen(false) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CyberBottomNav(
    currentTab: CyberTab,
    onTabSelected: (CyberTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val primaryColor = androidx.compose.material3.MaterialTheme.colorScheme.primary

    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .border(width = 1.dp, color = CyberBorder)
            .testTag("cyber_bottom_nav"),
        containerColor = CyberDark,
        tonalElevation = 8.dp
    ) {
        val items = listOf(
            Triple(CyberTab.TERMINAL, "Terminal", Icons.Default.Terminal),
            Triple(CyberTab.LINUX_CODES, "Linux", Icons.Default.Code),
            Triple(CyberTab.AI_TOOLS, "AI Tools", Icons.Default.AutoAwesome),
            Triple(CyberTab.SOFTWARE_LS, "Softwares", Icons.Default.Layers),
            Triple(CyberTab.CONNECTED_MATRIX, "Matrix", Icons.Default.Hub)
        )

        items.forEach { (tab, label, icon) ->
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
                        fontFamily = FontFamily.Monospace,
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = primaryColor,
                    selectedTextColor = primaryColor,
                    unselectedIconColor = TextCyberDim,
                    unselectedTextColor = TextCyberDim,
                    indicatorColor = CyberSurface
                ),
                modifier = Modifier.testTag("nav_item_${tab.name.lowercase()}")
            )
        }
    }
}
