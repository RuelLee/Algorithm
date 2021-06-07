package 가운데를말해요;

import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 값이 들어올 때마다 이를 계산하여 중앙값을 구하려면 시간이 오래 걸린다.
        // PriorityQueue 2개를 이용하여 큰 순서대로 값을 꺼내는 leftQueue 와 값을 작은 순서대로 꺼내는 rightQueue 로 만들자
        // 두 큐의 사이즈를 비교하여 right 가 크거나 같다면, left 에 넣고,
        // left 가 크다면 right 에 넣도록하자.
        // 그 후, left의 peek()와 right의 peek()를 비교하여 left 가 더 크다면 두 값을 교체한다.
        // left.peek()가 중앙값으로 맞춰진다.

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        PriorityQueue<Integer> leftQueue = new PriorityQueue<>(((o1, o2) -> Integer.compare(o2, o1)));
        PriorityQueue<Integer> rightQueue = new PriorityQueue<>();

        StringBuilder sb = new StringBuilder();
        leftQueue.add(sc.nextInt());
        sb.append(leftQueue.peek()).append("\n");

        for (int i = 0; i < n - 1; i++) {
            if (leftQueue.size() <= rightQueue.size())
                leftQueue.add(sc.nextInt());
            else
                rightQueue.add(sc.nextInt());

            if (leftQueue.peek() > rightQueue.peek()) {
                int temp = leftQueue.poll();
                leftQueue.add(rightQueue.poll());
                rightQueue.add(temp);
            }
            sb.append(leftQueue.peek()).append("\n");
        }
        System.out.println(sb);
    }
}