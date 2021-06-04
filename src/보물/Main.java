package 보물;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 두 수열이 주어질 때, 각각 한 숫자를 선택하여 곱한 값을 더했을 때 그 최소의 값을 구하여라.
        // 두 수열에서 가장 큰 수 * 가장 작은 수로 선택되도록 하여 큰 수끼리 서로 곱해지지 않게 해야한다.
        // PriorityQueue 를 활용하여 한 수열은 정순으로, 한 수열은 역순으로 정렬하여 곱하여 더해주자.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        PriorityQueue<Integer> queue1 = new PriorityQueue<>();
        for (int i = 0; i < n; i++)
            queue1.add(sc.nextInt());
        PriorityQueue<Integer> queue2 = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o2, o1);
            }
        });
        for (int i = 0; i < n; i++)
            queue2.add(sc.nextInt());

        int sum = 0;
        for (int i = 0; i < n; i++)
            sum += queue1.poll() * queue2.poll();

        System.out.println(sum);
    }
}
