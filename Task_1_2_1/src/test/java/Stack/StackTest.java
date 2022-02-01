package Stack;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;

import static org.junit.jupiter.api.Assertions.*;

class StackTest {
    static ArrayList<int[]> testArrays() {
        var list = new ArrayList<int[]>();

        list.add(new int[]{5, 4, 3, 2, 1});
        list.add(new int[]{0, 0, 0, 0, 0});
        list.add(new int[]{1});
        list.add(new int[]{2, 1});
        list.add(new int[]{-4, 8, -3, 6, -2, 4, -1, 2});
        list.add(new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE});

        return list;
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, -3, 15, Integer.MAX_VALUE})
    public void testStackPushSingle(int arg) {
        Stack<Integer> testedStack = new Stack<Integer>(1);
        testedStack.push(arg);
        int res = testedStack.pop();
//      New StackIterator
//        Stack.StackIterator iterat = new Stack.StackIterator();

        assertEquals(arg, res);
    }

    @ParameterizedTest
    @MethodSource("testArrays")
    public void testStackPushStack(int[] arg) {
        Integer[] input = Arrays.stream(arg).boxed().toArray(Integer[]::new);
        Stack<Integer> testedStack = new Stack<Integer>(input.length);
        Stack<Integer> pushedStack = new Stack<Integer>(input);

        testedStack.pushStack(pushedStack);

        Stack<Integer> resultStack = testedStack.popStack(input.length);
        Integer[] resultArr = new Integer[input.length];

        for (int i = 0; i < input.length; i++) {
            resultArr[input.length - 1 - i] = resultStack.pop();
        }

        assertArrayEquals(resultArr, input);

    }

    @Test
    public void testStackEmptyInit() {
        assertThrows(IllegalArgumentException.class, ()->new Stack<Integer>(0));
    }

    @ParameterizedTest
    @NullSource
    public void testStackNullInit(Integer[] input) {
        assertThrows(IllegalArgumentException.class, ()->new Stack<Integer>(input));
    }

    @Test
    public void testStackNullEmptyArrayInit() {
        Integer[] input = new Integer[] {};
        assertThrows(IllegalArgumentException.class, ()->new Stack<Integer>(input));
    }

    @Test
    public void testStackPushNull() {
        Stack<Integer> testedStack = new Stack<Integer>(5);
        assertThrows(IllegalArgumentException.class, ()->testedStack.push(null));
    }

    @Test
    public void testStackPopFromEmpty() {
        Stack<Integer> testedStack = new Stack<Integer>(5);
        assertThrows(EmptyStackException.class, ()->testedStack.pop());
    }

}