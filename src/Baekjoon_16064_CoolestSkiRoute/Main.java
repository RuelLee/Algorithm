/*
 Author : Ruel
 Problem : Baekjoon 16064번 Coolest Ski Route
 Problem address : https://www.acmicpc.net/problem/16064
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16064_CoolestSkiRoute;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

class Slope {
    int end;
    int condition;

    public Slope(int end, int condition) {
        this.end = end;
        this.condition = condition;
    }
}

public class Main {
    static List<List<Slope>> slopes;
    static int[] memo;

    public static void main(String[] args) throws IOException {
        // n개의 지점과 지점들을 잇는 m개의 슬로프가 있다.
        // 슬로프마다 컨디션을 갖고 있다.
        // 슬로프는 아래로 이어지기 때문에, 어디서 시작하던 이전에 도달했던 지점에 닿을 수는 없다.
        // 거치는 모든 슬로프의 컨디션 합을 최대로 하고자할 때, 그 값은?
        //
        // BFS, 메모이제이션 문제
        // 모든 지점마다 해당 지점에서 출발할 때, 얻을 수 있는 최대 컨디션 합을 구한다.
        // 이미 구했던 지점을 거쳐간다면, 계산해놨던 결과로 대체한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 지점, m개의 슬로프
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 슬로프 입력
        slopes = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            slopes.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            slopes.get(s).add(new Slope(t, c));
        }

        // 메모이제이션
        memo = new int[n + 1];
        Arrays.fill(memo, -1);
        
        // 각 지점에서 출발할 경우 얻을 수 있는 최대 점수를 계산
        int max = 0;
        for (int i = 1; i <= n; i++)
            max = Math.max(max, maxFromN(i));
        System.out.println(max);
    }
    
    // n 지점에 출발하여 얻을 수 있는 최대 점수 계산
    static int maxFromN(int n) {
        // 처음 방문한 경우일 때
        if (memo[n] == -1) {
            // 값을 0으로 다시 조정
            memo[n] = 0;
            // n에서 갈 수 있는 모든 슬로프 비교
            for (Slope s : slopes.get(n))
                memo[n] = Math.max(memo[n], s.condition + maxFromN(s.end));
        }
        // 계산이 된 경우, 해당 값 반환
        return memo[n];
    }
}