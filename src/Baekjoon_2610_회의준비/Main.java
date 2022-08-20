/*
 Author : Ruel
 Problem : Baekjoon 2610번 회의준비
 Problem address : https://www.acmicpc.net/problem/2610
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2610_회의준비;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] parents;
    static int[] ranks;

    public static void main(String[] args) throws IOException {
        // n개의 사람과, m개의 관계가 주어진다.
        // 서로 관계가 있는 사람은 반드시 같은 그룹으로 묶여야한다.
        // 각자의 의사는 자신과 관계가 있는 사람에게만 알릴 수 있기 때문에, 그룹의 대표자는
        // 모든 그룹원과 직간접적 관계가 가장 가까운 사람으로 정해, 의견이 전달되는 시간을 최소화하기로 한다.
        // 이 때, 모든 그룹의 수와 각 그룹의 대표자를 오름차순으로 출력하라.
        //
        // 분리 집합과 플로이드-와샬 문제
        // 두 개의 알고리즘을 복합적으로 사용할 수 있어야한다.
        // 우선 주어지는 관계를 토대로, 그룹을 묶으며 동시에 인접행렬을 통해 관계를 설정해준다.
        // 그리고 인접 행렬을 통해 각 그룹에서 개인이 다른 그룹원들과의 관계가 어느 정도인지 계산해준다.
        // 그 후, 모든 개인을 살펴보며, 해당 그룹에서 각 그룹원들과 가장 먼 관계값이 최소인지 살펴보며 대표자를 선정해준다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        init(n);
        // 인접행렬
        int[][] adjMatrix = new int[n + 1][n + 1];
        // 사람은 최대 100명이 주어지므로, 범위를 넘는 101 값으로 초기화해준다.
        for (int[] am : adjMatrix)
            Arrays.fill(am, 101);

        // 관계의 수.
        int m = Integer.parseInt(br.readLine());
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            // a와 b에 거리 1을 설정해주고
            adjMatrix[a][b] = adjMatrix[b][a] = 1;
            // 같은 그룹으로 묶는다.
            if (findParent(a) != findParent(b))
                union(a, b);
        }
        
        // 플로이드-와샬
        for (int via = 0; via < n + 1; via++) {
            for (int start = 0; start < n + 1; start++) {
                if (start == via)
                    continue;
                for (int end = 0; end < n + 1; end++) {
                    if (end == via || end == start)
                        continue;

                    if (adjMatrix[start][end] > adjMatrix[start][via] + adjMatrix[via][end])
                        adjMatrix[start][end] = adjMatrix[start][via] + adjMatrix[via][end];
                }
            }
        }

        // 각 그룹에 대표자를 기록할 것이다.
        // 각 그룹에 해당하는 가장 먼 관계거리의 최소값과 그 때의 대표자를 계산한다.
        int[][] representatives = new int[n + 1][2];
        // 가장 먼 관계 거리 값을 큰 값으로 초기화.
        for (int i = 0; i < n + 1; i++)
            representatives[i][1] = Integer.MAX_VALUE;

        // 그룹의 개수를 센다.
        int groupCounter = 0;
        for (int i = 1; i < n + 1; i++) {
            int group = findParent(i);
            // 처음 만나는 그룹이라면 groupCounter 증가/
            if (representatives[group][0] == 0)
                groupCounter++;
            // 가장 먼 관계 거리값.
            int costTime = 0;
            for (int j = 1; j < n + 1; j++) {
                // 서로 관계가 없다면 건너뛰고,
                if (adjMatrix[i][j] == 101)
                    continue;
                // 관계가 있다면 그 때의 거리가 최대값을 갱신하는지 살펴본다.
                costTime = Math.max(costTime, adjMatrix[i][j]);
            }

            // 그리고 해당 그룹에서 위에 계산된 관계거리가 최소값인지 확인하고
            // 맞다면 해당 값과 이 때의 사람의 번호를 기록해둔다.
            if (representatives[group][1] > costTime) {
                representatives[group][0] = i;
                representatives[group][1] = costTime;
            }
        }
        
        StringBuilder sb = new StringBuilder();
        // 그룹의 개수 출력
        sb.append(groupCounter).append("\n");
        // representatives 배열에서 대표자가 0인(=그룹이 없는) 값을 제외, 그리고 오름차순 정렬하여 출력해준다.
        for (int representative : Arrays.stream(representatives).mapToInt(r -> r[0]).filter(value -> value != 0).sorted().toArray())
            sb.append(representative).append("\n");
        System.out.print(sb);
    }

    // 유니온 연산.
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

    // 그룹의 대표를 찾는 메소드
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }

    // parents, ranks 초기화.
    static void init(int n) {
        parents = new int[n + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n + 1];
    }
}