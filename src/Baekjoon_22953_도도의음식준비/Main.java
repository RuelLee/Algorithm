/*
 Author : Ruel
 Problem : Baekjoon 22953번 도도의 음식 준비
 Problem address : https://www.acmicpc.net/problem/22953
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_22953_도도의음식준비;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 요리사, 만들어야할 음식의 개수 k, 격려 횟수 c가 주어진다.
        // 각각의 요리사에 대해 한 음식을 만드는 시간이 주어지며
        // 한 번 격려를 받을 때마다, 조리 시간이 1 줄어들며, 중복 격려가 가능하다.
        // k개의 음식이 완성되는 최소 시간은?
        //
        // 브루트 포스, 이분 탐색 문제
        // n이 최대 10, c가 최대 5로 주어지므로
        // 격려하는 경우에 대해서는 브루트 포스로 모든 경우를 생각하며
        // 최종적으로 격려가 끝난 후, 주어진 요리사들의 조리시간을 토대로
        // 이분탐색을 통해 k개의 요리가 완성되는 최소 시간을 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 요리사, k개의 요리, 격려 횟수 c
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        
        // 요리사들의 조리 시간
        int[] chefs = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 최소 조리시간 출력
        System.out.println(findMinTime(chefs, k, c));
    }
    
    // 브루트 포스 + 이분 탐색
    static long findMinTime(int[] chefs, int k, int c) {
        // 잔여 격려 횟수가 없거나, 요리사들의 최대 조리시간이 1이여서
        // 더 이상 격려를 할 수 없는 경우.
        if (c == 0 ||
                Arrays.stream(chefs).max().getAsInt() == 1) {
            // 이분 탐색
            long start = 0;
            long end = 1_000_000L * 1_000_000;
            while (start < end) {
                long mid = (start + end) / 2;
                // mid 시간 동안 k개 이상의 요리를 만드는지 확인.
                long sum = 0;
                for (int i = 0; i < chefs.length; i++)
                    sum += mid / chefs[i];
                if (sum >= k)       // k개 이상의 요리만든다면 end를 mid 범위로 좁히고
                    end = mid;
                else        // 만들 수 없었다면 start를 mid +1 ~ 로 좁힌다.
                    start = mid + 1;
            }
            // 현재 요리사들의 상태로 k개의 요리를 완성하는 최소 시간 반환
            return start;
        }

        // 격려를 아직 할 수 있는 경우.
        long minTime = Long.MAX_VALUE;
        for (int i = 0; i < chefs.length; i++) {
            // i번 요리사에 대해 격려를 할 수 있다면
            if (chefs[i] > 1) {
                // 격려를 해 요리 시간을 줄이고
                chefs[i]--;
                // 해당 상태로 만들어지는 경우들 중 최소 조리 시간을 가져온다.
                minTime = Math.min(minTime, findMinTime(chefs, k, c - 1));
                // 격려 취소
                chefs[i]++;
            }
        }
        // 찾은 최소 시간 반환
        return minTime;
    }
}