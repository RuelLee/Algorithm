package 행렬의곱셈;

import java.util.Arrays;

public class Solution {
    public static void main(String[] args) {
        // 먼저 기준이되는 answer 2차배열을 만들자
        // 2중 for문으로 각 자리의 값을 채울 것이다.
        // 배열의 곱셈 연산은, 앞배열의 가로열과, 뒷 배열의 세로열의 곱이다.
        // answer[i][j]를 채운다고 하면, arr1배열의 i 가로열과, arr2 배열의 j 세로열을 곱해야한다
        // 이 때 각 열의 값을 하나씩 방문할 for문이 하나 더 필요하다.

        int[][] arr1 = {{1, 4}, {3, 2}, {4, 1}};
        int[][] arr2 = {{3, 3}, {3, 3}};

        int[][] answer = new int[arr1.length][arr2[0].length];

        for (int i = 0; i < answer.length; i++) {   // answer row, arr1 row
            for (int j = 0; j < answer[i].length; j++) {    // answer col, arr2 col
                for (int k = 0; k < arr1[i].length; k++)    // arr1 col, arr2 row
                    answer[i][j] += arr1[i][k] * arr2[k][j];
            }
        }

        for (int[] ints : answer) {
            System.out.println(Arrays.toString(ints));
        }
    }
}