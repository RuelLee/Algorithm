/*
 Author : Ruel
 Problem : Baekjoon 18427번 함께 블록 쌓기
 Problem address : https://www.acmicpc.net/problem/18427
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18427_함께블록쌓기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int LIMIT = 10_007;

    public static void main(String[] args) throws IOException {
        // n명의 학생이 각각 최대 m개의 블록을 갖고 있다.
        // 각 학생이 하나의 블록만 쌓아, 높이 h의 탑을 만들고자 한다.
        // 만들 수 있는 경우의 수는?
        //
        // 배낭 문제
        // 각 학생이 동시에 하나의 블록만 쌓을 수 있음에 유의하며
        // 배낭 문제와 같이 푼다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 학생이 최대 m개의 블록을 갖고 있으며
        // 높이 h의 탑을 쌓고자 한다.
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());
        
        // 각 학생의 갖고 있는 블록들
        int[][] students = new int[n][];
        for (int i = 0; i < students.length; i++) {
            students[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            Arrays.sort(students[i]);
        }

        // 배낭 문제
        int[] dp = new int[h + 1];
        // 0인 경우 1가지
        dp[0] = 1;
        // 모든 학생들을 살펴본다.
        for (int[] student : students) {
            // 높이 i의 탑을 완성하는 경우를 계산.
            for (int i = dp.length - 1; i - student[0] >= 0; i--) {
                // student가 갖고 있는 블록들을 살펴본다.
                for (int j = 0; j < student.length; j++) {
                    if (i - student[j] < 0)
                        break;
                    
                    // i - student[j] 높이에, student[j]를 쌓으면 높이 i의 탑이 완성된다.
                    // 해당 경우의 수 합산
                    dp[i] += dp[i - student[j]];
                    // 모듈러 처리
                    dp[i] %= LIMIT;
                }
            }
        }
        
        // 높이 h 탑을 쌓는 경우의 수 출력
        System.out.println(dp[h]);
    }
}