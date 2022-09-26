/*
 Author : Ruel
 Problem : Baekjoon 17619번 개구리 점프
 Problem address : https://www.acmicpc.net/problem/17619
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17619_개구리점프;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    static int[] parents;
    static int[] ranks;

    public static void main(String[] args) throws IOException {
        // n개의 통나무가 수평 방향으로 연못에 떠있다.
        // 각 통나무는 x1, x2, y로 주어진다.
        // 개구리는 x가 겹치는 통나무들에 한해 수직 방향으로 점프하는 것이 가능하다.
        // q개의 질문이 주어진다.
        // q는 a, b로 이루워져있으며, a -> b로 이동하는 것이 가능한지 여부이다.
        // 가능하면 1, 불가능하면 0을 출력한다.
        //
        // 분리 집합 + 정렬 문제
        // 점프를 통해 이동은 양방향으로 가능하다.
        // 만약 a -> b가 가능하고, b -> c가 가능하다면 a -> c나 c -> a도 가능하다
        // 따라서 서로 이동이 가능한 통나무군을 하나의 집합으로 묶고 생각하면 된다.
        // 이 때 x 범위가 겹치는 통나무만 이동이 가능하므로
        // x1에 대해 정렬하여, 시작점이 이른 순서부터 살펴보되, 해당 시작점이 현재 살펴보는 집합의 끝점보다 작은지 여부를 살펴본다.
        // 집합의 끝점은 속한 통나무들의 끝점 중 가장 큰 값으로 한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        // 통나무들
        int[][] logs = new int[n][3];
        for (int i = 0; i < logs.length; i++) {
            st = new StringTokenizer(br.readLine());
            int x1 = Integer.parseInt(st.nextToken());
            int x2 = Integer.parseInt(st.nextToken());
            // 사실 높이는 필요하지 않다.
            int y = Integer.parseInt(st.nextToken());
            // 원래 인덱스
            logs[i][0] = i;
            // 시작점
            logs[i][1] = x1;
            // 끝점
            logs[i][2] = x2;
        }
        // 시작점에 대해 정렬한다.
        Arrays.sort(logs, Comparator.comparingInt(o -> o[1]));

        // 분리 집합 게산을 위한 parents와 ranks.
        parents = new int[n];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n];

        // 현재 집합의 끝점.
        // 초기값은 -1.
        int last = -1;
        for (int i = 0; i < logs.length; i++) {
            // 현재 살펴보는 통나무의 시작점이
            // last보다 큰 값일 경우 해당 집합과, 현재 살펴보는 통나무는
            // 겹치는 x좌표가 없으므로 서로 다른 집합이이다.
            // 따라서 새로운 집합군을 위해 끝점을 현재 통나무의 끝점으로 설정하고 넘어간다.
            if (logs[i][1] > last)
                last = logs[i][2];
            // 그렇지 않고, x좌표가 겹친다면
            else {
                // 해당 통나무를 현재 집합(=이전 통나무의 집합)에 포함시킨다.
                union(logs[i - 1][0], logs[i][0]);
                // 현재 집합의 끝점이 더 늘어났는지 확인.
                last = Math.max(last, logs[i][2]);
            }
        }

        // 쿼리 처리.
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            // 0 ~ n - 1까지의 인덱스를 사용했으므로 하나씩 값을 빼주고.
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;

            // a, b가 서로 같은 집합인지(= 이동 가능한지)에 대한 결과를 기록.
            sb.append(findParent(a) == findParent(b) ? 1 : 0).append("\n");
        }
        // 전체 기록 출력.
        System.out.print(sb);
    }

    // 집합의 대표자를 찾는다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }

    // a가 속한 집합과 b가 속한 집합을 하나의 집합으로 합친다.
    static void union(int a, int b) {
        int pa = findParent(a);
        int pb = findParent(b);

        if (ranks[pa] >= ranks[pb]) {
            parents[pb] = pa;
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else
            parents[pa] = pb;
    }
}