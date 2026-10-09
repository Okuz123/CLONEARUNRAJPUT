package com.example.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class BharatTab(val label: String, val hindiLabel: String) {
    HOME("Home", "होम"),
    AI_TOOLS("AI Tools", "AI टूल्स"),
    LINUX_CODES("Linux Codes", "लिनक्स"),
    SOFTWARE_LS("Softwares", "सॉफ्टवेयर"),
    ADMIN_PANEL("Admin", "एडमिन")
}

data class AppUiState(
    val currentTab: BharatTab = BharatTab.HOME,
    val pendingTerminalCommand: String? = null,
    val pendingMatrixFilterTarget: String? = null
)

class CyberViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    fun selectTab(tab: BharatTab) {
        _uiState.value = _uiState.value.copy(currentTab = tab)
    }

    fun triggerTerminalCommand(command: String) {
        _uiState.value = _uiState.value.copy(
            currentTab = BharatTab.LINUX_CODES,
            pendingTerminalCommand = command
        )
    }

    fun clearPendingTerminalCommand() {
        _uiState.value = _uiState.value.copy(pendingTerminalCommand = null)
    }

    fun navigateToConnectedMatrixWithFilter(targetId: String) {
        _uiState.value = _uiState.value.copy(
            currentTab = BharatTab.SOFTWARE_LS,
            pendingMatrixFilterTarget = targetId
        )
    }
}
