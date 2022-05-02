/*
 Author : Ruel
 Problem : Baekjoon 12781번 PIZZA ALVOLOC
 Problem address : https://www.acmicpc.net/problem/12781
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12781_PIZZAALVOLOC;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // CCW를 활용한 선분 교차 문제
        // (x2 - x1)(y3 - y1) - (x3 - x1)(y2 - y1)의 값이 음수라면 시계방향, 양수라면 반시계방향
        // 따라서 선분 a와 b에 교차를 알기 위해선, x범위, y범위 체크와 a로부터 b의 시작점까지의 ccw 와 a로부터 b의 끝점까지의 ccw의 곱이 -1이라면 교차하고 아니라면 교차하지 않는다.
        Scanner sc = new Scanner(System.in);

        int[][] points = new int[4][2];

        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[i].length; j++)
                points[i][j] = sc.nextInt();
        }

        int ccw1 = calcCCW(points[0], points[1], points[2]);        // a -> b1
        int ccw2 = calcCCW(points[0], points[1], points[3]);        // a -> b2
        int ccw3 = calcCCW(points[2], points[3], points[0]);        // b -> a1
        int ccw4 = calcCCW(points[2], points[3], points[1]);        // b -> a2

        if (checkXRange(points) && checkYRange(points) && (ccw1 * ccw2 < 0 || ccw3 * ccw4 < 0))
            System.out.println(1);
        else
            System.out.println(0);
    }

    static boolean checkXRange(int[][] points) {
        int minA = Math.min(points[0][0], points[1][0]);
        int maxA = Math.max(points[0][0], points[1][0]);

        int minB = Math.min(points[2][0], points[3][0]);
        int maxB = Math.max(points[2][0], points[3][0]);

        return maxB > minA && maxA > minB;      // 한 선분의 x 최대값이 다른 선분의 x의 최소값보단 커야한다. (같다면 교차가 아니라 접하므로 x)
    }

    static boolean checkYRange(int[][] points) {
        int minA = Math.min(points[0][1], points[1][1]);
        int maxA = Math.max(points[0][1], points[1][1]);

        int minB = Math.min(points[2][1], points[3][1]);
        int maxB = Math.max(points[2][1], points[3][1]);

        return maxB > minA && maxA > minB;  // y도 마찬가지
    }

    static int calcCCW(int[] a, int[] b, int[] c) {
        if ((b[0] - a[0]) * (c[1] - a[1]) - (c[0] - a[0]) * (b[1] - a[1]) < 0)  // 우리는 방향에 대한 정보만 필요하므로 큰 수는 없애버리고 음수, 양수만 판별해주자.
            return -1;
        return 1;
    }
}