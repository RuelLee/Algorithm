/*
 Author : Ruel
 Problem : Baekjoon 17386번 선분 교차 1
 Problem address : https://www.acmicpc.net/problem/17386
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 선분교차1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 수학적으로 y = ax + b로 만들어서
        // 교차점을 구하는 방법으로 풀려고 하니 시간 초과가 발생했다.
        // 세 점의 방향성을 나타내는 CCW를 통해 문제를 해결해야했다.
        // 세 점이 주어질 때,
        // (x2 - x1)(y3 - y1) - (y2 - y1)(x3 - x1)의 값이
        // 음수이면 시계방향, 직선이라면 0, 양수라면 반시계 방향이다.
        // 따라서 abc abd의 곱이 음수라면 ab의 직선이 cd 직선을 양분을 함을 알 수 있고,
        // cda cdb의 곱이 음수라면 cd 직선이 ab 직선을 양분함을 알 수 있다.
        // 하지만 여기서는 선분이 아니라 직선이므로 이 때 x 값의 범위가 서로 교차하는지 고려해줘야한다.
        // 또한 값의 범위가 100만인데, 곱셈을 두번하므로, 오버플로우를 조심해야한다!

        Scanner sc = new Scanner(System.in);

        long[][] points = new long[4][2];

        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[i].length; j++)
                points[i][j] = sc.nextInt();
        }
        if (points[0][0] > points[1][0]) {          // b의 x값이 a의 x값보다 작다면 두 위치를 바꿔준다
            long[] temp = points[0];
            points[0] = points[1];
            points[1] = temp;
        }
        if (points[2][0] > points[3][0]) {          // d의 x값이 c의 x값보다 작다면 두 위치를 바꿔준다
            long[] temp = points[2];
            points[2] = points[3];
            points[3] = temp;
        }

        if (points[0][0] > points[3][0] || points[2][0] > points[1][0])     // x값의 범위가 서로 교차하지 않는다면 두 선분은 만날 수 없다.
            System.out.println(0);
        else {
            int abc = calcCCW(points[0], points[1], points[2]);
            int abd = calcCCW(points[0], points[1], points[3]);
            int cda = calcCCW(points[2], points[3], points[0]);
            int cdb = calcCCW(points[2], points[3], points[1]);

            if (abc * abd < 0 && cda * cdb < 0)       // 세 점이 한 직선 위에 존재하는 경우는 주어지지 않는다고 했으니 두 곱이 0보다 작다면 두 선분은 서로 만난다.
                System.out.println(1);
            else
                System.out.println(0);
        }
    }

    static int calcCCW(long[] a, long[] b, long[] c) {
        long result = (b[0] - a[0]) * (c[1] - a[1]) - (b[1] - a[1]) * (c[0] - a[0]);
        if (result > 0)
            return 1;
        else if (result < 0)
            return -1;
        else
            return 0;
    }
}