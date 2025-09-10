/*
 Author : Ruel
 Problem : Baekjoon 9203번 호텔 예약
 Problem address : https://www.acmicpc.net/problem/9203
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9203_호텔예약;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.*;

public class Main {
    static final int[] daysInMonthPsums = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365};

    public static void main(String[] args) throws ParseException, IOException {
        // t개의 테스트케이스가 주어진다.
        // 각 테스트케이스마다 b개의 예약 정보, 청소 시간 c분이 주어진다.
        // 예약 정보는 "YYYY-MM-DD HH:MM" 형식으로 입실 시간과 퇴실 시간이 주어진다.
        // 모든 예약을 처리하려면 최소 몇 개의 방이 필요한지 계산하라
        //
        // 문자열 파싱, 두 포인터, 그리디, 정렬 문제
        // 가장 먼저 예약 정보를 하나의 수로 나타내는 작업이 필요하다.
        // 따라서 윤년 계산이 필요하다
        // 윤년은 4년마다 찾아오되, 100의 배수인 년은 윤년이 아니고, 400의 배수인 년은 윤년이다.
        // 그리고, 예약 정보를 파싱하여, 분을 기준으로 나타낸다.
        // 2013년부터 ~ 2016년 사이의 년만 주어지므로, 2013년 1월 1일 0시 0분을 0으로 보았다.
        // 그리고, 예약 정보를 토대로 입실 시간과 퇴실시간에 따라 각각 정렬한다.
        // 그리고 두 포인터를 사용하여, 동시에 필요한 방의 개수를 계산한다.
        // 무엇보다 문자열이 많이 쓰이므로, 문자열 배열을 사용하기보단
        // substring이나 stringTokenizer를 활용하여 메모리를 줄이는 것이 중요했다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        // 입실 시간과 퇴실 시간 배열을 매 테스트케이스마다 사용하므로
        // 최대 크기로 미리 선언해둔다.
        long[] startTImes = new long[5000];
        long[] endTimes = new long[5000];
        for (int testCase = 0; testCase < t; testCase++) {
            st = new StringTokenizer(br.readLine());
            // b개의 예약, 청소 시간 c
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            for (int i = 0; i < b; i++) {
                st = new StringTokenizer(br.readLine());
                st.nextToken();
                // 입실 시간
                long start = toMin(st.nextToken(), st.nextToken());
                // 퇴실 시간
                long end = toMin(st.nextToken(), st.nextToken());
                startTImes[i] = start;
                // 퇴실시간 + 청소 시간
                endTimes[i] = end + c;
            }
            // 각각 정렬
            Arrays.sort(startTImes, 0, b);
            Arrays.sort(endTimes, 0, b);

            int answer = 0;
            int j = 0;
            int count = 0;
            // 두 포인터
            for (int i = 0; i < b; i++) {
                // i번째 입실 시각보다 이른 시각의 퇴실 시각을 갖는 경우
                // 해당 방들은 더 이상 사용하지 않으므로, 방을 비운다.
                while (startTImes[i] >= endTimes[j]) {
                    count--;
                    j++;
                }
                // i번째 방에 입실.
                count++;
                // 최대 사용 방의 개수 비교
                answer = Math.max(answer, count);
            }
            // 답 기록
            sb.append(answer).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
    
    // 윤년 여부 확인
    static boolean leapYear(int year) {
        return (year % 400 == 0) || (year % 4 == 0 && year % 100 != 0);
    }
    
    // date와 time을 바탕으로 2013년 1월 1일 0시 0분을 기준으로
    // 누적 분을 계산
    static long toMin(String date, String time) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8, 10));
        int hour = Integer.parseInt(time.substring(0, 2));
        int minute = Integer.parseInt(time.substring(3, 5));

        long days = 0;
        for (int i = 2013; i < year; i++)
            days += leapYear(i) ? 366 : 365;
        days += daysInMonthPsums[month - 1] + (month > 2 && leapYear(year) ? 1 : 0);
        days += day - 1;
        return (days * 24 + hour) * 60 + minute;
    }
}