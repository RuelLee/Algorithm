/*
 Author : Ruel
 Problem : Baekjoon 28283번 해킹
 Problem address : https://www.acmicpc.net/problem/28283
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28283_해킹;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 네트워크 안에 n개의 컴퓨터가 주어진다.
        // 각 컴퓨터 간의 연결관계가 m개 주어진다.
        // 해커는 x개의 컴퓨터를 동시에 해킹하여 돈을 벌고자 한다.
        // 해킹된 컴퓨터는 각 분마다 A1 ... An에 해당하는 돈을 벌 수 있다.
        // 정부는 해킹이 감지되고 나서 0.5분 후 y개의 컴퓨터에 보안 시스템을 설치한다.
        // 보안 시스템은 설치된 후, 1분이 지나면 직접 연결된 다른 컴퓨터로 퍼져나간다.
        // 해커가 벌 수 있는 최대 금액을 출력하라
        // 무한히 많은 돈을 벌 수 있다면 -1을 출력한다.
        //
        // BFS 문제
        // 보안시스템은 0.5분 후 1분마다 퍼져나가므로
        // 0.5 -> 1.5 -> 2.5 -> ...
        // 같이 n.5의 형태를 띈다.
        // 이는 해킹이 당하면 n분 간은 해당하는 돈을 얻을 수 있음을 뜻한다.
        // 따라서 처음 보안시스템이 설치되는 컴퓨터들을 0으로 시작하여
        // 인접한 컴퓨터들을 BFS 탐색하여 각 컴퓨터에 보안시스템이 설치되는 최소 시간을 구한다.
        // 그 후, 해당하는 Ai를 곱해 상위 x개를 뽑으면 된다.
        // 만약 보안시스템이 설치되지 않으면서, Ai가 0이 아닌 컴퓨터가 존재하게 된다면
        // 해당 컴퓨터로부터 무한히 많은 돈을 벌 수 있으므로 -1을 출력한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 컴퓨터, m개의 연결관계
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        // 동시 해킹하는 x대의 컴퓨터
        int x = Integer.parseInt(st.nextToken());
        // 보안시스템이 처음에 설치되는 y대의 컴퓨터
        int y = Integer.parseInt(st.nextToken());
        
        // 각 컴퓨터에서 1분당 벌 수 있는 금액
        int[] as = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 네트워크 연결 관계
        List<List<Integer>> connections = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connections.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken()) - 1;
            int e = Integer.parseInt(st.nextToken()) - 1;

            connections.get(s).add(e);
            connections.get(e).add(s);
        }

        // 보안 시스템이 설치되는 컴퓨터
        int[] bs = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 각 컴퓨터에 보안시스템이 설치되는 시간.
        int[] guardTimes = new int[n];
        Arrays.fill(guardTimes, Integer.MAX_VALUE);
        Queue<Integer> queue = new LinkedList<>();
        for (int b : bs) {
            queue.offer(b - 1);
            guardTimes[b - 1] = 0;
        }
        // BFS 탐색
        boolean[] visited = new boolean[n + 1];
        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (visited[current])
                continue;

            for (int next : connections.get(current)) {
                if (guardTimes[next] > guardTimes[current] + 1) {
                    guardTimes[next] = guardTimes[current] + 1;
                    queue.offer(next);
                }
            }
            visited[current] = true;
        }

        // 우선순위큐로 상위 x대를 뽑는다.
        PriorityQueue<Long> priorityQueue = new PriorityQueue<>(Comparator.reverseOrder());
        for (int i = 0; i < n; i++) {
            // 만약 무한히 돈을 벌 수 있는 컴퓨터를 찾는다면
            // Long.MAX_VALUE 값을 담아 표시
            if (guardTimes[i] == Integer.MAX_VALUE && as[i] != 0)
                priorityQueue.offer(Long.MAX_VALUE);
            else        // 그렇지 않은 경우 벌 수있는 금액을 담는다.
                priorityQueue.offer((long) guardTimes[i] * as[i]);
        }
        
        // 만약 무한히 돈을 벌 수 있는 컴퓨터가 존재한다면 -1을 출력
        if (priorityQueue.peek() == Long.MAX_VALUE)
            System.out.println(-1);
        else {      // 그렇지 않다면 상위 x대를 뽑아 그 금액의 합을 출력
            long sum = 0;
            for (int i = 0; i < x; i++)
                sum += priorityQueue.poll();
            System.out.println(sum);
        }
    }
}