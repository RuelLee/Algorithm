/*
 Author : Ruel
 Problem : Baekjoon 7573번 고기잡이
 Problem address : https://www.acmicpc.net/problem/7573
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_7573_고기잡이;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[][] fishes;
    static int n;

    public static void main(String[] args) throws IOException {
        // (1, 1) ~ (n, n) 범위에 모눈종이가 주어지고,
        // 네 변의 길이의 합이 l이 되는 그물을 만들어 펼치려 한다.
        // n마리의 물고기 위치가 주어질 때, 잡을 수 있는 물고기의 최대 마릿수는?
        //
        // 브루트 포스 문제
        // n이 최대 1만, l이 최대 100, m이 최대 100이므로 누적합을 통해 계산하는 건 안된다.
        // 먼저, l로 만들 수 있는 그물의 모든 경우의 수를 따진다.
        // 그 후, 모든 물고기에 대해, 해당 물고기가 그물의 모든 위치에 있을 때, 다른 물고기들은 잡히는지 여부를 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // n * n 크기의 바다
        n = Integer.parseInt(st.nextToken());
        // 그물의 네 변의 길이 합 l, 물고기의 수 m
        int l = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 물고기 위치
        fishes = new int[m][2];
        for (int i = 0; i < fishes.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < fishes[i].length; j++)
                fishes[i][j] = Integer.parseInt(st.nextToken());
        }
        
        int max = 0;
        // 너비 width
        for (int width = 1; width < l / 2; width++) {
            // 높이 height인 그물
            int height = l / 2 - width;
            
            // 현재 물고기가
            for (int[] fish : fishes) {
                // 그물의 왼쪽 위를 (0, 0)으로 볼 때, (i, j)에 물고기가 위치할 경우
                // 다른 물고기들은 얼마나 잡히는지를 계산.
                for (int i = 0; i <= Math.min(height, fish[0] - 1); i++) {
                    for (int j = 0; j <= Math.min(width, fish[1] - 1); j++)
                        max = Math.max(max, fishCount(fish[0] - i, fish[1] - j, width, height));
                }
            }
        }
        // 답 출력
        System.out.println(max);
    }

    // 왼쪽 위의 좌표가 (criteriaR, criteriaC)이고, 그물의 너비가 width, 높이가 height일 때
    // 잡히는 물고기의 수를 계산한다.
    static int fishCount(int criteriaR, int criteriaC, int width, int height) {
        // 범위 밖으로 그물을 치는 경우는 없다 하였다.
        if (criteriaR + height > n || criteriaC + width > n)
            return 0;

        int count = 0;
        for (int[] f : fishes) {
            if (f[0] >= criteriaR && f[0] <= criteriaR + height &&
                    f[1] >= criteriaC && f[1] <= criteriaC + width)
                count++;
        }
        return count;
    }
}