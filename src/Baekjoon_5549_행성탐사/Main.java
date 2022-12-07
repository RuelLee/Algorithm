/*
 Author : Ruel
 Problem : Baekjoon 5549번 행성 탐사
 Problem address : https://www.acmicpc.net/problem/5549
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5549_행성탐사;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // m * n 크기의 정글, 바다, 얼음 지형으로 분리된 맵이 주어진다.
        // k개의 a, b, c, d로 왼쪽 위, 오른쪽 아래 좌표가 쌍으로 주어질 때
        // 해당 범위 안에 있는 지형의 개수를 세어 출력하라.
        //
        // 누적합 문제
        // 인데 2차원으로 생각해야하는 누적합 문제이다.
        // 따라서 각각의 누적합을 (0, 0)과 (i, j) 범위 안에 있는 값의 합이라고 생각하자.
        // 그리고 쿼리가 주어질 때 그 답은 누적합을 활용하여
        // o o o o o o
        // o o o x x x
        // o o o x x x 과 같이 x안의 값이 궁금하다면
        //
        // y y y y y y      y y y y y y    y y y o o o              y y y o o o
        // y y y y y y      o o o o o o    y y y o o o              o o o o o o
        // y y y y y y 에서 o o o o o o 과  y y y o o o 을 빼고 다시 o o o o o o 을 더해주자
        // 위와 같이 먼저 (0, 0) -> (c, d)까지의 값을 읽고
        // 해당 범위를 벗어나는 위의 사각형 (0, 0) -> (a - 1, d)을 빼고
        // 해당 범위를 벗어나는 왼쪽의 사각형 (0, 0) -> (c, b - 1)을 빼고
        // 두번 빼진 사각형인 (0, 0) -> (a - 1, b -1)의 값을 더해준다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 지도의 크기와 쿼리 개수
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(br.readLine());

        // 입력으로 주어지는 지도.
        char[][] map = new char[m][];
        for (int i = 0; i < map.length; i++)
            map[i] = br.readLine().toCharArray();

        // 각 (0, 0) -> (i, j)까지의 모든 지형의 합을 계산한다.
        int[][][] psums = new int[m + 1][n + 1][3];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                // 자기 지형값을 더하고
                psums[i + 1][j + 1][terraToInt(map[i][j])]++;

                // 자신의 윗 칸과 왼쪽 칸 값을 더한 후, 중복된 값을 빼기 위해 왼쪽 위 칸 값을 빼준다.
                for (int d = 0; d < psums[i + 1][j + 1].length; d++)
                    psums[i + 1][j + 1][d] += (psums[i][j + 1][d] + psums[i + 1][j][d] - psums[i][j][d]);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            // (0, 0) -> (c, d) 값에서
            // (0, 0) -> (a - 1, d) 위 사각형을 빼고
            // (0, 0) -> (c, b - 1) 왼쪽 사각형을 빼고
            // (0, 0) -> (a - 1, b -1) 중복된 사각형을 다시 더해 값을 구한다.
            for (int j = 0; j < 3; j++)
                sb.append(psums[c][d][j] - psums[a - 1][d][j] - psums[c][b - 1][j] + psums[a - 1][b - 1][j]).append(" ");
            sb.deleteCharAt(sb.length() - 1).append("\n");
        }

        // 정답 출력.
        System.out.print(sb);
    }

    // 지형에 따라 값을 분리하여 누적합을 구하기 위해
    // 지형 값을 각각을 idx 값과 연결해준다.
    static int terraToInt(char c) {
        switch (c) {
            case 'J' -> {
                return 0;
            }
            case 'O' -> {
                return 1;
            }
            case 'I' -> {
                return 2;
            }
        }
        return -1;
    }
}