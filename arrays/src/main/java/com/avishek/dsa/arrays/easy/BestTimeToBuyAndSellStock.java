
package com.avishek.dsa.arrays.easy;

/**
 * LeetCode 121 - Best Time to Buy and Sell Stock
 *
 * Finds the maximum profit from one buy and one later sell.
 *
 * Approach: One pass with a running minimum buying price.
 * Time Complexity: O(n)
 * Auxiliary Space Complexity: O(1)
 */
public class BestTimeToBuyAndSellStock {

    public int maxProfit(int[] prices) {

        // The first day is the earliest possible buying day.
        int buyingPrice = prices[0];

        // A transaction is optional, so profit cannot be negative.
        int maxProfit = 0;

        // Start at day 2 (index 1), the earliest possible selling day.
        for (int i = 1; i < prices.length; i++) {

            // Include the immediately preceding day as a buying
            // candidate. All older days are already represented
            // by the running minimum stored in buyingPrice.
            if (prices[i - 1] < buyingPrice) {
                buyingPrice = prices[i - 1];
            }

            // Sell on the current day using the cheapest earlier buy.
            int currentProfit = prices[i] - buyingPrice;

            // Keep the best profit found across all selling days.
            if (currentProfit > maxProfit) {
                maxProfit = currentProfit;
            }
        }

        return maxProfit;
    }
}
