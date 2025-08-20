/*
 Author : Ruel
 Problem : Baekjoon 3755번 Double Queue
 Problem address : https://www.acmicpc.net/problem/3755
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3755_DoubleQueue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) throws IOException {
        // 은행에서 손님들의 업무를 처리한다.
        // 각 손님에 대해 손님의 번호와 우선순위가 주어진다.
        // 그에 대해 다음 4가지 쿼리를 처리한다.
        // 0 : 시스템을 종료한다.
        // 1 k p : k번 손님을 p 우선선위로 추가한다.
        // 2 : 우선순위가 가장 높은 손님의 업무를 처리한다.
        // 3 : 우선순위가 가장 낮은 손님의 업무를 처리한다.
        // 대기중인 손님이 없는데 처리하고자하면 0을 출력하고,
        // 그 외의 경우는 업무를 처리하는 손님의 번호를 출력한다.
        //
        // 트리를 활용한 맵
        // 정렬을 하되, 우선순위가 가장 낮은 값도, 가장 높은 값도 필요한 문제
        // 따라서 손님의 번호와 우선순위가 중복되는 경우가 없다하였으므로 트리 맵을 통해 처리하면 편하다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 트리 맵 우선순위와 손님의 번호
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();

        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        while (true) {
            st = new StringTokenizer(br.readLine());
            int o = Integer.parseInt(st.nextToken());
            // 0일 경우 종료
            if (o == 0)
                break;

            switch (o) {
                // 1인 경우, 우선순위 p의 k번 손님 추가
                case 1 -> {
                    int k = Integer.parseInt(st.nextToken());
                    int p = Integer.parseInt(st.nextToken());
                    treeMap.put(p, k);
                }
                // 트리맵이 비었다면 0 그렇지 않다면 우선순위가 가장 높은 손님의 업무 처리
                case 2 -> sb.append(treeMap.isEmpty() ? 0 : treeMap.pollLastEntry().getValue()).append("\n");
                // 트리맵이 비었다면 0 그렇지 않다면 우선순위가 낮은 높은 손님의 업무 처리
                case 3 -> sb.append(treeMap.isEmpty() ? 0 : treeMap.pollFirstEntry().getValue()).append("\n");
            }
        }
        // 답 출력
        System.out.print(sb);
    }
}