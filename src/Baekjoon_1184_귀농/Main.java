/*
 Author : Ruel
 Problem : Baekjoon 1184번 귀농
 Problem address : https://www.acmicpc.net/problem/1184
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1184_귀농;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 땅이 n * n 크기로 주어진다.
        // 각 격자마다 수익 Aij가 주어진다. (-1000 < Aij < 1000)
        // 두 개의 직사각형을 나눠 두 사람에게 땅을 나누어주되, 두 직사각형이 한 점에서 만나게끔 하고자 한다.
        // 두 사람에게 같은 수익이 되게끔 땅을 나눠주는 경우의 수는?
        //
        // 누적 합, 해쉬 맵 문제
        // 세 점을 찾아 두 개의 땅으로 나누고자 한다면 2500 C 3으로 해결이 불가능.
        // 한 점을 찍어, 해당 점으로부터 왼쪽 위의 공간과 오른쪽 아래의 공간
        // 오른쪽 위의 공간과 왼쪽 아래의 공간에서 같은 수익이 나는 직사각형을 찾으면 된다.
        // 위의 경우는 2500 * 2500으로 가능
        // 범위의 수익 합은 누적합으로 계산

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n * n 크기의 격자
        int n = Integer.parseInt(br.readLine());

        // 누적합
        int[][] psums = new int[n + 1][n + 1];
        StringTokenizer st;
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= n; j++)
                psums[i][j] = psums[i][j - 1] + psums[i - 1][j] - psums[i - 1][j - 1] + Integer.parseInt(st.nextToken());
        }

        // 해쉬맵을 통해 구한 범위의 수익을 저장하되, 같은 수익이 발생하는 다른 모양의 땅도 있을 수 있으므로
        // 수익의 개수를 센다.
        HashMap<Integer, Integer> hashMap = new HashMap();
        int cnt = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                hashMap.clear();
                // 왼쪽 위의 공간에서 나오는 수익을 구분하여 개수를 해쉬 맵에 저장
                for (int r = 1; r <= i; r++) {
                    for (int c = 1; c <= j; c++) {
                        int extent = psums[i][j] - psums[i][c - 1] - psums[r - 1][j] + psums[r - 1][c - 1];
                        hashMap.put(extent, hashMap.getOrDefault(extent, 0) + 1);
                    }
                }
                // 오른쪽 아래에서 나오는 수익이 해쉬맵에 저장되어있는 값이라면
                // 동일한 수익을 갖는 땅의 개수만큼 cnt 증가
                for (int r = i + 1; r <= n; r++) {
                    for (int c = j + 1; c <= n; c++)
                        cnt += hashMap.getOrDefault(psums[r][c] - psums[r][j] - psums[i][c] + psums[i][j], 0);
                }

                // 위의 과정을 오른쪽 위 공간과 왼쪽 아래 공간에 대해서도 시행
                hashMap.clear();
                for (int r = 1; r <= i; r++) {
                    for (int c = j; c <= n; c++) {
                        int extent = psums[i][c] - psums[i][j - 1] - psums[r - 1][c] + psums[r - 1][j - 1];
                        hashMap.put(extent, hashMap.getOrDefault(extent, 0) + 1);
                    }
                }
                for (int r = i + 1; r <= n; r++) {
                    for (int c = 1; c < j; c++)
                        cnt += hashMap.getOrDefault(psums[r][j - 1] - psums[r][c - 1] - psums[i][j - 1] + psums[i][c - 1], 0);
                }
            }
        }
        // 총 개수 출력
        System.out.println(cnt);
    }
}