/*
 Author : Ruel
 Problem : Baekjoon 23295번 스터디 시간 정하기 1
 Problem address : https://www.acmicpc.net/problem/23295
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23295_스터디시간정하기1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명 학생의 스터디 참여 가능 시간대가 주어진다.
        // t시간의 시간을 스터디 시간으로 정하여 같이 공부하고자 한다.
        // 스터디 시간 동안 참가자들의 참여 시간 합을 최대화하고자할 때
        // 지정해야하는 시간 구간은?
        //
        // 누적합, 슬라이딩 윈도우 문제
        // 끝나는 시간을 기준으로
        // 해당 시간에 참여한 학생의 수를 누적합으로 구한다.
        // 가령 0 ~ 10000까지 참여하는 학생이 있다면
        // 끝나는 시간을 기준으로 1 ~ 10000까지 +1이 되어야한다.
        // 이를 직접 10000개의 칸을 +1을 하는 것보단 누적합을 이용하여
        // 1에는 1, 10001에는 -1을 표시해두고, 나중에 누적합을 구하면
        // n명 학생의 시간을 한번에 계산할 수 있다.
        // 이를 이용하여 참여하는 학생들의 시간 합이 최대인 구간을 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 학생, 스터디 시간 t 시간
        int n = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        
        // 누적합
        int[] psums = new int[100_002];
        for (int i = 0; i < n; i++) {
            int k = Integer.parseInt(br.readLine());
            for (int j = 0; j < k; j++) {
                st = new StringTokenizer(br.readLine());
                int start = Integer.parseInt(st.nextToken());
                int end = Integer.parseInt(st.nextToken());
                
                // 시작 + 1에 +1
                // 끝나는 시간 +1 에 -1
                psums[start + 1]++;
                psums[end + 1]--;
            }
        }
        // 누적합 처리
        for (int i = 1; i < psums.length; i++)
            psums[i] += psums[i - 1];

        long max = 0;
        long sum = 0;
        int[] answer = new int[]{0, t - 1};
        // 0 ~ t-1 시간까지의 참여 학생 시간 합
        for (int i = 1; i < t; i++)
            sum += psums[i];

        for (int endTime = t; endTime < psums.length; endTime++) {
            // 끝나는 시간이 endTime라면
            // endTime - t ~ endTime까지 참여한 학생들의 시간 합을 구한다.
            // sum에 endTime에 참여한 학생 시간 수 추가
            sum += psums[endTime];
            // 누적합에는 endTime - t시간에 공부가 끝난 학생이 저장되어있으므로
            // 해당 값은 차감
            sum -= psums[endTime - t];
            
            // 최대값 갱신한다면
            if (sum > max) {
                // 값 갱신
                max = sum;
                // 시간 구간 기록
                answer[0] = endTime - t;
                answer[1] = endTime;
            }
        }
        // 시간 구간 출력
        System.out.println(answer[0] + " " + answer[1]);
    }
}