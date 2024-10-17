/*
 Author : Ruel
 Problem : Baekjoon 31413번 입대
 Problem address : https://www.acmicpc.net/problem/31413
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_31413_입대;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 공군에 입대하기 위해서는 m점 이상의 가산점을 모아야한다.
        // 입대까지는 n일 남았고,
        // 남은 기간 동안, 봉사활동 혹은 헌혈을 할 수 있다.
        // 각 남은 기간 동안 봉사활동을 할 때 얻을 수 있는 매일 매일의 점수들이 주어지고
        // 헌혈을 할 경우 일괄적으로 a점을 획득하나, 헌혈한 날 포함 d일 동안 다른 활동을 할 수 없다.
        // 헌혈은 n일째에도 할 수 있다.
        // 헌혈을 최소한으로 하며, m점 이상을 모으고자 할 때
        // 최소 헌혈 횟수는?
        //
        // DP 문제
        // dp[날짜][헌혈횟수] = 점수
        // 로 dp를 세우고 채워나가면 된다.
        // 날은 최대 n일 이고, 헌혈 횟수는 최대 Math.ceil((double) n / d)회 할 수 있다.
        // 입대 직전까지 헐혈을 할 수 있기 때문.
        // dp를 채워가며, 마지막 날에 m점 이상 획득이 가능한 최소 헌혈횟수를 찾는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 남을 일짜 n, 요구 가산점 m
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 각 날짜마다 얻을 수 있는 봉사 활동 점수
        int[] volunteerWorks = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        st = new StringTokenizer(br.readLine());
        // 헌혈 시 얻는 점수 a와 휴식일 d
        int a = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());
        
        // dp
        int[][] dp = new int[n][(int) Math.ceil((double) n / d) + 1];
        // 첫 날 봉사활동을 할 경우 점수
        dp[0][0] = volunteerWorks[0];
        // 첫 날 헌혈을 할 경우 점수
        dp[Math.min(d - 1, n - 1)][1] = a;
        for (int i = 0; i < dp.length - 1; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                // 0점은 건너뜀.
                if (dp[i][j] == 0)
                    continue;

                // 현재 i번째 날을 마쳤고, 헌혈 횟수는 j일 때
                // i+1번째 날 봉사활동을 하는 경우.
                dp[i + 1][j] = Math.max(dp[i + 1][j], dp[i][j] + volunteerWorks[i + 1]);

                // i+1번째 날 헌혈을 하는 경우
                // 이로 인해 i+d일까지 아무 활동을 하지 못한다.
                // i+d가 n-1을 넘어갈 수도 있다. 하지만 입대 직전까지도 헌혈이 가능하므로
                // 두 값 중 적은 날로 계산해준다.
                int recoverDay = Math.min(i + d, n - 1);
                if (j + 1 < dp[recoverDay].length)
                    dp[recoverDay][j + 1] = Math.max(dp[recoverDay][j + 1], dp[i][j] + a);
            }
        }

        // 입대 직전
        // m점 이상 모은 헌혈 횟수들 중
        // 가장 적은 헌혈 횟수를 찾는다.
        int answer = -1;
        for (int i = 0; i < dp[n - 1].length; i++) {
            if (dp[n - 1][i] >= m) {
                answer = i;
                break;
            }
        }
        System.out.println(answer);
    }
}