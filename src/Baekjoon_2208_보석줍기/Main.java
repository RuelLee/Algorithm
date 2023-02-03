/*
 Author : Ruel
 Problem : Baekjoon 2208번 보석 줍기
 Problem address : https://www.acmicpc.net/problem/2208
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2208_보석줍기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 보석들이 일렬로 있으며
        // 최소 m개 이상의 보석을 연속해서 주워야하고, 줍는 것이 끝나면 바로 나와야한다.
        // 보석의 가치는 음수도 존재할 때, 총 주울 수 있는 보석의 가치를 최대로 하고자할 때
        // 그 값은?
        //
        // 누적합 문제
        // 누적합을 통해 연속해서 보석들을 주웠을 때, 그 가치를 최대하는 구간을 찾자.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 보석의 개수와 연속해서 주워야하는 보석의 최소 수
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 누적합
        int[] jewelsPSum = new int[n + 1];
        for (int i = 1; i < jewelsPSum.length; i++)
            jewelsPSum[i] = jewelsPSum[i - 1] + Integer.parseInt(br.readLine());
        
        // 누적합이 최소인 곳
        int minIdx = 0;
        // 최대 보석 가치들의 합
        // 가치가 음수인 보석도 존재하나, 보석을 줍지않고 나갈 수도 있다.
        // 따라서 초기값은 0
        int maxSum = 0;
        // m번까지의 누적합부터 시작.
        for (int i = m; i < jewelsPSum.length; i++) {
            // 연속해서 주워야하는 보석의 개수가 m개이므로
            // i까지의 연속합과, 0 ~ i - m까지의 연속합들의 차이 중
            // 값이 가장 큰 값을 찾는다.

            // 0 ~ i - m까지의 연속합 중 가장 작은 값을 찾는다.
            if (jewelsPSum[minIdx] > jewelsPSum[i - m])
                minIdx = i - m;
            // minIdx + 1 ~ i 까지의 보석들을 주울 때,
            // 가치 합이 최대값을 갱신하는지 확인한다.
            maxSum = Math.max(maxSum, jewelsPSum[i] - jewelsPSum[minIdx]);
        }

        // n개의 보석들 중 m개의 연속한 보석들을 주웠을 때
        // 최대 보석 가치 합을 출력한다.
        System.out.println(maxSum);
    }
}