/*
 Author : Ruel
 Problem : Baekjoon 7571번 점 모으기
 Problem address : https://www.acmicpc.net/problem/7571
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_7571_점모으기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * n 격자 위의 m개의 점이 주어진다.
        // 점들을 한 좌표에 모으고자 할 때, 점들의 이동 거리의 최소 합은?
        //
        // 정렬, 누적합 문제
        // 2141번 우체국 문제를 x축, y축에 대해 각각 구해주면 된다.
        // 가장 작은 값부터, 해당 점보다 작은 점들을 해당 점으로 모일 때의 값을 누적합으로 구하고
        // 마찬가지로 가장 큰 값으로부터, 해당 점보다 큰 점들을 해당 점으로 모일 때의 이동 거리 합을 누적합으로 구해
        // 두 누적합의 합이 제일 작은 값으로 모이면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * n 크기의 격자, m개의 점
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 각 점들의 위치
        int[][] points = new int[m][2];
        for (int i = 0; i < points.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 2; j++)
                points[i][j] = Integer.parseInt(st.nextToken());
        }

        long answer = 0;
        for (int i = 0; i < 2; i++) {
            int finalI = i;
            // x축, y축을 기준으로 점을 정렬
            Arrays.sort(points, Comparator.comparing(o -> o[finalI]));
            
            // 가장 작은 값으로부터
            long[] fromLeft = new long[m];
            int lastLoc = points[0][i];
            // j 점보다 작은 점들을 j번 점 위치로 모을 때의 이동거리 합
            for (int j = 1; j < points.length; j++) {
                fromLeft[j] = fromLeft[j - 1] + (lastLoc == points[j][i] ? 0 : (long) j * (points[j][i] - lastLoc));
                lastLoc = points[j][i];
            }

            // 가장 큰 값으로부터
            long[] fromRight = new long[m];
            lastLoc = points[m - 1][i];
            // j번 점보다 큰 점들을 j번 점 위치로 모을 때의 이동거리 합
            for (int j = m - 2; j >= 0; j--) {
                fromRight[j] = fromRight[j + 1] + (lastLoc == points[j][i] ? 0 : (long) (m - 1 - j) * (lastLoc - points[j][i]));
                lastLoc = points[j][i];
            }

            long min = Long.MAX_VALUE;
            // j번 위치로 점들이 모이는 경우의 이동 거리합은 두 누적합의 합
            // 해당 값이 제일 작은 값을 찾는다.
            for (int j = 0; j < m; j++)
                min = Math.min(min, fromLeft[j] + fromRight[j]);
            
            // answer에 더해준다.
            // 위 과정을 x축, y축에 대해 각각 시행
            answer += min;
        }
        // 답 출력
        System.out.println(answer);
    }
}