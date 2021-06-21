package 가장가까운공통조상;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static int[] parents;
    static int[] depths;
    static List<Integer>[] descendents;

    public static void main(String[] args) {
        // 트리에 깊이를 할당해주고
        // 두 노드의 깊이가 같을 때까지 부모노드로 이동하는 것이 관건.
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        for (int tc = 0; tc < T; tc++) {
            int N = sc.nextInt();

            parents = new int[N + 1];       // 부모 idx를 저장할 배열
            depths = new int[N + 1];        // 깊이를 저장할 배열
            descendents = new List[N + 1];  // 각 노드의 자손을 저장할 리스트 배열
            for (int i = 0; i < descendents.length; i++)
                descendents[i] = new ArrayList<>();

            for (int i = 0; i < N - 1; i++) {
                int a = sc.nextInt();
                int b = sc.nextInt();
                parents[b] = a;             // a는 b의 부모다.
                descendents[a].add(b);      // b는 a의 자식 중 하나
            }
            depthSetting();         // 깊이 세팅.

            int a = sc.nextInt();
            int b = sc.nextInt();
            while (depths[a] != depths[b]) {        // 두 노드의 깊이가 다르다면 같게 만들어주고
                if (depths[a] > depths[b])
                    a = parents[a];
                else
                    b = parents[b];
            }

            while (a != b) {        // 두 노드의 idx 값이 같아질 때까지 각 노드의 부모노드로 이동시킨다.
                a = parents[a];
                b = parents[b];
            }
            // 반복이 끝나면 a와 b는 공통된 조상 노드를 가르킨다.
            sb.append(a).append("\n");
        }
        System.out.println(sb);
    }

    static void depthSetting() {        // 루트 노드를 찾고, 재귀로 자손노드들에게 깊이를 지정해주자.
        int root = findRoot();
        for (int next : descendents[root])
            dfs(next, 1);
    }

    static int findRoot() {     // root idx를 찾는다.
        int n = 1;      // 아무 값에서 출발하여
        while (parents[n] != 0)     // 부모 값이 0(초기값)이 나올 때까지 부모로 이동.
            n = parents[n];
        return n;       // 해당 값이 루트 노드의 idx 값
    }

    static void dfs(int current, int depth) {
        depths[current] = depth;        // 현재 노드에 depth으로 설정해주고
        for (int n : descendents[current])      // 현재 노드의 자식 노드들에게 depth+1을 하여 재귀시키자.
            dfs(n, depth + 1);
    }
}