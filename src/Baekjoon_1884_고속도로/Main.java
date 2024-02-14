/*
 Author : Ruel
 Problem : Baekjoon 1884번 고속도로
 Problem address : https://www.acmicpc.net/problem/1884
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1884_고속도로;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Road {
    int end;
    int distance;
    int cost;

    public Road(int end, int distance, int cost) {
        this.end = end;
        this.distance = distance;
        this.cost = cost;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 교통비 k, n개의 도시, r개의 도로가 주어진다.
        // 도로는 시작 도시, 도착 도시, 도로 거리, 통행료가 주어진다.
        // 1번 도시에 n번 도시까지 k원 이내에 최소 거리로 도달하고 싶다.
        // 그 거리를 구하라
        //
        // 다익스트라
        // 1번에서 n번까지 도달하는 최소 거리를 구한다.
        // 단 k원 이내에 구해야하므로, 각 도시에 도달하는 최소 비용에 따른 최소 거리를 구별하여 계산한다.
        // 
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 교통비 k, n개의 도시, r개의 도로
        int k = Integer.parseInt(br.readLine());
        int n = Integer.parseInt(br.readLine());
        int r = Integer.parseInt(br.readLine());
        
        // 도로 정보
        List<List<Road>> roads = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            roads.add(new ArrayList<>());
        for (int i = 0; i < r; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            roads.get(Integer.parseInt(st.nextToken())).add(new Road(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }
        
        // 도시와 사용 통행료에 따른 최소 거리
        int[][] minDistances = new int[n + 1][k + 1];
        for (int[] md : minDistances)
            Arrays.fill(md, Integer.MAX_VALUE);
        Arrays.fill(minDistances[1], 0);
        
        // 다익스트라
        Queue<Road> queue = new LinkedList<>();
        queue.offer(new Road(1, 0, 0));
        while (!queue.isEmpty()) {
            Road current = queue.poll();
            // 같거나 더 적은 비용일 때,
            // 더 적은 거리로 도달하는 것이 가능했다면 건너뛴다.
            if (minDistances[current.end][current.cost] < current.distance)
                continue;

            // current.end에서 이동 가능한 도시들을 살펴본다.
            for (Road next : roads.get(current.end)) {
                // 비용 합과 거리 합
                int costSum = current.cost + next.cost;
                int distanceSum = current.distance + next.distance;
                
                // 비용 합이 k를 넘지 않으면서
                // 최소 거리를 갱신한다면
                if (costSum <= k && minDistances[next.end][costSum] > distanceSum) {
                    // i원 이상으로 next.end에 도달하는 경우에는
                    // 모두 distaceSum보다 같거나 적은 값으로 도달해야한다.
                    for (int i = costSum; i <= k; i++) {
                        if (minDistances[next.end][i] < distanceSum)
                            break;
                        minDistances[next.end][i] = distanceSum;
                    }
                    // 큐에 추가
                    queue.offer(new Road(next.end, distanceSum, costSum));
                }
            }
        }
        // n번 도시에 k원 이내로 도달하는 최소 거리 출력
        // 초기값이라면 불가능한 경우이므로 -1 출력
        System.out.println(minDistances[n][k] == Integer.MAX_VALUE ? -1 : minDistances[n][k]);
    }
}