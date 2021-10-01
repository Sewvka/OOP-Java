package test.java;
import main.java.HeapSort;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class HeapSortTest {

    @Test
    @DisplayName("Sort Descending array without negative integers")
    public void testDescendingArray() {
        int[] array = {5, 4, 3, 2, 1};
        int[] expResult = Arrays.copyOf(array, array.length);
        Arrays.sort(expResult);
        HeapSort.sort(array);
        assertArrayEquals(expResult, array);
    }

    @Test
    @DisplayName("Basic array with negative integers")
    public void testHeapSort2() {
        int[] array = {1, -1, 1, 2, 3};
        int[] expResult = Arrays.copyOf(array, array.length);
        Arrays.sort(expResult);
        HeapSort.sort(array);
        assertArrayEquals(expResult, array);
    }

    @Test
    @DisplayName("Empty array")
    public void testHeapSort3() {
        int[] array = {};
        int[] expResult = {};
        HeapSort.sort(array);
        assertArrayEquals(expResult, array);
    }

    @Test
    @DisplayName("Test with various negative integers")
    public void testHeapSort4() {
        int[] array = {-123, 3, -4315, 2};
        int[] expResult = Arrays.copyOf(array, array.length);
        Arrays.sort(expResult);
        HeapSort.sort(array);
        assertArrayEquals(expResult, array);
    }

    @Test
    @DisplayName("Test with Max and Min values")
    public void testHeapSort5() {
        int[] array = {1, Integer.MAX_VALUE, Integer.MIN_VALUE};
        int[] expResult = Arrays.copyOf(array, array.length);
        Arrays.sort(expResult);
        HeapSort.sort(array);
        assertArrayEquals(expResult, array);
    }

    @Test
    @DisplayName("Test with already sorted array")
    public void testHeapSort6() {
        int[] array = {1, 2, 3, 4, 5};
        int[] expResult = Arrays.copyOf(array, array.length);
        Arrays.sort(expResult);
        HeapSort.sort(array);
        assertArrayEquals(expResult, array);
    }

    @Test
    @DisplayName("Put Null instead of array")
    public void testHeapSort7() {
        int[] array = null;
        assertThrows(IllegalArgumentException.class, ()->HeapSort.sort(array));
    }

}