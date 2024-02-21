/*
 Author : Ruel
 Problem : Baekjoon 14630번 변신로봇
 Problem address : https://www.acmicpc.net/problem/14630
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14630_변신로봇;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class State {
    int idx;
    int cost;

    public State(int idx, int cost) {
        this.idx = idx;
        this.cost = cost;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 변신로봇의 형태가 주어진다.
        // 부품의 형태는 숫자로 주어지며, 부품의 형태 길이는 100을 넘지 않는다.
        // 길이가 다른 부품의 형태는 존재하지 않으며, 0으로 시작할 수 있다.
        // 다른 형태로 변신시키는데 드는 비용은 각각의 부품들 차이의 제곱 합과 같다
        // 예를 들어 123과 222의 경우 (1-2)^2 + (2-2)^2 + (3-2)^2 = 2 이다
        // 처음 형태와 원하는 형태가 주어질 때 최소 비용은?
        //
        // 다익스트라 문제
        // 비용이 주어지지 않았지만 형태를 통해 직접 계산이 가능한 다익스트라 문제

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 변신 형태
        int n = Integer.parseInt(br.readLine());
        String[] forms = new String[n];
        for (int i = 0; i < forms.length; i++)
            forms[i] = br.readLine();

        StringTokenizer st = new StringTokenizer(br.readLine());
        // 처음 상태와 원하는 상태
        int start = Integer.parseInt(st.nextToken()) - 1;
        int end = Integer.parseInt(st.nextToken()) - 1;
        
        // 각각의 형태에 도달하는 최소 비용
        int[] minCosts = new int[n];
        Arrays.fill(minCosts, Integer.MAX_VALUE);
        minCosts[start] = 0;
        // 비용이 적은 형태부터 우선적으로 계산
        PriorityQueue<State> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.cost));
        priorityQueue.offer(new State(start, 0));
        while (!priorityQueue.isEmpty()) {
            State current = priorityQueue.poll();
            // current.idx 형태로 도달하는 비용이 current.cost보다 더 적은 경우에는
            // 이미 계산이 됐다. 건너뛴다.
            if (minCosts[current.idx] > current.cost)
                continue;

            // 모든 형태들에 대해 비용을 계산하고
            // 최소 비용을 갱신하는지 확인.
            for (int i = 0; i < forms.length; i++) {
                int nextCosts = current.cost + calcCost(forms[current.idx], forms[i]);
                if (minCosts[i] > nextCosts) {
                    // 갱신할 경우, 값 갱신과 우선순위큐에 추가.
                    minCosts[i] = nextCosts;
                    priorityQueue.offer(new State(i, nextCosts));
                }
            }
        }
        // end에 도달하는 최소 비용 출력
        System.out.println(minCosts[end]);
    }
    
    // 두 형태 간의 비용 계산
    static int calcCost(String a, String b) {
        int cost = 0;
        for (int i = 0; i < a.length(); i++)
            cost += Math.pow(Math.abs(a.charAt(i) - b.charAt(i)), 2);
        return cost;
    }
}