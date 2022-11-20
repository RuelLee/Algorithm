/*
 Author : Ruel
 Problem : Baekjoon 5557번 1학년
 Problem address : https://www.acmicpc.net/problem/5557
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5557_1학년;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 숫자들이 주어진다.
        // 마지막 두 숫자 사이에는 = 을 넣고
        // 다른 두 숫자들 사이는 + , - 를 넣어 등식을 만들고자 한다.
        // 제한 조건으로 왼쪽으로부터 계산할 때 0 이상 20 이하의 수만 만들어져야한다.
        // 만들 수 있는 올바른 등식의 수는?
        //
        // DP문제
        // 처음 문제를 읽을 때 대충 읽어서 = 을 아무 곳이나 삽입할 수 있는 줄 알고,
        // 코딩을 했으나, 다시 읽어보니 마지막 두 수 사이에만 넣는 것이었다.
        // dp[i][j]를 통해 , 0 ~ i번째 수까지로 만들 수 있는 j값의 경우의 수를 세어나간다.
        // 최종적으로 dp[n-2]에 기록된 값들은 n -1 개의 수로 만들 수 있는 값들의 경우의 수
        // 마지막 수와 동일한 값을 갖는 경우의 수를 출력해준다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 수들
        int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // DP
        // dp[i][j] = 0 ~ i번째 수로 만들 수 있는 j값의 경우의 수
        long[][] dp = new long[n - 1][21];
        dp[0][nums[0]] = 1;
        for (int i = 0; i < dp.length - 1; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                // i번째까지의 수로 j값을 만드는 경우가 없다면 건너뛴다.
                if (dp[i][j] == 0)
                    continue;

                // 존재한다면, i + 1번째 수를 더하거나 뺐을 때
                // 범위를 넘어가지 않는다면 생성되는 수의 경우의 수를 계산해준다.
                // 현재 값 j에서 i + 1번째 수를 더한 값이 20을 넘지 않는다면
                // dp[i + 1][j + nums[i + 1]] 값은 현재 dp[i][j]의 경우의 수와 같다.
                // dp[i][j]인 경우에서 nums[i + 1]를 더하는 경우이므로
                if (j + nums[i + 1] <= 20)
                    dp[i + 1][j + nums[i + 1]] += dp[i][j];
                // 빼기일 경우도 마찬가지.
                if (j - nums[i + 1] >= 0)
                    dp[i + 1][j - nums[i + 1]] += dp[i][j];
            }
        }

        // n - 1번째 수까지의 더하거나 뺀 결과 값이
        // n번째 수와 같은 값을 갖는 경우는 몇 가지인지 출력한다.
        System.out.println(dp[n - 2][nums[n - 1]]);
    }
}