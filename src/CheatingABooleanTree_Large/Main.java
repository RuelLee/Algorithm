/*
 Author : Ruel
 Problem : Baekjoon 12704번 Cheating a Boolean Tree (Large)
 Problem address : https://www.acmicpc.net/problem/12704
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package CheatingABooleanTree_Large;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int MAX = 10001;
    static int[][] internalNode;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        // 자식이 2개 or 0개인 이진트리가 주어진다
        // 말단 노드는 0 또는 1 값이 주어지고, 그렇지 않은 노드들은 and나 or 게이트 형태를 취하고 있으며 자식 노드에 의해 값이 결정되며, 몇몇 게이트들은 다른 게이트로 변경할 수 있다.
        // 게이트 변경을 최소로 하며 루트 노드의 값을 v를 만들고자할 때, 게이트 변경 횟수는?
        // 트리 형태의 DP 문제
        // 말단 노드의 값을 정해져있으므로, bottom-up 재귀로 값을 채워나가자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        for (int t = 0; t < n; t++) {
            st = new StringTokenizer(br.readLine());
            int m = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            internalNode = new int[(m - 1) / 2 + 1][2];         // 게이트 노드가 무슨 게이트며, 변경 가능한지 저장.
            for (int i = 1; i < (m - 1) / 2 + 1; i++) {
                st = new StringTokenizer(br.readLine());
                internalNode[i][0] = Integer.parseInt(st.nextToken());
                internalNode[i][1] = Integer.parseInt(st.nextToken());
            }
            dp = new int[m + 1][2];
            for (int[] d : dp)
                Arrays.fill(d, MAX);
            for (int i = (m - 1) / 2 + 1; i < m + 1; i++)       // 말단 노드는 값이 정해져있다.
                dp[i][Integer.parseInt(br.readLine())] = 0;

            fillDP(1, m);

            sb.append("Case #").append(t + 1).append(": ");
            sb.append(dp[1][v] == MAX ? "IMPOSSIBLE" : dp[1][v]).append("\n");
        }
        System.out.println(sb);
    }

    static void fillDP(int node, int m) {       // node의 DP를 채운다
        if (node > (m - 1) / 2)     // 말단 노드라면 채울 필요 없다.
            return;

        fillDP(node * 2, m);        // node의 DP를 채우기 위해선 자식 노드인 node * 2와
        fillDP(node * 2 + 1, m);        // node * 2 + 1을 먼져 채워야한다.

        if (internalNode[node][0] == 1) {       // node가 and 게이트라면
            fillAndDP(node, 0);     // and에 관한 최소값을 갱신하고
            if (internalNode[node][1] == 1)     // 변경이 가능하다면
                fillOrDP(node, 1);      // 횟수가 1회 늘어난 상태에서 or에 관한 최소값을 갱신한다.
        } else {        // node가 or 게이트라면
            fillOrDP(node, 0);      // or에 관한 최소값을 갱신하고
            if (internalNode[node][1] == 1)     // 변경이 가능하다면
                fillAndDP(node, 1);     // 횟수가 1회 늘어난 상태에서 and에 관한 최소값을 갱신한다.
        }
    }

    static void fillAndDP(int node, int changed) {      // and 게이트에 관한 메소드
        for (int i = 0; i < 2; i++) {       // node * 2가 i값
            for (int j = 0; j < 2; j++)     // node * 2 + 1가 j값일 때, i * j가 되는 최소값. changed는 게이트 변경이 안되었다면 0, 변경이 되었다면 1이 들어올 것이다.
                dp[node][i & j] = Math.min(dp[node][i & j], dp[node * 2][i] + dp[node * 2 + 1][j] + changed);
        }
    }

    static void fillOrDP(int node, int changed) {       // or 게이트에 관한 메소드
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++)
                dp[node][i | j] = Math.min(dp[node][i | j], dp[node * 2][i] + dp[node * 2 + 1][j] + changed);
        }
    }
}