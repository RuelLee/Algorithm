/*
 Author : Ruel
 Problem : Jungol 1500번 트리플
 Problem address : https://jungol.co.kr/problem/1500
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1500_트리플;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 최대 770개의 점이 주어진다.
        // 세 점을 골라, 한 직선 위에 있는 경우를 찾아라
        // 그 때의 경우의 수와 포함되는 점들을 사전순으로 출력한다.
        //
        // 브루트 포스 문제
        // 770C3인 경우의 수인데, 1억 이내이므로 직접 계산이 가능
        // 세 점을 골라 두 직선의 기울기를 비교한다.
        // 단 나눗셈의 경우 오차가 발생할 수 있으므로 곱하기로 바꿔 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 점
        int n = Integer.parseInt(br.readLine());

        // 각 점들
        int[][] points = new int[n][2];
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            points[i][0] = Integer.parseInt(st.nextToken());
            points[i][1] = Integer.parseInt(st.nextToken());
        }

        // 경우의 수
        int cnt = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    // i, j, k의 세 점을 골라
                    // i, j의 직선의 기울기와
                    // i, k 직선의 기울기를 비교하여 같다면 기록
                    if ((points[j][1] - points[i][1]) * (points[k][0] - points[i][0]) ==
                            (points[k][1] - points[i][1]) * (points[j][0] - points[i][0])) {
                        cnt++;
                        sb.append(i + 1).append(" ").append(j + 1).append(" ").append(k + 1).append("\n");
                    }
                }
            }
        }
        // 답 출력
        System.out.println(cnt);
        System.out.println(sb);
    }
}