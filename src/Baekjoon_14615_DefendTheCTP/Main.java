/*
 Author : Ruel
 Problem : Baekjoon 14615번 Defend the CTP!!!
 Problem address : https://www.acmicpc.net/problem/14615
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14615_DefendTheCTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1번 도시부터 n번 도시가 주어지며,
        // m개의 튜브로 도시 간에 매우 빠른 이동이 가능하다.
        // 튜브는 한 방향으로만 이동할 수 있다.
        // 어떠한 빌런이 c번 도시에 폭탄을 설치했다.
        // 슈퍼히어로는 1번 도시에서 출발하여, c번 도시에서 폭탄을 들고, n번 도시로 가 폭탄을 블랙홀에 버려야한다.
        // 해당 과정이 가능하다면 Defend the CTP 그렇지 않다면 Destroyed the CTP를 출력하라
        //
        // BFS 문제
        // 튜브가 연결된 순방향에 따라 1 -> c 도시로 이동가능한지 BFS를 통해 탐색하고
        // 이번에는 튜브를 역방향으로 보며 n -> c로 이동가능한지 BFS를 통해 탐색한다.
        // 그리고 두 경우 모두 true인 경우 폭탄을 제거할 수 있다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 도시, m개의 튜브
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 튜브의 정방향과 역방향
        List<List<Integer>> forward = new ArrayList<>();
        List<List<Integer>> reverse = new ArrayList<>();
        for (int i = 0; i < n + 1; i++) {
            forward.add(new ArrayList<>());
            reverse.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            forward.get(x).add(y);
            reverse.get(y).add(x);
        }

        // 정방향으로 c번 도시로 갈 수 있는지 BFS를 통해 탐색
        Queue<Integer> queue = new LinkedList<>();
        boolean[] oneToBomb = new boolean[n + 1];
        queue.offer(1);
        oneToBomb[1] = true;
        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int next : forward.get(current)) {
                if (!oneToBomb[next]) {
                    queue.offer(next);
                    oneToBomb[next] = true;
                }
            }
        }
        
        // 역방향으로 n번 도시에서 c번 도시로 갈 수 있는지 탐색
        boolean[] bombToN = new boolean[n + 1];
        queue.offer(n);
        bombToN[n] = true;
        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int next : reverse.get(current)) {
                if (!bombToN[next]) {
                    queue.offer(next);
                    bombToN[next] = true;
                }
            }
        }

        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        // t개의 테스트케이스에 대해
        // 두 경우가 모두 가능할 때는 폭탄을 제거할 수 있으며
        // 그렇지 않을 때에는 폭탄을 제거할 수 없다.
        for (int testCase = 0; testCase < t; testCase++) {
            int c = Integer.parseInt(br.readLine());
            sb.append(oneToBomb[c] && bombToN[c] ? "Defend the CTP" : "Destroyed the CTP").append("\n");
        }
        // 답안 출력
        System.out.print(sb);
    }
}