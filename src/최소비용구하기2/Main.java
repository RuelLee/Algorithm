/*
 Author : Ruel
 Problem : Baekjoon 11778번 최소비용 구하기 2
 Problem address : https://www.acmicpc.net/problem/11779
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 최소비용구하기2;

import java.util.*;

class City {
    int end;
    int cost;

    public City(int end, int cost) {
        this.end = end;
        this.cost = cost;
    }
}

public class Main {
    static List<City>[] roads;
    static boolean[] check;
    static int[] minCosts;
    static int[] preCity;
    static final int MAX = 1_000_000_000;

    public static void main(String[] args) {
        // 간단한 다익스트라 문제.
        // 최종 위치까지의 경로를 표현해야하므로, 해당 지점에 도착하기 이전 위치를 기록해주고
        // 마지막에 최종 위치부터 거슬러올라가면서 스택에 담아주면 된다.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        init(n);

        for (int i = 0; i < m; i++)
            roads[sc.nextInt() - 1].add(new City(sc.nextInt() - 1, sc.nextInt()));

        int start = sc.nextInt() - 1;
        int end = sc.nextInt() - 1;

        PriorityQueue<City> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.cost, o2.cost));
        priorityQueue.add(new City(start, 0));
        minCosts[start] = 0;

        while (!priorityQueue.isEmpty()) {
            City current = priorityQueue.poll();
            if (check[current.end])
                continue;

            for (City next : roads[current.end]) {
                if (!check[next.end] && minCosts[next.end] > minCosts[current.end] + next.cost) {
                    minCosts[next.end] = minCosts[current.end] + next.cost;
                    preCity[next.end] = current.end;
                    priorityQueue.add(new City(next.end, minCosts[next.end]));
                }
            }
            check[current.end] = true;
        }

        Stack<Integer> stack = new Stack<>();
        stack.push(end + 1);
        while (preCity[stack.peek() - 1] != -1)
            stack.push(preCity[stack.peek() - 1] + 1);

        StringBuilder sb = new StringBuilder();
        sb.append(minCosts[end]).append("\n");
        sb.append(stack.size()).append("\n");
        sb.append(stack.pop());
        while (!stack.isEmpty())
            sb.append(" ").append(stack.pop());
        System.out.println(sb);
    }

    static void init(int n) {
        roads = new List[n];
        for (int i = 0; i < roads.length; i++)
            roads[i] = new ArrayList<>();

        check = new boolean[n];
        minCosts = new int[n];
        Arrays.fill(minCosts, MAX);
        preCity = new int[n];
        Arrays.fill(preCity, -1);
    }
}