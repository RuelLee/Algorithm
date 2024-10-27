/*
 Author : Ruel
 Problem : Baekjoon 2187번 점 고르기
 Problem address : https://www.acmicpc.net/problem/2187
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2187_점고르기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1 <= r <= 2,000,000, 1 <= c <= 2,000,000 의 좌표 내에 최대 1000개의 점이 주어진다.
        // 점들은 각자 가중치를 갖고 있다.
        // 우리는 세로 1 <= a <= 2,000,000, 가로 1 <= b <= 2,000,000 크기의 직사각형을 쳐
        // 점들을 이 사각형 안에 포함시키고자 한다.
        // 직사각형을 칠 때, 포함된 점들 중 가중치의 최대와 최소의 차가 최대가 되게끔 하고자 할 때
        // 차이값은?
        //
        // 브루트 포스 문제
        // 좌표가 상당히 크므로, 좌표를 기준으로 계산해서는 안된다.
        // 점이 1천개로 적으므로, 점을 기준으로
        // 사각형의 위치에 점이 왼쪽 위, 오른쪽 위, 왼쪽 아래, 오른쪽 아래 위치했을 때의 경우를 각각 상정하여
        // 포함되는 점들의 가중치 최대, 최소 값을 구해나가야한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 점의 개수 n, 직사각형의 세로 a, 가로 b
        int n = Integer.parseInt(st.nextToken());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        
        // 점들
        int[][] points = new int[n][];
        for (int i = 0; i < n; i++)
            points[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        int answer = 0;
        for (int i = 0; i < points.length; i++) {
            // i번째 점을 기준으로 살펴본다
            
            // minMax[0] -> 기준점이 왼쪽 위에 위치할 경우
            // minMax[1] -> 기준점이 오른쪽 위에 위치할 경우
            // minMax[2] -> 기준점이 왼쪽 아래에 위치할 경우
            // minMax[3] -> 기준점이 오른쪽쪽 위에 위치할 경우
            int[][] minMax = new int[4][2];
            for (int[] mm : minMax)
                Arrays.fill(mm, points[i][2]);

            for (int[] point : points) {
                // 기준점이 직사각형의 가장 왼쪽, 가장 위에 오도록 직사각형을 덮는 경우에
                // point가 포함되는지 확인
                if (point[0] >= points[i][0] && point[0] < points[i][0] + a &&
                        point[1] >= points[i][1] && point[1] < points[i][1] + b) {
                    minMax[0][0] = Math.min(minMax[0][0], point[2]);
                    minMax[0][1] = Math.max(minMax[0][1], point[2]);
                }
                
                // 기준점이 직사각형의 가장 오른쪽, 가장 위에 오도록 직사각형을 덮는 경우
                if (point[0] >= points[i][0] && point[0] < points[i][0] + a &&
                        point[1] > points[i][1] - b && point[1] <= points[i][1]) {
                    minMax[1][0] = Math.min(minMax[1][0], point[2]);
                    minMax[1][1] = Math.max(minMax[1][1], point[2]);
                }

                // 기준점이 직사각형의 가장 왼쪽, 가장 아래에 오도록 직사각형을 덮는 경우
                if (point[0] > points[i][0] - a && point[0] <= points[i][0] &&
                        point[1] >= points[i][1] && point[1] < points[i][1] + b) {
                    minMax[2][0] = Math.min(minMax[2][0], point[2]);
                    minMax[2][1] = Math.max(minMax[2][1], point[2]);
                }

                // 기준점이 직사각형의 가장 오른쪽, 가장 아래에 오도록 직사각형을 덮는 경우
                if (point[0] > points[i][0] - a && point[0] <= points[i][0] &&
                        point[1] > points[i][1] - b && point[1] <= points[i][1]) {
                    minMax[3][0] = Math.min(minMax[3][0], point[2]);
                    minMax[3][1] = Math.max(minMax[3][1], point[2]);
                }
            }
            
            // point[i]를 기준으로 한 경우를 모두 계산 완료
            // 해당 직사각형들에서 구할 수 있는 가중치 최대, 최소의 차를 answer에 반영 
            for (int j = 0; j < minMax.length; j++)
                answer = Math.max(answer, minMax[j][1] - minMax[j][0]);
        }
        // 답 출력
        System.out.println(answer);
    }
}