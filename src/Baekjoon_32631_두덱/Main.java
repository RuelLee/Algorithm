/*
 Author : Ruel
 Problem : Baekjoon 32631번 두 덱
 Problem address : https://www.acmicpc.net/problem/32631
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_32631_두덱;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 두 개 배낭에 n개의 물건이 차곡차곡 쌓여있다.
        // 첫 배낭은 A1, ..., An, 두번째 배낭은 B1, ..., Bn
        // 원빈이는 k번 두 배낭 중 하나를 선택해 가장 아래 혹은 가장 위에 있는 물건 중 하나를 꺼낸다.
        // 승형이는 원빈이의 행동이 끝난 후, 두 배낭 중 가벼운 배낭을 자신이 멘다.
        // 원빈이는 남은 배낭을 멘다.
        // 원빈이가 메게될 배낭의 최소 무게 합은?
        //
        // 브루트 포스, 누적합 문제
        // 물건을 꺼낼 때, 가장 바깥쪽 물건만 꺼낼 수 있으므로, 양 방향에서의 누적합을 구한뒤
        // 첫번째 배낭에서 i개 물건을 꺼내고, 두번째 배낭에서 k-i개 물건을 꺼낸 후의 무게를 구하고
        // 더 무거운 배낭의 무게 최솟값을 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 물건, k번의 행동
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 가방
        int[][] bags = new int[2][n];
        for (int i = 0; i < bags.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < bags[i].length; j++)
                bags[i][j] = Integer.parseInt(st.nextToken());
        }
        
        // 왼쪽에서의 누적합
        long[][] psumsFromLeft = new long[2][n + 2];
        for (int i = 0; i < bags.length; i++) {
            for (int j = 0; j < bags[i].length; j++)
                psumsFromLeft[i][j + 1] = psumsFromLeft[i][j] + bags[i][j];
        }
        
        // 오른쪽에서 누적합
        long[][] psumsFromRight = new long[2][n + 2];
        for (int i = 0; i < bags.length; i++) {
            for (int j = n - 1; j >= 0; j--)
                psumsFromRight[i][j + 1] = psumsFromRight[i][j + 2] + bags[i][j];
        }
        
        // 답
        long answer = Long.MAX_VALUE;
        for (int i = 0; i <= k; i++) {
            // 첫번째 배낭에서 i개 물건을 꺼내고, 두번째 배낭에서 (k - i)개 물건을 꺼낼 때
            // 첫번째 배낭에서 꺼낼 수 있는 최대 무게
            long max1 = 0;
            for (int j = 0; j <= i; j++)
                max1 = Math.max(max1, psumsFromLeft[0][j] + psumsFromRight[0][n + 1 - i + j]);
            
            // 두번째 배낭에서 꺼낼 수 있는 최대 무게
            long max2 = 0;
            for (int j = 0; j <= k - i; j++)
                max2 = Math.max(max2, psumsFromLeft[1][j] + psumsFromRight[1][n + 1 - (k - i) + j]);

            // 두 배낭 중 무거운 배낭을 원빈이가 메게될 배낭 무게의 최솟값을 구한다.
            answer = Math.min(answer, Math.max(psumsFromLeft[0][n] - max1, psumsFromLeft[1][n] - max2));
        }
        // 답 출력
        System.out.println(answer);
    }
}