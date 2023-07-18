/*
 Author : Ruel
 Problem : Baekjoon 9938번 방 청소
 Problem address : https://www.acmicpc.net/problem/9938
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9938_방청소;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] parents, ranks, vacancies;

    public static void main(String[] args) throws IOException {
        // n개의 술병과 l개의 서랍이 있다.
        // 각 술병은 두 개씩 수납 가능한 서랍이 정해져있다.
        // 해당 술병들을 순서대로
        // 1. a가 비어있다면 a 서랍에
        // 2. b가 비어있다면 b 서랍에
        // 3. a에 들어있는 술을 다른 서랍으로 이동시킨 후, a에 넣는다.
        // 4. b에 들어있는 술을 다른 서랍으로 이동시킨 후, b에 넣는다.
        // 위 4가지가 불가능한 경우 마셔버린다.
        // 각 술병들을 순서대로 서랍에 넣는다할 때, 각 술을 마시는지, 서랍에 담는지 출력하라
        //
        // 분리집합 문제
        // 위 문제가 분리집합 문제임을 인식하는 것이 어려운 문제
        // 위 네 가지의 경우를 통해 각 술병은 a 혹은 b에 담기게 된다.
        // 따라서 a와 b를 하나의 집합으로 묶고, 해당 집합에 비어있는 공간의 개수를 관리해나가면 된다.
        // 비어있는 공간이 0이라면 더 이상 술을 담을 수 없으므로 마셔야하고
        // 있다면 해당 공간에 술을 보관하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 술병과 l개의 서랍
        int n = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());
        
        // 집합의 대표
        parents = new int[l + 1];
        // 연산 단축을 위한 rank
        ranks = new int[l + 1];
        // 각 집합의 빈 공간
        vacancies = new int[l + 1];
        // 값 초기화
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        Arrays.fill(vacancies, 1);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            // i번째 술이 담길 수 있는 서랍은 a 혹은 b
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            int empty;
            // a와 b가 서로 다른 집합이라면
            if (findParent(a) != findParent(b)) {
                // 두 집합의 하나의 집합으로 묶고
                // 빈 공간의 수를 센다.
                empty = vacancies[findParent(a)] + vacancies[findParent(b)];
                union(a, b);
            } else      // 하나의 집합이라면 해당 집합의 빈 공간
                empty = vacancies[findParent(a)];
            
            // 빈 공간이 없다면 마셔버리고, SMECE 출력
            if (empty == 0)
                sb.append("SMECE").append("\n");
            else {      // 존재한다면 i 술병을 담아야하므로 하나 감소시킨다.
                vacancies[findParent(a)] = empty - 1;
                // 그리고 LADICA 출력
                sb.append("LADICA").append("\n");
            }
        }
        
        // 전체 답안 출력
        System.out.print(sb);
    }
    
    // 두 집합을 하나의 집합으로 묶는다.
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

    // 집합의 대표를 찾는다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }
}