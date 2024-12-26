/*
 Author : Ruel
 Problem : Baekjoon 26107번 Frog Jump
 Problem address : https://www.acmicpc.net/problem/26107
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_26107_FrogJump;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 나뭇잎이 주어진다.
        // 나뭇잎은 가로로 길게 늘어져있으며, 시작 위치와 끝 위치가 주어진다.
        // 개구리는 나뭇잎의 범위가 중복될 경우, 그냥 건너갈 수 있고,
        // 나뭇잎끼리 서로 떨어져있다면 점프를 통해 넘어갈 수 있다.
        // k개의 나뭇잎을 순서대로 방문한다고 했을 때
        // 최소 점프의 길이를 계산하라
        //
        // 누적합, 정렬, 스위핑 문제
        // 먼저, 스위핑을 통해 각 나뭇잎에 도달하기 위해 이전 나뭇잎에서 뛰어야하는 거리를 계산한다.
        // 그 후, 누적합 처리를 해주고
        // k개의 나뭇잎을 순서대로 방문해야하므로, 해당하는 구간의 점프의 개수를 더해 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 나뭇잎, k개의 방문해야하는 나뭇잎
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 나뭇잎의 시작점과 끝점 그리고 원래 순서
        int[][] intervals = new int[n][3];
        for (int i = 0; i < intervals.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 2; j++)
                intervals[i][j] = Integer.parseInt(st.nextToken());
            intervals[i][2] = i;
        }
        // 나뭇잎들을 시작점에 대해 오름차순
        // 시작점이 같다면 끝점에 대해 오름차순으로 정렬한다.
        Arrays.sort(intervals, (o1, o2) -> {
            if (o1[0] == o2[0])
                return Integer.compare(o1[1], o2[1]);
            return Integer.compare(o1[0], o2[0]);
        });

        // 해당 나뭇잎에 도착하려면 필요한 직전 점프의 길이
        long[] jumps = new long[n];
        // 시작 나뭇잎에서는 0
        jumps[intervals[0][2]] = 0;
        // 현재 최대 도달할 수 있는 위치
        int currentMaxLoc = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            // 최대 도달할 수 있는 거리보다, 현재 시작점이 더 클 경우
            // 점프를 해서 뛰어야한다.
            // 해당 거리 기록
            if (intervals[i][0] > currentMaxLoc)
                jumps[intervals[i][2]] = intervals[i][0] - currentMaxLoc;
            // 현재 나뭇잎에 올라섰으므로, 현재 나뭇잎의 끝점이 최대 도달 거리
            currentMaxLoc = Math.max(currentMaxLoc, intervals[i][1]);
        }
        
        // 누적합 처리
        for (int i = 1; i < jumps.length; i++)
            jumps[i] += jumps[i - 1];
        
        // 방문해야하는 나뭇잎의 순서
        int[] visit = new int[k];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < visit.length; i++)
            visit[i] = Integer.parseInt(st.nextToken()) - 1;
        
        // 현재 위치
        int current = 0;
        // 누적 점프 거리
        long answer = 0;
        for (int v : visit) {
            // 이동해야하는 나뭇잎의 번호와 누적합을 가지고서 
            // 필요한 점프 거리를 계산하여 누적한다.
            answer += (jumps[Math.max(v, current)] - jumps[Math.min(v, current)]);
            // 현재 위치
            current = v;
        }
        // 답 출력
        System.out.println(answer);
    }
}