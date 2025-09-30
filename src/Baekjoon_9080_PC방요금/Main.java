/*
 Author : Ruel
 Problem : Baekjoon 9080번 PC방 요금
 Problem address : https://www.acmicpc.net/problem/9080
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9080_PC방요금;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // pc방 1시간 요금은 1000원이며, 야간 정액권을 5000원에 구매하면, 저녁 10시부터 ~ 아침 8시까지 사용할 수 있다.
        // t개의 테스트케이스가 주어지며, 각 테스트케이스마다
        // 입장 시각과 이용 시간이 주어진다.
        // 지불해야하는 최소 이용 요금을 출력하라
        //
        // 시뮬레이션 문제
        // 여러 조건에 따라 계산하되, 야간 정액을 끊는 것이 유리한지
        // 저녁 10시가 넘었지만 그래도 통상 요금을 내는 것이 유리한지 등을 따져 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            st = new StringTokenizer(br.readLine());
            // 현재 시각
            int currentTime = parseTime(st.nextToken());
            // 남은 이용 시간
            int remainTime = Integer.parseInt(st.nextToken());
            // 요금
            int fare = 0;
            
            // 이용 시간이 남아있는 동안
            while (remainTime > 0) {
                // 아침 8시 이후, 저녁 10시 이전이라면
                // 통상 요금을 지불
                if (currentTime >= 480 && currentTime < 1320) {
                    // 끝나는 시각이 10시 이전일 때까지 한 시간마다 1000원씩 지불
                    while (remainTime > 0 && currentTime + 60 < 1320) {
                        currentTime += Math.min(60, remainTime);
                        fare += 1000;
                        remainTime -= Math.min(60, remainTime);
                    }
                    
                    // 현재 시각은 8시 59분 ~ 9시 59분 사이이다.
                    // 현재 이용 시간이 300분 이하로 남았다면
                    // 야간 정액을 끊지 않고 통상 요금으로 남은 시간을 이용하는 것이 유리.
                    if (remainTime <= 300) {
                        currentTime = (currentTime + remainTime) % 1440;
                        fare += (remainTime + 59) / 60 * 1000;
                        remainTime = 0;
                    } else {
                        // 300분 초과로 남았다면 10시까지 통상요금을 지불하고
                        fare += 1000;
                        remainTime -= 1320 - currentTime;
                        currentTime = 1320;

                        // 10시 이후부터 야간 정액을 사용하는 것이 유리
                        currentTime = (currentTime + Math.min(remainTime, 600)) % 1440;
                        fare += 5000;
                        remainTime -= Math.min(remainTime, 600);
                    }
                } else {
                    // 현재 시각이 저녁 10시 ~ 아침 8시 사이인 경우
                    // 현재 야간 정액을 끊을 경우, 이용할 수 있는 시간
                    int nightTime = (1440 + 480 - currentTime) % 1440;
                    // 5시간 이상이라면 야간 정액을 끊는 것이 유리
                    if (nightTime >= 300) {
                        currentTime = (currentTime + nightTime) % 1440;
                        fare += 5000;
                        remainTime -= nightTime;
                    } else {
                        // 5시간 미만이라면 통상 요금을 지불하는 것이 유리
                        // 다만 이 5시간은 남은 시간이 아니라 남은 야간 정액을 이용할 수 있는 시간이다.
                        // 8시 전까지 시간마다 천원을 지불하며 이용
                        while (remainTime > 0 && currentTime < 480) {
                            currentTime = (currentTime + Math.min(remainTime, 60)) % 1440;
                            fare += 1000;
                            remainTime -= Math.min(remainTime, 60);
                        }
                    }
                }
            }
            // 이번 테스트케이스 요금을 기록
            sb.append(fare).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
    
    // 시각을 int 값으로 변환
    static int parseTime(String s) {
        return Integer.parseInt(s.substring(0, 2)) * 60
                + Integer.parseInt(s.substring(3, 5));
    }
}