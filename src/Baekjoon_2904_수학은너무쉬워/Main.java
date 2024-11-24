/*
 Author : Ruel
 Problem : Baekjoon 2904번 수학은 너무 쉬워
 Problem address : https://www.acmicpc.net/problem/2904
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2904_수학은너무쉬워;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수가 주어지고,
        // 그 중 두 수를 a, b를 뽑아, a를 나눌 수 있는 소수 x를 골라
        // a를 a / x로, b를 b * x로 만들 수 있다.
        // 위 행동을 무한히 할 수 있을 때,
        // 할머니는 마지막에 남아있는 수들의 최대공약수만큼 사탕을 주신다.
        // 받을 수 있는 최대 사탕의 수와 그 때의 최소 행동의 수는?
        //
        // 에라토스테네스의 체 문제
        // 남아있는 수들의 최대공약수만큼 사탕을 주시므로
        // 소인수분해하여, 각각의 인수들이 모든 수에 골고루 배치되는 것이
        // 최대공약수를 가장 키울 수 있는 방법이다.
        // 따라서, 먼저 에라토스테네스의 체를 통해 소수를 구하고
        // 각각의 수를 소인수분해한다.
        // 그 후, 인수의 총 개수를 가지고서, 각각이 평균 인수 만큼 되려면 행해야하는 행동의 수
        // 또한 인수의 평균 개수를 가지고서 최대공약수를 구할 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 수
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] nums = new int[n];
        for (int i = 0; i < nums.length; i++)
            nums[i] = Integer.parseInt(st.nextToken());

        // 에라토스테네스의 체
        boolean[] notPrimeNumbers = new boolean[1_000_001];
        for (int i = 2; i < notPrimeNumbers.length; i++) {
            if (notPrimeNumbers[i])
                continue;
            for (int j = 2; i * j < notPrimeNumbers.length; j++)
                notPrimeNumbers[i * j] = true;
        }
        // 소수
        List<Integer> primeNumbers = new ArrayList<>();
        for (int i = 2; i < notPrimeNumbers.length; i++) {
            if (!notPrimeNumbers[i])
                primeNumbers.add(i);
        }

        int[][] counts = new int[n + 1][primeNumbers.size()];
        for (int i = 0; i < nums.length; i++) {
            // 각각을 소인수 분해 한다.
            for (int j = 0; j < primeNumbers.size(); j++) {
                while (nums[i] > 1 && nums[i] % primeNumbers.get(j) == 0) {
                    // i번째 수가 갖고 있는 j번째 소수의 개수를 센다.
                    counts[i][j]++;
                    // 전체 수의 j번째 소수의 개수 또한 센다.
                    counts[n][j]++;
                    nums[i] /= primeNumbers.get(j);
                }
                // nums[i]가 1이 되었다면, 다음 수로 넘어간다.
                if (nums[i] == 1)
                    break;
            }
        }
        
        // 전체 행동의 수
        int count = 0;
        // 최대공약수
        int answer = 1;
        for (int i = 0; i < counts[n].length; i++) {
            // 전체 수가 i번째 소수를 하나도 갖고 있지 않다면
            // 건너 뛴다.
            if (counts[n][i] == 0)
                continue;

            // i번째 소수가 counts[n][i]개 있는데
            // 이를 n개의 수로 등분했을 때
            // 각각이 갖고 있는 i번째 소수의 개수는 counts[n][i] / n개 혹은 (counts[n][i] / n)+1개 이다.
            // 이는 구하려는 최대공약수의 인수이다.
            answer *= (int) Math.pow(primeNumbers.get(i), counts[n][i] / n);
            // j번째 수가 i번째 소수를 인수로 counts[n][i] / n개보다 적게 갖고 있을 시
            // 넘치는 다른 수에게서 부족한 개수만큼을 가져와야한다.
            // 그 행동의 횟수 계산.
            for (int j = 0; j < n; j++) {
                if (counts[j][i] >= counts[n][i] / n)
                    continue;
                else
                    count += counts[n][i] / n - counts[j][i];
            }
        }
        // 전체 답 출력
        System.out.println(answer + " " + count);
    }
}