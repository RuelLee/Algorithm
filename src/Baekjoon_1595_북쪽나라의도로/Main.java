/*
 Author : Ruel
 Problem : Baekjoon 1595번 북쪽나라의 도로
 Problem address : https://www.acmicpc.net/problem/1595
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1595_북쪽나라의도로;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

class Road {
    int end;
    int distance;

    public Road(int end, int distance) {
        this.end = end;
        this.distance = distance;
    }
}

public class Main {
    static List<List<Road>> connections;

    public static void main(String[] args) throws IOException {
        // 도시 사이에 도로를 건설하는데는 비용이 많이 소모된다.
        // 따라서 특정 도시를 두 번 이상 지나가지 않고서
        // 임의의 두 도시간을 이동하는 경로는 유일하도록 설계되어있다.
        // 모든 도시들 중 가장 거리가 먼 두 도시 간의 거리를 출력하라
        //
        // DFS 문제
        // 임의의 두 도시간을 이동하는 경로는 유일하다 -> 트리 형태
        // 트리 형태에서 가장 먼 두 노드를 찾는 방법은
        // 임의의 한 노드에서 가장 먼 노드 a를 찾고
        // 그 가장 먼 노드에서 다시 가장 먼 노드 b를 찾는다.
        // a와 b가 해당 트리에서 가장 먼 노드들이다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 도시 번호는 최대 10000까지 주어진다.
        connections = new LinkedList<>();
        for (int i = 0; i < 10_001; i++)
            connections.add(new LinkedList<>());

        String input = br.readLine();
        while (input != null && input.length() > 4) {
            StringTokenizer st = new StringTokenizer(input);
            // a 도시와 b 도시는 거리 d의 도로로 연결되어있다.
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            connections.get(a).add(new Road(b, d));
            connections.get(b).add(new Road(a, d));

            input = br.readLine();
        }

        // 임의의 노드에서 가장 먼 노드를 찾는다.
        Road start = findFarthest(1, new boolean[10_001]);
        // start에서 가장 먼 노드 end를 찾는다.
        Road end = findFarthest(start.end, new boolean[10_001]);
        // 둘 사이의 거리를 출력한다.
        System.out.println(end.distance);
    }

    // n으로부터 가장 먼 노드를 찾는다.
    static Road findFarthest(int n, boolean[] visited) {
        // 방문 체크
        visited[n] = true;

        Road farthest = new Road(n, 0);
        // n에 연결된 모든 노드
        for (Road next : connections.get(n)) {
            // 미방문 노드이라면
            if (!visited[next.end]) {
                // next로부터 찾을 수 있는 가장 먼 노드를 찾는다.
                Road possibility = findFarthest(next.end, visited);
                // n - next까지의 거리를 더하고
                possibility.distance += next.distance;
                // next를 통해 찾은 가장 먼 노드가
                // 현재까지 찾은 노드들 중 가장 먼 거리를 갖고 있다면
                // 값 갱신
                if (farthest.distance < possibility.distance)
                    farthest = possibility;
            }
        }
        // 방문 표시 해제
        visited[n] = false;
        // 찾은 가장 먼 노드를 반환한다.
        return farthest;
    }
}