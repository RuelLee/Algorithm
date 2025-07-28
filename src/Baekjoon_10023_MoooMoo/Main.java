/*
 Author : Ruel
 Problem : Baekjoon 10023번 Mooo Moo
 Problem address : https://www.acmicpc.net/problem/10023
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10023_MoooMoo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 들판이 일렬로 늘어서있다.
        // 소는 총 b개의 다른 품종이 있으며, 각각은 vi의 크기로 운다.
        // 강한 바람이 불고 있어, 소리는 왼쪽에서 오른쪽으로만 전파되며
        // x의 소리는 옆 들판으로 전파될 때, x-1이 된다.
        // 각 들판에서의 소리가 주어질 때, 총 소들의 마릿수 중 최솟값은?
        //
        // 배낭 문제
        // 각각의 소리에 따라 있을 수 있는 소의 최소 마릿수를 배낭 문제와 같이 구한다.
        // 그리고 소리의 전파됨에 따라, 소리가 작아지는 경우에는 무시, 커지는 경우에는
        // 커진만큼에 해당하는 현재 들판에 있을 수 있는 소의 최솟값을 구해 누적시켜나간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 들판, 소 품종의 수 b
        int n = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        
        // 각 소들이 우는 소리의 크기
        int[] vs = new int[b];
        for (int i = 0; i < b; i++)
            vs[i] = Integer.parseInt(br.readLine());
        Arrays.sort(vs);
        
        // 각 들판에서의 소리 크기
        int[] xs = new int[n];
        for (int i = 0; i < n; i++)
            xs[i] = Integer.parseInt(br.readLine());

        // 이전 들판과 이번 들판과의 소리 크기 차이 중 가장 큰 값을 구한다.
        int max = xs[0];
        for (int i = 1; i < xs.length; i++) {
            if (xs[i] > xs[i - 1])
                max = Math.max(max, xs[i] - Math.max(xs[i - 1] - 1, 0));
        }

        // 해당 크기로 dp를 선언하여,
        // 각 소리의 크기마다 할당될 수 있는 최소 수의 소를 구한다.
        int[] dp = new int[max + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int v : vs) {
            for (int i = 0; i + v < dp.length; i++) {
                if (dp[i] == Integer.MAX_VALUE)
                    continue;

                dp[i + v] = Math.min(dp[i + v], dp[i] + 1);
            }
        }

        // 각 들판에서의 소리 크기 차이를 구해, 추가적으로 고려되는 소들의 수를 누적한다.
        // 해당하는 소리의 크기만큼 소를 배치하는 것이 불가능한 경우에는 -1을 기록
        int sum = dp[xs[0]] == Integer.MAX_VALUE ? -1 : dp[xs[0]];
        for (int i = 1; i < xs.length && sum != -1; i++) {
            if (xs[i] > xs[i - 1]) {
                int diff = xs[i] - Math.max(xs[i - 1] - 1, 0);
                if (dp[diff] == Integer.MAX_VALUE) {
                    sum = -1;
                    break;
                }
                sum += dp[diff];
            }
        }
        // 답 출력
        System.out.println(sum);
    }
}