/*
 Author : Ruel
 Problem : Baekjoon 10472번 십자뒤집기
 Problem address : https://www.acmicpc.net/problem/10472
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10472_십자뒤집기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int[] dr = {0, -1, 0, 1, 0};
    static int[] dc = {0, 0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // 3 * 3 크기의 보드가 주어진다.
        // 어떤 구역의 버튼을 누르면 자신을 포함한 인접한 상하좌우 네 칸의 색이 동시에 반전된다.
        // 만들고자하는 판의 모양이 주어질 때,
        // 눌러야하는 버튼의 최소 횟수는?
        //
        // 브루트 포스 문제
        // 3 * 3 크기로 총 9개의 버튼이 존재하고 이를 누른다 or 누르지 않는다 총 2의 9제곱 만큼의 가짓수가 존재한다.
        // 따라서 모든 경우의 수를 세더라도 그리크지 않다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            // 원하는 모양의 보드
            boolean[][] target = new boolean[3][3];
            for (int i = 0; i < target.length; i++) {
                String input = br.readLine();
                for (int j = 0; j < target[i].length; j++) {
                    if (input.charAt(j) == '*')
                        target[i][j] = true;
                }
            }
            // 눌러야하는 최소 버튼의 횟수를 구한다.
            int answer = findMinButtons(0, 0, new boolean[3][3], target);
            // 답안 기록
            sb.append(answer).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
    
    // 브루트 포스를 통해 눌러야하는 버튼의 최소 횟수를 구한다.
    // idx는 (0, 0) ~ (2, 2)까지의 격자를 각각 0 ~ 9의 수로 할당한다.
    // pushed는 누른 버튼의 횟수
    // board는 현재 상태, target은 원하는 보드의 상태
    static int findMinButtons(int idx, int pushed, boolean[][] board, boolean[][] target) {
        if (idx == 9) {
            // 마지막 버튼까지 눌렀다면
            // 보드의 상태를 비교한다.
            boolean found = true;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] != target[i][j]) {
                        found = false;
                        break;
                    }
                }
            }
            // 두 보드의 상태가 같다면 현재까지 누른 버튼의 횟수를
            // 그렇지 않다면 큰 값(최대 버튼은 9개 이므로 10은 나올 수 없는 값)을 반환한다.
            return found ? pushed : 10;
        }
        
        // 0 ~ idx -1 까지의 버튼 상태는 고정된 상태에서
        // 찾을 수 있는 최소 버튼의 누름 횟수
        int min = 10;
        // 현재 위치
        int row = idx / 3;
        int col = idx % 3;
        // 현재 위치의 버튼을 누르는 경우.
        // 자신을 포함한 다섯 방향에 대해 탐색
        for (int d = 0; d < 5; d++) {
            int nearR = row + dr[d];
            int nearC = col + dc[d];

            // 범위를 벗어나지 않는다면
            // 해당하는 구역을 반전시킨다.
            if (checkArea(nearR, nearC))
                board[nearR][nearC] = !board[nearR][nearC];
        }
        // 그 때의 최소 버튼 누름 횟수를 반환받아
        // min에 기록
        min = Math.min(min, findMinButtons(idx + 1, pushed + 1, board, target));

        // 버튼을 누르지 않았을 때.
        // 위에서 이미 눌렀으므로, 다시 반전시킨다.
        for (int d = 0; d < 5; d++) {
            int nearR = row + dr[d];
            int nearC = col + dc[d];

            if (checkArea(nearR, nearC))
                board[nearR][nearC] = !board[nearR][nearC];
        }
        // 그 때의 최소 버튼 누름 횟수
        min = Math.min(min, findMinButtons(idx + 1, pushed, board, target));

        // 버튼을 누르거나, 누르지 않았을 때
        // 두 경우에 대해 최소 버튼 누름 횟수를 구했으므로
        // 그 값을 반환한다.
        return min;
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < 3 && c >= 0 && c < 3;
    }
}