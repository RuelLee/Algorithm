/*
 Author : Ruel
 Problem : Baekjoon 30972번 닭강정의 전설
 Problem address : https://www.acmicpc.net/problem/30972
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30972_닭강정의전설;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * n 크기의 격자에 1g씩 닭고기가 놓여있다.
        // 각 격자마다 ki,j의 상수를 갖는다.
        // 구매자는 두 점(r1, c1), (r2, c2)를 고르면 해당 사격형 내의 모든 순살을 뭉쳐 튀긴다.
        // 해당 닭강정의 취기를 막아주는 정도는
        // 최외곽은 ki,j * -1, 내부는 * 1을 한 값의 합과 같다.
        // q개의 구매자가 주어질 때, 각각 취기를 막아주는 정도는?
        //
        // 누적합 문제
        // 이차원 누적합만 구할 줄 안다면 쉬운 문제
        // 그냥 누적합 처리하고, 내부를 먼저 구하고, 외부를 구해 내부 - 외부 값을 출력하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n * n 크기의 격자
        int n = Integer.parseInt(br.readLine());

        // 입력과 동시에 누적합 처리
        int[][] psums = new int[n + 1][n + 1];
        StringTokenizer st;
        for (int i = 1; i < psums.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j < psums[i].length; j++)
                psums[i][j] = psums[i][j - 1] + psums[i - 1][j] - psums[i - 1][j - 1] + Integer.parseInt(st.nextToken());
        }
        
        // q개의 쿼리 처리
        int q = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            // 사각형을 나타내는 두 점
            int r1 = Integer.parseInt(st.nextToken());
            int c1 = Integer.parseInt(st.nextToken());
            int r2 = Integer.parseInt(st.nextToken());
            int c2 = Integer.parseInt(st.nextToken());
            
            // 내부의 합
            int inner = psums[r2 - 1][c2 - 1] - psums[r2 - 1][c1] - psums[r1][c2 - 1] + psums[r1][c1];
            // 외부의 합
            int outer = psums[r2][c2] - psums[r2][c1 - 1] - psums[r1 - 1][c2] + psums[r1 - 1][c1 - 1] - inner;
            
            // 내부 - 외부
            sb.append(inner - outer).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}