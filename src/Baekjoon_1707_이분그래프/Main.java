/*
 Author : Ruel
 Problem : Baekjoon 1707번 이분 그래프
 Problem address : https://www.acmicpc.net/problem/1707
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1707_이분그래프;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int[] team;
    static boolean[] visited;
    static List<List<Integer>> connections;

    public static void main(String[] args) throws IOException {
        // k개 테스트케이스가 주어지고
        // v개의 정점, e개의 간선이 주어진다
        // 집합을 둘로 분할하여 각 집합에 속한 정점끼리는 인접하지 않도록 분할할 수 있다면 이를 이분그래프라고 부른다
        // 주어진 데이터가 이분 그래프인지 판별하라
        //
        // 완전탐색문제
        // 연결된 정점들을 방문하며 분할할 수 있는지 살펴본다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int v = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());

            // 각 정점들 간의 간선을 리스트로 저장한다.
            connections = new ArrayList<>();
            for (int i = 0; i < v + 1; i++)
                connections.add(new ArrayList<>());
            for (int i = 0; i < e; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                connections.get(a).add(b);
                connections.get(b).add(a);
            }

            // 방문 체크
            visited = new boolean[v + 1];
            // 그룹 체크.
            team = new int[v + 1];
            boolean bipartiteGraph = true;
            for (int i = 1; i < team.length; i++) {
                // 방문하지 않았고, 이분 그래프로 나누는 것이 불가능하다면
                // bipartiteGraph를 false로 만들고 반복문을 종료한다.
                if (!visited[i] && !isBipartiteGraph(i)) {
                    bipartiteGraph = false;
                    break;
                }
            }
            // bipartiteGraph에 따라 값을 출력한다.
            sb.append(bipartiteGraph ? "YES" : "NO").append("\n");
        }
        System.out.print(sb);
    }

    // n번 정점에서부터 DFS로 탐색한다.
    static boolean isBipartiteGraph(int n) {
        // 방문체크
        visited[n] = true;
        // 연결된 정점들을 살펴본다
        for (int next : connections.get(n)) {
            // 이미 방문한 적이 있다면
            if (visited[next]) {
                // 그룹이 할당되어있을 것인데, n과 next가 동일한 그룹이라면 이분 그래프가 아니다.
                // false 리턴.
                if (team[n] == team[next])
                    return false;
            } else {
                // 방문한 적이 없다면
                // next를 n가 다른 그룹으로 표시하고
                // isBipartiteGraph 를 재귀적으로 부른다.
                // 리턴값이 false가 돌아온다면 이분 그래프가 불가능한 경우.
                team[next] = (team[n] + 1) % 2;
                if (!isBipartiteGraph(next))
                    return false;
            }
        }
        // n가 관련된 모든 정점을 계산해도 false가 돌아오지 않았다면
        // 아직 n과 직간접적으로 연결된 정점들은 이분그래프가 성립한다
        // true 리턴.
        return true;
    }
}