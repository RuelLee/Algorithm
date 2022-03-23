/*
 Author : Ruel
 Problem : Baekjoon 11054번 가장 긴 바이토닉 부분 수열
 Problem address : https://www.acmicpc.net/problem/11054
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11054_가장긴바이토닉부분수열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 수열이 S1 < S2 < ... < Sk > Sk+1 > ... > Sn-1 > Sn 의 형태를 띌 때 그 수열을 바이토닉 수열이라고 한다
        // 수열이 주어졌을 때, 바이토닉 수열이며 가장 긴 부분 수열의 길이를 구하라
        //
        // 최장 증가 수열을 앞 뒤로 두 번 시행하는 문제
        // 수열의 크기와 수 자체의 크기도 별로 크지 않다
        // 이분 탐색을 사용할 필요없이 직접 계산해도 크게 무리가 가지 않는다
        // 수열의 각 수마다, 앞에서부터와 뒤에서부터 증가 수열의 길이를 구했을 때 최대값을 기록해둔다
        // 그리고 그 값을 서로 합치면 가장 긴 바이토닉 수열의 길이가 될 것이다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        int[] increase = new int[n];        // 앞에서부터 증가하는 형태일 때 해당 수가지의 최대 수열의 길이를 기록해둔다.
        int[] order = new int[n];       // 같은 순서에 위치하는 수들 중 가장 작은 수를 기록해둔다.
        Arrays.fill(order, 1001);
        for (int i = 0; i < nums.length; i++) {
            int maxOrder = 0;
            while (order[maxOrder] < nums[i])       // nums[i]가 위치할 수 있는 최대 순서를 구한다.
                maxOrder++;

            // i번째 수에 최대 순서를 기록해두고,
            increase[i] = maxOrder;
            // 같은 순서에 위치하는 수들 중 가장 작은 수를 order에 기록해둔다.
            order[maxOrder] = Math.min(order[maxOrder], nums[i]);
        }
        // 해당 수부터 최대 감소 수열의 길이를 구한다.
        // -> 반대로 거꾸로 끝에서부터 증가 수열의 길이를 구한다.
        int[] decrease = new int[n];
        order = new int[n];
        Arrays.fill(order, 1001);
        for (int i = nums.length - 1; i >= 0; i--) {        // 순서만 뒤에서부터 시작한다.
            int maxOrder = 0;
            while (order[maxOrder] < nums[i])
                maxOrder++;

            decrease[i] = maxOrder;
            order[maxOrder] = Math.min(order[maxOrder], nums[i]);
        }

        // increase와 decrease는 각 수가 앞에서부터 증가하는 형태일 때의 최대 순서와
        // 현재부터 마지막 수까지 감소하는 형태일 때의 최대 길이가 기록되어있다.
        // 따라서 두 수의 합이 바이토닉 부분 수열의 최대 길이가 된다.
        int max = 0;
        for (int i = 0; i < n; i++)     // 바이토닉 부분 수열의 길이들 중 가장 큰 값을 찾는다.
            max = Math.max(max, increase[i] + decrease[i] + 1);
        System.out.println(max);
    }
}