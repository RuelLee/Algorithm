/*
 Author : Ruel
 Problem : Baekjoon 1948번 임계경로
 Problem address : https://www.acmicpc.net/problem/1948
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1948_임계경로;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Route {
    int end;
    int time;

    public Route(int end, int time) {
        this.end = end;
        this.time = time;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 도시와 일방통행인 m개의 도로, 시작점과 도착점이 주어진다
        // 시작점에서 출발하여 도착점에 도달하는 가장 오래 시간이 걸리는 경로들을 찾아 시간과, 해당 경로들에 포함된 도로들의 개수를 나타내라
        // 위상 정렬 문제
        // 각 도시들에 도달하는 모든 경로들을 계산할 때까지 다음 경로로 넘어가선 안된다.
        // 따라서 각 도시에 도달하는 경로의 개수를 기억하고 있다가, 하나의 경로를 찾을 때마다 최대 걸린 시간을 업데이트해 나간다
        // 그리고 해당 도시에 들어오는 모든 경로에 대한 탐색이 끝났다면, 이제 그 도시에 다음 도시로 나갈 수 있다.
        // 또한 중요한 점은 가장 시간이 오래 걸리는 경로가 여러개일 수 있다
        // 그리고 그 경로들은 서로 중복된 도로를 지나갈 수 있다.
        // 시간이 가장 오래걸려서 b 도시에 도달했을 때, 출발 도시 a에 대한 기록을 남겨야하는데, 이 a 도시가 여러개가 생길 수 있으므로 리스트로 받자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());

        int[] weights = new int[n + 1];     // 위상 정렬에 대한 가중치. 해당 도시에 들어올 수 있는 도로의 개수이다.
        List<List<Route>> routes = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            routes.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            int[] road = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            routes.get(road[0]).add(new Route(road[1], road[2]));       // road[0] 도시에서 road[1] 도시로의 도로가 있으며, 이 때 소요시간은 road[2]이다.
            weights[road[1]]++;     // road[1] 도시로 들어가는 도로 추가.
        }

        StringTokenizer st = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(st.nextToken());       // 시작점
        int end = Integer.parseInt(st.nextToken());     // 도착점

        int[] maxTime = new int[n + 1];     // 각 도시에 도달하는 최대시간.
        List<List<Integer>> pi = new ArrayList<>();     // 각 도시에 최대 시간으로 도달했을 때의 출발 도시들을 기록해둠.
        for (int i = 0; i < n + 1; i++)
            pi.add(new ArrayList<>());
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        while (!queue.isEmpty()) {
            int current = queue.poll();     // 현재 current 도시.
            for (Route r : routes.get(current)) {       // 에서 갈 수 있는 도로들 r.
                if (--weights[r.end] == 0)      // r.end 도시에 대한 가중치가 0이 됐다면, queue에 추가해서 다음에 탐색해주자.
                    queue.offer(r.end);

                // r.end 도시에 도달하는 최대 시간이 이전 기록과 같거나 더 크다면
                if (maxTime[r.end] <= maxTime[current] + r.time) {
                    if (maxTime[r.end] < maxTime[current] + r.time) {       // 더 크다면
                        maxTime[r.end] = maxTime[current] + r.time;     // 기록 갱신
                        pi.get(r.end).clear();      // 출발 도시에 대한 기록을 지워준다.
                    }
                    // 최대 시간으로 도달했다면 출발 도시에 current 추가.
                    pi.get(r.end).add(current);
                }
            }
        }

        queue.offer(end);       // 도착 도시부터 역추적해나간다.
        boolean[] visited = new boolean[n + 1];     // 방문 체크
        int count = 0;
        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (visited[current])       // 만약 이 도시에서 역추적하는 과정을 이미 했다면 건너뛴다.
                continue;

            for (int p : pi.get(current)) {     // current 도시에 최대 시간에 도달하기 위해 출발해야하는 도시 p
                count++;        // p -> current 경로를 하나 센다.
                queue.offer(p);     // queue에 p 도시 추가.
            }
            // current에 도달하는 최대 소요 시간 출발 도시들을 체크가 끝났으므로 방문 체크.
            visited[current] = true;
        }
        // 도착 도시에 도달하는 최대 시간.
        System.out.println(maxTime[end]);
        // 그 때의 경로들에 포함된 도로의 개수.
        System.out.println(count);
    }
}