/*
 Author : Ruel
 Problem : Baekjoon 2201번 이친수찾기
 Problem address : https://www.acmicpc.net/problem/2201
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2201_이친수찾기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 이친수는 0으로 시작하지 않는다.
        // 연속하여 1이 두 번 나타나지 않는다. 를 만족하는 수이다.
        // 1, 10, 100, 101, 1000, 1001은 이친수이다.
        // k번째 이친수를 구하라
        //
        // DP 문제
        // dp[i] = i자리의 이친수의 마지막 수의 순서로 정의한다.
        // dp[1] = 1자리 이친수 중 마지막 수의 순서이므로 1
        // dp[2]= 2자리 이친수 중 마지막의 수의 순서이므로 1, 10 이므로 2
        // dp[3]= 3자리 이친수 중 마지막의 수의 순서이므로 1, 10, 100, 101 이므로 4
        // dp[0]은 0 자리 수는 없으나, 위의 경우들을 살펴보면, 앞에 1이 오는 경우에 한해 맨 뒤에 0이 올 수 있다.
        // 따라서 1로 정의하고 계산한다.
        // 위와 같이 정의한다.
        // n자리의 이친수의 개수는
        // 맨 앞의 수가 10이고, 나머지 뒤의 n-2자리의 수의 개수 +
        // 맨 앞의 수가 100이고 나머지 뒤의 n-3자리 수의 개수 + ...
        // 따라서 dp[n] = dp[n-2] + dp[n-3] + ... + dp[0] 으로 정의할 수 있다.
        // 그 후, 남은 순서와 dp 값을 비교하며, k값이 더 크다면, 값을 찾는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // k번째 이친수를 찾는다.
        // 임의로 0자리 수가 1개 있다고 계산했으므로 k+1번째 수를 찾는다.
        long k = Long.parseLong(br.readLine()) + 1;

        // psums[i] = i번째 자릿수 중 가장 큰 수의 순서
        long[] psums = new long[87];
        // 0자리의 수가 하나 있다고 가정.
        psums[0] = 1;
        // 1자리 수 1의 순서 2
        psums[1] = 2;

        // 두 값을 바탕으로 k의 제한에 맞는 조건까지의 자릿수를 찾는다.
        for (int i = 2; i < psums.length; i++)
            psums[i] = psums[i - 1] + psums[i - 2];

        // 답의 자릿수 계산
        int idx = 0;
        while (psums[idx] < k)
            idx++;
        // 답을 채울 공간
        int[] answer = new int[idx];
        // k가 0보다 큰 동안
        while (k > 0) {
            // k보다 같거나 작은 가장 큰 값을 psums에서 찾는다.
            idx = 0;
            while (psums[idx + 1] <= k)
                idx++;

            // 만약 k와 psums[idx]가 일치한다면
            // idx자리의 수 중 가장 큰 수가 필요하다.
            // 이 수는 101010...과 같이 10이 반복되는 형태이다.
            // 해당 수를 기록하고 남은 k = 0
            if (psums[idx] == k) {
                while (idx > 0) {
                    answer[answer.length - idx] = 1;
                    idx -= 2;
                }
                k = 0;
            } else {
                // 그 외의 경우
                // 우리는 idx자리의 수 내에서는 답을 찾을 수 없다.
                // idx+1번째 자리에 1을 넣고, k에서 idx이하 자릿수의 수 만큼을 빼준다.
                answer[answer.length - 1 - idx] = 1;
                k -= psums[idx];
            }
        }

        // 구한 답을 기록
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < answer.length; i++)
            sb.append(answer[i]);
        System.out.println(sb);
    }
}