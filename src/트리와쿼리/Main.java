/*
 Author : Ruel
 Problem : Baekjoon 15681 트리와 쿼리
 Problem address : https://www.acmicpc.net/problem/15681
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 트리와쿼리;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 트리로 정점과 루트, 간선이 주어진다.
        // Q로 주어지는 정점을 루트로 하는 서브트리의 정점의 개수를 구하라.
        // dfs 를 사용하되, 쿼리의 숫자가 크므로 메모이제이션을 사용하자
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int R = sc.nextInt();
        int Q = sc.nextInt();

        int[] child = new int[N + 1];       // 각 정점의 서브트리 정점의 개수를 저장할 공간
        List<Integer>[] lists = new List[N + 1];
        for (int i = 0; i < lists.length; i++)
            lists[i] = new ArrayList<>();

        for (int i = 0; i < N - 1; i++) {   // 누가 부모 노드인지 알 수 없다. 일단 서로 이어주자.
            int a = sc.nextInt();
            int b = sc.nextInt();
            lists[a].add(b);
            lists[b].add(a);
        }

        dfs(lists, child, R, new boolean[N + 1]);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Q; i++)
            sb.append(child[sc.nextInt()]).append("\n");
        System.out.println(sb);
    }

    static int dfs(List<Integer>[] lists, int[] child, int n, boolean[] check) {
        if (check[n])       // 이미 방문한 적이 있다면(부모 노드) 0을 리턴.
            return 0;

        check[n] = true;        // 아니라면 방문했다고 체크하고
        int childSum = 1;   // 정점의 개수를 자신을 일단 포함한 1
        for (int i : lists[n]) {
            if (!check[i])
                childSum += dfs(lists, child, i, check);    // 자식 노드를 루트로 갖는 서브트리의 정점의 개수를 더해준다.
        }
        return child[n] = childSum;     // 이를 메모해두자.
    }
}