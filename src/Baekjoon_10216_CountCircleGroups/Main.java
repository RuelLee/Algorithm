/*
 Author : Ruel
 Problem : Baekjoon 10216번 Count Circle Groups
 Problem address : https://www.acmicpc.net/problem/10216
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10216_CountCircleGroups;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int[] parents, ranks;

    public static void main(String[] args) throws IOException {
        // n개의 진영의 좌표와 각 진영마다 세워진 통신탑의 통신 거리가 주어진다.
        // 서로 통신거리가 닿는 진영들은 한 그룹처럼 행동한다.
        // 그룹의 개수를 구하라
        //
        // 분리 집합 문제
        // 거리와 통신 거리에 따라 하나의 그룹으로 묶는 문제
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            // n개의 진영
            int n = Integer.parseInt(br.readLine());
            // 각 진영의 위치와 통신 거리
            int[][] camps = new int[n][];
            for (int i = 0; i < camps.length; i++)
                camps[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            
            // 분리 집합을 위한 두 배열
            // 그룹의 대표
            parents = new int[n];
            for (int i = 1; i < parents.length; i++)
                parents[i] = i;
            // 연산 단축을 위한 rank
            ranks = new int[n];
            
            // 처음에는 n개의 그룹
            int count = n;
            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {
                    // 두 진영이 하나의 그룹으로 묶인다면
                    if (findParents(i) != findParents(j) && circleMeet(camps[i], camps[j])) {
                        // 같은 그룹으로 묶고, 그룹의 수를 하나 감소
                        union(i, j);
                        count--;
                    }
                }
            }
            sb.append(count).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }

    // 두 진영을 하나의 그룹으로 묶는다.
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
    
    // 그룹의 대표
    static int findParents(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParents(parents[n]);
    }
    
    // 두 진영이 만나는지 확인하는 메소드
    static boolean circleMeet(int[] camp1, int[] camp2) {
        return Math.pow(camp1[0] - camp2[0], 2) + Math.pow(camp1[1] - camp2[1], 2)
                <= Math.pow(camp1[2] + camp2[2], 2);
    }
}