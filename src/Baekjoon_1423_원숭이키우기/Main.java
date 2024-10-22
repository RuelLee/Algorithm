/*
 Author : Ruel
 Problem : Baekjoon 1423번 원숭이 키우기
 Problem address : https://www.acmicpc.net/problem/1423
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1423_원숭이키우기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 해빈이는 게임에서 d일 동안 케릭터를 훈련시킨다.
        // 해빈이가 가진 케릭터들의 레벨 별 수가 주어진다.
        // 하루에 한 케릭터를 1레벨 훈련시킬 수 있다.
        // 각 레벨 별로 정해진 힘이 있으며, d일 후에 이 힘의 합이 최대가 되게끔 하고자 한다.
        // 해빈이가 얻을 수 있는 힘의 최대 합은?
        //
        // DP 문제
        // dp[날짜][레벨] = 케릭터의 수로 하되
        // 0레벨은 없으므려, dp[날짜][0] = 케릭터 힘의 합으로 정하고 사용한다.
        // 각 날짜마다 한 케릭터를 잡아, 남을 일자 동안 훈련시켜 얻을 수 있는 힘 차이를 구하고
        // 각 날짜에 얻을 수 있는 힘의 최대 합을 갱신할 경우 값을 반영시켜주도록 한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 최대 레벨 n
        int n = Integer.parseInt(br.readLine());
        // 1레벨 부터, 각 레벨의 케릭터 수
        int[] characters = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 각 레벨의 힘
        int[] levels = new int[n + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i < levels.length; i++)
            levels[i] = Integer.parseInt(st.nextToken());
        // 잔여 훈련일 d
        int d = Integer.parseInt(br.readLine());

        // 0일 일 때
        // 각 레벨의 케릭터들의 수와, 힘의 합을 구한다.
        long[][] dp = new long[d + 1][n + 1];
        for (int i = 0; i < characters.length; i++) {
            dp[0][i + 1] = characters[i];
            dp[0][0] += dp[0][i + 1] * levels[i + 1];
        }
        
        // 0일부터, n-1일 까지
        for (int i = 0; i < dp.length - 1; i++) {
            // 만약 i+1일이 i일 값보다 낮다면
            // 아무 훈련도 하지 않고 하루를 보낼 수 있으므로
            // i일의 값으로 덮어쓴다.
            if (dp[i][0] > dp[i + 1][0]) {
                for (int j = 0; j < dp[i + 1].length; j++)
                    dp[i + 1][j] = dp[i][j];
            }

            // 각 레벨의 한 케릭터를 훈련시키는 경우를 계산.
            for (int j = 1; j < dp[i].length - 1; j++) {
                // j에 해당하는 케릭터가 없다면 건너뛴다.
                if (dp[i][j] == 0)
                    continue;

                // 레벨 j에 해당하는 케릭터를 훈련시킬 수 있는 가짓수를 따져본다.
                // 현재 렙과 최대 렙 그리고 남을 일을 비교하여
                // 훈련시킬 수 있는 최대 일을 구해
                // 1부터 해당 값까지의 경우를 계산한다.
                for (int k = 1; k <= Math.min(d - i, n - j); k++) {
                    // 현재 케릭터를 k일 훈련시킬 경우
                    // i+k 일에 얻을 수 있는 힘의 최대 합 값을 갱신하는지 확인.
                    if (dp[i + k][0] < dp[i][0] + levels[j + k] - levels[j]) {
                        // 갱신한다면 값 갱신
                        dp[i + k][0] = dp[i][0] + levels[j + k] - levels[j];
                        for (int l = 1; l < dp[i + k].length; l++)
                            dp[i + k][l] = dp[i][l];
                        dp[i + k][j]--;
                        dp[i + k][j + k]++;
                    }
                }
            }
        }

        // 최종 d일에 얻을 수 있는 최대 힘의 합을 출력한다.
        System.out.println(dp[d][0]);
    }
}