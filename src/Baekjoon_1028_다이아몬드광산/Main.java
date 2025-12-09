/*
 Author : Ruel
 Problem : Baekjoon 1028번 다이아몬드 광산
 Problem address : https://www.acmicpc.net/problem/1028
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1028_다이아몬드광산;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int r, c;

    public static void main(String[] args) throws IOException {
        // 다이아몬드 광산은 r * c 크기에 각 격자에 0, 1이 들어있는 배열이다.
        // 다이아몬드는
        //                                  1
        //                  1             1   1
        //      1         1   1         1       1
        //                  1             1   1
        //                                  1               ...
        // 과 같은 형태를 띄며 왼쪽부터 크기가 1, 2, 3, ... 이다.
        // 주어진 광산에서 발견할 수 있는 가장 큰 다이아몬드의 크기는?
        //
        // 누적합, DP 문제
        // 대각선 형태의 1의 연속한 개수가 중요하다
        // dp[i][j][방향] = 연속한 1의 개수로 정의하고
        // 0 = ↘, 1 = ↙, 2 = ↗, 3 = ↖의 방향으로 잡고
        // 해당 위치까지 대각선 방향으로 연속한 1의 개수를 세어준다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // r * c 크기의 광산
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        
        // 광산 정보
        char[][] map = new char[r][];
        for (int i = 0; i < map.length; i++)
            map[i] = br.readLine().toCharArray();
        
        // 누적합 계산
        int[][][] psums = new int[r][c][4];
        for (int i = 0; i < psums.length; i++) {
            for (int j = 0; j < psums[i].length; j++) {
                // 현재 칸의 값이 1인 경우
                if (map[i][j] == '1') {
                    // 왼쪽 위 대각선으로부터 이어지는 1의 개수를 누적한다.
                    psums[i][j][0] = (checkArea(i - 1, j - 1) ? psums[i - 1][j - 1][0] : 0) + 1;
                    // 오른쪽 위 대각선으로부터 이어지는 1의 개수를 누적한다.
                    psums[i][j][1] = (checkArea(i - 1, j + 1) ? psums[i - 1][j + 1][1] : 0) + 1;
                }
                
                if (map[r - 1 - i][j] == '1') {
                    // 왼쪽 아래 대각선으로부터 이어지는 1의 개수를 누적한다.
                    psums[r - 1 - i][j][2] = (checkArea(r - i, j - 1) ? psums[r - i][j - 1][2] : 0) + 1;
                    // 오른쪽 아래 대각선으로부터 이어지는 1의 개수를 누적한다.
                    psums[r - 1 - i][j][3] = (checkArea(r - i, j + 1) ? psums[r - i][j + 1][3] : 0) + 1;
                }
            }
        }
        
        // 다이아몬드 찾기
        int max = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                // 현재 칸이 0이면 건너뜀.
                if (map[i][j] == '0')
                    continue;
                
                // 현재 칸에서 아래로 이어지는 두 대각선 중 짧은 길이부터 현재 찾은 max보다 큰 값까지
                for (int length = Math.min(psums[i][j][2], psums[i][j][3]); length > max; length--) {
                    // 그 때의 아래 다이아몬드 끝에 해당하는 점을 찾고
                    // 해당 좌표에서 위로 이어지는 두 대각선 중 짧은 것의 길이가 length보다 같거나 길다면 다이아몬드를 발견했으므로
                    // max 값 갱신
                    int bottomRow = i + (length - 1) * 2;
                    if (checkArea(bottomRow, j) && Math.min(psums[bottomRow][j][0], psums[bottomRow][j][1]) >= length)
                        max = length;
                }
            }
        }
        // 답 출력
        System.out.println(max);
    }

    // 범위 체크
    static boolean checkArea(int row, int col) {
        return row >= 0 && row < r && col >= 0 && col < c;
    }
}