/*
 Author : Ruel
 Problem : Baekjoon 17305번 사탕 배달
 Problem address : https://www.acmicpc.net/problem/17305
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17305_사탕배달;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 3g, 5g 두 종류의 사탕이 총 n개 주어진다.
        // 각 사탕의 당도는 모두 달라 각각 주어진다.
        // 최대 w 그램의 사탕만 담을 수 있다 했을 때,
        // 담을 수 있는 사탕들의 최대 당도 합은?
        //
        // 그리디 + 누적합 문제
        // 먼저 각각 사탕을 무게에 따라 분리한 뒤
        // 당도가 높은 순서대로 누적합을 구한다.
        // 그 뒤, 3g의 모든 사탕 혹은 w를 가득 채울 정도로 3g의 사탕을 모두 담았을 때부터
        // 5g의 모든 사탕 혹은 w에 최대 5g의 사탕들을 담고, 나머지의 무게에 3g의 사탕들을 담았을 때까지
        // 당도 합을 구하며 최대값을 찾는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 사탕과 w의 무게
        int n = Integer.parseInt(st.nextToken());
        int w = Integer.parseInt(st.nextToken());
        
        // 3g의 사탕 내림차순
        PriorityQueue<Integer> w3 = new PriorityQueue<>(Comparator.reverseOrder());
        // 5g의 사탕 내림차순
        PriorityQueue<Integer> w5 = new PriorityQueue<>(Comparator.reverseOrder());
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            if (Integer.parseInt(st.nextToken()) == 3)
                w3.offer(Integer.parseInt(st.nextToken()));
            else
                w5.offer(Integer.parseInt(st.nextToken()));
        }

        // 3g의 사탕 누적합
        long[] psum3 = new long[w3.size() + 1];
        for (int i = 1; i < psum3.length; i++)
            psum3[i] += psum3[i - 1] + w3.poll();
        // 5g의 사탕 누적합
        long[] psum5 = new long[w5.size() + 1];
        for (int i = 1; i < psum5.length; i++)
            psum5[i] += psum5[i - 1] + w5.poll();

        long max = 0;
        // 3g의 사탕을 모두 담거나, w에 넘치지 않도록 가득 담았을 때부터 0까지
        // 남은 무게를 5g 사탕으로 채우고, 그 때의 당도합을 계산한다.
        for (int i = Math.min(w / 3, psum3.length - 1); i >= 0; i--)
            max = Math.max(max, psum3[i] + psum5[Math.min((w - i * 3) / 5, psum5.length - 1)]);

        // 답안 출력
        System.out.println(max);
    }
}