/*
 Author : Ruel
 Problem : Baekjoon 9470번 Strahler 순서
 Problem address : https://www.acmicpc.net/problem/9470
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9470_Strahler순서;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // t개의 테스트케이스
        // 각 테스트케이스마다 m개의 노드, p개의 간선이 주어진다.
        // 하천을 노드와 간선으로 나타낸다.
        // Strahler 순서는 다음과 같이 구한다.
        // 강의 근원의 순서는 1
        // 나머지 노드들은 해당 노드로 들어오는 강의 순서 중 가장 큰 값을 i라 했을 때
        // 해당 i가 하나라면 i, 2개 이상이면 i + 1값을 갖는다.
        // 하천 정보가 주어졌을 때 Strahler 순서를 구하라.
        //
        // 위상 정렬 문제
        // 위상 정렬로 강의 근원부터 시작하여 끝날 때까지 순서대로 살펴본다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int k = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());

            // 진입 차수
            int[] inDegree = new int[m];
            List<List<Integer>> edges = new ArrayList<>(m);
            for (int i = 0; i < m; i++)
                edges.add(new ArrayList<>());
            for (int i = 0; i < p; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken()) - 1;
                int b = Integer.parseInt(st.nextToken()) - 1;

                edges.get(a).add(b);
                inDegree[b]++;
            }

            // 진입 차수 순서대로 살펴본다.
            Queue<Integer> queue = new LinkedList<>();
            int[] strahler = new int[m];
            for (int i = 0; i < inDegree.length; i++) {
                if (inDegree[i] == 0) {
                    queue.offer(i);
                    strahler[i] = 1;
                }
            }

            int[] maxIncome = new int[m];
            int maxStrahler = 0;
            while (!queue.isEmpty()) {
                int current = queue.poll();
                // 각 노드의 strahler 값들 중 최대값이 전체 하천의 strahler 값.
                maxStrahler = Math.max(maxStrahler, strahler[current]);

                // current에서 뻗어나가는 다음 노드들을 살펴본다.
                for (int next : edges.get(current)) {
                    // 만약 next에 들어오는 상류들 중 current의 값이 가장 크다면
                    // next에 들어오는 income과 strahler값을 current의 strahler 값으로 바꿔준다.
                    if (maxIncome[next] < strahler[current])
                        maxIncome[next] = strahler[next] = strahler[current];
                    // 만약 current strahler 값과 같은 상류를 next가 갖고 있다면
                    // next의 strahler값은 current보다 1 크다.
                    else if (maxIncome[next] == strahler[current])
                        strahler[next] = strahler[current] + 1;

                    // next의 진입차수를 하나 감소시키고
                    // 만약 진입차수가 0이 되었다면 큐에 넣어 다음에 살펴본다.
                    if (--inDegree[next] == 0)
                        queue.offer(next);
                }
            }
            // 답안을 StringBuilder에 기록.
            sb.append(k).append(" ").append(maxStrahler).append("\n");
        }
        // 전체 답안 출력.
        System.out.print(sb);
    }
}