/*
 Author : Ruel
 Problem : Baekjoon 2624번 동전 바꿔주기
 Problem address : https://www.acmicpc.net/problem/2624
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2624_동전바꿔주기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // T원의 지폐를 동전으로 바꾸고 싶다.
        // k가지의 동전의 종류가 주어지며, 각 동전의 개수가 주어진다.
        // T원을 동전으로 바꾸는 경우의 수는?
        //
        // DP문제
        // 각 동전마다 주어진 개수가 있다는 점에 유의하며 DP로 문제를 풀어준다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // t와 k
        int t = Integer.parseInt(br.readLine());
        int k = Integer.parseInt(br.readLine());

        // 주어지는 동전들.
        int[][] coins = new int[k][];
        for (int i = 0; i < coins.length; i++)
            coins[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // t원까지 배열을 생성해주고.
        int[] dp = new int[t + 1];
        // 0원을 만드는 방법 한가지.
        dp[0] = 1;
        // 각 동전마다 살펴보며 만들 수 있는 금액의 가짓수를 계산한다.
        for (int[] coin : coins) {
            // 큰 금액부터 -> 작은 금액으로 계산해나간다.
            // DP를 동전마다 세우지 않고, 금액으로만 세웠으므로
            // 오름차순으로 계산하다간 각 동전의 개수가 중복으로 계산될 수 있다.
            for (int i = dp.length - 1; i > 0; i--) {
                // 각 동전의 최대 개수까지만
                for (int j = 1; j <= coin[1]; j++) {
                    // 만약 coin 동전을 j개 사용한 값이 i원을 벗어난다면 멈춘다.
                    if (i - coin[0] * j < 0)
                        break;

                    // 그렇지 않다면, 이전에 계산된 i - coin[0] * j 원을 만드는 방법의 수만큼
                    // i원을 만드는 방법이 추가된다.
                    // i - coin[0] * j 원에 coin을 j개만큼 사용하면 i원이 되므로.
                    dp[i] += dp[i - coin[0] * j];
                }
            }
        }
        // 최종적으로 구해진 t원을 만드는 방법의 수를 출력한다.
        System.out.println(dp[t]);
    }
}