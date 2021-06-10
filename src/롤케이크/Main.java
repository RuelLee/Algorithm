package 롤케이크;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // 롤케이크
        // 롤케이크와 롤케이크를 자를 수 있는 횟수 m이 주어질 때
        // 길이가 10인 롤케이를 최대한 많이 만들 때 그 수.
        // 10으로 나눠떨어지면서, 작은 숫자가 앞으로 와야한다. (20의 경우 한 번 자르는 것으로 2개가 생긴다.)
        // 우선순위큐에, Comparator 를 지정해줌으로써 10의 배수일 때 앞으로 + 크기 순 으로 정렬되도록 만들자.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 % 10 == 0) {
                    if (o2 % 10 == 0)
                        return Integer.compare(o1, o2);
                    return -1;
                } else if (o2 % 10 == 0)
                    return 1;
                else
                    return Integer.compare(o1, o2);
            }
        });
        for (int i = 0; i < n; i++)
            priorityQueue.add(sc.nextInt());

        int count = 0;
        while (!priorityQueue.isEmpty() && m > 0) {
            int current = priorityQueue.poll();
            if (current <= 10) {
                if (current == 10)
                    count++;
                continue;
            }
            if (current - 10 <= 10) {
                if (current - 10 == 10)
                    count += 2;
                else
                    count++;
            } else {
                priorityQueue.add(current - 10);
                count++;
            }
            m--;
        }
        System.out.println(count);
    }
}