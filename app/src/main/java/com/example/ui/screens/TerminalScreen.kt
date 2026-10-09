package com.example.ui.screens

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.CyberSoundSynthesizer
import com.example.data.repository.AiToolRepository
import com.example.data.repository.LinuxCommandRepository
import com.example.data.repository.SoftwareRepository
import com.example.ui.theme.CyberBlack
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberDark
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.LocalCyberThemeMode
import com.example.ui.theme.TextCyberBright
import com.example.ui.theme.TextCyberDim
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class TerminalLog(
    val id: String = java.util.UUID.randomUUID().toString(),
    val timestamp: String,
    val command: String,
    val output: String,
    val isError: Boolean = false
)

@Composable
fun TerminalScreen(
    onTriggerMatrixRain: () -> Unit,
    externalCommandToRun: String? = null,
    onExternalCommandHandled: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val themeMode = LocalCyberThemeMode.current
    val primaryColor = themeMode.primaryColor
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    var inputCommand by remember { mutableStateOf("") }
    var isExecuting by remember { mutableStateOf(false) }

    val logs = remember {
        mutableStateListOf(
            TerminalLog(
                timestamp = getCurrentTime(),
                command = "boot --init",
                output = """
============================================================
* HACK_MATRIX SHELL v3.1 [/bin/hacksh]
* ENTERTAINMENT & EDUCATIONAL TECH MATRIX DECK
* FOR SIMULATION & CHEATSHEET PURPOSES ONLY
============================================================
Type 'help' to view all available commands.
Quick commands: 'ls', 'matrix', 'scan 10.0.0.1', 'neofetch'
                """.trimIndent()
            )
        )
    }

    fun vibrateShort() {
        try {
            val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vm = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vm?.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator?.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(30)
            }
        } catch (_: Exception) {}
    }

    fun executeCommand(rawCmd: String) {
        val trimmed = rawCmd.trim()
        if (trimmed.isEmpty()) return

        vibrateShort()
        CyberSoundSynthesizer.playCommandExec()
        isExecuting = true

        scope.launch {
            val parts = trimmed.split(" ")
            val cmd = parts.first().lowercase()
            val arg = if (parts.size > 1) parts.subList(1, parts.size).joinToString(" ") else ""

            var output = ""
            var isError = false

            when (cmd) {
                "clear", "cls" -> {
                    logs.clear()
                    isExecuting = false
                    inputCommand = ""
                    return@launch
                }
                "help", "?" -> {
                    output = """
AVAILABLE SIMULATION COMMANDS:
  help               - Display this manual
  clear              - Clear terminal display
  ls                 - List categories and modules
  matrix             - Launch full-screen Matrix rain
  scan <target>      - Simulated cyber reconnaissance scan
  decrypt <cipher>   - Decrypt simulated cryptographic hash
  neofetch           - Display cyberpunk system specifications
  cowsay <text>      - ASCII art talking cow announcement
  whoami             - Current security context
  linux <name>       - Quick lookup for Linux command (e.g. linux curl)
  ai <name>          - Quick lookup for AI tool (e.g. ai sarvam, ai krutrim)
  software <name>    - Quick lookup for software (e.g. software nmap)
  uname -a           - Kernel architecture version
  quote              - Cybersecurity motto of the day
                    """.trimIndent()
                }
                "ls" -> {
                    output = """
drwxr-xr-x 8 root root 4096 Oct  8 10:00 /linux_commands/
  ├── file_ops/ (ls, cp, mv, grep, find, awk, sed)
  ├── networking/ (curl, wget, nmap, ss, tcpdump)
  ├── sys_admin/ (neofetch, top, htop, systemctl)
  └── permissions/ (chmod, chown, sudo)
drwxr-xr-x 4 root root 4096 Oct  8 10:00 /ai_tools_india/
  ├── sarvam_ai/ (Indic LLMs, Bulbul TTS, Saaras ASR)
  ├── krutrim_cloud/ (Ola sovereign GPU & Indic LLM)
  ├── hanooman_ai/ (BharatGPT IIT Bombay consortium)
  └── bhashini/ (Govt. of India Translation Mission)
drwxr-xr-x 6 root root 4096 Oct  8 10:00 /ai_tools_global/
  ├── chatgpt_plus/ ($20/mo OpenAI GPT-4o)
  ├── claude_pro/ ($20/mo Anthropic Sonnet 3.5)
  └── cursor_pro/ ($20/mo AI Code Editor)
drwxr-xr-x 8 root root 4096 Oct  8 10:00 /software_catalog/
  └── (nmap, wireshark, metasploit, burp, docker, k8s, redis)
                    """.trimIndent()
                }
                "matrix" -> {
                    output = ">>> INITIATING DIGITAL MATRIX RAIN SUBSYSTEM..."
                    onTriggerMatrixRain()
                }
                "scan" -> {
                    val target = if (arg.isNotEmpty()) arg else "192.168.1.1"
                    output = ">>> Pinging $target...\n"
                    delay(300)
                    output += "[+] Target is UP (0.42ms RTT)\n"
                    output += "[+] Initiating stealth SYN port audit...\n"
                    delay(400)
                    output += """
PORT     STATE    SERVICE       BANNER / VERSION
22/tcp   OPEN     SSH           OpenSSH 9.2p1 (Linux)
80/tcp   OPEN     HTTP          nginx/1.22.1 (Debian)
443/tcp  OPEN     HTTPS         nginx/1.22.1 TLSv1.3
5432/tcp OPEN     POSTGRESQL    PostgreSQL 16.2
11434/tcp OPEN    OLLAMA_AI     Ollama Local Inference API
[SIMULATION COMPLETE: 5 open ports identified]
                    """.trimIndent()
                    CyberSoundSynthesizer.playSuccess()
                }
                "decrypt" -> {
                    val hash = if (arg.isNotEmpty()) arg else "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"
                    output = ">>> HASH: $hash\n"
                    output += "[*] Analyzing hash signature: SHA-256\n"
                    output += "[*] Deploying rainbow table heuristic match...\n"
                    delay(500)
                    output += """
[+] CRACKED IN 0.18s!
[+] ALGORITHM : SHA-256
[+] RESULT    : "HACK_THE_PLANET_2026_INDIA"
[+] ENTROPY   : 92.4 bits (HIGH_SECURITY)
                    """.trimIndent()
                    CyberSoundSynthesizer.playSuccess()
                }
                "neofetch" -> {
                    output = LinuxCommandRepository.commands.find { it.id == "neofetch" }?.simulatedOutput
                        ?: "Neofetch specs loaded."
                }
                "whoami" -> {
                    output = "root (UID=0, GID=0, GROUPS=root,wheel,cyberdeck) [SIMULATED ROOT ACCESS]"
                }
                "uname" -> {
                    output = "Linux cyberdeck 6.10.4-zen1 #1 SMP PREEMPT_DYNAMIC GNU/Linux x86_64"
                }
                "quote" -> {
                    val quotes = listOf(
                        "\"There are two types of companies: those that have been hacked, and those that will be.\" — Robert Mueller",
                        "\"Security is not a product, but a process.\" — Bruce Schneier",
                        "\"The quieter you become, the more you are able to hear.\" — Kali Linux Motto",
                        "\"Knowledge is power, safeguard it wisely.\" — HackMatrix"
                    )
                    output = quotes.random()
                }
                "cowsay" -> {
                    val msg = if (arg.isNotEmpty()) arg else "HackMatrix: Build, Learn & Secure!"
                    output = """
  ________________________________________
< $msg >
  ----------------------------------------
         \   ^__^
          \  (oo)\_______
             (__)\       )\/\
                 ||----w |
                 ||     ||
                    """.trimIndent()
                }
                "linux" -> {
                    val cmdFound = LinuxCommandRepository.commands.find { it.name.contains(arg, ignoreCase = true) }
                    if (cmdFound != null) {
                        output = """
COMMAND: ${cmdFound.name} [${cmdFound.category}]
SUMMARY: ${cmdFound.summary}
SYNTAX : ${cmdFound.syntax}
EXAMPLE: ${cmdFound.example}
OUTPUT PREVIEW:
${cmdFound.simulatedOutput}
                        """.trimIndent()
                    } else {
                        output = "Command '$arg' not found in Linux encyclopedia. Type 'ls' to see list."
                        isError = true
                    }
                }
                "ai" -> {
                    val aiFound = AiToolRepository.tools.find { it.name.contains(arg, ignoreCase = true) || it.id.contains(arg, ignoreCase = true) }
                    if (aiFound != null) {
                        val pricingStr = aiFound.pricingTiers.joinToString("\n  • ") { "${it.name}: ${it.price}" }
                        output = """
TOOL    : ${aiFound.name} (${aiFound.origin.badge})
CATEGORY: ${aiFound.category}
TAGLINE : ${aiFound.tagline}
PURPOSE : ${aiFound.whatItsFor}
PRICING TIERS:
  • $pricingStr
CONNECTED TO: ${aiFound.connectedSoftware.joinToString(", ")}
                        """.trimIndent()
                    } else {
                        output = "AI tool '$arg' not found. Try 'ai sarvam', 'ai krutrim', or 'ai chatgpt'."
                        isError = true
                    }
                }
                "software" -> {
                    val swFound = SoftwareRepository.softwares.find { it.name.contains(arg, ignoreCase = true) || it.id.contains(arg, ignoreCase = true) }
                    if (swFound != null) {
                        output = """
SOFTWARE: ${swFound.name} [${swFound.category}]
PURPOSE : ${swFound.whatItsFor}
INSTALL : ${swFound.installCommand}
LICENSE : ${swFound.licenseType}
KEY COMMANDS:
${swFound.keyCommands.joinToString("\n") { "  • $it" }}
                        """.trimIndent()
                    } else {
                        output = "Software '$arg' not found. Try 'software nmap', 'software docker', etc."
                        isError = true
                    }
                }
                else -> {
                    // Try to match directly with linux commands
                    val directCmd = LinuxCommandRepository.commands.find { it.name.equals(cmd, ignoreCase = true) }
                    if (directCmd != null) {
                        output = directCmd.simulatedOutput
                    } else {
                        output = "hacksh: command not found: '$trimmed'. Type 'help' for command directory."
                        isError = true
                        CyberSoundSynthesizer.playError()
                    }
                }
            }

            logs.add(
                TerminalLog(
                    timestamp = getCurrentTime(),
                    command = trimmed,
                    output = output,
                    isError = isError
                )
            )

            isExecuting = false
            inputCommand = ""
            listState.animateScrollToItem(logs.size - 1)
        }
    }

    // Handle external command execution requested from other screens
    LaunchedEffect(externalCommandToRun) {
        if (!externalCommandToRun.isNullOrBlank()) {
            executeCommand(externalCommandToRun)
            onExternalCommandHandled()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CyberBlack)
            .padding(8.dp)
    ) {
        // Quick Action Chips Scrollable Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(bottom = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val quickChips = listOf(
                "help",
                "ls",
                "matrix",
                "scan 192.168.1.1",
                "neofetch",
                "ai sarvam",
                "ai krutrim",
                "software nmap",
                "decrypt",
                "cowsay",
                "quote",
                "clear"
            )
            quickChips.forEach { chipCmd ->
                AssistChip(
                    onClick = { executeCommand(chipCmd) },
                    label = {
                        Text(
                            text = chipCmd,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp,
                            color = primaryColor
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = CyberSurface,
                        labelColor = primaryColor
                    ),
                    border = AssistChipDefaults.assistChipBorder(
                        borderColor = primaryColor.copy(alpha = 0.5f),
                        borderWidth = 1.dp,
                        enabled = true
                    ),
                    modifier = Modifier.testTag("chip_$chipCmd")
                )
            }
        }

        // Terminal Output Screen
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .border(1.dp, CyberBorder, RoundedCornerShape(4.dp))
                .background(CyberDark)
                .padding(8.dp)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize()
            ) {
                items(logs, key = { it.id }) { log ->
                    Column(modifier = Modifier.padding(vertical = 4.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "[${log.timestamp}]",
                                color = TextCyberDim,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 10.sp
                            )
                            Text(
                                text = " guest@hackmatrix:~$ ",
                                color = primaryColor,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                            Text(
                                text = log.command,
                                color = TextCyberBright,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 12.sp
                            )
                        }
                        Text(
                            text = log.output,
                            color = if (log.isError) themeMode.accentColor else primaryColor,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp,
                            lineHeight = 16.sp,
                            modifier = Modifier.padding(start = 8.dp, top = 2.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Input Prompt Box
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputCommand,
                onValueChange = {
                    inputCommand = it
                    CyberSoundSynthesizer.playKeyClick()
                },
                modifier = Modifier
                    .weight(1f)
                    .testTag("terminal_input_field"),
                placeholder = {
                    Text(
                        text = "Enter terminal command (e.g. scan, help, ls)...",
                        color = TextCyberDim,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp
                    )
                },
                leadingIcon = {
                    Text(
                        text = " > ",
                        color = primaryColor,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                },
                trailingIcon = {
                    if (inputCommand.isNotEmpty()) {
                        IconButton(onClick = { inputCommand = "" }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear Input",
                                tint = TextCyberDim
                            )
                        }
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(onSend = { executeCommand(inputCommand) }),
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

            Spacer(modifier = Modifier.width(6.dp))

            IconButton(
                onClick = { executeCommand(inputCommand) },
                enabled = !isExecuting && inputCommand.isNotBlank(),
                modifier = Modifier
                    .size(48.dp)
                    .background(primaryColor, RoundedCornerShape(4.dp))
                    .testTag("terminal_send_button")
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Execute Command",
                    tint = CyberBlack,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

private fun getCurrentTime(): String {
    val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return sdf.format(Date())
}
