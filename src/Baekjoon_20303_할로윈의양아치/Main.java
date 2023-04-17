/*
 Author : Ruel
 Problem : Baekjoon 20303번 할로윈의 양아치
 Problem address : https://www.acmicpc.net/problem/20303
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20303_할로윈의양아치;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] personnel, parents, candies;

    public static void main(String[] args) throws IOException {
        // 스브러스는 거리를 돌아다니며 아이들의 사탕을 뺐는다.
        // 사탕을 뺐을 때는 해당 아이의 친구들의 사탕까지 모두 뺐는다.(친구의 친구는 친구)
        // 하지만 k명 이상의 아이에게서 사탕을 뺐는다면 울음소리가 공명하여 어른들이 거리로 나온다.
        // n명의 아이, m명의 친구관계, k가 주어진다.
        // 최대한 뺐을 수 있는 사탕의 수는?
        //
        // 분리 집합 + 배낭 문제
        // 주어지는 친구관계를 통해 해당하는 인원의 수와 사탕의 수로 그룹을 나눈다.
        // 그 후 k명 미만으로 그룹을 조합하여 최대 사탕의 수를 배낭 문제 해법으로 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 주어지는 아이, 친구 관계, 최소 울음소리 공명 인원
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 사탕의 수
        candies = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 그룹의 대표
        parents = new int[n];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        // 그룹에 속한 인원의 수
        personnel = new int[n];
        Arrays.fill(personnel, 1);

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            // a와 b가 다른 그룹이라면 두 그룹을 합치낟.
            if (findParent(a) != findParent(b))
                union(a, b);
        }

        // 해당 그룹에 대해 계산하였는지 여부.
        boolean[] visited = new boolean[n];
        // 그룹을 저장한다.
        List<int[]> groups = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            // i가 속한 그룹의 대표
            int parent = findParent(i);
            // 해당 그룹을 처음 방문하였다면
            if (!visited[parent]) {
                // 해당 그룹에 속한 인원과 사탕의 수를 리스트에 저장
                groups.add(new int[]{personnel[parent], candies[parent]});
                // 방문 체크
                visited[parent] = true;
            }
        }

        // 이제 배낭 문제가 되었다.
        // 그룹들을 조합하여 k명 미만이며 최대 사탕의 개수를 구한다.
        int[] dp = new int[k];
        for (int[] g : groups) {
            for (int i = dp.length - 1 - g[0]; i >= 0; i--) {
                if (dp[i + g[0]] < dp[i] + g[1])
                    dp[i + g[0]] = dp[i] + g[1];
            }
        }

        // k - 1명일 때의 최대 사탕의 개수를 출력한다.
        System.out.println(dp[k - 1]);
    }

    // 두 그룹을 하나의 그룹으로 합친다.
    // 합칠 때, 그룹의 인원과 사탕의 수를 한번에 계산한다.
    static void union(int a, int b) {
        int pa = findParent(a);
        int pb = findParent(b);
        
        // a가 속한 그룹의 인원이 더 많다면
        if (personnel[pa] >= personnel[pb]) {
            // b가 속한 그룹을 a의 그룹에 넣고
            parents[pb] = pa;
            // pa 그룹에 pb 그룹 사탕 개수 추가
            candies[pa] += candies[pb];
            // 마찬가지로 인원도 추가
            personnel[pa] += personnel[pb];
        } else {        // 반대로 pb 그룹에 pa 그룹이 속하는 경우
            parents[pa] = pb;
            candies[pb] += candies[pa];
            personnel[pb] += personnel[pa];
        }
    }

    // 그룹의 대표를 찾는다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }
}
