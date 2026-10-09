/*
 Author : Ruel
 Problem : Jungol 2577번 회전 초밥(고)
 Problem address : https://jungol.co.kr/problem/2577
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_2577_회전초밥_고;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 초밥이 회전 벨트 위에서 돌고 있다.
        // 초밥의 종류는 총 d종이고, k개의 초밥을 연속해서 먹는다.
        // 그 중 c 초밥을 쿠폰으로 무료로 제공한다 할 때
        // 먹을 수 있는 초밥의 서로 다른 종류의 최대 개수는?
        //
        // 두 포인터, 슬라이딩 윈도우 문제
        // k개의 범위에 대해 슬라이딩 윈도우로 전진시켜가며 최대 종류의 개수를 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 초밥, d종류, 연속하여 먹는 개수 k, 무료 제공 초밥 c
        int n = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        // 초밥
        int[] sushi = new int[n];
        for (int i = 0; i < sushi.length; i++)
            sushi[i] = Integer.parseInt(br.readLine());

        // 현재 범위의 각 초밥의 수
        int[] counts = new int[d + 1];
        // 현재 범위의 초밥 종류
        int kinds = 0;
        // 최대 개수 답
        int ans = 0;
        // 0 ~ k-1까지의 초밥 범위를 시작으로 잡고 계산
        for (int i = 0; i < k; i++) {
            if (counts[sushi[i]]++ == 0)
                kinds++;
        }
        // 이 때의 종류 개수
        ans = kinds + (counts[c] == 0 ? 1 : 0);

        // 한 칸씩 밀어가며 서로 다른 종류의 개수를 셈
        for (int i = 1; i < n; i++) {
            if (--counts[sushi[i - 1]] == 0)
                kinds--;
            if (counts[sushi[(i + k - 1) % n]]++ == 0)
                kinds++;

            ans = Math.max(ans, kinds + (counts[c] == 0 ? 1 : 0));
        }
        // 답 출력
        System.out.println(ans);
    }
}