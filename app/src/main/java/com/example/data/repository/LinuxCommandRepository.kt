package com.example.data.repository

import com.example.data.model.FlagOption
import com.example.data.model.LinuxCommand

object LinuxCommandRepository {

    val commands: List<LinuxCommand> = listOf(
        // FILE SYSTEM
        LinuxCommand(
            id = "ls",
            name = "ls",
            category = "File Operations",
            summary = "List directory contents with detailed file metadata.",
            syntax = "ls [OPTION]... [FILE]...",
            flags = listOf(
                FlagOption("-l", "Use a long listing format showing permissions, owner, size, timestamp"),
                FlagOption("-a", "Include hidden files starting with '.'"),
                FlagOption("-h", "Human-readable file sizes (e.g., 4K, 25M, 2G)"),
                FlagOption("-t", "Sort files by modification time, newest first"),
                FlagOption("-R", "Recursively list subdirectories")
            ),
            example = "ls -laSh /var/log",
            explanation = "Lists all files including hidden files in /var/log, with details, sorted by file size descending.",
            requiresRoot = false,
            simulatedOutput = """
total 248K
drwxr-xr-x 14 root root 4.0K Oct  8 10:00 .
drwxr-xr-x 11 root root 4.0K Oct  1 04:12 ..
-rw-r-----  1 root adm  124K Oct  8 09:59 auth.log
-rw-r--r--  1 root root  48K Oct  8 09:40 syslog
drwxr-xr-x  2 root root 4.0K Oct  5 12:30 nginx
-rw-r--r--  1 root root  12K Oct  7 18:02 dmesg
            """.trimIndent(),
            connectedTools = listOf("cat", "chmod", "find", "grep")
        ),
        LinuxCommand(
            id = "grep",
            name = "grep",
            category = "File Operations",
            summary = "Search pattern matching text files using regular expressions.",
            syntax = "grep [OPTIONS] PATTERN [FILE...]",
            flags = listOf(
                FlagOption("-r / -R", "Recursively search subdirectories"),
                FlagOption("-i", "Ignore case sensitivity in patterns"),
                FlagOption("-n", "Display line number along with matching lines"),
                FlagOption("-v", "Invert match (select non-matching lines)"),
                FlagOption("-E", "Interpret PATTERN as extended regular expression (regex)")
            ),
            example = "grep -rnI \"API_KEY\" /etc/app/",
            explanation = "Searches recursively for 'API_KEY' inside /etc/app, displaying line numbers while skipping binary files.",
            requiresRoot = false,
            simulatedOutput = """
/etc/app/config.json:42:  "API_KEY": "sk-proj-99xcyberkey_india_live",
/etc/app/auth.py:18:       client = Client(api_key=API_KEY)
            """.trimIndent(),
            connectedTools = listOf("sed", "awk", "find")
        ),
        LinuxCommand(
            id = "find",
            name = "find",
            category = "File Operations",
            summary = "Search for files in a directory hierarchy based on criteria.",
            syntax = "find [path] [expression]",
            flags = listOf(
                FlagOption("-name", "Match file name with wildcard support"),
                FlagOption("-type", "Filter by type (f = regular file, d = directory, l = symlink)"),
                FlagOption("-mtime", "Modified within N*24 hours ago"),
                FlagOption("-size", "Filter by file size (+100M, -10k)"),
                FlagOption("-exec", "Execute specified command on every matched item")
            ),
            example = "find /var/www -type f -name \"*.log\" -size +50M",
            explanation = "Locates all log files greater than 50 Megabytes inside /var/www directory.",
            requiresRoot = false,
            simulatedOutput = """
/var/www/access.log (78.4 MB)
/var/www/error.log (54.1 MB)
/var/www/cache/debug.log (112.0 MB)
[MATCH_COUNT: 3 files found]
            """.trimIndent(),
            connectedTools = listOf("ls", "grep", "xargs", "rm")
        ),
        LinuxCommand(
            id = "chmod",
            name = "chmod",
            category = "Security & Permissions",
            summary = "Change file mode bits (read, write, execute permissions).",
            syntax = "chmod [OPTION]... MODE[,MODE]... FILE...",
            flags = listOf(
                FlagOption("-R", "Change files and directories recursively"),
                FlagOption("+x", "Grant execute permissions to current user"),
                FlagOption("755", "Owner: rwx (7), Group: r-x (5), Others: r-x (5)"),
                FlagOption("600", "Owner: rw- (6), Group: --- (0), Others: --- (0) - strict private")
            ),
            example = "chmod 600 ~/.ssh/id_rsa && chmod +x deploy.sh",
            explanation = "Restricts SSH private key to user-only read/write, and makes deploy.sh executable.",
            requiresRoot = false,
            simulatedOutput = """
[MODE_UPDATED] ~/.ssh/id_rsa -> 0600 (-rw-------)
[MODE_UPDATED] deploy.sh     -> 0755 (-rwxr-xr-x)
            """.trimIndent(),
            connectedTools = listOf("chown", "ls", "sudo")
        ),
        LinuxCommand(
            id = "chown",
            name = "chown",
            category = "Security & Permissions",
            summary = "Change file owner and group attributes.",
            syntax = "chown [OPTION]... [OWNER][:[GROUP]] FILE...",
            flags = listOf(
                FlagOption("-R", "Operate on files and directories recursively"),
                FlagOption("-v", "Output a diagnostic for every file processed"),
                FlagOption("--reference", "Use ownership of reference file")
            ),
            example = "sudo chown -R www-data:www-data /var/www/html",
            explanation = "Assigns user 'www-data' and group 'www-data' to web server root directory.",
            requiresRoot = true,
            simulatedOutput = """
changed ownership of '/var/www/html/index.html' from root:root to www-data:www-data
changed ownership of '/var/www/html/api' from root:root to www-data:www-data
[OWNERSHIP_CHANGE_SUCCESS: 42 files updated]
            """.trimIndent(),
            connectedTools = listOf("chmod", "sudo", "useradd")
        ),
        // NETWORKING
        LinuxCommand(
            id = "curl",
            name = "curl",
            category = "Networking & Sockets",
            summary = "Transfer data from or to a server using HTTP, HTTPS, FTP, and WS protocols.",
            syntax = "curl [options] [URL...]",
            flags = listOf(
                FlagOption("-X", "Specifies custom HTTP method (GET, POST, PUT, DELETE)"),
                FlagOption("-H", "Pass custom HTTP headers (e.g. Authorization: Bearer ...)"),
                FlagOption("-d", "HTTP POST payload data (JSON, form-urlencoded)"),
                FlagOption("-I", "Fetch HTTP headers only without response body"),
                FlagOption("-s / -S", "Silent mode, suppressing progress meter but showing errors")
            ),
            example = "curl -s -X POST https://api.sarvam.ai/v1/chat -H \"Content-Type: application/json\" -d '{\"query\":\"Namaste\"}'",
            explanation = "Sends an authenticated JSON POST request to Sarvam Indic AI API endpoint.",
            requiresRoot = false,
            simulatedOutput = """
HTTP/2 200 OK
content-type: application/json; charset=utf-8
date: Wed, 08 Oct 2026 10:14:00 GMT

{
  "status": "success",
  "language": "hi-IN",
  "response": "नमस्ते! मैं आपकी किस प्रकार सहायता कर सकता हूँ?"
}
            """.trimIndent(),
            connectedTools = listOf("wget", "nmap", "python", "jq")
        ),
        LinuxCommand(
            id = "nmap",
            name = "nmap",
            category = "Networking & Sockets",
            summary = "Network exploration tool and security / port scanner.",
            syntax = "nmap [Scan Type...] [Options] {target specification}",
            flags = listOf(
                FlagOption("-sS", "TCP SYN Stealth scan (half-open, quick and evasive)"),
                FlagOption("-sV", "Probe open ports to determine service and version info"),
                FlagOption("-p", "Specify ports to scan (e.g. -p 80,443,8080 or -p 1-65535)"),
                FlagOption("-O", "Enable remote Operating System detection"),
                FlagOption("-A", "Aggressive scan (OS detection, version detection, script scanning, traceroute)")
            ),
            example = "nmap -sV -p 22,80,443 192.168.1.1",
            explanation = "Probes ports 22, 80, 443 on target IP to detect listening services and exact versions.",
            requiresRoot = true,
            simulatedOutput = """
Starting Nmap 7.94 ( https://nmap.org )
Nmap scan report for router.local (192.168.1.1)
Host is up (0.0014s latency).
PORT    STATE SERVICE  VERSION
22/tcp  open  ssh      OpenSSH 9.2p1 Debian 2+deb12u2
80/tcp  open  http     nginx 1.22.1
443/tcp open  ssl/http nginx 1.22.1
Nmap done: 1 IP address (1 host up) scanned in 1.48 seconds
            """.trimIndent(),
            connectedTools = listOf("wireshark", "tcpdump", "netstat", "ss")
        ),
        LinuxCommand(
            id = "ss",
            name = "ss",
            category = "Networking & Sockets",
            summary = "Utility to investigate network sockets and listening ports (modern netstat replacement).",
            syntax = "ss [options] [filter]",
            flags = listOf(
                FlagOption("-t", "Display TCP sockets only"),
                FlagOption("-u", "Display UDP sockets only"),
                FlagOption("-l", "Display listening sockets only"),
                FlagOption("-p", "Show process using the socket"),
                FlagOption("-n", "Do not resolve service names (show numeric ports)")
            ),
            example = "ss -tulpn",
            explanation = "Shows all listening TCP & UDP sockets with numeric port numbers and process IDs.",
            requiresRoot = true,
            simulatedOutput = """
Netid State  Recv-Q Send-Q Local Address:Port  Peer Address:Port Process
tcp   LISTEN 0      128          0.0.0.0:22         0.0.0.0:*     users:(("sshd",pid=842,fd=3))
tcp   LISTEN 0      511          0.0.0.0:80         0.0.0.0:*     users:(("nginx",pid=1120,fd=6))
tcp   LISTEN 0      128        127.0.0.1:5432       0.0.0.0:*     users:(("postgres",pid=915,fd=7))
tcp   LISTEN 0      128        127.0.0.1:11434      0.0.0.0:*     users:(("ollama",pid=2134,fd=4))
            """.trimIndent(),
            connectedTools = listOf("netstat", "lsof", "nmap", "curl")
        ),
        LinuxCommand(
            id = "tcpdump",
            name = "tcpdump",
            category = "Networking & Sockets",
            summary = "Command-line packet analyzer and network traffic capture utility.",
            syntax = "tcpdump [-i interface] [options] [filter-expression]",
            flags = listOf(
                FlagOption("-i", "Specify network interface to capture on (e.g. eth0, any)"),
                FlagOption("-n", "Don't convert host addresses to names"),
                FlagOption("-c", "Exit after receiving specified count of packets"),
                FlagOption("-w", "Write raw packet data to a .pcap capture file"),
                FlagOption("-X", "Display packet header and data in hex and ASCII")
            ),
            example = "sudo tcpdump -i eth0 -n -c 5 'tcp port 443'",
            explanation = "Captures 5 packets of TCP traffic on port 443 on interface eth0.",
            requiresRoot = true,
            simulatedOutput = """
tcpdump: verbose output suppressed, use -v[v]... for full protocol decode
listening on eth0, link-type EN10MB (Ethernet), snapshot length 262144 bytes
10:14:02.102 IP 192.168.1.45.54122 > 104.26.12.31.443: Flags [P.], seq 1:453, ack 1, win 502
10:14:02.138 IP 104.26.12.31.443 > 192.168.1.45.54122: Flags [.], ack 453, win 128
5 packets captured, 5 packets received by filter, 0 packets dropped by kernel
            """.trimIndent(),
            connectedTools = listOf("wireshark", "nmap", "ss")
        ),
        // SYSTEM ADMIN & HARDWARE
        LinuxCommand(
            id = "neofetch",
            name = "neofetch",
            category = "System & Hardware",
            summary = "CLI system information tool displaying ASCII distro art alongside specs.",
            syntax = "neofetch [options]",
            flags = listOf(
                FlagOption("--off", "Disable ASCII logo output"),
                FlagOption("--ascii_distro", "Specify which distribution logo to print"),
                FlagOption("--memory_percent", "Display memory usage percentage")
            ),
            example = "neofetch",
            explanation = "Prints OS, Kernel, Uptime, Memory, CPU, and Shell with stylized ASCII branding.",
            requiresRoot = false,
            simulatedOutput = """
        #####           user@cyberdeck
       #######          --------------
       ##O#O##          OS: Arch Linux x86_64
       #VVVVV#          Kernel: 6.10.4-zen-1-zen
     ##  VVV  ##        Uptime: 4 days, 16 hours, 22 mins
    #          ##       Packages: 942 (pacman), 14 (flatpak)
   #            ##      Shell: zsh 5.9
   #            ###     Terminal: /dev/pts/2
  QQ            #QQ     CPU: AMD Ryzen 9 7950X (32) @ 5.7GHz
  #QQ          #QQ      GPU: NVIDIA GeForce RTX 4090 24GB
   #   #    #   #       Memory: 8420MiB / 64230MiB (13%)
            """.trimIndent(),
            connectedTools = listOf("uname", "top", "free", "uptime")
        ),
        LinuxCommand(
            id = "top",
            name = "top / htop",
            category = "System & Hardware",
            summary = "Display dynamic real-time view of running processes and system resources.",
            syntax = "top [options] | htop",
            flags = listOf(
                FlagOption("-u", "Monitor only processes of specified user"),
                FlagOption("-p", "Monitor specific process IDs (PIDs)"),
                FlagOption("-d", "Specify delay time interval between screen updates in seconds")
            ),
            example = "htop",
            explanation = "Launches interactive colorful task manager with CPU bar graphs, RAM meters, and process tree.",
            requiresRoot = false,
            simulatedOutput = """
  CPU[|||||||||||||||         28.4%]   Tasks: 218, 114 thr; 2 running
  Mem[||||||||||||            8.4G/64G] Load average: 0.42 0.38 0.31
  Swp[                         0K/8G]   Uptime: 4 days, 16:22:01

  PID USER      PRI  NI  VIRT   RES   SHR S CPU% MEM%   TIME+  Command
 2134 root       20   0 14.2G  6.1G  240M S 22.0  9.5 12:44.12 ollama serve
 1120 www-data   20   0  182M   44M   18M S  4.2  0.1  1:02.40 nginx: worker
  842 root       20   0   16M  4.2M  3.1M S  0.0  0.0  0:04.18 /usr/sbin/sshd
            """.trimIndent(),
            connectedTools = listOf("ps", "kill", "free", "systemctl")
        ),
        LinuxCommand(
            id = "systemctl",
            name = "systemctl",
            category = "System & Hardware",
            summary = "Control the systemd system and service manager daemon.",
            syntax = "systemctl [OPTIONS...] COMMAND [UNIT...]",
            flags = listOf(
                FlagOption("status", "Show runtime status of service/daemon"),
                FlagOption("start / stop", "Start or terminate the specified service"),
                FlagOption("restart", "Restart the active service"),
                FlagOption("enable", "Configure service to start automatically on system boot"),
                FlagOption("list-units", "List all currently loaded systemd service units")
            ),
            example = "sudo systemctl status docker",
            explanation = "Checks if Docker container daemon is active, running, and inspects recent logs.",
            requiresRoot = true,
            simulatedOutput = """
● docker.service - Docker Application Container Engine
     Loaded: loaded (/lib/systemd/system/docker.service; enabled; vendor preset: enabled)
     Active: active (running) since Sun 2026-10-04 18:00:12 UTC; 4 days ago
       Docs: https://docs.docker.com
   Main PID: 1044 (dockerd)
      Tasks: 42
     Memory: 412.0M
     CGroup: /system.slice/docker.service
             └─1044 /usr/bin/dockerd -H fd:// --containerd=/run/containerd/containerd.sock
            """.trimIndent(),
            connectedTools = listOf("journalctl", "service", "ps", "top")
        ),
        // PROCESS MANAGEMENT
        LinuxCommand(
            id = "ps",
            name = "ps",
            category = "Process Control",
            summary = "Report a snapshot of the current processes running on the system.",
            syntax = "ps [options]",
            flags = listOf(
                FlagOption("aux", "Show all running processes for all users in BSD format"),
                FlagOption("-ef", "Show all processes in full-format standard list"),
                FlagOption("--forest", "Display hierarchical ASCII process tree")
            ),
            example = "ps aux | grep \"python\"",
            explanation = "Lists all running processes on the machine and filters for Python scripts.",
            requiresRoot = false,
            simulatedOutput = """
root       1420  1.2  3.4 1240100 224000 ?     Sl   Oct06  24:12 python3 -m ai_inference_server
hacker     2981  0.0  0.0    9120   2140 pts/1 S+   10:14   0:00 grep --color=auto python
            """.trimIndent(),
            connectedTools = listOf("top", "kill", "pkill", "grep")
        ),
        LinuxCommand(
            id = "kill",
            name = "kill / pkill",
            category = "Process Control",
            summary = "Send signals to processes (terminate, reload, interrupt).",
            syntax = "kill [-SIGNAL] PID... | pkill [options] pattern",
            flags = listOf(
                FlagOption("-9 (SIGKILL)", "Force kill immediately without cleanup (cannot be caught)"),
                FlagOption("-15 (SIGTERM)", "Polite termination request (default, allows process to save state)"),
                FlagOption("-1 (SIGHUP)", "Hangup signal, commonly causes daemons to reload config")
            ),
            example = "kill -9 1420 || pkill -f \"malicious_bot\"",
            explanation = "Force terminates process ID 1420, or terminates all processes matching pattern.",
            requiresRoot = false,
            simulatedOutput = """
[SIGNAL_SENT] SIGKILL (9) -> PID 1420
Process terminated successfully.
            """.trimIndent(),
            connectedTools = listOf("ps", "top", "killall")
        ),
        // PACKAGE MANAGERS
        LinuxCommand(
            id = "apt",
            name = "apt / dpkg",
            category = "Package Management",
            summary = "Advanced package manager for Debian and Ubuntu Linux distributions.",
            syntax = "apt [options] command [package...]",
            flags = listOf(
                FlagOption("update", "Resynchronize package index files from their sources"),
                FlagOption("upgrade", "Install newest versions of all packages currently installed"),
                FlagOption("install", "Download and install new package with its dependencies"),
                FlagOption("purge", "Remove package and delete all its configuration files")
            ),
            example = "sudo apt update && sudo apt install -y nmap wireshark",
            explanation = "Updates repository indices and automatically installs Nmap and Wireshark.",
            requiresRoot = true,
            simulatedOutput = """
Hit:1 http://deb.debian.org/debian bookworm InRelease
Reading package lists... Done
Building dependency tree... Done
The following NEW packages will be installed:
  nmap (7.94-1) wireshark (4.0.11-1)
0 upgraded, 2 newly installed, 0 to remove.
Need to get 8,410 kB of archives.
Unpacking nmap (7.94-1) ...
Setting up nmap ... Done.
[INSTALLATION_COMPLETE]
            """.trimIndent(),
            connectedTools = listOf("dpkg", "pacman", "dnf", "curl")
        ),
        LinuxCommand(
            id = "pacman",
            name = "pacman",
            category = "Package Management",
            summary = "Arch Linux package manager combining simple binary package format with easy build system.",
            syntax = "pacman <operation> [options] [targets]",
            flags = listOf(
                FlagOption("-Syu", "Full system upgrade: sync database and update all installed packages"),
                FlagOption("-S", "Install specific package from official repos"),
                FlagOption("-Rns", "Remove package, its unneeded dependencies, and configuration files"),
                FlagOption("-Ss", "Search repositories for packages matching keyword")
            ),
            example = "sudo pacman -Syu neovim tmux git",
            explanation = "Synchronizes repos, upgrades system, and installs Neovim, Tmux, and Git.",
            requiresRoot = true,
            simulatedOutput = """
:: Synchronizing package databases...
 core is up to date
 extra is up to date
resolving dependencies...
Packages (3) git-2.46.0-1  neovim-0.10.1-1  tmux-3.4-2
Total Download Size:    38.42 MiB
Total Installed Size:  142.10 MiB
:: Proceed with installation? [Y/n] Y
(3/3) checking package integrity                   [####################] 100%
(3/3) installing neovim                            [####################] 100%
            """.trimIndent(),
            connectedTools = listOf("apt", "dnf", "git")
        ),
        // ARCHIVES & DISK
        LinuxCommand(
            id = "tar",
            name = "tar",
            category = "Archives & Disk",
            summary = "Tape archive utility for creating and extracting compressed archives.",
            syntax = "tar [OPTION...] [FILE]...",
            flags = listOf(
                FlagOption("-c", "Create a new archive"),
                FlagOption("-x", "Extract files from an archive"),
                FlagOption("-z", "Filter the archive through gzip (.tar.gz)"),
                FlagOption("-v", "Verbosely list files processed"),
                FlagOption("-f", "Use archive file specified")
            ),
            example = "tar -czvf backup_source.tar.gz /var/www/project",
            explanation = "Compresses /var/www/project into a gzip compressed archive named backup_source.tar.gz.",
            requiresRoot = false,
            simulatedOutput = """
/var/www/project/
/var/www/project/src/
/var/www/project/src/main.py
/var/www/project/config.yaml
/var/www/project/.env
[ARCHIVE_CREATED] backup_source.tar.gz (compressed 18.2 MB -> 4.1 MB)
            """.trimIndent(),
            connectedTools = listOf("gzip", "zip", "rsync")
        ),
        LinuxCommand(
            id = "rsync",
            name = "rsync",
            category = "Archives & Disk",
            summary = "Fast, versatile, remote and local file-copying and synchronization tool.",
            syntax = "rsync [OPTION...] SRC... [DEST]",
            flags = listOf(
                FlagOption("-a", "Archive mode (preserves permissions, times, symlinks, recursive)"),
                FlagOption("-v", "Increase verbosity"),
                FlagOption("-z", "Compress file data during transfer"),
                FlagOption("-P", "Show progress bar and allow resuming interrupted transfers"),
                FlagOption("--delete", "Delete extraneous files from destination dir")
            ),
            example = "rsync -avzP --delete ./data/ root@192.168.1.50:/backup/data/",
            explanation = "Synchronizes local ./data directory to remote server over SSH efficiently with diff compression.",
            requiresRoot = false,
            simulatedOutput = """
sending incremental file list
dataset_v2.sqlite
      1,048,576,000 100%   98.24MB/s    0:00:10 (xfr#1, to-chk=4/10)
sent 420.12M bytes  received 1.4K bytes  41.98M bytes/sec
total size is 1.05G  speedup is 2.50
            """.trimIndent(),
            connectedTools = listOf("scp", "ssh", "tar")
        ),
        // SHELL UTILITIES & ENTERTAINMENT
        LinuxCommand(
            id = "cowsay",
            name = "cowsay",
            category = "Shell & Entertainment",
            summary = "Configurable talking cow in ASCII art for terminal fun and announcements.",
            syntax = "cowsay [options] [message]",
            flags = listOf(
                FlagOption("-b", "Borg mode cow"),
                FlagOption("-d", "Dead cow (XX eyes)"),
                FlagOption("-g", "Greedy cow ($$ eyes)"),
                FlagOption("-f", "Specify alternative ASCII cowfile (tux, dragon, etc.)")
            ),
            example = "cowsay \"Hack The Planet! Terminal Matrix Online.\"",
            explanation = "Prints an ASCII cow speaking your custom text message inside speech bubble.",
            requiresRoot = false,
            simulatedOutput = """
 ________________________________________
/ Hack The Planet! Terminal Matrix       \
\ Online.                                /
 ----------------------------------------
        \   ^__^
         \  (oo)\_______
            (__)\       )\/\
                ||----w |
                ||     ||
            """.trimIndent(),
            connectedTools = listOf("fortune", "echo", "cat")
        ),
        LinuxCommand(
            id = "xargs",
            name = "xargs",
            category = "Shell & Pipelines",
            summary = "Build and execute command lines from standard input streams.",
            syntax = "xargs [options] [command [initial-arguments]]",
            flags = listOf(
                FlagOption("-I", "Replace-str: replace occurrences of placeholder with input string"),
                FlagOption("-P", "Run up to max-procs processes concurrently (multi-core parallelism)"),
                FlagOption("-n", "Use at most max-args arguments per command line")
            ),
            example = "find . -name \"*.bak\" | xargs rm -f",
            explanation = "Pipes all matching backup files into rm command in batches for maximum efficiency.",
            requiresRoot = false,
            simulatedOutput = """
[XARGS_PIPELINE] Processed 128 items in 1 batch.
[EXEC_COMMAND] rm -f ./a.bak ./b.bak ./c.bak ...
Operation complete.
            """.trimIndent(),
            connectedTools = listOf("find", "grep", "awk")
        )
    )

    val categories: List<String> = listOf(
        "All",
        "File Operations",
        "Networking & Sockets",
        "System & Hardware",
        "Process Control",
        "Security & Permissions",
        "Package Management",
        "Archives & Disk",
        "Shell & Entertainment"
    )
}
