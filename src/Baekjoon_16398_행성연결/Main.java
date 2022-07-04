/*
 Author : Ruel
 Problem : Baekjoon 16398번 행성 연결
 Problem address : https://www.acmicpc.net/problem/16398
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16398_행성연결;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Main {
    static final int LIMIT = 100_000_001;

    public static void main(String[] args) throws IOException {
        // n개의 행성과 각 행성 간의 연결 비용이 인접 행렬로 주어진다
        // 모든 행성을 잇는 최소 비용을 구하라
        //
        // 최소 스패닝 트리 문제
        // Prim 알고리즘으로 풀어보았다.
        // 하나의 정점을 선택, 해당 정점에서 연결 가능한 도시들 중 아직 방문을 하지 않은 도시들에 대해
        // 최소 연결 비용을 갱신하며 갱신된 경우 우선순위큐에 담아 연결 비용이 낮은 순으로 다음 번에 탐색한다.
        // 계속해서 탐색을 하며, 해당 지점에서 다음 지점으로 가는 연산을 아직 수행하지 않았을 경우, 해당 지점의 최소 연결 비용은 계속 갱신될 수 있다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[][] adjMatrix = new int[n][];
        for (int i = 0; i < adjMatrix.length; i++)
            adjMatrix[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 각 정점의 최소 연결 비용
        int[] minCosts = new int[n];
        // 큰 값으로 초기화
        Arrays.fill(minCosts, LIMIT);
        // 0번 정점의 연결 비용은 0(= 시작 지점)
        minCosts[0] = 0;
        // 방문 체크
        boolean[] connected = new boolean[n];
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparing(planet -> minCosts[planet]));
        // 처음엔 0으로 시작.
        priorityQueue.offer(0);
        while (!priorityQueue.isEmpty()) {
            int current = priorityQueue.poll();

            // current -> next로 가는 경로를 계산하며, 최소 연결 비용을 갱신해나간다.
            for (int next = 0; next < adjMatrix[current].length; next++) {
                // current = next가 아니며, 아직 방문하지 않은 정점들 중 최소 연결 비용이 갱신된다면
                if (current != next && !connected[next] &&
                        minCosts[next] > adjMatrix[current][next]) {
                    // 연결 비용 갱신.
                    minCosts[next] = adjMatrix[current][next];
                    // 우선 순위 큐에 다시 담는다.
                    priorityQueue.remove(next);
                    priorityQueue.offer(next);
                }
            }
            // 방문 체크
            connected[current] = true;
        }
        // 모든 정점의 연결 비용을 합친 값이 최소 연결 비용의 합.
        System.out.println(Arrays.stream(minCosts).asLongStream().sum());
    }
}