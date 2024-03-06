/*
 Author : Ruel
 Problem : Baekjoon 28277번 뭉쳐야 산다
 Problem address : https://www.acmicpc.net/problem/28277
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28277_뭉쳐야산다;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 집합이 주어지고, q개의 쿼리를 처리한다.
        // 1 a b -> 집합 Sa를 Sa ∪ Sb로 바꾸고, Sb는 공집합으로 바꾼다.
        // 2 a -> 집합 Sa의 크기를 출력한다.
        //
        // 해쉬셋 문제
        // 각 집합의 원소가 최대 50만개까지 주어지므로
        // 큰 집합에 작은 집합의 원소들을 추가하는 방법으로 한다
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 집합, q개의 쿼리
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        
        // n개의 해쉬셋
        HashSet<Integer>[] sets = new HashSet[n];
        for (int i = 0; i < n; i++) {
            sets[i] = new HashSet<>();
            st = new StringTokenizer(br.readLine());
            int size = Integer.parseInt(st.nextToken());
            for (int j = 0; j < size; j++)
                sets[i].add(Integer.parseInt(st.nextToken()));
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            // 두 집합을 합치는 쿼리일 경우
            if (Integer.parseInt(st.nextToken()) == 1) {
                int a = Integer.parseInt(st.nextToken()) - 1;
                int b = Integer.parseInt(st.nextToken()) - 1;

                // 크기를 비교하고
                // b의 크긱 더 크다면, a와 b의 위치를 바꾼다.
                if (sets[b].size() > sets[a].size()) {
                    HashSet<Integer> temp = sets[a];
                    sets[a] = sets[b];
                    sets[b] = temp;
                }
                // a에 b집합을 추가한다.
                sets[a].addAll(sets[b]);
                sets[b].clear();
            } else      // a 집합의 크기를 기록한다.
                sb.append(sets[Integer.parseInt(st.nextToken()) - 1].size()).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}