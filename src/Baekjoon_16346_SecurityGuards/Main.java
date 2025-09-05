/*
 Author : Ruel
 Problem : Baekjoon 16346번 Security Guards
 Problem address : https://www.acmicpc.net/problem/16346
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16346_SecurityGuards;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 가드와 q개의 사고가 이차원 좌표로 주어진다.
        // 사고 지점에서 가장 가까운 가드의 거리를 체비쇼프 거리로 출력하라
        //
        // 누적합, 이분탐색 문제
        // 맨날 맨해튼 거리로만 주어지다 체비쇼프 거리라는 걸 처음 들어봤다.
        // (x1, y1), (x2, y2) 사이의 거리를 x y좌표 차이의 차가 큰 값으로 정의한다.
        // = Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2))
        // 따라서 가드를 누적합으로 표시한 뒤
        // 사고가 (a, b)에서 발생했을 때
        // (a + d, b + d) ~ (a - d, b - d)의 사각형 범위 내에 가드가 있는지 여부를 이분탐색을 통해 계산하며
        // 최소 d값을 찾으면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 가드, q개의 사고
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        
        // 가드들을 누적합으로 표시
        int[][] psums = new int[5002][5002];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) + 1;
            int y = Integer.parseInt(st.nextToken()) + 1;
            psums[x][y]++;
        }

        for (int i = 1; i < psums.length; i++) {
            for (int j = 1; j < psums[i].length; j++)
                psums[i][j] += (psums[i][j - 1] + psums[i - 1][j] - psums[i - 1][j - 1]);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            // 사고가 발생한 지점 (a, b)
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) + 1;
            int b = Integer.parseInt(st.nextToken()) + 1;
            
            // 이분 탐색
            int start = 0;
            int end = 5000;
            while (start < end) {
                // (a, b)로부터 체비쇼프 거리가 mid 이내를 탐색한다.
                int mid = (start + end) / 2;
                // 탐색 범위
                // 왼쪽 위
                int leftUpR = Math.max(1, a - mid);
                int leftUpC = Math.max(1, b - mid);
                // 오른쪽 아래
                int rightDownR = Math.min(5001, a + mid);
                int rightDownC = Math.min(5001, b + mid);

                // 해당 범위 내에 가드가 있는지 확인한다.
                if (psums[rightDownR][rightDownC] - psums[rightDownR][leftUpC - 1] - psums[leftUpR - 1][rightDownC] + psums[leftUpR - 1][leftUpC - 1] > 0)
                    end = mid;
                else
                    start = mid + 1;
            }
            // 최소 거리를 기록
            sb.append(end).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}