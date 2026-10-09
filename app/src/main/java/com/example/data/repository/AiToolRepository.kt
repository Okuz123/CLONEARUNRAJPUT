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
            category = "भारतीय भाषा AI व वॉइस इंजन",
            tagline = "भारत की अपनी 10+ भाषाओं का सुपरफास्ट AI मॉडल (बेंगलुरु)",
            description = "Bengaluru-based sovereign AI lab that engineered Sarvam-1 (2B Indic LLM), Bulbul (natural Indian voice TTS), and Saaras (speech recognition) optimized for Indian languages with high accuracy.",
            whatItsFor = "Building voice bots, speech transcription, and language translation in Hindi, Tamil, Telugu, Bengali, Kannada, Marathi, Punjabi, Gujarati, and Malayalam.",
            whatItsForHindi = "सरल हिंदी में समझें: यह भारत का अपना AI है जो आपकी भाषा (हिंदी, मराठी, तमिल, गुजराती आदि) में बिल्कुल इंसानों जैसी आवाज में बात कर सकता है। बैंक, कस्टमर केयर, कॉलेज और मोबाइल ऐप्स में भारतीय भाषाओं के वॉइस रोबोट बनाने के लिए यह सबसे बेहतरीन टूल है।",
            pricingTiers = listOf(
                PricingTier("मुफ्त ट्रायल (Free Trial)", "₹0 (50,000 टोकन)", "स्टूडेंट्स और टेस्टर्स के लिए 30 मिनट वॉइस टेस्टिंग बिल्कुल फ्री", false),
                PricingTier("Pay-As-You-Go API", "₹0.15 / 1k Tokens", "डेवलपर्स के लिए Sarvam-1 मॉडल, Bulbul वॉइस व Saaras स्पीच API", true),
                PricingTier("Enterprise Plan", "₹1,50,000+ / माह", "भारतीय कंपनियों के लिए प्राइवेट सर्वर, कस्टम ट्रेनिंग व 24x7 सपोर्ट", true)
            ),
            keyFeatures = listOf(
                "Sarvam-1: 2B लाइटवेट मॉडल जो विदेशी 7B मॉडल्स से भी तेज चलता है",
                "Bulbul: एकदम असली भारतीय लहजे (Accents) में साफ व नेचुरल आवाज",
                "Saaras: शोरगुल में भी भारतीय भाषाओं को सही-सही सुनकर लिखने वाला AI",
                "भारतीय डेवलपर्स के लिए आसान REST API व Python सपोर्ट"
            ),
            apiAvailable = true,
            connectedSoftware = listOf("python", "curl", "docker", "langchain", "ollama"),
            officialSite = "https://www.sarvam.ai"
        ),
        AiTool(
            id = "krutrim_ai",
            name = "Krutrim AI (Ola)",
            origin = AiOrigin.INDIA,
            category = "मल्टीमॉडल AI व भारतीय क्लाउड",
            tagline = "भारत का पहला स्वदेशी AI यूनिकॉर्न (भावेश अग्रवाल - Ola)",
            description = "India's sovereign AI computing cloud and foundational LLM trained on 2+ trillion tokens of Indian and global knowledge, hosting Indian data within domestic borders.",
            whatItsFor = "Multilingual generative chat across 22 Indian languages, programming assistance, document summarization, and hiring sovereign NVIDIA H100 GPU clusters in India.",
            whatItsForHindi = "सरल हिंदी में समझें: यह ओला (Ola) द्वारा बनाया गया भारत का सबसे बड़ा AI मॉडल और सुपरकंप्यूटर क्लाउड है। आप इसमें 22 भारतीय भाषाओं में निबंध लिख सकते हैं, कोडिंग करवा सकते हैं, और भारतीय डाटा सेंटर में सुपरफास्ट AI GPU किराए पर ले सकते हैं।",
            pricingTiers = listOf(
                PricingTier("Free User", "₹0 / माह", "दैनिक लिमिट के साथ टेक्स्ट चैट और सवाल-जवाब", false),
                PricingTier("Krutrim Pro", "₹999 / माह (~$12)", "सुपरफास्ट Krutrim-v2 मॉडल, वॉइस चैट, अनलिमिटेड प्रॉम्प्ट्स", true),
                PricingTier("Krutrim Cloud GPU", "₹65 - ₹280 / GPU घंटा", "भारतीय डाटा सेंटर में NVIDIA H100 और L40S GPU क्लाउड रेंटल", true),
                PricingTier("Enterprise Sovereign", "कस्टम कॉर्पोरेट ₹", "बैंकों और सरकारों के लिए भारतीय जमीन पर सुरक्षित प्राइवेट AI", true)
            ),
            keyFeatures = listOf(
                "22 भारतीय भाषाओं और 10 लिपियों (Scripts) की गहरी समझ",
                "पूरी तरह भारत के नियमों के तहत सुरक्षित डाटा स्टोरेज",
                "तस्वीरें देखकर जवाब देने वाला विजन AI (Multimodal)",
                "OpenAI SDK कम्पैटिबल API ताकि कोडिंग में मिनटों में जुड़ जाए"
            ),
            apiAvailable = true,
            connectedSoftware = listOf("linux_bash", "docker", "kubernetes", "python", "curl"),
            officialSite = "https://cloud.olakrutrim.com"
        ),
        AiTool(
            id = "hanooman_ai",
            name = "Hanooman AI (BharatGPT)",
            origin = AiOrigin.INDIA,
            category = "सार्वजनिक व स्वास्थ्य AI",
            tagline = "IIT बॉम्बे व SML कंसोर्टियम का जन-कल्याणकारी AI",
            description = "Developed by SML in collaboration with the BharatGPT consortium and IIT Bombay, engineered for India's healthcare, governance, agriculture, and educational sectors.",
            whatItsFor = "Empowering rural healthcare, extracting doctor prescriptions, drafting government welfare schemes, and tutoring in regional dialects.",
            whatItsForHindi = "सरल हिंदी में समझें: IIT बॉम्बे और भारत के शोधकर्ताओं द्वारा मिलकर बनाया गया AI मॉडल है। इसका उद्देश्य भारतीय डॉक्टरों को पर्चे समझने, किसानों को मौसम व फसल सलाह देने, और सरकारी योजनाओं की जानकारी आम लोगों की अपनी भाषा में उपलब्ध कराना है।",
            pricingTiers = listOf(
                PricingTier("नागरिक व छात्र (Citizen)", "₹0 (मुफ्त)", "छात्रों और आम जनता के लिए जानकारी हासिल करने हेतु फ्री", false),
                PricingTier("Bharat Business", "₹1,499 / माह", "छोटे व्यापारियों और स्टार्टअप्स के लिए वाणिज्यिक API", true),
                PricingTier("Govt & Healthcare", "कस्टम बजट (₹)", "अस्पतालों और सरकारी विभागों के लिए ऑन-प्रिमाइसेस सिक्योर सेटअप", true)
            ),
            keyFeatures = listOf(
                "11 भारतीय भाषाओं में आवाज व टेक्स्ट दोनों में बात करने की क्षमता",
                "आयुष्मान भारत और भारतीय स्वास्थ्य मानकों के अनुकूल",
                "आईआईटी बॉम्बे के सुपरकंप्यूटिंग रिसर्च का मजबूत आधार",
                "भारत से बाहर जीरो डाटा ट्रांसफर - 100% संप्रभु AI"
            ),
            apiAvailable = true,
            connectedSoftware = listOf("python", "curl", "nginx", "docker"),
            officialSite = "https://hanooman.ai"
        ),
        AiTool(
            id = "bhashini",
            name = "Project Bhashini (MeitY)",
            origin = AiOrigin.INDIA,
            category = "डिजिटल इंडिया भाषा मिशन",
            tagline = "भारत सरकार (इलेक्ट्रॉनिक्स व आईटी मंत्रालय) का राष्ट्रीय अनुवाद AI",
            description = "The Government of India's flagship AI mission powering UPI 123Pay voice payments, DigiLocker translation, and public governance in 22 constitutional Indian languages.",
            whatItsFor = "Integrating automatic voice prompts, text translation, and speech-to-text into Indian fintech, education, and citizen service apps.",
            whatItsForHindi = "सरल हिंदी में समझें: यह भारत सरकार का आधिकारिक AI प्रोजेक्ट है। अगर आप बिना इंटरनेट आवाज से UPI पेमेंट करते हैं या सरकारी वेबसाइट को अपनी भाषा में बदलते हैं, तो उसके पीछे भाषिणी AI ही काम करता है। भारतीय डेवलपर्स को यह मुफ्त में भाषा API उपलब्ध कराता है।",
            pricingTiers = listOf(
                PricingTier("ओपन मिशन (Public Mission)", "₹0 (सरकारी फ्री API)", "भारतीय स्टार्टअप्स, छात्रों और सामाजिक ऐप्स के लिए फ्री कीज", false),
                PricingTier("हाई-वॉल्यूम कमर्शियल", "सरकारी सब्सिडी / ग्रांट्स", "लाखों ट्रांजैक्शन करने वाली कंपनियों के लिए विशेष कोटा", true)
            ),
            keyFeatures = listOf(
                "22 आधिकारिक भारतीय संविधानिक भाषाओं का पूर्ण सपोर्ट",
                "आवाज से UPI पेमेंट (UPI 123Pay) को पावर देने वाला AI",
                "भारतीय बोलियों और उच्चारणों को सही पहचानने में अव्वल",
                "सरकार के ओपन डाटा और ULCA रिपॉजिटरी से जुड़ा हुआ"
            ),
            apiAvailable = true,
            connectedSoftware = listOf("curl", "python", "linux_bash", "nginx"),
            officialSite = "https://bhashini.gov.in"
        ),
        AiTool(
            id = "yellow_ai",
            name = "Yellow.ai DynamicNLP",
            origin = AiOrigin.INDIA,
            category = "कस्टमर सर्विस स्वायत्त AI",
            tagline = "बेंगलुरु से शुरू हुआ विश्व का नंबर-1 ऑटोमेटेड कस्टमर केयर AI",
            description = "Founded in Bengaluru, Yellow.ai is an enterprise leader in generative autonomous customer support bots, serving Fortune 500 companies in 135+ languages across WhatsApp and Web.",
            whatItsFor = "Automating support tickets, answering refund requests, WhatsApp shopping bots, and human-like telephone AI agents.",
            whatItsForHindi = "सरल हिंदी में समझें: जब आप जोमैटो, फ्लिपकार्ट या एयरलाइंस के व्हाट्सएप पर चैट करके तुरंत मदद पाते हैं, तो 90% संभावना है कि वहां Yellow.ai काम कर रहा है। यह बिना किसी इंसान के ग्राहकों के सवालों के सही उत्तर तुरंत देता है।",
            pricingTiers = listOf(
                PricingTier("फ्री सैंडबॉक्स", "₹0", "21 दिनों के लिए 1,000 मैसेजेस टेस्ट करने का फ्री एक्सेस", false),
                PricingTier("ग्रोथ स्टार्टर", "$599 / माह (~₹50,000)", "वेबसाइट व व्हाट्सएप बोट, 10,000 एक्टिव बातचीत प्रति माह", true),
                PricingTier("एंटरप्राइज सूट", "$2,400+ / माह (~₹2,00,000+)", "कस्टम वॉइस बॉट, CRM इंटीग्रेशन, 24x7 ऑटोमेशन व एनालिटिक्स", true)
            ),
            keyFeatures = listOf(
                "DynamicNLP: बिना ट्रेनिंग के इंसानी बातचीत के मकसद को समझना",
                "एक सेकंड से भी कम में फोन कॉल पर नेचुरल आवाज में जवाब",
                "व्हाट्सएप, फेसबुक मैसेंजर, और वेबसाइट पर एक साथ लाइव",
                "SOC 2 व ISO 27001 सर्टिफाइड हाई-सिक्योरिटी सिस्टम"
            ),
            apiAvailable = true,
            connectedSoftware = listOf("postman", "docker", "redis", "nginx", "curl"),
            officialSite = "https://yellow.ai"
        ),

        // GLOBAL PAID AI TOOLS
        AiTool(
            id = "chatgpt_plus",
            name = "OpenAI ChatGPT Plus",
            origin = AiOrigin.GLOBAL,
            category = "जनरेटिव AI व रीजनिंग",
            tagline = "दुनिया का सबसे प्रसिद्ध AI - GPT-4o, वॉइस व कैनवास युक्त",
            description = "OpenAI's flagship subscription with GPT-4o, Canvas interactive code workspace, DALL-E 3 image generation, and emotional Voice Mode.",
            whatItsFor = "Coding complex software, drafting legal/academic research, creative writing, visual design, and real-time voice conversations.",
            whatItsForHindi = "सरल हिंदी में समझें: दुनिया का सबसे मशहूर AI चैटबॉट। इसके पेड वर्जन (प्लस) में आपको सबसे स्मार्ट मॉडल GPT-4o, बोलकर बात करने का एडवांस्ड वॉइस मोड, और कोडिंग व फोटो बनाने की पूरी ताकत मिलती है।",
            pricingTiers = listOf(
                PricingTier("ChatGPT Free", "$0 / माह (फ्री)", "GPT-4o मिनी मॉडल के साथ बेसिक सवाल-जवाब", false),
                PricingTier("ChatGPT Plus", "$20 / माह (~₹1,700/माह)", "फुल GPT-4o, एडवांस्ड वॉइस, कैनवास कोडिंग, DALL-E फोटो जनरेशन", true),
                PricingTier("ChatGPT Team", "$25 / यूजर / माह (~₹2,100)", "ऑफिस टीम वर्कस्पेस, कंपनी डाटा मॉडल ट्रेनिंग में इस्तेमाल नहीं होता", true),
                PricingTier("API Pay-As-You-Go", "$2.50 / 10 लाख टोकन", "डेवलपर्स के लिए सीधे अपने ऐप में जोड़ने हेतु टोकन बिलिंग", true)
            ),
            keyFeatures = listOf(
                "GPT-4o: टेक्स्ट, आवाज और फोटो तीनों को एक साथ प्रोसेस करता है",
                "कैनवास एडिटर: कोड या आर्टिकल को स्क्रीन पर सीधे एडिट करने की सुविधा",
                "कोड इंटरप्रेटर: पायथन कोड चलाकर एक्सेल शीट और ग्राफ तुरंत बना देना",
                "लाखों कस्टम GPTs का उपयोग करने का मौका"
            ),
            apiAvailable = true,
            connectedSoftware = listOf("python", "curl", "docker", "vs_code", "langchain"),
            officialSite = "https://chatgpt.com"
        ),
        AiTool(
            id = "claude_pro",
            name = "Anthropic Claude Pro",
            origin = AiOrigin.GLOBAL,
            category = "हाई-इंटेलिजेंस कोडिंग व रिसर्च",
            tagline = "सॉफ्टवेयर इंजीनियर्स का पसंदीदा AI - Claude 3.5 Sonnet",
            description = "Anthropic's flagship model ranking #1 globally for programming, logic, and deep analytical reasoning, with interactive Artifacts preview.",
            whatItsFor = "End-to-end full stack development, debugging complex architectures, analyzing massive PDFs and financial reports.",
            whatItsForHindi = "सरल हिंदी में समझें: कोडिंग और तार्किक सोचने में Claude दुनिया में सबसे नंबर-1 माना जाता है। डेवलपर्स इसका उपयोग पूरा ऐप या वेबसाइट कोड लिखवाने और अपनी गलतियों (Bugs) को तुरंत सुधारने के लिए करते हैं।",
            pricingTiers = listOf(
                PricingTier("Claude Free", "$0 / माह", "Claude 3.5 Sonnet कुछ सीमित संदेशों के साथ", false),
                PricingTier("Claude Pro", "$20 / माह (~₹1,700/माह)", "5 गुना अधिक उपयोग सीमा, पीक ऑवर्स में प्राथमिकता, आर्टिफैक्ट्स", true),
                PricingTier("Claude Team", "$25 / यूजर / माह", "टीम शेयरिंग, 200,000 टोकन का विशाल कॉन्टेक्स्ट विंडो", true)
            ),
            keyFeatures = listOf(
                "Claude 3.5 Sonnet: कोडिंग बेंचमार्क में दुनिया का नंबर-1 स्कोर",
                "आर्टिफैक्ट्स: स्क्रीन पर लाइव React व HTML वेबसाइट तुरंत चलाकर दिखाना",
                "200,000 टोकन क्षमता: पूरी की पूरी किताब एक बार में पढ़कर उत्तर देना",
                "ईमानदार और सटीक उत्तर, कम से कम गलतियां (Hallucinations)"
            ),
            apiAvailable = true,
            connectedSoftware = listOf("vs_code", "python", "curl", "git", "docker"),
            officialSite = "https://claude.ai"
        ),
        AiTool(
            id = "cursor_ai",
            name = "Cursor Pro",
            origin = AiOrigin.GLOBAL,
            category = "AI कोड एडिटर",
            tagline = "VS Code पर आधारित AI कोडिंग एडिटर जो डेवलपर्स की स्पीड 10x करता है",
            description = "Engineered by Anysphere, Cursor is an AI-first code editor fork of VS Code with deep repo indexing and multi-file Composer editing.",
            whatItsFor = "Writing full codebases, refactoring dozens of project files in parallel, finding and fixing errors in terminal outputs.",
            whatItsForHindi = "सरल हिंदी में समझें: यह सॉफ्टवेयर डेवलपर्स के लिए बनाया गया जादुई एडिटर है। आप बस बोलें कि आपको ऐप में क्या फीचर चाहिए, यह आपके प्रोजेक्ट की 10 अलग-अलग फाइलों में एक साथ सही कोड लिखकर खुद टेस्ट कर देता है।",
            pricingTiers = listOf(
                PricingTier("हॉबी फ्री", "$0 / माह", "2,000 ऑटो-कम्प्लीशन कोड सुझाव फ्री", false),
                PricingTier("Cursor Pro", "$20 / माह (~₹1,700/माह)", "500 फास्ट Claude 3.5 व GPT-4o रिक्वेस्ट, अनलिमिटेड ऑटो-कम्प्लीट", true),
                PricingTier("Business", "$40 / यूजर / माह", "गोपनीयता सुरक्षा, कंपनी कोड की प्राइवेसी गारंटी, टीम बिलिंग", true)
            ),
            keyFeatures = listOf(
                "Composer (Cmd+I): पूरे प्रोजेक्ट की फाइल्स में एक साथ कोड लिखना",
                "पूरे गिट रिपॉजिटरी को स्कैन करके प्रोजेक्ट के हर हिस्से को समझना",
                "Claude 3.5 Sonnet और GPT-4o के बीच 1-क्लिक में स्विच करना",
                "टर्मिनल एरर पर क्लिक करते ही एरर का सही समाधान दे देना"
            ),
            apiAvailable = false,
            connectedSoftware = listOf("vs_code", "git", "linux_bash", "docker", "npm"),
            officialSite = "https://cursor.com"
        )
    )

    val categories: List<String> = listOf(
        "सभी (All)",
        "🇮🇳 भारत निर्मित AI",
        "🌐 ग्लोबल AI टूल्स",
        "भारतीय भाषा AI व वॉइस इंजन",
        "मल्टीमॉडल AI व भारतीय क्लाउड",
        "कस्टमर सर्विस स्वायत्त AI",
        "हाई-इंटेलिजेंस कोडिंग व रिसर्च"
    )
}
