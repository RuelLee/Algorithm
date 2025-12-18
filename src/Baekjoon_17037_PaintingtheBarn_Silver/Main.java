/*
 Author : Ruel
 Problem : Baekjoon 17037번 Painting the Barn (Silver)
 Problem address : https://www.acmicpc.net/problem/17037
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17037_PaintingtheBarn_Silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1001 * 1001 공간이 주어진다.
        // n개의 사각형 모양으로 페인트를 칠한다.
        // k번 덧칠해진 영역의 넓이를 구하라
        //
        // 2차원 누적합, 차분 배열 트릭, imos
        // 사각형에 대해 2차원 누적합 처리를 해주면 된다.
        // 0 0 0 0
        // 0 1 1 0
        // 0 1 1 0
        // 0 0 0 0 모양으로 누적합으로 칠하기 위해선
        //
        // 0 -1 0 1
        // 0 0 0 0
        // 0 1 0 -1
        // 0 0 0 0 으로 하면 누적합 처리를 하며 위 모양으로 바뀐다.
        // 즉 왼쪽 아래 점에 1을 찍고, 아래 오른쪽 끝 점과 위 왼쪽 끝 점엔 -1
        // 위 오른쪽 끝에는 1을 다시 찍어준다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 사각형, k번 칠해진 영역을 구한다.
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 누적합
        int[][] psums = new int[1002][1002];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int x1 = Integer.parseInt(st.nextToken());
            int y1 = Integer.parseInt(st.nextToken());
            int x2 = Integer.parseInt(st.nextToken());
            int y2 = Integer.parseInt(st.nextToken());

            // 왼쪽 아래 점
            psums[x1][y1]++;
            // 왼쪽 위, 오른쪽 아래 점
            psums[x2][y1]--;
            psums[x1][y2]--;
            // 오른쪽 위 점
            psums[x2][y2]++;
        }

        int cnt = 0;
        // (0, i), (i, 0)에 대해서 미리 선처리
        for (int i = 1; i < 1001; i++) {
            if ((psums[0][i] += psums[0][i - 1]) == k)
                cnt++;
            if ((psums[i][0] += psums[i - 1][0]) == k)
                cnt++;
        }

        // 그 외의 경우
        for (int i = 1; i < psums.length; i++) {
            for (int j = 1; j < psums[i].length; j++) {
                if ((psums[i][j] += (psums[i - 1][j] + psums[i][j - 1] - psums[i - 1][j - 1])) == k)
                    cnt++;
            }
        }
        // 답 출력
        System.out.println(cnt);
    }
}