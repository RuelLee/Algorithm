/*
 Author : Ruel
 Problem : Baekjoon 1995q번 태상이의 훈련소 생활
 Problem address : https://www.acmicpc.net/problem/19951
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_19951_태상이의훈련소생활;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 칸과 그 높이가 주어지고
        // m개의 명령이 주어진다.
        // 명령은 a b k 의 형태이고, a ~ b의 칸의 높이를 k만큼 변화시킨다.
        // 모든 명령이 시행되고 최종 칸들이 높이를 출력하라
        //
        // 간단한 누적합 문제
        // 모든 명령이 시행된 후의 결과를 출력하므로
        // 매번 명령을 일일이 시행하지않고, 모든 명령들의 변화량을 구해 한번에 시행한다.
        // 이 때 누적합을 사용한다
        // 가령 1 5 5가 주어진다면
        // 1 ~ 5까지의 칸을 +5 시키는데
        // 이를 배열에
        // 5 0 0 0 0 -5 로 저장해두면
        // 추후에 순차적으로 누적합을 구해나가면
        // 5 5 5 5 5 0이 된다.
        // 명령은 a칸에 +k, b+1 칸에 -k를 해 표시해두고, 누적합을 통해 각 칸에 명령들에 의한 변화량 총합을 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 각 칸의 높이
        int[] heights = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 변화량
        int[] diffs = new int[n + 1];
        for (int i = 0; i < m; i++) {
            // 명령의 형태는 a b k
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int k = Integer.parseInt(st.nextToken());

            // a칸 부터는 +k
            diffs[a] += k;
            // b + 1칸부터는 +k를 상쇄시켜주기 위해 -k를 한다.
            diffs[b + 1] -= k;
        }

        // 누적합
        int psum = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < heights.length; i++) {
            // 현재 칸의 diffs[i] 더해 누적합을 구하고
            psum += diffs[i];
            // 원래 높이에서 누적합을 더한 만큼이 최종 칸의 높이다.
            // 이를 StringBuilder에 기록
            sb.append(heights[i] + psum).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        // 최종 답안 출력.
        System.out.println(sb);
    }
}