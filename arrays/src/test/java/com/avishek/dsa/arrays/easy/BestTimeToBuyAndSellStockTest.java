
package com.avishek.dsa.arrays.easy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class BestTimeToBuyAndSellStockTest {

    private final BestTimeToBuyAndSellStock solution =
            new BestTimeToBuyAndSellStock();

    @Test
    void shouldFindMaximumProfit() {
        assertEquals(5, solution.maxProfit(
                new int[]{7, 1, 5, 3, 6, 4}));
    }

    @Test
    void shouldReturnZeroWhenPricesDecrease() {
        assertEquals(0, solution.maxProfit(
                new int[]{7, 6, 4, 3, 1}));
    }

    @Test
    void shouldHandleSingleDay() {
        assertEquals(0, solution.maxProfit(
                new int[]{5}));
    }

    @Test
    void shouldHandleIncreasingPrices() {
        assertEquals(4, solution.maxProfit(
                new int[]{1, 2, 3, 4, 5}));
    }

    @Test
    void shouldUpdateBuyingPriceMultipleTimes() {
        assertEquals(6, solution.maxProfit(
                new int[]{5, 1, 5, 0, 6}));
    }

    @Test
    void shouldHandleEqualPrices() {
        assertEquals(0, solution.maxProfit(
                new int[]{3, 3, 3}));
    }
}
