/*
 Author : Ruel
 Problem : Baekjoon 28127번 숫자탑과 쿼리
 Problem address : https://www.acmicpc.net/problem/28127
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28127_숫자탑과쿼리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 숫자가 적힌 블록으로 탑을 쌓는다.
        // 1층에는 a개의 블록이 존재하고, 1번부터 시작한다.
        // 가장 왼쪽의 블록부터 오른쪽으로 갈수록 하나씩 수가 증가한다.
        // i번째 층의 가장 오른쪽 블록보다 i+1번째 층의 가장 왼쪽 블록이 1 더 크다.
        // i번째 층에 있는 블록보다 i+1번째 층의 블록이 d개 더 많다.
        // q개의 쿼리가 들어온다.
        // a d x : a와 d가 주어질 때, x번 블록은 몇 층 몇 번째에 있는가
        //
        // 등차수열의 합, 이분 탐색 문제
        // 첫 항이 a, 공차가 d, 항의 개수 n으로 주어진다면
        // 등차수열의 합으로 n * (a + (a + d)) / 2 가 등차수열의 합임을 알고 있다.
        // 이를 이용하여, 등차수열의 합이 x보다 작지만 가장 큰 값을 찾아, 그 때의 n이
        // 원하는 바로 윗 층이 된다. 그리고, 등차수열의 합에서 x와의 거리가 순서가 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // q개의 커ㅜ리
        int q = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());

            // 이분 탐색을 통해 층을 찾는다.
            int start = 1;
            int end = 1415;
            while (start < end) {
                int mid = (start + end) / 2;
                // 등차수열의 합이 더 적다면
                // start 범위를 mid+1로 조정
                if (mid * (a + a + (long) d * (mid - 1)) / 2 < x)
                    start = mid + 1;
                else        // 같거나 크다면 end를 mid로 조정 
                    end = mid;
            }
            // start층에 x가 존재
            // x에서 start - 1층의 마지막 수를 뺀 만큼이 x가 start 층에서 등장하는 순서 
            int order = x - (start - 1) * (a + a + d * (start - 2)) / 2;
            // 답 기록
            sb.append(start).append(" ").append(order).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}