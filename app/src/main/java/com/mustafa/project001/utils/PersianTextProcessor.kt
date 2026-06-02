package com.mustafa.project001.utils

object PersianTextProcessor {

    // List of common Persian stop words
    private val stopWords = setOf(
        "از", "به", "در", "را", "با", "که", "این", "آن", "برای", "و", "اما",
        "ولی", "اگر", "چون", "تا", "هر", "بود", "است", "شد", "نیز", "هم",
        "یک", "دو", "سه", "بسیار", "خیلی", "باید", "شاید", "می", "نمی"
    )

    // Simple Stemming: Removes common suffixes
    private val suffixes = listOf("تر", "ترین", "‌ها", "ها", "می‌", "انه", "یت", "ان")

    // Define multi-word tokens that should be treated as single units
    private val multiWordTokens = listOf(
        "خداوند ج",
        "محمد ص",
        "قرانکریم",
        "ابوبکرصدیق",
        "ختم قران شریف",
        "آیت شریف",
        "انجیل شریف",
        "نمازجنازه",
        "عیدقربان",
        "نمازقضا",
        "کفاره روزه",
        "فدیه روزه",
        "خوش آمدید",
        "چطور هستی",
        "فرق نمی‌کند",
        "معذرت خواستن",
        "دوستت دارم",
        "بی اتفاقی",
        "خوش اخلاق",
        "بداخلاق",
        "به من چی",
        "طفل شیرخوار",
        "مادر کلان",
        "پدر کلان",
        "انگشتان دست",
        "ساق پا",
        "کف پا",
        "قفس سینه",
        "کف دست",
        "بند دست",
        "ستون فقرات",
        "بجلک پا",
        "ساعت جیبی",
        "ناخن گیر",
        "روغن موی",
        "شانه موی",
        "چپن لباس",
        "زیر پیراهنی",
        "گل موی",
        "ساعت دستی",
        "دستمال بینی",
        "بطری خورد",
        "کاغذ تشناب",
        "بکس جیبی",
        "ساعت دیواری",
        "خاک انداز",
        "باطله دانی",
        "تیل خاک",
        "اتاق خواب",
        "چراغ دستی",
        "تخت خواب",
        "نل آب",
        "جوال دوز",
        "برس بوت",
        "برس دندان",
        "کوچ وچوکی",
        "بخاری دیزلی",
        "بخاری برقی",
        "تل تایپ",
        "ماشین تایپستی",
        "کمان شبکه",
        "پل شبکه",
        "رنگ روغنی",
        "آببازی",
        "گدی پران",
        "پینگ پانگ",
        "ریسمان بازی",
        "گرگ و بره",
        "دنده کلک",
        "کمره فلم برداری",
        "ماشین موتر",
        "وسایط نقلیه",
        "چهار راهی",
        "شاهراه ترافیکی",
        "سرای موتر",
        "کراچی دستی",
        "تیل دیزل",
        "بطری موتر",
        "طیاره جت",
        "موتر لاری",
        "ریل برقی",
        "راه آهن",
        "اده موتر",
        "تانک تیل",
        "میدان هوایی",
        "پیاده رو",
        "باغ وحش",
        "معاینه خانه",
        "تیل فروشی",
        "ماموریت پولیس",
        "میدان بازی",
        "آثار قدیمه",
        "در مقابل",
        "قسمت تحتانی",
        "ازین طرف به اینطرف",
        "تمام شب",
        "نیمه شب",
        "سال قمری",
        "مضمون تاریخ",
        "تمام روز",
        "وقت آمدن",
        "مزه دار",
        "قد کوتاه",
        "نیمه شنوا",
        "قیمت بها",
        "کم عرض",
        "تیز و برنده",
        "پیشانی باز"
    )

    /**
     * Main processing function - combines multi-word token detection,
     * stop word removal, and stemming
     */
    fun processText(input: String): List<String> {
        if (input.isBlank()) return emptyList()

        // Step 1: First extract multi-word tokens
        val tokensWithMultiWords = extractMultiWordTokens(input)

        // Step 2: Process each token (remove stop words, apply stemming)
        return tokensWithMultiWords
            .map { it.lowercase() }
            .filter { it !in stopWords }
            .map { stem(it) }
            .filter { it.isNotBlank() }
    }

    /**
     * Extract multi-word tokens from input text
     * This is the core function that handles phrases like "خداوند ج" as single units
     */
    private fun extractMultiWordTokens(input: String): List<String> {
        if (input.isBlank()) return emptyList()

        val result = mutableListOf<String>()
        var remaining = input.trim()

        while (remaining.isNotEmpty()) {
            var matched = false

            // Try to match multi-word tokens first (longest first)
            val sortedTokens = multiWordTokens.sortedByDescending { it.length }

            for (token in sortedTokens) {
                if (remaining.startsWith(token)) {
                    result.add(token)
                    remaining = remaining.substring(token.length).trimStart()
                    matched = true
                    break
                }
            }

            // If no multi-word match, take the first single word
            if (!matched) {
                val spaceIndex = remaining.indexOf(' ')
                val word = if (spaceIndex == -1) {
                    remaining
                } else {
                    remaining.substring(0, spaceIndex)
                }
                result.add(word)
                remaining = remaining.substring(word.length).trimStart()
            }
        }

        return result
    }

    /**
     * Stemming: Removes common suffixes from words
     */
    private fun stem(word: String): String {
        var currentWord = word

        // Simple suffix stripping logic
        suffixes.forEach { suffix ->
            if (currentWord.endsWith(suffix) && currentWord.length > suffix.length) {
                currentWord = currentWord.substring(0, currentWord.length - suffix.length)
            }
        }

        // Remove prefix "می‌"
        if (currentWord.startsWith("می‌")) {
            currentWord = currentWord.replace("می‌", "")
        }

        return currentWord
    }

    /**
     * Process text WITHOUT stop word removal and stemming
     * Use this for exact word matching against your video dictionary
     */
    fun processTextExact(input: String): List<String> {
        if (input.isBlank()) return emptyList()
        return extractMultiWordTokens(input)
    }

    /**
     * Process text with multi-word tokens only, no filtering
     * Alternative method using while loop
     */
    fun processTextAdvanced(input: String): List<String> {
        if (input.isBlank()) return emptyList()

        var text = input.trim()
        val result = mutableListOf<String>()

        // Sort multi-word tokens by length (longest first)
        val sortedTokens = multiWordTokens.sortedByDescending { it.length }

        while (text.isNotEmpty()) {
            var matched = false

            for (token in sortedTokens) {
                if (text.startsWith(token)) {
                    result.add(token)
                    text = text.substring(token.length).trimStart()
                    matched = true
                    break
                }
            }

            if (!matched) {
                // Take single word
                val parts = text.split(" ", limit = 2)
                result.add(parts[0])
                text = if (parts.size > 1) parts[1].trimStart() else ""
            }
        }

        return result
    }

    /**
     * Check if a word/term is a multi-word token
     */
    fun isMultiWordToken(word: String): Boolean {
        return multiWordTokens.contains(word)
    }

    /**
     * Get all multi-word tokens (for debugging or UI display)
     */
    fun getAllMultiWordTokens(): List<String> {
        return multiWordTokens
    }
}