/*
 Author : Ruel
 Problem : Baekjoon 25046번 사각형 게임 (Small)
 Problem address : https://www.acmicpc.net/problem/25046
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25046_사각형게임_Small;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * n 크기의 격자가 주어지고 각 칸에는 절대값이 10^9 보다 같거나 작은 수가 들어있다.
        // 민우와 종진이는 이 격자를 두고 게임을 한다.
        // 1. 민우가 0개 이상의 행을 선택한다.
        // 2. 종진이가 0개 이상의 열을 선택한다.
        // 3. 두 친구가 모두 선택하거나 선택하지 않은 칸이라면 종진이가
        // 4. 한 친구만 선택한 칸이라면 민우가 점수를 얻는다.
        // 민우가 얻을 수 있는 최대 점수는? 1<= n <= 9
        //
        // 브루트포스 문제
        // 조합을 통해, 민우가 선택할 수 있는 행의 모든 경우를 따진다.
        // 그 후, 종진이도 선택할 수 있는 열의 경우를 따져...서는 안되고
        // 종진이는 민우가 선택한 행을 보고서, 해당 열을 선택할지 말지 결정할 수 있다.
        // 열에 속한 칸을 민우가 선택한 칸과 선택하지 않은 칸의 합들을 보고서
        // 민우가 선택한 칸의 합이 더 크다면 자신도 해당 열을 선택해 자신이 점수를 가져가며
        // 민우가 선택하지 않은 칸의 합이 더 크다면 자신 또한 선택하지 않아 자신이 점수를 가져간다.
        // 두 경우 모두 민우는 더 적은 점수를 가져가게 된다.
        // 위 경우를 계산해주면 끝!
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n * n 크기의 격자
        int n = Integer.parseInt(br.readLine());
        long[][] board = new long[n][n];
        StringTokenizer st;
        for (int i = 0; i < board.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < board[i].length; j++)
                board[i][j] = Integer.parseInt(st.nextToken());
        }
        // 답 출력
        System.out.println(bruteForce(0, new boolean[n], board));
    }
    
    // 브루트 포스, 백트래킹
    static long bruteForce(int idx, boolean[] selectedRows, long[][] board) {
        // 모든 행을 살펴봤다면
        if (idx == board.length) {
            long answer = 0;
            // 열을 순차적으로 돌면서
            for (int j = 0; j < board[0].length; j++) {
                // 민우가 선택한 행과 선택하지 않은 행의 합을 구한다.
                long[] sums = new long[2];
                for (int i = 0; i < board.length; i++)
                    sums[selectedRows[i] ? 1 : 0] += board[i][j];
                // 민우는 두 값 중 더 적은 값을 가져간다.
                answer += Math.min(sums[0], sums[1]);
            }
            // 최종적으로 해당 경우에 민우가 얻을 수 있는 점수.
            return answer;
        }
        
        long answer = Long.MIN_VALUE;
        // 민우가 idx번째 행을 선택하는 경우의 얻을 수 있는 최대 점수
        selectedRows[idx] = true;
        answer = Math.max(answer, bruteForce(idx + 1, selectedRows, board));
        // 민우가 idx번째 행을 선택하지 않는 경우, 얻을 수 있는 최대 점수
        selectedRows[idx] = false;
        answer = Math.max(answer, bruteForce(idx + 1, selectedRows, board));
        // 두 값을 비교하여, 큰 값 반환
        return answer;
    }
}