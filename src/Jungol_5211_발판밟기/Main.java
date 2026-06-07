/*
 Author : Ruel
 Problem : Jungol 5211번 발판밟기
 Problem address : https://jungol.co.kr/problem/5211
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_5211_발판밟기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int r, c;
    static int[][] map, order;
    static boolean[][] visited;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // r * c 크기의 격자가 주어지고 각 격자에는 A, B, C 중 하나가 주어진다.
        // 가장 바깥 칸 아무 곳에서 시작할 수 있고, 방문 순서는 A -> B -> C -> A 순서로 이루어져야한다.
        // 모든 칸을 방문할 때, 그 순서를 출력하라
        // 그럴 수 없다면 impossible을 출력한다.
        //
        // dfs, 백트래킹 문제
        // 아무 칸에서 시작해서, 미방문이고 방문하는 순서에 맞는지를 살펴보며 모두 방문한다.
        // 모든 칸을 방문한 경우, 그대로 dfs를 종료하고 방문 순서를 출력한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // r * c 크기의 격자
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        // 맵과 방문 순서, 빙문 체크 공간
        map = new int[r][c];
        order = new int[r][c];
        visited = new boolean[r][c];

        // A = 0, B = 1, C = 2로 숫자로 치환해서 저장
        for (int i = 0; i < r; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < c; j++)
                map[i][j] = st.nextToken().charAt(0) - 'A';
        }

        boolean found = false;
        // 모든 바깥 칸을 방문하며 시작
        for (int i = 0; i < r * c; i++) {
            int row = i / c;
            int col = i % c;
            if ((row == 0 || row == r - 1 || col == 0 || col == c - 1) &&
                    backTracking(1, row, col)) {
                found = true;
                break;
            }
        }

        // 답을 못 찾은 경우
        if (!found)
            System.out.println("impossible");
        else {
            // 찾은 경우
            // 방문 순서를 출력
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < r; i++) {
                sb.append(order[i][0]);
                for (int j = 1; j < c; j++)
                    sb.append(" ").append(order[i][j]);
                sb.append("\n");
            }
            System.out.print(sb);
        }
    }

    // dfs, 백트래킹
    // idx번째로 (row, col)칸을 방문
    static boolean backTracking(int idx, int row, int col) {
        // 방문 체크 및 방문 순서 기록
        visited[row][col] = true;
        order[row][col] = idx;
        // 마지막 칸을 방문 한 경우
        // true 반환
        if (idx == r * c)
            return true;

        // 그 외의 경우
        // 사방 탐색을 하여, 다음 이동할 칸이 있는지 탐색
        for (int d = 0; d < 4; d++) {
            int nextR = row + dr[d];
            int nextC = col + dc[d];

            // 해당 칸을 찾았고, true가 반환되어 온 경우
            // = 모든 칸을 순서대로 방문하는데 성공한 경우
            // true 반환
            if (checkArea(nextR, nextC) && !visited[nextR][nextC] && map[nextR][nextC] == (map[row][col] + 1) % 3 &&
                    backTracking(idx + 1, nextR, nextC))
                return true;
        }

        // 찾지 못한 경우
        // 방문 체크 해제 후, false 반환
        visited[row][col] = false;
        return false;
    }

    // 범위 체크
    static boolean checkArea(int row, int col) {
        return row >= 0 && row < r && col >= 0 && col < c;
    }
}