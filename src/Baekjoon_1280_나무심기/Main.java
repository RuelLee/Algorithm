/*
 Author : Ruel
 Problem : Baekjoon 1280번 나무 심기
 Problem address : https://www.acmicpc.net/problem/1280
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1280_나무심기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static long[] treeLoc;
    static long[] locSum;
    static final int LIMIT = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        // n개의 나무를 심는다
        // 첫번째 나무를 심는 비용은 없으며, 이후의 나무들은 기존에 심어진 나무들과의 거리 합이 심는 비용이다
        // 2 ~ n까지의 나무를 심는 비용의 곱을 구하여라
        //
        // 두 개의 펜윅트리를 이용하여 풀었다
        // 하나는 해당 위치에 나무가 몇 그루 심어져있는지를 나타내는 펜윅트리이고
        // 다른 하나는 0부터 해당 위치까지 심어져있는 나무들의 총 거리의 합이다
        // 이를 바탕으로 새로운 나무 tree를 심을 때
        // 1. 새로운 나무보다 거리가 가까운 나무들은
        // (tree - 거리가 가까운 나무1) + ... + (tree - 거리가 가까운 나무 n) = tree * n - (거리가 가까운 나무1 + ... + 거리가 가까운 나무n)
        // 2. 새로운 나무보다 거리가 먼 나무들은
        // (거리가 먼 나무1 - tree_) + ... + (거리가 먼 나무n - tree) = (거리가 먼 나무1 + ... + 거리가 먼 나무n) - tree * n
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        treeLoc = new long[200_000 + 2];        // 펜윅 트리이므로 0 위치를 사용해야하지만, 0 위치에도 나무가 있을 수 있으므로, 뒤로 한칸씩 민다.
        locSum = new long[200_000 + 2];
        inputTree(Integer.parseInt(br.readLine()));

        long mul = 1;       // 곱
        for (int i = 1; i < n; i++) {
            long sum = 0;       // i번째 나무를 심을 때의 거리의 합을 구한다.
            int tree = Integer.parseInt(br.readLine());

            long closer = calcNumOfTreeCloserThanN(tree - 1);       // tree보다 가까운 나무의 수
            sum += tree * closer - locSumCloserThanN(tree - 1);     // tree보다 가까울 경우엔 1번 방법을 사용한다.
            sum %= LIMIT;
            long farther = calcNumOfTreeCloserThanN(200000) - calcNumOfTreeCloserThanN(tree);       // tree보다 먼 나무의 수
            sum += locSumCloserThanN(200000) - locSumCloserThanN(tree) - farther * tree;        // tree보다 먼 경우엔 2번 방법을 사용한다.
            sum %= LIMIT;
            mul = (mul * sum) % LIMIT;
            inputTree(tree);        // tree 위치에 나무가 있음을 표시한다.
        }
        // 최종 곱 출력
        System.out.println(mul);
    }

    static long locSumCloserThanN(int n) {      // n보다 가까운 나무들의 거리 합을 구한다.
        n++;        // 0의 값을 사용하기 위해 한 칸씩 뒤로 밀었으므로, 이를 감안한다.
        long sum = 0;
        while (n > 0) {
            sum += locSum[n];
            n -= (n & -n);
        }
        return sum;
    }

    static int calcNumOfTreeCloserThanN(int n) {        // n보다 같거나 가까운 나무의 수를 구한다.
        int sum = 0;
        n++;
        while (n > 0) {
            sum += treeLoc[n];
            n -= (n & -n);
        }
        return sum;
    }

    static void inputTree(int loc) {        // loc 위치에 나무를 심는다.
        checkTree(loc);
        sumLocCloser(loc);
    }

    static void checkTree(int loc) {        // loc 위치에 나무가 있음을 표시한다.
        loc++;      // 마찬가지로 0 위치를 사용하기 위해 뒤로 한칸씩 밀었음을 감안한다.
        while (loc < treeLoc.length) {
            treeLoc[loc]++;
            loc += (loc & -loc);
        }
    }

    static void sumLocCloser(int loc) {     // loc보다 같거나 가까운 위치를 가진 나무들의 거리 총합을 구한다.
        int value = loc;
        loc++;
        while (loc < locSum.length) {
            locSum[loc] += value;
            loc += (loc & -loc);
        }
    }
}