/*
 Author : Ruel
 Problem : Baekjoon 20040번 사이클 게임
 Problem address : https://www.acmicpc.net/problem/20040
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20040_사이클게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] parents;
    static int[] ranks;

    public static void main(String[] args) throws IOException {
        // n개의 점이 주어지고, 이에 연결된 m개의 선분들이 주어진다
        // n개의 점들을 서로 이어나가며, 연결된 선분으로 인해 사이클이 발생한다면 게임이 종료된다고 한다
        // 이 때의 턴 수를 구하라
        //
        // 분리 집합 문제
        // 두 점들의 집합을 구하고, 서로 다른 집합에 속한다면, 한 집합으로 묶어준다
        // 이러다 두 점들이 이미 같은 집합에 속해있다면, 해당 선분으로 인해 사이클이 발생한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        init(n);

        int[][] lines = new int[m][2];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            lines[i][0] = Integer.parseInt(st.nextToken());
            lines[i][1] = Integer.parseInt(st.nextToken());
        }

        int answer = 0;
        for (int i = 0; i < m; i++) {
            // 두 점들이 같은 집합에 속한다면 해당 선분으로 사이클이 발생한다.
            // 답을 기록해주고 종료.
            if (findParents(lines[i][0]) == findParents(lines[i][1])) {
                answer = i + 1;
                break;
            }
            // 아니라면 두 점을 한 집합으로 묶어준다
            union(lines[i][0], lines[i][1]);
        }
        System.out.println(answer);
    }

    static void union(int a, int b) {       // union 연산. 한 집합으로 묶어준다.
        int pa = findParents(a);
        int pb = findParents(b);

        // 연산을 줄이기 위해 rank가 더 낮은 쪽을 더 높은 쪽에 속하게 한다.
        if (ranks[pa] >= ranks[pb]) {
            parents[pb] = pa;
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else
            parents[pa] = pb;
    }

    static int findParents(int n) {     // 속한 집합을 찾는다
        // 자신 혼자인 집합이거나, 자신이 집합의 대표일 때, 자신을 리턴.
        if (parents[n] == n)
            return n;
        // 그렇지 않다면, 자신이 속한 집합의 대표를 찾아간다
        // 연산을 줄이기 위해 경로 단축.
        return parents[n] = findParents(parents[n]);
    }

    static void init(int n) {       // parents와 ranks 초기화.
        parents = new int[n];
        for (int i = 0; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n];
    }
}