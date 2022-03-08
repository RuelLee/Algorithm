/*
 Author : Ruel
 Problem : Baekjoon 1693번 트리 색칠하기
 Problem address : https://www.acmicpc.net/problem/1693
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1693_트리색칠하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static List<List<Integer>> connection;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        // n개의 노드로 이루어진 트리 형태와 노드 간의 연결이 주어진다
        // 각 노드를 1 ~ n까지의 색으로 칠하려고 하고 그 때의 비용은 각 번호에 해당하는 비용이 든다고 한다.
        // 인접한 노드들은 같은 색으로 칠할 수 없다고 할 때, 트리의 모든 노드를 색칠하는 최소 비용은 얼마인가
        //
        // 먼저 트리를 통한 다이내믹 프로그래밍이라는 느낌은 문제를 읽자마자 왔다
        // 하지만 DP의 기준을 무엇으로 설정해야할까라는 문제에 직면했다.
        // n이 최대 10만까지 주어지므로, dp[노드][색]으로 지정할 경우 10만 * 10만이라는 말도 안되는 공간이 낭비된다
        // 사실 n개의 노드가 주어지더라도 n개의 색을 모두 사용하는 것은 아니다
        // 최악의 상황을 고려해서 색의 영역을 줄여야한다
        // 1. 먼저 1개의 색으로 트리를 칠할 때 가능한 노드의 수는 1개이다
        // 2. 다음으로 2개의 색으로 트리를 칠하는 경우는 자식 노드가 1번 색을 사용했기 때문에
        // 루트 노드가 2번으로 칠해지는 경우다. 노드의 개수 2개일 때 발생할 수 있다.
        // 3. 다음으로 3개의 색으로 트리를 칠하는 경우, 자식 노드가 1, 2번 색을 사용한 경우다
        //         3
        //      1      2
        //           1         과 같은 형태가 될 것이다. 노드 4개일 때 발생할 수 있다.
        // 4. 다음으로 4개의 색으로 트리를 칠하는 경우, 자식 노드가 1, 2, 3번 색을 사용한 경우다.
        //              4
        //      1       2       3
        //           1       1     2
        //                       1         과 같은 형태를 띄는 경우다. 노드가 8개일 때 발생할 수 있다.
        // f(n)을 반드시 n개의 색을 사용해서 트리를 색칠하는 최소 노드의 개수라고 한다면
        // f(1) = 1, f(2) = f(1) + 1, f(3) = f(2) + f(1) + 1, ... , f(n) = f(n-1) + f(n-2) + ... + f(1) + 1
        // 의 형태를 띄고, 이는 2의 제곱 수의 형태를 띈다
        // 따라서 n개의 노드가 최악의 경우, Math.ceil(log(n) / log(2)) + 1개의 색을 사용함을 알 수 있다.
        // 이를 통해 본다면 n이 10만이 들어오더라도 2^17이 약 13만이므로, 17개 이하의 색을 사용함을 알 수 있다.
        // 이를 통해 bottom-up 방식으로 트리에서의 DP를 사용해주면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // dp[노드][색] 그 때의 최소 비용.
        dp = new int[n + 1][(int) Math.ceil(Math.log(n) / Math.log(2)) + 1];
        connection = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connection.add(new ArrayList<>());
        StringTokenizer st;
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            connection.get(a).add(b);
            connection.get(b).add(a);
        }

        setRoot(1, new boolean[n + 1]);     // 1번 노드는 항상 존재하므로 1번 노드를 루트 노드로 설정한 트리라고 가정하자.
        // 1번 루트 노드부터 bottom-up 방식으로 DP를 채워나간다.
        fillDP(1);

        // dp[1][i]는 1번 노드를 i색으로 칠할 때의 최소비용이 저장되어있다.
        // 그 중 최소값을 찾아 출력한다.
        System.out.println(Arrays.stream(dp[1]).min().getAsInt());
    }

    static void fillDP(int node) {      // bottom-up 방식으로 DP를 채운다.
        for (int child : connection.get(node))      // 자식 노드들의 DP를 먼저 채우고.
            fillDP(child);

        dp[node][0] = Integer.MAX_VALUE;        // 0번 색은 없으므로 MAX_VALUE로 세팅.
        for (int i = 1; i < dp[node].length; i++) {     // node를 i번 색으로 칠하는 경우
            dp[node][i] = i;        // 먼저 node를 i로 칠하는 비용 i
            for (int child : connection.get(node)) {        // 그리고 node의 자식 노드들을 탐색한다.
                int minValue = Integer.MAX_VALUE;       // 자식 노드가 i번 색이 아닌 최소 비용인 색을 찾아 그 가격을 가져온다.
                for (int j = 1; j < dp[child].length; j++) {
                    if (i == j)     // node와 child가 i색으로 같은 경우는 건너 뛰고
                        continue;
                    // 아닌 경우 최소값을 갱신해준다.
                    minValue = Math.min(minValue, dp[child][j]);
                }
                // 찾아진 최소값을 node가 i색일 때의 비용에 더해준다.
                dp[node][i] += minValue;
            }
        }
    }

    static void setRoot(int root, boolean[] visited) {      // root 노드로 설정하고, connection에서 조상 노드를 제외하는 메소드.
        visited[root] = true;       // 방문 체크

        List<Integer> ancestor = new ArrayList<>();
        for (int node : connection.get(root)) {
            if (visited[node])      // 이미 방문한 적이 있는 노드라면 조상 노드
                ancestor.add(node);
            else
                setRoot(node, visited);
        }
        // connection에서 조상 노드는 제거.
        connection.get(root).removeAll(ancestor);
    }
}