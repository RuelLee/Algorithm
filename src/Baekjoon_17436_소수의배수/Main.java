/*
 Author : Ruel
 Problem : Baekjoon 17436번 소수의 배수
 Problem address : https://www.acmicpc.net/problem/17436
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17436_소수의배수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 소수와 자연수 m이 주어진다.
        // m 이하의 자연수 중 n개의 소수 중 적어도 하나로 나누어 떨어지는 수의 개수는?
        //
        // 포함 배제의 원리, 조합
        // m 이하에서 소수 a로 나누어 떨어지는 수의 개수는 m / a개다.
        // 이렇게 구해서 모두 더하면...? 당연히 답이 아니다.
        // 중복되서 세어지는 수가 있기 때문.
        // 가령 소수로 2, 5, m으로 10이 주어진다면
        // 2, 4, 6, 8, 10, 5, 10으로 7개가 되지만 당연히 10이 중복되어 그 수를 빼야한다.
        // n이 3이 되어 a, b, c가 주어진다면
        // a의 배수 + b의 배수 + c의 배수를 하면
        // a * b, b * c, c * a의 배수가 각각 두번씩 세어진다.
        // 그렇다고 각각을 모두 한번씩 빼면, a * b * c의 배수가 세 번 빠져 한번도 없게 되므로
        // 다시 a * b * c의 배수의 개수를 더해준다.
        // 
        // n개의 소수가 주어진다면
        // 1개의 소수로 나누어 떨어지는 수의 개수는 더하고
        // 2개의 소수의 곱으로 나누어 떨어지는 수의 개수는 빼고, ..
        // n개의 소수로 곱으로 나누어 떨어지는 수는 n이 짝수일 때는 빼고, 홀수일 때는 더한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // n개의 소수
        int n = Integer.parseInt(st.nextToken());
        // m이하의 자연수
        long m = Long.parseLong(st.nextToken());
        
        // 소수들
        int[] primeNumbers = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        long count = 0;
        // i개의 소수를 뽑아 해당 소수의 곱이 m이하의 자연수에 몇개 있는지 세고
        // i가 홀수라면 더하고, 짝수라면 빼준다.
        for (int i = 1; i <= n; i++)
            count += selectSAndCountMultiple(0, i, 0, primeNumbers, 1, m) * (i % 2 == 0 ? -1 : 1);
        System.out.println(count);
    }

    // s개의 소수를 뽑아, m이하에서 몇 개가 있는지 센다.
    static long selectSAndCountMultiple(int idx, int s, int selected, int[] primeNumbers, long multiple, long m) {
        // 모두 뽑았다면 해당 multiple의 배수가 m이하에 몇개 있는지 반환한다.
        if (s == selected)
            return m / multiple;
        // 마지막 소수까지 봤지만 s개의 소수를 뽑지 못하는 경우는 0개.
        else if (idx >= primeNumbers.length)
            return 0;

        // idx번째 소수를 뽑았을 때와, 그렇지 않았을 때
        // 두 경우의 합을 반환한다.
        return selectSAndCountMultiple(idx + 1, s, selected + 1, primeNumbers, multiple * primeNumbers[idx], m) +
                selectSAndCountMultiple(idx + 1, s, selected, primeNumbers, multiple, m);
    }
}