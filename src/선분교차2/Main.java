/*
 Author : Ruel
 Problem : Baekjoon 17387번 선분 교차 2
 Problem address : https://www.acmicpc.net/problem/17387
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 선분교차2;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // CCW 문제!
        // (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1)
        // 값이 음수면 시계방향, 양수면 반시계 방향
        // 선분 l1, l2  두 개의 선분이 있다했을 때
        // l1으로부터 l2의 한 점으로 진행하는 ccw의 값과 또 다른 한 점으로 진행하는 ccw의 곱이 음수를 나타낸다면 l1를 직선으로 나타냈을 때, 이는 l2 선분을 가로지른다.
        // 이 때 직선이라 말한 것은, 서로 닿지 않은 상태에서도 음수 상태가 나오기 때문.
        // 따라서 l2에서 l1으로의 ccw 값의 곱 또한 음수가 된다면, 이는 두 선분(직선이 아닌)이 서로 교차함을 알 수 있다!

        Scanner sc = new Scanner(System.in);

        int[][] points = new int[4][2];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++)
                points[i][j] = sc.nextInt();
        }

        int abc = calcCCW(points[0], points[1], points[2]);
        int abd = calcCCW(points[0], points[1], points[3]);
        int cda = calcCCW(points[2], points[3], points[0]);
        int cdb = calcCCW(points[2], points[3], points[1]);

        if (checkRange(points) && (abc * abd <= 0 && cda * cdb <= 0))       // x, y 범위가 겹치는 구간이 있어야하며, ccw의 곱들이 0보다 같거나 작아야한다(두 선분이 만나는 경우도 인정하기로 했으므로)
            System.out.println(1);
        else
            System.out.println(0);
    }

    static boolean checkRange(int[][] points) {         // 서로 교차하는 범위가 있는지 범위 체크
        int aXMin = Math.min(points[0][0], points[1][0]);
        int aXMax = Math.max(points[0][0], points[1][0]);
        int aYMin = Math.min(points[0][1], points[1][1]);
        int aYMax = Math.max(points[0][1], points[1][1]);

        int bXMin = Math.min(points[2][0], points[3][0]);
        int bXMax = Math.max(points[2][0], points[3][0]);
        int bYMin = Math.min(points[2][1], points[3][1]);
        int bYMax = Math.max(points[2][1], points[3][1]);

        return aXMax >= bXMin && bXMax >= aXMin && aYMax >= bYMin && bYMax >= aYMin;
    }

    static int calcCCW(int[] a, int[] b, int[] c) {         // CCW 계산
        long ccw = (long) (b[0] - a[0]) * (c[1] - a[1]) - (long) (b[1] - a[1]) * (c[0] - a[0]);
        return ccw > 0 ? 1 : ccw == 0 ? 0 : -1;
    }
}