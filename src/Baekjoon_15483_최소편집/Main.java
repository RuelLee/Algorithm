/*
 Author : Ruel
 Problem : Baekjoon 15483번 최소 편집
 Problem address : https://www.acmicpc.net/problem/15483
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15483_최소편집;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 두 문자열 A와 B가 주어졌을 때, A를 B로 만들고자 한다.
        // 만드는 방법에는 3가지 연산이 있다.
        // 삽입: A의 한 위치에 문자 하나를 삽입한다.
        // 삭제: A의 문자 하나를 삭제한다.
        // 교체: A의 문자 하나를 다른 문자로 교체한다.
        // 위 연산들을 최소로 사용하여 바꾸고자할 때, 최소 편집 횟수는?
        //
        // DP 문제
        // dp[A문자열의 현재 비교 대상 문자 위치][B문자열의 현재 비교 대상 문자 위치] = 최소 편집 횟수
        // 로 배열을 만들고 풀면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 주어지는 A, B 문자열
        String a = br.readLine();
        String b = br.readLine();

        // 두 문자열 모두 비교를 마치면 문자열 길이를 가르키게 된다.
        // 위 점을 고려하여 길이 + 1로 배열을 만든다.
        int[][] dp = new int[a.length() + 1][b.length() + 1];
        // 초기값으로 큰 값 대입
        for (int[] d : dp)
            Arrays.fill(d, Integer.MAX_VALUE);
        // 처음 비교할 때는 편집 횟수가 0
        dp[0][0] = 0;
        
        // 문자열 내를 가르킬 때까지만 살펴본다.
        for (int i = 0; i < dp.length - 1; i++) {
            for (int j = 0; j < dp[i].length - 1; j++) {
                // 큰 값이라면 도달하는 방법이 없는 경우. 건너뛴다.
                if (dp[i][j] == Integer.MAX_VALUE)
                    continue;

                // 각각의 인덱스가 가르키는 문자가 동일하다면
                // 편집하지 않고, 다음 인덱스로 넘기는 것이 가능.
                if (a.charAt(i) == b.charAt(j))
                    dp[i + 1][j + 1] = Math.min(dp[i + 1][j + 1], dp[i][j]);

                // 삽입하는 경우.
                // 현재 i가 가르키는 위치 앞에 j가 가리키는 문자와 동일한 문자를 삽입한다.
                // 이를 통해 j가 가리키는 인덱스를 하나 증가 가능.
                dp[i][j + 1] = Math.min(dp[i][j + 1], dp[i][j] + 1);
                // 삭제하는 경우
                // 현재 i가 가리키는 문자를 삭제한다.
                // 이를 통해 i가 가리키는 인덱스를 하나 증가 가능.
                dp[i + 1][j] = Math.min(dp[i + 1][j], dp[i][j] + 1);
                // 교체하는 경우
                // 현재 i가 가리키는 문자를 j가 가르키는 문자로 교체한다.
                // 이를 통해 i, j 두 인덱스를 하나씩 증가 가능.
                dp[i + 1][j + 1] = Math.min(dp[i + 1][j + 1], dp[i][j] + 1);
            }

        }

        // a의 마지막 인덱스까지 모두 연산을 적용하였지만 b의 인덱스가 끝나지 않은 경우
        // a의 문자열의 b보다 짧거나 a의 문자들을 삭제하여 더 짧아진 경우.
        // 위 경우 삽입 연산을 통해 같은 문자열로 만드는 것이 가능.
        for (int i = 0; i < dp[a.length()].length - 1; i++)
            dp[a.length()][i + 1] = Math.min(dp[a.length()][i + 1], dp[a.length()][i] + 1);

        // b의 마지막 인덱스까지 도달했지만, a가 마지막 인덱스에 도달하지 못한 경우.
        // a에 삭제 연산을 통해 같은 문자열로 만드는 것이 가능.
        for (int i = 0; i < dp.length - 1; i++)
            dp[i + 1][b.length()] = Math.min(dp[i + 1][b.length()], dp[i][b.length()] + 1);

        // 최종적으로 두 문자열이 같아지는 최소 편집 횟수를 출력한다.
        System.out.println(dp[a.length()][b.length()]);
    }
}