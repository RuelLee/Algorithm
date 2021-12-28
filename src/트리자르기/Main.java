/*
 Author : Ruel
 Problem : Baekjoon 2337번 트리 자르기
 Problem address : https://www.acmicpc.net/problem/2337
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 트리자르기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int[] child;
    static int[][] dp;
    static List<List<Integer>> connection;
    static final int MAX = 151;

    public static void main(String[] args) throws IOException {
        // 정원 정리(https://www.acmicpc.net/problem/1772)와 같은 문제
        // 트리에서의 다이나믹 프로그래밍으로 bottom-up 방식으로 채워나가자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        child = new int[n + 1];
        dp = new int[n + 1][n];
        for (int[] d : dp)
            Arrays.fill(d, MAX);
        connection = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connection.add(new ArrayList<>());

        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            connection.get(a).add(b);
            connection.get(b).add(a);
        }
        // 트리 형태를 띄므로, 어느 곳을 루트 지정해도 상관X
        // 1번을 루트로 지정하고, 각 정점에서 자식 노드들의 합을 child 배열에 기록해준다.
        fillChild(1, new boolean[n + 1]);
        // root 노드로부터 DP채우기를 시작한다.(bottom-up이므로, 말단 노드부터 채워지며 root는 가장 마지막에 채워진다.)
        fillDP(1);
        // 우리가 원하는 건 정점이 m개이길 원한다
        // 따라서 전체 정점의 개수n에서 (n-m)개 만큼의 노드를 제한 개수가 m개이다.
        int answer = dp[1][n - m];
        // root 노드가 아니더라도 자식 노드의 개수가 m-1개 이상이라면 답이 될 수 있다.
        // root가 아닌 노드도 살펴보자.
        for (int i = 1; i < child.length; i++) {
            if (child[i] < m - 1)
                continue;
            // 해당 노드에서 자식노드들의 개수 - (m - 1)개를 하면 m - 1개의 자식과 자신까지 총 m개의 서브 트리가 생성된다
            // 여기서 자신과 부모노드가 연결된 가지를 끊어야하므로 + 1번 한 값이 answer 후보. 이 값이 answer보다 작다면 갱신해준다.
            answer = Math.min(answer, dp[i][child[i] - (m - 1)] + 1);
        }
        System.out.println(answer == MAX ? -1 : answer);
    }

    static void fillDP(int p) {     // p에서의 DP를 채운다.
        for (int c : connection.get(p)) {
            // p의 DP를 채우기 위해서는 자식 c의 DP가 먼저 채워져있어야한다.
            fillDP(c);
            // 가지를 안 자를 때의 횟수는 0번
            dp[p][0] = 0;

            // i를 역순으로 계산하는 이유는 정순으로 계산할 시, 중복으로 계산될 수 있다.
            // (p에서 n번 가지치기에 c 서브 트리에 대한 값이 반영되었는데, n+1번 가지치기할 때, p에서 n번 가지치기 + c에서 1번 가지치기 한 값으로 중복 반영될 수 있다.)
            for (int i = child[p]; i > 0; i--) {        // p의 가지 치는 횟수가 i번이 되는 경우는
                for (int j = 1; j <= child[c] && i - j >= 0; j++)       // p가 i-j번의 가지치기를 한 상태에서, c가 j번의 가지치기를 하는 경우
                    dp[p][i] = Math.min(dp[p][i], dp[p][i - j] + dp[c][j]);
                // 그리고 c가지를 통채로 한번에 잘라, child[c] + 1개의 가지를 치는 경우.
                if (i >= child[c] + 1)
                    dp[p][i] = Math.min(dp[p][i], dp[p][i - child[c] - 1] + 1);
            }
        }
    }

    static int fillChild(int p, boolean[] visited) {        // 자식 노드의 개수를 센다.
        visited[p] = true;      // 방문 체크
        int count = 0;
        List<Integer> ancestor = new ArrayList<>();     // 자신보다 조상 노드가 연결되어있다면 여기에 저장
        for (int c : connection.get(p)) {
            if (visited[c])     // 방문한 적이 있다면 조상 노드
                ancestor.add(c);
            else            // 아니라면 자신의 자식노드이므로, 자식 노드의 개수를 세어, count에 더해준다.
                count += fillChild(c, visited);
        }
        // connection 에서 부모노드들을 제거해준다.
        connection.get(p).removeAll(ancestor);
        // p를 루트노드로 하는 서브트리에서 count개만큼의 자식을 가지치는 횟수는 1번씩 최대 count번이다.
        dp[p][count] = count;
        // p의 자식 노드는 c의 자식노드의 개수 + c까지이므로 +1을 해 리턴한다.
        return (child[p] = count) + 1;
    }
}