/*
 Author : Ruel
 Problem : Jungol 16765번 학교를 쌓아올리는 포닉스
 Problem address : https://jungol.co.kr/problem/16765
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_16765_학교를쌓아올리는포닉스;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 건물의 높이와 너비가 주어진다.
        // 이 때 스카이라인은 이 직사각형들을 이어붙인 직각다각형이다.
        // 공사 한 번으로 원하는 건물의 높이를 1 올릴 수 있다.
        // 스카이라인의 둘레를 최소화하고자 할 때, 그 때의 둘레와 공사의 횟수는?
        //
        // 단조 증가 그래프 문제
        // 결국 너비의 합은 변하지 않으므로 너비의 합은 따로 구해 나중에 *2를 해 더해준다.
        // 문제는 이웃한 건물 간의 높이 차이이다.
        // 건물의 높이 3 1 3으로 주어진다면 1의 오목한 부분 때문에 둘레의 합이 4가 증가한다.
        // 공사는 통해 이 오목한 부분을 없애주면 된다.
        // 왼쪽에서부터 살펴보며, 이전에 등장한 가장 높은 건물보다 더 낮은 건물이 등장한 경우, 공사를 통해 같은 높이를 맞춰준다.
        // 마찬가지로 오른쪽에서부터도 시행한다.
        // 그리고 한 건물씩 살펴보며, 양쪽에서 단조 증가로 온 경우, 스카이라인 둘레의 합과 공사 횟수를 구해 비교하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 건물
        int n = Integer.parseInt(br.readLine());
        int[] buildings = new int[n];
        StringTokenizer st;
        // 너비 합
        long widthSum = 0;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            // 각 빌딩의 높이
            buildings[i] = Integer.parseInt(st.nextToken());
            widthSum += Integer.parseInt(st.nextToken());

        }

        // 왼쪽에서부터 단조 증가를 계산
        // fromLeft[i][0] = i번째 건물까지 왼쪽에서부터 봐왔을 때, 가장 높은 건물의 높이
        // fromLeft[i][1] = i번째 건물까지 시행한 공사의 횟수
        long[][] fromLeft = new long[n][2];
        fromLeft[0][0] = buildings[0];
        for (int i = 1; i < n; i++) {
            fromLeft[i][0] = Math.max(fromLeft[i - 1][0], buildings[i]);
            fromLeft[i][1] = fromLeft[i - 1][1] + (buildings[i] < fromLeft[i - 1][0] ? (fromLeft[i - 1][0] - buildings[i]) : 0);
        }

        // 오른쪽에서부터도 계산
        long[][] fromRight = new long[n][2];
        fromRight[n - 1][0] = buildings[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            fromRight[i][0] = Math.max(fromRight[i + 1][0], buildings[i]);
            fromRight[i][1] = fromRight[i + 1][1] + (buildings[i] < fromRight[i + 1][0] ? (fromRight[i + 1][0] - buildings[i]) : 0);
        }

        // 양쪽의 값을 통해 답을 구한다.
        long[] ans = new long[2];
        Arrays.fill(ans, Long.MAX_VALUE);
        for (int i = 0; i < n; i++) {
            // 스카이 라인 중 세로 길이의 합
            // i번째 건물까지 양쪽에서 단조 증가로 증가해온 경우, 둘 중 높은 높이의 * 2
            long sum = Math.max(fromLeft[i][0], fromRight[i][0]) * 2;
            // 공사 횟수
            long cntSum = fromLeft[i][1] + fromRight[i][1];

            // 최솟값인지 확인
            if (ans[0] > sum) {
                ans[0] = sum;
                ans[1] = cntSum;
            } else if (ans[0] == sum)
                ans[1] = Math.min(ans[1], cntSum);
        }

        // 마지막으로 가로 길이의 합을 더해준다.
        ans[0] += widthSum * 2;
        // 답 출력
        System.out.println(ans[0] + " " + ans[1]);
    }
}