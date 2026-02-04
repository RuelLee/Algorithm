/*
 Author : Ruel
 Problem : Baekjoon 17136번 색종이 붙이기
 Problem address : https://www.acmicpc.net/problem/17136
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17136_색종이붙이기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[][] board;

    public static void main(String[] args) throws IOException {
        // 1 * 1, ..., 5 * 5 크기의 색종이가 각각 5개씩 주어진다.
        // 10 * 10 크기의 보드가 주어지고, 각 칸에 0 혹은 1이 주어진다.
        // 1을 모두 색종이로 덮어야한다.
        // 0인 곳은 색종이로 덮어서는 안된다.
        // 사용되는 최소 색종이의 갯수는?
        //
        // 브루트 포스, 백트래킹 문제
        // 각 칸마다 방문하며
        // 1인 경우, 해당 칸으로부터 오른쪽 아래로 정사각형 모양의 1이 배치되어있는지 확인한다.
        // 그러한 경우, 해당 영역에 해당 크기의 색종이로 덮고, 다음 칸으로 넘어간다.
        // 즉, 모든 경우를 직접해보고, 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 주어지는 10 * 10 크기 보드
        board = new int[10][10];
        StringTokenizer st;
        for (int i = 0; i < 10; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 10; j++)
                board[i][j] = Integer.parseInt(st.nextToken());
        }

        // 사용된 최소 색종이 갯수
        int answer = findAnswer(0, new int[]{0, 5, 5, 5, 5, 5});
        // 초기값인 경우 불가능한 경우이므로 -1 출력, 그 외의 경우 answer 출력
        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }

    // 현재 idx칸에 대해 탐색하며, 남은 색종이의 개수는 papers
    static int findAnswer(int idx, int[] papers) {
        // 마지막 칸까지 모두 살펴보았다면
        if (idx == 100) {
            int sum = 0;
            for (int i = 0; i < papers.length; i++)
                sum += papers[i];
            // 사용한 색종이의 개수를 계산하여 반환
            return 25 - sum;
        }

        // 현재 r, c
        int r = idx / 10;
        int c = idx % 10;
        // 만약 현재 칸이 0이라면 옆 칸의 값을 그대로 받아와 반환
        if (board[r][c] == 0)
            return findAnswer(idx + 1, papers);

        // 1인 경우
        // 현재 칸으로부터 정사각형 모양으로 1이 차 있는 영역이 있는지 확인한다.
        int min = Integer.MAX_VALUE;
        // 5 * 5 ~ 1 * 1 까지
        for (int i = 5; i > 0; i--) {
            // 만약 남은 색종이가 없거나, 범위를 벗어날 경우 건너뜀.
            if (papers[i] == 0 || r + i > 10 || c + i > 10)
                continue;

            // 해당 영역이 모두 1인 경우
            if (allOne(r, c, i)) {
                // 해당 영역을 0으로 채우고
                fillValue(r, c, i, 0);
                // 색종이를 사용하고
                papers[i]--;
                // 그로 파생되는 경우들에서 최소 색종이 사용 갯수를 반환 받아와
                // min 값을 갱신
                min = Math.min(min, findAnswer(idx + i, papers));
                // 색종이와 board의 상태 복구
                papers[i]++;
                fillValue(r, c, i, 1);
            }
        }
        // 찾은 최소 사용 색종이의 갯수 반환
        return min;
    }

    // r, c를 왼쪽 위로 갖으며, 한 변의 길이가 size인 정사각형 영역에서
    // 모두 1인지 확인
    static boolean allOne(int r, int c, int size) {
        for (int i = r; i < r + size; i++) {
            for (int j = c; j < c + size; j++) {
                if (board[i][j] == 0)
                    return false;
            }
        }
        return true;
    }

    // (r, c)를 왼쪽 위로 갖으며, 한 변의 길이가 size인 정사각형 영역의 값들을
    // value로 채운다.
    static void fillValue(int r, int c, int size, int value) {
        for (int i = r; i < r + size; i++) {
            for (int j = c; j < c + size; j++)
                board[i][j] = value;
        }
    }
}