/*
 Author : Ruel
 Problem : Baekjoon 12438번 새로운 달력 (Large)
 Problem address : https://www.acmicpc.net/problem/12438
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12438_새로운달력_Large;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 달력을 만들고자 한다.
        // 달이 바뀌면 줄바꿈을 한다.
        // 달의 개수, 월당 일수, 주당 일수가 주어진다.
        // 1년치 달력이 몇 줄에 표현되는가?
        //
        // 수학 문제
        // 먼저, 한 주의 시작 요일을 아는 것이 중요하다.
        // 따라서, 몇 달이 지나면, 시작 요일이 다시 처음과 같아지는가를 찾아야한다. 다시 말해 사이클을 찾는다.
        // 주당 일수가 100이므로 월당 일수와 주당 일수가 서로소더라도 최대 100번 안에 찾을 수 있다.
        // 사이클 주기를 찾고서는 각 달에 포함되는 주의 개수를 센다.
        // 그리고 사이클 동안 소요되는 주의 개수를 누적시켜 계산한다.
        // 1년의 달의 개수를 사이클로 나누고, 해당 사이클의 개수만큼을 미리 구해둔 사이클 당 주의 개수로 곱해준다.
        // 그리고 남은 달에 대해서는 미리 구해둔 월당 주의 개수를 누적시켜, 전체 주의 개수를 찾는다.
        // 재밌는 점은 달과 주의 서로 상관되는 조건이 없다는 것인데, 주의 길이가 달의 길이보다 더 길수도 있다는 점이다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트 케이스
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 1년에 속한 달의 개수
            long months = Long.parseLong(st.nextToken());
            // 한 달에 속한 일수
            long daysInMonth = Long.parseLong(st.nextToken());
            // 한 주에 속한 일수
            long daysInWeek = Long.parseLong(st.nextToken());

            int cycle = 1;
            // 다시 시작 요일이 같아지는 월의 개수를 구한다.
            while ((cycle * daysInMonth) % daysInWeek != 0)
                cycle++;
            
            // 해당 사이클 동안 각 달에 속한 주의 개수를 구한다.
            long[] dp = new long[cycle];
            long sum = 0;
            // 시작 요일
            long startIdx = 0;
            for (int i = 0; i < dp.length; i++) {
                // 한 달에 남은 일 수
                long remainDays = daysInMonth;
                // 만약 남은 일이 주에 남은 일 수보다 작은 경우
                // 한 달이 통째로 주에 들어간다.
                // 한 줄만 추가되고, 시작 요일이 변경된다.
                if (daysInWeek - startIdx > remainDays) {
                    dp[i]++;
                    startIdx += remainDays;
                } else {
                    // 그 외의 일반적인 경우
                    // 시작 요일을 고려하여 첫 줄에 표현되는 일의 개수 차감
                    remainDays -= (daysInWeek - startIdx);
                    // 남은 일 수를 주에 속한 일의 개수로 나눠 주의 개수를 계산.
                    // 그리고 남은 일이 있다면 한 주 추가
                    // 첫 주 + 가득찬 주의 개수 + 남은 일이 있다면 한 주 추가
                    dp[i] = 1 + remainDays / daysInWeek + (remainDays % daysInWeek == 0 ? 0 : 1);
                    // 시작 요일 변경
                    startIdx = (int) (remainDays % daysInWeek);
                }
                // 해당 월에 속한 주의 개수 누적
                sum += dp[i];
            }

            long answer = 0;
            // 1년이 몇 사이클인지 계산 후, 통째로 주의 개수 누적
            answer += months / cycle * sum;
            // 남은 월에 대해 직접 돌며 월당 주의 개수를 누적
            months %= cycle;
            for (int i = 0; i < months; i++)
                answer += dp[i];
            // 답 기록
            sb.append("Case #").append(testCase + 1).append(": ").append(answer).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}