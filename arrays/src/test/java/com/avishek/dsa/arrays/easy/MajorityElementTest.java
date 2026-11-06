
package com.avishek.dsa.arrays.easy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class MajorityElementTest {

    private final MajorityElement solution = new MajorityElement();

    @Test
    void shouldFindMajorityElement() {
        assertEquals(3, solution.majorityElement(
                new int[]{3, 2, 3}));
    }

    @Test
    void shouldHandleCandidateChanges() {
        assertEquals(2, solution.majorityElement(
                new int[]{2, 2, 1, 1, 1, 2, 2}));
    }

    @Test
    void shouldHandleSingleElement() {
        assertEquals(5, solution.majorityElement(
                new int[]{5}));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals(-1, solution.majorityElement(
                new int[]{-1, 2, -1, -1, 2}));
    }

    @Test
    void shouldHandleAllEqualElements() {
        assertEquals(4, solution.majorityElement(
                new int[]{4, 4, 4, 4}));
    }

    @Test
    void shouldHandleMajorityAtEnd() {
        assertEquals(7, solution.majorityElement(
                new int[]{1, 2, 7, 7, 7, 7, 7}));
    }
}
