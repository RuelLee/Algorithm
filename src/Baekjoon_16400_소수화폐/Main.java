/*
 Author : Ruel
 Problem : Baekjoon 16400번 소수 화폐
 Problem address : https://www.acmicpc.net/problem/16400
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16400_소수화폐;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static final int LIMIT = 123_456_789;

    public static void main(String[] args) throws IOException {
        // 소수 나라의 화폐는 소수로 이루어져있다.
        // 정확히 n원을 소수 화폐로 지불하고자할 때
        // 그 경우의 수는?
        //
        // 에라토스테네스의 체, DP 문제
        // 먼저 n이하의 소수를 모두 구한 뒤
        // 소수 화폐별로 지불할 수 있는 경우의 수를 DP를 통해 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 지불해야하는 금액 n
        int n = Integer.parseInt(br.readLine());
        
        boolean[] notPrimeNumber = new boolean[n + 1];
        // 소수
        List<Integer> primeNumbers = new ArrayList<>();
        for (int i = 2; i < notPrimeNumber.length; i++) {
            if (notPrimeNumber[i])
                continue;
            primeNumbers.add(i);
            for (int j = 2; i * j < notPrimeNumber.length; j++)
                notPrimeNumber[i * j] = true;
        }

        int[] dp = new int[n + 1];
        dp[0] = 1;
        // 5원을 만들 때 2 + 3원을 하던, 3 + 2원을 하던 순서만 바뀌었을 뿐
        // 결국 같은 경우의 수이다.
        // 따라서 중복되는 경우를 막기 위해 소수 화폐별로 계산을 한다.
        for (int primeNumber : primeNumbers) {
            for (int i = 0; i < dp.length; i++) {
                // 경우의 수가 존재하지 않는다면 건너뛴다.
                if (dp[i] == 0)
                    continue;
                // n원을 초과한다면 primeNumber 화폐를 사용하는 것을 종료
                else if (i + primeNumber >= dp.length)
                    break;
                
                // i + primeNumber원을 만드는 방법은 i원에 + primeNumber만큼을 더하는 경우
                dp[i + primeNumber] += dp[i];
                // 모듈러 연산
                dp[i + primeNumber] %= LIMIT;
            }
        }
        // n원을 소수 화폐로 지불하는 경우의 수 출력
        System.out.println(dp[n]);
    }
}