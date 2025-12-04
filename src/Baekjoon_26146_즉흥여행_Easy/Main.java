/*
 Author : Ruel
 Problem : Baekjoon 26146번 즉흥 여행 (Easy)
 Problem address : https://www.acmicpc.net/problem/26146
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_26146_즉흥여행_Easy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<List<Integer>> connections;
    static int[] ancestors, group;
    static int nodeCnt = 1, groupCnt = 1;

    public static void main(String[] args) throws IOException {
        // n개의 나라, m개의 항공편이 주어진다.
        // 어느 나라에서 여행을 시작하더라도, 모든 나라에 방문할 수 있는지 확인하라.
        // 하나의 나라, 하나의 항공편을 여러 번 방문, 사용할 수 있다.
        //
        // 강한 연결 요소 문제
        // 전체 노드가 하나의 강한 연결 요소로 묶이는지 확인하는 문제
        // 여러 개의 요소로 묶이면 서로 간에 왕복이 불가능해지므로, 임의의 나라에서 모든 나라를 방문하는 것이 불가능해진다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 나라, m개의 항공편
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 항공편 정보
        connections = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connections.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            connections.get(u).add(v);
        }
        
        // 도달할 수 있는 최상위 노드 번호
        ancestors = new int[n + 1];
        // 각 노드가 속한 그룹
        group = new int[n + 1];
        Stack<Integer> stack = new Stack<>();
        // 두 개 이상의 그룹으로 나뉘면 안되기 때문에
        // groupCnt가 2이하인 경우만 탐색
        for (int i = 1; i <= n && groupCnt <= 2; i++) {
            // 아직 그룹에 속하지 않았다면 -> 미방문 노드
            // 새롭게 강한 연결 요소가 있는지 찾는다.
            if (group[i] == 0) {
                stack.clear();
                tarjan(i, stack);
            }
        }
        // groupCnt가 2 초과가 되었다면, 2개 이상의 그룹으로 나뉜 것.
        // 불가능한 경우
        System.out.println(groupCnt > 2 ? "No" : "Yes");
    }
    
    // 타잔 알고리즘
    static int tarjan(int idx, Stack<Integer> stack) {
        // 현재 노드의 번호를 할당.
        // 방문 가능한 최상위 조상에도 같은 번호 할당 후 스택 추가
        int nodeNum = ancestors[idx] = nodeCnt++;
        stack.push(idx);

        for (int next : connections.get(idx)) {
            // 이미 강한 연결 요소 그룹이 할당됐다면, idx와는 묶일 수 없다.
            // 건너뜀.
            if (group[next] != 0)
                continue;
            // next의 방문 가능 최상위 조상이 0이 아니라면, 방문한 노드
            // 해당 노드의 방문 가능 최상위 조상 번호를 idx의 방문 가능 최상위 조상으로 가져온다.
            else if (ancestors[next] != 0)
                ancestors[idx] = Math.min(ancestors[idx], ancestors[next]);
            else        // 그 외의 경우는 tarjan 알고리즘을 재귀적으로 적용
                ancestors[idx] = Math.min(ancestors[idx], tarjan(next, stack));
        }

        // 방문 가능 최상위 노드가 자기 자신인 경우
        // 스택 내 idx 위에 담겨있는 노드들이 하나의 연결 요소
        // 같은 그룹으로 묶어준다.
        if (nodeNum == ancestors[idx]) {
            while (stack.peek() != idx)
                group[stack.pop()] = groupCnt;
            group[stack.pop()] = groupCnt++;
        }
        // idx의 방문 가능한 최상위 조상 노드 반환.
        return ancestors[idx];
    }
}