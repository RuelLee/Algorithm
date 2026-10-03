/*
 Author : Ruel
 Problem : Jungol 1912번 미로 탐색
 Problem address : https://jungol.co.kr/problem/1912
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1912_미로탐색;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 방과 m개의 연결 정보가 주어진다.
        // 1번 방부터 시작하여, 현재 방에 연결되어있는 미방문인 가장 작은 번호의 방에 방문한다.
        // 더 이상 미방문인 방이 없다면 이전 방으로 돌아가길 반복하며 모든 방을 방문한다할 때
        // 방들의 방문 순서는?
        //
        // DFS
        // dfs 탐색과 동일하다. 단지 번호가 작은 방을 우선해서 방문할 뿐.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 방, m개의 연결 정보
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 각 방의 연결 정보를 우선순위큐로 정렬해서 받는다.
        List<PriorityQueue<Integer>> connections = new ArrayList<>();
        for (int i = 0; i <= n; i++)
            connections.add(new PriorityQueue<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            connections.get(a).offer(b);
            connections.get(b).offer(a);
        }

        // 방문 여부 체크
        boolean[] visited = new boolean[n + 1];
        StringBuilder sb = new StringBuilder();
        // 스택을 이용해 DFS 방문한다
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        while (!stack.isEmpty()) {
            // 첫 방문인 방인 경우
            if (!visited[stack.peek()]) {
                // 체크 후, 표시
                visited[stack.peek()] = true;
                sb.append(' ').append(stack.peek());
            }

            // pq를 찾아, 연결된 미방문인 가장 작은 방을 찾는다.
            PriorityQueue<Integer> pq = connections.get(stack.peek());
            while (!pq.isEmpty() && visited[pq.peek()])
                pq.poll();

            // 다음 방이 있는 경우
            if (!pq.isEmpty())
                stack.push(pq.poll());
            else        // 없다면 뒤로 돌아간다.
                stack.pop();
        }
        sb.deleteCharAt(0);
        // 답 출력
        System.out.println(sb);
    }
}