package org.example;

import java.util.Arrays;

public class IndexPriorityQueue {
    int n;
    int index;
    private int[] pq; // pq[i] = index in original data
    private int[] im; // im[index] = position in pq
    private Double[] val; // val[i] = key

    public IndexPriorityQueue(int n) {
        this.n = n;
        this.index = 0;
        pq = new int[n];
        im = new int[n];
        val = new Double[n];
        Arrays.fill(im, -1); // -1 means not in the heap
        Arrays.fill(val, Double.POSITIVE_INFINITY);
    }

    public void insertInPq(int i, Double cost) {
        if (im[i] != -1) return; // already inserted
        val[i] = cost;
        pq[index] = i;
        im[i] = index;
        swim(index);
        index++;
    }

    public void change(int i, Double cost) {
        val[i] = cost;
            swim(im[i]);
            sink(im[i]);
    }

    public boolean contains(int i) {
        return im[i] != -1;
    }

    public int poll() {
        int min = pq[0];
        swap(0, index - 1);
        index--;
        im[min] = -1;
        val[min] = Double.POSITIVE_INFINITY;
        sink(0);
        return min;
    }

    public boolean isEmpty() {
        return index == 0;
    }

    public void swim(int i) {
        int p = (i - 1) / 2;
        while (i > 0 && val[pq[i]] < val[pq[p]]) {
            swap(i, p);
            i = p;
            p = (i - 1) / 2;
        }
    }

    public void sink(int i) {
        while (2 * i + 1 < index) {
            int left = 2 * i + 1, right = 2 * i + 2;
            int smallest = left;

            if (right < index && val[pq[right]] < val[pq[left]]) {
                smallest = right;
            }

            if (val[pq[i]] <= val[pq[smallest]]) break;

            swap(i, smallest);
            i = smallest;
        }
    }

    private void swap(int f, int s) {
        int temp = pq[f];
        pq[f] = pq[s];
        pq[s] = temp;
        im[pq[f]] = f;
        im[pq[s]] = s;
    }
}
