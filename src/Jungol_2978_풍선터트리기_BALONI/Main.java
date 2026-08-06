/*
 Author : Ruel
 Problem : Jungol 2978번 풍선 터트리기(BALONI)
 Problem address : https://jungol.co.kr/problem/2978
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_2978_풍선터트리기_BALONI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 풍선들이 일렬로 떠있되, 각자의 높이가 주어진다.
        // 왼쪽에서 화살을 쏘면, 오른쪽으로 날아가며, 풍선을 맞출 때마다, 높이가 1씩 낮아진다.
        // 모든 풍선을 터뜨리는데 필요한 최소 수의 화살은?
        //
        // 그리디 문제
        // 그냥 왼쪽에서부터 보며, 이전에 쏜 화살이 날아와 맞추는 경우, 해당 화살의 높이만 낮추고
        // 해당하는 높이의 화살이 없는 경우, 새로운 화살을 추가해나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 풍선
        int n = Integer.parseInt(br.readLine());

        // 필요한 화살의 수
        int cnt = 0;
        // 화살이 날고 있는 높이
        int[] hights = new int[1_000_001];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            // 현재 풍선의 높이
            int balloon = Integer.parseInt(st.nextToken());
            // 해당하는 화살이 있는 경우, 해당 높이의 화살을 감소
            if (hights[balloon] > 0)
                hights[balloon]--;
            else    // 없는 경우, 새로운 화살을 발사
                cnt++;
            // 해당 높이의 풍선을 맞추고, 높이가 1 낮아진 화살
            hights[balloon - 1]++;
        }
        // 답 출력
        System.out.println(cnt);
    }
}