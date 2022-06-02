/*
 Author : Ruel
 Problem : Baekjoon 17352번 여러분의 다리가 되어 드리겠습니다!
 Problem address : https://www.acmicpc.net/problem/17352
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17352_여러분의다리가되어드리겠습니다;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] parents;
    static int[] ranks;

    public static void main(String[] args) throws IOException {
        // n개의 섬과 그 섬들을 잇는 n-1개의 다리들이 있다.
        // n-1개의 다리들로 인해 어떤 두 섬 사이든 다리로 왕복할 수 있다.
        // 그런데 그 중 하나의 다리를 폭파시켰고, 왕복할 수 없는 섬이 생겼다
        // 다시 어떤 두 섬 사이든 왕복할 수 있도록 다리를 건설하려고 한다.
        // 여러 가지의 방법이 있을 경우, 그 중 아무거나 한 방법을 출력한다.
        // 입력으로는 폭파되지 않은 다리 n - 2개가 주어진다.
        //
        // 간단한 분리 집합 문제
        // 폭파되지 않은 다리를 통해 서로 연결된 섬들을 한 그룹으로 묶는다.
        // 그 후, 생기는 두 집합을 하나로 묶는 다리를 아무거나 하나 출력하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        init(n);

        // n - 2개의 다리
        for (int i = 0; i < n - 2; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            // a와 b는 연결된 다리가 있으므로 하나의 집합으로 묶는다.
            union(a, b);
        }

        // 기준을 1번 섬으로 삼았다.
        // 1번 섬과 다른 집합을 갖는 섬을 찾고,
        // 1번 섬과 해당 섬을 연결하는 다리를 건설한다.
        for (int i = 2; i < n + 1; i++) {
            if (findParent(1) != findParent(i)) {
                System.out.println(1 + " " + i);
                break;
            }
        }
    }

    // 유니온 연산.
    // a와 b를 한 집합으로 묶어준다.
    static void union(int a, int b) {
        int pa = findParent(a);
        int pb = findParent(b);

        // rank가 더 큰 쪽으로 속하게 함으로써 연산을 줄여준다.
        if (ranks[pa] >= ranks[pb]) {
            parents[pb] = pa;
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else
            parents[pa] = pb;
    }

    // 자신이 속한 집합의 대표를 출력한다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }

    // 초기화
    static void init(int n) {
        parents = new int[n + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n + 1];

    }
}