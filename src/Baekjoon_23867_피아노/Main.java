/*
 Author : Ruel
 Problem : Baekjoon 32867번 피아노
 Problem address : https://www.acmicpc.net/problem/32867
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23867_피아노;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 노트가 주어진다.
        // 손 길이는 건반 k개 만큼이다.
        // k개의 건반을 손의 이동 없이 한 번에 칠 수 있다.
        // 모든 노트를 연주하는데 필요한 최소 손의 이동은?
        // 첫 손의 위치는 원하는대로 정할 수 있다.
        //
        // 그리디 문제
        // 범위 내 노트들의 최소값과 최대값을 관리하며, 그 범위가 k 이내인 경우는 넘어가고
        // k를 벗어나는 경우는 손의 이동이 필요하다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // n개의 노트, 손의 길이 k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int cnt = 0;
        // 최저 음과 최고 음
        int min, max;
        // 첫 음
        min = max = Integer.parseInt(st.nextToken());
        for (int i = 0; i < n - 1; i++) {
            // 이번 노트
            int note = Integer.parseInt(st.nextToken());
            // 최저 음과 최고 음의 값을 갱신하는지 확인
            min = Math.min(min, note);
            max = Math.max(max, note);

            // 만약 그 범위가 k이상이 되어버렸다면
            // 손의 이동이 필요하다.
            // cnt 증가, 손의 이동 후 첫 음으로 note
            if (max - min >= k) {
                cnt++;
                min = max = note;
            }
        }
        // 전체 손의 이동 횟수 출력
        System.out.println(cnt);
    }
}