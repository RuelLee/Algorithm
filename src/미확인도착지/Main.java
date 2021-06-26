package 미확인도착지;

import java.util.*;

class Cost {
    int n;
    int cost;

    public Cost(int n, int cost) {
        this.n = n;
        this.cost = cost;
    }
}

public class Main {
    public static void main(String[] args) {
        // 다익스트라 문제이되, 특정 구간을 지나는지 확인해야한다
        // pi 배열을 설정하고, 자신이 해당 지점에 오기 위해선 어느 지점에서 왔는지 표시한다.
        // -> 틀렸습니다! -> 동일을 비용으로 다른 구간을 통해서 도달할 수 있다면 답으로 선택되지 않을 수 있다.
        // 특정 구간의 비용을 미세하게 적게 줘서, 같은 비용으로 도달할 수 있다면, 특정 구간이 우선적으로 선택되도록 해야한다
        // 다른 구간의 비용은 10배, 특정 구간의 비용은 9로 하여, 약 10%의 차이가 나도록 한다. -> 일반적인 경우에 경로 선택에 의미를 미치지 않을 것이다
        // 다만 동일한 비용으로 도달할 수 있는 다른 구간이 존재하더라도 우선적으로 특정 구간을 선택할 것이다.
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        StringBuilder stringBuilder = new StringBuilder();
        for (int tc = 0; tc < T; tc++) {
            int n = sc.nextInt();
            int m = sc.nextInt();
            int t = sc.nextInt();

            int s = sc.nextInt();
            int g = sc.nextInt();
            int h = sc.nextInt();

            List<Cost>[] costList = new List[n + 1];
            for (int i = 0; i < costList.length; i++)
                costList[i] = new ArrayList<>();

            for (int i = 0; i < m; i++) {
                int a = sc.nextInt();
                int b = sc.nextInt();
                int d = sc.nextInt();

                if ((a == g && b == h) || (a == h && b == g)) {     // 특정 구간에 대해서 비용을 9배
                    costList[a].add(new Cost(b, d * 9));
                    costList[b].add(new Cost(a, d * 9));
                } else {        // 그 외에는 10배
                    costList[a].add(new Cost(b, d * 10));
                    costList[b].add(new Cost(a, d * 10));
                }
            }

            Queue<Integer> candidate = new LinkedList<>();
            int[] pi = new int[n + 1];
            boolean[] check = new boolean[n + 1];
            for (int i = 0; i < t; i++)
                candidate.add(sc.nextInt());

            PriorityQueue<Cost> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.cost, o2.cost));
            Cost[] minCosts = new Cost[n + 1];
            for (int i = 0; i < minCosts.length; i++) {
                if (i == s)
                    minCosts[i] = new Cost(i, 0);
                else
                    minCosts[i] = new Cost(i, Integer.MAX_VALUE);
                priorityQueue.add(minCosts[i]);
            }

            while (!priorityQueue.isEmpty()) {
                Cost current = priorityQueue.poll();

                if (current.cost == Integer.MAX_VALUE)
                    break;

                for (Cost road : costList[current.n]) {
                    if (!check[road.n] && minCosts[road.n].cost > current.cost + road.cost) {
                        pi[road.n] = current.n;
                        minCosts[road.n].cost = current.cost + road.cost;
                        priorityQueue.remove(minCosts[road.n]);
                        priorityQueue.add(minCosts[road.n]);
                    }
                }
                check[current.n] = true;
            }
            List<Integer> answer = new ArrayList<>();
            while (!candidate.isEmpty()) {
                int current = candidate.poll();
                int temp = current;
                while (temp != 0) {
                    if ((temp == g && pi[temp] == h) || (temp == h && pi[temp] == g)) {     // 각 목적지 후보마다 pi 배열을 쫓아가며 g - h or h - g 구간이 발견된다면 답안에 넣고 브레이크.
                        answer.add(current);
                        break;
                    }
                    temp = pi[temp];
                }
            }
            Collections.sort(answer);
            if (!answer.isEmpty()) {
                for (int num : answer)
                    stringBuilder.append(num).append(" ");
                stringBuilder.append("\n");
            }
        }
        System.out.println(stringBuilder);
    }
}