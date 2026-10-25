package com.kuartet.mbois.navigation

import org.junit.Assert.assertEquals
import org.junit.Test

class ScreenRouteTest {

    @Test
    fun `detail route fills in the card id`() {
        assertEquals("detail/ketipung", Screen.Detail.createRoute("ketipung"))
    }

    @Test
    fun `interactive route fills in the card id`() {
        assertEquals("interactive/ketipung", Screen.Interactive.createRoute("ketipung"))
    }

    @Test
    fun `route patterns match the created routes`() {
        assertEquals("detail/{cardId}", Screen.Detail.route)
        assertEquals("interactive/{cardId}", Screen.Interactive.route)
    }
}
