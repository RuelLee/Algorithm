/*
 Author : Ruel
 Problem : Baekjoon 1162번 도로포장
 Problem address : https://www.acmicpc.net/problem/1162
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1162_도로포장;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static HashMap<Integer, HashMap<Integer, Integer>> roads;

    public static void main(String[] args) throws IOException {
        // n개의 도시와 m개의 도로가 주어진다
        // k개의 도로를 포장하여 해당 도로를 이용하는 시간을 0으로 만들 수 있다
        // 1에서 출발하여 n 도시에 도착하려할 때 최소 소요 시간을 구하라
        //
        // dijkstra 알고리즘과 dp를 사용해야한다는 느낌은 왔지만
        // 시간과 메모리로 조금 고생했던 문제
        // k개의 도로를 포장할 수 있으므로, 한 도시에서 다른 도시로 건너갈 때, 해당 도로를 포장하는 경우, 그렇지 않은 경우
        // 두 가지 경우에 대해 생각해야한다
        // 따라서 dp는 dp[도시][포장횟수]로 나타낼 수 있으며, 해당 사항을 다익스트라로 돌리면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 보통은 인접 배열, 인접 리스트로 만들지만 메모리 사용을 줄이고자 해쉬맵을 통해 만들어보았다.
        roads = new HashMap<>();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int time = Integer.parseInt(st.nextToken());

            // a와 b에 해당하는 해쉬맵이 없을 경우 생성.
            if (!roads.containsKey(a))
                roads.put(a, new HashMap<>());
            if (!roads.containsKey(b))
                roads.put(b, new HashMap<>());

            // 양방향 도로이므로 a -> b, b -> a에 대해 새로운 값이면 추가 혹은
            // 이미 있다면 최소값이 갱신될 때 교체.
            if (!roads.get(a).containsKey(b) ||
                    roads.get(a).get(b) > time) {
                roads.get(a).put(b, time);
                roads.get(b).put(a, time);
            }
        }

        // 다익스트라 알고리즘
        // 각 도시와 해당 도시를 방문할 때까지 사용한 도로 포장횟수에 따라 기록해야한다
        // 따라서 (n + 1)개의 도시에 대해 (k + 1)가지 경우의 수에 대한 dp를 생성하고 초기값으로 큰 값을 삽입.
        long[] minTime = new long[(n + 1) * (k + 1)];
        Arrays.fill(minTime, Long.MAX_VALUE);
        // 출발 지점에서의 소요 시간은 0
        minTime[1 * (k + 1)] = 0;
        boolean[] visited = new boolean[(n + 1) * (k + 1)];
        // 최소 도착 시간에 따라 오름차순으로.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingLong(o -> minTime[o]));
        // 우선순위큐에 출발지 삽입.
        priorityQueue.offer(1 * (k + 1));
        while (!priorityQueue.isEmpty()) {
            // 우선순위큐에 들어가는 값의 의미는
            int currentIdx = priorityQueue.poll();
            // (k + 1)로 나누면 해당 도시의 번호
            int currentCity = currentIdx / (k + 1);
            // (k + 1)로 모듈러 연산을 하면 거쳐온 도시의 포장 사용 횟수가 나온다.
            int currentK = currentIdx % (k + 1);

            // 연결 되어있는 다음 도시를 찾고
            for (int next : roads.get(currentCity).keySet()) {
                // 해당 도시의 idx 계산
                int nextIdx = next * (k + 1) + currentK;

                // next 도시로 도로 포장을 사용하지 않고 가는 경우.
                if (!visited[nextIdx] && minTime[nextIdx] > minTime[currentIdx] + roads.get(currentCity).get(next)) {
                    minTime[nextIdx] = minTime[currentIdx] + roads.get(currentCity).get(next);
                    priorityQueue.remove(nextIdx);
                    priorityQueue.offer(nextIdx);
                }

                // next 도시로 도로 포장을 사용하고 가는 경우.
                if (currentK < k && !visited[++nextIdx]) {
                    if (minTime[nextIdx] > minTime[currentIdx]) {
                        minTime[nextIdx] = minTime[currentIdx];
                        priorityQueue.remove(nextIdx);
                        priorityQueue.offer(nextIdx);
                    }
                }
            }
            // currentIdx 방문 체크.
            visited[currentIdx] = true;
        }
        // 최소 값을 찾는다
        // 도로 포장을 꼭 k번 다 사용할 필요는 없다. 도로 포장을 사용 안하고 n번 도시에 도착한 경우부터
        // k번 다 사용한 경우까지 중 가장 작은 값을 찾는다.
        long minTimeToN = Long.MAX_VALUE;
        for (int i = 0; i < k + 1; i++)
            minTimeToN = Math.min(minTimeToN, minTime[minTime.length - 1 - i]);
        System.out.println(minTimeToN);
    }
}