/*
 Author : Ruel
 Problem : Baekjoon 18768번 팀 배정
 Problem address : https://www.acmicpc.net/problem/18768
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18768_팀배정;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 참가자들에 대해 공격 능력치와 방어 능력치가 주어진다.
        // 참가자들을 두 팀으로 나누되, 팀 인원 수의 차이가 k이하가 되게하고
        // a팀의 공격 능력치 합과 b팀의 방어 능력치 합이 최대가 되게끔 하고자 한다.
        // 그렇게 팀을 짰을 때, 두 팀의 능력치 합의 최대값은?
        //
        // 그리디, 정렬 문제
        // 단순하게 모두를 공격 팀, 방어 팀으로 배정했을 때, 큰 능력치 합을 가진 경우를 기준으로
        // 팀원을 상대팀으로 보냈을 때, 두 팀의 능력치 합이 상승하거나, 감소폭이 적은 참가자들을 우선으로 보내면 된다.
        // 만약 감소한다면, 두 팀의 인원 수 차이가 k가 이하가 되도록 최소한의 선수를 보내고
        // 만약 상승한다면 두 팀의 인원 수가 k이하인 동안 계속 보낸다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트 케이스
        int t = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // n명의 선수, 두 팀의 최대 인원 차 k
            int n = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());

            // 모든 선수를 한 팀으로 몰았을 때의 능력치 합
            long[] totals = new long[2];
            int[] a = new int[n];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < a.length; i++)
                totals[0] += (a[i] = Integer.parseInt(st.nextToken()));

            int[] b = new int[n];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < b.length; i++)
                totals[1] += (b[i] = Integer.parseInt(st.nextToken()));
            // 처음에 어느 팀에 모두 배정되어있는가.
            boolean aMax = totals[0] >= totals[1];

            // 상대팀으로 넘겼을 때, 두 팀 능력치 합이 증가하거나, 감소폭이 적은 순서대로 살펴보기 위해
            // 우선순위큐를 사용한다
            // 모든 선수를 공격 팀으로 배정하는 것이, 방어팀으로 배정하는 것보다 더 점수가 높았던 경우
            // 방어 능력치 - 공격 능력치가 큰 순서대로 정렬
            // 반대인 경우는 공격 능력치 - 방어 능력치가 큰 순서대로 정렬
            PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> {
                return aMax ? Integer.compare(b[o2] - a[o2], b[o1] - a[o1]) : Integer.compare(a[o2] - b[o2], a[o1] - b[o1]);
            });
            for (int i = 0; i < n; i++)
                priorityQueue.offer(i);
            
            // 다수인 팀의 인원 수
            int count = n;
            // 두 팀의 능력치 합
            long answer = aMax ? totals[0] : totals[1];
            // 처음 다수인 팀의 선수가 소수인 팀보다 k 명보다 더 많거나
            // 두 팀의 인원수 차가 k 미만인 상태에서 처음 다수인 팀에서 처음 소수인 팀으로 보내는 것이 두 팀의 능력치 합이 상승한다면
            // 상대팀으로 선수를 보낸다.
            while ((2 * count - n) > k ||
                    ((n - 2 * count) < k && (aMax ? b[priorityQueue.peek()] - a[priorityQueue.peek()] : a[priorityQueue.peek()] - b[priorityQueue.peek()]) > 0)) {
                count--;
                answer += (aMax ? b[priorityQueue.peek()] - a[priorityQueue.peek()] : a[priorityQueue.peek()] - b[priorityQueue.peek()]);
                priorityQueue.poll();
            }
            // 최종 두 팀의 능력치 합 기록
            sb.append(answer).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}