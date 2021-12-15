/*
 Author : Ruel
 Problem : Baekjoon 2197번 분해 반응
 Problem address : https://www.acmicpc.net/problem/2197
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 분해반응;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int n, m;
    static List<List<Integer>> connection;
    static int[] children;
    static int[][] dp;
    static final int MAX = 1000;

    public static void main(String[] args) throws IOException {
        // 정원 정리(https://www.acmicpc.net/problem/1772)와 거의 같은 문제
        // 잎사귀가 원자로, 가지가 원자 연결로 바뀌었다
        // n개의 원자가 있고, 원자들이 n-1개의 연결로 이어져있을 때, 결합을 끊어
        // m개의 원자로 이뤄진 분자를 만들어내는 최소 결합 해제 횟수는?
        // 트리 형태를 띄고 있기 때문에, 하나를 루트로 선택한 뒤, 전체 트리에서 1개의 원자를 끊어내는 방법은,
        // 1에 속한 서브 트리에서 1개를 원자를 떼는 방법들 중 최소의 값을 가져오는 방식으로 bottom-up 방식으로 구성한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        connection = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connection.add(new ArrayList<>());
        for (int i = 1; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            connection.get(a).add(b);
            connection.get(b).add(a);
        }

        dp = new int[n + 1][n];
        for (int[] d : dp)
            Arrays.fill(d, MAX);

        children = new int[n + 1];
        fillChildren(1, new boolean[n + 1]);
        fillDP(1);

        int answer = dp[1][m - 1];          // m개의 원자가 연결되어있으려면 자신(1) + 자손 노드(m - 1)의 값이 필요.
        for (int i = 2; i < dp.length; i++)     // 다른 방법으로 루트가 아닌 노드에서는 자신 포함 m개인 횟수에서(dp[i][m - 1]) 자신과 부모노드와 연결된 결합을 끊는(1) 횟수.
            answer = Math.min(answer, dp[i][m - 1] + 1);
        System.out.println(answer);
    }

    static void fillDP(int parent) {
        for (int child : connection.get(parent)) {      // 연결된 자식 노드들을 순회하며
            fillDP(child);      // 자식 노드로 파생되는 서브트리에 대한 DP를 채우고 나서,
            for (int i = 0; i < children[parent]; i++) {    // parent와 연결된 원자의 개수가 i개가 되는 방법은
                for (int j = 1; j <= children[child] && i + j <= children[parent]; j++) {        // parent에 (i + j)개가 연결되어있을 때, child에서 j개를 떼는 방법
                    int leftAtoms = children[child] - j;        // 그 때 child에 연결된 원자들의 개수
                    dp[parent][i] = Math.min(dp[parent][i], dp[parent][i + j] + dp[child][leftAtoms]);
                }
                if (i + children[child] + 1 <= children[parent])        // parent 와 child 간의 연결을 끊어, (children[child] + 1)개의 원자를 끊어내는 방법.
                    dp[parent][i] = Math.min(dp[parent][i], dp[parent][i + children[child] + 1] + 1);
            }
        }
    }

    static int fillChildren(int parent, boolean[] visited) {        // parent에 연결된 전체 자손노드의 개수를 기록한다.
        visited[parent] = true;         // 방문체크
        List<Integer> ancestor = new LinkedList<>();
        int count = 0;      // 자식 노드의 개수
        for (int child : connection.get(parent)) {
            if (visited[child])     // 이미 방문한 적이 있다면 부모노드
                ancestor.add(child);
            else        // 아니라면 해당 child 원자로 생기는 서브 트리에 속한 모든 원자의 개수를 가져온다.
                count += fillChildren(child, visited);
        }
        connection.get(parent).removeAll(ancestor);     // 부모 노드는 제거
        dp[parent][0] = count;      // 자식 노드가 0개가 되려면 최악의 경우는 모든 노드를 일일이 떼는 횟수.
        dp[parent][count] = 0;      // 원래 count개의 원자가 붙어있기 때문에 필요한 횟수는 0
        return (children[parent] = count) + 1;      // parent에 붙은 원자들 + 1(자신)의 값을 반환한다.
    }
}