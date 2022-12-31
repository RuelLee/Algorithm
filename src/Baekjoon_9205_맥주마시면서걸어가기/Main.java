/*
 Author : Ruel
 Problem : Baekjoon 9205번 맥주 마시면서 걸어가기
 Problem address : https://www.acmicpc.net/problem/9205
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9205_맥주마시면서걸어가기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 편의점 위치와 집, 페스티벌 좌표가 주어진다.
        // 맥주 한 박스를 들고 출발하며, 박스에는 20개의 맥주가 들어있다.
        // 목이 마르면 안되기 때문에 50미터에 맥주를 한 병씩 마신다.
        // 편의점에서는 맥주를 채울 수 있다.
        // 집에서 편의점들을 들려 페스티벌 좌표로 갈 수 있는지 출력하라
        //
        // 그래프 탐색 문제
        // 집에서 시작하여 BFS를 통해 갈 수 있는 다음 지점들을 찾는 작업을 반복하며
        // 페스티벌 장소에 닿을 수 있는지 확인한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 테스트케이스
        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            // 편의점 개수
            int n = Integer.parseInt(br.readLine());

            // 집, 편의점들, 페스티벌 좌표
            int[][] points = new int[n + 2][];
            for (int i = 0; i < points.length; i++)
                points[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            // 각 지점에 도달할 수 있는지를 체크할 boolean 배열
            boolean[] possible = new boolean[n + 2];
            // 0번은 집
            possible[0] = true;
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(0);
            // BFS
            while (!queue.isEmpty()) {
                int current = queue.poll();

                for (int i = 0; i < points.length; i++) {
                    // 이미 도달했던 장소는 건너뛴다.
                    if (possible[i])
                        continue;

                    // 맨해튼 거리로 다음 장소와 거리를 계산하고 갈 수 있는지 따져본다.
                    if (Math.abs(points[current][0] - points[i][0]) + Math.abs(points[current][1] - points[i][1]) <= 1000) {
                        // 가능하다면 boolean 배열에 체크 후 큐 추가.
                        possible[i] = true;
                        queue.offer(i);
                    }
                }
            }
            // 최종적으로 페스티벌 장소에 도달할 수 있는지에 따른 답안 기록
            sb.append(possible[n + 1] ? "happy" : "sad").append("\n");
        }
        // 전체 답안 출력.
        System.out.print(sb);
    }
}