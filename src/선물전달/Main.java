/*
 Author : Ruel
 Problem : Baekjoon 1947번 선물 전달
 Problem address : https://www.acmicpc.net/problem/1947
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 선물전달;

import java.util.Scanner;

public class Main {
    static long[] dp;
    static int LIMIT = 1_000_000_000;

    public static void main(String[] args) {
        // 코드로는 너무 간단하지만 DP 문제는 점화식을 떠오리는 것이 관건!
        // 대회에 참가한 학생들이 선물 하나씩 가져와 나눠갖는다고 했을 때
        // 자신이 준비한 선물을 제외한 선물을 받을 수 있는 가지수를 구하는 문제
        // n번째 학생이 선물을 받는 가지수를 생각해보자
        // 먼저 (n - 1)명과 (n - 2)명이 나눠가질 수 있는 가지수를 안다고 가정하자
        // (n - 1)명이 나눠 받은 가지수에, n번째 학생이 끼어들어 자신과의 선물을 임의의 한명과 교환해도 성립한다
        // 이는 (n - 1)명이 나눠 받을 수 있는 가지수 * (n - 1)이 된다.(1 ~ (n - 1)번째 학생까지 교환이 가능하므로)
        // 또한 (n - 2)명이 나눠가진 가지수에, n - 1번째 학생이 끼어들어 n - 1번째 학생만 자기 자신의 선물을 들고 있는 가지수는
        // (n - 2)명이 나눠 가진 가지수 * (n - 1) 가지수이다.(총 (n - 1)명의 학생이 각각 자기 자신의 선물을 가지고 있을 수 있으므로)
        // 이 경우에 다시 n번째 학생이 끼어들어, n번째 학생과 (n - 1)번째 학생이 서로 선물을 교환하면 나눠 가질 수 있는 방법이 된다
        // 이 경우의 가지수는 (n - 2)명이 나눠 가진 가지수 * (n - 1)이다.
        // 따라서 n명이 선물을 나눠 가지는 방법의 가지수는
        // = (n - 1)명이 나눠가지는 수 * (n - 1) + (n - 2)명이 나눠가지는 수 * (n - 1)이다.
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        dp = new long[n + 1];
        if (n >= 2)
            dp[2] = 1;
        for (int i = 3; i < dp.length; i++) {
            dp[i] = (dp[i - 1] * (i - 1)
                    + dp[i - 2] * (i - 1)) % LIMIT;
        }
        System.out.println(dp[n]);
    }
}