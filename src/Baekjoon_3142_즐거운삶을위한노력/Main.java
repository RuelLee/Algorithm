/*
 Author : Ruel
 Problem : Baekjoon 3142번 즐거운 삶을 위한 노력
 Problem address : https://www.acmicpc.net/problem/3142
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3142_즐거운삶을위한노력;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        // 키보드와 화면이 주어진다.
        // 화면에 초기 정수는 1이다.
        // n(1 ~ 500_000)명의 사용자는 하나의 정수(1 ~ 1_000_000)를 입력한다.
        // 화면에 떠있는 정수와 곱한다.
        // 결과가 완전제곱수라면 사용자는 기쁘게 춤을 추며 떠난다.
        // 기쁘게 되는 사람을 알아내라
        //
        // 에라토스테네스의 체, 정수
        // 완전제곱수가 되려면
        // 소인수분해를 했을 때, 각 인수들이 짝수로 주어져야한다.
        // 따라서 사람들이 입력하는 수를 소인수분해하여
        // 인수의 개수를 세어나가며, 그 수가 모두 짝수일 때만 완전제곱수로 기쁘게 떠나는 사람이 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n명의 사용자
        int n = Integer.parseInt(br.readLine());
        
        // 에라토스테네스의 체
        boolean[] isPrimeNumber = new boolean[1_000_001];
        Arrays.fill(isPrimeNumber, true);
        // 소수
        List<Integer> primeNumbers = new ArrayList<>();
        isPrimeNumber[0] = isPrimeNumber[1] = false;
        for (int i = 2; i < isPrimeNumber.length; i++) {
            if (!isPrimeNumber[i])
                continue;
            primeNumbers.add(i);
            for (int j = 2; i * j < isPrimeNumber.length; j++)
                isPrimeNumber[i * j] = false;
        }

        StringBuilder sb = new StringBuilder();
        // 소인수분해를 통해
        // 각 인수가 몇 개인지 센다.
        int[] factors = new int[1_000_001];
        // 홀수인 인수의 개수
        int odd = 0;
        for (int i = 0; i < n; i++) {
            // 사용자가 입력한 수
            int num = Integer.parseInt(br.readLine());
            while (num > 1) {
                // num이 소수라면 그대로 
                // 인수 개수로 계산
                if (isPrimeNumber[num]) {
                    if (++factors[num] % 2 == 1)
                        odd++;
                    else
                        odd--;
                    break;
                }

                // 아닌 경우, 나누어떨어지는 소수를 찾는다.
                for (int j = 0; primeNumbers.get(j) <= Math.sqrt(num); j++) {
                    if (num % primeNumbers.get(j) == 0) {
                        if (++factors[primeNumbers.get(j)] % 2 == 1)
                            odd++;
                        else
                            odd--;
                        num /= primeNumbers.get(j);
                        break;
                    }
                }
            }
            // 홀수인 인수가 0일 때만 완전 제곱수 
            sb.append(odd == 0 ? "DA" : "NE").append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}