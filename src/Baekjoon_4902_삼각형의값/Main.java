/*
 Author : Ruel
 Problem : Baekjoon 4902번 삼각형의 값
 Problem address : https://www.acmicpc.net/problem/4902
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_4902_삼각형의값;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n줄로 이루어진 삼각형이 주어진다.
        //    △
        //  △▽△
        // △▽△▽△
        // n = 3일 때 위와 같이 주어진다.
        // 윗줄부터 차례대로 수가 주어진다.
        // 부분삼각형 중 1개짜리는 총 9개 존재하며, 4개짜리는 3개, 9개짜리는 1개 주어진다.
        // 이 들의 부분 삼각형들의 최대합을 구하라
        //
        // 누적합, 브루트포스 문제
        // 누적합을 세우고, 삼각형과 역삼각형 모양을 고려해서 가능한 부분삼각형들을 모두 구하고
        // 최대합을 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        String input = br.readLine();
        int testCase = 1;
        while (input != null) {
            StringTokenizer st = new StringTokenizer(input);
            // n줄
            int n = Integer.parseInt(st.nextToken());
            if (n == 0)
                break;

            // 삼각형에 해당하는 배열
            // 줄마다 할당하며, 값을 입력 받으며 누적합 처리
            int[][] triangle = new int[n][];
            for (int i = 0; i < n; i++) {
                triangle[i] = new int[(i + 1) * 2];
                for (int j = 1; j < triangle[i].length; j++)
                    triangle[i][j] = triangle[i][j - 1] + Integer.parseInt(st.nextToken());
            }
            
            // 각 칸의 절댓값이 1000을 넘지 않으므로, 가능한 부분합의 최댓값 중 가장 작은 것은 -999가 단일 한 칸으로 있는 것.
            int max = -1000;
            // 삼각형 모양의 부분 삼각형을 계산
            for (int i = 0; i < triangle.length; i++) {
                for (int j = 1; j < triangle[i].length; j += 2) {
                    // (i, j)를 꼭대기로 갖는 부분삼각형을 모두 구한다.
                    int sum = 0;
                    // 부분 삼각형의 바닥
                    // (j + (k - i) * 2) ~ (j - 1)의 부분합을 sum에 더하고
                    // 해당 값이 최댓값을 갱신하는지 확인
                    for (int k = i; k < triangle.length; k++) {
                        sum += (triangle[k][j + (k - i) * 2] - triangle[k][j - 1]);
                        max = Math.max(max, sum);
                    }
                }
            }
            
            // 역삼각형 형태를 계산
            for (int i = triangle.length - 1; i >= 0; i--) {
                for (int j = 2; j < triangle[i].length; j += 2) {
                    // 누적합
                    int sum = 0;
                    // (j - (i - k) * 2 - 1) ~ j까지의 합을 더해준다.
                    // 역삼각형 형태이므로 범위에 조심
                    for (int k = i; j - (i - k) * 2 > 0 && j < triangle[k].length; k--) {
                        sum += triangle[k][j] - triangle[k][j - (i - k) * 2 - 1];
                        max = Math.max(max, sum);
                    }
                }
            }
            // 답 기록
            sb.append(testCase).append(". ").append(max).append("\n");
            
            // 다음 입력
            input = br.readLine();
            testCase++;
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}