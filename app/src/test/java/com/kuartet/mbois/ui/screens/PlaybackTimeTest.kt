package com.kuartet.mbois.ui.screens

import java.util.Locale
import org.junit.Assert.assertEquals
import org.junit.Test

class PlaybackTimeTest {

    private fun format(millis: Float) = formatPlaybackTime(millis, Locale.ROOT)

    @Test
    fun `zero is shown as 00 colon 00`() {
        assertEquals("00:00", format(0f))
    }

    @Test
    fun `partial seconds are dropped`() {
        assertEquals("00:01", format(1999f))
    }

    @Test
    fun `minutes and seconds are zero padded`() {
        assertEquals("01:05", format(65_000f))
    }

    @Test
    fun `durations past an hour keep counting minutes`() {
        assertEquals("61:01", format(3_661_000f))
    }
}
