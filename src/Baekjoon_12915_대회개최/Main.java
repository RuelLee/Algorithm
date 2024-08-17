/*
 Author : Ruel
 Problem : Baekjoon 12915번 대회 개최
 Problem address : https://www.acmicpc.net/problem/12915
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12915_대회개최;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 5가지 종류의 문제가 주어진다.
        // E : 쉬운 문제
        // EM : 쉬운 문제나 중간 문제로 사용 가능
        // M : 중간 문제
        // MH : 중간이나 어려운 문제
        // H : 어려운 문제
        // 한 대회마다 쉬운 문제, 중간 문제, 어려운 문제가 하나씩 포함되어야한다.
        // 각 문제들의 개수가 주어질 떄, 최대로 개최할 수 있는 대회의 수는?
        //
        // 이분 탐색 문제
        // 대회의 개최 수를 정해두고, 해당 문제들로 해당 횟수만큼 대회를 개최할 수 있는지 판별한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 문제들
        int[] problems = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 이분 탐색
        int start = 0;
        int end = 200_000;
        while (start <= end) {
            int mid = (start + end) / 2;
            // mid 횟수만큼 대회 개최가 가능하다면
            // start 범위를 mid + 1로 옮긴다.
            if (possibleN(mid, problems))
                start = mid + 1;
            else        // 불가능하다면 end를 mid -1 로 옮긴다.
                end = mid - 1;
        }
        // end 회만큼 대회를 개최하는 것이 가능하다.
        System.out.println(end);
    }
    
    // problems 들로 n회 대회를 개최할 수 있는지 판별한다.
    static boolean possibleN(int n, int[] problems) {
        // E와 EM의 문제 개수가 n 미만이라면 개최 불가
        if (problems[0] + problems[1] < n)
            return false;
        else if (problems[3] + problems[4] < n)     // MH와 H의 문제 개수가 n 미만 이라면 개최 불가.
            return false;
        // EM의 여유분과 MH의 여유분 + M의 문제 개수가 n 미만이라면 개최 불가
        else if (problems[1] - Math.max(n - problems[0], 0) +
                problems[3] - Math.max(n - problems[4], 0) + problems[2] < n)
            return false;
        else        // 그 외의 경우는 개최 가능.
            return true;
    }
}