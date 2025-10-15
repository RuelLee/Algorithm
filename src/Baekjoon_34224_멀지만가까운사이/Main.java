/*
 Author : Ruel
 Problem : Baekjoon 34224번 멀지만 가까운 사이
 Problem address : https://www.acmicpc.net/problem/34224
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_34224_멀지만가까운사이;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 마을이 트리 형태로 n-1개의 간선으로 연결되어있다.
        // 각 간선마다 비용이 존재한다.
        // 두 마을 u, v 사이의 모든 간선을 xor 연산했을 때, 비용이 0이라면 멀지만 가까운 사이로 부른다.
        // 존재하는 멀지만 가까운 사이 쌍의 개수는?
        //
        // 트리 문제
        // xor = 같으면 0, 다르면 1이지만 문제에 적용시키기 위해 조금 생각해보면..
        // 임의의 한 점에서 이르는 각 노드의 거리를 모두 구한다면
        // 그 거리가 같은 점끼리의 xor 값은 0가 된다.
        // 그냥 한 점에서 다른 점에 이르는 거리를 계산하며, 그 거리의 같은 노드의 개수를 세어주면 되는 문제
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 노드
        int n = Integer.parseInt(br.readLine());
        
        // n-1개의 간선
        List<List<int[]>> connections = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connections.add(new ArrayList<>());
        StringTokenizer st;
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            connections.get(a).add(new int[]{b, c});
            connections.get(b).add(new int[]{a, c});
        }
        
        // 각 노드에 이르는 거리
        int[] xors = new int[n + 1];
        Arrays.fill(xors, -1);
        // 임의의 한 점이므로 항상 존재하는 1을 기준으로 잡는다.
        xors[1] = 0;
        
        // n이 최대 50만이므로 BFS로 탐색
        int[] queue = new int[n];
        int front = 0;
        int back = 0;
        queue[back++] = 1;
        // 해쉬맵을 통해 거리가 같은 노드의 개수를 센다.
        HashMap<Integer, Integer> counts = new HashMap<>();
        // 시작점은 0
        counts.put(0, 1);
        while (front < back) {
            // 현재 노드
            int current = queue[front++];
            
            // 에서 이를 수 있는 다음 노드 next[0]
            for (int[] next : connections.get(current)) {
                // 미방문인 경우
                if (xors[next[0]] == -1) {
                    // 거리 계산
                    xors[next[0]] = xors[current] ^ next[1];
                    
                    // 해쉬맵에 해당하는 거리의 노드 개수 추가
                    counts.put(xors[next[0]], counts.getOrDefault(xors[next[0]], 0) + 1);
                    // 큐에 next[0] 추가
                    queue[back++] = next[0];
                }
            }
        }
        
        // 각 거리가 같은 노드 개수에서 2개를 뽑는 조합을 계산
        long answer = 0;
        for (int key : counts.keySet()) {
            long num = counts.get(key);
            answer += num * (num - 1) / 2;
        }
        // 답 출력
        System.out.println(answer);
    }
}