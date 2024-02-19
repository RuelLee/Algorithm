/*
 Author : Ruel
 Problem : Baekjoon 28251번 나도리합
 Problem address : https://www.acmicpc.net/problem/28251
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28251_나도리합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] parents, ranks, nadoris;
    static long[] sum, powers;

    public static void main(String[] args) throws IOException {
        // n개의 나도리가 각각의 전투력을 갖고 있다.
        // 이들을 묶어 그룹을 만들면, 그 그룹의 전투력은
        // 서로 다른 두 나도리 전투력 곱의 합과 같다
        // 예를 들어, (1, 2, 3)의 나도리가 주어진다면 해당 그룹의 전투력은
        // 1 * 2 + 1 * 3 + 2 * 3 = 11이다
        // q개의 쿼리로, 두 다도리가 속한 그룹을 합치는 과정을 진행할 때
        // 합쳐진 그룹의 전투력을 출력하라
        //
        // 분리 집합 문제
        // 각 집합에 해당하는 각각의 나도리 전투력 합과 그룹 전투력을 모두 계산하여
        // 두 집합이 합쳐질 때, 생성되는 새로운 집합의 전투력을 계산할 때 활용한다.
        // a, b 그룹을 합쳐 만드는 새로운 그룹 c의 전투력은
        // a 각각의 나도리 전투력 합 * b 각각의 나도리 전투력 합 + a 그룹의 전투력 + b그룹의 전투력
        // 으로 나타낼 수 있다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 나도리, q개의 쿼리
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        
        // 초기 설정
        // 나도리들의 전투력
        nadoris = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 각 나도리가 속한 그룹의 대표
        parents = new int[n];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        // 연산을 줄이기 위한 ranks
        ranks = new int[n];
        // 집합 속한 나도리들의 전투력 합
        sum = new long[n];
        // 집합의 전투력
        powers = new long[n];
        for (int i = 0; i < nadoris.length; i++)
            sum[i] = nadoris[i];
        
        // q개의 쿼리 처리
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            
            // a, b가 속한 집합이 서로 다를 때만 union 실행
            if (findParent(a) != findParent(b))
                union(a, b);

            // 해당 그룹의 전투력 기록
            sb.append(powers[findParent(a)]).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }

    // a와 b가 속한 그룹을 하나의 그룹으로 묶는다.
    static void union(int a, int b) {
        // a와 b가 속한 그룹의 대표
        int pa = findParent(a);
        int pb = findParent(b);
        
        // pa가 pb보다 랭크가 같거나 높을 때
        if (ranks[pa] >= ranks[pb]) {
            // pb를 pa에 속하게 만든다.
            parents[pb] = pa;
            // 새로운 집합의 전투력
            powers[pa] += sum[pa] * sum[pb] + powers[pb];
            // 새로운 집합에서 나도리 각각의 전투력 합
            sum[pa] += sum[pb];
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else {        // pb가 pa보다 랭크가 높을 때
            parents[pa] = pb;
            powers[pb] += sum[pa] * sum[pb] + powers[pa];
            sum[pb] += sum[pa];
        }
    }

    // n이 속한 집합의 대표를 찾는다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }
}