/*
 Author : Ruel
 Problem : Baekjoon 4792번 레드 블루 스패닝 트리
 Problem address : https://www.acmicpc.net/problem/4792
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_4792_레드블루스패닝트리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Line {
    int a;
    int b;

    public Line(int a, int b) {
        this.a = a;
        this.b = b;
    }
}

public class Main {
    static List<Line> blueConnections;
    static List<Line> redConnections;
    static int[] ranks;
    static int[] parents;

    public static void main(String[] args) throws IOException {
        // 정점 n, 간선 m이 주어진다. 간선은 빨간색 또는 파란색의 색이 주어진다
        // 이들을 이용해서 최소 스패닝 트리를 만드는데, 정확히 k개의 파란색 간선을 이용하여 만드려고 한다.
        // 만드는 것이 가능하다면 1, 불가능하다면 0을 출력하라
        //
        // 최소 스패닝 트리 -> 분리 집합 문제인데....
        // 정확히 k개의 파란색 간선을 이용하여 최소 스패닝 트리를 만들 수 있는지 체크해야한다
        // 간선을 빨간색 우선적으로 사용하여 최소 스패닝 트리를 만들면, 최소한의 파란색 간선이 몇 개 사용됐는지 체크할 수 있고
        // 파란색 간선을 우선적으로 사용하여 최소 스패닝 트리를 만들면, 최대한의 파란색 간선의 사용 수를 구할 수 있다.
        // 이 두 값을 토대로 k개 파란색 간선을 이용하여 최소 스패닝 트리를 만들 수 있는지 확인할 수 있다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());

            if (n == 0 && m == 0 && k == 0)
                break;

            blueConnections = new ArrayList<>();
            redConnections = new ArrayList<>();

            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                char color = st.nextToken().charAt(0);
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                if (color == 'B')
                    blueConnections.add(new Line(a, b));
                else
                    redConnections.add(new Line(a, b));
            }

            // 빨간 간선을 우선적으로 사용하여 스패닝 트리를 만든다
            // 파란 간선을 최소로 하여 스패닝 트리를 만들 때 그 개수를 알 수 있다.
            ranks = new int[n + 1];
            parents = new int[n + 1];
            for (int i = 1; i < parents.length; i++)
                parents[i] = i;
            int minBlueCount = 0;
            // 빨간 간선으로 서로 parent가 다른 정점들을 잇는다.
            for (Line l : redConnections) {
                if (findParents(l.a) != findParents(l.b))
                    union(l.a, l.b);
            }
            // 파란 간선으로 parent가 다른 정점들을 잇는다.
            // 그리고 그 때의 간선의 개수를 센다.
            for (Line l : blueConnections) {
                if (findParents(l.a) != findParents(l.b)) {
                    union(l.a, l.b);
                    minBlueCount++;
                }
            }
            // 최소 스패닝 트리가 완성됐는지 확인한다.
            HashSet<Integer> hashSet = new HashSet<>();
            for (int i = 1; i < parents.length; i++)
                hashSet.add(findParents(i));
            // 최소 파란 간선의 개수가 k 초과거나, 스패닝 트리가 안 만들어졌다면
            // k개의 파란 간선으로 스패닝 트리를 만든 것이 불가능하다.
            if (minBlueCount > k || hashSet.size() > 1) {
                sb.append(0).append("\n");
                continue;
            }

            // 파란 간선을 우선적으로 최소 스패닝 트리를 만든다
            // 파란 간선을 최대로 사용하여 스패닝 트리를 만든다.
            ranks = new int[n + 1];
            parents = new int[n + 1];
            for (int i = 1; i < parents.length; i++)
                parents[i] = i;
            int maxBlueCount = 0;
            // 파란 간선을 통해 스패닝 트리를 만들며 그 개수를 센다.
            for (Line l : blueConnections) {
                if (findParents(l.a) != findParents(l.b)) {
                    union(l.a, l.b);
                    maxBlueCount++;
                }
            }
            // 그 후 빨간 간선을 통해 아직 parent가 다른 두 정점을 잇는다.
            for (Line l : redConnections) {
                if (findParents(l.a) != findParents(l.b))
                    union(l.a, l.b);
            }
            // 최소 스패닝 트리가 완성 됐는지 확인.
            hashSet = new HashSet<>();
            for (int i = 1; i < parents.length; i++)
                hashSet.add(findParents(i));
            // 파란 간선을 최대로 사용했음에도 k개보다 작거나, 스패닝 트리가 완성이 안됐다면 불가능한 경우.
            if (maxBlueCount < k || hashSet.size() > 1) {
                sb.append(0).append("\n");
                continue;
            }
            // 두 경우가 아니라면 파란색 간선 k개를 사용하여 최소 스패닝 트리를 만드는 것이 가능하다
            // 1 출력.
            sb.append(1).append("\n");
        }
        System.out.print(sb);
    }

    // a와 b를 하나의 집합으로 합친다
    // 랭크 압축 사용.
    // 랭크가 작은 쪽을 큰 쪽에 속하게 만드는게 더 연산이 적다.
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
    
    // parent 찾는 메소드.
    // 경로 압축 사용
    // 거쳐가는 경로의 정점들에게 가장 상위 parent 값으로 등록.
    static int findParents(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParents(parents[n]);
    }
}