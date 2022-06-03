/*
 Author : Ruel
 Problem : Baekjoon 1738번 골목길
 Problem address : https://www.acmicpc.net/problem/1738
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1738_골목길;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1번 위치에서 시작해 n번 위치에 도달하고자 한다
        // 지나가는 도로 중간중간에는 행인들이 흘린 금품을 줍거나, 깡패에게 갈취 당한다
        // 1 -> n으로 최대한 유리한 경로로 갈 때, 해당 경로를 출력하라.
        // 최적 경로가 존재하지 않는 경우 -1을 출력한다.
        //
        // 벨만 포드 or SPFA 문제
        // 위 경우, 양의 사이클이 있을 경우, 무한히 금품을 주울 수 있다.
        // SPFA를 통해 n으로 도달하는 경로를 구한 후
        // n으로 도달하는 경로가 있는지와 사이클이 존재하는지 확인.
        // 1. 사이클이 있다면 해당 사이클로부터 n으로 연결되는 경로가 있는지 확인.
        //  1-1. 사이클이 n으로 연결된다면, 최적 경로는 존재하지 않음
        //  1-2. n으로 연결되지 않는다면 해당 사이클 무시
        // 2. 경로가 있다면 경로를 역추적해 출력.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        List<HashMap<Integer, Integer>> roads = new ArrayList<>(n + 1);
        for (int i = 0; i < n + 1; i++)
            roads.add(new HashMap<>());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            // u -> v로 가는 경로가 처음 들어왔거나
            // 이번에 들어온 경로가 좀 더 많은 금품을 획득할 수 있다면 경로 저장.
            if (!roads.get(u).containsKey(v) || roads.get(u).get(v) < w)
                roads.get(u).put(v, w);
        }

        // 큐에 들어있는지 확인
        boolean[] enqueued = new boolean[n + 1];
        Queue<Integer> queue = new LinkedList<>();
        // 시작점은 1
        queue.offer(1);
        // 각 지점에 방문한 횟수.
        int[] visitTime = new int[n + 1];
        // 각 지점에 이를 때까지 얻을 수 있는 최대 이익.
        int[] maxBenefit = new int[n + 1];
        // -1000 * n -1로 초기값 설정.
        Arrays.fill(maxBenefit, -1000 * n - 1);
        // 시작점은 0.
        maxBenefit[1] = 0;
        // 해당 지점에 최대 이익으로 도달하기 위해 이전에 방문해야하는 곳.
        int[] preLocation = new int[n + 1];
        while (!queue.isEmpty()) {
            // 현재 위치.
            int current = queue.poll();
            // 큐 제거 체크.
            enqueued[current] = false;
            // 방문횟수가 n을 넘어간다면 사이클 존재. 종료.
            if (++visitTime[current] > n)
                break;

            // currnet에서 연결된 다음 지점 next
            for (int next : roads.get(current).keySet()) {
                // next에 도달하는 최적 경로가 갱신된다면
                if (maxBenefit[next] < maxBenefit[current] + roads.get(current).get(next)) {
                    // 해당 이익 기록.
                    maxBenefit[next] = maxBenefit[current] + roads.get(current).get(next);
                    // next의 이전 지점으로 current 저장.
                    preLocation[next] = current;
                    // 큐에 next에 들어있지 않다면.
                    if (!enqueued[next]) {
                        // 큐에 삽입하고, 큐 추가 체크.
                        queue.offer(next);
                        enqueued[next] = true;
                    }
                }
            }
        }

        // 사이클로부터 n으로 도달할 수 있는지 확인.
        queue.clear();
        // 사이클로부터 갈 수 있는 지점들 표시.
        boolean[] minusCycle = new boolean[n + 1];
        for (int i = 1; i < visitTime.length; i++) {
            // 사이클이 발생한 곳의 방문횟수는 n + 1.
            // 해당 지점을 큐에 삽입.
            if (visitTime[i] == n + 1) {
                queue.offer(i);
                // 사이클 표시.
                minusCycle[i] = true;
            }
        }
        // BFS로 사이클로부터 도달할 수 있는 지점들 확인.
        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int next : roads.get(current).keySet()) {
                if (!minusCycle[next]) {
                    queue.offer(next);
                    minusCycle[next] = true;
                }
            }
        }

        // 만약 n이 사이클로부터 도달할 수 있거나
        // n으로 도달하는 경로가 존재하지 않는다면 -1 출력.
        if (minusCycle[n] || preLocation[n] == 0)
            System.out.println(-1);
        else {
            // 그렇지 않다면 n으로 도달하는 경로를 역추적해 출력한다.
            Stack<Integer> stack = new Stack<>();
            // n으로부터 preLocation에 기록된 이전 지점들을 추적해간다.
            int loc = n;
            while (loc != 0) {
                stack.push(loc);
                loc = preLocation[loc];
            }
            StringBuilder sb = new StringBuilder();
            // 스택에서 꺼내면서 경로 출력.
            while (!stack.isEmpty())
                sb.append(stack.pop()).append(" ");
            System.out.println(sb);
        }
    }
}