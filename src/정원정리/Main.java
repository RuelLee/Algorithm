/*
 Author : Ruel
 Problem : Baekjoon 1772번 정원 정리
 Problem address : https://www.acmicpc.net/problem/1772
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 정원정리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<List<Integer>> connection;
    static int[] children;
    static int[][] dp;
    static int n, m;
    static final int MAX = 1000;

    public static void main(String[] args) throws IOException {
        // n개의 정점과 n - 1개의 간선으로 구성된 연결그래프.
        // 간선들 끊어 생기는 두 그래프 중 하나를 취해 최종적으로 m개의 정점만 남겨두려고 한다
        // 최소로 끊어야하는 간선의 수는?
        // 정점은 잎사귀, 간선은 가지라고 표현했다는 점에서 트리 형태를 띌거라는 힌트를 얻을 수 있다
        // 트리를 활용한 DP로 풀어야하는 문제
        // 임의의 한점을 root 노드로 선정하고, bottom-up 방식으로 서브트리들에서 i개의 잎사귀를 떼는 최소 간선의 수를 갱신해가자
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        init();
        for (int i = 1; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            // a와 b가 서로 연결되어 있다.
            connection.get(a).add(b);
            connection.get(b).add(a);
        }
        // 1번을 루트 노드로 정하자.
        setRootAndFillChildren(1, new boolean[n + 1]);
        fillDP(1);

        int answer = dp[1][m - 1];
        for (int i = 2; i < dp.length; i++) {
            if (children[i] + 1 >= m)
                answer = Math.min(answer, dp[i][m - 1] + 1);
        }
        System.out.println(answer);
    }

    static void fillDP(int leaf) {          // leaf 정점을 루트로 가지는 서브트리에 대한 DP를 채운다.
        for (int child : connection.get(leaf)) {        // leaf의 자식 노드들을 방문하며
            fillDP(child);      // 자식 노드들의 DP들 값이 채워져있어야 leaf의 DP를 대해 채울 수 있다.
            for (int i = 0; i < children[leaf]; i++) {      // leaf에 달린 잎사귀가 i개가 되는 방법은
                for (int j = 1; j <= children[child] && i + j <= children[leaf]; j++) {      // leaf에 i + j개의 잎사귀가 있을 때, j개의 잎사귀를 child에서 떼는 방법
                    int leftLeaves = children[child] - j;       // j개를 떼면 child 서브 트리에는 leftLeaves 만큼의 잎사귀가 남아있다.
                    // 해당 가지치기 방법이 최소 횟수를 갱신하는지 확인한다.
                    dp[leaf][i] = Math.min(dp[leaf][i], dp[leaf][i + j] + dp[child][leftLeaves]);
                }
                // leaf 잎사귀가 i개가 되는 방법 중 하나로, child 서브 트리를 자르는 방법. 한번으로 1 + children[child] 개의 잎사귀가 제거된다.
                if (i + children[child] + 1 <= children[leaf])
                    dp[leaf][i] = Math.min(dp[leaf][i], dp[leaf][i + children[child] + 1] + 1);
            }
        }
        // ** 위 for문의 순서가 중요하다. 해당 점 때문에 시간을 꽤나 날렸다. **
        // 각각의 child에 대해서는 결과값이 중복되서 반영될 수 있으나(1번 child에서 1개 떼고, 2번 child에서 2개 떼서 총 3개 떼는 경우는 가능)
        // 하지만 순서를 잘못 지정하면 1번 child에서 1개의 잎사귀를 뗀 값이, 1번 child에서 2개의 잎사귀를 떼는 값에 참조될 수 있다
        // for문을 작성할 때 순서에 유의하도록 하자.
    }

    static int setRootAndFillChildren(int leaf, boolean[] visited) {        // 루트 노드를 정하고, 자식 트리에 속한 정점의 개수를 기록하자.
        visited[leaf] = true;           // 방문 표시
        List<Integer> ancestor = new LinkedList<>();        // 자신보다 상위의 부모노드가 연결되어있을 수 있다. 이를 모아서 나중에 제거해주자.
        int count = 0;      // 서브트리에 속한 정점의 개수를 센다.
        for (int child : connection.get(leaf)) {
            if (visited[child])     // 이미 방문한 적이 있다면 부모 노드.
                ancestor.add(child);
            else        // 아니라면 나의 자식 노드이므로 재귀를 태워보내 해당 자식 노드에 속한 정점의 개수를 가져와 더해주자.
                count += setRootAndFillChildren(child, visited);
        }
        connection.get(leaf).removeAll(ancestor);       // 부모 노드는 connection에서 제거해주자
        children[leaf] = count;     // leaf를 root로 가지는 서브트리에 속한 정점의 개수는 count개
        dp[leaf][children[leaf]] = 0;       // 해당 서브트리에서 children[leaf]개의 잎사귀가 되기 위해 쳐내야하는 간선의 수는 0개
        dp[leaf][0] = children[leaf];       // 해당 서브트리에 속한 잎사귀가 0개가 되려면 최대 children[leaf]개의 간선을 쳐내면 된다(모두 일일이 쳐낼 경우)
        return children[leaf] + 1;      // 서브트리에 속한 정점과 root 총 children[leaf] + 1개를 리턴한다.
    }

    static void init() {
        connection = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connection.add(new ArrayList<>());
        children = new int[n + 1];
        dp = new int[n + 1][n];
        for (int[] d : dp)
            Arrays.fill(d, MAX);
    }
}