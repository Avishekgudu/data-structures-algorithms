package com.avishek.dsa.arrays.easy;

import java.util.HashMap;
import java.util.Map;

/**
 * Problem: Two Sum
 *
 * Given an integer array and a target, return the indices of two numbers
 * whose sum equals the target.
 *
 * Approach:
 * Use a HashMap to store each number and its index while traversing the array.
 * For every number, check whether its complement already exists.
 *
 * Time Complexity: O(n)
 * Space Complexity: O(n)
 */
public class TwoSum {

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> numberToIndex = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];

            if (numberToIndex.containsKey(complement)) {
                return new int[] {
                    numberToIndex.get(complement),
                    i
                };
            }

            numberToIndex.put(nums[i], i);
        }

        return new int[0];
    }
}