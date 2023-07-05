/*
 Author : Ruel
 Problem : Baekjoon 2381번 최대 거리
 Problem address : https://www.acmicpc.net/problem/2381
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2381_최대거리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // N개의 점이 주어질 때 L1 - metric의 최대 거리를 찾으라.
        // L1 - metric은 |a-c|+|b-d| 으로 계산한다.
        //
        // 정렬, 애드혹 문제
        // 두 점 사이의 관계는
        // 1. ↗과 같이 오른쪽 대각선과
        // 2. ↘과 같은 왼쪽 대각선이 나타날 수 있다.
        // 1의 경우 오른쪽 위의 점이 왼쪽 아래 점보다 x도 y도 모두 크므로
        // x와 y의 합의 차로 구할 수 있다.
        // 2의 경우 왼쪽 위의 점이 오른쪽 아래 점보다 y는 크지만 x는 작다.
        // 따라서 x와 y의 차의 차로 구할 수 있다.
        // 따라서 1은 (x + y)로 정렬하여, 최대값과 최소값의 차로 구하고
        // 2는 (x - y)로 정렬하여 마찬가지로 차를 구한다.
        // 선택된 두 점이 해당하는 관계가 아닐 경우에는
        // 다른 관계의 경우가 더 큰 값이 측정되므로 상관하지 않는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // n개의 점
        int n = Integer.parseInt(br.readLine());
        int[][] points = new int[n][];
        for (int i = 0; i < points.length; i++)
            points[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // (x + y)로 정렬한다.
        Arrays.sort(points, (o1, o2) -> Integer.compare(o1[0] + o1[1], o2[0] + o2[1]));
        int answer = 0;
        // 1의 경우 이므로, 최대값에서 최소값을 빼준다.
        answer = Math.max(answer, points[n - 1][0] - points[0][0] + points[n - 1][1] - points[0][1]);

        // (x - y)로 정렬한다.
        Arrays.sort(points, (o1, o2) -> Integer.compare(o1[0] - o1[1], o2[0] - o2[1]));
        // 2의 경우이므로 (x2 - y2) - (x1 - y1)을 해준다.
        answer = Math.max(answer, points[n - 1][0] - points[0][0] - (points[n - 1][1] - points[0][1]));

        // 계산된 값 출력.
        System.out.println(answer);
    }
}