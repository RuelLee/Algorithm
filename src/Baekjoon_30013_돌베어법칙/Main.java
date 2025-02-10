/*
 Author : Ruel
 Problem : Baekjoon 30013번 돌베어 법칙
 Problem address : https://www.acmicpc.net/problem/30013
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30013_돌베어법칙;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 귀뚜라미들의 울음소리를 1초 간격으로 n초간 측정하였다.
        // 귀뚜라미들의 울음소리 주기는 모두 같으며
        // 처음 울기 시작한 시점 부터 주기가 지날 때마다 울며
        // 임의의 시점에서 우는 것을 멈춘 후로는 울지 않는다.
        // 주어지는 귀뚜라미들의 울음소리를 보고서
        // 귀뚜라미의 최소 개체 수를 계산하라
        //
        // 브루트 포스 문제
        // 모든 개체들의 울음 소리 주기가 같다는 점을 유의하며
        // 각 주기 별로 첫 울음소리부터, 같은 주기에 있는 울음소리들은 모두 제외하는 방법으로
        // 귀뚜라미의 최소 개체 수를 계산해나간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n초 간의 기록
        int n = Integer.parseInt(br.readLine());
        String s = br.readLine();
        
        // 귀뚜라미의 최소 개체 수
        int answer = Integer.MAX_VALUE;
        // 울음 소리 주기를 1 ~ n - 1까지
        for (int cycle = 1; cycle < s.length(); cycle++) {
            // 해당 소리가 계산되었는지 체크.
            boolean[] captured = new boolean[n];
            // 최소 귀뚜라미의 수
            int bugCount = 0;
            for (int i = 0; i < s.length(); i++) {
                // 귀뚜라미 울음 소리가 아니거나, 이미 계산된 소리라면 건너뛴다.
                if (s.charAt(i) == '.' || captured[i])
                    continue;
                
                // 새로운 소리를 만났을 경우.
                // 귀뚜라미의 수 증가
                bugCount++;
                int idx = i;
                // 울음 소리를 멈출 때까지의 울음 소리들을 모두 체크한다.
                while (idx < s.length() && s.charAt(idx) == '#') {
                    captured[idx] = true;
                    idx += cycle;
                }
            }
            // 계산된 귀뚜라미의 수가 최솟값을 갱신하는지 확인
            answer = Math.min(answer, bugCount);
        }
        // 답 출력
        System.out.println(answer);
    }
}