package com.example.data.repository

import com.example.data.model.AiOrigin
import com.example.data.model.AiTool
import com.example.data.model.PricingTier

object AiToolRepository {

    val tools: List<AiTool> = listOf(
        // INDIA AI TOOLS
        AiTool(
            id = "sarvam_ai",
            name = "Sarvam AI",
            origin = AiOrigin.INDIA,
            category = "Indic Foundational LLMs & Voice",
            tagline = "Sovereign Indic Language AI Models & Speech Infrastructure",
            description = "Bengaluru-based foundational AI research lab that built Sarvam-1 (2B parameter high-efficiency Indic LLM), Bulbul (text-to-speech), Saaras (speech recognition), and Samvaad for 10+ major Indian languages.",
            whatItsFor = "Developing localized voice bots, customer service translation, real-time transcription, and generative apps in Hindi, Tamil, Telugu, Kannada, Bengali, Marathi, and Punjabi.",
            pricingTiers = listOf(
                PricingTier("Developer Trial", "₹0 (Free Credits)", "50,000 free tokens & 30 mins audio testing", false),
                PricingTier("Pay-As-You-Go API", "₹0.15 / 1k Tokens", "Full access to Sarvam-1, Bulbul TTS & Saaras ASR", true),
                PricingTier("Enterprise Sovereign", "₹1,50,000+ / mo", "On-premise deployment, custom fine-tuning, SLA support", true)
            ),
            keyFeatures = listOf(
                "Sarvam-1 2B specialized Indic LLM outperforming 7B models on Indian benchmarks",
                "Bulbul natural sounding Indic voice synthesis with authentic local accents",
                "Saaras speech-to-text with heavy background noise resistance",
                "Full REST API and Python SDK integration with Linux servers"
            ),
            apiAvailable = true,
            connectedSoftware = listOf("python", "curl", "docker", "langchain", "ollama"),
            officialSite = "https://www.sarvam.ai"
        ),
        AiTool(
            id = "krutrim_ai",
            name = "Krutrim AI",
            origin = AiOrigin.INDIA,
            category = "Multimodal Indic AI & Cloud",
            tagline = "India's First AI Unicorn & Full-Stack AI Computing Cloud",
            description = "Founded by Bhavish Aggarwal (Ola), Krutrim represents India's sovereign AI stack. It features multilingual foundational models trained on over 2 trillion tokens, as well as Krutrim Cloud providing AI GPU clusters and developer APIs.",
            whatItsFor = "General generative chat in 22 Indian languages, code generation, enterprise document search, and hiring cloud GPUs (H100/A100) hosted in Indian data centers.",
            pricingTiers = listOf(
                PricingTier("Free Tier", "₹0 / mo", "Standard text generation with daily rate limits", false),
                PricingTier("Krutrim Pro", "₹999 / mo (~$12)", "Priority access to Krutrim-v2, voice chat, and higher rate limits", true),
                PricingTier("Krutrim Cloud GPU", "₹65 - ₹280 / GPU hr", "NVIDIA H100/L40S cloud compute rental hosted in India", true),
                PricingTier("Enterprise AI Suite", "Custom ₹ Billing", "Dedicated private instance, SLA, enterprise integration", true)
            ),
            keyFeatures = listOf(
                "Understands and responds in 22 Indian languages and 10 scripts",
                "Krutrim Cloud infrastructure with Indian data sovereignty compliance",
                "Vision and audio multimodal processing",
                "Direct API access compatible with OpenAI SDK specs"
            ),
            apiAvailable = true,
            connectedSoftware = listOf("linux_bash", "docker", "kubernetes", "python", "curl"),
            officialSite = "https://cloud.olakrutrim.com"
        ),
        AiTool(
            id = "hanooman_ai",
            name = "Hanooman AI (BharatGPT)",
            origin = AiOrigin.INDIA,
            category = "Multimodal Sovereign LLM",
            tagline = "Consortium of IIT Bombay & SML Indic Generative Ecosystem",
            description = "Developed by SML (Seetha Mahalaxmi Healthcare) in partnership with the BharatGPT consortium and IIT Bombay. Built specifically for Indian healthcare, governance, financial services, and education sectors.",
            whatItsFor = "Government services delivery, multilingual administrative summaries, clinical documentation assistance, and localized educational tutoring across 11 Indian languages.",
            pricingTiers = listOf(
                PricingTier("Community", "₹0", "Basic access to chat interface for students & researchers", false),
                PricingTier("Bharat Business", "₹1,499 / mo", "Commercial license for small businesses, API access", true),
                PricingTier("Gov & Healthcare Enterprise", "Custom Quote (₹)", "HIPAA/Ayushman Bharat compliant on-premise installation", true)
            ),
            keyFeatures = listOf(
                "Multimodal text-to-speech and speech-to-text in 11 Indian languages",
                "Healthcare diagnosis assistance and prescription extraction",
                "IIT Bombay computational research backing",
                "Zero data transfer outside Indian borders"
            ),
            apiAvailable = true,
            connectedSoftware = listOf("python", "curl", "nginx", "docker"),
            officialSite = "https://hanooman.ai"
        ),
        AiTool(
            id = "bhashini",
            name = "Project Bhashini",
            origin = AiOrigin.INDIA,
            category = "National AI Translation Mission",
            tagline = "Digital India's National Language Technology Mission (MeitY)",
            description = "The Government of India's flagship AI initiative under the Ministry of Electronics and Information Technology (MeitY). Provides sovereign open-access AI pipelines for translation, transcription (ASR), and synthesis (TTS) for Indian languages.",
            whatItsFor = "Empowering Indian startups, banks, and public portals (like UPI 123Pay, DigiLocker, PM-Kisan) with voice and language AI interfaces.",
            pricingTiers = listOf(
                PricingTier("Open Public Mission", "₹0 (Free / Open API)", "Free API keys for Indian startups, researchers, and public welfare apps", false),
                PricingTier("High-Volume Commercial", "Nominal API Tier / Grants", "Subsidized government compute allocation for high throughput", true)
            ),
            keyFeatures = listOf(
                "ULCA (Universal Language Contribution API) open repository",
                "Covers 22 scheduled official Indian languages",
                "Powers conversational UPI voice payment prompts",
                "High-accuracy dialect translation benchmarks"
            ),
            apiAvailable = true,
            connectedSoftware = listOf("curl", "python", "linux_bash", "nginx"),
            officialSite = "https://bhashini.gov.in"
        ),
        AiTool(
            id = "yellow_ai",
            name = "Yellow.ai DynamicNLP",
            origin = AiOrigin.INDIA,
            category = "Enterprise Autonomous Agent",
            tagline = "Generative AI Agents for Customer Support & Sales Automation",
            description = "Founded in Bengaluru and now globally leading enterprise customer conversation automation. Powered by proprietary DynamicNLP and LLM orchestration across 135+ languages and 35+ messaging channels.",
            whatItsFor = "Automating support tickets, customer service voice bots, lead qualification, and WhatsApp commerce workflows with human-like interactions.",
            pricingTiers = listOf(
                PricingTier("Free Sandbox", "₹0", "21-day trial with 1,000 simulated messages", false),
                PricingTier("Growth Starter", "$599 / mo (~₹50,000)", "Omnichannel web & WhatsApp bot with 10k conversations", true),
                PricingTier("Enterprise AI Agent", "$2,400+ / mo (~₹2,00,000+)", "Custom LLM agents, CRM connectors, voice synthesis & analytics", true)
            ),
            keyFeatures = listOf(
                "Zero-shot DynamicNLP training requiring zero manual intent tagging",
                "Voice AI agent with sub-second latency telephony integration",
                "Pre-built integrations with Salesforce, Zendesk, SAP, and Shopify",
                "Enterprise security with SOC 2 Type II and ISO certifications"
            ),
            apiAvailable = true,
            connectedSoftware = listOf("postman", "docker", "redis", "nginx", "curl"),
            officialSite = "https://yellow.ai"
        ),
        AiTool(
            id = "gupshup_ai",
            name = "Gupshup ACE LLM",
            origin = AiOrigin.INDIA,
            category = "Conversational Commerce AI",
            tagline = "Domain-Specific Enterprise LLMs for WhatsApp & Messaging",
            description = "Mumbai-headquartered conversational cloud unicorn that launched ACE LLM, domain-specific generative models adapted for banking, insurance, retail, and e-commerce over WhatsApp, RCS, and SMS.",
            whatItsFor = "Transforming customer engagement journeys into conversational commerce via automated WhatsApp bot flows, catalog checkout, and claims processing.",
            pricingTiers = listOf(
                PricingTier("Developer PayG", "₹0.35 / Conversation", "Pay per conversational session + WhatsApp Meta messaging fee", true),
                PricingTier("ACE Business Suite", "₹25,000 / mo base", "ACE LLM agent setup, multi-agent orchestrator, visual bot studio", true),
                PricingTier("Enterprise Sovereign", "₹1,00,000+ / mo", "Dedicated private model hosting, custom ERP connectors", true)
            ),
            keyFeatures = listOf(
                "Domain-specific fine-tuning for Indian banking (BFSI) regulations",
                "Official Meta WhatsApp Business Solution Provider (BSP) integration",
                "Generative AI guardrails preventing hallucination in financial transactions",
                "Native multi-lingual Hindi-English (Hinglish) support"
            ),
            apiAvailable = true,
            connectedSoftware = listOf("curl", "postgresql", "redis", "docker"),
            officialSite = "https://www.gupshup.io"
        ),
        AiTool(
            id = "wysa_ai",
            name = "Wysa AI",
            origin = AiOrigin.INDIA,
            category = "Healthcare & Mental Wellness AI",
            tagline = "Clinically Proven AI Conversational Companion",
            description = "Pioneered in Bengaluru by Touchkin, Wysa is an evidence-based cognitive behavioral therapy (CBT) AI chatbot trusted by millions of users and top healthcare networks globally.",
            whatItsFor = "Mental health self-care, anxiety reduction, stress management, sleep exercises, and triaging users to licensed human psychologists.",
            pricingTiers = listOf(
                PricingTier("Free Basic", "₹0", "Daily AI check-ins and core CBT breathing exercises", false),
                PricingTier("Wysa Premium", "₹599 / mo ($12.99/mo)", "Access to 100+ clinical toolpacks, guided meditation, sleep routines", true),
                PricingTier("Coaching Tier", "₹2,499 / mo ($79/mo)", "Wysa Premium plus 1-on-1 sessions with human therapists", true),
                PricingTier("Enterprise Healthcare", "Custom / Employee", "B2B employee mental wellness health plans", true)
            ),
            keyFeatures = listOf(
                "Clinically validated in multiple peer-reviewed scientific studies",
                "Complete anonymity with no mandatory personal identifiers",
                "Safety emergency crisis detection and helpline routing",
                "FDA Breakthrough Device designation"
            ),
            apiAvailable = false,
            connectedSoftware = listOf("postgresql", "docker"),
            officialSite = "https://www.wysa.io"
        ),

        // GLOBAL PAID AI TOOLS
        AiTool(
            id = "chatgpt_plus",
            name = "OpenAI ChatGPT Plus / Team",
            origin = AiOrigin.GLOBAL,
            category = "Flagship Generative AI",
            tagline = "The Global Standard for Multimodal Reasoning & Code",
            description = "OpenAI's flagship subscription offering GPT-4o, Canvas interactive code workspace, Sora video generation previews, Advanced Voice Mode, and Custom GPT builders.",
            whatItsFor = "Advanced complex reasoning, software engineering, writing, deep data analysis, document digestion, and image generation via DALL-E 3.",
            pricingTiers = listOf(
                PricingTier("ChatGPT Free", "$0 / mo", "Standard GPT-4o mini with restricted rate limits", false),
                PricingTier("ChatGPT Plus", "$20 / mo (~₹1,700)", "GPT-4o, Advanced Voice, Canvas, DALL-E 3, Sora preview", true),
                PricingTier("ChatGPT Team", "$25 / user / mo", "Workspace collaboration, admin console, no model training on data", true),
                PricingTier("ChatGPT Enterprise", "$30 - $60 / user / mo", "Unlimited high-speed GPT-4o, SOC2 compliance, 128k context", true),
                PricingTier("API Pay-As-You-Go", "$2.50 / 1M input tokens", "Direct access to gpt-4o endpoints via REST API", true)
            ),
            keyFeatures = listOf(
                "GPT-4o multimodal vision, voice, and text architecture",
                "Interactive Canvas for side-by-side coding and editing",
                "Advanced Voice Mode with realistic emotional intonation",
                "Code Interpreter sandbox running real Python code in Docker"
            ),
            apiAvailable = true,
            connectedSoftware = listOf("python", "curl", "docker", "vs_code", "langchain"),
            officialSite = "https://chatgpt.com"
        ),
        AiTool(
            id = "claude_pro",
            name = "Anthropic Claude Pro",
            origin = AiOrigin.GLOBAL,
            category = "High-Intelligence Reasoning & Coding",
            tagline = "Industry Benchmark for Coding, Research & Artifacts",
            description = "Anthropic's flagship subscription giving access to Claude 3.5 Sonnet and Opus. Known for exceptional coding abilities, nuance in technical writing, and the interactive Artifacts preview UI.",
            whatItsFor = "Full-stack software engineering, architectural system design, legal contract auditing, and synthesizing hundreds of pages of documentation.",
            pricingTiers = listOf(
                PricingTier("Free Tier", "$0 / mo", "Standard Claude 3.5 Sonnet with strict message caps", false),
                PricingTier("Claude Pro", "$20 / mo (~₹1,700)", "5x more usage, priority access during peak hours, Claude 3.5 Sonnet", true),
                PricingTier("Claude Team", "$25 / seat / mo", "Shared project workspaces, centralized billing, 200k context", true),
                PricingTier("API Tokens", "$3.00 / 1M input tokens", "Direct Claude 3.5 Sonnet API via Anthropic Console", true)
            ),
            keyFeatures = listOf(
                "Claude 3.5 Sonnet #1 benchmark rank in coding and logic evaluations",
                "Interactive Artifacts window for live React/HTML/SVG rendering",
                "200,000 token context window (equivalent to full novels)",
                "Constitutional AI training minimizing deceptive outputs"
            ),
            apiAvailable = true,
            connectedSoftware = listOf("vs_code", "python", "curl", "git", "docker"),
            officialSite = "https://claude.ai"
        ),
        AiTool(
            id = "cursor_ai",
            name = "Cursor Pro",
            origin = AiOrigin.GLOBAL,
            category = "AI-First Code Editor",
            tagline = "The AI Code Editor Built on VS Code for 10x Developer Speed",
            description = "Engineered by Anysphere, Cursor is a high-speed fork of VS Code with deeply integrated AI models. Includes Composer for multi-file codebase generation, intelligent Tab completion, and indexing.",
            whatItsFor = "Full-stack refactoring, autonomous multi-file generation, repository-wide codebase Q&A, and terminal debugging.",
            pricingTiers = listOf(
                PricingTier("Hobbyist Free", "$0 / mo", "2,000 completions, 50 slow premium requests", false),
                PricingTier("Cursor Pro", "$20 / mo (~₹1,700)", "500 fast premium requests (Claude 3.5 Sonnet & GPT-4o), unlimited completions", true),
                PricingTier("Business Tier", "$40 / user / mo", "Enforced privacy mode, centralized team billing, SAML SSO", true)
            ),
            keyFeatures = listOf(
                "Cursor Composer (Cmd+I) editing dozens of files simultaneously across project",
                "Codebase Indexing analyzing entire Git repos for high-precision context",
                "Multi-model switcher: Claude 3.5 Sonnet, GPT-4o, Cursor-Small",
                "Terminal integration debugging bash errors in real time"
            ),
            apiAvailable = false,
            connectedSoftware = listOf("vs_code", "git", "linux_bash", "docker", "npm"),
            officialSite = "https://cursor.com"
        ),
        AiTool(
            id = "midjourney",
            name = "Midjourney v6.1",
            origin = AiOrigin.GLOBAL,
            category = "Generative Art & Visual Design",
            tagline = "State-of-the-Art Photorealistic & Concept Art AI Generator",
            description = "The premier generative visual engine capable of rendering hyperrealistic photography, 3D concept design, logos, and UI mockups from text prompts via Discord and Web UI.",
            whatItsFor = "Creative direction, marketing visual generation, game asset prototyping, architectural visualization, and concept branding.",
            pricingTiers = listOf(
                PricingTier("Basic Plan", "$10 / mo (~₹840)", "3.3 hrs fast GPU / month (~200 images), personal bot chat", true),
                PricingTier("Standard Plan", "$30 / mo (~₹2,500)", "15 hrs fast GPU / month + Unlimited Relax GPU generations", true),
                PricingTier("Pro Plan", "$60 / mo (~₹5,000)", "30 hrs fast GPU, Stealth Mode (private images), 12 concurrent jobs", true),
                PricingTier("Mega Plan", "$120 / mo (~₹10,000)", "60 hrs fast GPU, Stealth Mode, maximum concurrency", true)
            ),
            keyFeatures = listOf(
                "Photorealistic textures, skin details, lighting, and typography rendering",
                "Style Tuner and parameter controls (--ar, --v, --stylize, --sref)",
                "Web generation portal with inpainting and outpainting pan tools",
                "Community gallery showcase and prompt exploration"
            ),
            apiAvailable = false,
            connectedSoftware = listOf("docker", "web_browser"),
            officialSite = "https://midjourney.com"
        ),
        AiTool(
            id = "runway_ai",
            name = "Runway Gen-3 Alpha",
            origin = AiOrigin.GLOBAL,
            category = "Generative Cinema & Video AI",
            tagline = "High-Fidelity AI Video Generation & Motion Synthesis",
            description = "Industry-standard generative video platform powering Hollywood visual effects, advertising, and creative studios with Gen-3 Alpha text-to-video and image-to-video models.",
            whatItsFor = "Cinematic video creation, commercial VFX generation, motion brush camera control, and lip-sync character animations.",
            pricingTiers = listOf(
                PricingTier("Free Plan", "$0", "125 one-time credits, standard resolution exports", false),
                PricingTier("Standard Plan", "$15 / user / mo", "625 credits / mo (~125 sec of video), up to 4K upscaling", true),
                PricingTier("Pro Plan", "$35 / user / mo", "2,250 credits / mo, custom AI voice training, ProRes export", true),
                PricingTier("Unlimited Plan", "$95 / user / mo", "Unlimited relaxed video generations + 2,250 fast credits", true)
            ),
            keyFeatures = listOf(
                "Gen-3 Alpha photorealistic video with precise physical dynamics",
                "Camera motion control (pan, zoom, orbit, crane) and Motion Brush",
                "Text-to-Video, Image-to-Video, and Video-to-Video transformations",
                "Enterprise API for programmatic video asset generation"
            ),
            apiAvailable = true,
            connectedSoftware = listOf("python", "curl", "ffmpeg", "docker"),
            officialSite = "https://runwayml.com"
        ),
        AiTool(
            id = "elevenlabs",
            name = "ElevenLabs Voice AI",
            origin = AiOrigin.GLOBAL,
            category = "Generative Voice & Speech Synthesis",
            tagline = "Ultra-Realistic AI Voice Cloning & Text-to-Speech Engine",
            description = "The industry standard for human-grade expressive voice generation, instant voice cloning from short audio samples, speech-to-speech transformation, and AI sound effects.",
            whatItsFor = "Audiobook narration, video game character dubbing, YouTube voiceovers, automated customer telephony agents, and localization into 30+ languages.",
            pricingTiers = listOf(
                PricingTier("Free Plan", "$0 / mo", "10,000 characters/mo (~10 mins), 3 custom voices", false),
                PricingTier("Starter Plan", "$5 / mo (1st mo $1)", "30,000 characters/mo, Instant Voice Cloning, commercial license", true),
                PricingTier("Creator Plan", "$22 / mo (~₹1,850)", "100,000 characters/mo, Professional Voice Cloning, 192kbps audio", true),
                PricingTier("Pro Plan", "$99 / mo (~₹8,300)", "500,000 characters/mo, priority latency, 44.1kHz studio output", true)
            ),
            keyFeatures = listOf(
                "Expressive contextual inflection capturing humor, whispering, or sorrow",
                "Instant Voice Cloning from a 60-second audio snippet",
                "Conversational AI SDK for building sub-200ms voice agents",
                "Dubbing Studio automatically translating videos while retaining original voice"
            ),
            apiAvailable = true,
            connectedSoftware = listOf("python", "curl", "ffmpeg", "docker"),
            officialSite = "https://elevenlabs.io"
        ),
        AiTool(
            id = "github_copilot",
            name = "GitHub Copilot",
            origin = AiOrigin.GLOBAL,
            category = "AI Developer Pair Programmer",
            tagline = "The World's Most Deployed AI Coding Assistant",
            description = "Jointly developed by GitHub and OpenAI. Directly integrates into VS Code, Neovim, JetBrains, and Visual Studio to provide ghost-text code completions, chat assistance, and CLI explanation.",
            whatItsFor = "Autocomplete boilerplate code, unit test generation, security vulnerability fixing, pull request summaries, and terminal command lookups (`gh copilot suggest`).",
            pricingTiers = listOf(
                PricingTier("Copilot Individual", "$10 / mo ($100/yr)", "Full code completion, Copilot Chat, CLI terminal support", true),
                PricingTier("Copilot Business", "$19 / user / mo", "IP indemnity, organization-wide policy management, audit logs", true),
                PricingTier("Copilot Enterprise", "$39 / user / mo", "Fine-tuned on private internal repos, Bing search indexing", true)
            ),
            keyFeatures = listOf(
                "Multi-editor compatibility: VS Code, Neovim, IntelliJ, Android Studio",
                "Copilot Workspace environment for planning tasks from issue to PR",
                "CLI assistant translating natural language into Linux commands",
                "Zero data retention policies on commercial tiers"
            ),
            apiAvailable = false,
            connectedSoftware = listOf("git", "vs_code", "linux_bash", "neovim"),
            officialSite = "https://github.com/features/copilot"
        ),
        AiTool(
            id = "perplexity_pro",
            name = "Perplexity Pro",
            origin = AiOrigin.GLOBAL,
            category = "AI Search & Deep Research",
            tagline = "Conversational Search Engine with Live Verified Citations",
            description = "Combines real-time web indexing with frontier AI models (Claude 3.5, GPT-4o, Sonar). Delivers synthesized answers with interactive footnotes, mathematical computation, and document search.",
            whatItsFor = "Academic research, competitive intelligence, code bug troubleshooting, market research, and replacing standard search engine results.",
            pricingTiers = listOf(
                PricingTier("Standard Free", "$0 / mo", "Unlimited quick search queries", false),
                PricingTier("Perplexity Pro", "$20 / mo ($200/yr)", "300+ Pro queries/day, Claude 3.5 Sonnet & GPT-4o picker, file upload analysis", true),
                PricingTier("Enterprise Pro", "$40 / seat / mo", "SOC2 compliance, internal document search, enterprise privacy", true)
            ),
            keyFeatures = listOf(
                "Pro Search with interactive follow-up clarification questions",
                "Model toggle between Claude 3.5 Sonnet, GPT-4o, and Perplexity Sonar",
                "Spaces feature for organized collaborative research collections",
                "Live citations linking directly to authoritative web sources"
            ),
            apiAvailable = true,
            connectedSoftware = listOf("curl", "python", "docker"),
            officialSite = "https://www.perplexity.ai"
        )
    )

    val categories: List<String> = listOf(
        "All",
        "India AI Innovation",
        "Global Leading AI",
        "Indic Foundational LLMs & Voice",
        "Enterprise Autonomous Agent",
        "AI-First Code Editor",
        "Generative Art & Visual Design",
        "Generative Cinema & Video AI",
        "Healthcare & Mental Wellness AI"
    )
}
