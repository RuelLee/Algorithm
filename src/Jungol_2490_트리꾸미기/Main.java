/*
 Author : Ruel
 Problem : Jungol 2490번 트리 꾸미기
 Problem address : https://jungol.co.kr/problem/2490
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_2490_트리꾸미기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 노드로 이루어진 트리가 주어진다. 루트는 1번 노드이다.
        // 각 노드마다 p c t가 주어지는데
        // p는 부모노드, c는 자신을 포함 서브트리에 필요한 장식의 개수, t는 자신의 위치에 장식을 설치할 때의 설치 시간
        // 모든 필요한 장식을 설치할 때, 필요한 시간의 총합은?
        //
        // 트리 dp 문제
        // 하위 노드부터 필요한 최소 장식의 수를 채워가며 설치한다.
        // 하위 노드에 장식을 설치하더라도 상위에도 포함되므로, 최소 단위 설치 시간과 전체 장식 시간의 합도 상위노드로 채워나간다.
        // dp[i][0] = 현재 서브 트리에 설치된 장식의 수
        // dp[i][1] = 서브 트리 내에서 장식을 설치하는데 필요한 최소 단위 시간
        // dp[i][2] = 전체 장식 설치 시간

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 노드
        int n = Integer.parseInt(br.readLine());
        List<List<Integer>> child = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            child.add(new ArrayList<>());

        // 각 노드의 정보
        // 부모노드, 필요 장식 개수, 장식 설치 시간
        int[][] nodes = new int[n + 1][3];
        StringTokenizer st = new StringTokenizer(br.readLine());
        nodes[1][0] = Integer.parseInt(st.nextToken());
        nodes[1][1] = Integer.parseInt(st.nextToken());
        nodes[1][2] = Integer.parseInt(st.nextToken());
        for (int i = 2; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++)
                nodes[i][j] = Integer.parseInt(st.nextToken());
            child.get(nodes[i][0]).add(i);
        }

        // BFS로 각 노드의 깊이 측정
        int[] depths = new int[n + 1];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int next : child.get(cur)) {
                depths[next] = depths[cur] + 1;
                queue.offer(next);
            }
        }

        // 깊이의 역순으로, 루트 노드에서 먼 노드부터 처리
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(depths[o2], depths[o1]));
        for (int i = 1; i <= n; i++)
            priorityQueue.offer(i);

        // dp
        // dp[i][0] = 현재 서브 트리에 설치된 장식의 수
        // dp[i][1] = 서브 트리 내에서 장식을 설치하는데 필요한 최소 단위 시간
        // dp[i][2] = 전체 장식 설치 시간
        long[][] dp = new long[n + 1][3];
        // 각 노드의 장식 설치 시간
        for (int i = 1; i <= n; i++)
            dp[i][1] = nodes[i][2];

        while (!priorityQueue.isEmpty()) {
            // 현재 노드
            int cur = priorityQueue.poll();

            if (nodes[cur][1] > dp[cur][0]) {
                // 필요한 장식의 추가 개수
                long num = nodes[cur][1] - dp[cur][0];
                // 추가 개수를 채우고, 시간도 추가
                dp[cur][0] += num;
                dp[cur][2] += dp[cur][1] * num;
            }

            // 루트 노드가 아닌 경우, 부모 노드로 전파
            if (cur != 1) {
                // 장식의 총 개수 누적
                dp[nodes[cur][0]][0] += dp[cur][0];
                // 장식의 최소 설치 시간 전파
                dp[nodes[cur][0]][1] = Math.min(dp[nodes[cur][0]][1], dp[cur][1]);
                // 총 설치 시간 합산
                dp[nodes[cur][0]][2] += dp[cur][2];
            }
        }
        // 루트 노드에서의 총 설치 시간 출력
        System.out.println(dp[1][2]);
    }
}