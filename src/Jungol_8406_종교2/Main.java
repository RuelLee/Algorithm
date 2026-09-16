/*
 Author : Ruel
 Problem : Jungol 8406번 종교 2
 Problem address : https://jungol.co.kr/problem/8406
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_8406_종교2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] parents, members;

    public static void main(String[] args) throws IOException {
        // n명의 학생이 각각 서로 다른 종교를 믿고 있다.
        // q개의 다음 두 종류 쿼리를 처리한다.
        // 1 x y -> x와 y의 종교를 통합한다.
        // 2 x -> x가 믿고 있는 종교를 믿고 있는 사람의 수를 출력한다
        //
        // 분리 집합 문제
        // 분리 집합으로 처리하되, ranks 대신 memebers로 속한 인원의 수를 기준으로 연산을 줄이며 수를 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 학생, q개의 쿼리
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        // 분리 집합 초기화
        parents = new int[n + 1];
        for (int i = 1; i <= n; i++)
            parents[i] = i;
        members = new int[n + 1];
        Arrays.fill(members, 1);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int o = Integer.parseInt(st.nextToken());
            // x와 y가 믿는 종교를 하나로 합친다.
            if (o == 1) {
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                if (findParent(x) != findParent(y))
                    union(x, y);
            } else {
                // x가 믿는 종교의 신자 수를 기록한다.
                int x = Integer.parseInt(st.nextToken());
                sb.append(members[findParent(x)]).append("\n");
            }
        }
        // 답 출력
        System.out.print(sb);
    }

    // a와 b가 믿는 종교를 합친다.
    // 총 신자 수가 많은 쪽에 속하게 해, 연산을 줄인다.
    static void union(int a, int b) {
        int pa = parents[a];
        int pb = parents[b];

        if (members[pa] >= members[pa]) {
            parents[pb] = pa;
            members[pa] += members[pb];
        } else {
            parents[pb] = pa;
            members[pb] += members[pa];
        }
    }

    // n이 속한 집합의 대표자를 찾는다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }
}