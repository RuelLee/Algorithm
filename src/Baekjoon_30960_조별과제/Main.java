/*
 Author : Ruel
 Problem : Baekjoon 30960번 조별 과제
 Problem address : https://www.acmicpc.net/problem/30960
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30960_조별과제;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 홀수인 n이 주어진다.
        // 2명씩 조를 이루되, 하나의 조는 3명이 조를 이룬다.
        // 각 조의 어색함은 조에 속한 학번의 최댓값 - 최솟값으로 이루어진다.
        // 어색함의 합을 최소로 하고자할 때 그 합은?
        //
        // 정렬, dp 문제
        // 모든 학생이 하나의 조에 편성이 되어야하므로
        // 학번을 오름차순 정렬한 뒤, 현재까지 조원 구성이 완료된 기준으로
        // 어색함 합을 최소로 하는 값을 찾아나간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n명의 학생
        int n = Integer.parseInt(br.readLine());
        // 학번
        int[] studentIDs = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 오름차순 정렬
        Arrays.sort(studentIDs);

        // dp
        int[] dp = new int[n + 1];
        // 초기값 세팅
        // 1번과 2번 학생이 조를 이룬 경우.
        dp[2] = studentIDs[1] - studentIDs[0];
        // 1, 2, 3번 학생이 조를 이룬 경우.
        dp[3] = studentIDs[2] - studentIDs[0];

        // i번 학생이 조를 이룰 때, 어색함 합이 최소가 되는 값을 찾는다.
        for (int i = 4; i < dp.length; i++) {
            // 만약 i가 짝수라면, 3명이 조를 이루는 경우가 생길 수 없다.
            // 따라서 i-2번 학생까지 조를 이루는 어색함 합에
            // i-1, i번 학생이 조를 이룬 경우 어색함을 더해준다.
            if (i % 2 == 0)
                dp[i] = dp[i - 2] + studentIDs[i - 1] - studentIDs[i - 2];
            // i가 홀수라면, 3명이 이루는 경우가 생길 수 있다.
            // i번째 학생이 3명조에 들어가는 경우와 그렇지 않는 경우를 생각해볼 수 있다.
            // i번째 학생이 3명조에 들어간다면
            // i-3번까지의 어색함 합에 i-2, i-1, i학생이 포함되는 조의 어색함 합을 더한다.
            // i번째 학생이 2명인 조에 들어간다면
            // i-2번까지의 어색함 합에 i-1, i번 학생이 포함되는 조의 어색함 합을 더한다.
            // 두 값 중 더 작은 값을 취한다.
            else
                dp[i] = Math.min(dp[i - 3] + studentIDs[i - 1] - studentIDs[i - 3],
                        dp[i - 2] + studentIDs[i - 1] - studentIDs[i - 2]);
        }
        // 최종적으로 n명의 학생이 모두 조에 배정됐을 때
        // 어색함 합을 출력한다.
        System.out.println(dp[n]);
    }
}