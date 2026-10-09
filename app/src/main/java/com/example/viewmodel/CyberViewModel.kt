package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.audio.CyberSoundSynthesizer
import com.example.ui.theme.CyberThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class CyberTab(val label: String, val iconTag: String) {
    TERMINAL("Terminal", "terminal"),
    LINUX_CODES("Linux Codes", "code"),
    AI_TOOLS("AI Tools (India & Paid)", "ai"),
    SOFTWARE_LS("Software LS", "software"),
    CONNECTED_MATRIX("Connected Matrix", "network")
}

data class CyberUiState(
    val currentTab: CyberTab = CyberTab.TERMINAL,
    val themeMode: CyberThemeMode = CyberThemeMode.MATRIX_GREEN,
    val isScanlinesEnabled: Boolean = true,
    val isSoundEnabled: Boolean = true,
    val isMatrixRainFullScreen: Boolean = false,
    val pendingTerminalCommand: String? = null,
    val pendingMatrixFilterTarget: String? = null
)

class CyberViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CyberUiState())
    val uiState: StateFlow<CyberUiState> = _uiState.asStateFlow()

    fun selectTab(tab: CyberTab) {
        CyberSoundSynthesizer.playKeyClick()
        _uiState.value = _uiState.value.copy(currentTab = tab)
    }

    fun setThemeMode(mode: CyberThemeMode) {
        _uiState.value = _uiState.value.copy(themeMode = mode)
    }

    fun toggleScanlines() {
        _uiState.value = _uiState.value.copy(isScanlinesEnabled = !_uiState.value.isScanlinesEnabled)
    }

    fun toggleSound() {
        val newState = !_uiState.value.isSoundEnabled
        CyberSoundSynthesizer.isSoundEnabled = newState
        _uiState.value = _uiState.value.copy(isSoundEnabled = newState)
        if (newState) {
            CyberSoundSynthesizer.playSuccess()
        }
    }

    fun setMatrixRainFullScreen(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(isMatrixRainFullScreen = enabled)
    }

    fun triggerTerminalCommand(command: String) {
        _uiState.value = _uiState.value.copy(
            currentTab = CyberTab.TERMINAL,
            pendingTerminalCommand = command
        )
    }

    fun clearPendingTerminalCommand() {
        _uiState.value = _uiState.value.copy(pendingTerminalCommand = null)
    }

    fun navigateToConnectedMatrixWithFilter(targetId: String) {
        CyberSoundSynthesizer.playKeyClick()
        _uiState.value = _uiState.value.copy(
            currentTab = CyberTab.CONNECTED_MATRIX,
            pendingMatrixFilterTarget = targetId
        )
    }
}
