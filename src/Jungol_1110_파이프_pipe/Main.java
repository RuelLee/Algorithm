/*
 Author : Ruel
 Problem : Jungol 1110번 파이프(pipe)
 Problem address : https://jungol.co.kr/problem/1110
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1110_파이프_pipe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[][] flows, capacities;

    public static void main(String[] args) throws IOException {
        // m개의 파이프, n-2개의 마을이 주어진다.
        // 1번은 강, n번은 목적지 마을을 뜻한다.
        // 강에서 목저지 마을로 최대한 많은 물을 보내고자 한다.
        // 각 마을 간에 이어진 파이프와 용량이 주어질 때, 한번에 보낼 수 있는 최대 물의 양을 출력하라
        //
        // 최대 유량 문제
        // 각 연결된 마을 파이프의 용량 capacities와 현재 마을에서 다음 마을에 실제로 흘러가는 유량 flows을 배열에 정리하고
        // 1번 마을에서 지속적으로 물을 흘려보내 일을 더 이상 물이 흐르는 경로가 생기지 않을 때까지 반복한다.
        // 그 때까지의 유량합을 누적하여 출력한다.
        // 이 때, 현재 파이프에 물이 가득 찼더라도 다른 경로를 통해 물이 추가적으로 흘러갈 수 있으므로
        // 음의 유량을 발생시켜 탐색하여야한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // m개의 파이프, n개의 지점들
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        // 실제 흘러가는 유량
        flows = new int[n + 1][n + 1];
        // 파이프 용량
        capacities = new int[n + 1][n + 1];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            // 같은 마을 사이에 여러 파이프가 연결될 수 있다.
            capacities[s][e] += w;
        }

        int sum = 0;
        boolean[] visited = new boolean[n + 1];
        while (true) {
            Arrays.fill(visited, false);
            // 더 이상 흐르지 않을 때까지 1번 마을에서 계속 물을 흘려보낸다.
            int newFlow = makeNewFlow(1, Integer.MAX_VALUE, visited);
            if (newFlow == 0)
                break;
            // 그 때까지의 새로운 유량을 누적
            sum += newFlow;
        }
        // 답 출력
        System.out.println(sum);
    }

    // current 지점에, currentFlow 만큼의 물이 도착했을 때
    // currnet에서 흘려보낼 수 있는 유량을 반환한다.
    static int makeNewFlow(int current, int currentFlow, boolean[] visited) {
        // 목적지에 도달했다면 currentFlow 자체를 반환
        if (current == flows.length - 1)
            return currentFlow;

        // 방문 체크
        visited[current] = true;
        // 남은 물의 양
        int remain = currentFlow;
        for (int next = 1; next < capacities[current].length; next++) {
            // 미방문이고, 파이프에 여유가 있을 때
            if (!visited[next] && capacities[current][next] - flows[current][next] > 0) {
                // 새로운 물을 흘려보낸다.
                int newFlow = makeNewFlow(next, Math.min(remain, capacities[current][next] - flows[current][next]), visited);
                // 해당 만큼을 유량에 누적시키고
                flows[current][next] += newFlow;
                // 음의 유량도 발생시킨다.
                flows[next][current] -= newFlow;
                // remain에서 해당 유량 만큼 차감
                remain -= newFlow;
            }
        }
        // 총 흘린 유량을 반환
        return currentFlow - remain;
    }
}