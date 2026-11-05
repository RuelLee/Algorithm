/*
 Author : Ruel
 Problem : Jungol 10452번 무승부 없애기
 Problem address : https://jungol.co.kr/problem/10452
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_10452_무승부없애기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 원형으로 서 있는 사람들이 낸 가위 바위 보가 주어진다.
        // 이웃한 사람들끼리는 무승부가 없게끔 하고자한다.
        // 바꿔야하는 인원의 최소 수는?
        //
        // DP 문제
        // dp[i][j] = 0 ~ i번째 사람까지 이웃한 사람끼리 무승부가 없게끔 만들 때, 최소 변경 인원
        // j값에 따라 0 : 바위, 1 : 가위, 2 : 보
        // 하지만 원형으로 마지막과 첫번째가 이어진다.
        // dp값만으로는 첫번째 인원이 무엇을 냈는지 알 수 없다.
        // 따라서 첫번째 인원이 내는 값을 고정시킨 후, 각각 가위, 바위, 보에 대해서 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        // 테스트케이스의 수
        int t = Integer.parseInt(br.readLine());
        for (int testCase = 0; testCase < t; testCase++) {
            // 주어진 입력
            String input = br.readLine();
            // 값으로 변경
            int[] toInt = new int[input.length()];
            for (int i = 0; i < toInt.length; i++)
                toInt[i] = input.charAt(i) == 'R' ? 0 : (input.charAt(i) == 'S' ? 1 : 2);

            // dp[i][j] = i번째 사람이 j를 내면서 인근 사람과 무승부가 아니기 위한 최소 변경 인원 수
            int[][] dp = new int[toInt.length][3];
            int ans = Integer.MAX_VALUE;
            // 첫번째 사람이 내는 경우의 수
            // 0 : 바위, 1 : 가위, 2 : 보
            for (int i = 0; i < 3; i++) {
                for (int[] d : dp)
                    Arrays.fill(d, Integer.MAX_VALUE);
                // 값이 일치한다면 변경하지 않아도 됨
                dp[0][i] = toInt[0] == i ? 0 : 1;

                // 두번째 사람부터 체크
                for (int j = 1; j < dp.length &&
                        Math.min(dp[j - 1][0], Math.min(dp[j - 1][1], dp[j - 1][2])) != Integer.MAX_VALUE; j++) {
                    // j번째 사람이 원래 낸 값과 i가 일치한다면
                    // j-1번째 사람이 i를 내지 않은 값들 중 최소값을 가져온다.
                    dp[j][toInt[j]] = Math.min(dp[j - 1][(toInt[j] + 1) % 3], dp[j - 1][(toInt[j] + 2) % 3]);

                    // j번째 사람이 값을 k로 변경하는 경우
                    for (int k = toInt[j] + 1; k < toInt[j] + 3; k++) {
                        // 지금 내는 값 cur
                        // 그렇지 않은 두 값 left, right
                        int left = (k + 2) % 3;
                        int cur = k % 3;
                        int right = (k + 1) % 3;

                        // 만약 j번째 사람이 k를 내는 게 불가능한 경우.
                        if (Math.min(dp[j - 1][left], dp[j - 1][right]) == Integer.MAX_VALUE)
                            continue;

                        // 가능한 경우, 계산
                        dp[j][cur] = Math.min(dp[j - 1][left], dp[j - 1][right]) + 1;
                    }
                }
                // dp를 가지고 마지막 사람이 i를 내지 않은 경우의 최소 값을 가져온다.
                ans = Math.min(ans, Math.min(dp[dp.length - 1][(i + 1) % 3], dp[dp.length - 1][(i + 2) % 3]));
            }
            // 답 기록
            sb.append("Case #").append(testCase+1).append(": ").append(ans).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}