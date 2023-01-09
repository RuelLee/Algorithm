/*
 Author : Ruel
 Problem : Baekjoon 2118번 두 개의 탑
 Problem address : https://www.acmicpc.net/problem/2118
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2118_두개의탑;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 지점이 존재한다.
        // 이 지점들은 볼록 n각형을 이루고 있다.
        // 두 지점의 거리는 시계방향과 반시계 방향 거리 중 짧은 쪽의 거리이다.
        // 지점들 가장 먼 두 곳을 골라 탑을 세우고자 한다.
        // 두 탑의 거리는?
        //
        // 누적합과 두 포인터 문제
        // i, i +1 지점 사이의 거리들이 주어진다.
        // 이를 통해 첫번째 지점부터 마지막 지점까지의 거리를 누적합을 구한다.
        // 이를 통해 시계 방향, 반시계 방향 거리를 구한다.
        // 그리고  한 지점과 다른 지점에 대한 두 개의 포인터를 가지고서 
        // 두 거리가 멀어지는 한 포인터들 사이의 거리를 늘리며 최대 거리를 찾는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int[] cw = new int[n + 1];
        
        // 시계 방향 누적합
        for (int i = 0; i < cw.length - 1; i++)
            cw[i + 1] = cw[i] + Integer.parseInt(br.readLine());

        // 두 포인터
        int maxDistance = 0;
        int j = 1;
        // 첫번째 지점부터 마지막 지점까지
        for (int i = 0; i < cw.length - 1; i++) {
            // 어차피 한 바퀴 돌 경우, i, j 값만 서로 바뀔 뿐 다른 거리가 계산되지는 않는다.
            // 따라서 j를 최대 n번 지점까지만 증가시킨다.
            // j값은 두 지점의 거리가 멀어지는 한 증가 시킨다.
            while (j < cw.length - 1 &&
                    calcDistance(i, j, cw) < calcDistance(i, j + 1, cw))
                j++;
            // 두 지점 사이의 거리가 최대값을 갱신했는지 확인.
            maxDistance = Math.max(maxDistance, calcDistance(i, j, cw));
        }

        System.out.println(maxDistance);
    }

    // a 지점과 b 지점 사이의 거리를 계산한다.
    static int calcDistance(int a, int b, int[] cw) {
        // 시계 방향 거리
        // 두 지점 중 나중 지점에서 먼저 나온 지점의 누적합 차를 구하면 나온다.
        int cwDistance = cw[Math.max(a, b)] - cw[Math.min(a, b)];
        // 첫번째 ~ 다시 첫번째 지점까지의 전체 거리 합에서
        // 위에서 구한 시계 방향 거리를 제외하면 반시계 방향 거리가 나온다.
        int ccwDistance = cw[cw.length - 1] - cwDistance;
        
        // 두 값 중 적은 값이 두 지점 사이의 거리
        return Math.min(cwDistance, ccwDistance);
    }
}