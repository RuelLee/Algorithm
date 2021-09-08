/*
 Author : Ruel
 Problem : Baekjoon 2166번 다각형의 면적
 Problem address : https://www.acmicpc.net/problem/2166
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 다각형의면적;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 벡터의 외적을 활용한 문제
        // CCW가 외적이므로 이를 이용한다.
        // 다각형에서 한 점을 선택한 후, a가 포함되지 않는 선분을 구해, 삼각형을 만든다
        // 위 삼각형을 통해 넓이를 구하는 행위를 모든 다각형의 a를 제외한 모든 선분에 대해 행한다
        // 그 값을 합쳐준다면 다각형의 면적이 나온다
        // 방향에 따라 값의 합이 음수가 나올 수 있으므로 절대값을 취해주자.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int[][] points = new int[n][2];

        for (int i = 0; i < points.length; i++) {
            points[i][0] = sc.nextInt();
            points[i][1] = sc.nextInt();
        }

        double area = 0;

        for (int i = 1; i + 1 < points.length; i++)
            area += calcTriangleArea(points[0], points[i], points[i + 1]);
        area = Math.abs(area);
        area = Math.floor((area * 10) + 0.5) / 10;
        System.out.printf("%.1f", area);
    }

    static double calcTriangleArea(int[] a, int[] b, int[] c) {
        long ccw = ((long) (b[0] - a[0]) * (c[1] - a[1])) - ((long) (b[1] - a[1]) * (c[0] - a[0]));
        return ccw * 0.5;
    }
}