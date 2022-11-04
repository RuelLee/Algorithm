/*
 Author : Ruel
 Problem : Baekjoon 1943번 동전 분배
 Problem address : https://www.acmicpc.net/problem/1943
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1943_동전분배;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 여러 테스트케이스로 이루어진다.
        // n개의 동전 종류와 그 개수가 주어진다.
        // 그 금액을 정확히 반으로 나눌 수 있다면 1, 아니라면 0을 출력한다.
        //
        // DP를 활용한 배낭 문제
        // 동전 별로 금액을 살펴보며
        // 해당 동전으로 만들 수 있는 금액들을 표시해나간다.
        // 최종적으로 총합의 반 금액을 정확히 만들 수 있는지 확인한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String input = br.readLine();
        StringBuilder sb = new StringBuilder();
        while (input != null) {
            // 동전의 종류
            int n = Integer.parseInt(input);
            // 각 동전의 금액과 개수.
            int[][] coins = new int[n][];
            for (int i = 0; i < coins.length; i++)
                coins[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            // 최대 금액이 10만원으로 주어지므로 그에 반인 5만까지만 DP로 잡자.
            boolean[] dp = new boolean[50001];
            // 초기값으로 0원은 가능.
            dp[0] = true;
            // 총액 또한 따로 구한다.
            int sum = 0;
            // 동전 별로
            for (int[] coin : coins) {
                // 총합에도 해당 동전 반영
                sum += coin[0] * coin[1];

                // 같은 동전에 대해 중복 계산이 되는걸 막기 위해
                // 큰 값에서부터 내려온다.
                for (int i = dp.length - 1; i >= 0; i--) {
                    // i 금액을 만드는 것이 가능했다면
                    if (dp[i]) {
                        // coin 동전을 1개부터 j개까지 사용했을 때의 금액도 dp에 가능하다고 표시해주자.
                        for (int j = 1; j <= coin[1]; j++) {
                            // 만약 그 금액이 5만원을 넘는다면 필요없는 결과.
                            // 반복문을 종료
                            if (i + coin[0] * j >= dp.length)
                                break;

                            // i + coin[0] * j 금액이 가능하다고 표시.
                            dp[i + coin[0] * j] = true;
                        }
                    }
                }
            }
            // 최종적으로 총합이 짝수이며(홀수 일 경우 정확히 반으로 나누는 것이 불가능)
            // 반액이 가능할 경우 1 기록. 그렇지 않는 경우 0 기록.
            sb.append(sum % 2 == 0 && dp[sum / 2] ? 1 : 0).append("\n");
            input = br.readLine();
        }
        // 전체 결과 출력.
        System.out.println(sb);
    }
}