/*
 Author : Ruel
 Problem : Baekjoon 14846번 직사각형과 쿼리
 Problem address : https://www.acmicpc.net/problem/14846
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14846_직사각형과쿼리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * n 크기의 정사각형 행렬이 주어진다.
        // 이 때 다음 쿼리를 수행하는 프로그램을 작성하라
        // x1 y1 x2 y2: 왼쪽 윗칸이 (x1, y1)이고, 오른쪽 아랫칸이 (x2, y2)인 부분 행렬에
        // 포함되어 있는 서로 다른 정수의 개수를 출력한다.
        //
        // 누적합 문제
        // 정답을 한번에 누적합으로 셀 수는 없지만
        // 해당 좌표까지 등장한 수의 개수를 누적합으로 표시할 수 있다.
        // 따라서
        // psums[x][y][i] = 왼쪽 위부터 (x, y)까지 등장한 i의 개수를 센다.
        // 그 후, 쿼리를 각 수에 대해 모두 처리한 뒤, 개수가 1개 이상인 수들의 개수를 출력한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n * n 크기의 2차원 배열
        int n = Integer.parseInt(br.readLine());
        int[][] square = new int[n][];
        for (int i = 0; i < n; i++)
            square[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 각 수의 누적 등장 횟수
        int[][][] psums = new int[n + 1][n + 1][11];
        for (int i = 1; i < psums.length; i++) {
            for (int j = 1; j < psums[i].length; j++) {
                // 0 ~ k까지
                // (i-1, j)까지, (i, j-1)까지의 결과합에서 공통되는 부분인 (i-1, j-1) 부분을 빼준다.
                for (int k = 0; k < psums[i][j].length; k++)
                    psums[i][j][k] = psums[i - 1][j][k] + psums[i][j - 1][k] - psums[i - 1][j - 1][k];
                // i, j에서 새롭게 등장한 수 추가
                psums[i][j][square[i - 1][j - 1]]++;
            }
        }
        
        // 쿼리 처리
        int q = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // (x1, y1) ~ (x2, y2)까지
            int x1 = Integer.parseInt(st.nextToken());
            int y1 = Integer.parseInt(st.nextToken());
            int x2 = Integer.parseInt(st.nextToken());
            int y2 = Integer.parseInt(st.nextToken());

            int count = 0;
            // 10 이하의 수에 대해 몇개나 존재하는지 계산한다
            // (x2, y2)에서 (x1-1, y2), (x2, y1-1) 부분을 빼고, 두 부분에서 공통적으로 빠진 (x1-1, y1-1)을 한번 더 더해준다.
            // 그 후 수의 개수가 양수인지 확인하고, 양수이라면 count 증가.
            for (int j = 0; j < 11; j++)
                count += (psums[x2][y2][j] - psums[x1 - 1][y2][j] - psums[x2][y1 - 1][j] + psums[x1 - 1][y1 - 1][j] > 0 ? 1 : 0);
            // 쿼리에 대한 답변 기록
            sb.append(count).append("\n");
        }
        
        // 전체 답안 출력
        System.out.print(sb);
    }
}