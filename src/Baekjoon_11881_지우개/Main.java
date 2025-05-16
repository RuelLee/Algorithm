/*
 Author : Ruel
 Problem : Baekjoon 11881번 지우개
 Problem address : https://www.acmicpc.net/problem/11881
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11881_지우개;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static final int LIMIT = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        // 크기 n의 수열 a가 주어진다.
        // 서로 다른 원소 ai, aj, ak로 i-j-k 지우개를 만들고 싶다.
        // ai < aj < ak를 만족한다.
        // 만들 수 있는 지우개들의 부피 총 합은 얼마인가
        // 1,000,000,007 으로 나눈 나머지를 출력하라
        //
        // 누적합 문제
        // n이 최대 10만, 각각의 원소가 최대 10만이므로, int 범위를 넘어감에 주의
        // aj를 고정값으로 두면, ai는 aj보다 작은 값, ak는 aj보다 큰 값으로 된다.
        // 따라서 이를 통해 aj가 중간 값인 주사위 부피의 합을 구하면
        // (aj보다 작은 값들의 합) * aj * (aj보다 큰 값들의 합)로 나타낼 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 길이 n의 수열
        int n = Integer.parseInt(br.readLine());

        // 수열 내에 속한 각각의 원소 개수
        long[] counts = new long[100_001];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            counts[Integer.parseInt(st.nextToken())]++;

        // 누적합 처리
        long[] psums = new long[100_001];
        for (int i = 1; i < psums.length; i++)
            psums[i] = psums[i - 1] + i * counts[i];

        long answer = 0;
        for (int i = 2; i < 100_000; i++) {
            // aj의 길이가 i일 때
            // (i-1)까지의 누적합 * (i * aj의 개수) * (10만이하, i초과의 누적합)
            // 계산 도중에도 long 범위를 넘어갈 수 있음을 주의
            answer += ((psums[i - 1] * (i * counts[i])) % LIMIT) * (psums[100_000] - psums[i]);
            answer %= LIMIT;
        }
        // 답 출력
        System.out.println(answer);
    }
}