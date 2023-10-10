/*
 Author : Ruel
 Problem : Baekjoon 10564번 팔굽혀펴기
 Problem address : https://www.acmicpc.net/problem/10564
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10564_팔굽혀펴기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 주인공은 미식축구를 보며 팔굽혀펴기를 하고 있다.
        // 팀이 득점할 때마다 현재까지 득점한 점수만큼 팔굽혀펴기를 한다.
        // 만약 7점 득점을 했다면 7회, 추가로 3점을 득점해서 총 10점이 되었다면 10회
        // 또한 추가로 2점을 득점해 12점이 되었다면 12회 팔굽혀펴기를 하여
        // 총 7 + 10 + 12 = 29번 팔굽혀펴기를 한다.
        // 팔굽혀펴기를 한 횟수가 주어질 때
        // 팀이 득점한 가능한 점수 중 가장 큰 값은?
        //
        // DP 문제
        // 위 문제에서 중요한 점은 현재까지 팔굽혀펴기한 횟수와 그 때 가능한 점수'들'이다.
        // 따라서 DP를 통해
        // dp[팔굽혀펴기횟수][점수]로 구분하여 계산한다.
        // 한번에 득점할 수 있는 점수가 최대 20점이고, 팔굽혀펴기한 횟수가 최대 5000으로 주어지므로
        // 만약 20점씩 계속 득점하여 5천번 팔굽혀펴기한다면 약 440점 가량 득점도 가능하므로
        // dp의 열의 최대값은 440으로 한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 테스트케이스
        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 총 팔굽혀펴기 횟수
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            
            // 득점 가능한 점수들
            int[] scores = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            boolean[][] dp = new boolean[n + 1][441];
            // 0회, 0점에서 시작
            dp[0][0] = true;
            for (int i = 0; i < dp.length; i++) {
                for (int j = 0; j < dp[i].length; j++) {
                    // j 점수로 i번 팔굽혀펴기 하는 것이 가능한 경우에만
                    if (dp[i][j]) {
                        // 가능한 점수들로
                        for (int score : scores) {
                            // 현재까지 한 팔굽혀펴기 횟수 i와
                            // 이번에 score점을 득점한다면 하게되는 팔굽혀펴기 횟수 j + score
                            // 해당 팔굽혀펴기 횟수가 범위 안에 있는지 확인하고
                            // true로 체크
                            if (i + j + score < dp.length)
                                dp[i + j + score][j + score] = true;
                        }
                    }
                }
            }
            
            // 마지막 n회 팔굽혀펴기 기록들 중
            // 가장 많은 점수를 찾아
            int answer = -1;
            for (int i = dp[n].length - 1; i >= 0; i--) {
                if (dp[n][i]) {
                    answer = i;
                    break;
                }
            }
            // StringBuilder에 기록
            sb.append(answer).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}