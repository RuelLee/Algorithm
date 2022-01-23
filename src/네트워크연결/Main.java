/*
 Author : Ruel
 Problem : Baekjoon 1922번 네트워크 연결
 Problem address : https://www.acmicpc.net/problem/1922
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 네트워크연결;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Cable {
    int nextCom;
    int cost;

    public Cable(int nextCom, int cost) {
        this.nextCom = nextCom;
        this.cost = cost;
    }
}

public class Main {
    static int[][] adjMatrix;

    public static void main(String[] args) throws IOException {
        // 어제 Kruskal 알고리즘으로 최소 스패닝 문제를 풀었기 때문에, 오늘은 Prim 알고리즘으로 풀어보았다
        // Kruskal 알고리즘이 간선을 기준으로 비용이 낮은 간선부터 양쪽이 같은 그룹에 속해있는지 여부를 확인하면서 연결한다면
        // Prim 알고리즘은 정점을 중심으로, 하나의 정점이 연결될 때마다, 해당 정점에서 연결할 수 있지만
        // 아직 연결 안된 정점과의 간선들을 추가하며 최소 스패닝 트리를 완성한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());

        adjMatrix = new int[n + 1][n + 1];
        StringTokenizer st;
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            if (adjMatrix[a][b] == 0 || adjMatrix[a][b] > cost)
                adjMatrix[a][b] = adjMatrix[b][a] = cost;
        }

        boolean[] connected = new boolean[n + 1];
        connected[1] = true;    // 시작은 1번 정점
        // 1번 정점으로 연결 가능한 정점들에 대한 간선들을 추가.
        PriorityQueue<Cable> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.cost));
        for (int i = 1; i < adjMatrix[1].length; i++) {
            if (adjMatrix[1][i] != 0)
                priorityQueue.offer(new Cable(i, adjMatrix[1][i]));
        }

        int sum = 0;
        while (!priorityQueue.isEmpty()) {
            Cable current = priorityQueue.poll();
            if (connected[current.nextCom])         // 이미 연결된 곳이라면 건너뜀.
                continue;

            // nextCom으로부터 연결할 수 있지만 아직 연결되지 않은 다른 컴퓨터들을 잇는 간선들을 추가한다.
            for (int i = 1; i < adjMatrix[current.nextCom].length; i++) {
                if (adjMatrix[current.nextCom][i] == 0 || connected[i] || current.nextCom == i)
                    continue;

                priorityQueue.offer(new Cable(i, adjMatrix[current.nextCom][i]));
            }
            // nextCom을 네트워크에 포함시키는데 필요한 비용을 더함.
            sum += current.cost;
            // nextCom을 방문 표시.
            connected[current.nextCom] = true;
        }
        // 최종적으로 sum으로 최종 가격이 계산된다.
        System.out.println(sum);
    }
}