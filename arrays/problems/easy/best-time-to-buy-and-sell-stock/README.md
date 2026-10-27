# LeetCode 121 — Best Time to Buy and Sell Stock

**Difficulty:** Easy  
**Topic:** Arrays  
**Pattern:** One Pass / Running Minimum / Greedy  
**Problem:** https://leetcode.com/problems/best-time-to-buy-and-sell-stock/

## 1. Problem statement

Given `prices`, where `prices[i]` is the stock price on day `i`, choose **one buying day** and **one strictly later selling day** to maximize profit. If no profitable transaction exists, return `0`.

- Example: `prices = [7,1,5,3,6,4]` → `5` (buy at `1`, sell later at `6`).
- Example: `prices = [7,6,4,3,1]` → `0`.

**Constraints:** `1 <= prices.length <= 10^5`; `0 <= prices[i] <= 10^4`.

## 2. Key observations

1. Selling must happen **after** buying. A profitable pair is not just any two values; their index order matters.
2. For a fixed selling day `i`, the best buying price is the **smallest price among days `0` through `i-1`**.
3. We can track that smallest earlier price as a running minimum, without scanning the earlier days again.
4. Initialize maximum profit to `0` because choosing not to trade is allowed.

## 3. Approach evolution

### Brute force — test every valid buy/sell pair

For each buying index `buy`, try every `sell > buy` and update the maximum difference.

```java
int maxProfit = 0;
for (int buy = 0; buy < prices.length; buy++) {
    for (int sell = buy + 1; sell < prices.length; sell++) {
        maxProfit = Math.max(maxProfit, prices[sell] - prices[buy]);
    }
}
return maxProfit;
```

**Time:** `O(n²)`; **auxiliary space:** `O(1)`. Repeatedly inspecting earlier prices is unnecessary.

### Optimal — scan possible selling days once

Initialize `buyingPrice = prices[0]`. Start the selling index at `i = 1`, because day 0 cannot be a selling day: no earlier day exists on which to buy.

Before computing the profit for selling on day `i`, update the cheapest eligible buying price using `prices[i-1]`. Then calculate `prices[i] - buyingPrice`.

**Why only compare against `prices[i-1]`?**

`buyingPrice` is **not merely yesterday's price**. It already stores the minimum among all older days. At the start of iteration `i`, the previously processed candidate buying days are `0 ... i-2`. The *only newly eligible buying day* is `i-1`. Therefore:

```text
buyingPrice = min(previous buyingPrice, prices[i - 1])
            = min(prices[0], prices[1], ..., prices[i - 1])
```

This maintains a **running minimum**. Comparing the current day's price to every earlier index would repeat work; comparing only the immediately previous day **against the stored minimum** is sufficient.

**Why start evaluating selling on day 2 (index 1)?**

The transaction requires `buy < sell`. Index 0 has no earlier buying day. Index 1 is the earliest valid selling day, and day 0 is its only eligible buying day.

## 4. Java implementation

**File:** `BestTimeToBuyAndSellStock.java`

```java
package com.avishek.dsa.arrays.easy;

/**
 * LeetCode 121 — Best Time to Buy and Sell Stock.
 * One-pass running-minimum solution.
 * Time: O(n); auxiliary space: O(1).
 */
public class BestTimeToBuyAndSellStock {

    public int maxProfit(int[] prices) {
        // Day 0 is the first possible buying day.
        int buyingPrice = prices[0];

        // Never return a loss: we may choose not to trade.
        int maxProfit = 0;

        // Day 1 (the second day) is the first possible selling day.
        for (int i = 1; i < prices.length; i++) {
            // All older buying days are summarized by buyingPrice.
            // Add the only newly eligible buying day, i - 1.
            if (prices[i - 1] < buyingPrice) {
                buyingPrice = prices[i - 1];
            }

            // Evaluate selling today after buying on an earlier day.
            int currentProfit = prices[i] - buyingPrice;
            if (currentProfit > maxProfit) {
                maxProfit = currentProfit;
            }
        }

        return maxProfit;
    }
}
```

**Alternative equivalent pattern:** On each day, first calculate profit using the minimum from earlier days, then update the minimum with today's price for future selling days. Both approaches enforce buying before selling.

## 5. Dry run

`prices = [7,1,5,3,6,4]`

| Selling day (1-based) | Current selling price | Minimum earlier buying price | Profit today | Best profit so far |
|---|---:|---:|---:|---:|
| 2 | 1 | 7 | -6 | 0 |
| 3 | 5 | 1 | 4 | 4 |
| 4 | 3 | 1 | 2 | 4 |
| 5 | 6 | 1 | 5 | 5 |
| 6 | 4 | 1 | 3 | 5 |

**Result:** `5`. Buy on day 2 at price `1`, sell on day 5 at price `6`.

## 6. Correctness reasoning

**Invariant:** Immediately before evaluating a sale at index `i`, `buyingPrice` is the minimum of `prices[0..i-1]`.

- **Base case (`i = 1`):** `buyingPrice` is initialized to `prices[0]`; the update using `prices[0]` preserves the invariant.
- **Inductive step:** Assuming the stored value summarizes days before `i-1`, comparing it with `prices[i-1]` yields the minimum across all days before `i`.
- **Profit choice:** For any fixed selling day `i`, buying at the cheapest earlier price gives the maximum possible profit for that day.
- **Global optimum:** Every valid selling day from index 1 onward is considered; `maxProfit` retains the largest profit, or zero if none is positive.

Thus the algorithm returns the maximum profit from at most one valid transaction.

## 7. Complexity

- **Time:** `O(n)` — each potential selling day is evaluated once.
- **Auxiliary space:** `O(1)` — only a few integer variables are used.

## 8. Edge cases

| Input | Expected | Reason |
|---|---:|---|
| `[7]` | `0` | No later selling day |
| `[7,6,4,3,1]` | `0` | Prices only decrease |
| `[1,2,3,4,5]` | `4` | Buy first, sell last |
| `[3,3,3]` | `0` | No positive profit |
| `[2,1,2]` | `1` | Cheapest buy is in the middle |
| `[5,1,5,0,6]` | `6` | Running minimum updates later |
| `[0,10000]` | `10000` | Boundary values |

## 9. Common mistakes and interview insights

- **Buying after selling:** Comparing arbitrary minimum and maximum prices without respecting index order is invalid.
- **Checking only adjacent-day profit:** `prices[i] - prices[i-1]` misses gains spanning multiple days. Instead, compare against the *running minimum*.
- **Recomputing the minimum:** Scanning `prices[0..i-1]` on every day costs `O(n²)`; the running minimum updates in `O(1)` per day.
- **Returning a negative value:** Initialize the best profit to zero because no transaction is permitted.
- **Confusing single transaction with multiple transactions:** This problem allows at most one buy and one later sell. It is different from LeetCode 122.

**Core pattern:** Maintain a summary of the past (minimum purchase price), then evaluate each present opportunity (selling price) against that summary.

## 10. Source code and tests

- [Java solution](../../../src/main/java/com/avishek/dsa/arrays/easy/BestTimeToBuyAndSellStock.java)
- [JUnit tests](../../../src/test/java/com/avishek/dsa/arrays/easy/BestTimeToBuyAndSellStockTest.java)

Run from the repository root:

```powershell
mvn -pl arrays -am test
```

**Status:** Mark as completed after JUnit tests pass and the change is committed and pushed.
