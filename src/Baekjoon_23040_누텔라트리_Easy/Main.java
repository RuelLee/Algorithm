/*
 Author : Ruel
 Problem : Baekjoon 23040번 누텔라 트리 (Easy)
 Problem address : https://www.acmicpc.net/problem/23040
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23040_누텔라트리_Easy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] parents, ranks;

    public static void main(String[] args) throws IOException {
        // 정점이 N개인 트리가 주어진다.
        // n-1개의 간선과 각 정점의 색이 주어진다.
        // 시작노드가 검정이고, 뒤에 이어지는 노드들이 빨간색이라면 위와 같은 경로를 누텔라 경로라고 한다.
        // 총 몇 개의 누텔라 경로를 찾을 수 있는가?
        //
        // 분리 집합 문제
        // 연속한 빨간 정점들로 하나씩 경로가 생겨난다.
        // 따라서 결국에는 검정색 정점에 인접한 연속한 빨간색 정점의 수가 누텔라 경로의 수가 된다.
        // 따라서 인접한 빨간 정점들끼리 하나의 그룹으로 묶고 그 수를 센다.
        // 그 후, 검정 노드들을 돌아다니며, 인접한 빨간 정점 그룹을 계산하고,
        // 그룹원의 수만큼을 세어가면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 정점의 수
        int n = Integer.parseInt(br.readLine());
        
        // 간선들
        List<List<Integer>> edges = new ArrayList<>();
        for (int i = 0; i < n; i++)
            edges.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()) - 1;
            int v = Integer.parseInt(st.nextToken()) - 1;

            edges.get(u).add(v);
            edges.get(v).add(u);
        }
        
        // 분리 집합을 위한 초기화
        parents = new int[n];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n];
        
        // 정점의 색
        String colors = br.readLine();
        for (int i = 0; i < colors.length(); i++) {
            // 만약 검정이라면 건너뛰고
            if (colors.charAt(i) == 'B')
                continue;

            // 빨강이라면 인접한 다른 빨간 노드를 같은 그룹으로 묶는다.
            for (int near : edges.get(i)) {
                if (colors.charAt(near) == 'R' && findParents(i) != findParents(near))
                    union(i, near);
            }
        }

        // 각 그룹에 속한 빨간 정점의 수를 센다.
        int[] groupCounts = new int[n];
        for (int i = 0; i < parents.length; i++)
            groupCounts[findParents(i)]++;

        // 누텔라 경로의 수를 센다.
        // 그 범위가 약 21억을 넘을 수 있으므로 long 타입으로 계산한다.
        long count = 0;
        for (int i = 0; i < colors.length(); i++) {
            // 빨간 노드일 경우 시작점이 될 수 없으므로 건너뛴다.
            if (colors.charAt(i) == 'R')
                continue;

            // 해쉬셋을 통해 인접한 빨간 정점들의 그룹을 계산한다.
            HashSet<Integer> hashSet = new HashSet<>();
            for (int near : edges.get(i)) {
                // 빨간 정점이 인접하다면 해당 정점의 그룹 번호를 기록
                if (colors.charAt(near) == 'R')
                    hashSet.add(findParents(near));
            }

            // 계산된 인접한 그룹 번호를 가지고서
            // 누텔라 경로의 수를 계산한다.
            // 그 수는 해당 검정색 노드에 인접한, 연속한 빨간 노드의 개수와 같다.
            for (int group : hashSet)
                count += groupCounts[group];
        }
        
        // 답안 출력
        System.out.println(count);
    }

    static void union(int a, int b) {
        int pa = findParents(a);
        int pb = findParents(b);

        if (ranks[pa] >= ranks[pb]) {
            parents[pb] = pa;
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else
            parents[pa] = pb;
    }

    static int findParents(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParents(parents[n]);
    }
}