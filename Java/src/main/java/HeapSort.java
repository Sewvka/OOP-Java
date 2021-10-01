package main.java;

public class HeapSort {
    public static void sort(int[] arr) {
        if (arr == null) {
            return;
        }
        int len = arr.length;
        for (int i = len / 2 - 1; i >= 0; i--)
            heapify(arr, len, i);
        for (int i = len - 1; i > 0; i--) {
            swap_int(arr, 0, i);
            heapify(arr, i, 0);
        }
    }


    private static void heapify(int[] array, int len, int i) {
        int max = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < len && array[l] > array[max])
            max = l;

        if (r < len && array[r] > array[max])
            max = r;

        if (max != i) {
            swap_int(array, i, max);
            heapify(array, len, max);
        }
    }
    private static void swap_int(int[] arr, int ind1, int ind2) {
        int temp = arr[ind1];
        arr[ind1] = arr[ind2];
        arr[ind2] = temp;
    }
}
