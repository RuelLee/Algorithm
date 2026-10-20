/*
 Author : Ruel
 Problem : Jungol 12365번 물류 작업 최적화
 Problem address : https://jungol.co.kr/problem/12365
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_12365_물류작업최적화;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 각 시각마다 처리되는 물류의 양이 주어진다.
        // 해당 시각이 포함되는 연속된 구간의 물류 합을 최대화하고자 할때
        // 각 시각이 포함된 최대 연속 구간의 합을 구하라
        //
        // DP 문제
        // 두 개의 배열을 통해
        // fromLeft[i] = i가 포함된 왼쪽 구간의 최대 연속 구간 합.
        // fromRight[i] = i가 포함된 왼쪽 구간의 최대 연속 구간의 합을 구한다.
        // 그 후, j시각마다 fromLeft[i - 1] + j + fromRight[j + 1]을 구해주면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 1 ~ n까지의 시간
        int n = Integer.parseInt(br.readLine());

        // 각 시각에 주어지는 물류
        int[] boxes = new int[n + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++)
            boxes[i] = Integer.parseInt(st.nextToken());

        // fromLeft[i] = i가 포함된 왼쪽 구간의 최대 연속 구간 합.
        int[] fromLeft = new int[n + 2];
        // 왼쪽에서부터 살펴가며, 이전 값을 취할지 말지를 결정해나가며 계산한다.
        for (int i = 1; i <= n; i++)
            fromLeft[i] = Math.max(0, fromLeft[i - 1]) + boxes[i];

        // fromRight[i] = i가 포함된 왼쪽 구간의 최대 연속 구간의 합
        int[] fromRight = new int[n + 2];
        for (int i = n; i > 0; i--)
            fromRight[i] = Math.max(0, fromRight[i + 1]) + boxes[i];

        StringBuilder sb = new StringBuilder();
        // fromLeft[i - 1] + j + fromRight[j + 1]로 각 시각의 답을 구한다.
        for (int i = 1; i <= n; i++)
            sb.append(Math.max(fromLeft[i - 1], 0) + boxes[i] + Math.max(fromRight[i + 1], 0)).append(" ");
        sb.deleteCharAt(sb.length() - 1);
        // 답 출력
        System.out.println(sb);
    }
}