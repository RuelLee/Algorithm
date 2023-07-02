/*
 Author : Ruel
 Problem : Baekjoon 20010번 악덕 영주 혜유
 Problem address : https://www.acmicpc.net/problem/20010
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20010_악덕영주혜유;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] parents, ranks;

    public static void main(String[] args) throws IOException {
        // n개의 마을, k개의 도로가 주어진다.
        // 최소한의 비용을 통해 모든 마을을 연결시키고 그 때의 비용과
        // 가장 먼 두 마을 사이의 최단 거리 비용이 얼마인지 출력하라
        //
        // 최소 스패닝 트리, BFS
        // kruskal 혹은 prim 알고리즘을 통해 최소 스패닝 트리를 구한다
        // 트리에서 가장 먼 두 점은 임의의 한 점에서 가장 먼 a를 구한뒤,
        // a에서 가장 먼 b를 구한다.
        // a <-> b는 해당 트리에서 가장 먼 두 점이 된다.
        // 이를 이용하여 가장 먼 두 마을 사이의 최단 거리 비용을 구한다

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 마을 n, 도로 k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // kruskal 알고리즘을 위한 배열 초기화
        parents = new int[n];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n];

        // 도로들을 비용 순으로 정렬한다.
        int[][] roads = new int[k][];
        for (int i = 0; i < roads.length; i++)
            roads[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        Arrays.sort(roads, Comparator.comparingInt(value -> value[2]));

        int[][] adjMatrix = new int[n][n];
        for (int[] am : adjMatrix)
            Arrays.fill(am, Integer.MAX_VALUE);

        int sum = 0;
        // 도로들을 살펴보며
        for (int i = 0; i < roads.length; i++) {
            // 두 마을이 서로 다른 그룹이라면 해당 도로를 선택하여 연결한다.
            if (findParents(roads[i][0]) != findParents(roads[i][1])) {
                union(roads[i][0], roads[i][1]);
                // 비용 합 추가
                sum += roads[i][2];
                // 인접 행렬에 해당 도로 값 추가
                adjMatrix[roads[i][0]][roads[i][1]] =
                        adjMatrix[roads[i][1]][roads[i][0]] = roads[i][2];
            }
        }

        // 임의의 한 점(0)에서 가장 먼 점을 구하고
        int[] startPoint = findFarthest(0, adjMatrix);
        // start에서 가장 먼 점을 구한다.
        int[] farthest = findFarthest(startPoint[0], adjMatrix);
        
        // 총 도로 건실 비용과
        System.out.println(sum);
        // startPoint <-> farthest의 비용 출력
        System.out.println(farthest[1]);
    }

    // start에서 가장 먼 한 점과 그 거리를 구한다.
    static int[] findFarthest(int start, int[][] adjMatrix) {
        int[] minDistances = new int[adjMatrix.length];
        Arrays.fill(minDistances, Integer.MAX_VALUE);
        minDistances[start] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        boolean[] visited = new boolean[adjMatrix.length];
        int farthestPoint = start;
        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (visited[current])
                continue;

            for (int next = 0; next < adjMatrix[current].length; next++) {
                if (current == next || adjMatrix[current][next] == Integer.MAX_VALUE)
                    continue;
                else if (!visited[next] && minDistances[next] > minDistances[current] + adjMatrix[current][next]) {
                    minDistances[next] = minDistances[current] + adjMatrix[current][next];
                    if (minDistances[next] > minDistances[farthestPoint])
                        farthestPoint = next;
                    queue.offer(next);
                }
            }
            visited[current] = true;
        }

        // 가장 먼 지점과 그 거리를 반환.
        return new int[]{farthestPoint, minDistances[farthestPoint]};
    }
    
    // a와 b를 하나의 집합으로 묶는다.
    static void union(int a, int b) {
        int pa = findParents(a);
        int pb = findParents(b);

        if (ranks[pa] >= ranks[pb]) {
            parents[pb] = pa;
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else
            parents[pa] = pb;
    }

    // n 집합의 대표를 찾는다
    static int findParents(int n) {
        if (n == parents[n])
            return n;
        return parents[n] = findParents(parents[n]);
    }
}