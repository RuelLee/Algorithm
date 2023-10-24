/*
 Author : Ruel
 Problem : Baekjoon 25187번 고인물이 싫어요
 Problem address : https://www.acmicpc.net/problem/25187
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25187_고인물이싫어요;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] ranks, parents, tanks, newbie, vet;

    public static void main(String[] args) throws IOException {
        // n개의 물탱크에 대한 청정수/고인물에 대한 정보와
        // 물탱크가 연결된 m개의 파이프 정보가 주어진다.
        // 각 물탱크가 파이프로 연결된 그룹에 청정수의 탱크가 더 많다면
        // 해당 물탱크에서 청정수를, 그렇지 않다면 고인물을 얻는다.
        // q개의 물탱크에서 얻는 물의 정보를 출력하라
        //
        // 분리집합 문제
        // 분리집합을 통해 주어지는 파이프 정보를 통해 물탱크들을 하나의 그룹으로 묶고
        // 해당 그룹에서 청정수 탱크와 고인물 탱크의 수를 계산해나간다.
        // 나중에 쿼리를 처리할 때는 해당 탱크가 속한 그룹의 정보를 바탕으로 답안을 작성한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // n개의 물탱크, m개의 파이프, q개의 질문
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        
        // n이 속한 그룹에 청정수, 고인물 탱크의 수
        newbie = new int[n];
        vet = new int[n];
        tanks = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 초기값으로 물탱크 자신의 청정수/고인물 표시
        for (int i = 0; i < tanks.length; i++) {
            if (tanks[i] == 1)
                newbie[i]++;
            else
                vet[i]++;
        }
        
        // 분리집합의 연산을 줄이기 위한 rank
        ranks = new int[n];
        // 집합의 대표
        parents = new int[n];
        for (int i = 0; i < parents.length; i++)
            parents[i] = i;

        // 파이프를 연결한다.
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()) - 1;
            int v = Integer.parseInt(st.nextToken()) - 1;

            // 두 물탱크가 다른 그룹일 경우 하나의 그룹으로 묶는다.
            if (findParent(u) != findParent(v))
                union(u, v);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            // 해당하는 물탱크의 그룹을 찾고
            int group = findParent(Integer.parseInt(br.readLine()) - 1);
            // 해당 그룹의 청정수 탱크 수와 고인물 탱크의 수를 비교하여 답안 작성
            sb.append(newbie[group] > vet[group] ? 1 : 0).append("\n");
        }
        System.out.print(sb);
    }

    // a와 b 물탱크 그룹을 하나의 그룹으로 묶는다.
    static void union(int a, int b) {
        // 두 그룹의 대표를 찾아
        int pa = findParent(a);
        int pb = findParent(b);

        // ranks를 비교하여 더 큰 쪽에 속하게 만든다.
        if (ranks[pa] >= ranks[pb]) {
            // pa가 더 크거나 같으므로 pb가 pa에 속한다.
            // 청정수와 고인물 탱크의 수 반영
            newbie[pa] += newbie[pb];
            vet[pa] += vet[pb];
            // 그룹의 대표 변경
            parents[pb] = pa;
            // 만약 rank가 같았다면 이번 연산을 통해 pa의 rank 증가.
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else {
            // pa가 pb의 그룹에 속하는 경우.
            newbie[pb] += newbie[pa];
            vet[pb] += vet[pa];
            parents[pa] = pb;
        }
    }

    // 그룹의 대표를 찾는다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }
}