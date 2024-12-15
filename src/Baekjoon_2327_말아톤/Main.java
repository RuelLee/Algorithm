/*
 Author : Ruel
 Problem : Baekjoon 2327번 말아톤
 Problem address : https://www.acmicpc.net/problem/2327
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2327_말아톤;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 학생들이 팀을 이루어 그룹달리기에 참가한다.
        // 그룹달리기는 말아톤과 같은 형식이다.
        // 팀의 구성원 중 가장 늦게 도착한 사람의 도착 시간이 팀의 도착 시간이 된다.
        // 따라서 가장 느린 팀원이 가장 빠르게 되도록 팀을 구성해야한다.
        // 팀은 키의 전체 합이 h가 되도록 구성한다.
        // 각 학생들의 키와 달리기 속도가 주어질 때
        // 구성한 팀의 가장 느린 사람의 달리기 속도를 출력하라
        //
        // 배낭 문제
        // 키에 따른 가장 느린 달리기 속도를 가진 사람의 속도를
        // 배낭 문제와 같게 구해나가면 된다.
        // dp[키의 합] = 가장 느린 사람의 속도
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 팀을 구성할 때 전체 팀원의 키의 합 h
        int h = Integer.parseInt(st.nextToken());
        // n명의 학생
        int n = Integer.parseInt(st.nextToken());
        
        // 각 학생의 키와 달리기 속도
        int[][] students = new int[n][2];
        for (int i = 0; i < students.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < students[i].length; j++)
                students[i][j] = Integer.parseInt(st.nextToken());
        }

        int[] dp = new int[h + 1];
        dp[0] = Integer.MAX_VALUE;
        // 각 학생들을 순차적으로 살펴본다.
        for (int[] s : students) {
            // 한 학생이 중복으로 계산되지 않기 위해 큰 값부터 계산.
            // 키의 합이 j가 되는 경우
            // 현재 dp[j]의 가장 느린 달리기 속도를 가진 사람과
            // dp[j-s의 키]의 그룹에 s를 추가하여
            // 키의 합은 j, 가장 느린 사람의 속도는 j-s의 키 그룹과 s중 느린 사람의 속도
            // 를 비교하여 값을 계산한다.
            for (int j = dp.length - 1; j - s[0] >= 0; j--)
                dp[j] = Math.max(dp[j], Math.min(dp[j - s[0]], s[1]));
        }
        // 키의 합이 h일 때,
        // 가장 느린 사람의 최대 속도를 구한다.
        System.out.println(dp[h]);
    }
}