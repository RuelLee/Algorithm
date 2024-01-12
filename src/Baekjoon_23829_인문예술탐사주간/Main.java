/*
 Author : Ruel
 Problem : Baekjoon 23829번 인문예술탐사주간
 Problem address : https://www.acmicpc.net/problem/23829
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23829_인문예술탐사주간;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 나무가 심어져있는 위치가 주어진다.
        // 사진을 q번 찍는데 각 사진의 점수는 사진을 찍은 위치로부터 각 나무까지 거리의 합이다.
        // 각 사진에 대한 점수를 계산하라
        //
        // 이분 탐색, 누적합 문제
        // 먼저 각 나무 위치에 대해 사진 점수를 누적합을 통해 계산한다.
        // 그 후, 이분 탐색을 통해 사진을 찍는 위치와 가장 근사한 나무 위치를 찾고
        // 값을 보정하여 정확한 사진 점수를 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 나무, q개의 사진.
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        
        // 각 나무의 위치
        int[] trees = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            trees[i + 1] = Integer.parseInt(st.nextToken());
        // 정렬
        Arrays.sort(trees);
        
        // 각 나무 위치에서의 사진 점수
        long[] scores = new long[n + 1];
        // 0인 위치일 때의 값을 계산.
        for (int i = 1; i < trees.length; i++)
            scores[0] += trees[i];
        // 나무 위치에 해당하는 곳에서의 점수 계산.
        for (int i = 1; i < scores.length; i++)
            scores[i] = scores[i - 1] + (long) (i * 2 - n - 2) * (trees[i] - trees[i - 1]);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            // 원하는 사진 위치
            int photoLoc = Integer.parseInt(br.readLine());
            // 이분 탐색
            int start = 1;
            int end = n;
            while (start <= end) {
                int mid = (start + end) / 2;
                if (trees[mid] > photoLoc)
                    end = mid - 1;
                else
                    start = mid + 1;
            }
            // end의 위치가 photoLoc을 넘지 않으면서 가장 가까운 나무의 위치.
            // photoLoc과 trees[end] 사이의 거리 보정.
            long answer = scores[end] + (long) (photoLoc - trees[end]) * (2 * end - n);
            // 답 기록
            sb.append(answer).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}