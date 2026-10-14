/*
 Author : Ruel
 Problem : Jungol 3529번 문자들(LETTERS)
 Problem address : https://jungol.co.kr/problem/3529
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_3529_문자들_LETTERS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int r, c;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // r * c 공간이 주어지고 각 공간에 대문자 알파벳이 하나 들어있다.
        // 왼쪽 상단에서 출발하여, 서로 다른 알파벳을 최대한 많이 모으고 싶다.
        // 같은 공간을 두 번 방문할 수 없으며, 이미 획득한 문자가 있는 방도 갈 수 없다.
        // 최대한 얻을 수 있는 문자의 수는?
        //
        // DFS, 백트래킹 문제
        // r과 c가 아무리 크게 주어지더라도 결국 최대 문자의 수는 26개이다.
        // 따라서 DFS로 하더라도 스택 오버플로우가 나지 않는다.
        // DFS로 각 알파벳의 획득 여부를 체크해나가며 방을 방문한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // r * c 크기의 공간
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        // 각 방의 알파벳
        char[][] map = new char[r][];
        for (int i = 0; i < r; i++)
            map[i] = br.readLine().toCharArray();

        // 알파벳 획득 여부
        boolean[] check = new boolean[26];
        // 초기값
        check[map[0][0] - 'A'] = true;
        // 0, 0에서 출발
        int ans = findAnswer(0, 0, 1, map, check);
        // 답 출력
        System.out.println(ans);
    }

    // (row, col)에서 cnt개의 알파벳을 모았고, 그 종류는 check에 기록된 상태
    static int findAnswer(int row, int col, int cnt, char[][] map, boolean[] check) {
        int max = cnt;
        // 사방탐색
        for (int d = 0; d < 4; d++) {
            int nextR = row + dr[d];
            int nextC = col + dc[d];

            // r * c 범위 내이며, 미획득한 알파벳인 경우
            if (checkArea(nextR, nextC) && !check[map[nextR][nextC] - 'A']) {
                check[map[nextR][nextC] - 'A'] = true;
                // 해당 칸을 방문하는 경우를 계산
                // 추가적으로 더 획득한 알파벳의 수를 max에 반영
                max = Math.max(max, findAnswer(nextR, nextC, cnt + 1, map, check));
                check[map[nextR][nextC] - 'A'] = false;
            }
        }
        // 현재 상태에서 얻을 수 있는 최대 알파벳의 수를 반환.
        return max;
    }

    // r * c의 범위 체크
    static boolean checkArea(int row, int col) {
        return row >= 0 && row < r && col >= 0 && col < c;
    }
}