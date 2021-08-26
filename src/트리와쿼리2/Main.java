/*
 Author : Ruel
 Problem : Baekjoon 13511번 트리와 쿼리 2
 Problem address : https://www.acmicpc.net/problem/13511
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 트리와쿼리2;

import java.util.*;

class Edge {
    int anotherOne;
    int cost;

    public Edge(int anotherOne, int cost) {
        this.anotherOne = anotherOne;
        this.cost = cost;
    }
}

public class Main {
    static int[] levels;
    static int[][] parents;
    static long[] costs;

    public static void main(String[] args) {
        // 생각치 못한 조건 때문에 고생했던 것 같다.
        // 주어지는 간선이 두 정점 모두 이전에 등장하지 않았던 정점들이 등장할 수 있는 경우를 놓쳤다.
        // 전체적으로는 LCA와 희소배열 그리고 DP로 풀 수 있는 문제!
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        init(n, sc);

        int m = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            int q = sc.nextInt();
            if (q == 1)
                sb.append(getCost(sc.nextInt(), sc.nextInt())).append("\n");
            else if (q == 2)
                sb.append(getKthNode(sc.nextInt(), sc.nextInt(), sc.nextInt())).append("\n");
        }
        System.out.println(sb);
    }

    static int getKthNode(int u, int v, int k) {
        int lca = getLCA(u, v);
        k--;

        if (levels[u] - k > levels[lca]) {      // 왼쪽 트리인 경우
            while (k > 0) {     // 그냥 u로 부터 (k - 1)번째 부모를 찾아가면 된다!
                int jump = 0;
                while ((1 << (jump + 1)) <= k)
                    jump++;
                u = parents[u][jump];
                k -= 1 << jump;
            }
            return u;
        } else {        // 최소 공통 부모부터 오른쪽 트리인 경우
            // v로부터의 diff를 구해
            int diff = levels[v] - (levels[lca] + (k - (levels[u] - levels[lca])));
            while (diff > 0) {      // 마찬가지로 v의 diff번째 조상을 찾아가면 된다.
                int jump = 0;
                while ((1 << (jump + 1)) <= diff)
                    jump++;
                v = parents[v][jump];
                diff -= 1 << jump;
            }
            return v;
        }
    }

    static long getCost(int u, int v) {
        int lca = getLCA(u, v);
        // costs는 루트노드로 부터 자신까지 비용의 합을 갖고 있기 때문에
        // u ~ v 까지 비용의 합은 costs[u] + costs[v]에 최소공통부모까지의 합 x 2를 빼주면 된다!
        return costs[u] + costs[v] - costs[lca] * 2;
    }

    static int getLCA(int u, int v) {       // 최소 공통 부모를 구하는 메소드
        int diffLevel = Math.abs(levels[u] - levels[v]);        // 차이를 구하고
        while (diffLevel > 0) {     // 그 차이가 0이 될 때까지
            int jump = 0;
            while ((1 << (jump + 1)) <= diffLevel)      // 차이보다 작거나 같은 값까지 jump 값을 증가시켜
                jump++;
            if (levels[u] > levels[v])      // u의 level이 더 크다면 u를 희소배열을 사용하여 2^jump번째 부모노드로 바꾼다.
                u = parents[u][jump];
            else            // 반대라면 v를 2^jump번째 부모노드로 바꾼다
                v = parents[v][jump];
            diffLevel -= 1 << jump;     // 건너 뛴 만큼 차이를 줄여준다.
        }
        //현재 level은 같은 상태지만 같은 조상을 갖는지는 모른다.
        while (u != v) {        // u와 v가 LCA 자체가 될 때까지
            int jump = 0;
            while (parents[u][jump + 1] != parents[v][jump + 1])    // u와 v의 서로 다른 조상을 갖는 가장 마지막 희소배열 위치로 건너뛴다.
                jump++;
            u = parents[u][jump];
            v = parents[v][jump];
        }
        // 위 과정을 반복하면, 마지막엔 최소공통 부모 직전까지 도달할 것이고, 그 때는 jump값의 증가 없이 0을 거쳐, while문을 빠져나올 것이다.
        // 그 때 u와 v는 최소 공통 부모.
        return u;
    }

    static void init(int n, Scanner sc) {       // 배열, 리스트 할당 및 입력 처리!
        levels = new int[n + 1];
        parents = new int[n + 1][(int) Math.ceil((Math.log(n) / Math.log(2))) + 1];     // parents의 배열의 크기를 n에 따라 가장 작은 크기로 만든다.
        costs = new long[n + 1];

        List<List<Edge>> edges = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            edges.add(new ArrayList<>());

        for (int i = 0; i < n - 1; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int w = sc.nextInt();
            edges.get(u).add(new Edge(v, w));
            edges.get(v).add(new Edge(u, w));
        }

        levels[1] = 1;      // 1을 루트 노드로 생각.
        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        boolean[] visited = new boolean[n + 1];
        visited[1] = true;
        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (Edge edge : edges.get(current)) {
                if (visited[edge.anotherOne])       // 이미 방문했던 곳이라면 패쓰
                    continue;

                visited[edge.anotherOne] = true;
                parents[edge.anotherOne][0] = current;      // anotherOne의 parents는 current이며
                costs[edge.anotherOne] = costs[current] + edge.cost;    // cost[anoterOne]은 자신과 루트노드까지의 모든 cost의 합을 저장해둔다.
                levels[edge.anotherOne] = levels[current] + 1;      // level은 부모 노드의 level + 1
                queue.add(edge.anotherOne);             // 다음엔 anoterOne의 child를 방문하자!
            }
        }

        for (int col = 1; col < parents[0].length; col++) {
            for (int row = 1; row < parents.length; row++)
                parents[row][col] = parents[parents[row][col - 1]][col - 1];        // parents 의 희소배열을 채운다
        }
    }
}