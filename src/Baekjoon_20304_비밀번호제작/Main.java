/*
 Author : Ruel
 Problem : Baekjoon 20304번 비밀번호 제작
 Problem address : https://www.acmicpc.net/problem/20304
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20304_비밀번호제작;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 0 ~ n까지 설정가능한 비밀번호가 주어진다.
        // 두 비밀번호 사이의 안전 거리는 두 비밀번호를 이진법으로 표현했을 때, 서로 다른 비트의 개수로 정한다.
        // 비정상적인 로그인 시도가 m번 있었고, 해당 시도의 비밀번호들이 주어진다.
        // 이 때 시도된 비밀번호들과 안전 거리를 최대로 하는 새로운 비밀번호를 설정하고자할 때
        // 안전 거리의 값은?
        //
        // BFS, 비트마스킹 문제
        // m개의 시도들에 대해, 거리 0에서부터 시작하여
        // 없는 비트에 대해서는 더해주고, 있는 비트에 대해서는 빼주며 거리는 하나씩 늘려가며
        // 가능한 최대 안전 거리의 비밀번호를 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 0 ~ n까지의 비밀번호 범위, m번의 시도
        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());

        // 각 비밀번호와 시도한 비밀번호 사이와의 최소 안전 거리
        int[] distances = new int[n + 1];
        Arrays.fill(distances, Integer.MAX_VALUE);
        Queue<Integer> queue = new LinkedList<>();

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            // 시도한 비밀번호
            int password = Integer.parseInt(st.nextToken());
            // 안전 거리는 0
            distances[password] = 0;
            // 큐에 담아 BFS 탐색
            queue.offer(password);
        }

        // 최대 안전 거리
        int max = 0;
        while (!queue.isEmpty()) {
            // 비밀 번호
            int current = queue.poll();

            // n 범위 내에서 가능한 비트를 비교한다.
            for (int i = 0; (1 << i) <= n; i++) {
                int bit = (1 << i);
                // current에 bit가 포함되어 있고, current에서 bit를 제외한 비밀번호까지의 안전 거리가
                // 최솟값을 갱신하는 경우.
                // 해당 값이 max를 갱신하는지 확인하고 거리에 기록한 후, 큐에 추가
                if ((current & bit) != 0 && distances[current - bit] > distances[current] + 1) {
                    max = Math.max(max, distances[current - bit] = distances[current] + 1);
                    queue.offer(current - bit);
                }

                // current에 bit가 포함되지 않은 경우
                // bit를 추가했을 때, n보다 같거나 작고, 거리가 최솟값을 갱신하는 경우
                if ((current & bit) == 0 && (current + bit) <= n && distances[current + bit] > distances[current] + 1) {
                    max = Math.max(max, distances[current + bit] = distances[current] + 1);
                    queue.offer(current + bit);
                }
            }
        }
        // 찾은 최대 안전 거리 출력
        System.out.println(max);
    }
}