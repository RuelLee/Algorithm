/*
 Author : Ruel
 Problem : Baekjoon 17976번 Thread Knots
 Problem address : https://www.acmicpc.net/problem/17976
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17976_ThreadKnots;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 실이 x축과 평행하게 놓여있다.
        // 각 실에 대해서 시작 지점의 위치와 길이가 주어진다.
        // 실 마다 실 위 한 곳에 매듭을 묶고자 한다.
        // 이 때 매듭 사이의 최소 거리가 가장 멀게 하고 싶을 때 그 길이는?
        //
        // 이분 탐색
        // 이분 탐색을 통해 가능한 매듭 사이의 거리를 찾아나가면 된다.
        // 범위에 대해 자료형 조심
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 실
        int n = Integer.parseInt(br.readLine());

        int[][] lines = new int[n][2];
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 2; j++)
                lines[i][j] = Integer.parseInt(st.nextToken());
        }
        // 시작 지점에 따라 정렬
        Arrays.sort(lines, Comparator.comparingInt(o -> o[0]));
        
        // 이분 탐색
        long start = 0;
        long end = lines[n - 1][0] + lines[n - 1][1];
        while (start <= end) {
            // 최소 거리를 mid로 만드는 것이 가능하다면
            long mid = (start + end) / 2;
            // (mid + 1 ~ end) 범위에서도 가능한지 탐색
            if (possible(mid, lines))
                start = mid + 1;
            // 불가능하다면 (start ~ mid -1) 범위에 대해 탐색
            else
                end = mid - 1;
        }
        // 답 출력
        System.out.println(end);
    }
    
    // 매듭 사이의 최소 거리를 gap으로 만들 수 있는지 계산
    static boolean possible(long gap, int[][] lines) {
        // 최소 매듭의 위치는 무조건 첫 실의 시작 지점인게 좋다.
        long knot = lines[0][0];
        
        // 그 후엔, 현재 매듭의 위치 + gap이 다음 실의 끝 범위를 넘어서는지 확인.
        // 넘는다면 최소 거리를 gap으로 하는 것이 불가능하므로 false 반환
        for (int i = 1; i < lines.length; i++) {
            if (knot + gap > lines[i][0] + lines[i][1])
                return false;
            knot = Math.max(knot + gap, lines[i][0]);
        }
        // 모두 마쳤다면 true 반환
        return true;
    }
}