package com.kuartet.mbois.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CardGroupingTest {

    private fun card(id: String, categoryId: String, categoryName: String, index: Int) =
        MboisCard(id = id, name = id, categoryId = categoryId, categoryName = categoryName, cardIndex = index)

    @Test
    fun `cards are sorted by card index inside each category`() {
        val cards = listOf(
            card("kendang", "c1", "Alat Musik", 3),
            card("gong", "c1", "Alat Musik", 1),
            card("bonang", "c1", "Alat Musik", 2),
        )

        val grouped = groupCardsByCategory(cards)

        assertEquals(listOf("gong", "bonang", "kendang"), grouped.getValue("Alat Musik").map { it.id })
    }

    @Test
    fun `categories keep the order of their category id`() {
        val cards = listOf(
            card("rawon", "c3", "Makanan", 1),
            card("reog", "c2", "Tarian", 1),
            card("gong", "c1", "Alat Musik", 1),
        )

        val grouped = groupCardsByCategory(cards)

        assertEquals(listOf("Alat Musik", "Tarian", "Makanan"), grouped.keys.toList())
    }

    @Test
    fun `category id decides order before card index`() {
        val cards = listOf(
            card("b1", "c2", "B", 1),
            card("a9", "c1", "A", 9),
        )

        val grouped = groupCardsByCategory(cards)

        assertEquals(listOf("A", "B"), grouped.keys.toList())
    }

    @Test
    fun `empty input gives an empty map`() {
        assertTrue(groupCardsByCategory(emptyList()).isEmpty())
    }
}
