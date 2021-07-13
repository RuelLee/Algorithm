/*
 Author : Ruel
 Problem : Baekjoon 1167번 트리의 지름
 Problem address : https://www.acmicpc.net/problem/1167
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 트리의지름;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    static List<int[]>[] edges;     // 이어진 경로들을 나타내줄 Edge 리스트 배열
    static boolean[] check;         // 각 노드를 방문했는지를 표시할 boolean 배열

    public static void main(String[] args) {
        // 트리의 구조이므로 재귀적으로 타서 들어가되,
        // 각각의 자식 노드들에서 가장 비용이 많이드는 bigOne과 해당 자식 노드에서 구할 수 있는 지름을 구해, 부모 노드로 넘겨줄 것이다.
        // 또한 트리 구조이므로 어느 지점에서 시작을 하더라도 상관 없다.
        Scanner sc = new Scanner(System.in);

        int V = sc.nextInt();
        check = new boolean[V + 1];
        edges = new List[V + 1];
        for (int i = 1; i < edges.length; i++)
            edges[i] = new ArrayList<>();

        for (int i = 0; i < V; i++) {
            int from = sc.nextInt();
            while (true) {
                int to = sc.nextInt();
                if (to == -1)
                    break;
                edges[from].add(new int[]{to, sc.nextInt()});   // [0] = to, [1] = cost
            }
        }
        System.out.println(dfs(1)[1]);
    }

    static int[] dfs(int n) {   // [0] = bigOne, [1] = diameter
        check[n] = true;
        int diameter = 0;

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o2, o1));    // n의 자식 노드들에서 구한 가장 비용이 많이 드는 노드, binOne을 우선순위 큐에 내림차순으로 담는다.
        for (int[] e : edges[n]) {  // 자식노드들을 방문하며
            if (!check[e[0]]) {     // 이전에 방문했다면(= 부모 노드라면 패스)
                int[] r = dfs(e[0]);    // 자식 노드에서 bigOne과 diameter를 구한다.
                diameter = Math.max(diameter, r[1]);    // 자식 노드들 중 가장 큰 지름을 저장한다.
                priorityQueue.add(r[0] + e[1]);     // bigOne(r[0])은 n에서 e[0]에 가는 비용(e[1])까지 더한 값을 우선순위큐에 보관.
            }
        }
        if (priorityQueue.isEmpty())        // 우선순위큐가 비어있다면 자식노드가 없는 것. bigOne도 diameter도 0을 반환해주자.
            return new int[]{0, 0};
        else {      // 아니라면
            int first = priorityQueue.poll();   // first에 큰 값 하나.
            int second = 0;
            if (!priorityQueue.isEmpty())       // 아직 값이 들어있다면 second에도 poll
                second = priorityQueue.poll();

            // 이 중 bigOne은 first가 될 것이고, 자식노드들 중 갖고 있던 지름들 중 큰 값과, bigOne 중 큰 두 개의 합으로 이루어지는 새로운 diameter 중 큰 값을 넘겨준다.
            return new int[]{first, Math.max(diameter, first + second)};
        }
    }
}