/*
 Author : Ruel
 Problem : Jungol 1429번 문제집
 Problem address : https://jungol.co.kr/problem/1429
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1429_문제집;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 문제가 주어진다. 각 문제는 번호의 오름차순에 따라 어려움을 느끼게 된다.
        // 각 문제 간에는 관계가 있어 가령 a문제를 푼 다음 b문제를 풀면 쉽게 느껴진다.
        // 이러한 관계가 m개 주어진다.
        // 문제를 풀되, n개의 문제를 모두 풀며, 관계가 있는 경우, 먼저 푸는게 좋은 문제는 먼저 푼다. 그리고 가능한 쉬운 문제들 부터 푼다.
        // 모든 문제를 푸는 순서를 출력하라
        //
        // 위상 정렬, 우선순위큐 문제
        // 문제에 따른 선후 관계가 주어지므로 위상 정렬을 사용해야한다.
        // 그리고 가능한 쉬운 문제를 우선으로 풀므로, 더 이상 관계가 없는 문제들에 대해
        // 우선순위큐로 정렬하여 오름차순으로 풀어나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 문제, m개의 관계
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        List<List<Integer>> connections = new ArrayList<>();
        for (int i = 0; i <= n; i++)
            connections.add(new ArrayList<>());

        // 진입 차수
        int[] indegree = new int[n + 1];
        // 관계 정리
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            connections.get(a).add(b);
            indegree[b]++;
        }

        // 관계가 없는 문제들을 우선순위큐에 담는다.
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 1; i <= n; i++) {
            if (indegree[i] == 0)
                pq.offer(i);
        }

        StringBuilder sb = new StringBuilder();
        // 우선순위큐가 빌 때까지
        while (!pq.isEmpty()) {
            // current 문제를 풀고
            int current = pq.poll();
            sb.append(current).append(" ");

            // 연관된 문제들의 진입차수를 하나씩 낮춘다.
            // 그러며 진입차수가 0이 된 문제는 우선순위큐에 담는다.
            for (int next : connections.get(current)) {
                if (--indegree[next] == 0)
                    pq.offer(next);
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        // 답 출력
        System.out.println(sb);
    }
}