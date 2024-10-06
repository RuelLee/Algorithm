/*
 Author : Ruel
 Problem : Baekjoon 1214번 쿨한 물건 구매
 Problem address : https://www.acmicpc.net/problem/1214
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1214_쿨한물건구매;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // p원 지폐와 q원 지폐 두 종류로
        // 가격이 d원인 물건을 구매하고자 한다.
        // 두 지폐로 지불하는 금액은 d원 이상이어야 한다.
        // 지불하는 금액을 최소로 하고자 할 때 그 금액은?
        //
        // 브루트 포스, 가지치기
        // 브루트 포스를 활용하는 풀이방법이되, 수의 범위가 10억까지 주어지므로 가지치기를 해야한다.
        // 먼저 p와 q중 더 큰 금액권을 통해 해당 0장부터 해당 지폐로만 d원을 넘길 때까지의 금액을 살펴본다
        // 나머지 금액은 작은 금액권을 사용하여 채운다.
        // 위 방법까지가 브루트 포스
        // 그런데 중간에 같은 금액이 반복해서 발생하는 경우가 생긴다면
        // 두 지폐의 최소공배수만큼을 통해 사이클이 발생하는 경우다.
        // 그 경우에는 더 이상 찾아볼 필요없이 반복문을 종료한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 지불하고자 하는 금액 d
        int d = Integer.parseInt(st.nextToken());
        // 금액권의 종류 p, q
        int p = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        
        // 큰 금액권과 작은 금액권 분리
        int max = Math.max(p, q);
        int min = Math.min(p, q);

        int answer = Integer.MAX_VALUE;
        // max * i가 d 이상일 때까지
        // 0부터 탐색한다.
        for (int i = 0; i <= d / max + (d % max == 0 ? 0 : 1); i++) {
            int j;
            // 아직 잔여 금액이 있는 경우
            // 나머지 금액을 min으로 채운다.
            if (d - max * i > 0)
                j = (d - max * i) / min + ((d - max * i) % min == 0 ? 0 : 1);
            else
                j = 0;
            
            // 최종적으로 만들어진 금액
            int value = max * i + min * j;
            // 더 적은 금액을 찾았다면 갱신
            if (answer > value)
                answer = value;
            // 같은 금액이 다시 발생했다면 사이클이 생겨 반복하는 경우이므로
            // 반복문 종료
            else if (answer == value)
                break;
        }
        // 답 출력
        System.out.println(answer);
    }
}