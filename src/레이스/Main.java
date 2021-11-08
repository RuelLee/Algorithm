/*
 Author : Ruel
 Problem : Baekjoon 1508번 레이스
 Problem address : https://www.acmicpc.net/problem/1508
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 레이스;

import java.util.Scanner;

public class Main {
    static int[] loc;

    public static void main(String[] args) {
        // 심판을 둘 수 있는 위치, 최소 심판의 수가 주어질 때
        // 어느 곳에 심판을 배치하는 것이 심판 사이의 최소 거리를 가장 멀게 할 수 있는가에 대한 문제
        // 심판을 둘 수 있는 곳이 50가지로 모든 가지수에 대해 계산하기에는 많다
        // 이분탐색으로 거리를 주고, 해당하는 거리에 심판을 몇 명 배치할 수 있는가를 살펴보면 된다
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();           // 총 거리
        int m = sc.nextInt();           // 심판의 수
        int k = sc.nextInt();           // 심판을 둘 수 있는 위치의 수

        loc = new int[k];
        for (int i = 0; i < loc.length; i++)
            loc[i] = sc.nextInt();

        int start = 0;
        int end = n;
        long record = 0;
        while (start <= end) {      // 이분 탐색으로 심판 사이의 최소 거리를 추측한다
            int middle = (start + end) / 2;
            long result = putReferee(middle, m);
            if (result > 0) {       // middle로 배치하는 것이 가능하다면 더 멀리 배치할 수 있는지 확인한다.
                record = result;
                start = middle + 1;
            } else      // middle로 배치하는 것이 불가능한 경우, 범위를 좁힌다.
                end = middle - 1;
        }
        System.out.println(Long.toBinaryString(record));
    }

    static long putReferee(int minDistance, int m) {        // m명의 심판을 minDistance로 배치하는 것이 가능한지 살펴본다.
        int left = loc[0];      // 이전 심판의 위치.
        int count = 1;          // 배치된 심판의 수
        long bitmask = 1;       // 심판의 위치를 비트마스킹
        for (int i = 1; i < loc.length; i++) {      // 두번째 위치부터 살펴보며,
            bitmask <<= 1;      // 이전에 기록된 비트마스킹을 왼쪽으로 한칸씩 민다.
            if (count >= m)         // m명 모두 배치가 끝났다면 게속해서 비트쉬프트만 하고 턴을 넘긴다.
                continue;

            if (loc[i] - left >= minDistance) {     // 이전 심판과의 거리가 minDistance를 넘겼다면
                left = loc[i];          // 이전 위치에 현재 위치를 넣고,
                count++;            // 심판의 수를 하나 늘려주고
                bitmask |= 1;       // 현재 위치를 비트마스킹해 남기도록 한다.
            }
        }
        // m명 모두 배치가 끝났다면 bitmask 값을, 실패했다면 -1을 리턴한다.
        return count >= m ? bitmask : -1;
    }
}