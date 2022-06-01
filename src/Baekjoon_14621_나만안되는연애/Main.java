/*
 Author : Ruel
 Problem : Baekjoon 14621번 나만 안되는 연애
 Problem address : https://www.acmicpc.net/problem/14621
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14621_나만안되는연애;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Road {
    int from;
    int to;
    int distance;

    public Road(int from, int to, int distance) {
        this.from = from;
        this.to = to;
        this.distance = distance;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 대학교들이 남초 대학과 여초 대학으로 구분되어 주어진다
        // 대학교 간의 m개의 도로가 주어지며
        // 남초 대학은 여초 대학과만, 여초 대학은 남초 대학과만 연결될 수 있다.
        // 모든 대학을 연결하되 그 거리를 최소로 하고 싶다.
        // 그 때의 거리를 출력하라
        //
        // 연결될 수 있는 대학이 제한이 생기긴했지만 결국 최소 신장 트리(MST)이다.
        // prim 알고리즘을 통해 풀어보자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 각 대학이 남초인지 여초인지 저장한다.
        char[] universities = new char[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < universities.length; i++)
            universities[i] = st.nextToken().charAt(0);

        // 각 대학에서 다른 대학으로 이어지는 도로들을 저장한다.
        List<List<Road>> roads = new ArrayList<>(n + 1);
        for (int i = 0; i < n + 1; i++)
            roads.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            roads.get(u).add(new Road(u, v, d));
            roads.get(v).add(new Road(v, u, d));
        }

        // 연결된 대학들을 표시한다.
        boolean[] connected = new boolean[n + 1];
        //현재 연결된 대학으로부터 연결되지 않은 대학과의 도로를 최소힙(우선순위큐)을 활용해서 담는다.
        PriorityQueue<Road> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.distance));
        // 초기 도로 합은 0
        int sum = 0;
        // 연결된 대학은 1개. 1번 대학.
        int connectedUniversities = 1;
        connected[1] = true;
        // 1번 대학으로부터 갈 수 있는 도로들
        for (Road r : roads.get(1))
            priorityQueue.offer(r);
        while (!priorityQueue.isEmpty()) {
            Road current = priorityQueue.poll();
            // 뽑힌 도로가 남초 대학 간의 연결이거나, 여초 대학 간의 연결
            // 혹은 이미 연결된 대학이라면 건너뛴다.
            if (universities[current.from] == universities[current.to] ||
                    connected[current.to])
                continue;

            // 그렇지 않다면 연결된 대학의 수를 증가시켜주고
            connectedUniversities++;
            // 총 연결 도로 길이에 해당 도로의 길이를 더한다.
            sum += current.distance;
            // 그리고 연결 표시.
            connected[current.to] = true;
            // current.to에서 이동할 수 있는 도로들을 우선순위큐에 추가시켜준다.
            for (Road r : roads.get(current.to))
                priorityQueue.offer(r);
        }

        // 최종적으로 n개의 대학이 모두 연결된다면 도로의 길이 출력. 그렇지 않다면 -1 출력.
        System.out.println(connectedUniversities == n ? sum : -1);
    }
}