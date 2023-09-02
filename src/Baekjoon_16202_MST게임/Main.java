/*
 Author : Ruel
 Problem : Baekjoon 16202번 MST 게임
 Problem address : https://www.acmicpc.net/problem/16202
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16202_MST게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] parents, ranks;

    public static void main(String[] args) throws IOException {
        // n개의 정점, m개의 양방향 간선이 주어진다.
        // 각 턴마다 최소가중치의 간선을 하나씩 제거해나가면서 MST를 만들고
        // 그 때의 가중치 합을 출력한다.
        // MST를 만들 수 없다면 가중치 합은 0이다.
        //
        // 최소 스패닝 트리 문제
        // 간선이 가중치에 대해 오름차순으로 주어지므로 이를 이용하기 위해
        // kruskal 알고리즘이 더 유리하다고 생각했다.
        // 간선에 대해 가중치가 적은 것부터 양 정점을 잇기 때문.
        /// 따라서 매 턴, kruskal 알고리즘을 통해 MST를 만들고, 그 때의 가중치 합을 기록한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 정점, m개의 간선, k번의 턴
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 간선
        // 가중치가 1인 간선부터 m인 간선까지 주어진다.
        int[][] edges = new int[m][];
        for (int i = 0; i < edges.length; i++)
            edges[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        StringBuilder sb = new StringBuilder();
        // 간선을 제거하여 더 이상 MST가 이루어지지 않는다면
        // 이어지는 다음 턴들에서도 MST가 불가능하다.
        // 이를 boolean을 통해 나타내고 연산을 줄인다.
        boolean possible = true;
        for (int i = 0; i < k; i++) {
            // 가중치 합
            int cost = 0;
            // 이전 턴에 MST를 만드는 것이 가능했다면
            if (possible) {
                // parents 배열과 ranks 배열 초기화
                parents = new int[n + 1];
                for (int j = 1; j < parents.length; j++)
                    parents[j] = j;
                ranks = new int[n + 1];

                // 현재 턴이 i턴이므로
                // (i - 1)이하의 간선들을 사용할 수 없다.
                // i번째 간선부터 살펴보며, 두 정점이 스패닝 트리에 속해있지 않다면 잇는 과정을 반복한다.
                for (int j = i; j < edges.length; j++) {
                    if (findParent(edges[j][0]) != findParent(edges[j][1])) {
                        union(edges[j][0], edges[j][1]);
                        cost += (j + 1);
                    }
                }

                // 모든 정점이 하나의 스패닝 트리에 속해있는지 확인한다.
                int group = findParent(1);
                for (int j = 2; j <= n; j++) {
                    // 그렇지 않은 경우
                    if (findParent(j) != group) {
                        // possible을 false를 줘, 이후 턴들에 대해 연산을 줄이고
                        possible = false;
                        // 가중치 합을 초기화한다.
                        cost = 0;
                        break;
                    }
                }
            }

            // 이번 턴의 가중치 합을 기록한다.
            sb.append(cost).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        // k개의 턴에 대한 전체 MST 가중치 합 출력
        System.out.println(sb);
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

    // n이 속한 그룹의 대표를 찾는다.
    static int findParent(int n) {
        return parents[n] == n ? n : (parents[n] = findParent(parents[n]));
    }
}