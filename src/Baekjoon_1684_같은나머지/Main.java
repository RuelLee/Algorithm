/*
 Author : Ruel
 Problem : Baekjoon 1684번 같은 나머지
 Problem address : https://www.acmicpc.net/problem/1684
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1684_같은나머지;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 정수 N을 d로 나눴을 때, 몫을 q, 나머지를 r이라 할 때 r = N - q * d가 성립한다.
        // n개의 정수로 된 수열이 있을 때, 나눴을 때 같은 나머지를 갖게되는 d가 있다.
        // 수열이 주어질 때, d를 구하라.
        //
        // 유클리드 호제법
        // 같은 수로 나누었을 때 나머지가 같다 -> 차가 몫의 배수이다.
        // 따라서 각 수들의 차를 가지고 유클리드 호제법을 통해 최대공약수를 구하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 정수
        int n = Integer.parseInt(br.readLine());
        // 수열
        int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 정렬을 하여, 수들 간의 차이를 최소가 되게끔한다.
        Arrays.sort(nums);

        // 이웃한 수들의 차를 가지고서 최대 공약수를 구한다.
        int answer = nums[1] - nums[0];
        for (int i = 2; i < nums.length; i++)
            answer = getGCD(answer, nums[i] - nums[i - 1]);
        // 답 출력.
        System.out.println(answer);
    }
    
    // 유클리드 호제법을 통해 최대공약수를 구한다.
    static int getGCD(int a, int b) {
        // a와 b중 큰 수와 작은 수
        int max = Math.max(a, b);
        int min = Math.min(a, b);
        // min이 0보다 큰 동안
        while (min > 0) {
            // temp에 max를 min으로 나눴을 때의 나머지
            int temp = max % min;
            // min이 최대값
            max = min;
            // temp가 최소값이 된다.
            min = temp;
        }
        // min이 0이 된 시점의 max값이 최대공약수
        // max 반환
        return max;
    }
}