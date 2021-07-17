/*
 Author : Ruel
 Problem : Baekjoon 11758번 CCW
 Problem address : https://www.acmicpc.net/problem/11758
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package CCW;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 선분 교차 1에서 사용됐던 CCW 그 자체를 물어보는 문제.
        // CCW = counter clock wise = 반시계방향. 따라서 반시계방향이 양수, 시계방향이 음수.
        // (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 -x1) 임을 외워두자.
        Scanner sc = new Scanner(System.in);

        int[][] points = new int[3][2];
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[i].length; j++)
                points[i][j] = sc.nextInt();
        }
        System.out.println(calcCCW(points));
    }

    static int calcCCW(int[][] points) {
        long answer = ((long) points[1][0] - points[0][0]) * ((long) points[2][1] - points[0][1]) - ((long) points[1][1] - points[0][1]) * ((long) points[2][0] - points[0][0]);
        return answer > 0 ? 1 : (answer == 0 ? 0 : -1);
    }
}