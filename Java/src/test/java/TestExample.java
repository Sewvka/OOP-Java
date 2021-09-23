package test.java;
import main.java.HeapSort;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapSortTest {

    @Test
    public void testHeapSort1() {
        int[] array = {5, 4, 3, 2, 1};
        int[] expResult = {1, 2, 3, 4, 5};
        HeapSort.sort(array);
        assertArrayEquals(expResult, array);
    }

    @Test
    public void testHeapSort2() {
        int[] array = {1, -1, 1, 2, 3};
        int[] expResult = {-1, 1, 1, 2, 3};
        HeapSort.sort(array);
        assertArrayEquals(expResult, array);
    }

    @Test
    public void testHeapSort3() {
        int[] array = {};
        int[] expResult = {};
        HeapSort.sort(array);
        assertArrayEquals(expResult, array);
    }

    @Test
    public void testHeapSort4() {
        int[] array = {-123, 3, -4315, 2};
        int[] expResult = {-4315, -123, 2, 3};
        HeapSort.sort(array);
        assertArrayEquals(expResult, array);
    }

    @Test
    public void testHeapSort5() {
        int[] array = {1, Integer.MAX_VALUE, Integer.MIN_VALUE};
        int[] expResult = {Integer.MIN_VALUE, 1, Integer.MAX_VALUE};
        HeapSort.sort(array);
        assertArrayEquals(expResult, array);
    }

    @Test
    public void testHeapSort6() {
        int[] array = {1, 2, 3, 4, 5};
        int[] expResult = {1, 2, 3, 4, 5};
        HeapSort.sort(array);
        assertArrayEquals(expResult, array);
    }

}