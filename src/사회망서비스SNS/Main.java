/*
 Author : Ruel
 Problem : Baekjoon 2533번 사회망 서비스(SNS)
 Problem address : https://www.acmicpc.net/problem/2533
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 사회망서비스SNS;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<List<Integer>> connection;
    static int[][] minEarlyAdaptor;
    static boolean[] visited;

    public static void main(String[] args) {
        // 트리와 DP에 관한 문제!
        // 시작점으로부터 DFS를 시작해, 말단 노드로부터 자신이 루트인 서브트리에서 최소얼리어답터의 수를 구하면 된다.
        // 자식 노드가 없는 말단 노드는 그 값이 자신이 얼리어답터가 아닐 때는 0, 얼리어답터일 때는 1이 채워질 것이다.
        // 그럼 그럼 부모노드가 그 값을 참고하여
        // 1. 자신이 얼리어답터일 때 -> 1(자신) + 자식 노드들이 얼리어답터든 아니든 둘 중 최소값들을 더한 값!
        // 2. 자신이 얼리어답터가 아닐 때 -> 자식 노드들이 얼리어답터인 값들의 합
        // 이렇게 점차 채워 루트노드까지 값을 채워간다!
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        connection = new ArrayList<>();
        minEarlyAdaptor = new int[n + 1][2];
        visited = new boolean[n + 1];

        for (int i = 0; i < n + 1; i++)
            connection.add(new ArrayList<>());

        for (int i = 0; i < n - 1; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();

            connection.get(a).add(b);
            connection.get(b).add(a);
        }
        findMinEarlyAdaptor(1);
        System.out.println(Math.min(minEarlyAdaptor[1][0], minEarlyAdaptor[1][1]));
    }

    static void findMinEarlyAdaptor(int current) {
        visited[current] = true;    // 방문 체크해준다! 자식 노드에서 부모 노드를 참고하지 않도록.

        minEarlyAdaptor[current][1] = 1;
        for (int child : connection.get(current)) {
            if (!visited[child]) {      // 자식 노드만 방문
                findMinEarlyAdaptor(child);     // 자식 노드의 값을 채우고
                minEarlyAdaptor[current][0] += minEarlyAdaptor[child][1];       // 자신이 얼리어답터가 아닐 땐 자식 노드가 얼리어답터일 때
                minEarlyAdaptor[current][1] += Math.min(minEarlyAdaptor[child][0], minEarlyAdaptor[child][1]);      // 자신이 얼리어답터일 땐, 자식노드는 얼리어답터든 아니든 둘 중 최소값
            }
        }
    }
}