
package com.avishek.dsa.arrays.easy;

/**
 * LeetCode 169 - Majority Element
 *
 * Finds the element appearing more than n/2 times.
 * The problem guarantees that a majority element exists.
 *
 * Algorithm: Boyer-Moore Voting
 * Time Complexity: O(n)
 * Auxiliary Space Complexity: O(1)
 */
public class MajorityElement {

    public int majorityElement(int[] nums) {

        // Current potential majority element.
        int candidate = 0;

        // Net vote balance for the current candidate.
        int count = 0;

        for (int num : nums) {

            // Previous votes have cancelled out.
            // Select the current number as a new candidate.
            if (count == 0) {
                candidate = num;
            }

            // Matching numbers add a vote.
            // Different numbers cancel a vote.
            if (candidate == num) {
                count++;
            } else {
                count--;
            }
        }

        // The majority is guaranteed to exist.
        return candidate;
    }
}
