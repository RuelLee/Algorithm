/*
 Author : Ruel
 Problem : Baekjoon 2350번 대운하
 Problem address : https://www.acmicpc.net/problem/2350
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2350_대운하;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int[] parents, ranks;
    static List<List<Integer>> members;

    public static void main(String[] args) throws IOException {
        // n개의 도시와 m개의 운하가 주어진다.
        // 각 운하는 연결하는 도시와 폭이 주어진다.
        // k개의 출발지와 도착지가 주어질 때, 띄울 수 있는 배의 최대 폭은?
        //
        // 최소 스패닝 트리 문제
        // 이걸 어떻게 최소 스패닝 트리로 푸는지 인식하는데 시간이 오래걸렸다.
        // 운하의 수가 최대 10만개로 많이 주어지므로 일일이 매번 BFS를 통해 계산할 수는 없다.
        // 따라서 최소 신장 트리를 통해 도시들을 연결해나가며, 그 때의 최대 운하의 폭을 계산해야한다.
        // 이번 운하가 i j w로 주어진다면
        // i도시와 j도시를 연결하는 운하의 폭이 w라는 것이다.
        // 이 때 i와 이미 연결된 도시 그룹과 j와 연결된 도시 그룹 사이 간에 이동을 하려면
        // 반드시 이번 운하를 이용해야한다.
        // 따라서 현재까지 i 그룹에 속한 도시들과 j에 속한 도시들 간의 이동의 경우
        // 최대 운하의 폭은 w이다.
        // 위 작업을 운하의 폭이 넓은 순으로 계산해나간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 도시, m개의 운하, k개의 노선
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 운하
        int[][] canals = new int[m][];
        for (int i = 0; i < m; i++)
            canals[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 폭에 따라 내림차순 정렬
        Arrays.sort(canals, (o1, o2) -> Integer.compare(o2[2], o1[2]));
        
        // 최소 신장 트리
        ranks = new int[n + 1];
        parents = new int[n + 1];
        // 각 그룹에 속한 도시들
        members = new ArrayList<>();
        for (int i = 0; i < parents.length; i++) {
            parents[i] = i;
            members.add(new ArrayList<>());
            members.get(i).add(i);
        }
        
        // maxWidths[i][j] = i도시에서 j 도시로 갈 때, 최소 운하의 최대 폭
        int[][] maxWidths = new int[n + 1][n + 1];
        for (int[] canal : canals) {
            // 왼쪽 도시가 속한 그룹
            int pa = findParent(canal[0]);
            // 오른쪽 도시가 속한 그룹
            int pb = findParent(canal[1]);
            // 두 그룹이 다를 경우
            if (pa != pb) {
                // 왼쪽 도시와 오른쪽 도시 간의 그룹 간 이동일 경우
                // 반드시 이번 운하를 이용해야한다.
                // 따라서 해당하는 멤버들끼리의 maxWidths는 canals[i][2]이다.
                for (int paMember : members.get(pa)) {
                    for (int pbMember : members.get(pb)) {
                        maxWidths[paMember][pbMember] = maxWidths[pbMember][paMember]
                                = Math.max(maxWidths[pbMember][paMember], canal[2]);
                    }
                }
                // 두 그룹을 하나의 그룹으로 묶는다.
                union(pa, pb);
            }
        }
        
        // k개의 노선에 대한 답안 처리
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            sb.append(maxWidths[a][b]).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }

    // 두 도시를 하나의 그룹으로 묶는다.
    static void union(int a, int b) {
        int pa = findParent(a);
        int pb = findParent(b);

        if (ranks[pa] >= ranks[pb]) {
            parents[pb] = pa;
            // pb에 속한 멤버들을
            // pa에 속하게 한다.
            members.get(pa).addAll(members.get(pb));
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else {
            parents[pa] = pb;
            members.get(pb).addAll(members.get(pa));
        }
    }

    // n이 속한 도시의 대표를 찾는다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }
}