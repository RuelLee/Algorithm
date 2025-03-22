/*
 Author : Ruel
 Problem : Baekjoon 4343번 Arctic Network
 Problem address : https://www.acmicpc.net/problem/4343
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_4343_ArcticNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Connection {
    int a;
    int b;
    double d;

    public Connection(int a, int b, double d) {
        this.a = a;
        this.b = b;
        this.d = d;
    }
}

public class Main {
    static int[] parents, ranks;

    public static void main(String[] args) throws IOException {
        // 전초기지들을 네트워크로 연결하고자 한다.
        // 두 가지 방법이 있는데, 하나는 위성 연결 방법으로
        // 위성 연결이 된 전초 기지 사이에는 위성을 통해 통신할 수 잇다.
        // 다른 하나는 무선 통신으로 연결하는 방법인데, 두 기지 사이의 거리가 d를 초과하지 않는 경우에만 연결할 수 있다.
        // s개의 위성 연결 장치가 주어질 때, 모든 전초기지들을 연결하는 최소 d값을 구하라
        //
        // 최소 스패닝 트리 문제
        // 최소 스패닝 트리로 연결하며, 남은 그룹의 수가 s개가 될 때까지 진행한다.
        // 그 때까지 연결한 기지들의 최대 거리를 구한다.
        
        parents = new int[500];
        ranks = new int[500];

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 테스트 케이스
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < n; t++) {
            st = new StringTokenizer(br.readLine());
            // 위성 연결 장치 s개, 전초 기지들의 수 p
            int s = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());
            
            // 초기화
            init(p);
            // 전초 기지들의 위치
            int[][] points = new int[p][2];
            for (int i = 0; i < points.length; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < points[i].length; j++)
                    points[i][j] = Integer.parseInt(st.nextToken());
            }

            // 전초 기지들 사이의 거리를 우선순위큐에 담는다.
            PriorityQueue<Connection> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(o -> o.d));
            for (int i = 0; i < points.length; i++) {
                for (int j = i + 1; j < points.length; j++)
                    priorityQueue.offer(new Connection(i, j, Math.sqrt(Math.pow(points[i][0] - points[j][0], 2) + Math.pow(points[i][1] - points[j][1], 2))));
            }
            
            // 처음 그룹의 수
            int group = p;
            // 무선 연결된 전초 기지들 사이의 거리 중 최댓값
            double max = 0;
            // 그룹이 s개보다 많은 동안
            while (group > s) {
                // 연결한 두 전초 기지를 꺼낸다.
                Connection current = priorityQueue.poll();
                // 이미 두 전초 기지들이 직간접적으로 연결되어있다면 건너뛴다.
                if (findParent(current.a) == findParent(current.b))
                    continue;

                // 아닐 경우, 두 전초 기지를 무선으로 연결한다.
                union(current.a, current.b);
                // 그룹의 수 하나 감소
                group--;
                // 이 때의 거리가 최댓값을 갱신하는지 확인
                max = Math.max(max, current.d);
            }
            // 구한 d를 형식에 맞게 기록
            sb.append(String.format("%.2f", max)).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
    
    // ranks와 parents를 테스트케이스에 맞게 초기화
    static void init(int n) {
        for (int i = 0; i < n; i++) {
            parents[i] = i;
            ranks[i] = 0;
        }
    }

    // a와 b를 하나의 그룹으로 묶는다.
    static void union(int a, int b) {
        int pa = findParent(a);
        int pb = findParent(b);

        if (ranks[pa] >= ranks[pb]) {
            parents[pb] = pa;
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else
            parents[pa] = pb;
    }
    
    // n이 속한 그룹의 대표를 출력
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }
}