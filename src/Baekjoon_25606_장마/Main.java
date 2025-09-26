/*
 Author : Ruel
 Problem : Baekjoon 25606번 장마
 Problem address : https://www.acmicpc.net/problem/25606
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25606_장마;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n일 동안 비가 내린다.
        // 매일 내리는 비의 양이 주어진다.
        // 비가 내리는 t번째 날에 at만큼의 비가 내리고, 이를 받아 실험실에 보관한다.
        // 실험실에 보관해둔 물은 매일 m만큼씩 증발하고, 남은 물의 양이 m보다 적다면 남은 물이 모두 증발한다.
        // 이 때 q개의 다음 두 쿼리를 처리하라
        // 1 t : 장마 시작 t일 후, 모든 상자에 있는 물의 양
        // 2 t : 장마 시작 t일 후, 모든 상자에서 증발하는 물의 양
        //
        // 차분 배열 문제
        // at만큼의 비는 t+1일부터 t+1 + (at / m)일까지 m씩 증발하고, 그 다음날 물이 남았다면 나머지 양이 증발한다.
        // 이를 통해 날마다 증발하는 물의 양을 차분 배열을 통해 누적합 처리로 구할 수 있다.
        // 이와 날마다 내리는 비의 양을 개별로 누적합 처리하여, 남아있는 물의 양을 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 비가 내리는 n일, 매일 증발하는 물의 양 m, q개의 쿼리
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        
        // 강우량
        int[] rainfall = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            rainfall[i] = Integer.parseInt(st.nextToken());
        
        // 증발량
        int[] evaporation = new int[n + 1];
        for (int i = 0; i < n; i++) {
            if (rainfall[i] < m) {
                // 비가 온 양이 m보다 작다면
                // 하루만에 모두 증발한다.
                evaporation[i + 1] += rainfall[i];
                evaporation[Math.min(i + 2, n)] -= rainfall[i];
            } else {
                // 비가 오는 양이 m보다 같거나 큰 경우
                // 익일 m만큼 증발
                evaporation[i + 1] += m;
                // 그 이후, 강우량 / m일 동안 m씩 증발
                evaporation[Math.min(i + 1 + rainfall[i] / m, n)] -= (m - rainfall[i] % m);
                // 후, m+1일에 나머지 양 증발
                evaporation[Math.min(i + 2 + rainfall[i] / m, n)] -= rainfall[i] % m;
            }
        }
        
        // 증발량 누적합 처리
        for (int i = 1; i < evaporation.length; i++)
            evaporation[i] += evaporation[i - 1];

        // 증발량과 강우량을 바탕으로 전체 물의 양 계산
        for (int i = 1; i < rainfall.length; i++)
            rainfall[i] += rainfall[i - 1] - evaporation[i];
        
        // q개의 쿼리 처리
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            sb.append(Integer.parseInt(st.nextToken()) == 1 ? rainfall[Integer.parseInt(st.nextToken()) - 1] : evaporation[Integer.parseInt(st.nextToken()) - 1]).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}