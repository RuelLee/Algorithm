/*
 Author : Ruel
 Problem : Baekjoon 32715번 십자 찾기
 Problem address : https://www.acmicpc.net/problem/32715
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_32715_십자찾기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * m 크기의 격자판이 주어진다.
        // 각 격자 칸에 대해 색이 칠해졌다면 1, 그렇지 않다면 0이 주어진다.
        // 이 격자판에서 크기가 k인 십자가의 총 개수를 세고자 한다.
        // 그 개수는?
        // 길이가 1, 2, 3인 십자가
        //              □□■□□
        //      □■□  □□■□□
        //  ■  ■■■  ■■■■■
        //      □■□  □□■□□
        //              □□■□□
        //
        // 누적합 문제
        // 연속한 1의 개수를 누적합을 통해, 열과 행에 대해 계산한다.
        // 그 후, 십자가의 오른쪽 끝과 아래쪽 끝을 기준으로, 길이가 k인 십자가가 만들어지는지 확인한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 격자, 크기 k의 십자가
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(br.readLine());

        // 격자 입력
        int[][] map = new int[n][m];
        for (int i = 0; i < map.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < map[i].length; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }

        // 연속한 검정 칸의 누적합 처리
        int[][] rowPsums = new int[n][m];
        int[][] colPsums = new int[n][m];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 1) {
                    rowPsums[i][j] = (i > 0 ? rowPsums[i - 1][j] : 0) + 1;
                    colPsums[i][j] = (j > 0 ? colPsums[i][j - 1] : 0) + 1;
                }
            }
        }

        int count = 0;
        // 크기가 k인 십자가를 찾는다
        for (int i = 0; i < colPsums.length; i++) {
            for (int j = 0; j < colPsums[i].length; j++) {
                // 열에서 연속한 검정 칸의 길이가 k * 2 + 1 이상이고 (= 오른쪽 끝 십자가)
                // 십자가의 아래쪽에 해당하는 부분에서, 연속한 열의 검정칸의 개수가 k * 2 + 1개 이상이라면
                // 크기가 k인 십자가를 하나 찾을 수 있다.
                if (colPsums[i][j] >= k * 2 + 1 &&
                        i + k < n && j - k >= 0 && rowPsums[i + k][j - k] >= k * 2 + 1) {
                    count++;
                }
            }
        }
        // 답 출력
        System.out.println(count);
    }
}