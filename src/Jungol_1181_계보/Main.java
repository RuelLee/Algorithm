/*
 Author : Ruel
 Problem : Jungol 1181번 계보
 Problem address : https://jungol.co.kr/problem/1181
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1181_계보;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 외계인의 가족 족보가 주어진다.
        // 각 자신의 자식이 주어진다.
        // 외계인은 최대 d명의 조상을 가질 수 있다고 한다.
        // 족보를 알맞게 수정하기 위해 가상이 조상을 추가한다고 한다.
        // 올바른 족보가 되기 위해 추가해야하는 조상의 수는?
        //
        // DP 문제
        // dp를 통해 자신의 조상 노드의 수에 따라 추가해야하는 최소 가상 조상의 수를 구한다
        // dp[i] = i명의 조상이 주어질 때, 추가해야하는 가상 조상의 수를 구한다
        // 한 명의 가상 조상을 추가한다면, 그 아래에 d명의 조상이 묶이게 된다.
        // 따라서 dp[i] = 1(추가한 가상의 조상) + dp[i - (d - 1)]로 세울 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 0 ~ n번까지의 외계인이 주어진다.
        int n = Integer.parseInt(st.nextToken());
        // 최대 d명의 조상을 가질 수 있다.
        int d = Integer.parseInt(st.nextToken());

        // 각 조상의 부모 수
        int[] child = new int[n + 1];
        // 최대값을 구하고
        int max = 0;
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            max = Math.max(max, ++child[Integer.parseInt(st.nextToken())]);

        // dp를 채운다.
        int[] dp = new int[max + 1];
        for (int i = d + 1; i < dp.length; i++)
            dp[i] = 1 + dp[i - (d - 1)];

        // 각 외계인마다 조상의 수를 보고
        // 필요한 가상 조상의 수를 더한다.
        int sum = 0;
        for (int i = 0; i < child.length; i++)
            sum += dp[child[i]];
        // 답 출력
        System.out.println(sum);
    }
}