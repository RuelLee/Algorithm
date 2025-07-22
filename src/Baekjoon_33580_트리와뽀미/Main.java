/*
 Author : Ruel
 Problem : Baekjoon 33580번 트리와 뽀미
 Problem address : https://www.acmicpc.net/problem/33580
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_33580_트리와뽀미;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static List<List<Integer>> connections;

    public static void main(String[] args) throws IOException {
        // n개의 노드로 이루어진 트리가 주어진다.
        // 각 단위 시간마다 고양이의 출몰 장소가 주어진다.
        // 각 단위 시간마다 연결된 노드로 이동하거나, 해당 장소에 머무를 수 있다.
        // 첫 시작 위치는 원하는 곳에서 시작할 수 있다했을 때, 고양이를 만날 수 있는 최대 횟수는?
        //
        // DP 문제
        // dp[시간][위치] = 만난 최대 횟수로 dp를 채워나간다.
        // 시작 지점은 마음대로 선택이 가능하므로, 각 시간마다 고양이의 위치에 해당하는 값엔 1로 주고 시작한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 노드, 시간 t
        int n = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());

        // 트리 형태
        connections = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connections.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            connections.get(a).add(b);
            connections.get(b).add(a);
        }

        // dp[시간][위치] = 만난 횟수
        int[][] dp = new int[t + 1][n + 1];
        st = new StringTokenizer(br.readLine());
        // 고양이 출몰 지역
        int[] c = new int[t];
        // 각 시간의 고양이 출몰 위치에서 처음부터 기다리고 있었다는 설정
        for (int i = 0; i < t; i++)
            dp[i + 1][c[i] = Integer.parseInt(st.nextToken())] = 1;

        for (int i = 0; i < dp.length - 1; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                // 고양이를 만난 횟수가 0인 값은 건너뛴다.
                if (dp[i][j] == 0)
                    continue;
                
                // 시간 i, 위치 j에서 next로 이동하는 경우
                for (int next : connections.get(j))
                    dp[i + 1][next] = Math.max(dp[i + 1][next], dp[i][j] + (c[i] == next ? 1 : 0));
                // j에 그대로 남아있는 경우
                dp[i + 1][j] = Math.max(dp[i + 1][j], dp[i][j] + (c[i] == j ? 1 : 0));
            }
        }

        // 최대 만난 횟수를 찾는다.
        int max = 0;
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++)
                max = Math.max(max, dp[i][j]);
        }
        // 답 출력
        System.out.println(max);
    }
}