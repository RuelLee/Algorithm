/*
 Author : Ruel
 Problem : Baekjoon 17073번 나무 위의 빗물
 Problem address : https://www.acmicpc.net/problem/17073
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/


package Baekjoon_17073_나무위의빗물;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<List<Integer>> child;

    public static void main(String[] args) throws IOException {
        // 정점 1을 루트로 갖는 트리가 주어진다.
        // 1번 정점에는 물이 고여있으며 매초 다음과 같은 작업을 한다.
        // 물을 가지고 있으며, 자식 정점이 있다면 자식 정점 중 하나를 골라 물을 1 준다. 자식 정점이 여러 개라면 동일한 확률로 그 중 하나를 고른다.
        // 만약 부모 정점이 자신에게 물을 흘려보냈다면 받아서 쌓아 둔다.
        // 물이 더 이상 움직이지 않을 때, 물이 쌓인 노드들의 평균 물의 양은?
        //
        // 트리 문제
        // 물이니 뭐니 여러 변수들이 등장했지만, 결국엔 단말 노드의 개수를 세는 문제
        // 물은 결국 단말노드들에 모두 고이게 된다.
        // 따라서 처음에 루트가 갖고 있는 물의 양 / 단말 노드의 수가 답이 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // n개의 정점
        int n = Integer.parseInt(st.nextToken());
        // 처음 루트 노드가 갖고 있는 물의 양
        int w = Integer.parseInt(st.nextToken());
        
        // 연결 상태
        child = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            child.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            child.get(u).add(v);
            child.get(v).add(u);
        }

        int count = 0;
        // 단말 노드는 자신을 연결해주는 부모 노드 외에는
        // 다른 연결이 존재하지 않는다.
        // 따라서 리스트들을 살펴보며 그 크기가 1인 리스트들의 개수를 센다.
        // 물론 루트 노드는 제외
        for (int i = 2; i < child.size(); i++)
            count += (child.get(i).size() == 1 ? 1 : 0);

        // 처음 루트 노드가 갖고 있는 물의 양을 단말 노드의 수로 나눈 값이 답.
        System.out.println((double) w / count);
    }
}