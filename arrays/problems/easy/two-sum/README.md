# Two Sum — LeetCode 1

**Difficulty:** Easy  
**Topic:** Arrays, HashMap  
**Pattern:** Complement Lookup  
**Problem:** [LeetCode — Two Sum](https://leetcode.com/problems/two-sum/)

## 1. Problem Statement

Given an integer array `nums` and an integer `target`, return the indices of two distinct elements whose sum equals the target.

Assume exactly one valid solution exists in the original problem. You cannot use the same array element twice.

### Example

**Input:**
```text
nums = [2, 7, 11, 15]
target = 9
```

**Output:**
```text
[0, 1]
```

**Explanation:** `nums[0] + nums[1] = 2 + 7 = 9`.

## 2. Understanding the Problem

Before writing code, identify what is being requested.

- We need the indices, not the values.
- We must use two different array positions.
- The array is not guaranteed to be sorted.
- We want to minimize unnecessary comparisons.

**Key question:** Can we find the second number without examining every possible pair?

## 3. Approach 1 — Brute Force

The simplest approach is to examine every pair of distinct indices.

```java
public static int[] twoSumBruteForce(int[] nums, int target) {
    for (int i = 0; i < nums.length; i++) {
        for (int j = i + 1; j < nums.length; j++) {
            if (nums[i] + nums[j] == target) {
                return new int[] {i, j};
            }
        }
    }
    return new int[0];
}
```

### How we arrived at this approach

We know that the answer consists of two elements. Without using any additional data structure, we can systematically test every possible pair.

Starting `j` at `i + 1` prevents selecting the same position twice and avoids checking each pair in both orders.

**Time complexity:** O(n²) — in the worst case, we examine a quadratic number of pairs.

**Extra space complexity:** O(1) — no additional data structure grows with input size.

### Limitation

For large arrays, checking every pair becomes expensive. We need a faster way to locate the second number.

## 4. Deriving the Optimal Approach

Instead of asking:

*Which two numbers add up to the target?*

Ask:

*If I already know one number, what must the other number be?*

Mathematically:

```text
a + b = target

Therefore:

b = target - a
```

This missing value is called the **complement**.

For example:

```text
Current number = 7
Target         = 9
Complement     = 9 - 7 = 2
```

Now our problem becomes a lookup problem.

A HashMap can store previously visited numbers and their indices, allowing expected constant-time lookup.

This gives us a one-pass solution.

## 5. Approach 2 — HashMap

```java
public static int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> numberToIndex = new HashMap<>();

    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];

        if (numberToIndex.containsKey(complement)) {
            return new int[] {
                numberToIndex.get(complement), i
            };
        }

        numberToIndex.put(nums[i], i);
    }

    return new int[0];
}
```

### Why check before inserting?

Suppose:

```text
nums = [3]
target = 6
```

If we inserted `3` before checking, we might incorrectly use the same element twice.

Checking first guarantees that the complement comes from an earlier, different index.

## 6. Detailed Dry Run

**Input:** `nums = [2, 7, 11, 15]`, `target = 9`

| Iteration | Index | Number | Complement | Map before lookup | Action |
|---|---|---|---|---|---|
| 1 | 0 | 2 | 7 | `{}` | Store `2 → 0` |
| 2 | 1 | 7 | 2 | `{2=0}` | Found complement |

Return `[0, 1]`.

We stop immediately after finding the valid pair.

## 7. Why the Algorithm Works

At the start of each iteration, the HashMap contains values and indices from previously processed positions.

If the complement exists in the map, that previous element and the current element add up to the target. Their indices are distinct.

If the complement does not exist, storing the current value makes it available to future iterations.

Therefore, every valid pair can be found when the algorithm reaches the later of its two positions.

## 8. Complexity Analysis

| Approach | Time | Extra Space |
|---|---|---|
| Brute force | O(n²) | O(1) |
| One-pass HashMap | O(n) expected | O(n) |

The HashMap solution is asymptotically optimal in expected time for an unsorted array because reading the input may require examining all n elements.

HashMap operations are expected O(1), although pathological collision behavior can affect worst-case performance.

The O(n) space is the trade-off for faster lookups.

## 9. Edge Cases

| Input | Target | Expected result |
|---|---:|---|
| `[3, 3]` | 6 | `[0, 1]` |
| `[-3, 4, 3, 90]` | 0 | `[0, 2]` |
| `[1, 2, 3]` | 10 | `[]` |
| `[5]` | 10 | `[]` |
| `[0, 4, 3, 0]` | 0 | `[0, 3]` |

The empty-result behavior is an extension of the original LeetCode problem, which guarantees a solution.

For inputs using extreme integer values, Java `int` subtraction may overflow. If arbitrary full-range integers must be supported, compute the complement using `long` and check whether it is within the integer range.

## 10. Interview Insights

**Why use a HashMap?**  
It provides fast lookup while preserving the original indices.

**Why not sort the array?**  
Sorting changes element positions unless we retain the original indices. It also generally requires O(n log n) time.

**Can we use a HashSet?**  
A HashSet can tell us whether a complement exists, but a HashMap directly preserves the index needed for the answer.

**What if multiple solutions exist?**  
We need to clarify whether any valid pair or all valid pairs are required. The implementation returns the first pair it discovers.

## 11. Key Takeaway

The most important insight is not memorizing the HashMap solution.

It is recognizing the transformation:

**Pair search → Complement calculation → Fast lookup**

This pattern appears in many array and hashing problems.

## 12. Implementation and Tests

- [Java Solution](../../../src/main/java/com/avishek/dsa/arrays/easy/TwoSum.java)
- [JUnit Tests](../../../src/test/java/com/avishek/dsa/arrays/easy/TwoSumTest.java)

Run the tests from the repository root:

```powershell
mvn -pl arrays -am test
```

The `-pl arrays` option selects the Arrays module, and `-am` includes required reactor projects.