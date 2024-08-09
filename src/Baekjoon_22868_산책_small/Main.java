/*
 Author : Ruel
 Problem : Baekjoon 22868번 산책 (small)
 Problem address : https://www.acmicpc.net/problem/22868
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_22868_산책_small;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 정점과 m개의 도로가 주어진다.
        // s에서 시작하여 e를 거쳐 다시 s로 돌아오고자 한다.
        // 경로 내에 동일한 정점을 다시 방문하지 않고 다녀온다.
        // s -> e를 가능 동안 동일한 길이의 경로가 여러개라면
        // 사전순으로 우선하는 것을 선택한다.
        // s -> e -> s의 경로 길이 총합은?
        //
        // BFS 문제
        // 경로를 인접 리스트로 받고, 인접 리스트를 정렬하여 준 후
        // BFS를 통해 탐색하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 정점, m개의 도로
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 인접 리스트
        List<List<Integer>> roads = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            roads.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            roads.get(a).add(b);
            roads.get(b).add(a);
        }
        // 인접 리스트 정렬
        for (List<Integer> list : roads)
            Collections.sort(list);
        
        // s 출발지, e 도착지
        st = new StringTokenizer(br.readLine());
        int s = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());        

        // BFS, s -> e
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(s);
        int[] minDistances = new int[n + 1];
        Arrays.fill(minDistances, Integer.MAX_VALUE);
        // 이전 방문 정점 기록
        int[] pre = new int[n + 1];
        minDistances[s] = 0;
        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int next : roads.get(current)) {
                if (minDistances[next] > minDistances[current] + 1) {
                    minDistances[next] = minDistances[current] + 1;
                    pre[next] = current;
                    queue.offer(next);
                }
            }
        }
        // s -> e의 경로 길이
        int sum = minDistances[e];

        // s -> e의 경로를 추적하여
        // e -> s로 갈 때 방문할 수 없는 정점을 찾는다.
        Stack<Integer> stack = new Stack<>();
        stack.push(e);
        // 방문할 수 없는 정점 표시
        boolean[] blocked = new boolean[n + 1];
        while (pre[stack.peek()] != s) {
            stack.push(pre[stack.peek()]);
            blocked[stack.peek()] = true;
        }

        // BFS, e -> s
        queue = new LinkedList<>();
        queue.offer(e);
        Arrays.fill(minDistances, Integer.MAX_VALUE);
        minDistances[e] = 0;
        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int next : roads.get(current)) {
                // s -> e를 갈 때 방문한 정점은 방문하지 않는다.
                if (!blocked[next] && minDistances[next] > minDistances[current] + 1) {
                    minDistances[next] = minDistances[current] + 1;
                    queue.offer(next);
                }
            }
        }
        
        // 전체 경로의 길이
        sum += minDistances[s];
        // 답 출력
        System.out.println(sum);
    }
}