package 야근지수;

import java.util.PriorityQueue;

public class Solution {
    public static void main(String[] args) {
        // n시간의 근무시간을 활용하여, 남은 작업들을 최대한 균등한 상태로 만들어야한다.
        // 가장 큰 작업을 꺼내, 다음 큰 작업의 소요 시간을 비교하여
        int[] works = {2, 1, 2};
        int n = 1;

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o2, o1));

        for (int i : works)
            priorityQueue.add(i);

        while (n > 0 && priorityQueue.peek() > 0) {
            int sel = priorityQueue.poll();
            int next = priorityQueue.peek();

            int diff = sel - next;
            if (diff == 0) {    // 두 값의 차이가 없을 땐 값을 1만 줄여준다.
                priorityQueue.add(sel - 1);
                n -= 1;
            } else {
                if (n >= diff) {    // 두 값이 차이가 있을 땐 diff와 n을 비교하여 작은 수만큼 차감하여준다.
                    priorityQueue.add(sel - diff);
                    n -= diff;
                } else {
                    priorityQueue.add(sel - n);
                    n = 0;
                }
            }
        }
        long totalSum = 0;
        for (int i : priorityQueue)
            totalSum += Math.pow(i, 2);

        System.out.println(totalSum);
    }
}