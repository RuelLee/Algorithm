/*
 Author : Ruel
 Problem : Baekjoon 14699번 관악산 등산
 Problem address : https://www.acmicpc.net/problem/14699
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14699_관악산등산;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static List<List<Integer>> routes;
    static int[] memo;

    public static void main(String[] args) throws IOException {
        // 등산로의 n개의 쉼터와 쉼터들을 잇는 m개의 길이 있다.
        // n개의 쉼터에 대한 높이가 주어진다.
        // 각 쉼터에서 위로 올라가며 최대한 들릴 수 있는 쉼터의 개수를 출력하라
        //
        // 그래프 탐색과 메모이제이션 문제
        // 각 쉼터에서 자신보다 위로 올라가며 들릴 수 있는 최대 쉼터의 개수를 표시한다.
        // 그리고 쉼터를 참고할 때 이미 계산된 결과가 있다면 계산하지 않고, 값을 바로 참고한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        routes = new ArrayList<>(n);
        for (int i = 0; i < n; i++)
            routes.add(new ArrayList<>());
        
        // 쉼터들이 높이
        int[] height = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            
            // a < - > b 쉼터 사이에 통로가 있다.
            // 하지만 우리는 항상 낮은 곳에서 높은 곳으로 올라가므로
            // 낮은 곳 -> 높은 곳의 길만 등록하자.
            routes.get(height[a] < height[b] ? a : b).add(height[a] < height[b] ? b : a);
        }
        
        // 메모이제이션
        memo = new int[n];
        StringBuilder sb = new StringBuilder();
        // 각 쉼터에서 들릴 수 있는 최대 쉼터의 개수를 기록한다.
        for (int i = 0; i < n; i++)
            sb.append(findMaxRests(i)).append("\n");
        // 답안 출력.
        System.out.print(sb);
    }

    // bottom - up 방식으로 DFS를 통해 메모이제이션을 한다.
    static int findMaxRests(int n) {
        // n을 처음 방문했다면(= 기존에 계산된 결과가 없다면)
        if (memo[n] == 0) {
            // 현재 쉼터를 방문할 수 있으므로 최소 값 1
            memo[n] = 1;
            // 다음 쉼터를 방문할 수 있다면, 다음 쉼터 + 1(현재)
            // 값을 비교하며 최대 방문할 수 있는 쉼터의 개수를 찾고,
            // memo[n]에 기록
            for (int next : routes.get(n))
                memo[n] = Math.max(memo[n], findMaxRests(next) + 1);
        }
        // n에서 방문할 수 있는 최대 쉼터의 개수 반환.
        return memo[n];
    }
}