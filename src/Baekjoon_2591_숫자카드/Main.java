/*
 Author : Ruel
 Problem : Baekjoon 2591번 숫자카드
 Problem address : https://www.acmicpc.net/problem/2591
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2591_숫자카드;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1부터 34까지의 수가 적힌 카드가 충분히 많이 있다.
        // 40자리 이하의 수가 주어질 때,
        // 숫자 카드를 이어붙여 해당 수를 만드는 방법의 가짓수를 출력하라
        //
        // 메모이제이션 문제
        // 각 자리 혹은 다음 자리까지 2자리의 수가 수의 범위를 만족하는지를 살펴보며
        // 해당 자리에서 만들 수 있는 숫자카드 조합의 수를 bottom - up 방식으로 채워나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력으로 주어지는 숫자.
        String n = br.readLine();
        // 메모이제이션.
        int[] memo = new int[n.length()];

        System.out.println(fillMemo(n, 0, memo));
    }

    static int fillMemo(String number, int idx, int[] memo) {
        // 수의 길이를 넘어가는 idx가 주어졌다면, 숫자카드들로
        // 해당 수를 완성한 경우.
        // 한 가지로써 세준다.
        if (idx >= memo.length)
            return 1;
        
        // 해당 idx에 DP값이 없는 경우
        // 처음 해당 idx를 방문한 경우, 메모이제이션 작업 진행
        if (memo[idx] == 0) {
            // 해당 자리만 살펴보는 경우.
            int singleDigit = number.charAt(idx) - '0';
            // 하나의 수 카드를 만족하는지 확인하고 재귀로 메소드 호출
            if (singleDigit > 0 && singleDigit < 10)
                memo[idx] += fillMemo(number, idx + 1, memo);
            // idx와 idx + 1 자리의 수를 통해 두자릿수를 만드는 경우.
            if (idx + 1 < number.length()) {
                int doubleDigit = Integer.parseInt(number.substring(idx, idx + 2));
                // 해당 수가 카드 범위를 만족하는지 확인하고 재귀로 메소드 호출
                if (doubleDigit > 9 && doubleDigit < 35)
                    memo[idx] += fillMemo(number, idx + 2, memo);
            }
        }
        // 계산된 숫자카드 조합의 수 반환.
        return memo[idx];
    }
}