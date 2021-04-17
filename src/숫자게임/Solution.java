package 숫자게임;

import java.util.Collections;
import java.util.PriorityQueue;

public class Solution {
    public static void main(String[] args) {
        // A와 B를 큰 수부터 비교하며,
        // B보다 A가 큰 경우의 수를 센다
        // A가 더 큰 경우, B가 더 클 때까지 다음 큰 수를 불러와서 카운트를 한다.

        int[] A = {5, 1, 3, 7};
        int[] B = {2, 2, 6, 8};

        PriorityQueue<Integer> aQueue = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Integer> bQueue = new PriorityQueue<>(Collections.reverseOrder());

        for (int i = 0; i < A.length; i++) {
            aQueue.add(A[i]);
            bQueue.add(B[i]);
        }

        int count = 0;
        while (!aQueue.isEmpty() && !bQueue.isEmpty()) {
            int a = aQueue.poll();
            int b = bQueue.poll();

            while (!aQueue.isEmpty() && a >= b) {
                a = aQueue.poll();
            }
            if (a < b)
                count++;
        }
        System.out.println(count);
    }
}