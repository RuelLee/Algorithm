/*
 Author : Ruel
 Problem : Baekjoon 1810번 징검다리 달리기 2
 Problem address : https://www.acmicpc.net/problem/1810
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1810_징검다리달리기2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Seek {
    int idx;
    double distance;

    public Seek(int idx, double distance) {
        this.idx = idx;
        this.distance = distance;
    }
}

public class Main {
    static int[][] stones;

    public static void main(String[] args) throws IOException {
        // n개의 징검다리가 놓여있다.
        // 각 징검다리의 위치가 주어진다.
        // 각 징검다리를 이동할 때는 x와 y의 차이가 2이하로 나는 징검다리로만 이동할 수 있다.
        // (0, 0)에서 시작하여, y좌표 f이상인 징검다리로 도착하고자할 때
        // 최소 이동 거리는?
        //
        // dijkstra, 정렬, 이분탐색 문제
        // 먼저 징검다리를 y좌표별, y좌표가 같다면 x좌표별로 정렬한다.
        // 그 후, 각 징검다리로부터 다른 징검다리로 이동할 수 있는 연결관계를 찾는다.
        // 이 때 데이터의 범위가 크므로, 이분탐색을 통해 빠르게 위치를 찾아 연결관계를 구한다.
        // 그 후, 연결 관계에 따라 dijkstra를 통해 최소 거리를 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 징검다리, 목표 y좌표인 f
        int n = Integer.parseInt(st.nextToken());
        int f = Integer.parseInt(st.nextToken());
        
        // 징검다리
        stones = new int[n + 1][];
        // 시작 지점
        stones[0] = new int[2];
        for (int i = 1; i < stones.length; i++)
            stones[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // y좌표별로 정렬하되, 같다면 x좌표로 정렬
        Arrays.sort(stones, (o1, o2) -> {
            if (o1[1] == o2[1])
                return Integer.compare(o1[0], o2[0]);
            return Integer.compare(o1[1], o2[1]);
        });
        
        // 연결 관계
        List<HashSet<Integer>> list = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            list.add(new HashSet<>());
        // 모든 징검다리를 살펴본다.
        for (int i = 0; i < stones.length; i++) {
            // stones[i][1]부터 stones[i][1] + 2까지의 범위 내에서 이동할 수 있다.
            for (int y = stones[i][1]; y <= stones[i][1] + 2; y++) {
                // y좌표가 y이며
                // stones[i][0] - 2보다 같거나 큰
                // 가장 작은 x좌표를 갖는 징검다리를 이분탐색을 통해 찾는다.
                int start = i + 1;
                int end = stones.length - 1;

                while (start <= end) {
                    int mid = (start + end) / 2;
                    if (stones[mid][1] < y ||
                            (stones[mid][1] == y && stones[mid][0] + 2 < stones[i][0]))
                        start = mid + 1;
                    else
                        end = mid - 1;
                }

                // 찾은 start 위치부터 이동 가능한 징검다리들을 살펴보며
                // 이동 가능할 경우 해당 사항을 기록한다.
                for (int j = start; j < stones.length; j++) {
                    if (stones[j][1] != y || stones[j][0] - stones[i][0] > 2)
                        break;

                    list.get(i).add(j);
                    list.get(j).add(i);
                }
            }

        }
        
        // dijkstra
        double[] dp = new double[n + 1];
        boolean[] visited = new boolean[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        PriorityQueue<Seek> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(o -> o.distance));
        priorityQueue.offer(new Seek(0, 0));
        long answer = Long.MAX_VALUE;
        while (!priorityQueue.isEmpty()) {
            Seek current = priorityQueue.poll();
            if (visited[current.idx])
                continue;

            // current -> next로 이동 시, 최소 거리 갱신 여부 확인.
            for (int next : list.get(current.idx)) {
                double nextDistance = dp[current.idx] + calcDistance(current.idx, next);
                if (!visited[next] && dp[next] > nextDistance) {
                    dp[next] = nextDistance;
                    priorityQueue.offer(new Seek(next, nextDistance));
                }
            }
            // 방문 표시
            visited[current.idx] = true;
            if (stones[current.idx][1] >= f)
                answer = Math.min(answer, Math.round(dp[current.idx]));
        }
        // 답이 초기값이라면 불가능한 경우이므로 -1을 출력
        // 그 외의 경우 계산된 answer를 출력
        System.out.println(answer == Long.MAX_VALUE ? -1 : answer);
    }

    // 두 징검다리 사이의 거리 계산
    static double calcDistance(int aIdx, int bIdx) {
        return Math.sqrt(Math.pow(stones[aIdx][0] - stones[bIdx][0], 2) +
                Math.pow(stones[aIdx][1] - stones[bIdx][1], 2));
    }
}