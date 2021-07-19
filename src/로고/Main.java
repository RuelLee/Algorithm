/*
 Author : Ruel
 Problem : Baekjoon 3108번 로고
 Problem address : https://www.acmicpc.net/problem/3108
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 로고;

import java.util.HashSet;
import java.util.Scanner;

class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Main {
    static int[] parents;
    static int[] ranks;

    public static void main(String[] args) {
        // 사각형들의 좌하단과 우상단이 주어질 때,
        // 이어져있는 사각형들은 모두 몇 무리인지 표시하라.
        // 사각형의 두 점이 주어질 때 겹치지 않는 경우는
        // 한 사각형의 작은 x가 다른 한 사각형의 큰 x보다 클 때와 반대상황 2가지
        // 한 사각형의 작은 y가 다른 한 사각형의 큰 y보다 클 때와 반대상황 2가지
        // 그리고 사각형이 다른 사각형 안에 들어가있을 때와 반대 상황 2가지
        // 6가지를 제외하면 다른 경우에는 겹친다.
        // 이를 이용하여 각각 무리를 나눠주면 된다.
        // 도형 + union-find
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        Point[][] rectangles = new Point[n + 1][2];
        rectangles[0][0] = new Point(0, 0);
        rectangles[0][1] = new Point(0, 0);

        for (int i = 1; i < rectangles.length; i++) {
            for (int j = 0; j < 2; j++)
                rectangles[i][j] = new Point(sc.nextInt(), sc.nextInt());
        }
        makeSet(n);
        for (int i = 0; i < rectangles.length; i++) {
            for (int j = i + 1; j < rectangles.length; j++) {
                if (findParents(i) == findParents(j))   // 같은 무리에 속해있다면 패스
                    continue;

                if (areTheyMeet(rectangles, i, j))      // 아니라면 두 사각형이 만나는지 살펴보고
                    union(i, j);    // 만난다면 같은 무리로 표시
            }
        }
        HashSet<Integer> hashSet = new HashSet<>();
        for (int i = 0; i < rectangles.length; i++)
            hashSet.add(findParents(i));    // 무리를 모두 해쉬셋에 담아주자. 중복값은 제거될 것이다.
        System.out.println(hashSet.size() - 1);
    }

    static boolean areTheyMeet(Point[][] rectangles, int a, int b) {
        if (rectangles[a][0].x > rectangles[b][1].x || rectangles[a][1].x < rectangles[b][0].x)         // 교차하는 x값이 없는 경우
            return false;
        else if (rectangles[a][0].y > rectangles[b][1].y || rectangles[a][1].y < rectangles[b][0].y)    // 교차하는 y값이 없는 경우
            return false;
        else if (rectangles[a][0].x < rectangles[b][0].x && rectangles[b][1].x < rectangles[a][1].x && rectangles[a][0].y < rectangles[b][0].y && rectangles[b][1].y < rectangles[a][1].y)      // a 사각형 안에 b 사각형이 들어있는 경우
            return false;
        else if (rectangles[b][0].x < rectangles[a][0].x && rectangles[a][1].x < rectangles[b][1].x && rectangles[b][0].y < rectangles[a][0].y && rectangles[a][1].y < rectangles[b][1].y)      // b 사각형 안에 a 사각형이 들어있는 경우
            return false;
        return true;
    }

    static void makeSet(int n) {
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