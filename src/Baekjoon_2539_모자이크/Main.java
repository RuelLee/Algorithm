/*
 Author : Ruel
 Problem : Baekjoon 2539번 모자이크
 Problem address : https://www.acmicpc.net/problem/2539
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2539_모자이크;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 도화지의 행과 열이 주어진다.
        // 도화지의 특정 지점들에는 색이 잘못 칠해져있다.
        // 이 때 n개의 크기가 같은 정사각형 색종이를 사용하여 잘못 칠해진 점들을 덮고 싶다.
        // 색종이는 도화지의 아랫변에 맞춰서 붙인다.
        // 이 때 사용할 수 있는 가장 작은 크기의 색종이는?
        //
        // 이분 탐색 문제
        // 먼저 도화지를 한번에 뒤덮는 방법은 행과 열 중 큰 값의 색종이로 한번에 뒤덮는 경우이다.
        // 가장 작은 색종이는 행과 열 중 작은 값 중, 잘못 칠해질 점들의 최댓값보다는 같거나 커야한다
        // 위 가장 작은 값과 가장 큰 값을 범위로 이분 탐색을 통해
        // 모든 잘못 칠해진 점을 n개의 색종이로 덮는 최소 크기의 색종이를 찾는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 도화지의 행 r, 열 c
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        // n개의 색종이를 사용한다.
        int n = Integer.parseInt(br.readLine());
        
        // e개의 잘못 칠해진 점들
        int e = Integer.parseInt(br.readLine());
        int[][] spots = new int[e][];
        for (int i = 0; i < spots.length; i++)
            spots[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 열을 기준으로 정렬
        Arrays.sort(spots, Comparator.comparingInt(o -> o[1]));
        
        // 최대 행
        int maxRow = 0;
        for (int[] s : spots)
            maxRow = Math.max(maxRow, s[0]);
        
        // 이분 탐색
        int answer = binarySearch(maxRow, c, n, spots);
        // 정답 출력
        System.out.println(answer);
    }
    
    // 이분 탐색
    // maxRow, maxCol일 때, num개의 색종이로 모든 점들을 뒤덮을 수 있는
    // 최소 색종이의 크기를 찾는다.
    static int binarySearch(int maxRow, int maxCol, int num, int[][] spots) {
        int start = maxRow;
        int end = maxCol;
        while (start < end) {
            int mid = (start + end) / 2;
            if (possible(mid, num, spots))
                end = mid;
            else
                start = mid + 1;
        }
        return start;
    }

    // size 색종이 num개로 모든 spots를 뒤덮을 수 있는지 확인한다.
    static boolean possible(int size, int num, int[][] spots) {
        int covered = 0;
        for (int[] s : spots) {
            if (s[1] <= covered)
                continue;

            if (num-- == 0)
                break;
            covered = s[1] + size - 1;
        }
        return num >= 0 ? true : false;
    }
}