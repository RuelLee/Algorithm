/*
 Author : Ruel
 Problem : Baekjoon 2216번 문자열과 점수
 Problem address : https://www.acmicpc.net/problem/2216
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 문자열과점수;

import java.util.Scanner;

public class Main {
    static int[][] dp;

    public static void main(String[] args) {
        // 문자열 2개가 주어진다
        // 각 문자열의 임의의 위치에 공백을 삽입하여 두 문자열을 비교한다
        // 같은 위치의 문자가 일치한다면 A점, 둘 중 하나가 공백이라면 B점, 서로 다른 문자일 경우 C점이 주어진다(둘 다 공백일 수는 없다)
        // 이 때 최대 점수를 구하여라
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();
        int c = sc.nextInt();
        sc.nextLine();
        String stringA = sc.nextLine();
        String stringB = sc.nextLine();

        // 열은 A문자열, 행은 B문자열
        dp = new int[stringA.length() + 1][stringB.length() + 1];
        dp[0][0] = 0;       // 비교 시작 전 초기값 세팅
        // 열이 하나 값이 증가했다는 것은, 이전 A문자열의 문자에 공백을 추가하여 B의 한 문자를 처리했고, 다음 B문자를 보겠다는 의미.
        // 따라서 b점수가 추가된다.
        for (int i = 1; i < dp.length; i++)
            dp[i][0] = dp[i - 1][0] + b;
        // 행에 대해서도 마찬가지.
        for (int i = 1; i < dp[0].length; i++)
            dp[0][i] = dp[0][i - 1] + b;

        // 각 문자열의 첫번째 문자에 대한 인덱스를 1이라고 두겠다.
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[i].length; j++) {
                // 서로 다른 문자라면, 값이 정해지는 세가지 경우가 있다
                // 1. i-1, j-1 문자가 서로 일치하지 않아서 c점을 받아서 넘어온 경우
                // 2. i-1, j번 혹은 3.i, j-1번이 일치하지 않아 b점을 받아 넘어온 경우.
                int whenDifferent = Math.max(dp[i - 1][j - 1] + c, Math.max(dp[i - 1][j] + b, dp[i][j - 1] + b));
                // 4. 일치한다면 i-1, j-1번의 경우에서 A점을 더하면 된다.
                // 하지만 최종적인 값은 일치했을 경우에는, 위 네가지 경우에 대해서 가장 큰 값이고
                // 일치 하지 않았을 경우에는 세가지 경우에 대해서 가장 큰 값이다.
                dp[i][j] = stringA.charAt(i - 1) == stringB.charAt(j - 1) ? Math.max(dp[i - 1][j - 1] + a, whenDifferent) : whenDifferent;
            }
        }
        // 최종적인 값은 dp의 가장 마지막 위치에 자리한다.
        System.out.println(dp[dp.length - 1][dp[dp.length - 1].length - 1]);
    }
}