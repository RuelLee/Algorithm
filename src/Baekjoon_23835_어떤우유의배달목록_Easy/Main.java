/*
 Author : Ruel
 Problem : Baekjoon 23835번 어떤 우유의 배달목록 (Easy)
 Problem address : https://www.acmicpc.net/problem/23835
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23835_어떤우유의배달목록_Easy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static List<List<Integer>> connections;

    public static void main(String[] args) throws IOException {
        // n개의 방과 n-1개의 통로가 주어진다.
        // 임의의 두 방 사이를 통로를 통해 같은 방을 여러 번 지나지 않고 이동할 수 있는 경로가 정확히 한 개 있다.
        // a에서 b방으로 우유를 배달하는 경우
        // i번째 거치는 방에 i개의 우유를 배달한다.
        // 두 종류의 쿼리가 q개 주어진다.
        // 1 u v -> u에서 v로 우유를 배달한다.
        // 2 x -> x번 방에 배달된 우유의 총 수를 출력
        //
        // 트리, DFS 탐색 문제
        // u에서 v까지의 경로를 찾으며, 거쳐간 방의 수만큼 우유를 배달한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 방
        int n = Integer.parseInt(br.readLine());
        
        // n-1개의 통로
        connections = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connections.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            connections.get(a).add(b);
            connections.get(b).add(a);
        }
        
        // q개의 쿼리
        int q = Integer.parseInt(br.readLine());
        // 각 방에 배달된 우유의 개수
        int[] delivered = new int[n + 1];
        boolean[] visited = new boolean[n + 1];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int order = Integer.parseInt(st.nextToken());
            if (order == 1) {       // 1번 쿼리
                // u -> v로 우유 배달
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                Arrays.fill(visited, false);
                delivery(u, v, 0, delivered, visited);
            } else      // x방에 배달된 우유의 개수 기록
                sb.append(delivered[Integer.parseInt(st.nextToken())]).append("\n");
        }
        // 쿼리 처리 결과 출력
        System.out.print(sb);
    }
    
    // 1번 쿼리 처리 메소드
    static boolean delivery(int current, int end, int via, int[] delivered, boolean[] visited) {
        // 방문 체크
        visited[current] = true;
        // 목적지에 도착했다면
        if (current == end) {
            // 경유 개수만큼 우유 배달
            delivered[current] += via;
            // true 반환
            return true;
        }
        
        // current에서 이동가능한 모든 방 탐색
        for (int next : connections.get(current)) {
            // 미방문이고, end로 우유 배달이 가능한 경우에만
            if (!visited[next] && delivery(next, end, via + 1, delivered, visited)) {
                // current 방에 via개의 우유 배달.
                delivered[current] += via;
                // true 반환.
                return true;
            }
        }
        // 배달하지 못한 경우 false 반환
        return false;
    }
}