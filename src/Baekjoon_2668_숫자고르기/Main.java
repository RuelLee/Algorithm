/*
 Author : Ruel
 Problem : Baekjoon 2668번 숫자고르기
 Problem address : https://www.acmicpc.net/problem/2668
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2668_숫자고르기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int[] pairs;
    static boolean[] cycled;

    public static void main(String[] args) throws IOException {
        // 1부터 ~ n까지는 각각 매칭된 수가 있다.
        // 이 때 몇 개의 수를 뽑은 집합과, 매칭된 수의 집합이 일치하는 경우가 있다
        // 이러한 조건을 만족시키며 수를 가장 많이 뽑을 때, 그 때의 수의 개수와 수들을 출력하라
        //
        // DFS 문제
        // '몇 개의 수를 뽑은 집합과 매칭된 수의 집합이 일치한다' 라는 점을 조금 생각해보면
        // 수에서 매칭된 수로 탐색해나갈 때, 사이클이 발생하는 경우와 같다고 생각할 수 있다.
        // 예를 들어
        // 수   : 1 2 3 4 5 6 7
        // 매칭 : 3 1 1 5 5 4 6
        // 으로 주어졌을 대, 1을 살펴보면
        // 1 -> 3 -> 1 로 1에서 출발하여 다시 1로 돌아올 수 있다.
        // 이렇게 사이클이 생기는 수를 최대한 많이 찾아내면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 매칭되는 수들
        pairs = new int[n + 1];
        for (int i = 1; i < pairs.length; i++)
            pairs[i] = Integer.parseInt(br.readLine());

        // 사이클 여부
        cycled = new boolean[n + 1];
        // 사이클이 생기는 수의 개수
        int count = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < pairs.length; i++) {
            // 이전에 계산이 됐다면 다시 계산할 필요는 없다.
            // 그렇지 않을 경우에만 findAnswer 메소드를 호출.
            if (!cycled[i])
                findAnswer(i, new boolean[n + 1]);

            // 만약 i에서 시작해서 i로 다시 돌아올 수 있다면
            // count 증가, 정답 중 하나로 출력.
            if (cycled[i]) {
                count++;
                sb.append(i).append("\n");
            }
        }

        // 최종적으로 찾아진 사이클이 발생하는 수의 개수와 수들을 출력한다.
        sb.insert(0, count + "\n");
        System.out.print(sb);
    }

    // dfs를 통해 사이클이 생기는 여부를 확인한다.
    static void findAnswer(int n, boolean[] visited) {
        // 방문한 적이 있다면, n -> n이 성립.
        if (visited[n]) {
            // n에 대해서는 사이클이 성립함을 체크해주고, 종료.
            cycled[n] = true;
            return;
        }

        // 만약 방문한 적이 없다면 방문 체크 후,
        visited[n] = true;
        // 매칭된 수로 findAnswer를 재귀 호출.
        findAnswer(pairs[n], visited);
    }
}