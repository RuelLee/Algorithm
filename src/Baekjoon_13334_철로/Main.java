/*
 Author : Ruel
 Problem : Baekjoon 13334번 철로
 Problem address : https://www.acmicpc.net/problem/13334
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13334_철로;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 통근하는 사람들의 집과 사무실의 위치가 주어진다
        // 최대 길이 d의 철로를 건설하여 최대한 많은 사람들이 통근하는데 이용하도록 하고자 한다
        // 이 때 통근하는 사람의 최대 수는?
        //
        // 정렬을 이용한 스위핑 문제
        // 우리는 d라는 범위 안에 최대한 많은 사람을 넣어야한다
        // 통근하는 사람들을 끝점을 토대로 정렬을 한 후, 순차적으로 살펴본다
        // 이 때 시작점을 기준으로 정렬하는 우선순위큐에 담아간다
        // 그러면서 현재의 끝점과 우선순위큐의 시작점의 차이가 d보다 크다면, 우선순위큐에서 해당 시작점을 가진 사람을 빼준다
        // 이렇게 한다면 끝점을 토대로 거리가 d이내인 최대한 많은 사람들이 우선순위큐에 담아지며, 이 때 최대 크기를 기록하면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 통근하는 사람들의 좌표가 주어진다.
        int[][] workers = new int[n][2];
        for (int i = 0; i < workers.length; i++)
            workers[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).sorted().toArray();
        int d = Integer.parseInt(br.readLine());

        // 끝 점을 기준으로 정렬한다.
        Arrays.sort(workers, Comparator.comparingInt(o -> o[1]));

        // 시작점의 좌표가 작을수록 먼저 나오는 우선순위큐.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> workers[o][0]));
        int maxCount = 0;
        for (int i = 0; i < n; i++) {
            // i번째 사람 추가.
            priorityQueue.offer(i);
            // i번째 사람의 끝점과 우선순위큐에 들어있는 사람들의 시작점의 차이가 d보다 크다면 우선순위큐에 들어있는 사람을 빼준다
            while (!priorityQueue.isEmpty() && workers[i][1] - workers[priorityQueue.peek()][0] > d)
                priorityQueue.poll();
            // 그 때의 크기를 저장
            maxCount = Math.max(maxCount, priorityQueue.size());
        }
        // 거리 d이내 철도를 건설했을 때, 통근이 가능한 가장 많은 사람의 수.
        System.out.println(maxCount);
    }
}