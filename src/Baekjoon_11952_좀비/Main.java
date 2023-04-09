/*
 Author : Ruel
 Problem : Baekjoon 11952번 좀비
 Problem address : https://www.acmicpc.net/problem/11952
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11952_좀비;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 도시, m개의 도로가 주어진다
        // 그 중 k개의 도시는 좀비에 점령당했고,
        // 좀비에게 점령당한 도시로부터 s번 이하로 이동할 수 있는 도시는 위험한 도시라고 한다.
        // 안전한 도시의 숙박비는 p, 위험한 도시의 숙박비는 q이며
        // 1번 도시로부터 n번 도시로 이동하고할 때 소모되는 최소 숙박비를 구하라
        //
        // BFS 문제
        // 좀비로 점령당한 도시로부터 이동횟수가 위험한 도시들을 BFS를 통해 찾고
        // 다시 1번 도시로부터 BFS를 통해 n번 도시로 이동하는데 드는 최소 숙박비를 찾는다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 주어지는 도시, 도로, 좀비에 점령당한 도시들의 수, 위험한 도시의 범위
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());

        // 안전한 도시와 위험한 도시의 숙박비
        st = new StringTokenizer(br.readLine());
        int p = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        // 좀비에 점령당한 도시로부터 이동횟수가 s 이하인 도시들을 찾는다.
        int[] distancesFromZombie = new int[n];
        Arrays.fill(distancesFromZombie, Integer.MAX_VALUE);
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < k; i++) {
            int city = Integer.parseInt(br.readLine()) - 1;
            distancesFromZombie[city] = 0;
            queue.offer(city);
        }
        
        // 주어지는 m개의 도로
        List<List<Integer>> roads = new ArrayList<>();
        for (int i = 0; i < n; i++)
            roads.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;

            roads.get(a).add(b);
            roads.get(b).add(a);
        }

        // BFS를 통해 위험한 도시들을 찾는다.
        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (distancesFromZombie[current] == s)
                continue;

            for (int next : roads.get(current)) {
                if (distancesFromZombie[next] > distancesFromZombie[current] + 1) {
                    distancesFromZombie[next] = distancesFromZombie[current] + 1;
                    queue.offer(next);
                }
            }
        }

        // BFS를 통해 1번 도시로부터 n번 도시로 도달하는데 드는 최소 숙박비를 찾는다.
        long[] charges = new long[n];
        Arrays.fill(charges, Long.MAX_VALUE);
        charges[0] = 0;
        queue.offer(0);
        while (!queue.isEmpty()) {
            int current = queue.poll();

            // current -> next로 가는 경우를 계산
            for (int next : roads.get(current)) {
                // next가 만약 최종 목적지일 경우에는 숙박하지 않으므로 0
                // 안전한 도시라면 p, 위험한 도시라면 q
                int charge = (next == n - 1 ? 0 :
                        distancesFromZombie[next] == Integer.MAX_VALUE ? p : q);
                // next에 도달하는 최소 숙박비를 갱신하는지 확인하고
                // 갱신한다면 큐에 추가
                if (distancesFromZombie[next] != 0 && charges[next] > charges[current] + charge) {
                    charges[next] = charges[current] + charge;
                    queue.offer(next);
                }
            }
        }
        // n번 도시에 도달하는 최소 숙박비를 출력한다.
        System.out.println(charges[n - 1]);
    }
}