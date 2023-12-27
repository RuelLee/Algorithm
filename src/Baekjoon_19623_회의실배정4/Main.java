/*
 Author : Ruel
 Problem : Baekjoon 19623번 회의실 배정 4
 Problem address : https://www.acmicpc.net/problem/19623
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_19623_회의실배정4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 회의 시작 시간, 종료 시간, 참여 인원이 주어진다.
        // 한 개의 회의실에서 회의들을 진행한다고 할 때,
        // 가장 많은 인원이 회의에 참여하고자할 때 그 인원은?
        //
        // 좌표 압축, DP 문제
        // 회의 시작 시간과 종료 시간의 범위가 2^32으로 매우 크지만
        // n은 최대 10만이므로, 값을 압축하여 처리한다.
        // dp는 dp[시간]으로 해당 시간까지 진행할 수 있는 최대 회의 참여 인원으로 한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 회의
        int n = Integer.parseInt(br.readLine());
        
        // 회의 들의 정보
        int[][] meetings = new int[n][];
        // 좌표 압축
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < meetings.length; i++) {
            meetings[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            priorityQueue.offer(meetings[i][0]);
            priorityQueue.offer(meetings[i][1]);
        }
        // 회의는 시작 순서대로 살펴본다.
        Arrays.sort(meetings, Comparator.comparingInt(o -> o[0]));

        HashMap<Integer, Integer> compress = new HashMap<>();
        while (!priorityQueue.isEmpty()) {
            int current = priorityQueue.poll();
            if (!compress.containsKey(current))
                compress.put(current, compress.size() + 1);
        }
        
        // 각 시간까지 참여하는 회의의 최대 인원
        int[] dp = new int[compress.size() + 1];
        // 첫번째 회의로 초기값 설정.
        dp[compress.get(meetings[0][1])] = Math.max(dp[compress.get(meetings[0][1])], meetings[0][2]);
        int idx = 0;
        // 두번째 회의 부터 살펴본다.
        for (int i = 1; i < meetings.length; i++) {
            // 시작시간과 종료 시간
            int start = compress.get(meetings[i][0]);
            int end = compress.get(meetings[i][1]);
            // 이전 회의까지 진행된 결과들 중 최대 값을 start 위치까지 적용시킨다.
            if (idx < start) {
                for (int j = idx; j < start; j++)
                    dp[j + 1] = Math.max(dp[j + 1], dp[j]);
                idx = start;
            }
            // i번째 회의를 진행할 때, end 시간까지 참여한 회의 인원을 최대로 할 수 있는지 확인.
            dp[end] = Math.max(dp[end], dp[start] + meetings[i][2]);
        }
        // idx를 마지막까지 이동시켜 가장 큰 값이 마지막에 위치하도록 한다.
        for (int j = idx; j < dp.length - 1; j++)
            dp[j + 1] = Math.max(dp[j + 1], dp[j]);
        
        // 참여할 수 있는 회의의 최대 인원 출력
        System.out.println(dp[compress.size()]);
    }
}