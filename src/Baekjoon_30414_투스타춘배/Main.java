/*
 Author : Ruel
 Problem : Baekjoon 30414번 투스타 춘배
 Problem address : https://www.acmicpc.net/problem/30414
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30414_투스타춘배;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<List<Integer>> connections;
    static int[] a, b, dp;

    public static void main(String[] args) throws IOException {
        // n개의 산이 n-1개의 길로 연결되어있다. 트리 형태를 띄고 있다.
        // p번 산에 n명인 부대가 있다.
        // 모든 산의 현재 높이가 a[1] , ..., a[n]으로 주어질 때,
        // b[1], ..., b[n]으로 바꾸고 싶다.
        // 다음과 같은 행동들을 할 수 있다.
        // * 산의 높이를 x만큼 깎는다. x만큼의 토양을 얻는다.
        // * 갖고 있는 토양을 사용하여 x만큼 높이를 높인다.
        // * 흙을 x만큼 구매한다. 비용이 x만큼 소모된다.
        // * 다른 산으로 이동한다. 이미 지나왔던 길은 이용할 수 없다.
        // 모든 산을 원하는 높이로 바꾸는데 드는 최소 비용은?
        //
        // 트리 dp, dfs 문제
        // dfs를 통해 탐색을 하며 다음과 같은 절차를 따른다.
        // 1. 자식 노드들의 부족한 토양분을 모두 계산한다.
        // 2. 자신의 여분의 토양이 있다면 자식 노드들의 부족분을 이를 통해 채우고
        // 3. 그렇지 않다면 자식 노드들의 부족분 + 자신의 부족분을 부모 노드에 청구한다.
        // 위 과정을 반복하여, 최종적으로 루트 노드에는 각 서브트리에서 부모 노드가 자식 노드들에게
        // 부족한 토양을 보내주고서, 부족분을 구매한 금액이 합산 계산될 것이다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 산, 시작 노드 p
        int n = Integer.parseInt(st.nextToken());
        int p = Integer.parseInt(st.nextToken());
        
        // 현재 높이
        a = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < a.length; i++)
            a[i] = Integer.parseInt(st.nextToken());
        // 원하는 높이
        b = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < b.length; i++)
            b[i] = Integer.parseInt(st.nextToken());

        // 산들 간의 연결 정보
        connections = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connections.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            connections.get(u).add(v);
            connections.get(v).add(u);
        }
        
        // 자신을 루트로 하는 서브 트리의 부족 토양 청구 금액
        dp = new int[n + 1];
        System.out.println(findAnswer(p, new boolean[n + 1]));
    }

    // 현재 node 노드, 방문 체크 visited
    static int findAnswer(int node, boolean[] visited) {
        // 방문 체크
        visited[node] = true;

        // 자식 노드들의 부족분을 합산한다.
        for (int child : connections.get(node)) {
            if (!visited[child])
                dp[node] += findAnswer(child, visited);
        }

        // node의 흙이 잉여분이 있다면
        // 청구된 금액의 일부 혹은 전체를 잉여분을 통해 메꾼다.
        if (a[node] >= b[node])
            dp[node] = Math.max(dp[node] - (a[node] - b[node]), 0);
        // 자신도 부족하다면, 청구 금액을 합산한다.
        else
            dp[node] += b[node] - a[node];
        // 해당 금액을 반환한다.
        return dp[node];
    }
}