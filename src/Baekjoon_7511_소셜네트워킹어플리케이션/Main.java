/*
 Author : Ruel
 Problem : Baekjoon 7511번 소셜 네트워킹 어플리케이션
 Problem address : https://www.acmicpc.net/problem/7511
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_7511_소셜네트워킹어플리케이션;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] parents;
    static int[] ranks;

    public static void main(String[] args) throws IOException {
        // SNS에서 서로 간의 친구 관계 그래프에서 경로가 있는지 알고 싶다.
        // t개의 테스트케이스
        // n명의 유저, k개의 친구 관계(a, b)가 주어진다
        // 그리고 m개의 경로가 있는지 확인할 두 유저(u, v)가 주어진다.
        // 경로가 있다면 1, 없다면 0을 출력한다.
        //
        // 분리 집합 문제
        // 친구관계에 따라 같은 그룹으로 묶고, m개의 쿼리에 대해
        // 같은 집합이면 1, 아니면 0을 출력한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        // 테스트케이스
        for (int t = 0; t < testCase; t++) {
            int n = Integer.parseInt(br.readLine());
            // parents, ranks 배열 초기화.
            init(n);
            
            // k개의 친구 관계
            int k = Integer.parseInt(br.readLine());
            for (int i = 0; i < k; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                // 서로 다른 집합이라면 하나의 집합으로 묶는다.
                if (findParents(a) != findParents(b))
                    union(a, b);
            }
            
            // 경로가 존재하는지 알아볼 m개의 유저 쌍.
            int m = Integer.parseInt(br.readLine());
            sb.append("Scenario ").append((t + 1)).append(":").append("\n");
            for (int i = 0; i < m; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());

                // u와 v가 같은 집합이라면 1, 아니라면 0을 출력한다.
                sb.append(findParents(u) == findParents(v) ? 1 : 0).append("\n");
            }
            sb.append("\n");
        }
        System.out.print(sb);
    }

    // 배열 초기화.
    static void init(int n) {
        parents = new int[n + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n + 1];
    }

    // a와 b가 속한 집합들을 하나의 집합으로 합친다.
    static void union(int a, int b) {
        int pa = findParents(a);
        int pb = findParents(b);

        // ranks에 따라 ranks 더 큰 집합에 더 작은 집합을 포함시킨다.
        if (ranks[pa] >= ranks[pb]) {
            parents[pb] = pa;
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else
            parents[pa] = pb;
    }

    // 집합의 대표를 찾는다.
    static int findParents(int n) {
        if (parents[n] == n)
            return n;
        // 경로 단축.
        return parents[n] = findParents(parents[n]);
    }
}