/*
 Author : Ruel
 Problem : Baekjoon 12004번 Closing the Farm (Silver)
 Problem address : https://www.acmicpc.net/problem/12004
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12004_ClosingtheFarm_Silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    static int[] parents, ranks;

    public static void main(String[] args) throws IOException {
        // n개의 헛간이 m개의 도로로 이어져있다.
        // 도로가 잇는 두 헛간에 대한 정보들이 주어지며
        // n개의 헛간을 주어지는 순서대로 폐쇄하고자 한다.
        // 그 때마다, 폐쇄하지 않은 헛간들이 모두 서로 연결되어있는지 여부를 출력하라
        //
        // 분리 집합 문제
        // 폐쇄하는 순서를 반대로 살펴보며 역으로 연결해나가면서
        // 등장한 모든 헛간들이 연결되어있는지 확인하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 헛간, m개의 도로
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 분리 집합을 위한 초기화
        parents = new int[n + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n + 1];

        // m개의 도로
        List<List<Integer>> connections = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connections.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            connections.get(x).add(y);
            connections.get(y).add(x);
        }

        // 스택을 통해 폐쇄하는 헛간을 역순으로 살펴본다.
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++)
            stack.push(Integer.parseInt(br.readLine()));

        StringBuilder sb = new StringBuilder();
        boolean[] opened = new boolean[n + 1];
        while (!stack.isEmpty()) {
            // 이번에 연결할 헛간
            int current = stack.pop();
            opened[current] = true;

            // 도로를 살펴보며, 열려있는 헛간들에 대해 연결이 안되어있는지 여부를 살펴보고
            // 연결한다.
            for (int near : connections.get(current)) {
                if (opened[near] && findParent(current) != findParent(near))
                    union(current, near);
            }
            
            // 열려있는 모든 헛간들이 연결되어있는지 확인한다.
            boolean fullConnected = true;
            for (int i = 1; i < opened.length; i++) {
                if (opened[i] && findParent(current) != findParent(i)) {
                    fullConnected = false;
                    break;
                }
            }

            // 역순으로 살펴보기 때문에, 답도 역순으로 기록한다.
            sb.insert(0, (fullConnected ? "YES" : "NO") + "\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }

    // a가 속한 그룹과 b가 속한 그룹을 하나의 그룹으로 묶는다.
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

    // n이 속한 그룹의 대표를 찾는다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }
}