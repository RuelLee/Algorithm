/*
 Author : Ruel
 Problem : Baekjoon 2665번 미로 만들기
 Problem address : https://www.acmicpc.net/problem/2665
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2665_미로만들기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * n 모양의 방이 주어진다
        // 방에는 1과 0이 주어지는데, 0이 주어진 방은 사면이 벽으로 둘러싸여있어 들어갈 수 없고
        // 붙어있는 1의 방들은 서로 이동할 수 있다.
        // 0으로 주어진 방을 최소한으로 1의 방으로 바꾸어 윗줄 맨 왼쪽 방에서 아랫줄 맨 오른쪽 방으로 가고 싶다.
        // 이 때 바꾸어야하는 최소한의 방의 개수는?
        //
        // BFS 문제
        // 이긴하나 흰색으로 이웃한 방들끼리는 우선적으로 탐색하고, 그 다음으로 탐색한 1의 방들과 이웃한 0의 방들을
        // 탐색하는 식으로 약간의 우선순위를 주면 좋다 -> 우선순위큐
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력 데이터
        int n = Integer.parseInt(br.readLine());
        int[][] map = new int[n][n];
        for (int i = 0; i < map.length; i++) {
            String input = br.readLine();
            for (int j = 0; j < map[i].length; j++)
                map[i][j] = input.charAt(j) - '0';
        }

        // 해당 공간으로 이동하기 위해 바꾸어야하는 방의 최소 개수
        int[][] minWallBreak = new int[n][n];
        // 최대값으로 초기화
        for (int[] mwb : minWallBreak)
            Arrays.fill(mwb, 50 * 50 + 1);
        // 시작 지점은 0
        minWallBreak[0][0] = 0;

        // 우선순위큐를 사용하여 바꾸는 방의 개수가 적은 지역부터 우선적으로 탐색한다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(value -> minWallBreak[value / n][value % n]));
        // 시작 위치
        priorityQueue.offer(0);
        while (!priorityQueue.isEmpty()) {
            // 좌표가 아닌 index를 통해 탐색하므로 다시 좌표로 바꾸어준다.
            int current = priorityQueue.poll();
            // row
            int r = current / n;
            // col
            int c = current % n;

            // 사방탐색
            for (int d = 0; d < 4; d++) {
                int nextR = r + dr[d];
                int nextC = c + dc[d];
                // 다음 지점의 index
                int next = nextR * n + nextC;

                // 맵의 범위를 벗어나지 않으며
                if (checkArea(nextR, nextC, n)) {
                    // 다음 지점이 흰색 방(1)이고
                    if (map[nextR][nextC] == 1) {
                        // 최소 변환 개수를 갱신하게 된다면
                        if (minWallBreak[nextR][nextC] > minWallBreak[r][c]) {
                            // 해당 값 저장.
                            minWallBreak[nextR][nextC] = minWallBreak[r][c];
                            // 우선순위큐에 삽입.
                            priorityQueue.remove(next);
                            priorityQueue.offer(next);
                        }
                        // 그렇지 않고 검정색 방(0)이며, 최소 변환 개수를 갱신한다면
                    } else if (minWallBreak[nextR][nextC] > minWallBreak[r][c] + 1) {
                        // 값 갱신
                        minWallBreak[nextR][nextC] = minWallBreak[r][c] + 1;
                        // 우선순위큐에 삽입.
                        priorityQueue.remove(next);
                        priorityQueue.offer(next);
                    }
                }
            }
        }
        // 목적지의 최소 변환 개수 출력.
        System.out.println(minWallBreak[n - 1][n - 1]);
    }

    static boolean checkArea(int r, int c, int n) {
        return r >= 0 && r < n && c >= 0 && c < n;
    }
}