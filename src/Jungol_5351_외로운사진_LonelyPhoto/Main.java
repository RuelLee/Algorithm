/*
 Author : Ruel
 Problem : Jungol 5351번 외로운 사진 (Lonely Photo)
 Problem address : https://jungol.co.kr/problem/5351
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_5351_외로운사진_LonelyPhoto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n마리의 소가 G 혹은 H로 주어진다.
        // 연속한 세 마리 이상의 소를 사진으로 찍었을 때, 각 종류의 소가 한 마리만 있다면 해당 사진을 버린다고 한다.
        // 모든 경우의 수로 촬영할 때, 버리는 사진의 수는
        //
        // 조합 문제
        // 각 소마다, 자신보다 왼쪽에 같은 색의 가장 가까운 위치, 자신보다 오른쪽에 같은 색의 가장 가까운 위치들을 계산해둔다.
        // 그 후, 자신이 가장 오른쪽이며 자신과 같은 색의 소가 없는 사진, 자신이 가장 왼쪽이며 같은 색의 소가 없는 사진
        // 자신이 사이에 있으며, 같은 색의 소가 없는 사진을 각각 구해 더한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n마리의 소
        int n = Integer.parseInt(br.readLine());
        String input = br.readLine();
        int[] cows = new int[n];
        int[] sameLeft = new int[n];
        int[] lastAppeared = new int[2];
        Arrays.fill(lastAppeared, -1);
        for (int i = 0; i < n; i++) {
            if (input.charAt(i) == 'H')
                cows[i] = 1;

            // 왼쪽에서 자신과 같은 색의 소의 가장 가까운 위치
            sameLeft[i] = lastAppeared[cows[i]];
            // 현재 색의 가장 가까운 위치를 i로 갱신
            lastAppeared[cows[i]] = i;
        }

        // 오른쪽에 대해서도 마찬가지로 계산
        int[] sameRight = new int[n];
        Arrays.fill(lastAppeared, n);
        for (int i = n - 1; i >= 0; i--) {
            sameRight[i] = lastAppeared[cows[i]];
            lastAppeared[cows[i]] = i;
        }

        long answer = 0;
        for (int i = 0; i < n; i++) {
            // 자신의 왼쪽에 연이어 있는 다른 색의 소의 수
            int left = i - sameLeft[i] - 1;
            // 자신의 오른쪽에 있는 다른 색의 소의 수
            int right = sameRight[i] - i - 1;

            // 왼쪽에 있는 다른 색의 소의 수가 2마리 이상이라면
            // 버리는 사진의 수를 합산
            if (left >= 2)
                answer += left - 1;
            // 오른쪽도 마찬가지
            if (right >= 2)
                answer += right - 1;

            // 양 옆에 최소 한마리씩 다른 색의 소가 있는 경우
            // 계산
            if (left >= 1 && right >= 1)
                answer += (long) left * right;
        }
        // 답 출력
        System.out.println(answer);
    }
}