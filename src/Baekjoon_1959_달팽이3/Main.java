/*
 Author : Ruel
 Problem : Baekjoon 1959번 달팽이3
 Problem address : https://www.acmicpc.net/problem/1959
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1959_달팽이3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // m행, n열로 되어있는 표에 달팽이 모양으로 선을 그린다.
        // ㅇ	→	↘
        // ↗	↘	↓
        // ↑	↓	↓
        // ↑	끝	↓
        // ↖	←	↙
        // 다음과 같이 시계방향으로 선을 그린다.
        // m과 n이 주어질 때
        // 선이 꺾이는 곳의 개수와 끝나는 점의 좌표를 출력하라
        //
        // 조건이 많은 분기 문제
        // m과 n이 같을 때, m이 클 때, n이 클 때
        // 를 각각 작은 쪽이 홀수일 때와 짝수일 때로 나눠 생각한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 주어지는 m, n
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        // 한 바퀴를 도는 횟수는, m과 n중 더 작은 것의 1/2배 만큼 돈다.
        long circle = Math.min(m, n) / 2;
        if (m == n) {
            // m과 n이 같은 경우
            if (m % 2 == 1) {
                // m이 홀수일 때
                // circle 바퀴를 돌고서, 안쪽으로 한칸 진행한다.
                System.out.println(circle * 4);
                System.out.println((circle + 1) + " " + (circle + 1));
            } else {
                // m이 짝수 일 때
                // circle 바퀴 중 마지막 바퀴에서
                // 오른쪽 직진 -> 아래로 직진 -> 왼쪽으로 직진에서 끝난다.
                // 마지막 바퀴의 꺽이는 횟수는 2번이므로 감안
                System.out.println(circle * 4 - 2);
                System.out.println((m - circle + 1) + " " + circle);
            }
        } else if (m > n) {
            // m이 더 큰 경우
            if (n % 2 == 1) {
                // n이 홀수 일 때
                // circle 바퀴를 진행한 후,
                // 오른쪽 직진 -> 아래로 직진에서 끝난다.
                // 추가로 1번의 꺾임이 생긴다.
                System.out.println(circle * 4 + 1);
                System.out.println((m - circle) + " " + (circle + 1));
            } else {
                // n이 짝수일 때
                // circle의 마지막 바퀴에서
                // 오른쪽 직진 -> 아래로 직진 -> 왼쪽 직진 -> 위로 직진까지 이루어진다.
                // 마지막 바퀴에서 꺾임은 3번.
                System.out.println(circle * 4 - 1);
                System.out.println((circle + 1) + " " + circle);
            }
        } else {
            // n이 더 큰 경우.
            if (m % 2 == 1) {
                // m이 홀수라면
                // 마지막 바퀴 진행 후, 오른쪽으로 직진이 생김.
                System.out.println(circle * 4);
                System.out.println((circle + 1) + " " + (n - circle));
            } else {
                // m이 짝수라면
                // 마지박 바퀴 진행에서
                // 오른쪽 직진 -> 아래로 직진 -> 왼쪽으로 직진에서 끝남.
                // 2번의 꺾임
                System.out.println(circle * 4 - 2);
                System.out.println((m - circle + 1) + " " + circle);
            }
        }
    }
}