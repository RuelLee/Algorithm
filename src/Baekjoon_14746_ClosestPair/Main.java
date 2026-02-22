/*
 Author : Ruel
 Problem : Baekjoon 14746번 Closest Pair
 Problem address : https://www.acmicpc.net/problem/14746
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14746_ClosestPair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // p집합과 q집합의 점들이 각각 n, m개 주어진다.
        // p집합은 y = c1인 직선 위에 존재하고, q집합은 y = c2인 직선 위에 존재한다.
        // 두 집합의 점들 사이의 거리 중 가장 가까운 거리와 동일한 거리를 갖는 쌍의 개수를 구하라
        //
        // 두 포인터 문제
        // 직성 상 위에 있으므로
        // 그냥 두 직선의 각 점들을 정렬하고, 포인터를 처음에 위치시킨 후
        // 두 포인터 중 더 작은 값을 가르키는 포인터를 진행시키며 두 점 사이의 거를 비교한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // p집합의 크기 n, q집합의 크기 m
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        // p집합은 y = c1, q집합은 y = c2 직선 위에 있다.
        int c1 = Integer.parseInt(st.nextToken());
        int c2 = Integer.parseInt(st.nextToken());

        // p집합
        int[] p = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            p[i] = Integer.parseInt(st.nextToken());

        // q집합
        int[] q = new int[m];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++)
            q[i] = Integer.parseInt(st.nextToken());
        // 정렬
        Arrays.sort(p);
        Arrays.sort(q);

        // 두 포인터
        int i = 0, j = 0;
        // 최소 거리
        int minDistance = Integer.MAX_VALUE;
        // 쌍의 개수
        int same = 1;
        // 두 포인터 중 더 작은 값을 가르키는 포인터를 증가시키며
        // 두 점의 거리를 비교한다.
        while (i < n && j < m) {
            int distance = Math.abs(p[i] - q[j]);
            if (distance < minDistance) {
                minDistance = distance;
                same = 1;
            } else if (distance == minDistance)
                same++;

            if (p[i] <= q[j])
                i++;
            else
                j++;
        }
        // 답 출력
        System.out.println((minDistance + Math.abs(c1 - c2)) + " " + same);
    }
}