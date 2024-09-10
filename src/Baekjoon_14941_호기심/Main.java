/*
 Author : Ruel
 Problem : Baekjoon 14941번 호기심
 Problem address : https://www.acmicpc.net/problem/14941
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14941_호기심;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // a와 b 사이의 소수들의 합과 차를 이용한 특수 함수 F를 만들었다.
        // 3 * a1 - a2 + 3*a3 + .... An
        // F(3, 7)이라면 3 5 7 3개의 소수가 있고
        // 3 * 3 - 5 + 3 * 7 = 25이다.
        // n개의 쿼리가 주어질 때 이에 답하라
        // a b : F(a, b) 1<= a <= b <= 10^5
        //
        // 에라토스테네스의 체, 이분 탐색, 누적합 문제
        // 에라토스테네스의 체로 10^5 범위까지 소수 판정을 한다.
        // 그 후, 소수들을 홀수번째와 짝수번째에 따라 나눠 누적합을 구한다.
        // 그 후 쿼리들을 누적합으로 처리한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 에라토스테네스의 체, 소수 판정
        boolean[] notPrimeNumber = new boolean[100001];
        notPrimeNumber[1] = true;
        // 소수들
        List<Integer> primeNumbers = new ArrayList<>();
        // 각 소수의 홀짝을 구분하기 위해 순서를 해쉬맵으로 저장
        HashMap<Integer, Integer> order = new HashMap<>();
        for (int i = 2; i < notPrimeNumber.length; i++) {
            if (notPrimeNumber[i])
                continue;

            // 소수 추가
            primeNumbers.add(i);
            // 순서 추가
            order.put(i, order.size() + 2);
            for (int j = 2; i * j < notPrimeNumber.length; j++)
                notPrimeNumber[i * j] = true;
        }

        // 소수들을 홀짝수에 나눠 누적합 처리
        int[] psums = new int[primeNumbers.size() + 2];
        for (int i = 2; i < primeNumbers.size(); i++)
            psums[i] = psums[i - 2] + primeNumbers.get(i - 2);

        // n개의 쿼리
        int n = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // F(a, b)를 구한다.
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            // a보다 같거나 큰 최소 소수를 구한다.
            int start = 0;
            int end = primeNumbers.size() - 1;
            while (start < end) {
                int mid = (start + end) / 2;
                if (primeNumbers.get(mid) < a)
                    start = mid + 1;
                else
                    end = mid;
            }
            int minPrimeNum = primeNumbers.get(start);

            // b보다 같거나 작은 최대 소수를 구한다
            start = 0;
            end = primeNumbers.size() - 1;
            while (start <= end) {
                int mid = (start + end) / 2;
                if (primeNumbers.get(mid) <= b)
                    start = mid + 1;
                else
                    end = mid - 1;
            }
            int maxPrimeNum = primeNumbers.get(end);


            int answer = 0;
            // 만약 minPrimeNum과 maxPrimeNum의 홀짝수가 같다면
            // 두 소수 모두 *3을 해 더해주는 쪽에 속하게 된다.
            // 그 때의 누적합을 구한다.
            if (order.get(maxPrimeNum) % 2 == order.get(minPrimeNum) % 2) {
                answer += (psums[order.get(maxPrimeNum)] - psums[order.get(minPrimeNum) - 2]) * 3;
                answer -= (psums[order.get(maxPrimeNum) - 1] - psums[order.get(minPrimeNum) - 1]);
            } else {
                // 서로 다르다면 minPrimeNum이 속한 쪽은 *3
                // maxPrimeNum이 속한 쪽은 *(-1)이 된다.
                answer += (psums[order.get(maxPrimeNum) - 1] - psums[order.get(minPrimeNum) - 2]) * 3;
                answer -= (psums[order.get(maxPrimeNum)] - psums[order.get(minPrimeNum) - 1]);
            }
            // 답 기록
            sb.append(answer).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}