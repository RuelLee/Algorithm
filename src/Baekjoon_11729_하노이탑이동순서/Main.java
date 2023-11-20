/*
 Author : Ruel
 Problem : Baekjoon 11729번 하노이 탑 이동 순서
 Problem address : https://www.acmicpc.net/problem/11729
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11729_하노이탑이동순서;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int count = 0;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        // 1, 2, 3의 막대기가 있으며
        // n개의 원판이 큰 순서대로 1번 막대기에 꽂혀있다.
        // 큰 원판이 작은 원판 아래에 위치한다는 조건을 만족하면서
        // 모든 원판을 3번 막대기로 옮기는 방법을 찾아라
        //
        // 하노이 탑 문제
        // 하노이 탑에서 핵심은 가장 무거운 원판을 목표한 막대기로 옮기는 것이다.
        // 예를 들어 n이 3으로 주어진다면
        // 2개의 원판을 우선적으로 2로 옮긴 후,
        // 마지막 원판을 3으로 옮기고, 2에 있는 원판 2개를 3으로 옮겨주면 된다.
        // 다시 말해
        // n개의 원판을 1번에서 3번으로 옮기는 방법은
        // n-1개의 원판을 다른 하나로 옮긴 후,
        // 가장 무거운 원파을 3번으로 옮기고
        // 다시 n-1개의 원판을 3번으로 옮기면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 원판
        int n = Integer.parseInt(br.readLine());
        
        // 하노이 탑
        hanoiTower(n, 1, 3, 2);
        // 총 옮긴 횟수
        System.out.println(count);
        // 원판을 옮기는 방법 출력
        System.out.print(sb);
    }
    
    // 하노이 탑
    static void hanoiTower(int n, int start, int end, int buffer) {
        // n이 0이 되면 옮기지 않고 종료.
        if (n == 0)
            return;
        
        // n개의 원판을 start -> end로 옮기고, buffer가 다른 하나의 막대기
        // 먼저 n-1개의 원판을 start -> buffer로 옮기고
        hanoiTower(n - 1, start, buffer, end);
        // 가장 무거운 원판을 end로 옮긴 후
        sb.append(start).append(" ").append(end).append("\n");
        count++;
        // buffer에 있는 n-1개의 원판을 end로 옮긴다.
        hanoiTower(n - 1, buffer, end, start);
    }
}