/*
 Author : Ruel
 Problem : Baekjoon 27945번 슬슬 가지를 먹지 않으면 죽는다
 Problem address : https://www.acmicpc.net/problem/27945
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_27945_슬슬가지를먹지않으면죽는다;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    static int[] parents, ranks;

    public static void main(String[] args) throws IOException {
        // 1 ~ n까지의 번호가 붙은 요리 학원들과 m개의 양방향 도로가 주어진다.
        // i번째 도로에는 ti일에만 여는 디저트 노점이 존재한다. ti는 모두 다르다.
        // n개의 요리 학원을 모두 연결하는 n-1개의 도로를 선택하고
        // 날마다 디저트 노점을 들린다고 할 때
        // 더 이상 디저트 노점을 들리지 못하게 되는 날의 최댓값을 구하라
        //
        // 분리 집합 문제
        // 또한 n 개의 요리 학원들을 n-1개의 도로로 이으려면 같은 요리 학원을 잇는 복수의 경로가 존재해서는 안된다.
        // 다시 말해 최소 스패닝 트리로 연결되어야한다.
        // 따라서 
        // 매일 매일 디저트 노점에 들려야하므로 도로를 ti를 기준으로 정렬한다.
        // 매일 노점을 갈 수 있는지 확인하며, 
        // 양쪽의 요리 학원이 직간접적으로 이어지지 않았다면(= 같은 집합에 속해있지 않다면)
        // 도로를 잇는다.(= 같은 집합에 포함시킨다)
        // 위 과정을 더 이상할 수 없을 때까지 진행하며, 그 때가 더 이상 노점을 들리지 못하는 날이다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 요리 학원, m개의 도로
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 도로들
        int[][] roads = new int[m][3];
        for (int i = 0; i < roads.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < roads[i].length; j++)
                roads[i][j] = Integer.parseInt(st.nextToken());
        }
        // ti를 기준으로 오름차순 정렬
        Arrays.sort(roads, Comparator.comparingInt(o -> o[2]));

        // 분리 집합을 위한 parents와 ranks
        parents = new int[n + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n + 1];

        // 첫째날부터 차근차근 디저트 노점을 들린다.
        int answer = 1;
        for (int i = 0; i < roads.length; i++) {
            // 오늘 방문할 디저트 노점이 있는 도로의 양 쪽 끝에 있는 요리 학원들이
            // 직간접적으로 연결되어있지 않다면
            // 해당 도로를 선택한다.
            if (roads[i][2] == answer && findParent(roads[i][0]) != findParent(roads[i][1])) {
                union(roads[i][0], roads[i][1]);
                answer++;
            } else      // 만약 오늘 방문할 디저트 노점이 없거나 요리 학원들이 서로 연결되어있었다면 더 이상 도로를 잇지 못한다.
                break;
        }
        
        // 답 출력
        System.out.println(answer);
    }

    // a와 b를 같은 집합으로 묶는다.
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

    // n이 속한 집합의 대표를 출력한다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }
}