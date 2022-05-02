/*
 Author : Ruel
 Problem : Baekjoon 10217번 KCM Travel
 Problem address : https://www.acmicpc.net/problem/10217
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10217_KCMTravel;

import java.util.*;

class Ticket {
    int end;
    int cost;
    int time;

    public Ticket(int end, int cost, int time) {
        this.end = end;
        this.cost = cost;
        this.time = time;
    }
}

public class Main {
    static final int INF = 100 * 1000;          // 문제 조건에 공항의 수는 100개, 티켓의 최대 시간은 1000이므로 이를 곱한 수를 INF 로 정해주자.

    public static void main(String[] args) {
        // 다익스트라 알고리즘을 쓰되, 특정 공항에 도착할 때의 비용에 따른 시간을 갖고 있어야한다.
        // 특정 공항에 최소시간으로 도착했더라도 비용이 초과해 최종목적지에 못 다다를 수 있기 때문.
        // 따라서 int 이차원 배열로 minTime[도착공항][도착비용] = 도착시간을 저장해준다.
        // 도착시간만 고려하는 것이 아닌 도착비용도 고려하기 때문에 당연히 가짓수가 늘어난다.
        // 이는 도착시간이 최소인 것부터 우선순위큐를 활용하여 처리하고 최종 목적지에 도달하였을 때 바로 연산을 그만하도록하자.

        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        StringBuilder sb = new StringBuilder();
        for (int tc = 0; tc < T; tc++) {
            int n = sc.nextInt();
            int m = sc.nextInt();
            int k = sc.nextInt();

            List<Ticket>[] lists = new List[n + 1];     // 인접리스트
            for (int i = 1; i < lists.length; i++)
                lists[i] = new ArrayList<>();

            for (int i = 0; i < k; i++)
                lists[sc.nextInt()].add(new Ticket(sc.nextInt(), sc.nextInt(), sc.nextInt()));

            int[][] minTime = new int[n + 1][m + 1];        // minTime[도착지][도착비용] = 도착시간
            for (int[] a : minTime)
                Arrays.fill(a, INF);

            PriorityQueue<Ticket> priorityQueue = new PriorityQueue<>(((o1, o2) -> {
                if (o1.time == o2.time)         // 소요 시간이 같다면 비용이 낮은 것을
                    return Integer.compare(o1.cost, o2.cost);
                return Integer.compare(o1.time, o2.time);       // 일반적으로는 소요 시간이 더 적은 것을 우선적으로 처리한다.
            }));

            priorityQueue.add(new Ticket(1, 0, 0));     // 출발
            int answer = INF;
            while (!priorityQueue.isEmpty()) {
                Ticket current = priorityQueue.poll();
                if (current.end == n) {     // 최종목적지에 도달했다면
                    answer = Math.min(answer, current.time);        // answer 값을 갱신하고 종료.
                    break;
                }

                // 최종목적지에 아직 도착하지 못했다면, 해당 공항에서 갈 수 있는 다른 공항들을 살펴보자.
                for (Ticket next : lists[current.end]) {
                    if (current.cost + next.cost > m)       // 비용이 예산을 넘는다면 의미가 없다. 다음으로 continue
                        continue;

                    if (minTime[next.end][current.cost + next.cost] > current.time + next.time) {       // 최소 시간이 갱신된다면
                        minTime[next.end][current.cost + next.cost] = current.time + next.time;     // 값 갱신하고
                        priorityQueue.add(new Ticket(next.end, current.cost + next.cost, current.time + next.time));        // 우선순위큐에 담아 다음 번 연산을 하도록하자.
                    }
                }
            }
            // answer 가 INF라면 주어진 예산으로 도착이 불가능. Poor KCM 출력. 아니라면 answer 출력.
            sb.append(answer == INF ? "Poor KCM" : answer).append("\n");
        }
        System.out.println(sb);
    }
}