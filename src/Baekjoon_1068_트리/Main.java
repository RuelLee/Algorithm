/*
 Author : Ruel
 Problem : Baekjoon 1068번 트리
 Problem address : https://www.acmicpc.net/problem/1068
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1068_트리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static List<List<Integer>> child;

    public static void main(String[] args) throws IOException {
        // n개의 노드로 이루어진 트리가 있다
        // 각 노드의 부모 노드가 주어진다
        // 그리고 부모 노드와 연결을 끊을 노드가 주어진다
        // 연결을 끊은 후, 단말 노드의 개수는 몇 개인가
        // DFS로 풀 수 있는 문제
        // 루트 노드로부터 시작해서, 말단 노드까지 진행. 그 후 말단 노드의 개수를 bottom-up 방식으로 올려보낸다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int root = -1;
        child = new ArrayList<>();      // 자식 노드들을 저장할 리스트
        for (int i = 0; i < n; i++)
            child.add(new ArrayList<>());
        int[] parents = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < parents.length; i++) {
            parents[i] = Integer.parseInt(st.nextToken());

            // 부모 노드가 -1이라면 루트 노드
            if (parents[i] == -1)
                root = i;
            // 아니라면 자신의 부모노드에 자신을 자식으로 표시한다.
            else
                child.get(parents[i]).add(i);
        }
        // 부모 노드와 간선을 끊을 노드.
        int delete = Integer.parseInt(br.readLine());
        // 만약 delete가 루트 노드라면 계산할 필요없이 0이 답.
        if (delete == root)
            System.out.println(0);
        // 아니라면 parents[delete]의 child 리스트에서 delete를 지워준다.
        else {
            child.get(parents[delete]).remove(Integer.valueOf(delete));
            System.out.println(calcLeafNodes(root));
        }
    }

    static int calcLeafNodes(int root) {        // 단말 노드의 개수를 세는 메소드
        if (child.get(root).isEmpty())      // 자신의 자식 노드가 없다면 단말 노드. 1을 리턴한다.
            return 1;

        int sum = 0;
        for (int next : child.get(root))        // 자신의 자식 노드들의 단말 노드의 개수를 센다.
            sum += calcLeafNodes(next); // 자식 노드인 next로부터 calcLeafNodes 메소드를 보내 단말 노드의 개수를 센다.
        // root 노드의 자식 노드들의 단말 노드의 개수를 합쳐 반환한다.
        return sum;
    }
}