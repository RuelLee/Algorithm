/*
 Author : Ruel
 Problem : Baekjoon 4384번 공평하게 팀 나누기
 Problem address : https://www.acmicpc.net/problem/4384
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_4384_공평하게팀나누기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 학생의 몸무게가 주어진다.
        // 학생들을 최대 한 명 차이가 나는 두 팀으로 나누어
        // 두 팀의 몸무게 합의 차이가 최소가 되고자할 때
        // 각 팀의 무게는?
        // 팀의 무게는 오름차순으로 출력한다
        //
        // 배낭 문제
        // dp[사람의 수][무게] = 가능 여부
        // 로 dp를 세운다.
        // 해당 결과를 통해
        // 짝수일 경우 n/2명의 가능한 무게합 중 sum / 2에 가장 가까운 값을 가져온다.
        // 만약 홀수일 경우
        // n/2명과 n/2 + 1명도 고려한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n명의 몸무게
        int n = Integer.parseInt(br.readLine());
        int[] weights = new int[n];
        int sum = 0;
        for (int i = 0; i < weights.length; i++)
            sum += (weights[i] = Integer.parseInt(br.readLine()));
        
        // dp[명수][무게] = 가능여부
        boolean[][] dp = new boolean[n + 1][sum + 1];
        // 0명일 때는 0 초기값
        dp[0][0] = true;
        // 한 명씩 모두 살펴본다
        for (int weight : weights) {
            // 중복 계산되지 않도록 내림차순으로 살펴본다.
            // 가능한 인원 수
            for (int i = dp.length - 1; i >= 0; i--) {
                // 가능한 무게 합
                for (int j = dp[i].length - 1; j >= 0; j--) {
                    // i명일 때, 합이 j가 되는 것이 가능하다면
                    // i+1명일 때, j+weight도 가능하다.
                    if (dp[i][j])
                        dp[i + 1][j + weight] = true;
                }
            }
        }

        int answer = 0;
        // n/2명으로 만들 수 있는 sum / 2에 가장 가까운 몸무게 합을 찾는다.
        for (int i = sum / 2; i >= 0; i--) {
            if (dp[n / 2][i]) {
                answer = i;
                break;
            }
        }
        // 인원이 홀수일 경우
        if (n % 2 == 1) {
            // n/2 + 1명도 고려한다.
            for (int i = sum / 2; i >= 0; i--) {
                if (dp[n / 2 + 1][i] && Math.abs(2 * i - sum) < Math.abs(2 * answer - sum)) {
                    answer = i;
                    break;
                }
            }
        }
        
        // 찾은 두 팀의 몸무게 합 출력
        System.out.println(answer + " " + (sum - answer));
    }
}