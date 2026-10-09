package com.example.data.repository

import com.example.data.model.SoftwareItem

object SoftwareRepository {

    val softwares: List<SoftwareItem> = listOf(
        SoftwareItem(
            id = "docker",
            name = "Docker Engine",
            category = "डेवऑप्स व कंटेनर्स (DevOps)",
            summary = "Package applications into isolated container packages that run everywhere identically.",
            whatItsFor = "Running apps, microservices, databases, and AI servers without environment conflicts.",
            whatItsForHindi = "सरल शब्दों में: डॉकर आपके सॉफ्टवेयर और उसकी सभी जरूरी फाइलों को एक छोटे से बंद डब्बे (कंटेनर) में पैक कर देता है। फायदा यह होता है कि जो ऐप आपके कंप्यूटर पर चल रहा है, वह बिना किसी एरर के किसी भी क्लाउड सर्वर पर हूबहू चलेगा।",
            howItWorks = "Uses Linux kernel namespaces and cgroups to run isolated processes with virtually zero overhead compared to bulky VMs.",
            installCommand = "curl -fsSL https://get.docker.com | sh",
            licenseType = "Open Source (Apache 2.0)",
            isCommercialPaid = false,
            keyCommands = listOf(
                "docker run -d -p 80:80 nginx (कंटेनर चलाना)",
                "docker ps (चल रहे कंटेनर देखना)",
                "docker-compose up -d (पूरा प्रोजेक्ट चालू करना)"
            ),
            connectedTools = listOf("kubernetes", "nginx", "ollama", "postgresql"),
            docsUrl = "https://www.docker.com"
        ),
        SoftwareItem(
            id = "ollama",
            name = "Ollama Local AI",
            category = "लोकल AI मॉडल इंजन (AI)",
            summary = "Run powerful open-source Large Language Models offline on your own laptop or server.",
            whatItsFor = "Running Llama 3.2, DeepSeek, Mistral, and Qwen locally without sending sensitive data to external cloud APIs.",
            whatItsForHindi = "सरल शब्दों में: अगर आप बिना इंटरनेट या बिना चैटजीपीटी को पैसे दिए अपने खुद के लैपटॉप या ऑफिस सर्वर पर पावरफुल AI चलाना चाहते हैं, तो Ollama 1-क्लिक में आपके कंप्यूटर पर AI चालू कर देता है।",
            howItWorks = "Bundles llama.cpp into a unified local server with automated GPU acceleration (CUDA, ROCm, Metal).",
            installCommand = "curl -fsSL https://ollama.com/install.sh | sh",
            licenseType = "Open Source (MIT)",
            isCommercialPaid = false,
            keyCommands = listOf(
                "ollama run llama3.2 (मॉडल से सीधी बातचीत)",
                "ollama list (डाउनलोड किए मॉडल देखना)",
                "ollama pull qwen2.5-coder (कोडिंग मॉडल डाउनलोड करना)"
            ),
            connectedTools = listOf("docker", "curl", "python", "sarvam_ai"),
            docsUrl = "https://ollama.com"
        ),
        SoftwareItem(
            id = "nmap_soft",
            name = "Nmap Security Scanner",
            category = "नेटवर्क व साइबर सुरक्षा (CyberSec)",
            summary = "Network exploration tool and security port scanner for defensive auditing.",
            whatItsFor = "Auditing network security, finding open listening ports, and discovering active devices on local networks.",
            whatItsForHindi = "सरल शब्दों में: यह नेटवर्क का एक्स-रे करने वाला स्कैनर है। यह बताता है कि आपके वाईफाई या सर्वर में कौन-से दरवाजे (पोर्ट्स) खुले हैं और कहीं कोई अनधिकृत कंप्यूटर या हैकर तो नहीं जुड़ा हुआ है।",
            howItWorks = "Sends custom raw network packets to analyze response flags and determine OS and service banners.",
            installCommand = "sudo apt install -y nmap",
            licenseType = "Open Source (GPL)",
            isCommercialPaid = false,
            keyCommands = listOf(
                "nmap -sn 192.168.1.0/24 (नेटवर्क के सभी डिवाइसेस देखना)",
                "nmap -sV -p 80,443 target.com (वेब सर्वर वर्जन जांचना)"
            ),
            connectedTools = listOf("wireshark", "ss", "curl"),
            docsUrl = "https://nmap.org"
        ),
        SoftwareItem(
            id = "nginx",
            name = "Nginx Web Server",
            category = "वेब सर्वर व रिवर्स प्रॉक्सी (Web)",
            summary = "High-performance HTTP server, reverse proxy, and SSL/TLS termination gateway.",
            whatItsFor = "Serving websites at extreme speed and routing domain traffic to multiple backend microservices.",
            whatItsForHindi = "सरल शब्दों में: दुनिया की ज्यादातर बड़ी वेबसाइट्स (जैसे नेटफ्लिक्स, इंस्टाग्राम) Nginx का इस्तेमाल करती हैं। जब हजारों लोग एक साथ आपकी वेबसाइट खोलते हैं, तो यह ट्रैफिक को संभालकर सही सर्वर पर तेजी से भेजता है।",
            howItWorks = "Asynchronous, event-driven architecture that handles tens of thousands of concurrent connections with low memory.",
            installCommand = "sudo apt install -y nginx && sudo systemctl start nginx",
            licenseType = "Open Source (2-Clause BSD)",
            isCommercialPaid = false,
            keyCommands = listOf(
                "sudo systemctl status nginx (सर्वर की स्थिति देखना)",
                "sudo nginx -t (कॉन्फ़िगरेशन टेस्ट करना)",
                "sudo systemctl reload nginx (नया नियम लागू करना)"
            ),
            connectedTools = listOf("curl", "docker", "systemctl"),
            docsUrl = "https://nginx.org"
        ),
        SoftwareItem(
            id = "postgresql",
            name = "PostgreSQL Database",
            category = "डेटाबेस व स्टोरेज (Database)",
            summary = "World's most advanced open-source object-relational database with vector search (pgvector).",
            whatItsFor = "Safely storing bank transactions, user profiles, enterprise records, and AI vector embeddings.",
            whatItsForHindi = "सरल शब्दों में: यह दुनिया का सबसे भरोसेमंद डेटाबेस है। जब लाखों यूजर्स का डेटा, पासवर्ड और ऑर्डर्स बिना किसी नुकसान के सुरक्षित रखने हों, तो दुनिया की बड़ी कंपनियां PostgreSQL पर ही भरोसा करती हैं।",
            howItWorks = "ACID compliant transaction engine with multi-version concurrency control and pgvector extension.",
            installCommand = "sudo apt install -y postgresql postgresql-contrib",
            licenseType = "Open Source (PostgreSQL License)",
            isCommercialPaid = false,
            keyCommands = listOf(
                "sudo -u postgres psql (डेटाबेस कंसोल खोलना)",
                "\\l (सभी डेटाबेस देखना)",
                "CREATE EXTENSION vector; (AI सर्च फीचर जोड़ना)"
            ),
            connectedTools = listOf("redis", "docker", "python"),
            docsUrl = "https://www.postgresql.org"
        )
    )

    val categories: List<String> = listOf(
        "सभी (All)",
        "डेवऑप्स व कंटेनर्स (DevOps)",
        "लोकल AI मॉडल इंजन (AI)",
        "नेटवर्क व साइबर सुरक्षा (CyberSec)",
        "वेब सर्वर व रिवर्स प्रॉक्सी (Web)",
        "डेटाबेस व स्टोरेज (Database)"
    )
}
