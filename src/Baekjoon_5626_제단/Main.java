/*
 Author : Ruel
 Problem : Baekjoon 5626번 제단
 Problem address : https://www.acmicpc.net/problem/5626
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5626_제단;

import java.io.*;
import java.util.Arrays;

public class Main {
    static int[] delta = {-1, 0, 1};
    public static void main(String[] args) throws IOException {
        // n열 짜리 제단을 쌓는다.
        // 같은 높이를 갖는 연속한 열을 선택하여, 첫 열과 마지막 열을 제외한 모든 열의 높이를 1 높인다.
        // 수백년이 흐르는 동안 도둑들이 제단의 일부 열을 훔쳐갔다.
        // 남은 제단의 높이들을 가지고, 가능한 제단의 경우의 수를 출력하라
        //
        // DP문제
        // 제단을 쌓을 때, 동일한 높이를 갖는 연속한 열을 선택하여, 첫 열과 마지막 열을 제외하고 높이를 높였다.
        // 이를 생각해보면 제단들의 높이는 이전보다 같거나 큰 높이로 증가하다, 어느 시점부터 다시 같거나 작은 높이를 갖으며 낮아진다.
        // 이를 통해 각 열을 살펴보며, 가능한 높이의 경우 수를 계산해나간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 제단 열의 수
        int n = Integer.parseInt(br.readLine());
        
        // 각 제단의 높이
        int[] altars = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // DP는 2개의 열로 선언하여 이전 경우의 수와
        // 이번 경우의 수, 두 경우를 분리하여 계산한다.
        long[][] dp = new long[2][10_001];
        // 제단의 높이가 최대 1만까지 주어지지만, 가능한 높이 구간은 일부이다.
        // 따라서 높이 구간을 갖고서 해당 구간만 계산하자.
        int minHeight = 0;
        int maxHeight = 0;
        // 첫번째 제단은 무조건 높이가 0이여야 한다.
        // 따라서 0이나 -1이 들어온 경우, 경우의 수가 1이지만
        // 그 외가 높이가 들어오는 경우, 불가능한 경우이다.
        dp[0][0] = (altars[0] == 0 || altars[0] == -1) ? 1 : 0;
        
        // 첫번째 제단부터 마지막 제단까지
        for (int i = 1; i < altars.length; i++) {
            // 이번에 사용할 DP를 0으로 채운다.
            Arrays.fill(dp[i % 2], 0);
            
            // 도둑이 훔쳐간 제단이라면
            if (altars[i] == -1) {
                // 이전 제단이 가능했던 높이를 바탕으로
                // 이번 제단이 가능한 높이들의 경우의 수를 구한다.
                // minHeight부터 maxHeight까지
                for (int j = minHeight; j <= maxHeight; j++) {
                    // 감소하거나 같거나, 증가하거나하는 3가지 경우의 가능하다.
                    for (int k : delta) {
                        // 해당하는 경우의 높이
                        int next = j + k;
                        // 범위를 벗어나지 않는다면 해당 경우의 수를 계산한다.
                        if (next >= 0 && next < dp[i % 2].length) {
                            dp[i % 2][next] += dp[(i - 1) % 2][j];
                            dp[i % 2][next] %= 1_000_000_007;
                        }
                    }
                }
                // 가장 낮은 높이가 감소하거나
                // 가장 높은 높이에서 증가하는 경우가 발생할 경우, 제단 높이 범위가 달라진다
                // 해당 변경 사항 반영
                minHeight = Math.max(minHeight - 1, 0);
                maxHeight = Math.min(maxHeight + 1, 10000);
            } else {            // 만약 제단이 남아있는 경우라면
                // 해당 제단의 높이가 가능하려면, 이번 제단의 높이보다 1 작았거나, 같았거나, 1 컸던 경우만 가능하다.
                // 위 내용을 바탕으로 이번 제단에서 가능한 경우의 수를 계산한다.
                for (int j : delta) {
                    // 가능한 이전 제단의 높이.
                    int pre = altars[i] + j;
                    // 범위를 벗어나지 않는다면
                    // 이번 제단 높이에 가능한 경우의 수로 더해준다.
                    if (pre >= 0 && pre < dp[i % 2].length) {
                        dp[i % 2][altars[i]] += dp[(i - 1) % 2][pre];
                        dp[i % 2][altars[i]] %= 1_000_000_007;
                    }
                }
                // 해당 제단의 높이만 가능한 것이므로
                // minHeight, maxHeight 값 모두 이번 제단의 높이로 바꿔준다.
                minHeight = maxHeight = altars[i];
            }
        }

        // 결국 마지막 제단의 높이는 0이여야한다.
        // 해당하는 경우의 수 출력.
        System.out.println(dp[(n - 1) % 2][0]);
    }
}