/*
 Author : Ruel
 Problem : Baekjoon 17025번 Icy Perimeter
 Problem address : https://www.acmicpc.net/problem/17025
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17025_IcyPerimeter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    static int n;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * n 크기의 격자가 주어진다.
        // 빈 칸은 ., 아이스크림은 #으로 주어진다.
        // 상하좌우로 인접해있는 아이스크림은 하나의 덩어리로 볼 때
        // 가장 면적이 넓은 아이스크림이 여러개라면 그 둘레가 가장 적은 것의
        // 면적과 둘레를 출력하라
        //
        // 그래프 탐색, BFS 문제
        // 붙어있는 아이스크림 덩어리를 탐색하며, 블록의 개수와 해당 블록의 상하좌우를 살펴,
        // 빈 칸 혹은 맵 범위의 바깥인 경우의 개수를 세어 둘레를 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n * n 크기의 격자
        n = Integer.parseInt(br.readLine());

        // 아이스크림 입력 정보
        char[][] map = new char[n][];
        for (int i = 0; i < n; i++)
            map[i] = br.readLine().toCharArray();

        // 계산된 격자 칸 체크
        boolean[][] check = new boolean[n][n];
        int maxArea = 0;
        int minRound = Integer.MAX_VALUE;
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // 처음 만나는 아이스크림 칸인 경우
                if (map[i][j] == '#' && !check[i][j]) {
                    // 큐에 추가
                    queue.offer(i * n + j);
                    check[i][j] = true;

                    // 붙어있는 블럭의 개수와 둘레 길이 계산
                    int area = 0;
                    int round = 0;
                    while (!queue.isEmpty()) {
                        int current = queue.poll();
                        int row = current / n;
                        int col = current % n;

                        // 현재 블록의 개수 추가
                        area++;
                        // 사방탐색
                        for (int d = 0; d < 4; d++) {
                            int nextR = row + dr[d];
                            int nextC = col + dc[d];

                            // 범위를 벗어나거나 빈 칸인 경우
                            // 둘레의 길이 추가
                            if (!checkArea(nextR, nextC) || map[nextR][nextC] == '.')
                                round++;
                            else if (!check[nextR][nextC]) {
                                // 아직 미 방문 격자인 경우
                                // 큐에 추가 및 체크
                                queue.offer(nextR * n + nextC);
                                check[nextR][nextC] = true;
                            }
                        }
                    }

                    // 해당 아이스크림 덩어리의 면적과 둘레가
                    // 최대 면적 및 최소 둘레를 만족하는지 확인
                    if (area > maxArea) {
                        maxArea = area;
                        minRound = round;
                    } else if (area == maxArea)
                        minRound = Math.min(round, minRound);
                }
            }
        }

        // 계산 결과 출력
        System.out.println(maxArea + " " + minRound);
    }

    // 범위 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < n;
    }
}