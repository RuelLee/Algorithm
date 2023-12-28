/*
 Author : Ruel
 Problem : Baekjoon 23327번 리그전 오브 레전드
 Problem address : https://www.acmicpc.net/problem/23327
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23327_리그전오브레전드;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 팀이 참가하는 리그전이 주어진다.
        // 팀이 너무 많아 l ~ r까지의 팀을 포함하는 디비전으로 분리하려한다.
        // 리그전의 재미는 리그전에서 치르는 모든 경기의 재미 합이다.
        // 인기가 1 2 3인 팀이 참가했다면 1 * 2 + 2 * 3 + 1 * 3한 11이 답이다.
        // n이 최대 10만, 재미를 구할 디비전의 수가 q가 최대 20만 주어질 때
        // 각 디비전들의 재미들을 구하라.
        //
        // 누적합 문제
        // 각 팀의 인기합과, 해당 팀까지의 재미합을 누적합으로 구해놓는다.
        // 그 후 l, r에 해당하는 값이 들어오면
        // 재미합[r]은 1 ~ r까지의 팀들이 만들어내는 재미합이므로
        // 먼저 1 ~ l-1까지의 팀들이 만들어내는 재미합인 재미합[l-1]을 한다.
        // 재미합[l-1]은 1 ~ l-1까지의 팀들만이 만들어내는 재미합이다.
        // 따라서 이는 (1 ~ l-1)의 팀 중 하나와 (l ~ r)까지의 팀이 만들어내는 재미합은 고려되지 않았다.
        // 이는 인기합[l-1] * (인기합[r] - 인기합[l-1])로 구할 수 있고 이 값을 빼면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 팀 q개의 디비전
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        
        // 각 팀의 인기
        int[] popularity = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 인기 합
        int[] popSums = new int[n + 1];
        // 재미 합
        long[] funSums = new long[n + 1];
        for (int i = 1; i < popSums.length; i++) {
            popSums[i] = popSums[i - 1] + popularity[i - 1];
            funSums[i] = funSums[i - 1] + (long) popSums[i - 1] * popularity[i - 1];
        }

        StringBuilder sb = new StringBuilder();
        // q개의 디비전 처리
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            // l ~ r 까지의 팀으로 디비전을 구성.
            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());

            // 재미합[r]에서
            // 1 ~ l-1까지의 팀으로만 만드는 재미합[l-1]을 빼주고
            // (1 ~ l-1) 중 하나의 팀과 (l ~ r)의 팀 중 하나가 만들어내는 재미합을 빼준 값이
            // (l ~ r)까지의 디비전의 재미합.
            long answer = funSums[r] - funSums[l - 1] - (long) (popSums[r] - popSums[l - 1]) * popSums[l - 1];
            sb.append(answer).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}