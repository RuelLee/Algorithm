/*
 Author : Ruel
 Problem : Baekjoon 27728번 개구리와 쿼리
 Problem address : https://www.acmicpc.net/problem/27728
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_27728_개구리와쿼리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * n 크기의 2차원 배열로 이루어진 연못에 q마리의 개구리들이 모여있다.
        // q마리의 개구리들의 위치는 (sx, sy)이다
        // 개구리들은 현재 칸에서 y가 1 증가하는 오른쪽 방향으로 이동할 수 있고, 그 때는, 현재 칸에 적힌 수만큼 시간이 소모된다.
        // 모든 개구리들은 x가 감소하는 방향, 위쪽으로 l이상 점프할 수 있다.
        // 점프하는데는 시간이 소모되지 않는다.
        // 다음과 같이
        // sx sy l이 주어질 때, 해당 개구리가 육지(x, n+1)에 도달하는 최소 시간을 구하라.
        //
        // 누적합, 누적최솟값 문제
        // 먼저 해당 지점부터 우측으로 쭉 이동한다는 가정하에 오른쪽에서부터 누적합을 구한다.
        // 그리고, 점프를 할 수 있는데, l이상만큼 해야하므로, 1 ~ sx - l까지는 모두 점프가 가능하다.
        // 따라서 세로로는 누적최솟값을 구해나간다.
        // 그 후, 각 개구리가 현재 바로 점프를 할지, 혹은 현재 sx에서 일정 칸만큼 진행을 한 후, 점프를 할지를 모두 고려해여 값을 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * n 크기의 연못
        // q개의 쿼리
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        
        // 오른쪽에서부터 누적합
        int[][] psums = new int[n][n];
        for (int i = 0; i < psums.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < psums.length; j++)
                psums[i][j] = Integer.parseInt(st.nextToken());
            for (int j = psums.length - 2; j >= 0; j--)
                psums[i][j] += psums[i][j + 1];
        }
        
        // 누적 최솟값
        int[][] minColPsums = new int[n][n];
        for (int j = 0; j < minColPsums[0].length; j++)
            minColPsums[0][j] = psums[0][j];
        for (int i = 1; i < minColPsums.length; i++) {
            for (int j = 0; j < minColPsums[i].length; j++)
                minColPsums[i][j] = Math.min(psums[i][j], minColPsums[i - 1][j]);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            // 현재 개구리의 위치 (sx, sy)
            int sx = Integer.parseInt(st.nextToken()) - 1;
            int sy = Integer.parseInt(st.nextToken()) - 1;
            // 최소 점프 길이 l
            int l = Integer.parseInt(st.nextToken());
            
            // 현재 위치에서 점프를 하지 않을 경우, 최소 시간
            int min = psums[sx][sy];
            // j칸까지는 sx에서 진행을 하고,
            // j칸에서 점프를 하여, j부터는 새로운 x에서 진행하는 경우
            for (int j = sy; j < minColPsums.length; j++)
                min = Math.min(min, psums[sx][sy] - psums[sx][j] + minColPsums[sx - l][j]);
            sb.append(min).append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }
}