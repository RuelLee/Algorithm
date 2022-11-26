/*
 Author : Ruel
 Problem : Baekjoon 19538번 루머
 Problem address : https://www.acmicpc.net/problem/19538
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_19538_루머;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 사람이 주어지고, 자신들의 주변인들에 대한 정보가 주어진다.
        // 이 사람들 사이에 루머가 퍼지는데, 주변인들의 반 이상이 해당 루머를 믿을 경우, 자신도 믿는다고 한다.
        // m명의 최초 유포자가 주어질 때
        // 각 사람들이 루머를 믿게되는 시점을 출력하라.
        // 믿지 않는다면 -1을 출력한다.
        //
        // BFS 문제
        // 새롭게 루머를 믿는 사람들에 한해
        // 루머를 믿는 사람의 주변인들을 살펴보며 해당인의 주변인들 중 절반 이상이 루머를 믿는지 살펴보며
        // 루머를 믿는다면 해당 시간을 기록하고 다음 시간에 살펴본다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 입력으로 주어지는 주변인들.
        List<List<Integer>> friends = new ArrayList<>(n + 1);
        friends.add(new ArrayList<>());
        for (int i = 1; i < n + 1; i++) {
            friends.add(new ArrayList<>());
            StringTokenizer st = new StringTokenizer(br.readLine());
            while (st.hasMoreTokens()) {
                int friend = Integer.parseInt(st.nextToken());
                if (friend != 0)
                    friends.get(i).add(friend);
            }
        }

        // m명의 최초 유포자.
        int m = Integer.parseInt(br.readLine());
        int[] minTimes = new int[n + 1];
        Arrays.fill(minTimes, Integer.MAX_VALUE);
        StringTokenizer st = new StringTokenizer(br.readLine());
        Queue<Integer> nextQueue = new LinkedList<>();
        while (st.hasMoreTokens()) {
            int starter = Integer.parseInt(st.nextToken());
            minTimes[starter] = 0;
            nextQueue.offer(starter);
        }

        int time = 1;
        // 주변인들 중 루머를 믿는 사람을 센다.
        int[] nearBy = new int[n + 1];
        while (!nextQueue.isEmpty()) {
            // 현재 믿고 있는 사람들
            // 해당인들의 주변인들에게 nearBy 값을 하나씩 증가시켜준다.
            for (int friend : nextQueue) {
                for (int ff : friends.get(friend))
                    nearBy[ff]++;
            }
            
            Queue<Integer> queue = nextQueue;
            nextQueue = new LinkedList<>();
            // 큐가 빌 때까지
            while (!queue.isEmpty()) {
                int current = queue.poll();

                // current의 주변인들을 살펴본다.
                for (int friend : friends.get(current)) {
                    // 이미 믿고 있다면 건너 뛰고
                    if (minTimes[friend] != Integer.MAX_VALUE)
                        continue;

                    // 그렇지 않는데, friend의 주변인의 절반 이상이 루머를 믿고 있는지 확인한다.
                    // 그렇다면 현재 시간을 기록
                    // 큐에 넣어 다음 시간에 살펴본다.
                    if ((int) ((double) nearBy[friend] / friends.get(friend).size() + 0.5) == 1) {
                        minTimes[friend] = time;
                        nextQueue.offer(friend);
                    }
                }
            }
            // 시간 증가.
            time++;
        }

        // 답안을 작성한다.
        // 초기값 그대로라면 루머를 믿지 않는 사람이므로 -1 출력
        // 다른 사람들은 루머를 믿기 시작한 시간을 기록한다.
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < n + 1; i++)
            sb.append(minTimes[i] == Integer.MAX_VALUE ? -1 : minTimes[i]).append(" ");
        sb.deleteCharAt(sb.length() - 1);
        // 답안 출력.
        System.out.println(sb);
    }
}