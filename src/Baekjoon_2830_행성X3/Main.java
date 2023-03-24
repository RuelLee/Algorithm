/*
 Author : Ruel
 Problem : Baekjoon 2830번 행성 X3
 Problem address : https://www.acmicpc.net/problem/2830
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2830_행성X3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 외계 행성의 주민들은 자연수다.
        // 두 외계인의 친밀도는 서로을 이진수로 바꾸어, 각 자리의 수가 같다면
        // 0, 다르다면 1을 적어, 결과값 이진수를 다시 10진수로 바꾸면 그들의 친밀도가 된다.
        // 행성의 가치는 모든 친밀도의 합이라고 할 때, 행성의 가치를 구하라
        //
        // 비트마스킹 문제
        // 문제만 읽어봐서는 xor 연산만 사용하면 쉽겠는데? 라고 생각되지만
        // 주민 수인 n이 최대 100만까지 주어지므로 일일이 계산해서는 n^2 으로 시간초과
        // 문제 해결의 키는, 서로 다른 수일 때 각 자리에 1이 남게 되므로
        // 각 자리에 0과 1의 개수를 계산하고 서로 곱해준다면 각 자리에 나올 수 있는 1의 개수가 된다
        // 이를 자릿수인 2의 x 제곱과 곱해 모두 더해주면 된다

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 최대 100만까지 주어지므로 해당하는 자릿수만큼의 배열을 준비한다.
        int[] counts = new int[(int) Math.ceil(Math.log(1_000_000) / Math.log(2)) + 1];
        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(br.readLine());
            // 각 자리에 1이 있는지 확인하고 개수를 계산한다.
            for (int j = 0; j < counts.length; j++) {
                if (num == 0)
                    break;
                else if (num % 2 == 1)
                    counts[j]++;

                num >>= 1;
            }
        }
        
        // 친밀도의 총합
        long sum = 0;
        // 각 자릿수 별로 0과 1의 개수를 찾고,
        // 자릿수와 곱해, 해당 자릿수의 친밀도 합을 구한다.
        for (int j = 0; j < counts.length; j++) {
            long zeroCount = n - counts[j];
            long oneCount = counts[j];
            
            sum += zeroCount * oneCount * (long) Math.pow(2, j);
        }
        
        // 전체 친밀도의 합 출력
        System.out.println(sum);
    }
}