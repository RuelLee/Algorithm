/*
 Author : Ruel
 Problem : Baekjoon 2406번 안정적인 네트워크
 Problem address : https://www.acmicpc.net/problem/2406
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2406_안정적인네트워크;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] parents, ranks;

    public static void main(String[] args) throws IOException {
        // n개의 컴퓨터가 있다.
        // 1번 컴퓨터는 다른 모든 컴퓨터와 연결되어있으며, 다른 컴퓨터들은 서로 m개의 연결로만 연결되어있다.
        // 연결은 직접 연결이 끊기더라도 다른 컴퓨터를 경유하는 간접 연결이 존재한다면 고장나지 않는다.
        // 어떤 한 컴퓨터가 고장나더라도 네트워크에 고장이 나지 않도록하고자 할 때
        // 추가로 연결해야하는 연결에 대한 비용과 그 쌍의 개수, 그리고 해당 쌍들을 출력하라
        //
        // 최소 신장 트리
        // 처럼 보이지 않게 위장을 많이하려한 문제
        // 문제만 바로 읽어선 최소신장트리 문제같지 않다.
        // 하지만 1번 컴퓨터는 다른 모든 컴퓨터에 연결이 되어있으므로
        // 1번 컴퓨터를 제외한 다른 컴퓨터는 하나 고장나더라도 1번 컴퓨터를 통해 항상 연결될 수 있다.
        // 하지만 문제는 1번 컴퓨터가 고장나는 경우,
        // 1번 컴퓨터를 제외한 다른 모든 컴퓨터들이 서로 직간접적으로 연결되어야한다.
        // 따라서 1번 컴퓨터를 제외한 다른 컴퓨터들에 대해 최소신장트리로 연결해주면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // Kruskal 알고리즘 사용
        // parent와 ranks 초기화
        parents = new int[n];
        ranks = new int[n];
        for (int i = 0; i < parents.length; i++)
            parents[i] = i;

        // 연결되어있는 컴퓨터쌍 입력.
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;

            // 두 컴퓨터를 하나의 그룹으로 묶는다.
            union(a, b);
        }

        // 연결비용 입력
        int[][] adjMatrix = new int[n][];
        for (int i = 0; i < adjMatrix.length; i++)
            adjMatrix[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 가장 연결 비용이 낮은 두 연결들부터 살펴본다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(value -> adjMatrix[value / n][value % n]));
        for (int i = 1; i < adjMatrix.length; i++) {
            for (int j = i + 1; j < adjMatrix[i].length; j++) {
                // i, j는 서로 다른 그룹일 경우에만 우선순위큐에 추가.
                if (findParents(i) != findParents(j))
                    priorityQueue.offer(i * n + j);
            }
        }
        
        // 새로운 연결들에 대한 비용과 쌍의 개수
        int cost = 0;
        int pair = 0;
        // 해당 쌍들 기록
        StringBuilder sb = new StringBuilder();
        while (!priorityQueue.isEmpty()) {
            // a, b 연결에 대해 살펴본다.
            int a = priorityQueue.peek() / n;
            int b = priorityQueue.poll() % n;

            // 만약 두 컴퓨터가 이미 연결이 되어있다면 건너뛰기.
            if (findParents(a) == findParents(b))
                continue;
            
            // 아닌 경우 두 컴퓨터를 연결하고
            union(a, b);
            // 비용 추가
            cost += adjMatrix[a][b];
            // 쌍 추가
            pair++;
            // StringBuilder에 어떤 두 쌍이 연결되었는지 기록
            sb.append(a + 1).append(" ").append(b + 1).append("\n");
        }
        
        // 정답 출력
        System.out.println(cost + " " + pair);
        System.out.print(sb);
    }

    // a 컴퓨터가 속한 그룹의 대표를 출력.
    static int findParents(int a) {
        if (parents[a] == a)
            return a;
        // 경로 단축
        return parents[a] = findParents(parents[a]);
    }

    // 두 컴퓨터를 하나의 그룹으로 묶는다.
    static void union(int a, int b) {
        // 두 컴퓨터가 속한 각각 그룹의 대표들
        int pa = findParents(a);
        int pb = findParents(b);

        // 대표의 ranks를 비교하고
        // 더 적은 쪽을 다 큰 쪽에 속하도록 한다.
        if (ranks[pa] >= ranks[pb]) {
            parents[pb] = pa;
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else
            parents[pa] = pb;
    }
}