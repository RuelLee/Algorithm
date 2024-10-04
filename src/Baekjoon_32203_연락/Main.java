/*
 Author : Ruel
 Problem : Baekjoon 32203번 연락
 Problem address : https://www.acmicpc.net/problem/32203
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_32203_연락;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] parents, ranks;
    static int[][] members;

    public static void main(String[] args) throws IOException {
        // n명의 사람들이 주어지고, m번의 모임을 갖는다.
        // 각 사람들의 주민등록번호 뒷자리의 첫번째 숫자가 주어지며, 홀수일 경우 남성, 짝수일 경우 여성이다.
        // 각 모임에는 두 사람이 만나, 서로 연락처를 교환한다.
        // 각 사람들을 통해 직간접적으로 연결된 사람들은 서로 연락을 할 수 있다고 한다.
        // 임의의 a, b, c사람에 대해, a와 b가 연락이 가능하고, b와 c가 연락이 가능하다면 a와 c도 연락이 가능한 식이다.
        // 각 모임이 마칠 때마다 연락 가능한 남녀 쌍의 개수를 출력하라
        //
        // 분리 집합 문제
        // 분리 집합을 통해 하나의 그룹으로 묶고
        // 그룹 내에 속한 남녀의 인원 수를 체크해가며 답을 작성한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 사람, m개의 모임
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 분리 집합을 위한 값 설정
        // 집합의 대표
        parents = new int[n + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        // 연산 단축을 위한 ranks
        ranks = new int[n + 1];
        
        // 해당 집합에 속한 남녀 인원
        members = new int[n + 1][2];
        int[] gender = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 처음에는 자기 자신만 속함
        for (int i = 0; i < gender.length; i++)
            members[i + 1][gender[i] % 2]++;

        StringBuilder sb = new StringBuilder();
        // 남녀 연락 가능한 쌍의 수
        long sum = 0;
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            // a와 b가 만남을 갖는다.
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            int pa = findParents(a);
            int pb = findParents(b);
            
            // 두 사람이 서로 다른 집합에 속해있다면
            if (pa != pb) {
                // 각각 두 집합이 가능했던 연락의 수만큼을 sum에 제외
                sum -= (long) members[pa][0] * members[pa][1];
                sum -= (long) members[pb][0] * members[pb][1];
                // 하나의 그룹으로 묶고 
                union(a, b);
                int idx = findParents(a);
                // 해당 그룹에서 가능한 연락의 수 추가
                sum += (long) members[idx][0] * members[idx][1];
            }
            // 연락 가능한 남녀의 쌍 기록
            sb.append(sum).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }

    // a와 b를 하나의 그룹으로 묶는다.
    static void union(int a, int b) {
        int pa = findParents(a);
        int pb = findParents(b);

        if (ranks[pa] >= ranks[pb]) {
            parents[pb] = pa;
            // 그룹에 속한 남녀의 수 합산
            for (int i = 0; i < 2; i++)
                members[pa][i] += members[pb][i];

            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else {
            parents[pa] = pb;
            for (int i = 0; i < 2; i++)
                members[pb][i] += members[pa][i];
        }
    }

    // n이 속한 그룹의 대표를 찾는다.
    static int findParents(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParents(parents[n]);
    }
}