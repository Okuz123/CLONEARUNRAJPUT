package com.example.data.repository

import com.example.data.model.FlagOption
import com.example.data.model.LinuxCommand

object LinuxCommandRepository {

    val commands: List<LinuxCommand> = listOf(
        LinuxCommand(
            id = "ls",
            name = "ls",
            category = "फाइल ऑपरेशन (File Ops)",
            summary = "List directory contents with detailed file metadata.",
            summaryHindi = "फोल्डर में मौजूद सभी फाइलों और फोल्डर्स की सूची देखना।",
            syntax = "ls [OPTION]... [FILE]...",
            flags = listOf(
                FlagOption("-l", "विस्तृत लिस्ट: परमिशन, ओनर, साइज और तारीख देखना"),
                FlagOption("-a", "छुपी हुई (Hidden) फाइल्स को भी दिखाना"),
                FlagOption("-h", "फाइल का साइज आसानी से समझने योग्य फॉर्मेट (4K, 25M, 2G) में दिखाना"),
                FlagOption("-t", "सबसे नई फाइलों को सबसे ऊपर रखना")
            ),
            example = "ls -laSh /var/log",
            explanation = "Lists all files including hidden ones, showing file size in readable format, sorted largest first.",
            explanationHindi = "सरल शब्दों में: जब आपको देखना हो कि आपके फोल्डर में कौन-सी फाइल्स हैं, उनका साइज कितना है, और कोई छुपी हुई फाइल तो नहीं है, तब आप ls -laSh कमांड का उपयोग करते हैं।",
            requiresRoot = false,
            simulatedOutput = """
total 248K
drwxr-xr-x 14 root root 4.0K Oct  9 10:00 .
-rw-r-----  1 root adm  124K Oct  9 09:59 auth.log
-rw-r--r--  1 root root  48K Oct  9 09:40 syslog
drwxr-xr-x  2 root root 4.0K Oct  9 12:30 nginx
            """.trimIndent(),
            connectedTools = listOf("cat", "chmod", "find", "grep")
        ),
        LinuxCommand(
            id = "grep",
            name = "grep",
            category = "फाइल ऑपरेशन (File Ops)",
            summary = "Search pattern matching text files using regular expressions.",
            summaryHindi = "फाइलों के अंदर किसी शब्द या कोड को तेजी से खोजना।",
            syntax = "grep [OPTIONS] PATTERN [FILE...]",
            flags = listOf(
                FlagOption("-r", "सभी सब-फोल्डर्स में अंदर तक खोजना (Recursive)"),
                FlagOption("-i", "अक्षरों के बड़े-छोटे होने (Case) का भेद नजरअंदाज करना"),
                FlagOption("-n", "जिस लाइन पर शब्द मिला, उस लाइन का नंबर भी दिखाना"),
                FlagOption("-v", "जो लाइनें मेल नहीं खातीं, केवल उन्हें दिखाना")
            ),
            example = "grep -rnI \"API_KEY\" /etc/app/",
            explanation = "Searches recursively for 'API_KEY' inside the project directory, skipping binary files.",
            explanationHindi = "सरल शब्दों में: अगर आपके पास 100 फाइलों का प्रोजेक्ट है और आपको ढूंढना है कि पासवर्ड या 'API_KEY' किस फाइल की कौन-सी लाइन में लिखी है, तो grep 1 सेकंड में लाइन नंबर के साथ खोज देता है।",
            requiresRoot = false,
            simulatedOutput = """
/etc/app/config.json:42:  "API_KEY": "sk-proj-99x_sarvam_india_live",
/etc/app/auth.py:18:       client = Client(api_key=API_KEY)
            """.trimIndent(),
            connectedTools = listOf("sed", "awk", "find")
        ),
        LinuxCommand(
            id = "curl",
            name = "curl",
            category = "नेटवर्किंग व API (Networking)",
            summary = "Transfer data from or to a server using HTTP, HTTPS, and WebSockets.",
            summaryHindi = "इंटरनेट से डाटा डाउनलोड करना या AI API को डेटा भेजना।",
            syntax = "curl [options] [URL...]",
            flags = listOf(
                FlagOption("-X", "HTTP मेथड चुनना (GET, POST, PUT, DELETE)"),
                FlagOption("-H", "हेडर पास करना (जैसे API ऑथेंटिकेशन टोकन)"),
                FlagOption("-d", "भेजे जाने वाला JSON डाटा"),
                FlagOption("-I", "केवल हेडर रिस्पॉन्स देखना")
            ),
            example = "curl -X POST https://api.sarvam.ai/v1/chat -H \"Content-Type: application/json\" -d '{\"msg\":\"नमस्ते\"}'",
            explanation = "Sends a POST request to an Indian AI API endpoint with JSON payload.",
            explanationHindi = "सरल शब्दों में: जब भी आप टर्मिनल या सर्वर से किसी AI मॉडल (जैसे सर्वम AI या चैटजीपीटी) को सवाल भेजकर जवाब मंगाना चाहते हैं, तो curl कमांड का उपयोग सबसे ज्यादा होता है।",
            requiresRoot = false,
            simulatedOutput = """
HTTP/2 200 OK
content-type: application/json; charset=utf-8

{
  "status": "success",
  "language": "hi-IN",
  "response": "नमस्ते! मैं आपकी किस प्रकार सहायता कर सकता हूँ?"
}
            """.trimIndent(),
            connectedTools = listOf("wget", "nmap", "python")
        ),
        LinuxCommand(
            id = "chmod",
            name = "chmod",
            category = "सिक्योरिटी व परमिशन (Security)",
            summary = "Change file mode bits (read, write, execute permissions).",
            summaryHindi = "फाइल को पढ़ने, लिखने या चलाने की परमिशन (हक) बदलना।",
            syntax = "chmod [OPTION]... MODE FILE...",
            flags = listOf(
                FlagOption("+x", "फाइल को रन/एग्जीक्यूट करने की परमिशन देना"),
                FlagOption("755", "ओनर को सब हक (rwx), बाकी लोगों को सिर्फ पढ़ने-चलाने का"),
                FlagOption("600", "केवल ओनर को पढ़ने-लिखने का हक (सीक्रेट फाइल्स हेतु)")
            ),
            example = "chmod +x deploy.sh && chmod 600 ~/.ssh/id_rsa",
            explanation = "Makes script executable and protects private SSH key.",
            explanationHindi = "सरल शब्दों में: अगर आप कोई शेल स्क्रिप्ट या सर्वर प्रोग्राम चलाते हैं और एरर आता है 'Permission Denied', तो chmod +x लिखकर आप कंप्यूटर को आदेश देते हैं कि इस फाइल को चलने दो।",
            requiresRoot = false,
            simulatedOutput = """
[MODE_UPDATED] deploy.sh -> 0755 (-rwxr-xr-x)
[MODE_UPDATED] ~/.ssh/id_rsa -> 0600 (-rw-------)
            """.trimIndent(),
            connectedTools = listOf("chown", "sudo")
        ),
        LinuxCommand(
            id = "top",
            name = "top / htop",
            category = "सिस्टम मॉनिटर (System Monitor)",
            summary = "Display real-time view of running processes, CPU, and RAM consumption.",
            summaryHindi = "कंप्यूटर का टास्क मैनेजर - कौन-सा प्रोग्राम कितना CPU और RAM खा रहा है।",
            syntax = "top | htop",
            flags = listOf(
                FlagOption("-u", "केवल खास यूजर के प्रोग्राम देखना"),
                FlagOption("-p", "खास प्रोसेस ID (PID) को मॉनिटर करना")
            ),
            example = "htop",
            explanation = "Interactive visual task manager showing CPU cores, RAM, and live processes.",
            explanationHindi = "सरल शब्दों में: जब आपका सर्वर या कंप्यूटर धीमा चलने लगे, तो htop दबाते ही रंग-बिरंगा चार्ट आ जाता है जो तुरंत बता देता है कि कौन सा ऐप सारा रैम और प्रोसेसर खींच रहा है।",
            requiresRoot = false,
            simulatedOutput = """
  CPU[|||||||||||||||         28.4%]   Tasks: 218 running
  Mem[||||||||||||            8.4G/64G] Uptime: 4 days, 16:22:01
  PID USER      CPU% MEM%  Command
 2134 root      22.0  9.5  ollama serve (Local AI)
 1120 www-data   4.2  0.1  nginx: worker process
            """.trimIndent(),
            connectedTools = listOf("ps", "kill", "free")
        ),
        LinuxCommand(
            id = "tar",
            name = "tar",
            category = "आर्काइव व बैकअप (Archives)",
            summary = "Tape archive tool for compressing and extracting .tar.gz archives.",
            summaryHindi = "फाइलों को जिप/कंप्रेस करना और एक्सट्रैक्ट (अनजिप) करना।",
            syntax = "tar [OPTION...] [FILE]...",
            flags = listOf(
                FlagOption("-c", "नया जिप आर्काइव बनाना (Create)"),
                FlagOption("-x", "जिप फाइल से सारा डाटा बाहर निकालना (Extract)"),
                FlagOption("-z", "Gzip कंप्रेशन का इस्तेमाल करना (.tar.gz)"),
                FlagOption("-v", "स्क्रीन पर फाइल्स के नाम दिखाना (Verbose)"),
                FlagOption("-f", "फाइल का नाम निर्दिष्ट करना")
            ),
            example = "tar -xzvf project_backup.tar.gz",
            explanation = "Extracts compressed archive into current directory.",
            explanationHindi = "सरल शब्दों में: लिनक्स में जब आप कोई सॉफ्टवेयर या बैकअप डाउनलोड करते हैं, तो वह .tar.gz फॉर्मेट में होता है। उसे अनपैक करने के लिए tar -xzvf कमांड का उपयोग किया जाता है।",
            requiresRoot = false,
            simulatedOutput = """
project_backup/
project_backup/app/
project_backup/src/main.py
[EXTRACTION COMPLETE: 42 files extracted]
            """.trimIndent(),
            connectedTools = listOf("gzip", "zip", "rsync")
        )
    )

    val categories: List<String> = listOf(
        "सभी (All)",
        "फाइल ऑपरेशन (File Ops)",
        "नेटवर्किंग व API (Networking)",
        "सिक्योरिटी व परमिशन (Security)",
        "सिस्टम मॉनिटर (System Monitor)",
        "आर्काइव व बैकअप (Archives)"
    )
}
