/*
 Author : Ruel
 Problem : Baekjoon 2162번 선분 그룹
 Problem address : https://www.acmicpc.net/problem/2162
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 선분그룹;

import java.util.HashSet;
import java.util.Scanner;

public class Main {
    static int[] parents;
    static int[] ranks;

    public static void main(String[] args) {
        // CCW를 사용하여 선분이 교차하는지 여부를 찾고
        // 이에 교차한다면 union-find를 통해 한 그룹으로 묶어줘야한다
        // 두 가지 알고리즘을 같이 활용해야하는 문제. 알고리즘만 알고 있다면 난이도는 높지 않다.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[][][] points = new int[n][2][2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < points[i].length; j++) {
                for (int k = 0; k < points[i][j].length; k++)
                    points[i][j][k] = sc.nextInt();
            }
        }

        parents = new int[n];
        for (int i = 0; i < n; i++)
            parents[i] = i;
        ranks = new int[n];

        for (int i = 0; i < points.length; i++) {       // 한 선분과
            for (int j = i + 1; j < points.length; j++) {   // 다른 한 선분을 비교하며
                if (findParents(i) != findParents(j) && areTheyCross(points[i], points[j]))     // 서로 다른 그룹이고, 교차한다면
                    union(i, j);        // 한 그룹으로 묶어준다.
            }
        }

        HashSet<Integer> hashSet = new HashSet<>();
        int maxMember = 0;
        int[] count = new int[n];
        for (int i = 0; i < n; i++) {
            int group = findParents(i);
            hashSet.add(group);         // 그룹을 해쉬셋에 넣고,
            count[group]++;             // 그룹원의 수를 하나 증가
            maxMember = Math.max(maxMember, count[group]);      // 최대 그룹원의 수 갱신
        }
        System.out.println(hashSet.size() + "\n" + maxMember);
    }

    static void union(int a, int b) {       // 서로 다른 두 그룹을 합쳐주는 메소드
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

    static int findParents(int n) {         // 자신의 부모(group)을 찾아주는 메소드
        if (parents[n] == n)
            return n;
        return parents[n] = findParents(parents[n]);
    }

    static boolean areTheyCross(int[][] a, int[][] b) {     // 두 선분이 서로 교차하는지 체크
        return checkRange(a, b) && calcCCW(a[0], a[1], b[0]) * calcCCW(a[0], a[1], b[1]) <= 0 && calcCCW(b[0], b[1], a[0]) * calcCCW(b[0], b[1], a[1]) <= 0;
    }

    static boolean checkRange(int[][] a, int[][] b) {       // 서로 범위가 겹치는지 체크
        int aMinX, aMaxX, aMinY, aMaxY;

        if (a[0][0] < a[1][0]) {
            aMinX = a[0][0];
            aMaxX = a[1][0];
        } else {
            aMinX = a[1][0];
            aMaxX = a[0][0];
        }

        if (a[0][1] < a[1][1]) {
            aMinY = a[0][1];
            aMaxY = a[1][1];
        } else {
            aMinY = a[1][1];
            aMaxY = a[0][1];
        }

        int bMinX, bMaxX, bMinY, bMaxY;

        if (b[0][0] < b[1][0]) {
            bMinX = b[0][0];
            bMaxX = b[1][0];
        } else {
            bMinX = b[1][0];
            bMaxX = b[0][0];
        }

        if (b[0][1] < b[1][1]) {
            bMinY = b[0][1];
            bMaxY = b[1][1];
        } else {
            bMinY = b[1][1];
            bMaxY = b[0][1];
        }

        return aMinX <= bMaxX && bMinX <= aMaxX && aMinY <= bMaxY && bMinY <= aMaxY;
    }

    static int calcCCW(int[] a, int[] b, int[] c) {     // ccw 계산
        int ccw = (b[0] - a[0]) * (c[1] - a[1]) - (b[1] - a[1]) * (c[0] - a[0]);
        return Integer.compare(ccw, 0);
    }
}