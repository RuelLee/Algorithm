/*
 Author : Ruel
 Problem : Baekjoon 15961번 회전 초밥
 Problem address : https://www.acmicpc.net/problem/15961
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15961_회전초밥;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 회전 하는 벨트 위에 n개의 접시가 회전하고 있으며, 각 음식의 종류가 주어진다.
        // 음식의 가짓수는 d개 이며, k개의 음식을 연속하여 먹을 경우, 쿠폰을 발행하여, c번 음식을 무료로 제공한다고 한다.
        // 가장 다양하게 음식을 먹는 경우, 모두 몇 가지의 음식을 먹을 수 있는가?
        //
        // 슬라이딩 윈도우 문제
        // 첫 음식부터 마지막 음식까지 k개의 음식들과 c 음식에 대해 종류별로 카운팅을 하며
        // 가장 많은 음식의 종류를 세어나가자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        // k 범위 안에 있는 음식들의 종류를 카운팅한다.
        int[] dishes = new int[d + 1];
        // 벨트 위에 음식들.
        int[] queue = new int[n];
        for (int i = 0; i < queue.length; i++)
            queue[i] = Integer.parseInt(br.readLine());

        // k개의 음식을 항상 연속으로 먹을 것이므로, c 음식은 항상 포함시켜두자.
        dishes[c]++;
        int kinds = 1;
        // 0번부터 k개의 음식에 대해 종류를 센다.
        for (int i = 0; i < k; i++) {
            if (dishes[queue[i]]++ == 0)
                kinds++;
        }

        // 1번 음식부터 n-1번 음식까지 해당 음식부터, 뒤로 k개의 음식을 먹었을 때의 종류를 계산해보자.
        int maxKinds = kinds;
        for (int i = 1; i < queue.length; i++) {
            // (i - 1) 음식은 dishes에서 하나 빼준다.
            // 만약 (i - 1) 음식을 뺌으로써 k 범위 안에 종류가 하나 줄어들었는지 확인.
            if (--dishes[queue[i - 1]] == 0)
                kinds--;

            // (i + k - 1)번째 음식을 추가해준다.
            // 벨트는 순환하므로, n을 넘어갈 경우, 모듈러 연산 값을 가져온다.
            // 만약 해당 음식 추가로 종류가 늘어났는지 확인한다.
            if (dishes[queue[(i + k - 1) % n]]++ == 0)
                kinds++;

            // 종류의 최대값을 저장한다.
            maxKinds = Math.max(maxKinds, kinds);
        }

        // 최대 음식들의 종류 값 출력.
        System.out.println(maxKinds);
    }
}