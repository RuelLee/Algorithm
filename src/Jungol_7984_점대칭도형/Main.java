/*
 Author : Ruel
 Problem : Jungol 7984번 점대칭도형
 Problem address : https://jungol.co.kr/problem/7984
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_7984_점대칭도형;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 직사각형이 주어진다.
        // 각 직사각형의 너비와 높이가 주어지며, 모두 짝수이다.
        // 각 직사격형의 중심은 사사분면의 원점에 위치하며, 각 너비와 높이는 x축과 y축에 평행하다
        // 모든 사각형을 겹쳐놓았을 때의 넓이는?
        //
        // 정렬 문제
        // 먼저, 직사각형의 중심이 원점에 위치하고, x축과 y축에 평행하므로
        // 한 사분면만 따져 *4를 해서 너비를 구하는 것이 가능.
        // x기준으로 원점에서 먼 순서대로, x값이 같다면 y축이 큰 순서대로 직사각형을 살펴가며 넓이를 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 직사각형
        int n = Integer.parseInt(br.readLine());
        int[][] rectangles = new int[n][2];
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            rectangles[i][0] = Integer.parseInt(st.nextToken()) / 2;
            rectangles[i][1] = Integer.parseInt(st.nextToken()) / 2;
        }
        // x값이 큰 순서대로, 같다면 y값이 큰 순서대로 정렬.
        Arrays.sort(rectangles, (o1, o2) -> {
            if (o1[0] == o2[0])
                return Integer.compare(o2[1], o1[1]);
            return Integer.compare(o2[0], o1[0]);
        });

        // 총 넓이 합
        long ans = 0;
        // 계산된 마지막 x 위치
        long lastLoc = Integer.MAX_VALUE;
        // 높이
        long height = 0;
        for (int i = 0; i < n; i++) {
            // 만약 현재 계산된 높이보다 i번째 사각형의 높이가 더 낮다면
            // 겹쳐져서 계산하지 않아도 된다.
            if (rectangles[i][1] < height)
                continue;

            // 높이 * 마지막 계산된 위치로부터 현재까지의 위치
            ans += height * (lastLoc - rectangles[i][0]);
            // 높이 갱신
            height = Math.max(height, rectangles[i][1]);
            // 마지막 계산 x위치 갱신
            lastLoc = rectangles[i][0];
        }

        // lastLoc ~ 0까지의 넓이 계산
        ans += height * lastLoc;
        // 답 출력
        System.out.println(ans * 4);
    }
}