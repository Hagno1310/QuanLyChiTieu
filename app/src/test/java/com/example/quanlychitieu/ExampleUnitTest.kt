package com.example.quanlychitieu

import org.junit.Assert.assertEquals
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun formatMoney_usesThousandSeparator() {
        assertEquals("120.000 đ", formatMoney(120000))
        assertEquals("200.000 đ", formatMoney(listOf(30000L, 50000L, 120000L).sum()))
    }
}
