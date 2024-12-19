/*
 Author : Ruel
 Problem : Baekjoon 15673번 헤븐스 키친 2
 Problem address : https://www.acmicpc.net/problem/15673
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15673_헤븐스키친2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 요리사 일렬로 줄을 서 있다.
        // 각 요리사는 스타성에 대한 점수를 갖고 있으며 이는 -100 ~ 100으로 주어진다.
        // 한 명 이상의 연속한 요리사들을 한 팀으로 하여, 두 팀을 뽑는다.
        // 두 팀의 속한 스타성 점수 합의 곱이 방송의 흥미도가 된다.
        // 흥미도의 가장 큰 값은 얼마인가?
        //
        // 누적합, dp 문제
        // 먼저 왼쪽으로부터 요리사들의 스타성을 누적합으로 구한다.
        // 그 후, 왼쪽에서부터 해당 요리사까지의 범위 내에서 구할 수 있는 스타성 점수 합의 최소, 최대값을 구한다.
        // 마찬가지로 오른쪽에서부터의 누적합과, 스타성 점수 합의 최소, 최대값을 구한다.
        // 그 값을 바탕으로, 양쪽에서 팀을 골랐을 때, 얻을 수 있는 흥미도의 최대값을 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n명의 요리사들
        int n = Integer.parseInt(br.readLine());
        // 각 요리사의 스타성 점수
        int[] cooks = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < cooks.length; i++)
            cooks[i] = Integer.parseInt(st.nextToken());

        // 왼쪽에서부터의 누적합
        int[] psums = new int[n + 1];
        for (int i = 1; i < psums.length; i++)
            psums[i] = psums[i - 1] + cooks[i - 1];
        // 왼쪽에서부터 해당 요리사까지의 범위 내에 구할 수 있는 스타성 합의 최소, 최대값
        int[][] minMaxFromLeft = new int[n + 1][2];
        int min, max;
        min = max = minMaxFromLeft[1][0] = minMaxFromLeft[1][1] = psums[1];
        for (int i = 2; i < minMaxFromLeft.length; i++) {
            minMaxFromLeft[i][0] = Math.min(minMaxFromLeft[i - 1][0], psums[i] - Math.max(max, 0));
            minMaxFromLeft[i][1] = Math.max(minMaxFromLeft[i - 1][1], psums[i] - Math.min(min, 0));

            min = Math.min(min, psums[i]);
            max = Math.max(max, psums[i]);
        }

        // 오른쪽에서부터의 누적합
        Arrays.fill(psums, 0);
        for (int i = psums.length - 2; i >= 0; i--)
            psums[i] = psums[i + 1] + cooks[i];
        // 오른쪽부터 해당 요리사까지의 범위 내에 구할 수 있는 스타성 합의 최소, 최대값
        int[][] minMaxFromRight = new int[n + 1][2];
        min = max = minMaxFromRight[n - 1][0] = minMaxFromRight[n - 1][1] = psums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            minMaxFromRight[i][0] = Math.min(minMaxFromRight[i + 1][0], psums[i] - Math.max(max, 0));
            minMaxFromRight[i][1] = Math.max(minMaxFromRight[i + 1][1], psums[i] - Math.min(min, 0));

            min = Math.min(min, psums[i]);
            max = Math.max(max, psums[i]);
        }

        long answer = Long.MIN_VALUE;
        // 위에서 구한 값을 바탕으로, i까지의 한 팀과 i+1부터의 한 팀에 대해
        // 구할 수 있는 흥미도의 최대값을 구한다.
        // 음수 * 음수로 큰 값이 될 수 있으므로, 최소값끼리의 곱, 최대값끼리의 곱을 구해
        // 큰 값을 계산해나간다.
        for (int i = 1; i < n; i++)
            answer = Math.max(answer, Math.max((long) minMaxFromLeft[i][0] * minMaxFromRight[i][0],
                    (long) minMaxFromLeft[i][1] * minMaxFromRight[i][1]));
        // 답 출력
        System.out.println(answer);
    }
}