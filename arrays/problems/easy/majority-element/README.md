# LeetCode 169 — Majority Element

**Difficulty:** Easy  
**Topic:** Arrays  
**Algorithm:** Boyer–Moore Voting  
**Pattern:** Candidate Selection and Vote Cancellation  
**Problem:** https://leetcode.com/problems/majority-element/

## 1. Problem Statement

Given an integer array `nums` of length `n`, return the element that appears **more than ⌊n/2⌋ times**.

A majority element is guaranteed to exist.

### Example 1

```text
Input:  nums = [3, 2, 3]
Output: 3
```

### Example 2

```text
Input:  nums = [2, 2, 1, 1, 1, 2, 2]
Output: 2
```

### Constraints

- `1 <= nums.length <= 50,000`
- `-10^9 <= nums[i] <= 10^9`
- A majority element always exists.

**Follow-up:** Solve the problem in `O(n)` time and `O(1)` auxiliary space.

---

## 2. Initial Approach — Frequency Counting

The first idea is to count how many times each number appears.

For example:

```text
nums = [2, 2, 1, 1, 1, 2, 2]

Number 1 → 3 occurrences
Number 2 → 4 occurrences
```

Because `n = 7`, an element must occur at least four times to be the majority.

### Why not use the number as an array index?

An approach such as:

```java
count[nums[i]]++;
```

is unsafe for this problem because input values can be negative or very large.

For example, `count[-3]` is an invalid Java array access.

A HashMap avoids this limitation.

### HashMap approach

```java
Map<Integer, Integer> frequency = new HashMap<>();

for (int num : nums) {
    int count = frequency.getOrDefault(num, 0) + 1;

    if (count > nums.length / 2) {
        return num;
    }

    frequency.put(num, count);
}
```

**Time:** `O(n)` expected  
**Auxiliary space:** `O(n)` worst case

This is a valid solution, but it does not satisfy the constant-space follow-up.

---

## 3. Key Observation — Majority Means More Than Half

A majority element occurs more than all other elements combined.

This gives us a useful property:

**If we repeatedly remove pairs of different elements, the majority element of the original array must remain the majority among the uncancelled elements, provided any remain.**

Why?

Suppose the majority element appears `M` times and all other elements appear `R` times.

Because it is a majority:

```text
M > R
```

Cancelling one majority occurrence with one different element reduces both counts by one, preserving the inequality.

Cancelling two non-majority elements also cannot eliminate the majority's advantage.

This observation leads to the Boyer–Moore Voting Algorithm.

---

## 4. Deriving the Boyer–Moore Algorithm

Instead of maintaining a frequency for every distinct number, maintain only:

- `candidate`: The current potential majority element.
- `count`: The net number of votes supporting that candidate.

Process the array from left to right.

### Rules

1. When `count == 0`, select the current element as the new candidate.
2. If the current element matches the candidate, increase `count`.
3. Otherwise, decrease `count`.
4. After processing all elements, return the candidate.

The count represents a **vote balance**, not the candidate's total frequency.

### Why can we select a new candidate when count becomes zero?

When the count reaches zero, the elements contributing to the current voting group can be paired into different-value cancellations.

Those cancelled elements cannot change the identity of the overall majority.

We can therefore continue processing the remaining array with a fresh candidate.

---

## 5. Java Implementation

**Class:** `MajorityElement`

```java
package com.avishek.dsa.arrays.easy;

/**
 * LeetCode 169 - Majority Element
 *
 * Boyer-Moore Voting Algorithm.
 *
 * Time Complexity: O(n)
 * Auxiliary Space Complexity: O(1)
 */
public class MajorityElement {

    public int majorityElement(int[] nums) {

        int candidate = 0;
        int count = 0;

        for (int num : nums) {

            // Select a new candidate when votes are balanced.
            if (count == 0) {
                candidate = num;
            }

            // Equal values add votes; different values cancel votes.
            if (candidate == num) {
                count++;
            } else {
                count--;
            }
        }

        // A majority element is guaranteed to exist.
        return candidate;
    }
}
```

---

## 6. Detailed Dry Run

**Input:**

```text
nums = [2, 2, 1, 1, 1, 2, 2]
```

| Iteration | Number | Candidate | Count | Action |
|---|---:|---:|---:|---|
| 1 | 2 | 2 | 1 | Select 2, add vote |
| 2 | 2 | 2 | 2 | Add vote |
| 3 | 1 | 2 | 1 | Cancel vote |
| 4 | 1 | 2 | 0 | Cancel vote |
| 5 | 1 | 1 | 1 | Select 1, add vote |
| 6 | 2 | 1 | 0 | Cancel vote |
| 7 | 2 | 2 | 1 | Select 2, add vote |

**Final candidate: 2**

Notice that the candidate changes during execution.

This is not an error. A candidate is provisional until the traversal finishes.

---

## 7. Correctness Explanation

The algorithm conceptually removes pairs of elements with different values.

Whenever `count` becomes zero, the votes accumulated in the current group have cancelled.

The majority element appears more than half the time in the original array. Therefore, it cannot be completely eliminated by cancelling different-value pairs.

After all possible cancellations, the surviving candidate must be the majority element.

Because the problem guarantees that a majority exists, no additional verification pass is required.

**Important:** If the majority were not guaranteed, Boyer–Moore would return only a candidate. A second pass would then be necessary to verify its frequency.

---

## 8. Complexity Analysis

### Time Complexity — O(n)

The algorithm visits each element exactly once.

Each iteration performs constant-time comparisons and updates.

Therefore:

```text
Time = O(n)
```

### Auxiliary Space Complexity — O(1)

Only two variables are maintained:

```text
candidate
count
```

Their number does not grow with the input size.

Therefore:

```text
Auxiliary Space = O(1)
```

---

## 9. Edge Cases

| Scenario | Input | Expected |
|---|---|---|
| Single element | `[5]` | `5` |
| All elements equal | `[4,4,4,4]` | `4` |
| Candidate changes | `[2,2,1,1,1,2,2]` | `2` |
| Negative majority | `[-1,2,-1,-1,2]` | `-1` |
| Majority near end | `[1,2,7,7,7,7,7]` | `7` |

All examples satisfy the guarantee that a majority element exists.

---

## 10. Common Mistakes

### Mistake 1 — Treating count as frequency

The `count` variable is not the total number of occurrences of the candidate.

It represents the net vote balance after cancellations.

### Mistake 2 — Assuming the first candidate is always correct

The candidate may change multiple times.

Only the final candidate is guaranteed to be the majority.

### Mistake 3 — Using a HashMap for the constant-space follow-up

HashMap frequency counting uses additional memory proportional to the number of distinct values.

Boyer–Moore uses only two variables.

### Mistake 4 — Applying Boyer–Moore without checking the problem guarantee

If a majority element is not guaranteed, a second pass is necessary to verify that the candidate occurs more than `n/2` times.

---

## 11. Interview Insights

**Q: Why is this algorithm called a voting algorithm?**

Matching elements contribute positive votes to the current candidate. Different elements cancel those votes.

**Q: Can the candidate change?**

Yes. A new candidate is selected whenever the vote balance reaches zero.

**Q: Does the algorithm modify the input?**

No. The array is only read.

**Q: Why does it use constant space?**

It stores only the current candidate and its vote balance.

**Q: Is sorting an alternative?**

Yes. After sorting, the element at index `n/2` is guaranteed to be the majority. However, sorting generally takes `O(n log n)` time and may modify the input.

---

## 12. Comparison of Approaches

| Approach | Time | Auxiliary Space |
|---|---|---|
| Brute-force frequency counting | O(n²) | O(1) |
| HashMap counting | O(n) expected | O(n) |
| Sorting | O(n log n) | Implementation-dependent |
| Boyer–Moore Voting | O(n) | O(1) |

**Optimal choice:** Boyer–Moore Voting.

---

## 13. Key Learning

The most important insight is that we do not need to know the exact frequency of every number.

Because the majority element is guaranteed to occur more than all other elements combined, cancelling different-value pairs preserves enough information to identify it.

This transforms a frequency-counting problem into a constant-space voting algorithm.

### Problem-solving progression

```text
Understand majority frequency
          ↓
Consider frequency array
          ↓
Recognize negative and large values
          ↓
Use HashMap counting
          ↓
Identify O(n) space limitation
          ↓
Observe majority cancellation property
          ↓
Apply Boyer-Moore Voting
          ↓
O(n) time and O(1) space
```

---

## 14. Source Code and Tests

**Implementation:** [MajorityElement.java](../../../src/main/java/com/avishek/dsa/arrays/easy/MajorityElement.java)

**JUnit tests:** [MajorityElementTest.java](../../../src/test/java/com/avishek/dsa/arrays/easy/MajorityElementTest.java)

Run from the repository root:

```powershell
mvn -pl arrays -am test
```

---

## 15. Final Summary

| Property | Value |
|---|---|
| Problem | Majority Element |
| LeetCode | 169 |
| Difficulty | Easy |
| Data Structure | Array |
| Algorithm | Boyer–Moore Voting |
| Time Complexity | O(n) |
| Auxiliary Space | O(1) |
| Modifies Input | No |
| Majority Guaranteed | Yes |

**Core lesson:** A strong mathematical property of the input can eliminate the need for additional data structures.