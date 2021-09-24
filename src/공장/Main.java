/*
 Author : Ruel
 Problem : Baekjoon 7578번 공장
 Problem address : https://www.acmicpc.net/problem/7578
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 공장;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    static long[] fenwickTree;

    public static void main(String[] args) {
        // 오랜만에 펜윅 트리
        // A라인과 B라인이 같은 수끼리 서로 연결되어있을 때, 교차하는 지점의 개수를 구하는 문제
        // 한쪽 라인을 선택해 다른 한 쪽 라인을 방문, 그보다 오른쪽에 있는 기기들 중 이미 방문한 적 있는 기기들의 개수를 더해주면 된다.
        // 만약 A라인을 2번을 선택해, B라인 3번으로 왔을 때,
        // B라인 4번 이상 중 이미 방문이 체크되어있는 곳은 A에 2번 미만과 연결된 경우이다.
        // 따라서 위와 같은 경우는 교차하므로 해당 개수를 모두 세 더해주면 된다
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < n; i++)
            hashMap.put(sc.nextInt(), i + 1);

        int[] downsideIdx = new int[n + 1];
        for (int i = 0; i < n; i++)
            downsideIdx[hashMap.get(sc.nextInt())] = i + 1;
        fenwickTree = new long[n + 1];      // 펜윅 트리 생성

        long sum = 0;
        for (int i = 1; i < downsideIdx.length; i++) {
            sum += getRightSideChecked(downsideIdx[i]);     // 자신보다 오른쪽에 이미 방문한 기기가 몇 개인지 센다.
            check(downsideIdx[i]);      // 자신 방문 체크.
        }
        System.out.println(sum);
    }

    static long getRightSideChecked(int idx) {      // idx보다 오른쪽에 방문한 기기들이 몇개인지 센다.
        return getSum(fenwickTree.length - 1) - getSum(idx);
    }

    static void check(int idx) {        // 현재 기기는 방문했다고 표시를 남겨준다.
        while (idx < fenwickTree.length) {
            fenwickTree[idx]++;
            idx += (idx & -idx);
        }
    }

    static long getSum(int idx) {       // 1 ~ idx까지 방문한 기기들의 수를 가져온다.
        long sum = 0;
        while (idx > 0) {
            sum += fenwickTree[idx];
            idx -= (idx & -idx);
        }
        return sum;
    }
}