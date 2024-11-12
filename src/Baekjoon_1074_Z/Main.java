/*
 Author : Ruel
 Problem : Baekjoon 1074번 Z
 Problem address : https://www.acmicpc.net/problem/1074
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1074_Z;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 2^n * 2^n 행렬을 z모양으로 탐색한다.
        // n이 1일 경우
        // 0 1 으로 탐색한다.
        // n > 1일 경우, 배열을 크기가 2^(n-1) * 2^(n-1)으로 4등분한 후, 재귀적으로 방문한다.
        // n이 2인 경우는
        // 0  1  4  5
        // 2  3  6  7
        // 8  9  12 13
        // 10 11 14 15 와 같이 방문한다.
        // n과 r행, c열이 주어질 때, 방문 순서를 출력하라
        //
        // 단순 반복으로도 풀 수 있는 문제
        // 현재 행렬의 길이를 가지고서
        // 행이 전체 길이의 반 이상일 경우, 순서는 전체 크기의 1/2을 넘어가며
        // 열이 전체 길이의 반 이상일 경우, 순서는 전체 크기의 1/4을 넘어간다.
        // 해당 과정을 하며, 길이를 반씩 줄여가며, 길이가 1이상인 경우 계속 반복한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 2^n의 행렬, r행, c열
        int n = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        
        // 행렬의 한 변의 길이
        int length = (int) Math.pow(2, n);
        // 순서
        int order = 0;
        // 길이가 0 초과인 경우 계속 반복ㄴ
        while (length > 0) {
            // 행이 길이의 반을 넘어가는 경우
            // 순서는 전체 크기의 1/2을 넘어간다.
            if (r >= length / 2) {
                order += length * length / 4 * 2;
                r -= length / 2;
            }

            // 열이 길이의 반을 넘어가는 경우
            // 순서는 전체 크기의 1/4을 넘어간다.
            if (c >= length / 2) {
                order += length * length / 4;
                c -= length / 2;
            }
            
            // 위 두 과정을 끝내면, (r, c)가 속한 length / 2 크기의 행렬만 살펴보면 된다. 
            length /= 2;
        }
        
        // 전체 순서 출력
        System.out.println(order);
    }
}