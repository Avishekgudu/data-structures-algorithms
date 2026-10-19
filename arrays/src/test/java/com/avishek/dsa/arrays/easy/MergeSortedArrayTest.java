
package com.avishek.dsa.arrays.easy;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;

class MergeSortedArrayTest {

    private final MergeSortedArray solution = new MergeSortedArray();

    @Test
    void shouldMergeTwoSortedArrays() {
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int[] nums2 = {2, 5, 6};

        solution.merge(nums1, 3, nums2, 3);

        assertArrayEquals(new int[]{1, 2, 2, 3, 5, 6}, nums1);
    }

    @Test
    void shouldHandleEmptySecondArray() {
        int[] nums1 = {1};

        solution.merge(nums1, 1, new int[]{}, 0);

        assertArrayEquals(new int[]{1}, nums1);
    }

    @Test
    void shouldHandleEmptyFirstArray() {
        int[] nums1 = {0};

        solution.merge(nums1, 0, new int[]{1}, 1);

        assertArrayEquals(new int[]{1}, nums1);
    }

    @Test
    void shouldHandleDuplicates() {
        int[] nums1 = {1, 2, 2, 0, 0};
        int[] nums2 = {2, 3};

        solution.merge(nums1, 3, nums2, 2);

        assertArrayEquals(new int[]{1, 2, 2, 2, 3}, nums1);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] nums1 = {-3, -1, 0, 0};
        int[] nums2 = {-2, 4};

        solution.merge(nums1, 2, nums2, 2);

        assertArrayEquals(new int[]{-3, -2, -1, 4}, nums1);
    }
}
