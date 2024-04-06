/*
 Author : Ruel
 Problem : Baekjoon 27924번 윤이는 엄청난 것을 훔쳐갔습니다
 Problem address : https://www.acmicpc.net/problem/27924
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_27924_윤이는엄청난것을훔쳐갔습니다;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.List;

class Seek {
    int node;
    int idx;

    public Seek(int node, int idx) {
        this.node = node;
        this.idx = idx;
    }
}

public class Main {

    public static void main(String[] args) throws IOException {
        // n개의 정점과 n-1개의 간선으로 이루어진 트리가 주어진다.
        // 윤이는 달구와 포닉스를 피해 리프 노드에 도달하려한다.
        // 각 턴에는 윤이가 먼저 간선을 따라 한 칸 이동하고
        // 달구와 포닉스가 간선을 한 칸 이동한다.
        // 윤이가 달구 혹은 포닉스와 같은 정점에 위치하는 경우 즉시 체포되고
        // 윤이가 리프 노드에 도달하는 즉시 탈출한다.
        // 윤이가 탈주할 수 있는지를 계산하라
        // 리프 노드는 이웃한 정점이 하나밖에 없는 노드이다.
        //
        // BFS 문제
        // 먼저 각 위치에서 다른 노드에 이르는 최소거리를 BFS를 통해 탐색한다.
        // 그 후, 리프 노드에서 윤이가 다른 두 사람보다 일찍 도착하는지를 체크하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 정점
        int n = Integer.parseInt(br.readLine());
        // n-1개의 간선
        List<List<Integer>> connections = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connections.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            connections.get(u).add(v);
            connections.get(v).add(u);
        }
        
        // 윤이, 달구, 포닉스의 처음 위치
        StringTokenizer st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        
        // 각각이 각 노드에 이르는 최소 거리
        int[][] distances = new int[n + 1][3];
        for (int[] d : distances)
            Arrays.fill(d, Integer.MAX_VALUE);
        distances[a][0] = distances[b][1] = distances[c][2] = 0;

        Queue<Seek> queue = new LinkedList<>();
        queue.offer(new Seek(a, 0));
        queue.offer(new Seek(b, 1));
        queue.offer(new Seek(c, 2));
        while (!queue.isEmpty()) {
            Seek current = queue.poll();

            for (int child : connections.get(current.node)) {
                if (distances[child][current.idx] > distances[current.node][current.idx] + 1) {
                    distances[child][current.idx] = distances[current.node][current.idx] + 1;
                    queue.offer(new Seek(child, current.idx));
                }
            }
        }

        // 리프 노드들을 살펴보며
        // 윤이가 리프 노드에 가장 먼저 도착하는지 확인한다.
        boolean answer = false;
        for (int i = 1; i < connections.size(); i++) {
            if (connections.get(i).size() == 1 &&
                    distances[i][0] < Math.min(distances[i][1], distances[i][2])) {
                answer = true;
                break;
            }
        }
        // 답안 출력
        System.out.println(answer ? "YES" : "NO");
    }
}