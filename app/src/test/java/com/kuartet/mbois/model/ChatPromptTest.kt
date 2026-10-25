package com.kuartet.mbois.model

import org.junit.Assert.assertEquals
import org.junit.Test

class ChatPromptTest {

    private val card = MboisCard(id = "ketipung", name = "Ketipung", categoryName = "Alat Musik")

    @Test
    fun `template placeholders are filled with the card name and category`() {
        val template = "Kamu pemandu budaya. Jelaskan {card_name} dari kategori {category_name}."

        assertEquals(
            "Kamu pemandu budaya. Jelaskan Ketipung dari kategori Alat Musik.",
            buildSystemInstruction(template, card)
        )
    }

    @Test
    fun `every occurrence of a placeholder is replaced`() {
        val template = "{card_name}, {card_name}"

        assertEquals("Ketipung, Ketipung", buildSystemInstruction(template, card))
    }

    @Test
    fun `template without placeholders is returned unchanged`() {
        assertEquals("Jawab singkat.", buildSystemInstruction("Jawab singkat.", card))
    }

    @Test
    fun `model name from Firestore is used when set`() {
        assertEquals("gemini-3-flash", resolveModelName(AiConfig(modelName = "gemini-3-flash")))
    }

    @Test
    fun `empty model name falls back to the default model`() {
        assertEquals(DEFAULT_MODEL_NAME, resolveModelName(AiConfig(modelName = "")))
    }
}
