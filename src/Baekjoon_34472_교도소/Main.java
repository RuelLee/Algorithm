/*
 Author : Ruel
 Problem : Baekjoon 34472번 교도소
 Problem address : https://www.acmicpc.net/problem/34472
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_34472_교도소;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 교도소에 n개의 방이 주어진다.
        // 각 방의 죄수의 수가 주어진다.
        // 각 방은 i ~ n까지 번호가 붙어있고, 1 -> 2, 2 -> 3, ..., n-1 -> n, n -> 1로
        // 단방향 원형 형태로 이루어져있다.
        // 모든 방의 죄수의 수를 같게 만들고자 한다면
        // 최소 이동 횟수는 얼마인가?
        //
        // 그리디 문제
        // 못 풀겠어서 다른 사람의 풀이를 참고했더니 엄청났다.
        // x축을 방의 번호, y 축을 죄수의 수 - average로 잡고, 이를 바닷가 모래라고 생각한다.
        // 그리고서 왼쪽에서부터 팔로 휩쓴다.
        // 그렇다면 평균보다 많은 곳은 모래가 쓸려갈 것이고
        // 평균보다 적은 곳은 쓸려온 모래가 찰 것이다.
        // 그렇다면 중간에 부족해서 모래가 덜 찬 곳은?을 해결하기 위해
        // 1 ~ 2 * n까지 휩쓸어주면 된다.
        // 여러 방법을 생각했지만 단순하고 명확할 풀이법이었다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 방
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 각 방의 죄수의 수
        long[] prison = new long[n];
        // 평균
        long average = 0;
        for (int i = 0; i < prison.length; i++)
            average += prison[i] = Long.parseLong(st.nextToken());
        average /= n;

        // 평균과의 차이
        long[] diffs = new long[n];
        for (int i = 0; i < prison.length; i++)
            diffs[i] = prison[i] - average;

        // 총 이동 횟수
        long count = 0;
        // 현재 여분의 죄수
        long sum = 0;
        // 0번부터 2 * n 전까지 휩쓸며 평균보다 적은 곳을 채운다.
        for (int i = 0; i < 2 * n; i++) {
            // 여분의 죄수를 일단 i % n번 방에 모두 넣는다
            diffs[i % n] += sum;
            sum = 0;

            // 평균보다 넘친다면, 넘친 만큼을 데리고 다음 방에 간다.
            if (diffs[i % n] > 0) {
                sum += diffs[i % n];
                diffs[i % n] = 0;
                count += sum;
            }
        }
        // 총 이동 횟수를 출력
        System.out.println(count);
    }
}