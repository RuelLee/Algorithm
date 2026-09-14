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
    static int[][] map, answer;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};


    public static void main(String[] args) throws IOException {
        // r * c 칸에 A, B, C 세 종류 중 하나의 알파벳이 놓여있다.
        // A -> B -> C -> A 순서로 밟을 수 있으며, 최외각의 어느 발판에서 시작해도 된다.
        // 모든 발판을 밟으려고 할 때, 그 순서는? 불가능하다면 impossible을 출력한다
        //
        // 백트래킹, dfs 문제
        // dfs로 최외각의 칸들에서 시작하며, 모든 발판을 밟는 경우를 찾는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        // r * c 크기의 맵
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        map = new int[r][c];
        for (int i = 0; i < r; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < c; j++) {
                char value = st.nextToken().charAt(0);
                if (value == 'B')
                    map[i][j] = 1;
                else if (value == 'C')
                    map[i][j] = 2;
            }
        }

        // 밟은 순서
        answer = new int[r][c];
        int i = 0;
        int j = 0;
        // 방문 체크
        boolean[][] visited = new boolean[r][c];
        // (0, 0)에서 시작
        boolean found = backTracking(1, 0, 0, visited);
        // (0, 0)에서부터 시계방향으로 최외각을 돌며 체크
        while (j + 1 < c && !found)
            found = backTracking(1, i, ++j, visited);
        while (i + 1 < r && !found)
            found = backTracking(1, ++i, j, visited);
        while (j - 1 >= 0 && !found)
            found = backTracking(1, i, --j, visited);
        while (i - 1 > 0 && !found)
            found = backTracking(1, --i, j, visited);

        // 못 찾은 경우
        // impossible 출력
        if (!found)
            System.out.println("impossible");
        else {
            // 찾은 경우
            // answer를 답안으로 작성
            StringBuilder sb = new StringBuilder();
            for (int[] a : answer) {
                sb.append(a[0]);
                for (int k = 1; k < a.length; k++)
                    sb.append(" ").append(a[k]);
                sb.append("\n");
            }
            // 출력
            System.out.print(sb);
        }
    }

    // 백트래킹
    // 현재 순서는 idx, 위치는 (r, c)
    static boolean backTracking(int idx, int r, int c, boolean[][] visited) {
        visited[r][c] = true;
        answer[r][c] = idx;
        // 모든 발판을 밟은 경우 true 반환
        if (idx == map.length * map[0].length)
            return true;

        // 4방 탐색
        for (int d = 0; d < 4; d++) {
            int nextR = r + dr[d];
            int nextC = c + dc[d];

            // 다음 발판을 찾은 경우
            // 해당 발판을 밟아 쭉 진행했을 때, 모든 발판을 밟는 것이 가능하다면 true 반환
            if (checkArea(nextR, nextC) && !visited[nextR][nextC] && map[nextR][nextC] == (map[r][c] + 1) % 3 &&
                    backTracking(idx + 1, nextR, nextC, visited))
                return true;
        }

        // 불가능한 경우
        // false 반환
        visited[r][c] = false;
        return false;
    }

    // 범위 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < map.length && c >= 0 && c < map[0].length;
    }
}