/*
 Author : Ruel
 Problem : Jungol 1871번 줄세우기
 Problem address : https://jungol.co.kr/problem/1871
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1871_줄세우기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 아이가 줄 서 있다.
        // 각 아이는 자신의 번호가 있는데, 번호 순대로 줄을 세우고 싶다.
        // 자리를 바꿀 때는, 아이를 원하는 위치까지 이끌 수 있다.
        // 최소한의 자리 바꿈을 통해 번호순대로 줄을 세울 때, 그 횟수는?
        //
        // 최장 증가 부분 수열 문제
        // 자리를 바꾸지 않고서, 오름차순이 유지되는 아이들만 제외한 뒤, 다른 아이들의 위치를 바꾸면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n명의 아이
        int n = Integer.parseInt(br.readLine());
        int[] child = new int[n];
        for (int i = 0; i < n; i++)
            child[i] = Integer.parseInt(br.readLine());

        // 오름차순을 유지할 수 있는 부분 수열의 최대 길이를 구한다.
        int maxLength = 0;
        int[] order = new int[n];
        Arrays.fill(order, Integer.MAX_VALUE);
        // 이분 탐색으로 위치를 찾는다.
        for (int i = 0; i < n; i++) {
            int start = 0;
            int end = maxLength;
            while (start < end) {
                int mid = (start + end) / 2;
                if (child[i] <= order[mid])
                    end = mid;
                else
                    start = mid + 1;
            }
            // 해당 순서에 아이의 번호 기록
            order[start] = child[i];
            // 최대 길이 갱신
            maxLength = Math.max(maxLength, start + 1);
        }
        // n명 중, maxLength만큼을 제외한 아이의 위치를 변경시키면
        // 번호순으로 줄 세울 수 있다.
        System.out.println(n - maxLength);
    }
}