/*
 Author : Ruel
 Problem : Baekjoon 2188번 축사 배정
 Problem address : https://www.acmicpc.net/problem/2188
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2188_축사배정;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static List<List<Integer>> connections;
    static int[] matched;

    public static void main(String[] args) throws IOException {
        // n마리의 소와 m개의 축사가 주어진다
        // 각 소는 들어가길 원하는 축사가 있다할 때, 최대한 많은 소들을 원하는 축사에 배정하고 싶다
        // 이 때 배정할 수 있는 소의 최대 수는?
        // 이분 매칭 문제!
        // 각 소가 원하는 축사들을 순서대로 보며, 비어있다면 배정하고,
        // 비어있지 않다면 해당 축사에 있는 소를 다른 축사에 배정할 수 있는지 알아본다
        // 다른 축사에 배정했다면, 원래 소를 해당 축사에 배정할 수 있다. 그렇지 않다면 배정이 불가능한 경우.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        connections = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connections.add(new ArrayList<>());

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            for (int j = 0; j < s; j++)
                connections.get(i + 1).add(Integer.parseInt(st.nextToken()));
        }
        // 축사의 상태를 나타낼 것이다.
        matched = new int[m + 1];
        int count = 0;
        for (int i = 1; i < n + 1; i++) {
            // i번 소를 배정하는데 성공했다면 count값을 하나 증가시킨다.
            if (bipartiteMatching(i, new boolean[n + 1]))
                count++;
        }
        System.out.println(count);
    }

    // 이분 매칭
    static boolean bipartiteMatching(int n, boolean[] visited) {
        // 소에 대해서 방문 체크.
        visited[n] = true;
        // n 소가 들어가길 원하는 축사들을 살펴본다.
        for (int cowshed : connections.get(n)) {
            // 축사가 비어있거나, 해당 축사의 소를 다른 축사로 배정하는 것이 가능하다면
            if (matched[cowshed] == 0 || (!visited[matched[cowshed]] && bipartiteMatching(matched[cowshed], visited))) {
                // 해당 축사에 n번 소를 배정.
                matched[cowshed] = n;
                // true 리턴으로 끝냄.
                return true;
            }
        }
        // 모든 축사를 살펴봤다면 배정이 불가능한 경우. false 리턴.
        return false;
    }
}