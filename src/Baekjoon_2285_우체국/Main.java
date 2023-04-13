/*
 Author : Ruel
 Problem : Baekjoon 2285번 우체국
 Problem address : https://www.acmicpc.net/problem/2285
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2285_우체국;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 마을이 일직선 상에 있으며, 각 마을의 위치와 인구가 주어진다
        // 우체국을 하나 세우려하는데, 각 사람들이 우체국에 이르는 거리가 최소가 되는 지점에 세우려한다.
        // 그러한 지점이 여러곳이라면 위치가 작은 것을 출력한다.
        // 우체국의 위치는 어디인가
        //
        // 정렬, 누적합 문제
        // 마을에서의 거리가 아니라, 각 사람들로부터 거리의 합이다.
        // 따라서 왼쪽 마을 별로 누적합을 구한다.
        // 그 후 누적합의 인원이 절반이 넘어가는 최초의 마을 위치에 우체국을 세우면 된다.
        // 절반이 넘지 않는다면 그 위치는 나머지 인원들이 오른쪽에서 와야한다는 의미기 때문.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 마을의 위치와 인구를 입력 받는다
        int n = Integer.parseInt(br.readLine());
        int[][] villages = new int[n][];
        for (int i = 0; i < villages.length; i++)
            villages[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 위치에 따라 정렬
        Arrays.sort(villages, Comparator.comparingInt(value -> value[0]));

        // 인구수 누적합
        long[] psum = new long[n];
        psum[0] = villages[0][1];
        for (int i = 1; i < psum.length; i++)
            psum[i] = psum[i - 1] + villages[i][1];

        // 이분 탐색으로 누적 인구수가 전체 인구의 절반을 넘는 최초의 지점을 찾는다.
        int left = 0;
        int right = n - 1;
        while (left < right) {
            int mid = (left + right) / 2;
            if (psum[mid] * 2 < psum[n - 1])
                left = mid + 1;
            else
                right = mid;
        }

        // 해당 마을의 위치에 우체국을 세운다.
        System.out.println(villages[left][0]);
    }
}