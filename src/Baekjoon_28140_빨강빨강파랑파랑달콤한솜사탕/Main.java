/*
 Author : Ruel
 Problem : Baekjoon 28140번 빨강~ 빨강~ 파랑! 파랑! 달콤한 솜사탕!
 Problem address : https://www.acmicpc.net/problem/28140
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28140_빨강빨강파랑파랑달콤한솜사탕;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 길이 n의 문자열이 주어진다.
        // l과 r이 주어질 때
        // a와 b번째 문자는 R이고, c번째, b번째 문자가 B이며
        // l <= a < b < c < d <= r 를 만족하는 a, b, c, d를 찾고자한다.
        // l과 r이 q번 주어질 때
        // 해당하는 a, b, c, d를 아무거나 출력하거나, 답이 없다면 -1을 출력한다
        //
        // DP문제
        // 로 풀었다. 문제 분류는 이분 탐색으로 되어있었지만.
        // 자신을 제외한 가장 최근에 등장한 R과 B를 기록해두고
        // 그 값을 통해 참조하는 형식으로 쿼리를 처리한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 길이 n의 문자열
        int n = Integer.parseInt(st.nextToken());
        // q개의 쿼리
        int q = Integer.parseInt(st.nextToken());
        String s = br.readLine();
        
        // 자신보다 작은 범위에서 가장 최근에 등장한 R의 idx를 저장
        int[] lastRed = new int[n];
        Arrays.fill(lastRed, -1);
        // 자신보다 작은 범위에서 가장 최근에 등장한 B의 idx 저장
        int[] lastBlue = new int[n];
        Arrays.fill(lastBlue, -1);
        for (int i = 1; i < n; i++) {
            // 이전 문자가 R이라면 해당 위치 기록.
            // 그렇지 않다면 lastRed[i-1]에 있는 값을 가져온다.
            lastRed[i] = (s.charAt(i - 1) == 'R' ? i - 1 : lastRed[i - 1]);
            // B에 대해서도 마찬가지.
            lastBlue[i] = (s.charAt(i - 1) == 'B' ? i - 1 : lastBlue[i - 1]);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());

            int a, b, c, d;
            // d부터 찾아나간다.
            // r이 B이라면 해당 r 그렇지 않다면, lastBlue[r]에 기록된 값을 가져와
            // 해당 값이 존재한다면 c, b, a 순으로 idx을 찾아나간다.
            // 마지막 구한 a가 l보다 같거나 큰지 확인하고
            // 모든 조건을 만족한다면 해당 하는 a, b, c, d값 기록
            if ((d = (s.charAt(r) == 'B' ? r : lastBlue[r])) != -1 && (c = lastBlue[d]) != -1 &&
                    (b = lastRed[c]) != -1 && (a = lastRed[b]) != -1 && a >= l)
                sb.append(a).append(" ").append(b).append(" ").append(c).append(" ").append(d).append("\n");
            else        // 만족하는 r r b b 를 찾지 못했다면 -1 기록
                sb.append(-1).append("\n");
        }
        
        // 전체 답안 출력
        System.out.print(sb);
    }
}