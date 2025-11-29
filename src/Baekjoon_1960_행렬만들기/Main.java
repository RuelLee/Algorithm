/*
 Author : Ruel
 Problem : Baekjoon 1960번 행렬만들기
 Problem address : https://www.acmicpc.net/problem/1960
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1960_행렬만들기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[][] capacity, flows;

    public static void main(String[] args) throws IOException {
        // n * n 크기의 행렬이 주어진다.
        // 각 격자 안에는 1 혹은 0이 들어있다.
        // 각 행과 열에 있는 총 1의 개수가 주어진다.
        // 가능한 행렬을 만들어 출력하라
        //
        // 최대 유량 문제
        // 각 행과 열에 해당하는 1의 개수가 주어진다.
        // start -> 행 -> 열 -> end로 이어지는 흐름을 만든다.
        // strat -> 행은 각 행에 배치된 1의 개수.
        // 각 각 행에서 각 열에는 하나의 1만 배치할 수 있음으로 1
        // 열 -> end는 각 열에 배치된 1의 개수
        // 만큼을 capacity로 갖는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n * n 크기의 행렬
        int n = Integer.parseInt(br.readLine());

        // start + n개의 행 + n개의 열 + end 
        int size = n * 2 + 2;
        // 최대 유량
        capacity = new int[size][size];
        flows = new int[size][size];

        StringTokenizer st = new StringTokenizer(br.readLine());
        // 각 행의 1의 개수 합과 열의 1의 개수 합
        int rowSum = 0, colSum = 0;
        // start -> 행
        for (int i = 0; i < n; i++)
            rowSum += capacity[0][i + 1] = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        // 열 -> end
        for (int i = 0; i < n; i++)
            colSum += capacity[n + i + 1][n * 2 + 1] = Integer.parseInt(st.nextToken());
        // 행 -> 열
        for (int i = 1; i <= n; i++) {
            for (int j = n + 1; j <= 2 * n; j++)
                capacity[i][j]++;
        }

        // 만약 rowSum과 colSum이 다르다면 불가능한 경우.
        boolean possible = (rowSum == colSum);
        boolean[] visited = new boolean[size];
        // 더 이상 유량이 발생하지 않을 때까지 새로운 흐름을 흘린다.
        while (true && possible) {
            Arrays.fill(visited, false);
            int newFlow = fordFulkerson(0, Integer.MAX_VALUE, visited);
            rowSum -= newFlow;
            if (newFlow == 0)
                break;
        }

        StringBuilder sb = new StringBuilder();
        // 정상적으로 rowSum과 colSum이 같았고, 모든 1을 배치한 경우라면
        // 가능한 경우
        if (possible && rowSum == 0) {
            // 1 기록 후
            sb.append(1).append("\n");
            // 행과 열에 따른 1의 배치 기록
            for (int i = 1; i <= n; i++) {
                for (int j = n + 1; j <= 2 * n; j++)
                    sb.append(flows[i][j]);
                sb.append("\n");
            }
        } else      // 위의 경우에 속하지 않는다면 불가능한 경우
            sb.append(-1).append("\n");
        // 출력
        System.out.print(sb);
    }

    // idx번 노드에 flow만큼 흘러들어올 때
    // idx에서 흘려보낼 수 있는 만큼을 반환한다.
    static int fordFulkerson(int idx, int flow, boolean[] visited) {
        // end에 도착했다면 도착한 만큼을 반환
        if (idx == capacity.length - 1)
            return flow;
        
        // 방문 체크
        visited[idx] = true;
        int sum = 0;
        // idx -> i로 흘려보낸다.
        for (int i = 0; i < capacity.length; i++) {
            // 미방문 노드이고, capacity - flows가 양수인 경우
            // 추가적으로 흘려보낼 수 있다
            if (!visited[i] && capacity[idx][i] - flows[idx][i] > 0) {
                // 새로운 흐름
                int newFlow = fordFulkerson(i, Math.min(flow - sum, capacity[idx][i] - flows[idx][i]), visited);
                // sum에 누적
                sum += newFlow;
                // flows에 기록
                flows[idx][i] += newFlow;
                flows[i][idx] -= newFlow;
            }
        }
        // idx에서 발생한 흐름 반환.
        return sum;
    }
}