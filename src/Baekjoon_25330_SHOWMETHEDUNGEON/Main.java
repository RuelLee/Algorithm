/*
 Author : Ruel
 Problem : Baekjoon 25330번 SHOW ME THE DUNGEON
 Problem address : https://www.acmicpc.net/problem/25330
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25330_SHOWMETHEDUNGEON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1 ~ n의 마을이 주어지고, 초기 마을 0이 주어진다.
        // 0번 마을은 모든 마을과 직접적으로 연결되어있다. 1 ~ n번 마을을 서로 이어져있지 않다.
        // 각 마을에 대해 몬스터 공격력 A와 주민의 수P가 주어진다.
        // 초기 체력은 k이고, i번 마을을 방문하기 전에 t1, t2, ..., tk마을을 방문했다면
        // i번 마을을 방문할 때, A1 + A2 + ... + Ak + Ai의 체력이 소모된다.
        // 체력을 최대 k만큼 소모하며 가능한 많은 주민을 구하고자할 때, 그 수는?
        //
        // 비트마스킹, 최단 경로
        // n이 최대 20으로 주어지므로 bitmask를 통해 처리할 수 있고, 브루트포스도 가능하다.
        // 단 동일한 마을들을 방문하더라도 그 순서에 의해 소모되는 체력이 다를 수 있다.
        // 따라서 dijkstra를 통해 최단 경로로 방문한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 마을, 초기 체력 k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        // 각 마을 몬스터 공격력
        int[] attacks = new int[n + 1];
        for (int i = 1; i < attacks.length; i++)
            attacks[i] = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        // 각 마을 주민의 수
        int[] pops = new int[n + 1];
        for (int i = 1; i < pops.length; i++)
            pops[i] = Integer.parseInt(st.nextToken());

        int size = 1 << n;
        // 비트마스킹에 따른 몬스터 공격력 합
        int[] attackSums = new int[size + 1];
        // 인구 합
        int[] popSums = new int[size + 1];
        // 해당 상태로 마을들을 방문하는데 소모하는 체력의 최소값
        int[] hp = new int[size + 1];
        Arrays.fill(hp, Integer.MAX_VALUE);
        hp[0] = 0;
        
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
        priorityQueue.offer(new int[]{0, 0});
        // 구할 수 있는 최대 주민의 수
        int answer = 0;
        while (!priorityQueue.isEmpty()) {
            // 현재 상태와 소모 체력
            int[] current = priorityQueue.poll();
            // 더 체력을 소모해 현재 상태를 만들 수 있다면
            // 이미 이전에 계산됐을 것이므로 건너뜀.
            if (hp[current[0]] < current[1])
                continue;

            for (int i = 1; i <= n; i++) {
                // i번째 마을을 아직 방문하지 않았고
                if ((current[0] & (1 << (i - 1))) == 0) {
                    // i번 마을을 방문할 때의 비트마스킹
                    int next = (current[0] | (1 << (i - 1)));
                    // 그 때의 몬스터 공격력 합
                    attackSums[next] = attackSums[current[0]] + attacks[i];
                    // 인구 합
                    popSums[next] = popSums[current[0]] + pops[i];

                    // 누적 소모 체력이 k를 넘지 않고
                    // 더 적은 체력 소모로 next 상태를 만들 수 있다면
                    if (hp[current[0]] + attackSums[next] <= k && hp[next] > hp[current[0]] + attackSums[next]) {
                        // 해당 소모 값 기록
                        hp[next] = hp[current[0]] + attackSums[next];
                        // 현재 구한 최대 주민의 수
                        answer = Math.max(answer, popSums[next]);
                        // 우선순위큐에 추가
                        priorityQueue.offer(new int[]{next, hp[next]});
                    }
                }
            }
        }
        // 답 출력
        System.out.println(answer);
    }
}