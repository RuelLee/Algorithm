package 요세푸스문제;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 큐를 활용한 기본적인 문제.
        // 끝 -> 처음으로 이어지는 구조이며, k번째 원소를 계속하여 빼낼 때,
        // 뺀 원소들을 순서대로 출력.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int k = sc.nextInt();

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++)
            queue.add(i + 1);

        StringBuilder sb = new StringBuilder();
        sb.append("<");
        int count = k;
        while (queue.size() > 1) {
            count--;
            if (count == 0) {
                sb.append(queue.poll()).append(", ");
                count = k;
            } else
                queue.offer(queue.poll());
        }
        sb.append(queue.poll()).append(">");
        System.out.println(sb);
    }
}