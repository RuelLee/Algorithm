/*
 Author : Ruel
 Problem : Baekjoon 16118번 달빛 여우
 Problem address : https://www.acmicpc.net/problem/16118
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16118_달빛여우;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Road {
    boolean type;
    int turn;
    int end;
    int distance;

    public Road(boolean type, int turn, int end, int distance) {
        this.type = type;
        this.turn = turn;
        this.end = end;
        this.distance = distance;
    }
}

public class Main {
    static int[] delta = {1, 4};

    public static void main(String[] args) throws IOException {
        // n개의 나무 그루터기와 달빛 여우와 달빛 늑대가 있다.
        // 달빛 여우는 일정한 속도로 진행하며 
        // 달빛 늑대는 첫 오솔길은 달빛 여우의 2배의 속도, 다음은 1/2의 속도, 다시 2배의 속도 .. 를 반복한다.
        // 달빛 여우가 달빛 늑대보다 일찍 도착할 수 있는 그루터기의 수는?
        //
        // 다익스트라 문제
        // 여우의 경우 일정한 속도로 이동하지만 늑대의 경우 이동 횟수에 따라 속도가 변하므로
        // 여우는 거리 * 2, 늑대는 *1과 *4를 번갈아가면서 거리를 계산해주도록 한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 그루터기의 수
        int n = Integer.parseInt(st.nextToken());
        // 길의 수
        int m = Integer.parseInt(st.nextToken());

        // 인접리스트로 길을 정리.
        List<List<Road>> roads = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            roads.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            roads.get(a).add(new Road(true, 0, b, d));
            roads.get(b).add(new Road(true, 0, a, d));
        }

        // 여우가 각 그루터기에 도착하는 최소 거리
        int[] foxMinDistances = new int[n + 1];
        // 값 초기화
        Arrays.fill(foxMinDistances, Integer.MAX_VALUE);
        foxMinDistances[1] = 0;

        // 늑대가 각 그루터기에 도착하는 최소 거리
        // 홀수번째 횟수인지, 짝수번째 횟수인지에 따라 다르므로 2가지 경우로 분리 계산.
        int[][] wolfMinDistances = new int[2][n + 1];
        // 초기화
        for (int[] wmd : wolfMinDistances)
            Arrays.fill(wmd, Integer.MAX_VALUE);
        wolfMinDistances[0][1] = 0;

        // 방문 체크
        // visited[0]은 여우의 공간
        // visited[1]은 늑대가 홀수번째 횟수로
        // visited[2]는 늑대가 짝수번재 횟수로 도착했을 때.
        boolean[][] visited = new boolean[3][n + 1];
        // 우선순위큐로 최소 거리에 따라 우선적으로 탐색
        PriorityQueue<Road> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.distance, o2.distance));
        // 여우
        priorityQueue.offer(new Road(false, 0, 1, 0));
        // 늑대
        priorityQueue.offer(new Road(true, 0, 1, 0));
        while (!priorityQueue.isEmpty()) {
            Road current = priorityQueue.poll();
            // 여우나 늑대가 해당 그루터기에서 이미 방문 후 계산을 마쳤다면 건너뛴다.
            if ((current.type == false && visited[0][current.end]) ||
                    (current.type == true && visited[current.turn % 2 + 1][current.end]))
                continue;

            // 여우인 경우
            if (!current.type) {
                for (Road next : roads.get(current.end)) {
                    // 다음 그루터기를 방문하지 않았고, 최소 거리가 갱신되는 경우
                    if (!visited[0][next.end] && foxMinDistances[next.end] > foxMinDistances[current.end] + next.distance * 2) {
                        foxMinDistances[next.end] = foxMinDistances[current.end] + next.distance * 2;
                        priorityQueue.offer(new Road(current.type, 0, next.end, foxMinDistances[next.end]));
                    }
                }
                // 방문 체크
                visited[0][current.end] = true;
            } else {        // 늑대의 경우
                for (Road next : roads.get(current.end)) {
                    // 다음 그루터기를 방문하지 않았고, 최소 거리가 갱신 되는 경우
                    // 늑대의 경우 홀수번째인지 짝수번째 인지 분리 계산
                    // 홀수 일 때는 *1의 거리, 짝수일 때는 *4의 거리
                    if (!visited[(current.turn + 1) % 2 + 1][next.end] &&
                            wolfMinDistances[(current.turn + 1) % 2][next.end] > wolfMinDistances[current.turn % 2][current.end] + next.distance * delta[current.turn % 2]) {
                        wolfMinDistances[(current.turn + 1) % 2][next.end] = wolfMinDistances[current.turn % 2][current.end] + next.distance * delta[current.turn % 2];
                        priorityQueue.offer(new Road(current.type, current.turn + 1, next.end, wolfMinDistances[(current.turn + 1) % 2][next.end]));
                    }
                }
                // 방문 체크
                visited[current.turn % 2 + 1][current.end] = true;
            }
        }

        // 여우가 늑대보다 빨리 도달할 수 있는 그루터기의 수를 계산한다.
        int count = 0;
        for (int i = 1; i < n + 1; i++) {
            if (foxMinDistances[i] < Math.min(wolfMinDistances[0][i], wolfMinDistances[1][i]))
                count++;
        }

        // 답안 출력.
        System.out.println(count);
    }
}