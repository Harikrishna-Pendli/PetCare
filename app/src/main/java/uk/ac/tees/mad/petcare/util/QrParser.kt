package uk.ac.tees.mad.petcare.utils

object QrParser {

    /**
     * Try to parse vaccine + date from QR text.
     * Returns Pair(vaccineName, dateString) or null if not recognized.
     *
     * Supported-ish formats:
     *   "Rabies: 20-12-2024"
     *   "Deworming | 10/04/2025"
     *   "Vaccine=Parvo ; Date=2025-02-18"
     */
    fun parseVaccinationData(text: String): Pair<String, String>? {
        val clean = text.trim()
        if (clean.isEmpty()) return null

        val separators = listOf(":", "|", ";", "=")

        val firstLine = clean.lines().firstOrNull() ?: clean

        for (sep in separators) {
            if (firstLine.contains(sep)) {
                val parts = firstLine.split(sep).map { it.trim() }.filter { it.isNotEmpty() }
                if (parts.size >= 2) {
                    val vaccine = parts[0]
                    val date = parts[1]
                    if (date.matches(Regex(".*\\d{4}.*"))) {
                        return vaccine to date
                    } else {
                        if (vaccine.matches(Regex(".*\\d{4}.*"))) {
                            return date to vaccine
                        }
                    }
                }
            }
        }

        val lower = clean.lowercase()
        val dateRegex = Regex("(date[:=]\\s*)([0-9\\-/\\.]{6,})", RegexOption.IGNORE_CASE)
        val vaccineRegex = Regex("(vaccine[:=]\\s*)([A-Za-z0-9 ]{3,})", RegexOption.IGNORE_CASE)

        val dateMatch = dateRegex.find(clean)
        val vaccineMatch = vaccineRegex.find(clean)

        if (vaccineMatch != null && dateMatch != null) {
            return vaccineMatch.groupValues[2].trim() to dateMatch.groupValues[2].trim()
        }

        val tokens = clean.split(Regex("\\s+|,|;|:|\\||=")).map { it.trim() }.filter { it.isNotEmpty() }
        val dateToken = tokens.firstOrNull { it.matches(Regex(".*\\d{4}.*")) }
        val vaccineToken = tokens.firstOrNull { !it.matches(Regex(".*\\d.*")) }

        if (vaccineToken != null && dateToken != null) {
            return vaccineToken to dateToken
        }

        return null
    }
}
