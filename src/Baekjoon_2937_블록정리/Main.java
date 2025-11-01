/*
 Author : Ruel
 Problem : Baekjoon 2937번 블록 정리
 Problem address : https://www.acmicpc.net/problem/2937
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2937_블록정리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * n의 보드판 위에 m개의 블럭이 놓여있다.
        // 각 블럭의 좌표가 주어진다.(처음엔 쌓여있을 수 있다)
        // 최소한의 블럭을 옮겨, 직사각형 모양으로 배치하되, 한 블록이 다른 블록 위에 있으며 안된다.
        // 그 때의 최소 이동 블럭의 수를 구하라
        //
        // 브루트 포스, 누적합 문제
        // m개의 블럭을 현재 위치에 따라 누적합을 시킨다.
        // 만약 쌓여있을 경우, 위에 있는 것은 반드시 움직여야하므로, 따로 빼주자.
        // 그 후, m으로 만들 수 있는 직사각형을 모두 시도한다.
        // 누적합에 따라, 이미 배치가 되어있는 블록들은 제외한 나머지 블록들의 개수를 세어주면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * n의 보드판, m개의 블록
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 누적합
        int[][] psums = new int[n + 1][n + 1];
        // 겹쳐있을 경우, 위의 블록은 반드시 움직여야한다.
        int mustMove = 0;
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            // 각 좌표에 블록이 없다면 하나의 블록 추가
            if (psums[r][c] == 0)
                psums[r][c]++;
            // 이미 있다면 mustMove 카운터 추가
            else
                mustMove++;
        }
        
        // 누적합 처리
        for (int i = 1; i < psums.length; i++) {
            for (int j = 1; j < psums[i].length; j++)
                psums[i][j] += psums[i - 1][j] + psums[i][j - 1] - psums[i - 1][j - 1];
        }
        
        // m개의 개수에 따른 직사각형의 한 변의 최대 길이
        int max = Math.min(n, m);
        int answer = Integer.MAX_VALUE;
        // 높이
        for (int height = 1; height <= max; height++) {
            // m이 height로 나누어떨어지지 않거나, m / height가 max보다 커지면 건너뜀.
            if (m % height != 0 || m / height > max)
                continue;
            
            // 너비
            int width = m / height;
            // (i, j)를 왼쪽 위 끝점으로 하는 직사각형을 만들 때, 이동하는 블록의 개수를 구한다.
            for (int i = 1; i + height - 1 < psums.length; i++) {
                for (int j = 1; j + width - 1 < psums[i].length; j++) {
                    // 현재 원하는 위치에 있는 블록의 수
                    int blocks = psums[i + height - 1][j + width - 1] - psums[i + height - 1][j - 1] - psums[i - 1][j + width - 1] + psums[i - 1][j - 1];
                    // 움직여야하는 나머지 블록의 수의 최소값을 찾는다.
                    answer = Math.min(answer, m - blocks);
                }
            }
        }
        // 답 출력
        System.out.println(answer);
    }
}