/*
 Author : Ruel
 Problem : Baekjoon 9027번 Stadium
 Problem address : https://www.acmicpc.net/problem/9027
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9027_Stadium;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    // n개의 도시가 x축 위에 늘어서있다.
    // 각각의 도시의 위치와 야구 팬의 수가 주어진다.
    // 도시 중 하나에 경기장을 건설하는데,
    // 모든 야구 팬들의 이동거리 합이 최소가 되는 도시에 건설하고자 한다.
    // 건설해야하는 도시의 x축 위치를 출력하라
    //
    // 누적합 문제
    // 두 배열을 통해, 하나는 왼쪽에서 해당 도시에 오는 야구팬들의 총 이동거리 합을 구하고
    // 반대는 오른쪽에서 오는 경우를 구한다.
    // 그리고 두 개의 합이 양쪽에서 오는 모든 야구 팬들의 이동거리 합이 된다.
    // 이 값이 최소인 도시를 찾으면 된다.
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int t = Integer.parseInt(br.readLine());
        // 각각 왼쪽과 오른쪽에서부터 계산하는 누적합
        long[] psumsFromLeft = new long[100_001];
        long[] psumsFromRight = new long[100_001];
        // 마을의 위치와 팬의 수
        int[] locs = new int[100_000];
        int[] pops = new int[100_000];
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            // n개의 도시
            int n = Integer.parseInt(br.readLine());

            // 도시의 위치
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++)
                locs[i] = Integer.parseInt(st.nextToken());
            
            // 팬의 수
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++)
                pops[i] = Integer.parseInt(st.nextToken());
            
            // 왼쪽에서부터 누적합 계산
            // 이전까지의 누적합 + 이전까지의 팬의 수 * 현재 도시와 이전 도시의 거리 차
            long pop = pops[0];
            for (int i = 1; i < n; i++) {
                psumsFromLeft[i] = psumsFromLeft[i - 1] + pop * (locs[i] - locs[i - 1]);
                pop += pops[i];
            }
            
            // 오른쪽에서부터 계산
            pop = pops[n - 1];
            for (int i = n - 2; i >= 0; i--) {
                psumsFromRight[i] = psumsFromRight[i + 1] + pop * (locs[i + 1] - locs[i]);
                pop += pops[i];
            }

            // 두 누적합이 최소인 지점을 찾는다.
            int answer = 0;
            for (int i = 1; i < n; i++) {
                if (psumsFromLeft[i] + psumsFromRight[i] < psumsFromLeft[answer] + psumsFromRight[answer])
                    answer = i;
            }
            // 해당 도시의 위치 기록
            sb.append(locs[answer]).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}