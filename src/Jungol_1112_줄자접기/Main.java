/*
 Author : Ruel
 Problem : Jungol 1112번 줄자접기
 Problem address : https://jungol.co.kr/problem/1112
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1112_줄자접기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 줄 자의 길이와
        // 빨간 점, 파란 점, 노란 점을 가르키는 위치가 각각 두 개씩 주어진다.
        // 각 점들을 두 점끼리 서로 맞닿는 위치로 접고자 한다.
        // 최종적으로 모든 점이 맞닿았을 때, 길이는?
        //
        // 수학
        // 두 점을 기준으로 평균을 내면 해당 부분을 접게 된다.
        // 그 때, 줄자의 길이는 평균부터 양 끝점의 길이 중 더 긴 쪽이 된다.
        // 그리고 나머지 점들의 위치가 평균으로부터 떨어진 거리로 바뀌게 된다.
        // 위 과정을 반복해주면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 줄자의 길
        double length = Integer.parseInt(br.readLine());
        // 각 점의 위치
        // 편의상 정렬도 해준다.
        double[][] points = new double[3][2];
        StringTokenizer st;
        for (int i = 0; i < 3; i++) {
            st = new StringTokenizer(br.readLine());
            points[i][0] = Double.parseDouble(st.nextToken());
            points[i][1] = Double.parseDouble(st.nextToken());
            Arrays.sort(points[i]);
        }

        for (int i = 0; i < 3; i++) {
            // 이미 맞닿아있다면 건너뛴다.
            if (points[i][0] == points[i][1])
                continue;

            // 그렇지 않은 경우, 접게되는 위치를 구하고
            double half = (points[i][0] + points[i][1]) / 2;
            // 접고 나서의 길이 계산
            length = Math.max(half, length - half);
            // 나머지 점들의 위치 보정
            for (int j = i; j < 3; j++) {
                for (int k = 0; k < 2; k++)
                    points[j][k] = Math.abs(points[j][k] - half);
            }
        }
        // 최종 줄 자의 길이 출력
        System.out.printf("%.1f%n", length);
    }
}