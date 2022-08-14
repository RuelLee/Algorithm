/*
 Author : Ruel
 Problem : Baekjoon 2268번 수들의 합 7
 Problem address : https://www.acmicpc.net/problem/2268
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2268_수들의합7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main2 {
    static int n;
    static long[] fenwickTree;
    static int[] nums;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 입력 처리
        n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        // 각 순서의 수를 기록한다.
        nums = new int[n + 1];
        // 펜윅 트리.
        fenwickTree = new long[n + 1];

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            // 합 쿼리 처리
            if (Integer.parseInt(st.nextToken()) == 0) {
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                sb.append(getSumOneToOrder(Math.max(a, b)) - getSumOneToOrder(Math.min(a, b) - 1)).append("\n");
            // 수정 쿼리 처리
            } else
                modifyValue(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        System.out.print(sb);
    }

    // 합을 1 ~ n까지의 합을 처리하는 메소드.
    static long getSumOneToOrder(int order) {
        long sum = 0;
        // order가 0보다 큰 동안
        while (order > 0) {
            // 펜윅 트리의 값을 더해나간다
            sum += fenwickTree[order];
            // order는 order와 -order를 & 연산한 값만큼 줄여나간다.
            order -= (order & -order);
        }
        return sum;
    }

    // order번째 수를 value로 수정.
    static void modifyValue(int order, int value) {
        // 원래 수와의 차이를 구하고
        int diff = value - nums[order];
        // order번째 수를 value로 수정.
        nums[order] = value;
        // 그리고 펜윅트리의 값을 업데이트한다.
        // order가 fenwickTree의 범위를 벗어나지 않는 동안
        while (order < fenwickTree.length) {
            // diff만큼 펜윅 트리를 갱신한다.
            fenwickTree[order] += diff;
            // order는 order와 -order를 &연산한 만큼씩 늘려간다.
            order += (order & -order);
        }
    }
}