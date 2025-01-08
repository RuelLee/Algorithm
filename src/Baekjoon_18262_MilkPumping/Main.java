/*
 Author : Ruel
 Problem : Baekjoon 18262번 Milk Pumping
 Problem address : https://www.acmicpc.net/problem/18262
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18262_MilkPumping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Pipe {
    int end;
    int cost;
    int flow;

    public Pipe(int end, int cost, int flow) {
        this.end = end;
        this.cost = cost;
        this.flow = flow;
    }
}

public class Main {
    static List<List<Pipe>> pipes;

    public static void main(String[] args) throws IOException {
        // n개의 포인트가 존재하고, 이들을 잇는 m개의 파이프가 주어진다.
        // 1번 포인트에서 n번 포인트로 우유를 옮기고자 한다.
        // 각 파이프는 연결점 두 곳과 비용, 유량이 주어진다.
        // n번 포인트에서 유량 / 비용의 값을 최대화하고자 할 때
        // 그 값의 * 10^6의 정수부 값은?
        //
        // 다익스트라 문제
        // 각 지점에서 최소 비용만 계산하는 것이 아니라
        // 각 지점에서 유량 별 최소 비용을 계산하면 된다.
        // 따라서 minCosts[지점][유량] = 최소 비용으로 세우고 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 포인트, m개의 파이프
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 파이프 입력 처리
        pipes = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            pipes.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int f = Integer.parseInt(st.nextToken());

            pipes.get(a).add(new Pipe(b, c, f));
            pipes.get(b).add(new Pipe(a, c, f));
        }

        // 다익스트라
        // 각 지점에서 유량 별로 최소 비용을 계산한다.
        int[][] minCosts = new int[n + 1][1001];
        // 초기값 세팅
        for (int[] mc : minCosts)
            Arrays.fill(mc, Integer.MAX_VALUE);
        minCosts[1][1000] = 0;
        // 우선순위큐로 최저 비용인 것부터 계산.
        PriorityQueue<Pipe> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.cost));
        // 1번 지점에서, 비용은 0, 유량은 최대인 상태로 시작.
        priorityQueue.offer(new Pipe(1, 0, 1000));
        while (!priorityQueue.isEmpty()) {
            Pipe current = priorityQueue.poll();
            // 이전에 더 적은 비용으로 해당 지점에서의 유량을 계산했다면 건너뛴다.
            if (minCosts[current.end][current.flow] < current.cost)
                continue;

            // current.end에서 연결할 수 있는 파이프들을 살펴본다.
            for (Pipe next : pipes.get(current.end)) {
                // next 파이프를 선택했을 때의 유량
                int nextFlow = Math.min(current.flow, next.flow);
                // next.end에 nextFlow 유량으로 연결하는 비용이 최소인지 확인하고
                // 그렇다면 비용 갱신 및 우선순위큐에 추가
                if (minCosts[next.end][nextFlow] > current.cost + next.cost) {
                    minCosts[next.end][nextFlow] = current.cost + next.cost;
                    priorityQueue.offer(new Pipe(next.end, minCosts[next.end][nextFlow], nextFlow));
                }
            }
        }

        // 답으로써 (유량 / 비용)의 값에 10^6을 곱하여 생긴 정수부만 답으로 원하므로
        // 해당 값을 계산하여 비교한다.
        int answer = 0;
        for (int i = 1; i < minCosts[n].length; i++) {
            if (minCosts[n][i] == Integer.MAX_VALUE)
                continue;
            answer = Math.max(answer, i * 1_000_000 / minCosts[n][i]);
        }
        // 답 출력
        System.out.println(answer);
    }
}