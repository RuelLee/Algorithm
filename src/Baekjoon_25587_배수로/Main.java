/*
 Author : Ruel
 Problem : Baekjoon 25587번 배수로
 Problem address : https://www.acmicpc.net/problem/25587
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25587_배수로;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] parents, ranks, drains, rainfalls, members;
    static int count = 0;

    public static void main(String[] args) throws IOException {
        // n개의 도시에 각각 강우량과 배수로 용량이 주어진다.
        // 공사를 통해 각 도시의 배수로 용량을 공유할 수 있다.
        // a, b 두 도시를 공사를 통해 배수로 용량을 공유한다면
        // 두 도시의 강우량 합이 배수로 용량 합 이하라면 홍수가 나지 않는다.
        // m개의 쿼리가 주어진다.
        // 쿼리는 아래와 같이 두 종류일 때, 쿼리에 답하라
        // 1 x y : x와 y 도시에 대해 공사를 진행한다.
        // 2 : 현재 홍수가 나는 도시의 개수를 출력한다
        //
        // 분리 집합 문제
        // 분리 집합을 통해
        // 각 집합에 속하는 도시의 수, 강우량 합, 배수로 용량 합을 갖고서
        // 홍수가 발생하는 도시의 개수를 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 도시, m개의 쿼리
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 각 도시가 속한 집합의 대표
        parents = new int[n + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n + 1];
        // 각 집합에 속한 도시의 수
        members = new int[n + 1];
        Arrays.fill(members, 1);
        
        // 배수로 용량
        drains = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < drains.length; i++)
            drains[i] = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        // 강우량
        rainfalls = new int[n + 1];
        for (int i = 1; i < rainfalls.length; i++) {
            rainfalls[i] = Integer.parseInt(st.nextToken());
            if (rainfalls[i] > drains[i])
                count++;
            // 초기 상태의 홍수가 발생하는 도시의 수 계산
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            // 1번 쿼리가 주어진다면
            if (Integer.parseInt(st.nextToken()) == 1) {
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                // a, b가 서로 다른 집합에 속해있다면
                // 두 도시를 같은 집합으로 묶는다.
                if (findParent(a) != findParent(b))
                    union(a, b);
            } else      // 2번 쿼리라면 현재 홍수가 발생하는 도시의 수를 출력한다.
                sb.append(count).append("\n");
        }
        System.out.print(sb);
    }
    
    // 두 도시에 공사를 진행해 배수로 용량을 공유한다.
    static void union(int a, int b) {
        // 두 도시가 속한 집합의 대표
        int pa = findParent(a);
        int pb = findParent(b);
        
        // 두 도시가 속한 집합의 모든 도시에 대해
        // 현재 홍수가 발생하는 도시의 수
        int current = (rainfalls[pa] > drains[pa] ? members[pa] : 0) +
                (rainfalls[pb] > drains[pb] ? members[pb] : 0);
        
        // pa의 ranks가 pb보다 높다면
        // pb가 pa에 속하게끔한다.
        if (ranks[pa] >= ranks[pb]) {
            // 대표 지정
            parents[pb] = pa;
            // 그룹에 속한 도시의 수
            members[pa] += members[pb];
            // 강우량 합
            rainfalls[pa] += rainfalls[pb];
            // 배수로 용량 합
            drains[pa] += drains[pb];
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else {        // pb가 pa에 속하는 경우.
            parents[pa] = pb;
            members[pb] += members[pa];
            rainfalls[pb] += rainfalls[pa];
            drains[pb] += drains[pa];
        }
        
        // 현재 합쳐진 그룹의 대표
        int p = findParent(a);
        // 두 집합이 합쳐짐으로써 발생한 홍수가 발생한 도시 수의 변동량 반영
        count += ((rainfalls[p] > drains[p] ? members[p] : 0) - current);
    }

    // n 도시가 속한 집합의 대표를 찾는다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }
}