/*
 Author : Ruel
 Problem : Baekjoon 24538번 작업 일지
 Problem address : https://www.acmicpc.net/problem/24538
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_24538_작업일지;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 한 공장에서 k일 동안 총 n명의 직원이 일을 했다.
        // 각 직원의 일을 시작 날 s와 마지막으로 일을 한 날 e가 주어진다.
        // 각 직원은 날마다 숙련도가 상승하여, 근무 i일 차에 i일의 수익을 낸다.
        // k일 동안 매일 기록한 수익을 출력하라
        //
        // 누적 합
        // 공장 이익을 살펴보면, 직원이 하루에 1씩 수익을 더 내게 되므로
        // 어제보다 오늘 직원의 수만큼의 이익이 증가하게 된다.
        // 만약 어제까지만 근무한 직원이 있다면, 해당 직원의 근무날짜에 따른 이익만큼이 감소한다.
        // 따라서
        // imos 법으로 매일 근무하는 인원을 관리하고
        // 각 직원의 마지막 근무 다음 일에는 해당 직원의 근무일을 고려한 감소 이익을 표시해둔다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 직원과 공장 가동일 k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 매일 근무한 직원의 수
        int[] workersPerDay = new int[k + 2];
        // 직원 퇴사에 따른 이익 감소
        long[] decreaseProfit = new long[k + 2];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            // 직원의 근무 시작일
            int s = Integer.parseInt(st.nextToken());
            // 근무 마지막 일
            int e = Integer.parseInt(st.nextToken());

            // 근무 시작일에 직원수 +1
            workersPerDay[s]++;
            // 근무 마지막일 +1일에 직원수 -1
            workersPerDay[e + 1]--;
            // 근무 마지막일 + 1일에 해당 직원이 퇴사함에 따른 감소 이익.
            decreaseProfit[e + 1] += (e - s + 1);
        }

        StringBuilder sb = new StringBuilder();
        // 현재 공장의 이익
        long profit = 0;
        // 1일 ~ k일까지 이익 계산
        for (int i = 1; i <= k; i++) {
            // imos 법으로 현재 근무하는 직원의 수를 구하고, 해당 수만큼 이익 증가.
            profit += (workersPerDay[i] += workersPerDay[i - 1]);
            // i-1일 퇴사한 직원에 따른 이익 감소 반영
            profit -= decreaseProfit[i];
            
            // 답 기록
            sb.append(profit).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        // 전체 답 출력
        System.out.println(sb);
    }
}