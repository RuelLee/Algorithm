/*
 Author : Ruel
 Problem : Jungol 1208번 귀가
 Problem address : https://jungol.co.kr/problem/1208
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1208_귀가;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // a ~ z, A ~ Y로 이름이 붙은 목장들이 있다.
        // 그리고 A ~ Z의 목장에는 한 마리의 소가 있다.
        // 해당 소들이 'Z'의 헛간으로 가고자 한다.
        // p개의 목장 사이의 거리 혹은 목장과 헛간 사이의 거리가 주어질 때
        // 헛간과 가장 가까운 목장과 그 거리는?
        //
        // dijkstra, 최단 경로 문제
        // 헛간에서부터 각 목장까지의 최단 경로를 구해, 가장
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // p개의 경로
        int p = Integer.parseInt(br.readLine().trim());
        List<List<int[]>> list = new ArrayList<>();
        for (int i = 0; i < 52; i++)
            list.add(new ArrayList<>());

        // 경로 입력
        StringTokenizer st;
        for (int i = 0; i < p; i++) {
            st = new StringTokenizer(br.readLine());
            char c = st.nextToken().charAt(0);
            int a = c <= 'Z' ? c - 'A' : c - 'a' + 26;
            c = st.nextToken().charAt(0);
            int b = c <= 'Z' ? c - 'A' : c - 'a' + 26;
            int d = Integer.parseInt(st.nextToken());

            list.get(a).add(new int[]{b, d});
            list.get(b).add(new int[]{a, d});
        }

        // dijkstra
        int[] distances = new int[52];
        boolean[] visited = new boolean[52];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[25] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
        pq.offer(new int[]{25, 0});
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            if (cur[1] > distances[cur[0]])
                continue;

            for (int[] next : list.get(cur[0])) {
                if (distances[next[0]] > cur[1] + next[1]) {
                    distances[next[0]] = cur[1] + next[1];
                    pq.offer(new int[]{next[0], distances[next[0]]});
                }
            }
            visited[cur[0]] = true;
        }

        // A ~ Y까지의 목장들 최소 거리를 찾아
        int minIdx = 0;
        for (int i = 1; i < 25; i++) {
            if (distances[i] < distances[minIdx])
                minIdx = i;
        }
        // 출력
        System.out.println((char) (minIdx + 'A') + " " + distances[minIdx]);
    }
}