/*
 Author : Ruel
 Problem : Baekjoon 10976번 피난
 Problem address : https://www.acmicpc.net/problem/10976
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10976_피난;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int[][] capacities, flows;
    static List<List<Integer>> edges;

    public static void main(String[] args) throws IOException {
        // 1번 굴에서부터 n번굴까지 여왕개미를 피난시키고자 한다.
        // m개의 도로가 주어지며
        // 1번 굴과 n번 굴, 둘 중 하나와 연결된 굴은 한 마리의 개미만 이용할 수 있다고 한다.
        // 최대 몇 마리의 여왕 개미를 n번 굴로 피난시킬 수 있는가
        //
        // 최대 유량 문제
        // 1번 굴에서부터 n번 굴까지 주어지는 도로에 따라 용량과 흐름을 설정해주고
        // 최대 유량이 얼마가 되는지 계산한다
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 굴의 개수와 도로의 개수
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            
            // 용량과 흐름
            capacities = new int[n + 1][n + 1];
            flows = new int[n + 1][n + 1];
            
            // 통로 입력
            edges = new ArrayList<>();
            for (int i = 0; i < n + 1; i++)
                edges.add(new ArrayList<>());
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                
                // x에서 y로만 이동할 수 있지만
                edges.get(x).add(y);
                // 음의 유량도 고려해야하므로 y에서 x로의 통로도 있다고는 등록해두자
                edges.get(y).add(x);
                // 대신 용량은 x -> y로만 설정한다.
                // x == 1 이거나 y == n인 경우는 용량이 1, 그 외의 경우 무제한.
                capacities[x][y] = (x == 1 || y == n ? 1 : Integer.MAX_VALUE);
            }

            // ford fulkerson을 통해 문제를 해결하므로
            // 더 이상 새로운 흐름이 발생하지 않을 때까지 메소드를 호출한다.
            int sum = 0;
            while (true) {
                int newFlow = fordFulkerson(1, Integer.MAX_VALUE, new boolean[n + 1]);
                if (newFlow == 0)
                    break;
                sum += newFlow;
            }
            // 그 때까지의 합이 n번 굴까지 여왕 개미를 피난시킬 때, 최대수.
            sb.append(sum).append("\n");
        }
        // 전체 답안 출력.
        System.out.print(sb);
    }

    // Ford fulkerson
    static int fordFulkerson(int n, int flow, boolean[] visited) {
        // 최종 위치에 도달했다면
        // 도착한 흐름 만큼을 리턴.
        if (n == visited.length - 1)
            return flow;
        
        // 방문 체크
        visited[n] = true;
        // 나가는 유량
        int output = 0;
        for (int next : edges.get(n)) {
            // 더 이상 보낼 유량이 없다면 종료
            if (flow - output == 0)
                break;

            // 미방문이고, 잔여 용량이 존재하거나 음의 흐름이 발생할 수 있다면
            if (!visited[next] && capacities[n][next] - flows[n][next] > 0) {
                // 새로운 흐름을 흘려보낼 수 있는지 확인한다.
                int newFlow = fordFulkerson(next, Math.min(flow - output, capacities[n][next] - flows[n][next]), visited);
                // 발생한 흐름을 표시한다.
                flows[n][next] += newFlow;
                flows[next][n] -= newFlow;
                output += newFlow;
            }
        }
        // n에서 흘려보낼수 있는 흐름을 반환한다.
        return output;
    }
}