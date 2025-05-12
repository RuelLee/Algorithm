/*
 Author : Ruel
 Problem : Baekjoon 26073번 외로운 곰곰이는 친구가 있어요
 Problem address : https://www.acmicpc.net/problem/26073
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_26073_외로운곰곰이는친구가있어요;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 친구들의 2차원 좌표가 주어진다.
        // i번째 친구는 ki개의 이동 가능한 거리가 있고, x나 y에 평행한 방향으로 이동을 할 수 있다.
        // 해당 친구가 (0, 0)에 도달할 수 없다면 Gave up, 올 수 있으면 Ta-da 를 출력한다
        //
        // 유클리드 호제법, 베주 항등식
        // 베주 항등식...? 을 사용하는 문제
        // a와 b의 최대공약수를 d라고 둔다면
        // ax + by = d를 만족하는 x와 y가 반드시 존재한다.
        // 이동할 수 있는 거리들의 최소공배수를 구해 최소공배수가 친구의 x, y좌표의 약수라면
        // 해당 친구는 (0, 0)으로 이동할 수 있다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n명의 친구
        int n = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            // 각 친구의 위치
            int[] loc = new int[2];
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < loc.length; j++)
                loc[j] = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            // 해당 친구가 이동가능한 거리들
            int[] distances = new int[Integer.parseInt(st.nextToken())];
            for (int j = 0; j < distances.length; j++)
                distances[j] = Integer.parseInt(st.nextToken());
            
            // 최대공약수
            int gcd = distances[0];
            for (int j = 1; j < distances.length; j++)
                gcd = getGCD(gcd, distances[j]);
            
            // 각 좌표들이 gcd로 나눠떨어진다면 (0, 0)에 도달 가능
            sb.append(loc[0] % gcd == 0 && loc[1] % gcd == 0 ? "Ta-da" : "Gave up").append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }

    // 유클리드 호제법
    // a, b의 최대공약수를 구한다.
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