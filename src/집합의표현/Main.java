/*
 Author : Ruel
 Problem : Baekjoon 1717번 집합의 표현
 Problem address : https://www.acmicpc.net/problem/1717
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 집합의표현;

import java.util.Scanner;

public class Main {
    static int[] parents;
    static int[] ranks;

    public static void main(String[] args) {
        // union-find 문제
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();

        init(n);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            int order = sc.nextInt();
            int a = sc.nextInt();
            int b = sc.nextInt();

            switch (order) {
                case 0:
                    union(a, b);
                    break;
                case 1:
                    if (findParents(a) == findParents(b))
                        sb.append("YES").append("\n");
                    else
                        sb.append("NO").append("\n");
                    break;
                default:
                    sb.append("ERROR").append("\n");
            }
        }
        System.out.println(sb);
    }

    static void init(int n) {
        parents = new int[n + 1];
        ranks = new int[n + 1];

        for (int i = 0; i < parents.length; i++)
            parents[i] = i;
    }

    static int findParents(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParents(parents[n]);
    }

    static void union(int a, int b) {
        int pa = findParents(a);
        int pb = findParents(b);

        if (ranks[pa] > ranks[pb])
            parents[pb] = pa;
        else {
            parents[pa] = pb;
            if (ranks[pa] == ranks[pb])
                ranks[pb]++;
        }
    }
}