package com.example.data.repository

import com.example.data.model.SoftwareItem

object SoftwareRepository {

    val softwares: List<SoftwareItem> = listOf(
        // CYBERSECURITY & AUDITING
        SoftwareItem(
            id = "nmap_soft",
            name = "Nmap (Network Mapper)",
            category = "Cybersecurity & Recon",
            summary = "The gold standard open-source utility for network discovery and security vulnerability auditing.",
            whatItsFor = "Used by network administrators and defensive security teams to inventory network devices, identify open listening ports, discover running services/versions, and test firewall rules.",
            howItWorks = "Sends raw network packets using custom IP frames to determine which hosts are available on the network, what services they offer, and what operating systems they run.",
            installCommand = "sudo apt update && sudo apt install -y nmap",
            licenseType = "Open Source (Nmap Public Source License / GPL)",
            isCommercialPaid = false,
            keyCommands = listOf(
                "nmap -sV -sC -p- 192.168.1.1 (Full port scan with default scripts)",
                "nmap -sn 192.168.1.0/24 (Subnet ping sweep live host discovery)",
                "nmap --script vuln 10.0.0.5 (Check known CVE vulnerabilities)"
            ),
            connectedTools = listOf("wireshark", "tcpdump", "metasploit", "ss", "linux_bash"),
            docsUrl = "https://nmap.org"
        ),
        SoftwareItem(
            id = "wireshark",
            name = "Wireshark Packet Analyzer",
            category = "Cybersecurity & Recon",
            summary = "The world's foremost network protocol analyzer for deep inspection of live traffic and capture files.",
            whatItsFor = "Analyzing packet streams in microscopic detail, troubleshooting slow network connections, debugging cryptographic TLS handshakes, and investigating malicious intrusions.",
            howItWorks = "Hooks into network interfaces using libpcap/WinPcap to capture raw frames promiscuously and decodes hundreds of network communication protocols in real time.",
            installCommand = "sudo apt install -y wireshark tshark",
            licenseType = "Open Source (GNU GPL v2)",
            isCommercialPaid = false,
            keyCommands = listOf(
                "tshark -i eth0 -f \"tcp port 80\" -Y \"http.request\"",
                "wireshark capture_dump.pcap",
                "tshark -r traffic.pcap -z io,phs (Protocol hierarchy statistics)"
            ),
            connectedTools = listOf("tcpdump", "nmap", "curl", "linux_bash"),
            docsUrl = "https://www.wireshark.org"
        ),
        SoftwareItem(
            id = "metasploit",
            name = "Metasploit Framework",
            category = "Cybersecurity & Auditing",
            summary = "The world's most widely used penetration testing platform and exploit development framework.",
            whatItsFor = "Enables security researchers and authorized red teams to verify vulnerabilities, manage security assessments, and conduct defense posture verification.",
            howItWorks = "Provides a modular database of verified exploits, payloads (such as Meterpreter), auxiliary scanners, and post-exploitation modules written in Ruby.",
            installCommand = "curl https://raw.githubusercontent.com/rapid7/metasploit-omnibus/master/config/templates/metasploit-framework-wrappers/msfupdate.erb > msfinstall && chmod +x msfinstall && ./msfinstall",
            licenseType = "Open Source (BSD) / Metasploit Pro (Commercial)",
            isCommercialPaid = true,
            keyCommands = listOf(
                "msfconsole (Launch interactive console)",
                "use auxiliary/scanner/portscan/tcp",
                "set RHOSTS 192.168.1.100 && run",
                "search type:exploit platform:linux"
            ),
            connectedTools = listOf("nmap", "postgresql", "wireshark", "ruby", "linux_bash"),
            docsUrl = "https://www.metasploit.com"
        ),
        SoftwareItem(
            id = "burp_suite",
            name = "Burp Suite Professional",
            category = "Cybersecurity & Auditing",
            summary = "Leading web vulnerability scanner, intercepting proxy, and security testing toolkit.",
            whatItsFor = "Intercepting and tampering with HTTP/WebSocket requests, detecting OWASP Top 10 vulnerabilities (SQLi, XSS, SSRF, CSRF), and automated security scanning.",
            howItWorks = "Acts as a man-in-the-middle HTTP/HTTPS proxy between the tester's browser and target web application, providing tools like Repeater, Intruder, and Decoder.",
            installCommand = "Download Linux .sh installer from PortSwigger and execute with sh burpsuite_pro_linux.sh",
            licenseType = "Commercial Paid ($449 / user / year) / Free Community Edition",
            isCommercialPaid = true,
            keyCommands = listOf(
                "burpsuite & (Launch GUI proxy)",
                "Configure browser proxy to 127.0.0.1:8080",
                "Burp Intruder: Payload spray testing",
                "Burp Repeater: Manual request manipulation"
            ),
            connectedTools = listOf("postman", "curl", "nginx", "wireshark"),
            docsUrl = "https://portswigger.net/burp"
        ),
        SoftwareItem(
            id = "ghidra",
            name = "Ghidra Reverse Engineering",
            category = "Reverse Engineering",
            summary = "NSA-developed open-source software reverse engineering (SRE) suite with decompiler.",
            whatItsFor = "Analyzing compiled binary files, firmware, malware reverse engineering, disassembly, and reconstructing high-level C/C++ source code from machine code.",
            howItWorks = "Translates assembly code into an intermediate representation (P-Code) and runs advanced data-flow analysis to generate clean decompiled C code.",
            installCommand = "sudo apt install -y openjdk-17-jdk && wget https://github.com/NationalSecurityAgency/ghidra/releases/download/.../ghidra.zip && unzip ghidra.zip",
            licenseType = "Open Source (Apache 2.0)",
            isCommercialPaid = false,
            keyCommands = listOf(
                "./ghidraRun (Launch interactive analysis GUI)",
                "analyzeHeadless <project_path> -import <binary> -postScript <script.py>"
            ),
            connectedTools = listOf("linux_bash", "python", "docker"),
            docsUrl = "https://ghidra-sre.org"
        ),
        SoftwareItem(
            id = "john_the_ripper",
            name = "John the Ripper",
            category = "Cybersecurity & Auditing",
            summary = "High-speed multi-algorithm password security auditor and hash analysis tool.",
            whatItsFor = "Auditing system password strength by attempting to recover hashed passwords using dictionary rules, wordlists, and brute-force combinatorics.",
            howItWorks = "Optimized vectorized CPU and GPU algorithms supporting hundreds of hash and cipher types (Unix crypt, MD5, SHA-256, Kerberos, ZIP/PDF hashes).",
            installCommand = "sudo apt install -y john",
            licenseType = "Open Source (GPL v2)",
            isCommercialPaid = false,
            keyCommands = listOf(
                "john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt",
                "john --show hashes.txt",
                "unshadow /etc/passwd /etc/shadow > combined_hashes.txt"
            ),
            connectedTools = listOf("linux_bash", "chmod", "sudo"),
            docsUrl = "https://www.openwall.com/john"
        ),

        // DEVOPS, SYSTEM & CONTAINERS
        SoftwareItem(
            id = "docker",
            name = "Docker Engine & Compose",
            category = "DevOps & Cloud",
            summary = "Platform for developing, shipping, and running applications in lightweight isolated containers.",
            whatItsFor = "Packaging applications and all their dependencies into a standardized container unit that runs predictably across developer laptops, cloud VMs, and Kubernetes.",
            howItWorks = "Uses Linux kernel namespaces, cgroups, and overlay filesystems to run isolated processes without the overhead of full hypervisor virtual machines.",
            installCommand = "curl -fsSL https://get.docker.com | sh && sudo systemctl enable --now docker",
            licenseType = "Open Source (Apache 2.0) / Docker Desktop Commercial Tiers ($5 - $24/user/mo)",
            isCommercialPaid = false,
            keyCommands = listOf(
                "docker run -d -p 80:80 --name web nginx:alpine",
                "docker-compose up -d --build",
                "docker exec -it <container_id> /bin/bash",
                "docker ps -a && docker system prune -af"
            ),
            connectedTools = listOf("kubernetes", "nginx", "linux_bash", "postgresql", "sarvam_ai", "ollama"),
            docsUrl = "https://www.docker.com"
        ),
        SoftwareItem(
            id = "kubernetes",
            name = "Kubernetes (K8s)",
            category = "DevOps & Cloud",
            summary = "Open-source system for automating deployment, scaling, and management of containerized applications.",
            whatItsFor = "Managing complex microservice fleets with automatic rolling updates, health check self-healing, horizontal autoscaling, and cloud load balancing.",
            howItWorks = "Clusters worker nodes governed by a control plane (kube-apiserver, etcd, kube-scheduler, kube-controller-manager) organizing containers into Pods.",
            installCommand = "curl -LO \"https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl\" && sudo install kubectl /usr/local/bin/",
            licenseType = "Open Source (Apache 2.0)",
            isCommercialPaid = false,
            keyCommands = listOf(
                "kubectl get pods -A -o wide",
                "kubectl apply -f deployment.yaml",
                "kubectl logs -f deployment/ai-service -n production",
                "kubectl scale deployment/api-server --replicas=5"
            ),
            connectedTools = listOf("docker", "nginx", "terraform", "linux_bash"),
            docsUrl = "https://kubernetes.io"
        ),
        SoftwareItem(
            id = "nginx",
            name = "Nginx Web Server",
            category = "Web & Servers",
            summary = "High-performance HTTP server, reverse proxy, load balancer, and SSL/TLS termination gateway.",
            whatItsFor = "Serving static content at ultra-high throughput, routing incoming web traffic across backend microservices, managing SSL certificates, and protecting APIs.",
            howItWorks = "Asynchronous, event-driven, non-blocking architecture handling tens of thousands of concurrent network connections per worker process with minimal RAM.",
            installCommand = "sudo apt install -y nginx && sudo systemctl start nginx",
            licenseType = "Open Source (2-Clause BSD) / NGINX Plus Commercial ($3,000+ / yr)",
            isCommercialPaid = false,
            keyCommands = listOf(
                "sudo nginx -t (Test configuration syntax)",
                "sudo systemctl reload nginx",
                "cat /etc/nginx/sites-available/default",
                "tail -f /var/log/nginx/access.log"
            ),
            connectedTools = listOf("curl", "docker", "certbot", "systemctl", "linux_bash"),
            docsUrl = "https://nginx.org"
        ),
        SoftwareItem(
            id = "postgresql",
            name = "PostgreSQL Database",
            category = "Databases & Storage",
            summary = "The world's most advanced open-source object-relational database system.",
            whatItsFor = "Storing critical relational business data, supporting complex SQL queries, JSON document storage, geospatial data (PostGIS), and vector embeddings (pgvector).",
            howItWorks = "Multi-Version Concurrency Control (MVCC), write-ahead logging (WAL), strong ACID transaction guarantees, and extensive plugin extensions.",
            installCommand = "sudo apt install -y postgresql postgresql-contrib",
            licenseType = "Open Source (PostgreSQL License)",
            isCommercialPaid = false,
            keyCommands = listOf(
                "sudo -u postgres psql (Access interactive database terminal)",
                "\\l (List all databases)",
                "\\dt (List all tables in current database)",
                "CREATE EXTENSION vector; (Enable AI embedding searches)"
            ),
            connectedTools = listOf("redis", "docker", "python", "systemctl"),
            docsUrl = "https://www.postgresql.org"
        ),
        SoftwareItem(
            id = "redis",
            name = "Redis In-Memory Store",
            category = "Databases & Storage",
            summary = "In-memory data structure store used as a distributed database, cache, message broker, and streaming engine.",
            whatItsFor = "Sub-millisecond latency caching, session state storage, rate-limiting counters, publish/subscribe messaging queues, and AI prompt caching.",
            howItWorks = "Stores entire working datasets directly in RAM with optional background disk persistence snapshots (RDB/AOF), providing single-threaded non-blocking throughput.",
            installCommand = "sudo apt install -y redis-server && sudo systemctl start redis",
            licenseType = "Source Available (RSALv2/SSPLv1)",
            isCommercialPaid = false,
            keyCommands = listOf(
                "redis-cli ping (Replies PONG)",
                "redis-cli set \"session_user_99\" \"active\" EX 3600",
                "redis-cli get \"session_user_99\"",
                "redis-cli monitor"
            ),
            connectedTools = listOf("postgresql", "docker", "python", "nginx"),
            docsUrl = "https://redis.io"
        ),
        SoftwareItem(
            id = "ollama",
            name = "Ollama Local AI Runner",
            category = "AI Infrastructure",
            summary = "Get up and running with large language models locally on Linux, macOS, and Windows.",
            whatItsFor = "Running open-weights LLMs (Llama 3.2, DeepSeek, Mistral, Gemma 2, Qwen) privately offline without sending data to external cloud APIs.",
            howItWorks = "Bundles llama.cpp into a unified daemon serving OpenAI-compatible REST API endpoints, utilizing GPU acceleration (CUDA, ROCm, Metal) automatically.",
            installCommand = "curl -fsSL https://ollama.com/install.sh | sh",
            licenseType = "Open Source (MIT)",
            isCommercialPaid = false,
            keyCommands = listOf(
                "ollama run llama3.2 (Download and start interactive chat)",
                "ollama pull qwen2.5-coder",
                "ollama list",
                "ollama serve"
            ),
            connectedTools = listOf("linux_bash", "curl", "python", "docker", "sarvam_ai"),
            docsUrl = "https://ollama.com"
        ),
        SoftwareItem(
            id = "git_cli",
            name = "Git & GitHub CLI",
            category = "Developer Tools",
            summary = "Distributed version control system for tracking changes in source code during software development.",
            whatItsFor = "Branching, merging code, tracking revision histories, collaborating on codebases with remote teams, and managing automated CI/CD pipelines.",
            howItWorks = "Content-addressable file system that snapshots file trees cryptographically using SHA-1/SHA-256 hashes in a directed acyclic graph (DAG).",
            installCommand = "sudo apt install -y git gh",
            licenseType = "Open Source (GPL v2)",
            isCommercialPaid = false,
            keyCommands = listOf(
                "git clone <repo_url>",
                "git checkout -b feature/cyber-matrix",
                "git commit -am \"feat: terminal emulator\"",
                "git log --oneline --graph --decorate",
                "gh pr create --title \"New Feature\" --body \"Details\""
            ),
            connectedTools = listOf("github_copilot", "cursor_ai", "vs_code", "linux_bash"),
            docsUrl = "https://git-scm.com"
        ),
        SoftwareItem(
            id = "ffmpeg",
            name = "FFmpeg Multimedia Suite",
            category = "Developer Tools",
            summary = "The leading multimedia framework capable of decoding, encoding, transcoding, muxing, and filtering video and audio.",
            whatItsFor = "Converting video formats, extracting audio tracks, resizing resolutions, streaming live feeds, and preparing training audio for speech AI models.",
            howItWorks = "Massive collection of C codecs and libav* libraries executing lightning-fast pipeline operations via command line.",
            installCommand = "sudo apt install -y ffmpeg",
            licenseType = "Open Source (LGPL / GPL)",
            isCommercialPaid = false,
            keyCommands = listOf(
                "ffmpeg -i input.mp4 -vn -ar 44100 -ac 2 output.mp3",
                "ffmpeg -i input.mov -vcodec h264 -acodec aac output.mp4",
                "ffmpeg -i video.mp4 -vf \"fps=10,scale=480:-1\" output.gif"
            ),
            connectedTools = listOf("runway_ai", "elevenlabs", "linux_bash", "python"),
            docsUrl = "https://ffmpeg.org"
        )
    )

    val categories: List<String> = listOf(
        "All",
        "Cybersecurity & Recon",
        "Cybersecurity & Auditing",
        "Reverse Engineering",
        "DevOps & Cloud",
        "Web & Servers",
        "Databases & Storage",
        "AI Infrastructure",
        "Developer Tools"
    )
}
