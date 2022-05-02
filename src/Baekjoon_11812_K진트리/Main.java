/*
 Author : Ruel
 Problem : Baekjoon 11812번 K진 트리
 Problem address : https://www.acmicpc.net/problem/11812
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11812_K진트리;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 트리 문제
        // 일반적으로 이진트리로 나타내면
        //          1
        //      2       3
        //    4   5   6    7
        // 로 나타낼 수 있고, 각 자식의 범위는
        // (n - 1) * 2 + 2 ~ n * 2 + 1 로 나타낼 수 있다
        // 이를 k진 트리의 n의 자식으로 일반화하면
        // (n - 1) * k + 2 ~ n * k + 1 로 나타낼 수 있고,
        // 거꾸로 자식(m)에서 부모를 찾는 방법은
        // (m + k - 2) / k 로 나타낼 수 있다.
        // 따라서 주어지는 두 값의 레벨을 같은 레벨로 맞춰준 뒤,
        // 두 값의 부모값이 같아질 때까지 계속 부모 노드를 찾아가며 거리를 계산하면 된다.
        Scanner sc = new Scanner(System.in);

        long n = sc.nextLong();     // 주어지는 최대 노드의 값이지만, 사실 별 의미가 없는 입력 같다
        int k = sc.nextInt();
        int q = sc.nextInt();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            long a = sc.nextLong();
            long b = sc.nextLong();
            if (k == 1) {       // 1진 트리가 값이 차이가 곧 거리므로 그냥 차를 츨력해주자.
                sb.append(Math.abs(a - b)).append("\n");
                continue;
            }
            int levelA = findLevel(a, k);
            int levelB = findLevel(b, k);

            int distance = 0;
            while (levelA != levelB) {
                if (levelA > levelB) {
                    a = (a + k - 2) / k;
                    levelA--;
                } else {
                    b = (b + k - 2) / k;
                    levelB--;
                }
                distance++;
            }
            while (a != b) {
                a = findParents(a, k);
                b = findParents(b, k);
                distance += 2;
            }
            sb.append(distance).append("\n");
        }
        System.out.println(sb);
    }

    static long findParents(long a, int k) {
        return (a + k - 2) / k;
    }

    static int findLevel(long a, int k) {
        if (a == 1)
            return 1;

        long range = 1;
        int level = 1;
        while (range < a) {
            level++;
            range = range * k + 1;
        }
        return level;
    }
}