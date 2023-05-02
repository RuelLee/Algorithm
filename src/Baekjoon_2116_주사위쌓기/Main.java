/*
 Author : Ruel
 Problem : Baekjoon 2116번 주사위 쌓기
 Problem address : https://www.acmicpc.net/problem/2116
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2116_주사위쌓기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int[] opposite = {5, 3, 4, 1, 2, 0};

    public static void main(String[] args) throws IOException {
        // n개의 주사위의 단면도가 주어진다.
        // 일반적인 주사위와는 다르게 맞은 편 눈의 합이 7이 되진 않는다.
        // 순서대로 주사위를 쌓되, 마주보는 면의 눈이 서로 같게 되게 하면서
        // 쌓여진 탑의 한 면의 눈들의 합이 최대가 되게끔하고자 한다.
        // 그 때 그 값은?
        //
        // DP 문제
        // DP를 통해 상단의 면에 따라 사용할 수 있는 면들과 사용할 수 없는 상단, 하단의 값을 알 수 있다.
        // 사용가능한 면들의 눈 중 가장 큰 값을 더해나간다!

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 주사위의 개수
        int n = Integer.parseInt(br.readLine());
        // 주어지는 주사위들의 단면도
        int[][] dices = new int[n][];
        for (int i = 0; i < n; i++)
            dices[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // dp를 통해 윗면의 눈에 따라 분리하여 계산한다.
        int[][] dp = new int[n][7];
        // 첫번째 주사위 세팅
        for (int i = 0; i < dices[0].length; i++) {
            // 주사위의 윗면과 아랫면이 6이 아니라면
            // 사용할 수 있는 가장 큰 값은 6
            if (dices[0][i] != 6 && dices[0][opposite[i]] != 6)
                dp[0][dices[0][i]] = 6;
            // 둘 중 한 면에 6이 포함되어있지만, 5는 없는 경우
            // 사용 가능한 가장 큰 값은 5
            else if (dices[0][i] != 5 && dices[0][opposite[i]] != 5)
                dp[0][dices[0][i]] = 5;
            // 6과 5 모두 포함되어있는 경우, 4
            else
                dp[0][dices[0][i]] = 4;
        }

        // 두번째 주사위부터 순서대로 모두 계산한다.
        for (int i = 1; i < dp.length; i++) {
            // 6면을 모두 살펴본다.
            for (int j = 0; j < dices[i].length; j++) {
                // 사용 가능한 면 중 가장 큰 값
                int plus;
                // 만약 i번째 주사위의 윗면과 아랫면 모두 6이 아닌 경우
                if (dices[i][j] != 6 && dices[i][opposite[j]] != 6)
                    plus = 6;
                // 6은 맞지만 5는 아닌 경우
                else if (dices[i][j] != 5 && dices[i][opposite[j]] != 5)
                    plus = 5;
                // 6과 5인 경우.
                else
                    plus = 4;

                // dp[주사위][윗면] 이므로
                // i번째 주사위의 윗면은 dice[i][j]
                // 아랫면은 dices[i][opposite[j]] 이므로
                // i-1번째 주사위까지 계산한 dp중 윗면이 dices[i][opposite[j]]인 dp에
                // 사용 가능한 면 중 가장 큰 값을 더해 기록해둔다.
                dp[i][dices[i][j]] = dp[i - 1][dices[i][opposite[j]]] + plus;
            }
        }

        // 어느 면이 맨 위든 상관없이
        // 마지막 주사위까지 모두 탑을 쌓았을 때, 한 면의 합이 가장 큰 값을 출력한다.
        System.out.println(Arrays.stream(dp[n - 1]).max().getAsInt());
    }
}