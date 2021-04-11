package 정수삼각형;

import java.util.Arrays;

public class Solution {
    public static void main(String[] args) {
        int[][] triangle = {{7}, {3, 8}, {8, 1, 0}, {2, 7, 4, 4}, {4, 5, 2, 6, 5}};

        int[][] value = new int[triangle.length][];     // 해당 지점까지 도달하는 최대값을 저장할 공간

        for (int i = 0; i < triangle.length; i++)
            value[i] = new int[triangle[i].length];

        value[0][0] = triangle[0][0];
        for (int i = 1; i < triangle.length; i++) {
            for (int j = 0; j < triangle[i].length; j++) {
                int left = 0;
                int right = 0;

                if (!isOver(i - 1, j - 1, value))   // 2차원 범위를 벗어나지 않는다면 자신의 왼쪽 위까지 도달하는 최대 값을 가져옴.
                    left = value[i - 1][j - 1];
                if (!isOver(i - 1, j, value))   // 2차원 범위를 벗어나지 않는다면, 자신의 오른쪽 위까지 도달하는 최대 가져옴.
                    right = value[i - 1][j];

                value[i][j] = Math.max(left, right) + triangle[i][j];   // value[i][j]에는 left, right 중 높은 값에 triangle[i][j] 값을 더해서 저장.
            }
        }
        int rowMax = value.length - 1;
        int colMax = value[rowMax].length - 1;
        Arrays.sort(value[rowMax]); // 가장 아래 행만 정렬.
        System.out.println(value[rowMax][colMax]);  // 그 중 가장 큰 값인 마지막 값을 가져온다.
    }

    static boolean isOver(int i, int j, int[][] arr) {  // 2차원 배열 범위를 벗어나는지 체크해줌.
        if (i < 0 || j < 0 || i >= arr.length || j >= arr[i].length)
            return true;
        return false;
    }
}