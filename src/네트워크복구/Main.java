/*
 Author : Ruel
 Problem : Baekjoon 2211번 네트워크 복구
 Problem address : https://www.acmicpc.net/problem/2211
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 네트워크복구;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[][] adjMatrix;

    public static void main(String[] args) throws IOException {
        // 1번 컴퓨터와 다른 컴퓨터들을 직간접적으로 연결하고자 한다
        // 1번에서 각 컴퓨터에 이르는 시간은 최소 시간이어야한다.
        // 이 때 필요한 컴퓨터 간의 연결의 개수와 연결들을 출력하라
        // 다익스트라 문제!
        // 1번 컴퓨터부터 모든 컴퓨터가 연결이 될 떄까지 다익스트라를 돌린다
        // 그 후, 저장된 pi 배열을 통해 연결의 개수와 연결들을 추적한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        adjMatrix = new int[n + 1][n + 1];      // 인접 행렬에 저장
        for (int[] am : adjMatrix)
            Arrays.fill(am, 1000 * 10 + 1);
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int time = Integer.parseInt(st.nextToken());

            if (adjMatrix[a][b] > time)     // 같은 경로에 복수의 값이 들어올 경우를 대비 더 작은 값일 때만 갱신.
                adjMatrix[a][b] = adjMatrix[b][a] = time;
        }

        boolean[] connected = new boolean[n + 1];       // 1번 컴퓨터와 직간접적으로 연결됐는지 여부.
        int[] pi = new int[n + 1];          // 자신이 연결된 컴퓨터.
        Arrays.fill(pi, -1);
        int[] time = new int[n + 1];        // 1번 컴퓨터로부터 해당 컴퓨터로 도달하는데 걸리는 시간.
        Arrays.fill(time, 1000 * 10 + 1);
        time[1] = 0;
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> time[o]));      // 해당 컴퓨터에 도달하는 시간이 짧은 순서대로.
        priorityQueue.offer(1);     // 1번 컴퓨터로부터 시작.
        while (!priorityQueue.isEmpty()) {
            int current = priorityQueue.poll();
            if (connected[current])     // current 컴퓨터가 이미 연결되어있다면 패스.
                continue;

            for (int i = 1; i < adjMatrix[current].length; i++) {
                if (time[i] > time[current] + adjMatrix[current][i]) {      // 1 -> i까지 가는데 걸리던 이전 경로의 시간보다 current를 경유하는 경로가 더 빠르다면
                    pi[i] = current;        // i의 이전 경로로 current 저장.
                    time[i] = time[current] + adjMatrix[current][i];        // 갱신된 최소 시간 기록.
                    priorityQueue.remove(i);        // 우선순위큐에 i가 이미 있다면 지우고
                    priorityQueue.offer(i);         // 다시 넣어 재정렬.
                }
            }
            connected[current] = true;      // current로부터 갈 수 있는 다른 컴퓨터들에 대한 계산이 끝났으므로, current는 1번 컴퓨터와 연결되었다고 체크.
        }

        // 답안을 적기 위한 계산.
        int count = 0;
        connected = new boolean[n + 1];     // connected를 다시 초기화.
        connected[1] = true;
        StringBuilder sb = new StringBuilder();
        for (int i = 2; i < connected.length; i++) {
            if (connected[i])       // 이미 연결된 컴퓨터라면 패스.
                continue;

            int start = i;      // 아니라면 i 컴퓨터부터 시작해서.
            while (!connected[start]) {     // start가 연결되어있지 않은 동안
                sb.append(pi[start]).append(" ").append(start).append("\n");        // 이전 컴퓨터와 start의 연결을 기록하고
                connected[start] = true;        // start 컴퓨터를 연결됐다고 기록한다.
                count++;    // pi[start]와 start 간의 연결을 센다.
                start = pi[start];      // 그리고 start를 pi[start]로 변경.
            }
        }
        // 최종적으로 StringBuilder에는 연결들이 모두 기록되었고, count에는 그 개수가 기록되었다.
        // 가장 앞에 연결의 개수를 기록.
        sb.insert(0, count + "\n");
        System.out.println(sb);
    }
}