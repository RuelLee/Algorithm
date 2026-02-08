/*
 Author : Ruel
 Problem : Baekjoon 1206번 사람의 수
 Problem address : https://www.acmicpc.net/problem/1206
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1206_사람의수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 설문 문항에 대해 평균 점수가 주어진다.
        // 응답자가 줄 수 있는 점수는 0 ~ 10의 정수로 된 수이다.
        // 평균 점수가 소수점 셋째 자리에서 자른 값으로 주어질 때
        // 가능한 응답자의 수는?
        //
        // 브루트 포스 문제
        // 평균이 소수점 셋째 자리에서 잘린 값으로 주어지기 때문에
        // 사실 1000명이면 모든 경우에 대해 가능하다.
        // 따라서 각각의 평균에 대해 1 ~ 1000 명으로 해당 평균을 만들 수 있는지 체크해보면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        // 평균 값들을 1000배하여 정수값으로 만든다.
        int[] averages = new int[n];
        for (int i = 0; i < n; i++) {
            String[] input = br.readLine().split("\\.");
            averages[i] = Integer.parseInt(input[0]) * 1000 + Integer.parseInt(input[1]);
        }

        // 응답자의 수 p
        for (int p = 1; p <= 1000; p++) {
            boolean found = true;
            // 모든 평균들에 대해
            for (int average : averages) {
                boolean possible = false;
                // 누적 점수가 s점이고 인원이 p인 경우
                // 평균이 average가 될 수 있는지 확인한다.
                for (int s = 0; s <= p * 10000; s += 1000) {
                    if (s / p == average) {
                        possible = true;
                        break;
                    }
                }
                // 그러한 경우가 없다면
                // 해당 p의 경우는 불가능한 경우.
                if (!possible) {
                    found = false;
                    break;
                }
            }

            // 그러한 p를 찾았다면
            // 이후 수에 대해는 찾지말고 해당 p를 출력한 뒤 반복문 종료
            if (found) {
                System.out.println(p);
                break;
            }
        }
    }
}