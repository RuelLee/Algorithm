/*
 Author : Ruel
 Problem : Baekjoon 14167번 Moocast
 Problem address : https://www.acmicpc.net/problem/14167
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14167_Moocast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int[][] cows;

    public static void main(String[] args) throws IOException {
        // n마리의 소의 (x, y)좌표가 주어진다.
        // 무전기를 통해 소들 사이에 울음소리를 전달한다.
        // x 비용의 무전기로는 sqrt(x) 만큼의 거리에 소리를 전달할 수 있다.
        // 모든 소들 사이에 울음소리를 전달할 때, 가장 비싼 무전기의 비용은?
        //
        // 최소 스패닝 트리 문제
        // 모든 정점을 연결하되, 최소 비용으로 연결하고, 최소 비용들 중 최댓값을 찾으면 된다.
        // kruskal, prim 알고리즘으로 풀 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n마리의 소
        int n = Integer.parseInt(br.readLine());

        // 소들의 위치
        cows = new int[n][2];
        StringTokenizer st;
        for (int i = 0; i < cows.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < cows[i].length; j++)
                cows[i][j] = Integer.parseInt(st.nextToken());
        }

        // 연결된 소들
        boolean[] connected = new boolean[n];
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
        // 0번 소부터 시작
        priorityQueue.offer(new int[]{0, 0});
        // 가장 비싼 무전기의 가격
        int max = 0;
        while (!priorityQueue.isEmpty()) {
            // 현재 최소 비용으로 연결할 수 있는 소
            int[] current = priorityQueue.poll();
            // 이미 연결했다면 건너뛴다.
            if (connected[current[0]])
                continue;

            // 연결하고, 무전기의 비용 비교
            connected[current[0]] = true;
            max = Math.max(max, current[1]);

            // current에서 연결할 수 있는 다른 소들을 살펴본다.
            for (int i = 1; i < cows.length; i++) {
                // 이미 연결된 경우 건너뜀.
                if (connected[i])
                    continue;

                // current <-> i 사이의 거리에 따른 비용을 우선순위큐에 추가
                priorityQueue.offer(new int[]{i, calcDistance(current[0], i)});
            }
        }
        // 모든 정점이 연결된 뒤, 구한 무전기의 최댓값을 출력
        System.out.println(max);
    }

    // 두 소들 사이의 거리의 제곱을 반환.
    // 사실상 무전기의 값
    static int calcDistance(int a, int b) {
        return (cows[a][0] - cows[b][0]) * (cows[a][0] - cows[b][0]) +
                (cows[a][1] - cows[b][1]) * (cows[a][1] - cows[b][1]);
    }
}