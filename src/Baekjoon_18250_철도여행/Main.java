/*
 Author : Ruel
 Problem : Baekjoon 18250번 철도 여행
 Problem address : https://www.acmicpc.net/problem/18250
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18250_철도여행;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] parents, ranks;

    public static void main(String[] args) throws IOException {
        // n개의 도시, m개의 노선이 주어진다.
        // 모든 노선을 한번씩 타려고할 때, 필요한 경로의 수는?
        //
        // 분리 집합, 오일러 경로 문제
        // 노선에 연결된 두 도시를 하나의 집합으로 묶으며, 각 도시의 진입, 진출 차수를 계산한다.
        // 한 집합 내에 진입/진출 차수가 짝수로만 이루어져있다면, 출발 도시에서 모든 노선을 거쳐 다시 출발 도시로 도착하는 것이 가능하다
        // -> 이는 한 번의 경로로 모든 노선을 방문할 수 있다.
        // 진입/진출 차수가 홀수인 도시가 있다면, 해당 도시에서 출발, 도착을 해야한다.
        // 2개씩 쌍으로 출발, 도착 도시를 나누면 되므로, (짝수인 도시의 수 + 1) / 2로 계산할 수 있다.
        // 집합 내의 도시가 하나 뿐이라면 노선이 없으므로, 계산하지 않는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 도시, m개의 노선
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        // 분리 집합을 위한 초기화
        parents = new int[n + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n + 1];
        
        // 진입/진출 차수
        int[] inOuts = new int[n + 1];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            
            // 진입/진출 차수 증가
            inOuts[a]++;
            inOuts[b]++;
            // 두 도시를 하나의 집합으로 묶는다.
            union(a, b);
        }

        // 각 집합의 진입/진출 차수의 개수를 홀짝으로 나눠 계산.
        int[][] counts = new int[n + 1][2];
        for (int i = 1; i < counts.length; i++) {
            if (inOuts[i] > 0)
                counts[findParent(i)][inOuts[i] % 2]++;
        }

        int answer = 0;
        for (int i = 1; i < counts.length; i++) {
            // 한 집합에 노선이 하나 이상 포함되어 있고
            // = 진입/진출 차수가 1 이상인 도시들이 있고
            if (counts[i][0] + counts[i][1] > 0) {
                // 진입/진출 차수가 홀수인 도시가 없다면 한 번의 여행으로 모든 노선 방문 가능
                if (counts[i][1] == 0)
                    answer++;
                else        // 홀수인 도시가 있다면 2개씩 짝지어 경로를 생성.
                    answer += (counts[i][1] + 1) / 2;
            }
        }
        // 답 출력
        System.out.println(answer);
    }

    // a와 b를 하나의 집합으로 묶는다.
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

    // n이 속한 집합의 대표를 찾는다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }
}