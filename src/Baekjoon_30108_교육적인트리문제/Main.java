/*
 Author : Ruel
 Problem : Baekjoon 30108번 교육적인 트리 문제
 Problem address : https://www.acmicpc.net/problem/30108
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30108_교육적인트리문제;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 노드로 이루어진 트리 형태가 주어진다.
        // 루트 노드는 1번 노드이며, 각각의 노드는 점수를 갖고 있다.
        // 한 개의 노드를 선택하려면 1번 노드이거나, 자신의 부모 노드 또한 선택이 되어있어야한다.
        // 1 ~ n개의 노드를 선택할 때 얻을 수 있는 각각의 최대 점수는?
        //
        // 그리디 문제
        // 사실 최소 신장 트리에서 사용하는 프림 알고리즘과 매우 유사하다고도 볼 수 있다.
        // 먼저, 루트 노드를 선택하고, 다음에 선택할 수 있는 선택지에 루트 노드의 자식들을 넣되
        // 점수 순으로 내림차순하여 우선적으로 점수가 높은 노드를 선택하게끔 한다.
        // 그 후, 선택한 노드를 점수에 포함시키며, 해당 노드의 자식 노드들을 다음 선택지들 안에 넣는 작업을 반복해나간다.
        // 그러면서 얻을 수 있는 최대 점수들을 모두 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 노드
        int n = Integer.parseInt(br.readLine());

        // 연결 관계
        List<List<Integer>> child = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            child.add(new ArrayList<>());
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n - 1; i++)
            child.get(Integer.parseInt(st.nextToken())).add(i + 2);

        // 각 노드의 점수
        int[] scores = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < scores.length; i++)
            scores[i] = Integer.parseInt(st.nextToken());

        StringBuilder sb = new StringBuilder();
        // 처음 점수는 0
        long sum = 0;
        // 선택됐는지 여부
        // 우선 순위 큐. 점수 별로 내림차순 탐색
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(scores[o2], scores[o1]));
        // 루트 노드 추가
        priorityQueue.offer(1);
        while (!priorityQueue.isEmpty()) {
            int current = priorityQueue.poll();

            // 아니라면 점수에 반영하고 기록
            sum += scores[current];
            sb.append(sum).append("\n");
            // current의 자식 노드들을 우선순위큐에 추가
            for (int c : child.get(current))
                priorityQueue.offer(c);

        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}