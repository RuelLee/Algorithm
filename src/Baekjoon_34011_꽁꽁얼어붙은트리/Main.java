/*
 Author : Ruel
 Problem : Baekjoon 34011번 꽁꽁 얼어붙은 트리
 Problem address : https://www.acmicpc.net/problem/34011
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_34011_꽁꽁얼어붙은트리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 노드가 n개인 트리 형태가 주어진다.
        // 1번 노드가 루트 노드이며 n-1마리의 고양이들이 모여있다.
        // 트리는 꽁꽁 얼어있어, 각 고양이는 2, 3, 4, ... , n만큼 미끄러진 뒤에 멈춰설 수 있다.
        // 방향을 루트 노드 쪽 
        // 고양이들은 원하는 횟수 만큼 이동을 할 수 있다.
        // 각 고양이가 도달할 수 있는 노드가 가장 많은 것을 출력하라
        //
        // BFS, 트리, 소수 판정, 에라토스테네스의 체
        // 먼저 노드들의 깊이를 측정한다.
        // 깊이 = 미끄러지는 길이로 해당하는 깊이의 노드가 몇 개인지로 계산할 수 있다.
        // 각 고양이들을 정해진 만큼 미끄러지되, 그 횟수는 마음대로 할 수 있으므로
        // 각 미끄러지는 길이의 배수만큼을 이동할 수 있다.
        // 2의 경우 2, 4, 6, 8, ... 4의 경우 4, 8, 12, ...
        // 미끄러지는 길이가 배수인 경우, 작은 쪽이 큰 쪽을 포함하게 되므로
        // 소수인 경우만 세어주면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 노드
        int n = Integer.parseInt(br.readLine());
        
        List<List<Integer>> child = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            child.add(new ArrayList<>());
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n - 1; i++)
            child.get(Integer.parseInt(st.nextToken())).add(i + 2);
        
        // 깊이
        int[] depths = new int[n + 1];
        // 해당 깊이의 노드의 개수
        int[] counts = new int[n];
        // BFS로 각 노드의 깊이 측정
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int next : child.get(current)) {
                counts[depths[next] = depths[current] + 1]++;
                queue.offer(next);
            }
        }

        // 아무 이동을 하지 못하더라도 루트 노드엔 서있을 수 있다.
        int answer = 1;
        // 방문 체크
        // 에라토스테네스의 체로 배수 관계일 때는 세지 않을 것이다.
        boolean[] visited = new boolean[n];
        for (int i = 2; i < n; i++) {
            // 이전에 계산한 수의  배수라면 건너 뛴다.
            if (visited[i])
                continue;
            
            // 방문 가능 노드의 합
            int sum = 0;
            // 깊이가 i * j인 노드들을 방문하며 개수를 누적
            for (int j = 1; i * j < n; j++) {
                // 방문 체크
                visited[i * j] = true;
                sum += counts[i * j];
            }
            // 방문 가능 노드의 수 + 루트 노드로 최대값 갱신
            answer = Math.max(answer, sum + 1);
        }
        // 답 출력
        System.out.println(answer);
    }
}