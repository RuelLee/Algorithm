/*
 Author : Ruel
 Problem : Baekjoon 20002번 사과나무
 Problem address : https://www.acmicpc.net/problem/20002
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20002_사과나무;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * n 크기의 정사각형 과수원이 주어진다.
        // 각 칸에는 사과나무가 심어져있고, 이익 - 이건비로 각 칸의 값이 주어진다.
        // 과수원에서 1 <= k <= n 크기의 사과나무들을 수확할 수 있을 때
        // 얻을 수 있는 최대 이익은?
        //
        // 이차원 누적합 문제
        // 누적합을 2차원으로 생각해야하는 문제
        // ■■■
        // ■□□
        // ■□□의 넓이를 구하고 싶다면
        //
        // □□□   □■■   □□□   □■■   ■■■
        // □□□   □■■   ■■■   ■■■   ■□□
        // □□□ - □■■ - ■■■ + ■■■ = ■□□
        // 과 같은 형태로 구할 수 있다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        // 주어지는 과수원 각 칸의 이익 - 인건비
        int[][] orchard = new int[n][];
        for (int i = 0; i < orchard.length; i++)
            orchard[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 이차원 누적합
        // 위의 형태를 유의하며, 누적합 계산
        int[][] psum = new int[n + 1][n + 1];
        for (int i = 0; i < orchard.length; i++) {
            for (int j = 0; j < orchard[i].length; j++)
                psum[i + 1][j + 1] = psum[i][j + 1] + psum[i + 1][j] + orchard[i][j] - psum[i][j];
        }

        // k 크기의 최대 총 이익 계산
        int max = Integer.MIN_VALUE;
        // (i, j) 에서
        for (int i = 1; i < psum.length; i++) {
            for (int j = 1; j < psum[i].length; j++) {
                // 가능한 최대 크기의 정사각형
                int maxSIze = Math.min(psum.length - i, psum[i].length - j);
                // 1칸짜리부터, maxSize - 1 크기까지의 정사각형 모양의 땅에 대한
                // 총 이익을 계산하고, 최대 이익인지 확인.
                for (int size = 0; size < maxSIze; size++) {
                    max = Math.max(max,
                            psum[i + size][j + size] - psum[i + size][j - 1] - psum[i - 1][j + size] + psum[i - 1][j - 1]);
                }
            }
        }

        // 답안 출력
        System.out.println(max);
    }
}