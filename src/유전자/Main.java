/*
 Author : Ruel
 Problem : Baekjoon 2306번 유전자
 Problem address : https://www.acmicpc.net/problem/2306
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 유전자;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 가장 작은 유전자 "at", "gc"를 KOI 유전자라고 부른다
        // X가 KOI유전자를 만족한다면 "aXt"나 "gXc"도 KOI 유전자이고, XX형태 또한 KOI 유전자이다
        // KOI 부분 서열로 구성되어있어, 중간에 0개 이상의 유전자를 버린 형태라도 성립한다 ( abt의 형태라도 b를 버리면 at가 나오므로 성립)
        // 유전자 배열이 주어질 때 가장 긴 KOI 유전자의 길이를 구하라
        // DP문제인 것은 느낌이 들었지만 어떻게 적용해야할지에 대한 고민이 길었다
        // 먼저 검사할 유전자의 길이를 2부터 차근차근 늘려갈 것이다
        // dp[i][j]는 i인덱스로부터 j인덱스까지의 부분의 KOI 유전자 중 가장 긴 길이를 저장한다
        // i인덱스와 j인덱스가 KOI 유전자를 만족한다면 이보다 안쪽인 dp[i+1][j-1]의 길이에 2를 더한 값을 저장해준다
        // 이는 감싸는 형태만 고려된 값이므로, XX 형태를 고려하기 위해서 i와 j 사이에 k값을 두고, dp[i][k] + dp[k+1][j] 값이 dp[i+1][j-1] 값보다 더 크다면 해당 값으로 갱신해준다
        // 최종적으로 전체 길이의 중 가장 긴 길이의 KOI 유전자의 길이는 dp[0][dp.length()-1]에 저장될 것이다.
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        int[][] dp = new int[input.length()][input.length()];
        for (int diff = 1; diff < input.length(); diff++) {
            for (int i = 0; i < input.length(); i++) {
                int j = i + diff;
                if (j >= input.length())
                    break;

                if ((input.charAt(i) == 'a' && input.charAt(j) == 't') ||
                        (input.charAt(i) == 'g' && input.charAt(j) == 'c')) {       // 선택된 i, j 값이 KOI 유전자를 만족한다면
                    dp[i][j] = dp[i + 1][j - 1] + 2;        // 감싸는 형태의 길이를 증가시켜준다.
                }
                for (int k = i; k < j; k++)     // 이번에는 XX형태로 덧붙이는 형태의 길이가 더 길진 않은지 체크해준다.
                    dp[i][j] = Math.max(dp[i][j], dp[i][k] + dp[k + 1][j]);
            }
        }
        System.out.println(dp[0][dp.length - 1]);
    }
}