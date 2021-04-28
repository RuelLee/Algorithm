package 최솟값만들기;

import java.util.PriorityQueue;

public class Solution {
    public static void main(String[] args) {
        // A, B의 숫자들을 같은 순서끼리 곱한 값의 총합이 최소일 때 그 값을 구하라는 문제.
        // 최대값 * 최대값이 되어선 안된다!
        // 최소값 * 최대값 -> 그 다음 최소값 * 그다음 최대값 ....
        // 한 배열은 오름차순, 한 배열은 내림차순으로 값을 뽑아내서 서로 곱해주자.
        int[] A = {1, 4, 2};
        int[] B = {5, 4, 4};

        PriorityQueue<Integer> aQueue = new PriorityQueue<>();
        PriorityQueue<Integer> bQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o2, o1));

        for (int i = 0; i < A.length; i++) {
            aQueue.add(A[i]);
            bQueue.add(B[i]);
        }

        int total = 0;
        while (!aQueue.isEmpty())
            total += aQueue.poll() * bQueue.poll();

        System.out.println(total);
    }
}