package com.avishek.dsa.arrays.easy;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class TwoSumTest {

    @Test
    void shouldReturnIndicesWhenPairExists() {

        int[] nums = {2, 7, 11, 15};
        int target = 9;

        int[] result = TwoSum.twoSum(nums, target);

        assertArrayEquals(new int[] {0, 1}, result);
    }

    @Test
    void shouldReturnEmptyArrayWhenPairDoesNotExist() {

        int[] nums = {1, 2, 3};
        int target = 10;

        int[] result = TwoSum.twoSum(nums, target);

        assertArrayEquals(new int[0], result);
    }
}