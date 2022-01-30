package Stack;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Iterator;

/**
 * Represents an array that functions like a stack data structure.
 *
 * @param <E> - type of element that will be stored within stack.
 */
public class Stack<E> implements Iterable<E> {
    private Object[] stackArr;
    private int stackSize;

    /**
     * Initializes an empty stack.
     *
     * @param capacity - initial length of stack.
     */
    public Stack(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Stack size must be at least 1!");
        }

        this.stackSize = 0;
        this.stackArr = new Object[capacity];
    }

    /**
     * Initializes a stack by taking an existing array as a base.
     *
     * @param arr - This array is copied into the stack.
     */
    public Stack(E[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array can't be null!");
        }

        if (arr.length < 1) {
            throw new IllegalArgumentException("Array size must be at least 1!");
        }

        this.stackSize = arr.length;
        this.stackArr = new Object[arr.length+5];

        System.arraycopy(arr, 0, this.stackArr, 0, arr.length);
    }

    /**
     * Checks if stack is full and resizes it if more space is needed.
     */
    private void resize() {
        if (stackArr.length <= stackSize) {
            stackArr = Arrays.copyOf(stackArr, stackArr.length * 2);
        }
    }

    /**
     * Pushes an element to the end of the stack.
     *
     * @param elem - the element that needs to be pushed.
     */
    public void push(E elem) {
        if (elem == null) throw new IllegalArgumentException("Cannot push null element to stack!");

        stackSize++;
        resize();
        stackArr[stackSize - 1] = elem;
    }

    /**
     * Removes the last element of the stack.
     *
     * @return returns the value of the removed element.
     */
    public E pop() {
        if (stackSize == 0) throw new EmptyStackException();

        E poppedElem = (E) stackArr[stackSize - 1];
        stackArr[stackSize - 1] = null;
        stackSize--;
        return poppedElem;
    }

    /**
     * Pushes an array to the stack.
     *
     * @param pushedStack - stack that needs to be pushed.
     */
    public void pushStack(Stack<E> pushedStack) {
        int count = pushedStack.count();
        for (int i = 0; i < count; i++) {
            push(pushedStack.pop());
        }
    }

    /**
     * Removes multiple elements from the top of the stack.
     *
     * @param elemNum - number of elements that need to be removed.
     *
     * @return - returns the Stack that was popped.
     */
    public Stack<E> popStack(int elemNum) {
        Stack<E> returnStack = new Stack<E>(elemNum);
        for (int i = 0; i < elemNum; i++) {
            returnStack.push(pop());
        }

        return returnStack;
    }

    /**
     * @return - returns the amount of non-empty elements in the stack.
     */
    public int count() {
        return stackSize;
    }

    //@Override
    public Iterator<E> iterator() {
        return new StackIterator();
    }

    public class StackIterator implements Iterator<E> {
        int current;

        public StackIterator() {
            current = 0;
        }

        //@Override
        public boolean hasNext() {
            return (current < stackArr.length - 1);
        }

        //@Override
        public E next() {
            E data = (E) stackArr[current];
            current++;
            return data;
        }
    }
}