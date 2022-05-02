/*
 Author : Ruel
 Problem : Baekjoon 12900번 Cheating a Boolean Tree
 Problem address : https://www.acmicpc.net/problem/12900
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12900_CheatingABooleanTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int MAX = 10_001;
    static int[][] internalNode;
    static int[][] dp;
    static int m, v;

    public static void main(String[] args) throws IOException {
        // 포화 이진 트리의 형태가 주어진다
        // 그 중 internal node(1 ~ (i - m) / 2)는 자신의 자식들의 값에 의해 값이 정해진다
        // internal node는 and 혹은 or 값을 가지며, 몇몇의 internal node는 변경이 가능하다
        // 전체 트리의 값을 v로 하고자할 때, internal node의 값을 최소한으로 변경하고자할 때의 변경 횟수
        // 트리의 다이나믹 프로그래밍
        // DP를 통해 각 노드가 0 또는 1을 값을 갖는 최소 변경 횟수를 저장하자
        // 말단 노드는 주어진 값이 변경이 불가능하므로, 주어진 값에 대해선 변경횟수가 0, 다른 값에 대해선 MAX 값으로 정해두자
        // 그 후, root(1번 노드)로 부터 bottom - up 방식으로 DP를 채워 답을 찾자!
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            st = new StringTokenizer(br.readLine());

            m = Integer.parseInt(st.nextToken());       // 노드의 개수
            v = Integer.parseInt(st.nextToken());       // 원하는 전체 트리의 값.
            internalNode = new int[(m - 1) / 2 + 1][2];     // internal node에 대한 정보를 저장.
            for (int i = 1; i < (m - 1) / 2 + 1; i++) {
                st = new StringTokenizer(br.readLine());
                internalNode[i][0] = Integer.parseInt(st.nextToken());
                internalNode[i][1] = Integer.parseInt(st.nextToken());
            }
            dp = new int[m + 1][2];     // 각 노드가 0 또는 1 값을 갖는 최소 internal node 변경 횟수를 저장한다.
            for (int[] d : dp)
                Arrays.fill(d, MAX);        // MAX 값으로 전체를 초기화해두자.
            for (int i = (m - 1) / 2 + 1; i < m + 1; i++)       // 단말 노드에 주어진 값이 되는데, internal node의 최소 변경 횟수는 0
                dp[i][Integer.parseInt(br.readLine())] = 0;
            fillDP(1);      // root 노드로부터 재귀 시작!
            sb.append("Case #").append(t + 1).append(": ");
            sb.append(dp[1][v] == MAX ? "IMPOSSIBLE" : dp[1][v]).append("\n");
        }
        System.out.println(sb);
    }

    static void fillDP(int node) {
        if (node > (m - 1) / 2)     // (m - 1) / 2 보다 큰 번호의 노드라며 단말 노드다. DP를 채울 필요 없다.
            return;

        fillDP(node * 2);       // 아니라면 자신의 자식 노드인 node * 2, node * 2 + 1 노드의 DP를 채워준다.
        fillDP(node * 2 + 1);

        if (internalNode[node][0] == 0) {       // node가 or 값을 갖는다면
            orCalc(node, 0);        // or 그대로일 때 최소값을 갱신하고
            if (internalNode[node][1] == 1)     // and 로 변경이 가능하다면, 변경하고 나서의 최소값을 갱신한다(변경했으므로 + 1)
                andCalc(node, 1);
        } else {        // 마찬가지로 node가 and 값을 갖는다면
            andCalc(node, 0);       // and 그대로일 때 최소값을 갱신하고
            if (internalNode[node][1] == 1)     // or 로 변경이 가능하다면, 변경하고 나서의 최소값을 갱신한다.
                orCalc(node, 1);
        }
    }

    static void andCalc(int node, int switched) {       // internal node가 and 값일 때
        for (int i = 0; i < 2; i++) {       // node * 2 노드의 값이 i일 때
            for (int j = 0; j < 2; j++)     // node * 2 노드의 값이 j일 때
                dp[node][i & j] = Math.min(dp[node][i & j], dp[node * 2][i] + dp[node * 2 + 1][j] + switched);
            // dp[node][i & j]의 값과 dp[node * 2][i] + dp[node * 2 + 1][j] 값을 비교해 최소라면 갱신한다.
            // switched 값은 node 가 원래 and 였다면 0, or 였다면 1로 들어와 변경에 따른 최소값 반영을 해준다.
        }
    }

    static void orCalc(int node, int switched) {        // internal node가 or 값일 때
        for (int i = 0; i < 2; i++) {       // node * 2 노드의 값이 i 일때
            for (int j = 0; j < 2; j++)     // node * 2 + 1 노드의 값이 j일 때
                dp[node][i | j] = Math.min(dp[node][i | j], dp[node * 2][i] + dp[node * 2 + 1][j] + switched);
            // dp[node][i | j]의 값과 dp[node * 2][i] + dp[node * 2 + 1][j] 값을 비교해 최소라면 갱신한다.
        }
    }
}