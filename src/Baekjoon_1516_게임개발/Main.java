/*
 Author : Ruel
 Problem : Baekjoon 1516번 게임 개발
 Problem address : https://www.acmicpc.net/problem/1516
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1516_게임개발;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static List<List<Integer>> canDevelop;

    public static void main(String[] args) throws IOException {
        // n종류의 건물이 있고,
        // n개의 건물에 대한 건축 시간과, 선행 건물이 주어진다
        // 각 건물들을 세우는데 필요한 최소 시간은?
        //
        // 위상 정렬 문제
        // 각 건물들의 진입 차수를 설정해주고, 진입차수가 0이 된 건물들을 세워나가며
        // 해당 건물을 세움으로써 건설이 가능해지는 건물들을 살펴본다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        int[] times = new int[n + 1];       // 각 건물들의 건축 시간
        int[] inDegree = new int[n + 1];        // 진입 차수
        canDevelop = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            canDevelop.add(new ArrayList<>());

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            times[i + 1] = Integer.parseInt(st.nextToken());        // 건추 시간
            int preBuilt = Integer.parseInt(st.nextToken());        // 선행 건물
            while (preBuilt != -1) {
                canDevelop.get(preBuilt).add(i + 1);        // preBuilt를 먼저 지어야만 i + 1 건물을 세울 수 있다.
                inDegree[i + 1]++;      // 진입차수 증가.
                preBuilt = Integer.parseInt(st.nextToken());
            }
        }

        int[] finishedTime = new int[n + 1];        // 각 건물들의 최소 건축 시간.
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i < inDegree.length; i++) {
            if (inDegree[i] == 0) {     // 처음부터 건설이 가능한(= 진입 차수가 0인) 건물들을 먼저 큐에 넣는다.
                queue.offer(i);
                finishedTime[i] = times[i];     // 해당 건물의 최소 건축 시간은 건설하는데 드는 시간만 소요한다.
            }
        }

        while (!queue.isEmpty()) {
            int current = queue.poll();
            // current 건물이 세워졌으므로, 다음 후행 건물들을 살펴보자.

            // current를 세움으로써 next를 세울 수 있게 된다.
            for (int next : canDevelop.get(current)) {
                inDegree[next]--;       // next의 진입차수를 낮춰주고
                // next의 선행 건물들 중 가장 늦게 완성되는 건물의 시간을 계산할 필요가 있다
                // 진입 차수가 0이 되기 전까진, next의 선행 건물들이 완성되는 가장 늦은 시간을 기록해두자.
                finishedTime[next] = Math.max(finishedTime[next], finishedTime[current]);
                // 진입 차수가 0이 되었다면 곧바로 건설 명령을 내린다.
                if (inDegree[next] == 0) {
                    // 선행 건물들이 모두 세워진 가장 늦은 시간이 finishedTime[next]에 기록되어있다
                    // 여기에 건설 시간만 더해주면 된다.
                    finishedTime[next] += times[next];
                    // 그리고 queue에 넣어 next의 후행 건물들을 살펴본다.
                    queue.offer(next);
                }
            }
        }
        // 기록된 건축물들의 최소 건축 시간을 출력한다.
        System.out.println(Arrays.stream(finishedTime, 1, finishedTime.length).mapToObj(String::valueOf).collect(Collectors.joining("\n")));
    }
}