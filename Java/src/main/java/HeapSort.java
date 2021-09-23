package main.java;

public class HeapSort {
    static public void sort(int[] arr)
    {
        int len = arr.length;
        for (int i = len / 2 - 1; i >= 0; i--)
            heapify(arr, len, i);
        for (int i = len - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            heapify(arr, i, 0);
        }
    }


    static private void heapify(int[] array, int len, int i)
    {
        int max = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < len && array[l] > array[max])
            max = l;

        if (r < len && array[r] > array[max])
            max = r;

        if (max != i) {
            int swap = array[i];
            array[i] = array[max];
            array[max] = swap;

            heapify(array, len, max);
        }
    }
}
