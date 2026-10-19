
package com.avishek.dsa.arrays.easy;

/**
 * LeetCode 88 - Merge Sorted Array
 *
 * Problem:
 * Merge two sorted arrays into nums1 in non-decreasing order.
 * nums1 contains m valid elements followed by n buffer positions.
 *
 * Approach:
 * Three pointers, traversing both arrays from right to left.
 *
 * Time Complexity: O(m + n)
 * Auxiliary Space Complexity: O(1)
 */
public class MergeSortedArray {

    public void merge(int[] nums1, int m, int[] nums2, int n) {

        // Index of the last valid element in nums1.
        int i = m - 1;

        // Index of the last element in nums2.
        int j = n - 1;

        // Index where the next largest element should be placed.
        int k = m + n - 1;

        // Compare the largest remaining elements in both arrays.
        while (i >= 0 && j >= 0) {

            if (nums1[i] >= nums2[j]) {

                // Place the larger nums1 element at the end.
                nums1[k] = nums1[i];
                i--;

            } else {

                // Place the larger nums2 element at the end.
                nums1[k] = nums2[j];
                j--;
            }

            // Move to the next available destination position.
            k--;
        }

        // Copy any remaining elements from nums2.
        while (j >= 0) {
            nums1[k] = nums2[j];
            j--;
            k--;
        }

        // Remaining nums1 elements are already correctly positioned.
    }
}
