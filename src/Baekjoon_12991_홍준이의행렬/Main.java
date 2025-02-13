/*
 Author : Ruel
 Problem : Baekjoon 12991번 홍준이의 행렬
 Problem address : https://www.acmicpc.net/problem/12991
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12991_홍준이의행렬;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 길이 n의 수열 A, B가 주어진다.
        // 두 수열을 통해 배열을 만드는데, i행 j열 원소는 A수열의 i번째, B수열의 j번째의 수의 곱으로 정의한다.
        // 그 후, 배열의 원소들을 모두 정렬하였다.
        // 이 때, k번째 값은?
        //
        // 이분 탐색 문제
        // 이분 탐색 내에 이분 탐색을 써서 구현하는 문제

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 크기 n의 수열 A, B
        // 구하고자 하는 순서 K
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 두 수열
        long[][] arrays = new long[2][n];
        for (int i = 0; i < arrays.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < arrays[i].length; j++)
                arrays[i][j] = Integer.parseInt(st.nextToken());
            // 어차피 원래 수열에서의 순서가 중요한 것은 아니고
            // 만들어진 배열에서의 오름차순이 중요하기 때문에, 배열을 정렬
            Arrays.sort(arrays[i]);
        }

        // 이분 탐색
        // 가장 작은 원소와 가장 큰 원소
        long start = arrays[0][0] * arrays[1][0];
        long end = arrays[0][n - 1] * arrays[1][n - 1];
        while (start <= end) {
            long mid = (start + end) / 2;

            // mid보다 작은 원소의 개수를 센다.
            int count = 0;
            // A행렬의 모든 원소에 대해
            for (int i = 0; i < arrays[0].length; i++) {
                // 이분 탐색을 통해 곱해서 mid보다 작은 가장 큰 B의 원소를 구한다.
                int s = 0;
                int e = n - 1;
                while (s <= e) {
                    int m = (s + e) / 2;
                    if (arrays[0][i] * arrays[1][m] >= mid)
                        e = m - 1;
                    else
                        s = m + 1;
                }
                // e는 idx이므로 개수는 e + 1개
                count += e + 1;
            }
            
            // 작은 것들의 개수가 count개 이므로
            // mid는 count + 1번째
            // 만약 순서가 k보다 크다면 end의 범위를 mid -1로 줄여 탐색
            if (count + 1 > k)
                end = mid - 1;
            // 같거나 작다면 start를 mid + 1로 줄여 탐색
            else
                start = mid + 1;
        }
        // 최종적으로 end가 가르키는 값이 k번째 원소의 값
        System.out.println(end);
    }
}