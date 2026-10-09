package com.example.data.repository

import com.example.data.model.NodeType
import com.example.data.model.TechConnection

object ConnectionsRepository {

    val connections: List<TechConnection> = listOf(
        TechConnection(
            sourceId = "ls",
            sourceName = "Linux CLI & Bash",
            sourceType = NodeType.LINUX_COMMAND,
            targetId = "docker",
            targetName = "Docker Engine",
            targetType = NodeType.SOFTWARE,
            relationship = "Core Runtime & Shell",
            description = "Docker containers run lightweight Linux environments where commands like ls, ps, and grep are used for container lifecycle maintenance."
        ),
        TechConnection(
            sourceId = "docker",
            sourceName = "Docker Engine",
            sourceType = NodeType.SOFTWARE,
            targetId = "ollama",
            targetName = "Ollama Local AI",
            targetType = NodeType.SOFTWARE,
            relationship = "Containerized AI Service",
            description = "Ollama is deployed in Docker containers with NVIDIA GPU pass-through to serve local models with minimal system configuration."
        ),
        TechConnection(
            sourceId = "curl",
            sourceName = "curl Command",
            sourceType = NodeType.LINUX_COMMAND,
            targetId = "sarvam_ai",
            targetName = "Sarvam AI (India)",
            targetType = NodeType.AI_TOOL,
            relationship = "API Interconnect",
            description = "Linux shell scripts use curl to send JSON requests to Sarvam's Bulbul TTS and Sarvam-1 Indic language endpoints."
        ),
        TechConnection(
            sourceId = "krutrim_ai",
            sourceName = "Krutrim Cloud (India)",
            sourceType = NodeType.AI_TOOL,
            targetId = "kubernetes",
            targetName = "Kubernetes (K8s)",
            targetType = NodeType.SOFTWARE,
            relationship = "Sovereign Cloud Orchestration",
            description = "Krutrim Cloud provides AI clusters orchestrated via Kubernetes on Indian soil for complete data residency compliance."
        ),
        TechConnection(
            sourceId = "nmap_soft",
            sourceName = "Nmap",
            sourceType = NodeType.SOFTWARE,
            targetId = "wireshark",
            targetName = "Wireshark",
            targetType = NodeType.SOFTWARE,
            relationship = "Network Packet & Recon Chain",
            description = "Defensive teams run Nmap scans to audit open ports while simultaneously capturing packet traces in Wireshark for deep anomaly detection."
        ),
        TechConnection(
            sourceId = "nmap_soft",
            sourceName = "Nmap",
            sourceType = NodeType.SOFTWARE,
            targetId = "metasploit",
            targetName = "Metasploit Framework",
            targetType = NodeType.SOFTWARE,
            relationship = "Auditing Pipeline",
            description = "Nmap XML output feeds directly into Metasploit db_import to match open ports with potential configuration audit checks."
        ),
        TechConnection(
            sourceId = "metasploit",
            sourceName = "Metasploit",
            sourceType = NodeType.SOFTWARE,
            targetId = "postgresql",
            targetName = "PostgreSQL",
            targetType = NodeType.SOFTWARE,
            relationship = "Assessment Database",
            description = "Metasploit connects to PostgreSQL to persist workspace records, discovered hosts, loot, and vulnerability reports."
        ),
        TechConnection(
            sourceId = "git_cli",
            sourceName = "Git & GitHub CLI",
            sourceType = NodeType.SOFTWARE,
            targetId = "github_copilot",
            targetName = "GitHub Copilot",
            targetType = NodeType.AI_TOOL,
            relationship = "Developer Intelligence",
            description = "GitHub Copilot reads Git context, generates commit descriptions, and explains diffs directly within the editor terminal."
        ),
        TechConnection(
            sourceId = "cursor_ai",
            sourceName = "Cursor Pro",
            sourceType = NodeType.AI_TOOL,
            targetId = "git_cli",
            targetName = "Git Repository",
            targetType = NodeType.SOFTWARE,
            relationship = "Codebase Indexing",
            description = "Cursor indexes entire Git repositories to perform multi-file AI code modifications and automatic branch updates."
        ),
        TechConnection(
            sourceId = "ffmpeg",
            sourceName = "FFmpeg Suite",
            sourceType = NodeType.SOFTWARE,
            targetId = "elevenlabs",
            targetName = "ElevenLabs Voice AI",
            targetType = NodeType.AI_TOOL,
            relationship = "Audio Engineering Pipeline",
            description = "Engineers use FFmpeg to normalize, resample (44.1kHz), and slice raw audio before training ElevenLabs voice clones."
        ),
        TechConnection(
            sourceId = "ffmpeg",
            sourceName = "FFmpeg Suite",
            sourceType = NodeType.SOFTWARE,
            targetId = "runway_ai",
            targetName = "Runway Gen-3",
            targetType = NodeType.AI_TOOL,
            relationship = "Cinematic Post-Processing",
            description = "AI-generated video clips from Runway are stitched, color-corrected, and converted into ProRes/H.264 formats using FFmpeg pipelines."
        ),
        TechConnection(
            sourceId = "ss",
            sourceName = "ss Socket Utility",
            sourceType = NodeType.LINUX_COMMAND,
            targetId = "nginx",
            targetName = "Nginx Web Server",
            targetType = NodeType.SOFTWARE,
            relationship = "Socket Monitoring",
            description = "Linux sysadmins execute 'ss -tulpn' to verify that Nginx is properly listening on ports 80 and 443 without port collisions."
        ),
        TechConnection(
            sourceId = "burp_suite",
            sourceName = "Burp Suite Pro",
            sourceType = NodeType.SOFTWARE,
            targetId = "nginx",
            targetName = "Nginx Proxy",
            targetType = NodeType.SOFTWARE,
            relationship = "Web Gateway Interception",
            description = "Burp Suite intercepts HTTP requests targeting backend services exposed through Nginx reverse proxies to detect misconfigurations."
        ),
        TechConnection(
            sourceId = "yellow_ai",
            sourceName = "Yellow.ai (India)",
            sourceType = NodeType.AI_TOOL,
            targetId = "redis",
            targetName = "Redis Cache",
            targetType = NodeType.SOFTWARE,
            relationship = "Low-Latency Session Memory",
            description = "Conversational AI agents use Redis in-memory storage to maintain multi-turn chat dialogues across messaging sessions."
        ),
        TechConnection(
            sourceId = "bhashini",
            sourceName = "Project Bhashini (India)",
            sourceType = NodeType.AI_TOOL,
            targetId = "curl",
            targetName = "curl / REST API",
            targetType = NodeType.LINUX_COMMAND,
            relationship = "Public Service Integration",
            description = "Public service portals (like DigiLocker and UPI 123Pay) invoke Bhashini translation pipelines using curl and HTTP APIs."
        )
    )

    fun getConnectionsFor(id: String): List<TechConnection> {
        return connections.filter { it.sourceId == id || it.targetId == id }
    }
}
