/*
 Author : Ruel
 Problem : Baekjoon 6002번 Job Hunt
 Problem address : https://www.acmicpc.net/problem/6002
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_6002_JobHunt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // c개의 도시와 p개의 단방향 도로, 그리고 f개의 단방향 비행 노선이 주어진다.
        // 도로는 비용 없이 단방향으로 도시를 연결하고, 비행 노선은 비행 요금이 존재한다.
        // 요금은 목적 도시에 도착하여 일을 한 뒤 갚는 것도 가능하다.
        // 하나의 도시에서 최대 d원을 벌 수 있다.
        // 이 때 다른 도시에 들렸다가 다시 돌아와서 d원을 버는 것도 가능하다.
        // s 도시에 시작하여 최대로 많은 돈을 벌고자 한다면 얼마까지 벌 수 있는가
        // 무한히 많은 돈을 벌 수 있다면 -1을 출력한다.
        //
        // 최단 경로 문제
        // 일단 최단 경로로 각 경로에 도달하는 최대 금액을 계산한다.
        // 그런데 루프가 발생하여 같은 지점을 무한히 돌며 계속 돈을 벌 수 있다면 더 이상 탐색하지 않고 -1을 출력해야한다.
        // 따라서 각 도시를 방문하는 횟수를 계산하되, 최악의 경우, 한 도시가 다른 모든 도시에 연결되어 c-1번 방문이 가능할 수도 있다.
        // 따라서 카운트가 c 이상이 되는 경우, 사이클이 발생하여 무한히 돈을 버는 게 가능하다고 판단할 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 한 도시에서 벌 수 있는 최대 금액 d, p개의 단방향 도로, f개의 단방향 비행 노선, 시작 도시 s
        int d = Integer.parseInt(st.nextToken());
        int p = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        int f = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());
        
        // 각 도로 및 항공 노선 정보
        List<List<int[]>> roads = new ArrayList<>();
        for (int i = 0; i < c + 1; i++)
            roads.add(new ArrayList<>());
        for (int i = 0; i < p; i++) {
            st = new StringTokenizer(br.readLine());
            roads.get(Integer.parseInt(st.nextToken())).add(new int[]{Integer.parseInt(st.nextToken()), 0});
        }
        for (int i = 0; i < f; i++) {
            st = new StringTokenizer(br.readLine());
            roads.get(Integer.parseInt(st.nextToken())).add(new int[]{Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())});
        }
        
        // 각 도시까지 벌 수 있는 최대 금액
        int[] maxMoney = new int[c + 1];
        // 각 도시 방문 횟수
        int[] visitCounter = new int[c + 1];
        // 시작 도시
        maxMoney[s] = d;
        // 최대한 반복을 줄이기 위해 벌 수 있는 금액이 높은 순으로 살펴본다.
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o2[1], o1[1]));
        priorityQueue.offer(new int[]{s, d});
        boolean loop = false;
        int answer = 0;
        while (!priorityQueue.isEmpty()) {
            int[] current = priorityQueue.poll();
            // 최대 금액 갱신
            answer = Math.max(answer, current[1]);
            // 만약 방문 횟수 c 초과가 되었다면 사이클이 존재하므로 더 이상 계산하지 않는다.
            if (visitCounter[current[0]]++ > c) {
                loop = true;
                break;
            } else if (maxMoney[current[0]] > current[1])   // 더 많은 금액으로 해당 도시까지 돈을 버는 것이 가능했다면 건너뛴다.
                continue;

            // 이동 가능한 다음 도시들을 살펴본다.
            for (int[] next : roads.get(current[0])) {
                // 다음 도시로 이동할 때, 얻을 수 있는 최대 금액과 현재 다음 도시까지 벌 수 있다고 계산된 금액을 비교하여
                // current -> next로 이동하는 것이 유리한지 살펴보고 그렇다면 값 갱신 후 큐에 추가
                if (maxMoney[next[0]] < maxMoney[current[0]] + d - next[1]) {
                    maxMoney[next[0]] = maxMoney[current[0]] + d - next[1];
                    priorityQueue.offer(new int[]{next[0], maxMoney[next[0]]});
                }
            }
        }
        // 사이클이 존재한다면 -1
        // 그 외의 경우 최대 금액 answer 출력
        System.out.println(loop ? -1 : answer);
    }
}