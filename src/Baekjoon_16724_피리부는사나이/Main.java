/*
 Author : Ruel
 Problem : Baekjoon 16724번 피리 부는 사나이
 Problem address : https://www.acmicpc.net/problem/16724
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16724_피리부는사나이;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
    static char[][] map;
    static int[] parents;
    static int[] ranks;

    public static void main(String[] args) throws IOException {
        // n * m 크기의 지도가 주어지고 각 칸에는 방향이 주어진다(지도 밖의 방향이 주어지는 경우는 없다)
        // 각 구간에 있는 사람은 방향에 따라 움직인다.
        // 계속 움직이는 사람들을 위해 특정 칸의 방향을 없애고자 한다.(더 이상 움직이지 않도록)
        // 방향을 없앨 최소 칸의 개수는?
        //
        // 분리 집합 문제
        // 방향에 따라 사람은 사이클을 이루며 무한히 이동한다
        // 따라서 사이클 중에 한 칸이라도 방향이 사라지면, 해당 칸에서 멈춰서게 된다
        // 모든 칸에 대해서 자신이 가르키는 방향의 칸과 같은 집합으로 묶고
        // 해당 집합에서 한 칸의 방향을 없애면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        map = new char[n][];
        for (int i = 0; i < map.length; i++)
            map[i] = br.readLine().toCharArray();

        parents = new int[n * m];
        ranks = new int[n * m];
        for (int i = 0; i < parents.length; i++)
            parents[i] = i;

        for (int i = 0; i < n * m; i++) {
            switch (map[i / m][i % m]) {
                case 'L' -> union(i, i - 1);        // L일 경우 왼쪽 칸과
                case 'R' -> union(i, i + 1);        // R일 경우 오른쪽 칸과
                case 'U' -> union(i, i - m);        // U일 경우 윗쪽 칸과
                case 'D' -> union(i, i + m);        // D일 경우 아랫칸과 같은 집합으로 묶는다.
            }
        }

        // 최종적으로 모든 칸이 속한 집합을 해시셋에 담는다.
        HashSet<Integer> hashSet = new HashSet<>();
        for (int i = 0; i < n * m; i++)
            hashSet.add(findParents(i));
        // 해시셋의 크기가 최소 집합의 크기 = 방향을 없애도 되는 최소 칸의 수
        System.out.println(hashSet.size());
    }

    static int findParents(int n) {
        if (parents[n] == n)        // 집합의 번호가 자기 자신과 같을 경우 자신 리턴.
            return n;
        // 그렇지 않을 경우, 자신이 가르키는 대상의 집합 번호를 찾는다.
        // 경로 단축.
        return parents[n] = findParents(parents[n]);
    }

    static void union(int a, int b) {
        // a와 b를 한 집합으로 묶기 위해 두 집합이 속한 대표를 찾아 같은 집합으로 묶는다.
        int pa = findParents(a);
        int pb = findParents(b);

        // pa의 랭크가 같거나 더 높다면
        if (ranks[pa] >= ranks[pb]) {
            parents[pb] = pa;       // pb를 pa에 속하게 한다
            if (ranks[pa] == ranks[pb])     //랭크가 같을 경우 pa의 랭크 증가.
                ranks[pa]++;
        } else          // 그렇지 않다면 pa를 pb에 속하게 한다.
            parents[pa] = pb;
    }
}