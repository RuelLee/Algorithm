/*
 Author : Ruel
 Problem : Baekjoon 9890번 택배
 Problem address : https://www.acmicpc.net/problem/8980
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 택배;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

class Box {
    int from;
    int to;
    int weight;

    public Box(int from, int to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}

public class Main {
    public static void main(String[] args) {
        // 그리디 문제는 만만한게 없는 것 같다
        // 어떤 식으로 선택해서 먼저 취하고 어떻게 표시할지 생각해내는 것이 관건인 것 같다.
        // 1번 마을부터 n번 마을까지 한 방향으로 차가 운행할 때, 최대 옮길 수 있는 택배 수를 구하는 문제다.
        // 자신보다 크지만 작은 마을에 얼른 배달하고 다음 물건을 받는 것이 이득이다
        // 따라서 목적지가 작은 숫자 순서대로 택배를 정렬한다
        // 그리고 각 마을에서 실을 수 있는 택배량을 각각 설정해주자
        // 그 후, 정렬된 택배를 순서대로 실으면서, 중간에 거쳐가는 마을들의 실을 수 있는 택배량을 체크하고, 가장 적은 량만큼을 빼주자
        // 위와 같은 연산을 반복하자.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int c = sc.nextInt();
        int m = sc.nextInt();

        PriorityQueue<Box> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.to, o2.to));      // 목적지에 따라 정렬한다.
        for (int i = 0; i < m; i++)
            priorityQueue.offer(new Box(sc.nextInt(), sc.nextInt(), sc.nextInt()));

        int[] capacities = new int[n];      // 각 마을에서 실을 수 있는 택배량
        Arrays.fill(capacities, c);
        int sum = 0;
        while (!priorityQueue.isEmpty()) {
            Box current = priorityQueue.poll();     // 택배를 하나 꺼내

            int minCapacity = Integer.MAX_VALUE;
            for (int i = current.from; i < current.to; i++) {       // from부터 to까지 거쳐가는 마을들의 실을 수 있는 택배의 최소량을 구하자
                minCapacity = Math.min(minCapacity, capacities[i]);
                if (minCapacity == 0)
                    break;
            }
            if (minCapacity == 0)       // 만약 0이라면 더 이상 연산할 것이 없다.
                continue;

            int allowedWeight = Math.min(minCapacity, current.weight);      // 현재 택배와 수용량 중 작은 값을 택해
            for (int i = current.from; i < current.to; i++)     // 중간에 거쳐가는 마을들의 수용량을 전부 줄여주자.
                capacities[i] -= allowedWeight;
            sum += allowedWeight;       // 총 이동시킨 택배량에 해당량 만큼 더해주자
        }
        System.out.println(sum);        // sum이 총 이동시킨 택배량.
    }
}