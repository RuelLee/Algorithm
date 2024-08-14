/*
 Author : Ruel
 Problem : Baekjoon 16971번 배열 B의 값
 Problem address : https://www.acmicpc.net/problem/16971
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16971_배열B의값;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * m 크기의 배열 a가 있을 때
        // 다음 방법을 이용하여 (n - 1) * (m - 1) 크기의 배열을 만들 수 있다.
        // B[i][j] = A[i][j] + A[i+1][j] + A[i+1][j+1] + A[i][j+1] (1 ≤ i < N, 1 ≤ j < M)
        // 배열의 값은 배열 모든 원소를 합한 값이다.
        // 임의의 두 행이나 두 열을 교환하여 배열의 값을 최대로 만들고자 할 때
        // 그 값은?
        //
        // 누적합
        // 원래 배열과 새로 생기는 배열 간의 배열의 값에 대해 살펴보면
        // 각 원소가 B의 배열 값에 몇 배가 되느냐를 살펴보면
        //  1 2 ... 2 1
        //  2 4 ... 4 2
        //  2 4 ... 4 2
        //  1 2 ... 2 1
        // 과 같이 표현된다. 
        // 각 꼭지점에 속한 원소는 1번
        // 그 외의 가장자리 행이나 열에 속한 원소는 2번
        // 그 외의 안쪽에 자리 잡은 원소는 4번
        // 따라서 2 ~ n -1 행, 2 ~ m-1열에 대해서는 서로 간의 교환이 무의미하다.
        // 1행이나 n행과 안쪽 행 간의 교환
        // 1열이나 m-1열과 안쪽 열 간의 교환이 생겨야한다.
        // 누적합을 통해 각 행에서의 합과 각 열의 합을 구한 뒤
        // 가장자리 열이나 행과 안쪽 열이나 행을 교환했을 때 차이가 가장 커지는 값을 찾으면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 행렬
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 행렬
        int[][] a = new int[n][];
        for (int i = 0; i < a.length; i++)
            a[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 누적합
        long[][] psums = new long[n][m];
        psums[0][0] = a[0][0];
        for (int i = 1; i < n; i++)
            psums[i][0] = psums[i - 1][0] + a[i][0];
        for (int i = 1; i < m; i++)
            psums[0][i] = psums[0][i - 1] + a[0][i];
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++)
                psums[i][j] = psums[i - 1][j] + psums[i][j - 1] + a[i][j] - psums[i - 1][j - 1];
        }
        
        // 각 행에 대해서 합
        long[] rowSums = new long[n];
        // 첫번째 열과 마지막 열은 한번만, 내부의 원소들의 값은 2배
        rowSums[0] = psums[0][m - 1] * 2 - a[0][0] - a[0][m - 1];
        for (int i = 1; i < rowSums.length; i++)
            rowSums[i] = (psums[i][m - 1] - psums[i - 1][m - 1]) * 2 - a[i][0] - a[i][m - 1];

        // 첫행과 마지막행 중 더 큰 행을 찾는다
        int rowIdx = rowSums[0] > rowSums[n - 1] ? 0 : n - 1;
        // 안쪽 행 중 가장 작은 값을 찾는다.
        int rowTarget = rowIdx;
        for (int i = 1; i < n - 1; i++) {
            if (rowSums[i] < rowSums[rowTarget])
                rowTarget = i;
        }
        // 두 행 간의 차이
        long rowDiff = rowSums[rowIdx] - rowSums[rowTarget];

        // 열에 대해서도 마찬가지로 진행.
        long[] colSums = new long[m];
        colSums[0] = psums[n - 1][0] * 2 - a[0][0] - a[n - 1][0];
        for (int i = 1; i < m; i++)
            colSums[i] = (psums[n - 1][i] - psums[n - 1][i - 1]) * 2 - a[0][i] - a[n - 1][i];
        
        int colIdx = colSums[0] > colSums[m - 1] ? 0 : m - 1;
        int colTarget = colIdx;
        for (int i = 1; i < m - 1; i++) {
            if (colSums[i] < colSums[colTarget])
                colTarget = i;
        }
        long colDiff = colSums[colIdx] - colSums[colTarget];
        
        // 원래 생성되는 행렬 b의 값
        long totalSum = 0;
        for (int i = 0; i < n; i++)
            totalSum += rowSums[i];
        totalSum *= 2;
        totalSum -= (rowSums[0] + rowSums[n - 1]);

        // 만약 행 간의 교환이 열 간의 교환보다 배열의 값이 더 커진다면
        if (rowDiff > colDiff) {
            // 해당하는 rowTarger은 한번 빼주고
            totalSum -= rowSums[rowTarget];
            // 해당하는 rowIdx는 한번 더 더해준다
            totalSum += rowSums[rowIdx];
        } else {
            // 만약 열 간의 교환이 더 크다면 해당하는 값을 보정해준다.
            totalSum -= colSums[colTarget];
            totalSum += colSums[colIdx];
        }
        
        // 답 출력
        System.out.println(totalSum);
    }
}