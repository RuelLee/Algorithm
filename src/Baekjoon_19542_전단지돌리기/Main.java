/*
 Author : Ruel
 Problem : Baekjoon 19542번 전단지 돌리기
 Problem address : https://www.acmicpc.net/problem/19542
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_19542_전단지돌리기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static List<List<Integer>> connections;
    static int[] distancesFromTerminals;

    public static void main(String[] args) throws IOException {
        // n개의 노드, n-1개의 연결로 이루어진 트리 형태의 도로가 주어진다.
        // s위치에서 시작하여 모든 위치에 전단지를 돌리려고 한다.
        // 현재 노드에서 d만큼 떨어진 노드는 직접 이동하지 않고서 전단지를 던질 수 있다고 한다.
        // 모든 노드에 전단지를 돌리고 되돌아오는 최소 거리는?
        //
        // DFS 문제
        // 먼저 DFS를 통해 각 노드에서 단말 노드에 이르는 거리를 구한다.
        // 그 후, 단말 노드로부터 거리가 d이하인 노드에선 더 이상 다음 노드로 나아가지 않고
        // 돌아가며 거리를 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 노드, 시작 위치 s, 전단지를 던질 수 있는 거리 d
        int n = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());
        
        // 도로 연결
        connections = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connections.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            connections.get(x).add(y);
            connections.get(y).add(x);
        }

        // 각 노드에서 단말 노드로 이르는 거리 계산
        distancesFromTerminals = new int[n + 1];
        calcDFT(s, new boolean[n + 1]);
        
        // 거리는 왕복이므로 계산한 거리 * 2한 값을 출력
        System.out.println(calcDistance(s, d, new boolean[n + 1]) * 2);
    }
    
    // 실제로 이동해야하는 거리를 계산
    static int calcDistance(int node, int d, boolean[] visited) {
        // 방문 체크
        visited[node] = true;
        // 만약 현재 노드에서 단말 노드까지의 거리가 d이하라면
        // 더 이상 진행하지 않고 돌아간다.
        if (distancesFromTerminals[node] <= d)
            return 0;

        int sum = 0;
        for (int next : connections.get(node)) {
            // 아직 탐색하지 않고, 거리가 d이상인 다음 노드가 존재한다면
            // 방문해야한다.
            // 그 때 거리 합은
            // next 노드에서 진행하는 거리 + node에서 next로 진행하는 거리 1 이다.
            if (!visited[next] && distancesFromTerminals[next] >= d)
                sum += calcDistance(next, d, visited) + 1;
        }
        // node에서 파생되어 이동한 거리를 반환한다.
        return sum;
    }

    // 단말 노드까지의 거리를 계산한다.
    static void calcDFT(int node, boolean[] visited) {
        // 방문 체크
        visited[node] = true;

        for (int next : connections.get(node)) {
            if (!visited[next]) {
                // DFS
                calcDFT(next, visited);
                // 현재 노드에서 단말 노드까지의 거리와
                // next에서 단말노드까지의 거리 +1 중 더 큰 값이
                // 현재 노드에서 단말 노드까지의 거리
                distancesFromTerminals[node] = Math.max(distancesFromTerminals[node], distancesFromTerminals[next] + 1);
            }
        }
    }
}