/*
 Author : Ruel
 Problem : Baekjoon 15809번 전국시대
 Problem address : https://www.acmicpc.net/problem/15809
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15809_전국시대;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int[] parents, ranks, armies;

    public static void main(String[] args) throws IOException {
        // n개의 나라와 각각이 가진 병력의 수가 주어진다.
        // m개의 동맹 혹은 전쟁이 일어난다.
        // 동맹은 두 나라의 병력이 합쳐지며
        // 전쟁은 두 나라 중 더 많은 병력을 가진 국가가 승리하며, 패배한 나라는 속국이 된다.
        // 승리한 나라의 병력은 패배한 나라의 병력을 뺀 수치가 된다.
        // 두 나라의 병력의 수가 같을 경우, 두 나라 모두 멸망한다.
        // m의 기록이 끝났을 때, 남은 국가와 병력의 수를 오름차순으로 출력하라
        //
        // 분리 집합 문제
        // 기록에 따라 항상 두 나라를 병합하며, 동맹이냐, 전쟁이냐에 따라 병사의 수가 바뀌게된다.
        // 두 국가의 병사 수가 같을 경우엔 두 나라 모두 멸망함을 유의하자
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // n개의 국가, m개의 기록
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        // 분리집합을 위해 parents, ranks 초기화
        parents = new int[n];
        for (int i = 0; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n];
        
        // 각 국가가 가진 병력의 수
        armies = new int[n];
        for (int i = 0; i < armies.length; i++)
            armies[i] = Integer.parseInt(br.readLine());
        
        // m개의 기록
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int o = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken()) - 1;
            int q = Integer.parseInt(st.nextToken()) - 1;

            union(p, q, o);
        }
        
        // 남은 국가를 세고
        int count = 0;
        boolean[] visited = new boolean[n];
        // 병력을 오름차순으로 정렬
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < armies.length; i++) {
            // i 국가가 속한 팀
            int team = findParents(i);
            // 방문하지 않은 팀이고, 병사의 수가 0이 아니라면
            // 멸망하지 않고 남은 국가
            if (!visited[team] && armies[team] != 0) {
                // count 증가
                count++;
                // 우선순위큐에 병사의 수 삽입.
                priorityQueue.offer(armies[team]);
                // 방문 체크
                visited[team] = true;
            }
        }
        
        // 답안 출력
        StringBuilder sb = new StringBuilder();
        sb.append(count).append("\n");
        while (!priorityQueue.isEmpty())
            sb.append(priorityQueue.poll()).append(" ");
        sb.deleteCharAt(sb.length() - 1);

        System.out.println(sb);
    }

    // a, b국가를 병합시킨다.
    static void union(int a, int b, int type) {
        int pa = findParents(a);
        int pb = findParents(b);
        
        // pa에 pb 국가를 속하게 만드는 경우
        if (ranks[pa] >= ranks[pb]) {
            // pa의 병력의 수는 동맹일 경우, 두 국가 병력의 합이고
            // 전쟁일 경우, 두 국가 병력 수의 차이다.
            armies[pa] = (type == 1 ? armies[pa] + armies[pb] : Math.abs(armies[pa] - armies[pb]));
            parents[pb] = pa;
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else {        // pb에 pa를 속하게 만드는 경우.
            armies[pb] = (type == 1 ? armies[pa] + armies[pb] : Math.abs(armies[pa] - armies[pb]));
            parents[pa] = pb;
        }
    }

    // n 국가가 속한 국가를 반환한다.
    static int findParents(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParents(parents[n]);
    }
}