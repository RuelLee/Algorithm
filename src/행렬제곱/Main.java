package 행렬제곱;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    static HashMap<Long, int[][]> memo;

    public static void main(String[] args) {
        // 피보나치 수열의 방법2와 같은 요령
        // 제곱으로 주어지는 수가 크므로, 메모이제이션을 활용한 분할정복으로 처리하자.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        long b = sc.nextLong();

        int[][] matrix = new int[n][n];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++)
                matrix[i][j] = sc.nextInt() % 1000;
        }

        memo = new HashMap<>();
        memo.put(1L, matrix);

        StringBuilder sb = new StringBuilder();
        for (int[] i : pow(matrix, b)) {
            for (int j : i)
                sb.append(j).append(" ");
            sb.append("\n");
        }
        System.out.println(sb);
    }

    static int[][] pow(int[][] matrix, long b) {
        if (!memo.containsKey(b)) {
            if (b % 2 == 0)
                memo.put(b, multiple(pow(matrix, b / 2), pow(matrix, b / 2)));
            else
                memo.put(b, multiple(multiple(pow(matrix, (b - 1) / 2), pow(matrix, (b - 1) / 2)), pow(matrix, 1)));
        }
        return memo.get(b);
    }

    static int[][] multiple(int[][] matrixA, int[][] matrixB) {
        int[][] answer = new int[matrixA.length][matrixA[0].length];
        for (int i = 0; i < answer.length; i++) {
            for (int j = 0; j < answer[i].length; j++) {
                for (int k = 0; k < matrixB.length; k++)
                    answer[i][j] += (int) (((long) matrixA[i][k] * matrixB[k][j]) % 1000);
                answer[i][j] %= 1000;
            }
        }
        return answer;
    }
}