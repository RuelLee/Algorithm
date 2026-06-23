/*
 Author : Ruel
 Problem : Jungol 8986번 과학 수행평가
 Problem address : https://jungol.co.kr/problem/8986
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_8986_과학수행평가;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static int r, c;

    public static void main(String[] args) throws IOException {
        // r * c 격자 칸이 주어진다. '.'은 도체, '#'은 부도체이다.
        // 1열의 도체에 전기가 흐른다. 전기가 흐르는 도체는 상하좌우에 있는 도체에도 전기가 흐른다.
        // c열의 도체 중 하나라도 전기가 흐르면 전구가 켜진다.
        // 가장 많은 도체들을 부도체로 만들고도, 전구를 켜지게끔하고 싶다.
        // 부도체로 바꿀 수 있는 도체의 최대 수는?
        //
        // 그래프 탐색, 최단거리 문제
        // 1열에서 시작해서 최단거리로 c열에 도달할 때, 그 경로를 제외한 모든 도체를 부도체로 바꿔도 된다.
        // 따라서 모든 칸의 도체를 세고, 1열 -> c열의 최단거리를 빼면 답이 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // r행 c열
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        // 주어지는 격자칸
        char[][] map = new char[r][];
        for (int i = 0; i < r; i++)
            map[i] = br.readLine().toCharArray();

        // 최단거리
        int[][] minDistances = new int[r][c];
        for (int[] md : minDistances)
            Arrays.fill(md, Integer.MAX_VALUE);
        Queue<Integer> queue = new LinkedList<>();
        // 도체의 칸
        int cnt = 0;
        // 모든 칸들을 돌며, 도체를 세고, 1열인 도체의 경우 큐에 추가
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (map[i][j] == '.') {
                    cnt++;
                    if (j == 0) {
                        minDistances[i][j] = 1;
                        queue.offer(i * c + j);
                    }
                }
            }
        }

        // BFS
        while (!queue.isEmpty()) {
            int current = queue.poll();
            int row = current / c;
            int col = current % c;

            for (int d = 0; d < 4; d++) {
                int nextR = row + dr[d];
                int nextC = col + dc[d];

                if (checkArea(nextR, nextC) && map[nextR][nextC] == '.' && minDistances[nextR][nextC] > minDistances[row][col] + 1) {
                    minDistances[nextR][nextC] = minDistances[row][col] + 1;
                    queue.offer(nextR * c + nextC);
                }
            }
        }

        int answer = 0;
        // c열의 cnt에서 최단 거리를 뺀 최댓값을 구한다.
        for (int i = 0; i < r; i++) {
            if (minDistances[i][c - 1] != Integer.MAX_VALUE)
                answer = Math.max(answer, cnt - minDistances[i][c - 1]);
        }
        // 답 출력
        System.out.println(answer);
    }

    // 범위 체크
    static boolean checkArea(int row, int col) {
        return row >= 0 && row < r && col >= 0 && col < c;
    }
}