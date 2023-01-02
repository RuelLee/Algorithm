/*
 Author : Ruel
 Problem : Baekjoon 16437번 양 구출 작전
 Problem address : https://www.acmicpc.net/problem/16437
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16437_양구출작전;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 섬으로 이루어진 나라가 있다.
        // 각 섬에는 양 떼나 늑대 떼가 있다.
        // 양들은 1번 섬으로 이동하여 다른 곳으로 이주하고자 한다.
        // 각 섬에서 1번 섬으로 가는 경로는 유일하며, p번 섬으로 가는 다리가 있다.
        // 늑대는 자신의 섬에서 움직이지 않으며 들어온 양을 각 최대 한마리만 먹는다고 한다.
        // 얼마나 많은 양이 1번 섬에 도달할 수 있는가?
        //
        // 그래프 탐색 문제
        // 1번섬으로 가는 경로가 유일하며, p번 섬으로 가는 다리가 있다 -> 트리 형태
        // 1번이 루트인 트리형태를 띄게 되며 따라서 DFS를 통해 깊이 우선 탐색을 하며
        // 루트로 도달할 수 있는 양의 수를 구하면 된다!
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // n개의 섬
        int n = Integer.parseInt(br.readLine());
        int[] sheep = new int[n + 1];
        int[] wolves = new int[n + 1];
        
        // 하위 노드들
        List<List<Integer>> child = new ArrayList<>(n + 1);
        for (int i = 0; i < n + 1; i++)
            child.add(new ArrayList<>());

        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            char t = st.nextToken().charAt(0);
            int a = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());

            // S 일 경우, i + 2 섬에는 양이 a마리
            if (t == 'S')
                sheep[i + 2] = a;
            // W일 경우 i + 2 섬에는 늑대가 a마리
            else
                wolves[i + 2] = a;

            // i + 2에서 1로 가는 경로가 유일한데, p로 다리가 연결되어 있다
            // -> p가 부모 노드
            child.get(p).add(i + 2);
        }

        System.out.println(findAnswer(1, sheep, wolves, child));
    }

    // DFS 탐색을 통해 각 노드에 도달하는 양의 수를 구한다.
    static long findAnswer(int node, int[] sheep, int[] wolves, List<List<Integer>> child) {
        // 현재 노드의 양의 수 - 늑대의 수
        long answer = sheep[node] - wolves[node];

        // 자식 노드에서 올라온 양의 수를 더해준다.
        for (int next : child.get(node))
            answer += findAnswer(next, sheep, wolves, child);

        // 만약 늑대가 많다면 음수가 되고,
        // 양이 많다면 양수가 된다.
        // 남은 수와 0을 비교하여 큰 수를 리턴한다.
        // (늑대가 남은 경우, 음수이고 이동하지 않으므로)
        return Math.max(answer, 0);
    }
}