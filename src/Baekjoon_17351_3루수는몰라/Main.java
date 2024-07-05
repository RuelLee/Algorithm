/*
 Author : Ruel
 Problem : Baekjoon 17351번 3루수는 몰라
 Problem address : https://www.acmicpc.net/problem/17351
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17351_3루수는몰라;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static char[] mola = {'M', 'O', 'L', 'A'};

    public static void main(String[] args) throws IOException {
        // n * n 크기의 격자가 주어진다.
        // 각 칸에는 알파벳이 적혀있으며, (1, 1) -> (n, n)으로 이동하고자 한다.
        // 이동하는 동안 최대한 MOLA라는 부분 문자열을 많이 만들고자한다.
        // 이동은 (r, c)에서 (r+1, c) 혹은 (r, c+1)로 이동할 수 있다.
        // 최대한 모은 mola 부분 문자열의 개수는?
        //
        // DP 문제
        // dp를 통해 현재까지 모은 mola의 개수를 정리한다.
        // 다만 모으는 중간일 수도 있으므로
        // 나머지 연산을 통해 모은 부분문자열의 수와 모으고 있는 정보를 표시한다.
        // 4의 배수를 토대로, 1은 m을 모은 경우, 2는 o까지, 3은 l까지, 4는 a까지 모은 경우이다.
        // 마찬가지로 5는 o까지, 6은 l까지 ...
        // 중간에 다른 알파벳을 만나게 되면, 4의 배수를 기점으로 초기화된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n * n 크기의 격자
        int n = Integer.parseInt(br.readLine());
        char[][] map = new char[n][];
        for (int i = 0; i < map.length; i++)
            map[i] = br.readLine().toCharArray();

        // dp
        int[][] dp = new int[n][n];
        // 만약 첫 칸이 m이라면 m을 모은 것.
        if (map[0][0] == 'M')
            dp[0][0] = 1;

        // 맵 전체를 돈다.
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                // 다음 열이 범위를 벗어나지 않고,
                if (j + 1 < dp[i].length) {
                    // 다음 알파벳을 만족한다면
                    // dp[i][j+1]의 값과 dp[i][j] + 1 값 중 큰 값을 남겨둔다.
                    if (map[i][j + 1] == mola[dp[i][j] % mola.length])
                        dp[i][j + 1] = Math.max(dp[i][j + 1], dp[i][j] + 1);
                    else if (map[i][j + 1] == mola[0])      // 만약 다음 알파벳은 아니지만 m이라면 4의 배수를 기점으로 초기화한 후 +1한다.
                        dp[i][j + 1] = Math.max(dp[i][j + 1], dp[i][j] / 4 * 4 + 1);
                    else    // 위 두 경우 모두 아니라면 4의 배수로 초기화만 진행.
                        dp[i][j + 1] = Math.max(dp[i][j + 1], dp[i][j] / 4 * 4);
                }
                // 행에 대해서도 같은 연산.
                if (i + 1 < dp.length) {
                    if (map[i + 1][j] == mola[dp[i][j] % mola.length])
                        dp[i + 1][j] = Math.max(dp[i + 1][j], dp[i][j] + 1);
                    else if (map[i + 1][j] == mola[0])
                        dp[i + 1][j] = Math.max(dp[i + 1][j], dp[i][j] / 4 * 4 + 1);
                    else
                        dp[i + 1][j] = Math.max(dp[i + 1][j], dp[i][j] / 4 * 4);
                }
            }
        }
        // 최종적으로 (n, n)에서 모은 부분문자열의 수를 출력한다.
        // 4의 배수를 기점으로 모이므로, 4로 나눈 값을 출력한다.
        System.out.println(dp[n - 1][n - 1] / 4);
    }
}