/*
 Author : Ruel
 Problem : Baekjoon 11049번 행렬 곱셈 순서
 Problem address : https://www.acmicpc.net/problem/11049
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 행렬의곱셈순서;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 이전에 풀었던 파일 합치기와 유사.
        // 행렬을 곱할 경우, a * b, b * c 형태의 행렬 두 개를 곱할 때
        // 결과 행렬은 a * c의 형태를 갖는다. 이를 저장해야하고,
        // 이 때의 연산횟수는 a * b * c 값을 갖는데 이를 저장할 공간 또한 필요하다.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        // dpMatrix[i][j] 는 i ~ j번째 행렬의 곱이 최소값일 때
        // 그 결과 행렬의 형태를 저장할 것이다.
        int[][][] dpMatrix = new int[n][n][2];

        for (int i = 0; i < n; i++) {
            dpMatrix[i][i][0] = sc.nextInt();
            dpMatrix[i][i][1] = sc.nextInt();
        }

        // minOperationCount[i][j] 는 i 번째부터, j 번째까지의 행렬을 곱했을 때, 그 연산횟수의 최솟값을 저장할 것이다.
        int[][] minOperationCount = new int[n][n];
        for (int i = 0; i < minOperationCount.length; i++) {
            for (int j = 0; j < minOperationCount[i].length; j++) {
                if (i == j)     // i와 j가 같다면 주어지는 행렬이므로 0
                    minOperationCount[i][j] = 0;
                else
                    minOperationCount[i][j] = Integer.MAX_VALUE;
            }
        }

        for (int diff = 1; diff < dpMatrix.length; diff++) {        // 차이를 점점 늘려가며 마지막엔 모든 행렬을 곱한 결과까지 도달할 것이다.
            for (int start = 0; start + diff < dpMatrix.length; start++) {      // 시작 지점은 +diff를 했을 때 범위를 넘어서지 않을 때까지.
                // criteria는 마지막에 행해지는 연산을 의미한다.
                // 예를 들어 diff = 2, start = 0일 때, criteria는 0과 1이 존재할 수 있다.
                // criteria가 0이라면, [0][0] * [1][2] 즉, 0번째 행렬과, 1번째, 2번재 행렬이 이미 곱해진 결과 행렬과의 곱셈을 진행할 것이다.
                // criteria가 1이라면, [0][1] * [2][2] 0번째 행렬과 1번째 행렬을 곱하고, 그 후에 2번재 행렬을 곱할 것이다.
                for (int criteria = start; criteria < start + diff; criteria++) {
                    int operationCount = countOperation(dpMatrix[start][criteria], dpMatrix[criteria + 1][start + diff]);
                    operationCount += minOperationCount[start][criteria] + minOperationCount[criteria + 1][start + diff];
                    if (operationCount < minOperationCount[start][start + diff]) {      // 연산횟수의 최솟값이 갱신.
                        minOperationCount[start][start + diff] = operationCount;        // 값을 갱신해주고
                        dpMatrix[start][start + diff] = matrixForm(dpMatrix[start][criteria], dpMatrix[criteria + 1][start + diff]);        // 결과 행렬의 형태도 저장해준다.
                    }
                }
            }
        }
        // 원하는 결과는 모든 행렬을 곱했을 때의 결과이므로, minOperationCount[0][n-1] 값을 출력해주면 된다.
        System.out.println(minOperationCount[0][minOperationCount.length - 1]);
    }

    static int[] matrixForm(int[] a, int[] b) {     // 두 행렬을 곱했을 때, 결과 행렬의 형태를 계산해주는 함수.
        return new int[]{a[0], b[1]};
    }

    static int countOperation(int[] a, int[] b) {       // 두 행렬을 곱했을 때, 연산횟수를 계산해주는 함수.
        return a[0] * a[1] * b[1];
    }
}