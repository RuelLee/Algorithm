/*
 Author : Ruel
 Problem : Jungol 2608번 점 모으기
 Problem address : https://jungol.co.kr/problem/2608
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_2608_점모으기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 행과 열의 크기가 각각 n인 격자 내에 m개의 점이 주어진다.
        // 각 점의 위치가 주어지며, 이 점들을 한 점에 모으고자 한다.
        // 거리는 맨해튼 거리로 측정한다할 때, 모든 점을 한 점으로 모으는데 필요한 최소 거리는?
        //
        // 누적합, 중앙값 문제
        // 맨해튼 거리로 측정하므로, 결국 각 점들의 row와 col을 분리하여 각각 계산해도 된다.
        // 각 row와 col을
        // 작은 위치에서부터, 해당 위치로 옮기는데 필요한 거리의 합과
        // 큰 위치에서 해당 위치로 옮기는데 필요한 거리의 합을 비교하여, 합이 최소인 점을 각각 계산해 더해주면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * n 크기의 격자, m개의 점
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        // row, col을 분리하여 각 위치의 점의 개수를 센다.
        int[] rowCnt = new int[n + 1];
        int[] colCnt = new int[n + 1];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            rowCnt[r]++;
            colCnt[c]++;
        }

        // row 작은 위치 -> 큰 위치로 살펴가며 해당 위치보다 작은 위치의 점들이 해당 위치로 오는데 필요한
        // 맨해튼 거리의 합을 계산한다.
        int[] rowUD = new int[n + 2];
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            rowUD[i] = rowUD[i - 1] + sum;
            sum += rowCnt[i];
        }
        // 반대로 큰 위치에서 -> 작은 위치로 살핀다.
        int[] rowDU = new int[n + 2];
        sum = 0;
        for (int i = n; i > 0; i--) {
            rowDU[i] = rowDU[i + 1] + sum;
            sum += rowCnt[i];
        }

        // col에 대해서도 계산
        int[] colLR = new int[n + 2];
        sum = 0;
        for (int i = 1; i <= n; i++) {
            colLR[i] = colLR[i - 1] + sum;
            sum += colCnt[i];
        }
        int[] colRL = new int[n + 2];
        sum = 0;
        for (int i = n; i > 0; i--) {
            colRL[i] = colRL[i + 1] + sum;
            sum += colCnt[i];
        }

        // row와 col 각각 이동 거리 합이 적은 위치의 합을 찾는다.
        int rowMinSum = Integer.MAX_VALUE;
        int colMinSum = Integer.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            rowMinSum = Math.min(rowMinSum, rowUD[i] + rowDU[i]);
            colMinSum = Math.min(colMinSum, colLR[i] + colRL[i]);
        }
        // 답 출력
        System.out.println(rowMinSum + colMinSum);
    }
}