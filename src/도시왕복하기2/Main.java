/*
 Author : Ruel
 Problem : Baekjoon 2316번 도시 왕복하기 2
 Problem address : https://www.acmicpc.net/problem/2316
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 도시왕복하기2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static int[][] capacity;
    static int[][] flow;
    static List<List<Integer>> routes;

    public static void main(String[] args) {
        // 각 정점마다 boolean 배열로 방문체크를 하려다 실패만 했다
        // 요점은 각 정점을 in과 out 두개로 분할하여 in과 out을 연결하는 capacity 1의 간선을 하나 추가한다
        // 그 후 알고리즘을 돌리면 in과 out을 지나는 회수는 1번밖에 있을 수 밖에 없다 (= 정점을 지나는 회수가 한번으로 제한된다)
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int p = sc.nextInt();
        init(n, p, sc);

        int answer = 0;
        while (true) {
            int addedFlow = fordFulkerson(0, Integer.MAX_VALUE, new boolean[2 * n]);
            if (addedFlow == 0)
                break;
            answer += addedFlow;
        }
        System.out.println(answer);
    }

    static int fordFulkerson(int current, int minFlow, boolean[] routeCheck) {
        if (current == 1 * 2 + 1)       // 2번 정점의 out에 도착하는 개수
            return minFlow;

        routeCheck[current] = true;     // 이번 루트에서 이 정점은 다시 방문하지 않기 위해 체크해준다.
        for (int next : routes.get(current)) {
            if (!routeCheck[next] && capacity[current][next] - flow[current][next] > 0) {       // 방문하지 않은 정점이고, 유량 여유가 있을 때
                int returnedMinFlow = fordFulkerson(next, Math.min(minFlow, capacity[current][next] - flow[current][next]), routeCheck);
                if (returnedMinFlow > 0) {      // 2번 정점에 도착했다면
                    flow[current][next] += returnedMinFlow;     // flow에 값 추가
                    flow[next][current] -= returnedMinFlow;
                    return returnedMinFlow;
                }
            }
        }
        return 0;       // 2번 정점에 도착하지 못했다면 0 리턴
    }

    static void init(int n, int p, Scanner sc) {
        capacity = new int[2 * n][2 * n];       // in, out 두개의 정점으로 나뉘므로 2배의 공간이 필요하다
        flow = new int[2 * n][2 * n];

        routes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 2; j++)     // in과 out 두 번 추가해준다. n번 정점은 (n - 1)*2 와, (n - 1)*2 + 1 두개로 나누어질 것이다.
                routes.add(new ArrayList<>());
            routes.get(2 * i).add(2 * i + 1);       // in에서 out으로 연결하는 간선을 추가해주자
            if (i == 0 || i == 1)       // 1번 정점과 2번 정점은 한도 제한이 없다!
                capacity[2 * i][2 * i + 1] = Integer.MAX_VALUE;
            else
                capacity[2 * i][2 * i + 1] = 1;
        }

        for (int i = 0; i < p; i++) {
            int a = sc.nextInt() - 1;
            int b = sc.nextInt() - 1;

            routes.get(2 * a + 1).add(2 * b);       // a out -> b in    주어진 a -> b
            routes.get(2 * b).add(2 * a + 1);       // b in -> a out    역방향으로도 지나갈 수 있도록
            routes.get(2 * b + 1).add(2 * a);       // b out -> a in    주어진 간선이 양방향이므로 b out에서 a in으로도 갈 수 있도록 해주자.
            routes.get(2 * a).add(2 * b + 1);       // a in -> b out
            capacity[2 * a + 1][2 * b] = 1;         // a out -> b로 오는 capacity 1
            capacity[2 * b + 1][2 * a] = 1;         // b out -> a로 오는 capacity 1
        }
    }
}