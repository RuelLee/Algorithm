/*
 Author : Ruel
 Problem : Baekjoon 1727번 커플 만들기
 Problem address : https://www.acmicpc.net/problem/1727
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 커플만들기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 남자와 m명의 여자를 커플링하려고 한다
        // 서로간의 성격을 정의한 숫자가 주어지고, 가능한 모든 남여커플을 만들되, 성격 차이의 합을 최소로 하고 싶다
        // 이 때 성격 차이의 합은?
        // DP문제
        // 성격 차이를 줄이기 위해서는 서로의 성격을 정렬하고,
        // DP를 활용하여 커플링했을 때의 최소 차이를 저장한다
        // 예를 들어 dp[i][j]라면, ~ i까지의 남자와 ~ j까지의 여자를 커플링했을 때 성격차이의 최소 합이다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[] majority, minority;       // 다수와 소수로 분리하여 입력 받는다.
        if (n > m) {
            majority = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            minority = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        } else {
            minority = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            majority = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        Arrays.sort(majority);      // 정렬한다. 차이를 최소화하기 위해서는 둘 다 일정하게 증가하거나 감소하는 순서로 살펴봐야한다.
        Arrays.sort(minority);

        // 행을 소수, 열을 다수
        int[][] dp = new int[minority.length + 1][majority.length + 1];
        dpSetting(dp);
        // 소수는 모두 매칭되어야한다.
        for (int i = 1; i < dp.length; i++) {
            // 다수의 경우 매칭되지 않을 수도 있다.
            // 그렇다하더라도 서로 간에 남은 인원 최소 같아야한다(아니면 소수에서도 커플링되지 않는 인원이 발생)
            // 따라서 다수의 경우 동일 번호부터 남은 인원이 같아지는 지점까지 살펴본다.
            for (int j = i; minority.length - i <= majority.length - j; j++) {
                // dp[i][j]의 차례
                // dp[i][j]가 되는 방법은 dp[i - 1][j - 1]에서 다음 순서의 남여가 각각 선택되는 경우거나
                // 다수에 포함된 j번째 인원을 커플링하지 않고 넘어가는 경우(dp[i][j - 1]값을 가져옴)이다.
                // 둘 중 작은 값을 가져온다
                int diff = Math.abs(minority[i - 1] - majority[j - 1]);
                dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 1] + diff);
                dp[i][j] = Math.min(dp[i][j], dp[i][j - 1]);
            }
        }
        // 결과적으로 최소 차이합은 dp 가장 끝에 저장된다. 해당 값을 가져오자.
        System.out.println(dp[dp.length - 1][dp[dp.length - 1].length - 1]);
    }

    static void dpSetting(int[][] dp) {
        for (int[] d : dp)
            Arrays.fill(d, Integer.MAX_VALUE);
        Arrays.fill(dp[0], 0);
    }
}