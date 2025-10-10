/*
 Author : Ruel
 Problem : Baekjoon 3973번 Time To Live
 Problem address : https://www.acmicpc.net/problem/3973
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3973_TimeToLive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    // 컴퓨터 간의 연결
    static List<List<Integer>> connections;
    // n개의 노드
    static int n;
    // 트리의 지름을 계산할 때 사용한 각 노드까지의 거리
    static int[] distances;
    // 트리의 지름을 이루는 두 노드가 각 노드에 이르는 거리
    static int[][] distancesFromSides;

    public static void main(String[] args) throws IOException {
        // n개의 컴퓨터가 주어지며, n-1개의 연결이 주어진다.
        // 한 컴퓨터에서 다른 컴퓨터를 연결하는 경로는 유일하다.
        // 컴퓨터들 중 하나의 컴퓨터를 라우터로 사용하고자 한다.
        // 이 때 다른 컴퓨터들을 연결하는 시간이 가장 짧은 컴퓨터를 선택하고자 한다.
        // 그 때 라우터로부터 가장 먼 컴퓨터까지의 연결 거리는?
        //
        // 트리의 지름 문제
        // n-1개의 연결, 각각의 노드를 연결하는 유일한 경로 -> 트리
        // 트리의 지름 문제로 먼저 트리의 지름을 이루는 가장 먼 거리를 갖는 두 노드를 찾는다.
        // 그리고 모든 노드에 대해서 해당 노드를 라우터로 삼았을 때, 트리의 지름을 이루는 두 노드까지의 거리를 보면 된다.
        // 해당 거리 중 큰 값이 가장 연결이 먼 거리이기 때문. 이 값이 가장 작은 값을 갖는 노드를 찾아 그 값을 출력하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        connections = new ArrayList<>();
        for (int i = 0; i < 100_000; i++)
            connections.add(new ArrayList<>());
        distances = new int[100_000];
        distancesFromSides = new int[2][100_000];

        StringBuilder sb = new StringBuilder();
        // c개의 테스트 케이스
        int c = Integer.parseInt(br.readLine());
        StringTokenizer st;
        for (int testCase = 0; testCase < c; testCase++) {
            // n개의 노드
            n = Integer.parseInt(br.readLine());
            for (int i = 0; i < n; i++)
                connections.get(i).clear();
            
            // 컴퓨터 간의 연결
            for (int i = 0; i < n - 1; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                connections.get(a).add(b);
                connections.get(b).add(a);
            }
            
            // 임의의 한 점으로 가장 먼 노드를 구한다.
            // 해당 노드가 트리의 지름 중 한 노드
            int one = findFurthestNode(0);
            // one으로부터 가장 먼 노드를 구한다.
            // one과 furthest가 트리의 지름을 이룬다.
            int furthest = findFurthestNode(one);
            // one부터 각 노드에 이르는 거리
            for (int i = 0; i < n; i++)
                distancesFromSides[0][i] = distances[i];
            // furthest부터 각 노드에 이르는 거리
            findFurthestNode(furthest);
            for (int i = 0; i < n; i++)
                distancesFromSides[1][i] = distances[i];
            
            // 두 노드부터의 다른 노드까지의 거리 중 큰 값을 찾고
            // 해당 큰 값들 중 가장 작은 값을 찾는다.
            int answer = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++)
                answer = Math.min(answer, Math.max(distancesFromSides[0][i], distancesFromSides[1][i]));
            // 답 기록
            sb.append(answer).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }

    // bfs로 start로부터 각 노드에 이르는 거리를 구하고
    // 가장 먼 노드를 반환한다.
    static int findFurthestNode(int start) {
        // 거리 초기화
        for (int i = 0; i < n; i++)
            distances[i] = Integer.MAX_VALUE;
        distances[start] = 0;

        // bfs
        // start로부터 각 노드의 거리를 구하고
        // 가장 먼 노드를 찾는다.
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        int furthestNode = start;
        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (distances[furthestNode] < distances[current])
                furthestNode = current;

            for (int next : connections.get(current)) {
                if (distances[next] > distances[current] + 1) {
                    distances[next] = distances[current] + 1;
                    queue.offer(next);
                }
            }
        }
        // 가장 먼 노드 반환
        return furthestNode;
    }
}