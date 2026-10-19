# LeetCode 88 — Merge Sorted Array

**Difficulty:** Easy  
**Topic:** Arrays  
**Algorithmic Pattern:** Two Pointers — Reverse Traversal  
**Approach:** In-Place Merge from Right to Left  
**Problem Link:** https://leetcode.com/problems/merge-sorted-array/

---

## 1. Problem Statement

You are given two integer arrays `nums1` and `nums2`, sorted in **non-decreasing order**, and two integers `m` and `n` representing the number of valid elements in each array.

Merge both arrays into a single sorted array.

The result must be stored directly inside `nums1`, without returning a new array.

**Important conditions:**

- `nums1` has a total length of `m + n`.
- Only the first `m` elements of `nums1` contain valid input values.
- The remaining `n` positions are placeholders.
- `nums2` contains exactly `n` valid elements.
- Both arrays are already sorted.
- The final result must remain sorted in non-decreasing order.

### Example 1

**Input:**

```text
nums1 = [1, 2, 3, 0, 0, 0]
m = 3

nums2 = [2, 5, 6]
n = 3
```

**Output:**

```text
[1, 2, 2, 3, 5, 6]
```

**Explanation:**

The valid elements are:

```text
nums1 = [1, 2, 3]
nums2 = [2, 5, 6]
```

After merging:

```text
nums1 = [1, 2, 2, 3, 5, 6]
```

### Example 2

**Input:**

```text
nums1 = [1]
m = 1

nums2 = []
n = 0
```

**Output:**

```text
[1]
```

No merging is required because `nums2` is empty.

### Example 3

**Input:**

```text
nums1 = [0]
m = 0

nums2 = [1]
n = 1
```

**Output:**

```text
[1]
```

The zero in `nums1` is a placeholder, not a valid input element.

### Constraints

- `nums1.length == m + n`
- `nums2.length == n`
- `0 <= m, n <= 200`
- `1 <= m + n <= 200`
- `-10^9 <= nums1[i], nums2[j] <= 10^9`

**Follow-up:** Can the merge be completed in `O(m + n)` time?

---

## 2. Understanding the Problem

Before implementing an algorithm, identify the important characteristics.

**Observation 1: Both arrays are sorted.**

This means we already know the relative order of elements within each array.

**Observation 2: nums1 has additional capacity.**

We do not need to allocate another array to store the result.

**Observation 3: The final result must be stored in nums1.**

We must be careful not to overwrite valid elements before processing them.

**Observation 4: We need to preserve duplicates.**

For example:

```text
nums1 = [1, 2, 0]
nums2 = [2]

Result = [1, 2, 2]
```

The problem requires merging, not removing duplicates.

### Questions to ask before coding

1. Can we take advantage of the fact that both arrays are sorted?
2. Can we merge without creating another array?
3. What happens if we start writing at index 0?
4. Can we instead use the unused positions at the end of nums1?
5. How do we handle an empty array?

These questions lead us toward the optimal approach.

---

## 3. Approach 1 — Brute Force Using Sorting

The simplest approach is:

1. Copy all elements of `nums2` into the available positions of `nums1`.
2. Sort the entire `nums1` array.

### Java implementation

```java
import java.util.Arrays;

public void mergeBruteForce(int[] nums1, int m,
                            int[] nums2, int n) {

    for (int j = 0; j < n; j++) {
        nums1[m + j] = nums2[j];
    }

    Arrays.sort(nums1);
}
```

### Example

Initially:

```text
nums1 = [1, 2, 3, 0, 0, 0]
nums2 = [2, 5, 6]
```

After copying:

```text
nums1 = [1, 2, 3, 2, 5, 6]
```

After sorting:

```text
nums1 = [1, 2, 2, 3, 5, 6]
```

### Complexity analysis

**Time Complexity: O((m + n) log(m + n))**

Copying requires `O(n)` time, and sorting requires `O((m+n) log(m+n))` time.

**Auxiliary Space Complexity:** Depends on the sorting implementation. Java's `Arrays.sort(int[])` uses an in-place primitive-array sorting algorithm with a bounded recursion stack.

### Why should we optimize?

We are sorting elements that are already individually sorted.

This repeats work unnecessarily.

We should be able to merge the arrays in linear time.

---

## 4. Approach 2 — Two Pointers with a Temporary Array

Because both arrays are sorted, we can compare elements using two pointers.

Initialize:

```text
i = 0
j = 0
```

Compare:

```text
nums1[i] and nums2[j]
```

Copy the smaller element into a temporary array and advance the corresponding pointer.

Continue until all elements have been merged.

### Complexity

**Time:** `O(m + n)`

**Auxiliary Space:** `O(m + n)`

This improves the time complexity, but it requires additional memory.

### Limitation

The problem already provides enough space inside `nums1`.

Can we achieve the same linear time without allocating another array?

Yes.

---

## 5. Deriving the Optimal Approach

### The key insight

Both arrays are sorted in ascending order.

Therefore, their largest valid elements are at the end.

Consider:

```text
nums1 = [1, 2, 3, 0, 0, 0]
nums2 = [2, 5, 6]
```

The largest elements are:

```text
nums1 → 3
nums2 → 6
```

The largest value in the merged result must occupy the last position.

Instead of merging from the beginning, we can start filling the array **from the end**.

This avoids overwriting valid elements that we have not processed yet.

### Why reverse traversal works

The extra capacity in `nums1` is located at the end.

When we write the largest remaining element into the rightmost available position, we do not destroy any smaller element that still needs to be compared.

After placing one element, we move backward.

This produces the final sorted array without using an additional array.

---

## 6. Three-Pointer Technique

We maintain three pointers:

| Pointer | Initial Value | Purpose |
|---|---|---|
| `i` | `m - 1` | Last valid element in nums1 |
| `j` | `n - 1` | Last element in nums2 |
| `k` | `m + n - 1` | Last available destination index |

### Initial state

```text
nums1 = [1, 2, 3, 0, 0, 0]
               i        k

nums2 = [2, 5, 6]
               j
```

The pointers initially refer to:

```text
i = 2 → nums1[i] = 3
j = 2 → nums2[j] = 6
k = 5 → destination index
```

### Decision rule

If:

```text
nums1[i] >= nums2[j]
```

Place `nums1[i]` at `nums1[k]`, then decrement `i`.

Otherwise, place `nums2[j]` at `nums1[k]`, then decrement `j`.

In both cases, decrement `k`.

Continue while both arrays have unprocessed elements.

---

## 7. Optimal Java Implementation

**File:** `MergeSortedArray.java`

```java
package com.avishek.dsa.arrays.easy;

/**
 * LeetCode 88 - Merge Sorted Array
 *
 * Approach:
 * Three pointers with reverse traversal.
 *
 * Time Complexity: O(m + n)
 * Auxiliary Space Complexity: O(1)
 */
public class MergeSortedArray {

    public void merge(int[] nums1, int m,
                      int[] nums2, int n) {

        // Last valid element in nums1.
        int i = m - 1;

        // Last element in nums2.
        int j = n - 1;

        // Last available destination index in nums1.
        int k = m + n - 1;

        // Merge while both arrays have remaining elements.
        while (i >= 0 && j >= 0) {

            if (nums1[i] >= nums2[j]) {

                // Place the larger element from nums1.
                nums1[k] = nums1[i];
                i--;

            } else {

                // Place the larger element from nums2.
                nums1[k] = nums2[j];
                j--;
            }

            // Move the destination pointer backward.
            k--;
        }

        // Copy any remaining nums2 elements.
        while (j >= 0) {

            nums1[k] = nums2[j];
            j--;
            k--;
        }

        // Remaining nums1 elements are already in place.
    }
}
```

### Why direct assignment instead of swapping?

An initial implementation might swap `nums1[i]` and `nums1[k]`.

However, swapping is unnecessary.

We only need to place the larger remaining element into the destination position.

```java
nums1[k] = nums1[i];
```

This is simpler, avoids an unnecessary temporary variable, and makes the algorithm easier to reason about.

---

## 8. Detailed Dry Run

**Input:**

```text
nums1 = [1, 2, 3, 0, 0, 0]
nums2 = [2, 5, 6]

m = 3
n = 3
```

Initial pointers:

```text
i = 2
j = 2
k = 5
```

### Iteration 1

Compare:

```text
nums1[2] = 3
nums2[2] = 6
```

Since `6 > 3`, place `6` at index 5.

```text
nums1 = [1, 2, 3, 0, 0, 6]
```

Update:

```text
i = 2
j = 1
k = 4
```

### Iteration 2

Compare:

```text
nums1[2] = 3
nums2[1] = 5
```

Since `5 > 3`, place `5` at index 4.

```text
nums1 = [1, 2, 3, 0, 5, 6]
```

Update:

```text
i = 2
j = 0
k = 3
```

### Iteration 3

Compare:

```text
nums1[2] = 3
nums2[0] = 2
```

Since `3 >= 2`, place `3` at index 3.

```text
nums1 = [1, 2, 3, 3, 5, 6]
```

Update:

```text
i = 1
j = 0
k = 2
```

### Iteration 4

Compare:

```text
nums1[1] = 2
nums2[0] = 2
```

Since `2 >= 2`, place the element from `nums1` at index 2.

```text
nums1 = [1, 2, 2, 3, 5, 6]
```

Update:

```text
i = 0
j = 0
k = 1
```

### Iteration 5

Compare:

```text
nums1[0] = 1
nums2[0] = 2
```

Since `2 > 1`, place `2` at index 1.

```text
nums1 = [1, 2, 2, 3, 5, 6]
```

Update:

```text
i = 0
j = -1
k = 0
```

The main loop terminates because `j < 0`.

No elements remain in `nums2`.

The remaining element `1` from `nums1` is already correctly positioned.

### Final result

```text
[1, 2, 2, 3, 5, 6]
```

---

## 9. Why Do We Need Only One Additional While Loop?

Our main loop is:

```java
while (i >= 0 && j >= 0)
```

It stops when either array is exhausted.

There are two possibilities.

### Case 1: nums1 is exhausted first

For example:

```text
nums1 = [4, 5, 0, 0]
nums2 = [1, 2]
```

After placing `5` and `4` at the end, the remaining elements from `nums2` must be copied into the beginning.

Therefore:

```java
while (j >= 0) {
    nums1[k] = nums2[j];
    j--;
    k--;
}
```

### Case 2: nums2 is exhausted first

For example:

```text
nums1 = [1, 2, 3, 0]
nums2 = [4]
```

After placing `4` at the end:

```text
nums1 = [1, 2, 3, 4]
```

The remaining `nums1` elements are already correctly positioned.

Therefore, an additional `while (i >= 0)` loop is unnecessary.

---

## 10. Correctness Explanation

The algorithm maintains the following invariant:

**At the beginning of each iteration, all positions to the right of `k` contain the largest elements of the merged result in their correct sorted positions.**

Initially, no positions have been filled, so the invariant holds.

During each iteration:

1. Both arrays are sorted.
2. Their largest unprocessed elements are at indices `i` and `j`.
3. The larger of these two elements must be the largest remaining element overall.
4. Placing it at index `k` therefore puts it in its final correct position.
5. Moving the appropriate pointers backward preserves the invariant.

When one array is exhausted, the remaining elements are either copied from `nums2` or already positioned correctly in `nums1`.

Therefore, the algorithm produces the correct sorted merge.

---

## 11. Complexity Analysis

Let:

```text
m = number of valid elements in nums1
n = number of elements in nums2
```

### Time Complexity: O(m + n)

Each iteration moves at least one input pointer backward.

Every input element is processed at most once.

Therefore:

```text
O(m + n)
```

This is asymptotically optimal for the general merging problem because the input elements may all need to be examined.

### Auxiliary Space Complexity: O(1)

The algorithm uses only three integer pointers:

```text
i
j
k
```

No temporary array or collection is allocated.

Therefore:

```text
O(1)
```

---

## 12. Comparison of Approaches

| Approach | Time Complexity | Auxiliary Space |
|---|---|---|
| Copy and sort | O((m+n) log(m+n)) | Implementation-dependent |
| Two pointers with temporary array | O(m+n) | O(m+n) |
| Reverse three-pointer merge | O(m+n) | O(1) |

**Preferred approach:** Reverse three-pointer merge.

It achieves linear time while using constant auxiliary space.

---

## 13. Edge Cases

### Case 1: nums2 is empty

```text
nums1 = [1]
m = 1
nums2 = []
n = 0

Output = [1]
```

No modification is necessary.

### Case 2: nums1 has no valid elements

```text
nums1 = [0]
m = 0
nums2 = [1]
n = 1

Output = [1]
```

All elements must be copied from `nums2`.

### Case 3: Duplicate values

```text
nums1 = [1, 2, 2, 0, 0]
m = 3
nums2 = [2, 3]
n = 2

Output = [1, 2, 2, 2, 3]
```

Duplicates must be preserved.

### Case 4: Negative numbers

```text
nums1 = [-3, -1, 0, 0]
m = 2
nums2 = [-2, 4]
n = 2

Output = [-3, -2, -1, 4]
```

The algorithm works with negative values.

### Case 5: All nums2 elements are smaller

```text
nums1 = [4, 5, 0, 0]
m = 2
nums2 = [1, 2]
n = 2

Output = [1, 2, 4, 5]
```

### Case 6: All nums2 elements are larger

```text
nums1 = [1, 2, 0, 0]
m = 2
nums2 = [3, 4]
n = 2

Output = [1, 2, 3, 4]
```

---

## 14. Common Mistakes

### Mistake 1: Merging from the beginning

Writing into the beginning of `nums1` may overwrite valid elements that have not been processed.

**Improvement:** Merge from right to left.

### Mistake 2: Treating placeholder zeros as valid values

The last `n` positions in `nums1` are not part of the original input.

**Improvement:** Initialize `i = m - 1`, not `nums1.length - 1`.

### Mistake 3: Forgetting remaining nums2 elements

If `nums1` becomes exhausted first, the remaining `nums2` values still need to be copied.

**Improvement:** Include `while (j >= 0)`.

### Mistake 4: Using unnecessary swaps

Swapping requires additional assignments even though only the destination value matters.

**Improvement:** Use direct assignment.

### Mistake 5: Returning a new array

The problem explicitly requires modifying `nums1` in place.

**Improvement:** Use a `void` method and modify the supplied array.

---

## 15. Interview Insights

**Q1. Why do we merge from right to left?**

Because `nums1` has unused capacity at the end. Filling that space first avoids overwriting unprocessed input elements.

**Q2. Why are three pointers necessary?**

Two pointers track the largest remaining values in the input arrays. The third tracks the next destination position.

**Q3. Can this be solved using only two pointers?**

Yes, with a temporary array and an output index derived from its current size, but the direct in-place solution is clearest with three pointers.

**Q4. What happens when the compared values are equal?**

Either value may be selected. Both choices preserve sorted order because the values are equal.

**Q5. Is the algorithm stable?**

The presented algorithm preserves the relative order of equal elements within each original array. However, when equal values occur across both arrays, choosing from `nums1` during a reverse merge places the `nums2` element earlier in the final array.

If a stable merge requires elements from `nums1` to precede equal elements from `nums2`, the tie-breaking rule must be adjusted.

**Q6. What is the main pattern to recognize?**

When merging sorted arrays and the destination has spare capacity at the end, consider **reverse traversal with multiple pointers**.

---

## 16. Key Takeaways

1. Always examine the input properties before choosing an algorithm.
2. Sorted input often suggests a two-pointer technique.
3. Extra capacity at the end of an array can enable in-place operations.
4. Traversal direction can determine whether overwriting is safe.
5. A carefully maintained invariant helps prove algorithm correctness.
6. Handle leftover elements explicitly.
7. Prefer direct assignments when swapping is unnecessary.

### Problem-solving journey

```text
Understand the problem
        ↓
Identify sorted input
        ↓
Consider brute-force sorting
        ↓
Recognize redundant sorting work
        ↓
Apply two-pointer merging
        ↓
Notice additional space requirement
        ↓
Use available capacity in nums1
        ↓
Merge from right to left
        ↓
Achieve O(m+n) time and O(1) space
```

---

## 17. Implementation and Tests

**Java implementation:**

[MergeSortedArray.java](../../../src/main/java/com/avishek/dsa/arrays/easy/MergeSortedArray.java)

**JUnit tests:**

[MergeSortedArrayTest.java](../../../src/test/java/com/avishek/dsa/arrays/easy/MergeSortedArrayTest.java)

Run tests from the repository root:

```powershell
mvn -pl arrays -am test
```

---

## 18. Final Summary

| Property | Value |
|---|---|
| Problem | Merge Sorted Array |
| LeetCode | 88 |
| Difficulty | Easy |
| Data Structure | Array |
| Pattern | Two Pointers |
| Traversal | Right to Left |
| Time Complexity | O(m+n) |
| Auxiliary Space | O(1) |
| Modifies Input | Yes |
| Returns | void |

**Core lesson:** The direction in which we process an array can be just as important as the data structure or algorithm we choose.