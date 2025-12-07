package uk.ac.tees.mad.petcare.utils

object QrParser {

    // Supported formats:
    // Rabies: 20-12-2024
    // Deworming | 10/04/2025
    // Vaccine=Parvo ; Date=2025-02-18
    fun parseVaccinationData(text: String): Pair<String, String>? {

        val clean = text.trim()
        val parts = clean.split(":", "|", ";", "=")
            .map { it.trim() }
            .filter { it.isNotBlank() }

        if (parts.size < 2) return null

        val vaccine = parts[0]
        val date = parts[1]
        val validDate = date.matches(Regex(".*\\d{4}.*"))

        return if (validDate) {
            vaccine to date
        } else null
    }
}
