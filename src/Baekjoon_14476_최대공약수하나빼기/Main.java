/*
 Author : Ruel
 Problem : Baekjoon 14476번 최대공약수 하나 빼기
 Problem address : https://www.acmicpc.net/problem/14476
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14476_최대공약수하나빼기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 정수가 주어진다.
        // 해당 정수들 중 하나를 뺐을 때, 나머지 n - 1개의 정수들의 최대공약수가 가장 커지는 수를 찾으시오
        // 그 때의 최대공약수와 뺀 수를 출력하라
        //
        // 유클리드 호제법
        // 빼는 수를 기준으로 오른쪽 부분과 왼쪽 부분으로 나눌 수 있다.
        // 따라서 왼쪽에서부터 i번까지의 최대공약수를 구하고
        // 오른쪽에서부터 i ~ n-1번까지의 최대공약수를 구한다.
        // 그 후, i번 수를 뺏을 때의 최대공약수는
        // 0 ~ i번까지의 최대공약수와 i+1 ~ n-1까지의 최대공약수와 같다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 정수
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] nums = new int[n];
        for (int i = 0; i < nums.length; i++)
            nums[i] = Integer.parseInt(st.nextToken());

        // 왼쪽에서부터 최대공약수를 구해나간다.
        int[] fromLeft = new int[n];
        fromLeft[0] = nums[0];
        // 오른쪽에서부터 최대공약수를 구한다.
        int[] fromRight = new int[n];
        fromRight[n - 1] = nums[n - 1];
        for (int i = 1; i < n; i++) {
            fromLeft[i] = getGCD(fromLeft[i - 1], nums[i]);
            fromRight[n - 1 - i] = getGCD(fromRight[n - i], nums[n - 1 - i]);
        }

        int[] answer = new int[2];
        boolean found = false;
        for (int i = 0; i < nums.length; i++) {
            // i번 수를 뺐을 때의 최대공약수
            // fromLeft[i-1]과 fromRight[i+1]의 최대공약수를 구한다.
            // 범위를 i-1이나 i+1이 범위를 벗어나는 경우를 고려해준다.
            int gcd = getGCD(i - 1 < 0 ? fromRight[i + 1] : fromLeft[i - 1],
                    i + 1 >= nums.length ? fromLeft[i - 1] : fromRight[i + 1]);
            // 최댓값을 갱신하는 경우
            if (gcd > answer[0] && nums[i] % gcd != 0) {
                // 답을 찾았다고 표시
                found = true;
                // 답 갱신
                answer[0] = gcd;
                answer[1] = nums[i];
            }
        }
        // 답을 찾은 경우는 답을 출력
        // 그렇지 못한 경우는 -1을 출력
        System.out.println(found ? answer[0] + " " + answer[1] : -1);
    }

    // 유클리드 호제법
    // a와 b의 최대공약수를 찾는다.
    static int getGCD(int a, int b) {
        int max = Math.max(a, b);
        int min = Math.min(a, b);

        while (min > 0) {
            int temp = max % min;
            max = min;
            min = temp;
        }
        return max;
    }
}