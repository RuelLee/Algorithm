/*
 Author : Ruel
 Problem : Baekjoon 17433번 신비로운 수
 Problem address : https://www.acmicpc.net/problem/17433
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17433_신비로운수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 0이 아닌 정수 n개가 주어졌을 때
        // 해당 정수들을 m으로 나눈 나머지가 모두 같은 가장 큰 m을 구하고자 한다.
        //
        // 유클리드 호제법
        // 정수들을 각각 xi라 했을 때
        // xi = m * ai + b
        // xj = m * aj + b
        // xj - xi = m * (aj - ai)
        // 각각 정수의 차는 m의 배수가 된다.
        // 따라서 각각의 정수들의 차를 구해 유클리드 호제법을 통해 최대공약수를 구해주면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            // 정수의 개수
            int n = Integer.parseInt(br.readLine());
            // 정수
            int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            // 정렬
            Arrays.sort(nums);
            // 최소 공배수.
            // 초기값으로 0번째와 1번째 수의 차로 함.
            int gcd = nums[1] - nums[0];
            // 이후 인접한 수의 차와 최대공약수를 지속적으로 구한다.
            for (int i = 2; i < nums.length; i++)
                gcd = getGCD(gcd, nums[i] - nums[i - 1]);
            
            // gcd가 0이라면 모든 수에 대해 나머지가 같은 경우
            // INFINITY 출력
            // 그 외의 경우 gcd 출력
            sb.append(gcd == 0 ? "INFINITY" : gcd).append("\n");
        }
        System.out.print(sb);
    }

    // 유클리드 호제법으로 최대공약수 구하기
    static int getGCD(int a, int b) {
        while (a > 0) {
            int temp = b % a;
            b = a;
            a = temp;
        }
        return b;
    }
}