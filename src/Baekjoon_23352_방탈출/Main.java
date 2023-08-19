/*
 Author : Ruel
 Problem : Baekjoon 23352번 방탈출
 Problem address : https://www.acmicpc.net/problem/23352
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23352_방탈출;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int n, m;
    static int[][] map;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * m 격자의 방들이 주어진다.
        // 각 방에는 0 ~ 9의 수가 적혀있다.
        // 방을 이동할 때는
        // 0이 적힌 방으로는 이동할 수 없다.
        // 임의의 방에서 다른 방으로 이동할 때는 항상 최단 경로로 이동한다.
        // 위 조건을 만족하는 경로 중 가장 긴 경로의 시작 방과 끝 방에 적힌 숫자의 합
        // 중 가장 큰 값을 구하고자 한다.
        //
        // BFS 문제
        // 모든 방에 대해, BFS를 통해 가장 먼 방까지의 거리와 그 때의 두 방에 적힌 수의 합을 구한다.
        // 첫째로 먼 거리가 우선되고, 그러한 것이 여러개라면 합이 큰 값이 우선됨에 유의하자
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        map = new int[n][];
        for (int i = 0; i < map.length; i++)
            map[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 답안
        int[] answer = new int[2];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] != 0) {
                    // 0이 아닌 방에서
                    // BFS를 통해 가장 먼 방을 구한다.
                    int[] returned = bfs(i, j);
                    // 거리가 가장 멀거나
                    // 거리가 같다면 합이 큰 경우
                    // answer를 returned로 대체
                    if (returned[0] > answer[0] ||
                            (returned[0] == answer[0] && returned[1] > answer[1]))
                        answer = returned;
                }
            }
        }

        System.out.println(answer[1]);
    }

    // BFS
    static int[] bfs(int row, int col) {
        // row, col에서 BFS를 한다.
        // 각 격자에 이르는 최소 거리
        int[][] minDistances = new int[n][m];
        for (int[] md : minDistances)
            Arrays.fill(md, Integer.MAX_VALUE);
        minDistances[row][col] = 0;
        
        // 최소 거리가 가장 멀고, 값이 가장 큰 곳의 위치
        int maxIdx = row * m + col;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(row * m + col);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            // 현재 행과 열
            int currentRow = current / m;
            int currentCol = current % m;

            // 사방탐색
            for (int d = 0; d < 4; d++) {
                int nextR = currentRow + dr[d];
                int nextC = currentCol + dc[d];
                
                // 맵 범위를 벗어나지 않고, 0이 아니며
                // 최소거리를 갱신한다면
                if (checkArea(nextR, nextC) && map[nextR][nextC] != 0 && minDistances[nextR][nextC] > minDistances[currentRow][currentCol] + 1) {
                    minDistances[nextR][nextC] = minDistances[currentRow][currentCol] + 1;
                    // 큐에 추가
                    queue.offer(nextR * m + nextC);
                    // 혹시 가장 먼 거리 혹은 가장 먼거리 중 하나이면서 가장 큰 값이라면
                    // maxIdx 값 갱신
                    if (minDistances[nextR][nextC] > minDistances[maxIdx / m][maxIdx % m] ||
                            (minDistances[nextR][nextC] == minDistances[maxIdx / m][maxIdx % m] && map[maxIdx / m][maxIdx % m] < map[nextR][nextC])) {
                        maxIdx = nextR * m + nextC;
                    }
                }
            }
        }

        // 최종적으로 (row, col)에서 (maxIdx / m, maxIdx % m)이 시작과 끝 방으로 계산되었다.
        // 두 방 사이의 거리와 수 합을 반환한다.
        return new int[]{minDistances[maxIdx / m][maxIdx % m], map[row][col] + map[maxIdx / m][maxIdx % m]};
    }
    
    // 맵 범위 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}