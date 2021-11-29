/*
 Author : Ruel
 Problem : Baekjoon 2957번 이진 탐색 트리
 Problem address : https://www.acmicpc.net/problem/2957
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 이진탐색트리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) throws IOException {
        // 어제 푼 이진 검색 트리와 유사하다
        // 대신 바로 트리셋을 사용하여 lower bound와 upper bound를 바로 사용했다.
        // 문제 자체는 전역 변수 counter를 갖고 있으면서, 값이 추가될 때마다 자신의 조상 노드의 높이를 더해나간다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        TreeSet<Integer> treeSet = new TreeSet<>();
        StringBuilder sb = new StringBuilder();
        int[] depths = new int[n + 1];      // 조상 노드의 높이를 저장해둘 공간.
        long count = 0;     // 값이 추가될 때마다 해당 조상 노드의 높이를 더할 카운터
        for (int i = 0; i < n; i++) {
            int value = Integer.parseInt(br.readLine());
            Integer lb = treeSet.floor(value);      // lower bound
            Integer ub = treeSet.ceiling(value);    // upper bound
            int height = Math.max(lb == null ? -1 : depths[lb], ub == null ? -1 : depths[ub]) + 1;      // lb, ub 중 큰 값은 다음 값이 삽입될 노드의 조상 노드의 깊이. 따라서 + 1을 해주자.
            depths[value] = height;     // 이렇게 얻은 높이를 저장해주고,
            sb.append(count += height).append("\n");        // Stringbuilder에는 count에 height를 더한 값을 기록해주자.
            treeSet.add(value);     // 값 추가.
        }
        System.out.println(sb);
    }
}