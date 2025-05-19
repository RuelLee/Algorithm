/*
 Author : Ruel
 Problem : Baekjoon 1914번 하노이 탑
 Problem address : https://www.acmicpc.net/problem/1914
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1914_하노이탑;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class Main {
    static StringBuilder sb;

    public static void main(String[] args) throws IOException {
        // 3개의 장대와 n개의 서로 다른 크기의 원판이 주어진다.
        // 첫번째 장대에 큰 순서대로 원판들이 쌓여있다.
        // 이를
        // 1. 한 개의 한 원판만 다른 장대로 옮길 수 있다.
        // 2. 쌓아 놓은 원판은 항상 위의 것이 아래의 것보다 작아야 한다.
        // 두 규칙을 만족하며 3번째 장대로 옮기고자 한다.
        // 이 때의 원판을 옮기는 횟수와
        // n이 20이하일 경우는 장대의 최상단 원판을 옮기는 과정을 출력한다.
        //
        // 재귀 문제
        // 잘 알려진 하노이탑 문제.
        // from, to, extra 장대가 있고, from -> to로 n개의 원판을 옮긴다면
        // 이는 from에서 extra로 n-1개의 원판을 옮긴 후
        // from에 to로 마지막 가장 큰 원판을 옮기고
        // extra에 있는 n-1개의 원판을 to로 옮기는 과정과 같다.
        // 이 과정을 재귀로 풀어내면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 원판
        int n = Integer.parseInt(br.readLine());

        // n이 최대 100까지 주어져 long 범위도 벗어난다.
        // 따라서 BigInteger로 처리
        // 단순히 이동 횟수만 빠르게 계산해둔다.
        BigInteger[] dp = new BigInteger[n + 1];
        dp[1] = BigInteger.ONE;
        for (int i = 2; i < dp.length; i++)
            dp[i] = BigInteger.ONE.add(dp[i - 1].multiply(BigInteger.TWO));

        sb = new StringBuilder();
        // 이동 횟수 기록
        sb.append(dp[n]).append("\n");
        // n이 20 이하일 때는 직접 재귀를 통해 과정을 시행하며 기록
        if (n <= 20)
            recursion(n, 1, 3, 2);
        // 답 출력
        System.out.print(sb);
    }

    // 재귀
    // size개의 원판을 from에서 to로 옮긴다.
    static void recursion(int size, int from, int to, int extra) {
        // size가 0일 경우.
        // 더 이상 옮길 원판이 없으므로 종료.
        if (size == 0)
            return;

        // 그 외의 경우
        // n-1개의 원판을 extra로 옮긴 후
        recursion(size - 1, from, extra, to);
        // 마지막 원판을 to로 옮기고
        sb.append(from).append(" ").append(to).append("\n");
        // extra에 있는 n-1개의 원판을 다시 to로 옮긴다.
        recursion(size - 1, extra, to, from);
    }
}