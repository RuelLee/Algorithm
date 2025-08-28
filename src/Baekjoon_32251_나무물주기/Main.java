/*
 Author : Ruel
 Problem : Baekjoon 32251번 나무 물 주기
 Problem address : https://www.acmicpc.net/problem/32251
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_32251_나무물주기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<List<Integer>> edges;
    static long[] fruits;

    public static void main(String[] args) throws IOException {
        // 나무가 주어진다.
        // n개의 정점과 (n-1)개의 간선으로 이루어져있으며, 두 정점 간의 경로가 유일하게 존재한다.
        // 1번 정점을 나무의 뿌리라고 부르자.
        // 각 정점마다 열매가 존재한다.
        // 한 정점에 물을 주면, 열매 크기 만큼의 물을 먹고, 먹은 만큼 열매가 커진 후, 남은 양을 자식 정점들에게 분배한다.
        // 이 때 분배되는 양은 Math.floor(남은 양 / 자식의 수)이다.
        // q개의 다음 쿼리를 처리하라
        // 1 u x : u 정점에 x만큼 물을 준다.
        // 2 u : 정점 u의 열매 크기를 출력한다.
        //
        // DFS 문제
        // n과 q가 최대 10만으로 주어져서, 어? 이거 DFS나 BFS 쓰면 시간 초과 나는거 아니야? 싶지만
        // 물의 양이 자신의 열매만큼이 제외되고, 나머지를 자식의 수만큼 분배가 되므로 자식이 2개씩만 되더라도 로그함수적으로 단계가 줄어든다.
        // 그냥 BFS나 DFS로 풀어도 되는 문제
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 정점, q개의 쿼리
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        
        // 간선
        edges = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            edges.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            edges.get(u).add(v);
            edges.get(v).add(u);
        }
        // 간선에 부모 노드도 섞여있으므로 제외해준다.
        pruning(1, -1);
        
        // 각 열매의 크기
        fruits = new long[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < fruits.length; i++)
            fruits[i] = Integer.parseInt(st.nextToken());

        // 쿼리 처리
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int o = Integer.parseInt(st.nextToken());
            if (o == 1) {       // u 정점에 x만큼 물을 준다.
                int u = Integer.parseInt(st.nextToken());
                int x = Integer.parseInt(st.nextToken());

                watering(u, x);
            } else      // u번 정점의 열매 크기 기록
                sb.append(fruits[Integer.parseInt(st.nextToken())]).append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }

    // idx 정점에 water만큼 물을 준다.
    static void watering(int idx, long water) {
        // u번 열매가 물을 먹는 양
        long feed = Math.min(water, fruits[idx]);
        // 해당만큼의 물을 제외
        water -= feed;
        // 열매 성장
        fruits[idx] += feed;
        
        // 자식들에게 남은 물을 분배
        long division = edges.get(idx).isEmpty() ? 0 : (water / edges.get(idx).size());
        // 분배할 양이 0보다 큰 경우에만
        if (division > 0) {
            // 자식들에게 분배
            for (int c : edges.get(idx))
                watering(c, division);
        }
    }

    // 가지치기
    // 리스트에 부모 노드도 속해있으므로 부모 노드를 제외한다.
    static void pruning(int idx, int parent) {
        edges.get(idx).remove(Integer.valueOf(parent));
        for (int next : edges.get(idx))
            pruning(next, idx);
    }
}