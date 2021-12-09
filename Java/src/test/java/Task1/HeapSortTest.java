package Task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HeapSortTest {
    static Stream<Arguments> arrayProvider() {
        return Stream.of(
                Arguments.of(new int[]{5, 4, 3, 2, 1}, "Sort Descending array without negative integers"),
                Arguments.of(new int[]{1, -1, 1, 2, 3}, "Basic array with negative integers"),
                Arguments.of(new int[]{-123, 3, -4315, 2}, "Test with various negative integers"),
                Arguments.of(new int[]{}, "Empty array"),
                Arguments.of(new int[]{1, Integer.MAX_VALUE, Integer.MIN_VALUE}, "Test with Max and Min values"),
                Arguments.of(new int[]{1, 2, 3, 4, 5}, "Test with already sorted array")
        );
    }

    @DisplayName("A parameterized test")
    @ParameterizedTest(name = "{index}: {1}")
    @MethodSource("arrayProvider")
    void heapSortTest(int[] sampleArray, String name) {
        int[] sampleArraySorted = sampleArray.clone();
        Arrays.sort(sampleArraySorted);
        HeapSort.sort(sampleArray);
        assertArrayEquals(sampleArray, sampleArraySorted);
    }

    @Test
    @DisplayName("Put Null instead of array")
    public void emptyArrayTest() {
        int[] array = null;
        assertThrows(IllegalArgumentException.class, ()->HeapSort.sort(array));
    }

}