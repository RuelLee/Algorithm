/*
 Author : Ruel
 Problem : Jungol 8842번 사진 촬영
 Problem address : https://jungol.co.kr/problem/8842
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_8842_사진촬영;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * n 크기의 격자가 주어지고, 각 위치마다 소가 있다.
        // k * k 크기의 사진을 찍으려하는데, 이 때의 매력 지수는 포함된 소의 아름다움의 합이다.
        // q번 (r, c)의 소에게 풀을 먹여 아름다움을 v로 바꾼다.
        // 이 때, 각각에서 찍을 수 있는 사진의 매력 지수 최대 값은?
        //
        // 누적합 문제
        // sums[i][j] = (i, j)를 좌측 상단으로 갖는 k * k 크기 사진의 매력 지수로 정의한다.
        // cows[i][j] = (i, j) 소의 아름다움으로 정의한다.
        // 그 후, 각 쿼리 때마다 sums와 cows를 갱신해가며 최대 매력 지수를 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * n 크기의 격자, k * k 크기의 사진
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // (i, j)를 좌측 상단으로 갖는 k * k 크기의 사진이기 때문에
        // n보다 더 작은 값을 갖는다.
        int SIZE = n - (k - 1);
        int[][] sums = new int[SIZE][SIZE];
        // 각 소의 아름다움
        int[][] cows = new int[n][n];
        // q개의 쿼리
        int q = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        // 현재 최대 매력 지수
        int max = 0;
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            // 소의 위치
            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            // 변경되는 아름다움 값
            int v = Integer.parseInt(st.nextToken());
            // 차이값
            int diff = v - cows[r][c];
            // 값 갱신
            cows[r][c] = v;

            // (r, c)의 소가 포함되는 가장 이른 번호의 사진 시작 범위
            int r1 = Math.max(0, r - (k - 1));
            int c1 = Math.max(0, c - (k - 1));
            // (r, c)의 소가 포함되는 가장 늦은 번호의 사진 범위
            int r2 = Math.min(Math.min(r1 + k, r + 1), SIZE);
            int c2 = Math.min(Math.min(c1 + k, c + 1), SIZE);
            // 값을 갱신하며 최댓값을 계산
            for (int row = r1; row < r2; row++) {
                for (int col = c1; col < c2; col++)
                    max = Math.max(max, sums[row][col] += diff);
            }
            // 답 기록
            sb.append(max).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}