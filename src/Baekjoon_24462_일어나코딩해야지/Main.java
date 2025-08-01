/*
 Author : Ruel
 Problem : Baekjoon 24462번 일어나... 코딩해야지...
 Problem address : https://www.acmicpc.net/problem/24462
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_24462_일어나코딩해야지;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 알람이 울리는 시간 d와 n개의 알람에 대한 정보가 주어진다.
        // 알람은 t와 k로 주어지며, 첫 알람이 울리는 시각 t, 주기 k로 나타낸다.
        // d는 k의 배수임이 보장된다.
        // n개의 알람 중 두 개의 알람을 선택하여, 울리는 횟수가 최대가 되게끔 하고자 한다.
        // 중복하여 울리는 경우는 하나로 센다.
        // 선택해야하는 알람의 번호들과 울리는 횟수를 출력한다.
        //
        // 브루트 포스, 유클리드 호제법
        // 먼저, 알람에 대해 하나의 알람이 d동안 울리는 횟수는 d / k + 1 - (t / k)로 정의할 수 있다.
        // 따라서 두 알람을 선택하여 각각의 알람이 울리는 횟수를 더하고
        // 두 개의 알람이 울려도 한 번으로 세므로, 동시에 울리는 공배수의 시간 만큼은 알람 횟수를 하나씩 제거한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 알람의 개수 n, 울리는 시간 d
        int n = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());
        
        // 알람에 대한 정보
        int[][] alarms = new int[n][2];
        for (int i = 0; i < alarms.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < alarms[i].length; j++)
                alarms[i][j] = Integer.parseInt(st.nextToken());
        }

        int[] answer = new int[3];
        for (int i = 0; i < alarms.length; i++) {
            for (int j = i + 1; j < alarms.length; j++) {
                int sum = 0;
                // i번 알람이 울리는 횟수
                sum += d / alarms[i][1] + 1;
                sum -= alarms[i][0] / alarms[i][1];
                // j번 알람이 울리는 횟수
                sum += d / alarms[j][1] + 1;
                sum -= alarms[j][0] / alarms[j][1];
                
                // 최소 공배수
                long LCM = (long) alarms[i][1] * alarms[j][1] / getGCD(alarms[i][1], alarms[j][1]);
                // 두 알람이 동시에 울리기 시작하는 시각
                long startTime = Math.max(alarms[i][0], alarms[j][0]);
                // 0이 아닌 경우에는 정확히 LCM의 배수가 아닐 수 있으므로, 값 보정
                if (startTime != 0)
                    startTime = ((startTime - 1) / LCM + 1) * LCM;
                // 반대로 두 개의 알람이 동시에 울리는 횟수에 대해서는 차감
                sum -= d / LCM + 1;
                sum += startTime / LCM;
                
                // 최대 횟수를 갱신하는지 확인
                if (sum > answer[2]) {
                    answer[0] = i;
                    answer[1] = j;
                    answer[2] = sum;
                }
            }
        }
        // 답 출력
        System.out.println((answer[0] + 1) + " " + (answer[1] + 1));
        System.out.println(answer[2]);
    }

    static int getGCD(int a, int b) {
        int min = Math.min(a, b);
        int max = Math.max(a, b);

        while (min > 0) {
            int temp = max % min;
            max = min;
            min = temp;
        }
        return max;
    }
}