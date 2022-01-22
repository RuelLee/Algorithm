/*
 Author : Ruel
 Problem : Baekjoon 10021번 Watering the Fields
 Problem address : https://www.acmicpc.net/problem/10021
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package WateringTheFields;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Pipe {
    int startFiled;
    int endFiled;
    int distance;

    public Pipe(int startFiled, int endFiled, int distance) {
        this.startFiled = startFiled;
        this.endFiled = endFiled;
        this.distance = distance;
    }
}

public class Main {
    static int[] parents;
    static int[] ranks;

    public static void main(String[] args) throws IOException {
        // 최소 스패닝 트리 문제
        // 오랜만에 kruskal 알고리즘으로 풀어보았다.
        // prim 알고리즘으로도 풀이 가능!
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        int[][] fields = new int[n][2];     // 각 필드의 위치.
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 2; j++)
                fields[i][j] = Integer.parseInt(st.nextToken());
        }

        init(n);
        PriorityQueue<Pipe> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.distance));      // 간선의 길이에 따른 오름차순으로 정렬
        for (int i = 0; i < fields.length - 1; i++) {
            for (int j = i + 1; j < fields.length; j++)
                priorityQueue.offer(new Pipe(i, j, (int) Math.pow(Math.abs(fields[i][0] - fields[j][0]), 2) + (int) Math.pow(Math.abs(fields[i][1] - fields[j][1]), 2)));
        }
        while (!priorityQueue.isEmpty() && priorityQueue.peek().distance < c)       // c보다 작은 파이프는 연결이 불가능하다.
            priorityQueue.poll();

        int sum = 0;        // 총 비용
        int count = 0;      // 연결된 필드의 개수
        while (!priorityQueue.isEmpty()) {
            Pipe current = priorityQueue.poll();
            if (findParents(current.startFiled) != findParents(current.endFiled)) {     // 서로 직간접적으로 연결된 필드들이 아니라면
                union(current.startFiled, current.endFiled);            // current.startField와 current.endField를 서로 연결하고
                sum += current.distance;        // 거리 합을 증가
                count++;        // 연결된 필드의 개수 증가.
            }
        }

        // 총 연결된 필드의 개수가 n-1개라면(처음 필드는 시작필드로 안셌으므로)
        // sum을 출력. 그렇지 않다면 n개의 필드를 연결하는 게 불가능한 경우. -1 출력.
        System.out.println(count == n - 1 ? sum : -1);
    }

    static void init(int n) {
        parents = new int[n];
        ranks = new int[n];
        for (int i = 0; i < parents.length; i++)
            parents[i] = i;
    }

    static int findParents(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParents(parents[n]);
    }

    static void union(int a, int b) {
        int pa = findParents(a);
        int pb = findParents(b);

        if (ranks[pa] > ranks[pb])
            parents[pb] = pa;
        else {
            parents[pa] = pb;
            if (ranks[pa] == ranks[pb])
                ranks[pb]++;
        }
    }
}