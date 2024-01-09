/*
 Author : Ruel
 Problem : Baekjoon 14411번 합집합
 Problem address : https://www.acmicpc.net/problem/14411
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14411_합집합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 중심이 (0, 0)에 존재하는 가로 세가로 짝수인 직사각형들이 주어진다.
        // 각 사각형들은 특정 색으로 칠해져있다.
        // 채색된 면적의 전체 넓이는?
        //
        // 정렬 문제
        // 모든 직사각형의 중심이 (0, 0)에 위치하므로 직사각형들은 면적이 서로 겹치는데
        // 이 때 영역의 합집합을 구해야한다.
        // 따라서 가장 x좌표가 먼 직사각형부터 가까운 직사각형까지 살펴보며
        // 높이가 변하는 시점에 따라 넓이를 더해나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // n개의 직시각형
        int n = Integer.parseInt(br.readLine());
        int[][] rectangles = new int[n][];
        for (int i = 0; i < rectangles.length; i++)
            rectangles[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // x의 좌표가 먼 사각형부터 우선적으로 계산하기 위해 정렬한다.
        Arrays.sort(rectangles, (o1, o2) -> Integer.compare(o2[0], o1[0]));

        // 가장 먼 직사각형의 너비
        int width = rectangles[0][0];
        // 높이
        int height = rectangles[0][1];
        long sum = 0;
        for (int i = 1; i < rectangles.length; i++) {
            // 이번 직사각형이 이전 직사각형보다 높이가 같거나 작다면
            // 건너뛴다.
            if (rectangles[i][1] <= height)
                continue;
            
            // 높이가 더 높아졌다면
            // 이전 직사각형부터, 현재 직사각형까지의 거리에 대한 사각형의 너비를 구한다.
            sum += (long) (width - rectangles[i][0]) * height;
            // 현재 직사각형의 너비와 높이 갱신
            width = rectangles[i][0];
            height = rectangles[i][1];
        }
        // 최종적으로 남은 너비와 높이는 가장 안쪽의 사각형에 대해 계산되지 않은 값이므로
        // 해당 값을 더해준다.
        sum += (long) width * height;
        // 답안 출력
        System.out.println(sum);
    }
}