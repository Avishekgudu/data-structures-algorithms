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
``