/*
 Author : Ruel
 Problem : Baekjoon 25172번 꼼꼼한 쿠기의 졸업여행
 Problem address : https://www.acmicpc.net/problem/25172
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25172_꼼꼼한쿠기의졸업여행;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int[] parents, ranks, members;

    public static void main(String[] args) throws IOException {
        // n개의 관광지와 관광지들을 잇는 m개의 도로가 주어진다.
        // 하나씩 관광지들과 해당 관광지와 연결된 모든 도로들을 지운다.
        // 남은 관광지들이 서로 모두 직간접적으로 연결되어있다면 CONNECT, 그렇지 않다면 DISCONNECT를 출력하라
        //
        // 분리 집합, 오프라인 쿼리 문제
        // 제거되는 관광지가 주어진다.
        // 이를 역순으로 연결해나가며, 등장한 모든 관광지가 연결되어있는지 확인하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 관광지, m개의
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 도로 처리
        List<List<Integer>> roads = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            roads.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            roads.get(a).add(b);
            roads.get(b).add(a);
        }

        // 제외되는 관광지를 순서에 따라 저장.
        int[] exclude = new int[n];
        for (int i = 0; i < exclude.length; i++)
            exclude[i] = Integer.parseInt(br.readLine());
        
        // 분리 집합을 위한 세팅
        parents = new int[n + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n + 1];
        // 해당 그룹에 속한 관광지의 수를 기록.
        members = new int[n + 1];
        Arrays.fill(members, 1);
        
        // 답
        boolean[] answers = new boolean[n + 1];
        // 등장했던 관광지 표시
        boolean[] appeared = new boolean[n + 1];
        
        // 제외되는 관광지를 역순으로 쫓아가며 관광지들을 잇는다.
        for (int i = exclude.length - 1; i >= 0; i--) {
            // i번째 관광지와 연결된 다른 관광지들을 살펴보며
            // 이미 등장했었는지, 서로 연결되어있지 않은지 살펴보고
            // 그러하다면 연결.
            for (int next : roads.get(exclude[i])) {
                if (appeared[next] && findParent(next) != findParent(exclude[i]))
                    union(next, exclude[i]);
            }
            // i번째 관광지 등장 체크
            appeared[exclude[i]] = true;
            
            // i번째 등장 관광지가 속한 그룹의 관광지 개수가
            // 등장한 관광지 개수만큼 존재한다면 모든 관광지가 연결된 것.
            answers[i] = (members[findParent(exclude[i])] == n - i);
        }
        
        StringBuilder sb = new StringBuilder();
        // 기록한 답들을 살펴보며 답안 작성
        for (boolean answer : answers)
            sb.append(answer ? "CONNECT" : "DISCONNECT").append("\n");
        // 전체 답안 출력
        System.out.print(sb);
    }
    
    // a와 b를 같은 그룹으로 묶는다.
    static void union(int a, int b) {
        int pa = findParent(a);
        int pb = findParent(b);
        
        // 같은 그룹으로 묶을 때,
        // 그룹에 속한 그룹원의 수도 관리
        if (ranks[pa] >= ranks[pb]) {
            members[pa] += members[pb];
            parents[pb] = pa;
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else {
            members[pb] += members[pa];
            parents[pa] = pb;
        }
    }

    // n이 속한 그룹의 대표를 찾는다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }
}