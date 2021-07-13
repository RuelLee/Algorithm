/*
 Author : Ruel
 Problem : Baekjoon 1167번 트리의 지름
 Problem address : https://www.acmicpc.net/problem/1167
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 트리의지름;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main2 {
    static List<int[]>[] edges;
    static boolean[] check;

    public static void main(String[] args) {
        // 두번의 dfs를 돌려 푸는 방법도 있다고 한다.
        // 먼저 아무 지점에서 가장 먼 곳의 값과 비용을 가져온다.
        // 이제 그 점으로부터 가장 먼 곳의 비용을 가져오면 그 값이 트리의 지름.
        Scanner sc = new Scanner(System.in);

        int V = sc.nextInt();
        edges = new List[V + 1];
        check = new boolean[V + 1];
        for (int i = 1; i < edges.length; i++)
            edges[i] = new ArrayList<>();

        for (int i = 0; i < V; i++) {
            int from = sc.nextInt();
            while (true) {
                int to = sc.nextInt();
                if (to == -1)
                    break;
                edges[from].add(new int[]{to, sc.nextInt()});
            }
        }
        int[] first = dfs(1);       // 아무 값에서 가장 먼 곳의 값과 비용을 가져온다.
        Arrays.fill(check, false);
        int[] answer = dfs(first[0]);   // 계산된 값으로부터 트리의 지름을 구한다.
        System.out.println(answer[1]);

    }

    static int[] dfs(int n) {   // 재귀적으로 가장 비용이 큰 곳의 값과 비용을 가져온다.
        check[n] = true;

        int[] max = new int[2];
        for (int[] e : edges[n]) {
            if (!check[e[0]]) {
                int[] currentTurn = dfs(e[0]);
                if (max[1] < currentTurn[1] + e[1]) {
                    max[0] = currentTurn[0];
                    max[1] = currentTurn[1] + e[1];
                }
            }
        }
        if (max[0] == 0)    // 갱신이 되지 않았다면 그것은 자식노드가 없는 곳. 자기 자신을 반한해주자.
            max[0] = n;

        return max;
    }
}