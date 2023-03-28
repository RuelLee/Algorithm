/*
 Author : Ruel
 Problem : Baekjoon 17222번 위스키 거래
 Problem address : https://www.acmicpc.net/problem/17222
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17222_위스키거래;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 주은이와 명진이는 사적으로 위스키를 거래하는 사이이다.
        // 주은이는 가능한 위스키를 많이 사고 싶어하지만, 자신의 평판과 품위 때문에 많은 양을 직구매하기는 꺼려한다.
        // 따라서 명진이는 명진의 친구들에게 위스키를 보내고
        // 명진이의 친구들은 주은이의 친구들 혹은 명진이의 친구들에게, 주은이의 친구들은 주은이에게 위스키를 보낸다.
        // 각 친구들도 자신들의 평판 때문에 한 사람에게 받을 수 있는 위스키의 양이 정해져있다.
        // 주은이가 최대 몇 병의 위스키를 구할 수 있는가?
        //
        // 최대 유량 문제
        // 여태까지 Ford Fulkerson 알고리즘으로 주로 구현한 것 같아 이번에는
        // BFS 형태로 탐색하는 Edmonds Karp 알고리즘으로 구현해보았다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 명진이와 주은이의 친구들의 수
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        // source = 명진, sink = 주은
        int source = 0;
        int sink = n + m + 1;
        
        // 각 친구들이 한 사람에게 받을 수 있는 최대 위스키의 수
        int[] maxIncome = new int[sink + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < n + 1; i++)
            maxIncome[i] = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        for (int i = n + 1; i < n + m + 1; i++)
            maxIncome[i] = Integer.parseInt(st.nextToken());

        long[][] capacities = new long[sink + 1][sink + 1];
        // 주은이의 친구들은 주은이에게 무한대로 보낼 수 있다.
        for (int i = 1; i < n + 1; i++)
            capacities[i][sink] = Long.MAX_VALUE;
        // 명진이의 친구들은 자신이 갖고 있는 연락망을 토대로
        // 위스키들을 보낼 수 있다.
        for (int i = n + 1; i < sink; i++) {
            st = new StringTokenizer(br.readLine());
            // frineds 명의 연락처를 알고 있고
            int friends = Integer.parseInt(st.nextToken());
            for (int j = 0; j < friends; j++) {
                // friend에게 friend의 제한만큼 위스키를 보낼 수 있다.
                int friend = Integer.parseInt(st.nextToken());
                capacities[i][friend] = maxIncome[friend];
            }
            // 명진이의 친구인 i는 명진이에게 자신의 제한만큼 받을 수 있다.
            capacities[source][i] = maxIncome[i];
        }
        
        // 실제로 보내는 위스키의 수
        long[][] flows = new long[sink + 1][sink + 1];
        // BFS를 통해 탐색한다.
        while (true) {
            // 이전 위치를 기록한다.
            int[] preLoc = new int[sink + 1];
            Arrays.fill(preLoc, -1);
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(source);
            while (!queue.isEmpty()) {
                int current = queue.poll();
                // 현재 위치가 주은이라면 더 이상 탐색하지 않고 위스키를 보내기 시작한다.
                if (current == sink)
                    break;

                // current에서 위스키를 보낼 수 있는 친구들을 탐색한다.
                for (int next = 0; next < capacities[current].length; next++) {
                    if (capacities[current][next] - flows[current][next] > 0 && preLoc[next] == -1) {
                        queue.offer(next);
                        preLoc[next] = current;
                    }
                }
            }
            // 만약 BFS를 통해 주은이에게 도착하는 위스키가 없다면 더이상의 BFS 탐색을 하지 않고 종료한다.
            if (preLoc[sink] == -1)
                break;

            // 주은이가 받을 수 있는 경로가 존재한다면
            // 기록된 경로를 토대로 보낼 수 있는 최대 위스키의 수를 구한다.
            long flow = Long.MAX_VALUE;
            int loc = sink;
            while (preLoc[loc] != -1) {
                flow = Math.min(flow, capacities[preLoc[loc]][loc] - flows[preLoc[loc]][loc]);
                loc = preLoc[loc];
            }

            // 구한 최대 위스키의 수를 실제 flows에 기록해주고
            // 다음 BFS를 통해 다른 경로를 찾기 시작한다.
            loc = sink;
            while (preLoc[loc] != -1) {
                flows[preLoc[loc]][loc] += flow;
                loc = preLoc[loc];
            }
        }

        // 주은이에게 도착한 위스키의 수를 구한다.
        long sum = 0;
        for (int i = 1; i < n + 1; i++)
            sum += flows[i][sink];
        // 답안 출력
        System.out.println(sum);
    }
}