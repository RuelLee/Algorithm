/*
 Author : Ruel
 Problem : Baekjoon 14927번 전구 끄기
 Problem address : https://www.acmicpc.net/problem/14927
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14927_전구끄기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {0, -1, 0, 1, 0};
    static int[] dc = {0, 0, 1, 0, -1};
    static int[][] original, temp;

    public static void main(String[] args) throws IOException {
        // n * n 형태의 격자 위에 전구들이 하나씩 꽂혀있다.
        // 현재 전구들이 불이 켜져있는지 꺼져있는지가 주어진다.
        // 각 위치에는 스위치가 있고, 스위치는 현재 칸과 상하좌우의 한 칸씩의 전구들의 상태를 반전시킨다.
        // 최소 횟수로 모든 전구를 끄고자 할 때, 스위치를 누르는 횟수는?
        //
        // 브루트 포스
        // 먼저, 윗 칸의 전구를 끌 수 있는 마지막 기회는, 아랫 칸의 스위치를 누를 때이다.
        // 다시 말해, 윗 칸의 전구가 켜져있다면 현재 칸에서는 반드시 스위치를 눌러야한다.
        // 제일 윗 줄은 자신보다 윗 칸이 없기 때문에, 모든 경우에 대해 계산하고,
        // 두번째 줄부터는 자신보다 윗 칸에 불이 켜져있을 때만 스위치를 누르도록 한다.
        // 모든 과정을 거치고, 마지막 줄 또한 불이 모두 꺼져있다면, 해당 스위치를 누른 횟수를 반환하며
        // 횟수를 비교하여 가장 적은 값을 출력한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st;

        // 원래 전구 상태
        original = new int[n][n];
        temp = new int[n][n];
        for (int i = 0; i < original.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < original[i].length; j++)
                original[i][j] = Integer.parseInt(st.nextToken());
        }
        
        // 답
        int answer = findAnswer(0, 0);
        // 초기값인 경우, 불가능한 경우이므로 -1 출력
        // 그 외의 경우 answer 출력
        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }

    // 첫째줄의 모든 col에 대해 브루트포스로 모든 경우의 수를 계산한 후,
    // 두번째 줄부터는 윗칸이 켜있는 경우에 대해 스위치를 누른다.
    // 그 후 마지막 줄에 켜져있는 전구가 있는지를 살펴본다.
    static int findAnswer(int col, int pushed) {
        // 첫째줄의 모든 경우의 수를 따진 뒤
        if (col == original.length) {
            // 현재 상태를 temp에 복사
            for (int i = 0; i < original.length; i++) {
                for (int j = 0; j < original[i].length; j++)
                    temp[i][j] = original[i][j];
            }

            // 그 후, 둘째 줄부터 윗 칸이 켜져있는 경우에 한해 스위치를 누른다.
            for (int i = 1; i < temp.length; i++) {
                for (int j = 0; j < temp[i].length; j++) {
                    if (temp[i - 1][j] == 1) {
                        pushed++;
                        pushButton(i, j, temp);
                    }
                }
            }
            
            // 마지막 줄에 켜져있는 전구가 있다면 불가능한 경우.
            // 최소값을 찾는데 방해가 되지 않게 큰 값을 반환
            for (int i = 0; i < temp[temp.length - 1].length; i++) {
                if (temp[temp.length - 1][i] == 1)
                    return Integer.MAX_VALUE;
            }
            // 그 외의 경우, 버튼을 누른 횟수 반환.
            return pushed;
        }
        
        // 첫째줄을 브루트포스로 모든 경우의 수를 계산
        // 두 상태에 대한 최소 버튼을 누르는 횟수
        int min = Integer.MAX_VALUE;
        // 먼저 (0, col)의 스위치를 누르지 않는 경우
        min = Math.min(min, findAnswer(col + 1, pushed));
        // 스위치를 누르는 경우
        pushButton(0, col, original);
        min = Math.min(min, findAnswer(col + 1, pushed + 1));
        // 상태 복구
        pushButton(0, col, original);
        
        // 찾은 최소 버튼 누름 횟수 반환
        return min;
    }

    // (r, c)의 버튼을 누른 경우, 현재 칸과 상하좌우 한 칸의 상태를 반전.
    static void pushButton(int r, int c, int[][] switches) {
        for (int d = 0; d < 5; d++) {
            int row = r + dr[d];
            int col = c + dc[d];

            if (checkArea(row, col, switches))
                switches[row][col] = (switches[row][col] + 1) % 2;
        }
    }
    
    // (r, c)의 범위 체크
    static boolean checkArea(int r, int c, int[][] switches) {
        return r >= 0 && r < switches.length && c >= 0 && c < switches[r].length;
    }
}