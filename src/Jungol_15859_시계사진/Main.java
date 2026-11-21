/*
 Author : Ruel
 Problem : Jungol 15859번 시계 사진
 Problem address : https://jungol.co.kr/problem/15859
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_15859_시계사진;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int cycle = 360_000;

    public static void main(String[] args) throws IOException {
        // n개의 바늘이 있는 시계 사진이 두 장 주어진다.
        // 현재 각도에서 1/1000도로 놓인 바늘의 위치가 주어진다.
        // 시계를 회전 시켜 두 시계를 일치시킬 수 있는지 판별하라
        //
        // kmp 문제
        // 먼저 바늘을 정렬 -> 바늘 사이의 간격을 계산
        // 그리고 kmp를 통해, 각 바늘이 0인 위치를 기준으로(=확인을 두바퀴)
        // 바늘 간격들이 일치하는지 여부를 판별한다

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 바늘
        int n = Integer.parseInt(br.readLine());
        // 두 장의 시계 사진
        int[][] pictures = new int[2][n];
        StringTokenizer st;
        for (int i = 0; i < pictures.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < pictures[i].length; j++)
                pictures[i][j] = Integer.parseInt(st.nextToken());
            // 정렬
            Arrays.sort(pictures[i]);
        }
        // 바늘 사이의 간격
        int[][] diffs = new int[2][n];
        for (int i = 0; i < pictures.length; i++) {
            // 마지막 바늘과 첫 바늘 사이의 간격은 음수가 나올 수 있다.
            // 나머지 연산으로 양수 값으로 처리
            for (int j = 0; j < pictures[i].length; j++)
                diffs[i][j] = (pictures[i][(j + 1) % pictures[i].length] + cycle - pictures[i][j]) % cycle;
        }

        // kmp
        // pi 배열
        int[] pi = new int[n];
        int j = 0;
        for (int i = 1; i < diffs[0].length; i++) {
            while (j > 0 && diffs[0][i] != diffs[0][j])
                j = pi[j - 1];
            if (diffs[0][i] == diffs[0][j])
                pi[i] = ++j;
        }

        j = 0;
        boolean possible = false;
        // i를 두바퀴 돌리는 이유
        // 0 ~ n-1까지가 원래 길이이며 0번 바늘이 기준이 되는 시점
        // n-1 ~ 2n-2 까지를 살펴본다.
        for (int i = 0; i < diffs[1].length * 2 - 1; i++) {
            while (j > 0 && diffs[1][i % diffs[1].length] != diffs[0][j])
                j = pi[j - 1];
            if (diffs[1][i % diffs[1].length] == diffs[0][j]) {
                // n개가 일치했다면 방향을 기울여 일치하는 경우
                // 가능 체크 후, 반복문 종료
                if (++j == diffs[0].length) {
                    possible = true;
                    break;
                }
            }
        }
        // 답 출력
        System.out.println(possible ? "possible" : "impossible");
    }
}