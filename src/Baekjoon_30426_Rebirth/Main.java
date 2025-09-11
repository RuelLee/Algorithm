/*
 Author : Ruel
 Problem : Baekjoon 30426번 Rebirth
 Problem address : https://www.acmicpc.net/problem/30426
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30426_Rebirth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 차원이 있고, 현재 m차원에 있다.
        // k개의 문제가 주어지며 각각 맞았을 때와 틀렸을 때의 Gi와 Yi가 주어진다.
        // c차원에서 i번째 문제를 맞았다면 (c + Gi) % n, 틀렸다면 (c + Yi) % n 차원으로 이동한다.
        // l개의 불안정한 차원이 존재하며, 해당 차원에 도달한 순간 미아가 되어 이동하지 못한다.
        // m차원에서 k개의 문제를 풀고, 0차원으로 이동가능한지 출력하라
        //
        // DP 문제
        // dp[문제번호][차원] = 방문가능여부로 세우고 문제를 푼다.
        // 문제가 최대 3000개 주어지므로 dp[3000]을 세워야하나
        // 사실 이전 결과만 있으면 되므로, dp[2]로 선언해, 두 개의 배열을 왔다갔다거리면서 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 차원, 현재 차원 m
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        // 문제의 개수 k
        int k = Integer.parseInt(st.nextToken());
        
        // 문제 정보
        int[][] problems = new int[k][2];
        for (int i = 0; i < problems.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < problems[i].length; j++)
                problems[i][j] = Integer.parseInt(st.nextToken());
        }
        
        // 불안정한 차원
        int l = Integer.parseInt(br.readLine());
        boolean[] unstable = new boolean[n];
        for (int i = 0; i < l; i++)
            unstable[Integer.parseInt(br.readLine())] = true;
        
        // 차원 방문 가능 여부
        boolean[][] visited = new boolean[2][n];
        // 현재 m차원에 존재
        visited[0][m] = true;
        for (int i = 0; i < k; i++) {
            // 다음 결과값을 초기화
            Arrays.fill(visited[(i + 1) % 2], false);

            for (int j = 0; j < visited[i % 2].length; j++) {
                // j번 차원이 불안정하지 않고, 방문이 가능하다면
                // 해당 차원에서 i번 문제를 맞거나 틀렸을 때
                // 이동가능한 차원 표시
                if (!unstable[j] && visited[i % 2][j]) {
                    visited[(i + 1) % 2][(j + problems[i][0]) % n] = true;
                    visited[(i + 1) % 2][(j + problems[i][1]) % n] = true;
                }
            }
        }
        // 최종적으로 k번 문제를 푼 후, 0번 차원에 도달할 수 있는지에 따른 결과 출력
        System.out.println(visited[k % 2][0] ? "utopia" : "dystopia");
    }
}