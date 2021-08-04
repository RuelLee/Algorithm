/*
 Author : Ruel
 Problem : Baekjoon 17412번 도시 왕복하기 1
 Problem address : https://www.acmicpc.net/problem/17412
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 도시왕복하기1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static int[][] capacity;
    static int[][] flow;
    static List<List<Integer>> route;
    static int answer;

    public static void main(String[] args) {
        // 네트워크 유량 문제
        // 포드 풀커슨이나 에드몬드 카프 알고리즘을 모른다면 풀 수 없는 문제..
        // 처음 접근할 때 무조건 길을 찾아 해당 루트를 폐쇄하고 다른 루트를 찾는 식으로 했는데 그럴 경우
        // 6 7
        // 1 3
        // 1 4
        // 3 4
        // 3 5
        // 4 6
        // 5 2
        // 6 2
        // 와 같이 1 -> 3 -> 5 -> 2, 1 -> 4 -> 6 -> 2 로 가는 길이 두 개 있지만, 이게 입력에 따라, 1 -> 3 -> 4 -> 6 -> 2로만 가서 한 가지로만 셀 경우 틀릴 수 있다.
        // 이를 보완한 것이 위 두 알고리즘
        // 둘 다 Source에서 Sink로 가는 경로를 찾은 뒤,
        // 거치는 경로에서 최소 유량을 찾고 해당 유량을 각 경로에 더해준다
        // * 이 때 해당 경로의 유량을 줄이고 다른 곳으로 유량을 흘렸을 때 더 큰 값이 되는 경우를 찾기 위해서
        // 역방향으로 음의 유량 또한 더해준다.
        // 이를 dfs로 찾아 더 이상의 더해지는 유량이 0이 될 때까지 반복하는 것이 -> 포드 풀커슨 알고리즘
        // dfs로 찾을 때 병목 현상이 있는 곳을 지난다면 dfs를 많이 반복해야할 수 있다. 이를 bfs로 푼 게 -> 에드몬드 카프 알고리즘
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int p = sc.nextInt();
        init(n, p, sc);

        while (true) {
            if (fordFulkerson(0, Integer.MAX_VALUE, new boolean[n]) == 0)
                break;
        }
        System.out.println(answer);
    }

    static int fordFulkerson(int current, int minFlow, boolean[] check) {
        if (current == 1) {         // 목적지에 도달했다면
            answer += minFlow;      // 거처온 경로들에서의 유량 중 최소값이 남았을 것이다. 이를 더해주고,
            return minFlow;         // 위의 경로들에게도 유량을 더해주기 위해 리턴해준다.
        }

        check[current] = true;
        for (int next : route.get(current)) {
            if (!check[next] && capacity[current][next] - flow[current][next] > 0) {    // 방문 한 적 없는 곳이고, 유량의 여유가 있다면
                check[next] = true;
                int returnedMinFlow = fordFulkerson(next, Math.min(minFlow, capacity[current][next] - flow[current][next]), check);     // 해당 경로로 재귀를 보내고 유량을 받는다
                if (returnedMinFlow > 0) {          // 돌아온 유량이 0이 아니라면 경로 찾는데 성공.
                    flow[current][next] += returnedMinFlow;     // 정방향 경로에 유량을 더해주고
                    flow[next][current] -= returnedMinFlow;     // 역방향 경로에는 음의 유량을 더해준다.
                    return returnedMinFlow;
                }
            }
        }
        return 0;       // 여기까지 진해됐다면 경로의 없는 경우. 0을 리턴해주자.
    }

    static void init(int n, int p, Scanner sc) {
        capacity = new int[n][n];
        flow = new int[n][n];
        route = new ArrayList<>();
        for (int i = 0; i < n; i++)
            route.add(new ArrayList<>());

        for (int i = 0; i < p; i++) {
            int a = sc.nextInt() - 1;
            int b = sc.nextInt() - 1;
            route.get(a).add(b);
            route.get(b).add(a);
            capacity[a][b] = 1;
        }
    }
}