/*
 Author : Ruel
 Problem : Baekjoon 1736번 쓰레기 치우기
 Problem address : https://www.acmicpc.net/problem/1736
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1736_쓰레기치우기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Trash {
    int r;
    int c;

    public Trash(int r, int c) {
        this.r = r;
        this.c = c;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 세로 n, 가로 m 크기의 격자판이 주어진다.
        // 각 지점에 쓰레기들이 주어지는데, 로봇을 이용해 이를 제거하려한다
        // 각 로봇은 왼쪽 위에서 출발해서 오른쪽 아래로만 이동할 수 있다.
        // 필요한 최소 로봇의 수를 출력하라
        //
        // 그리디 문제
        // 쓰레기들을 좌표를 모아서 오른쪽이나 아랫쪽 우선순위를 준 다음
        // 해당 방향이 같다면 나머지 방향에 대해서 우선을 순위를 줘서 정렬을 해준다
        // ex) 행에 대해서 정렬을 하되, 행이 같다면 열에 대해서 정렬 or
        // 열에 대해서 정렬을 하되, 열이 같다면 행에 대해서 정렬
        // 그 후 r, c가 증가하는 방향으로 쓰레기를 주워가며,
        // 몇 사이클을 반복해야 모든 쓰레기가 주어지는지 계산한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        List<Trash> trashes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                if (Integer.parseInt(st.nextToken()) == 1)
                    trashes.add(new Trash(i, j));
            }
        }

        trashes.sort((o1, o2) -> {
            // 행이 같다면 열에 대해 정렬
            if (o1.r == o2.r)
                return Integer.compare(o1.c, o2.c);
            // 기보적으로는 행에 대해 정렬.
            return Integer.compare(o1.r, o2.r);
        });

        // 주운 쓰레기의 수
        int count = 0;
        // 반복 횟수.
        int cycle = 0;
        // 쓰레기 제거 여부 체크.
        boolean[] visited = new boolean[trashes.size()];
        // 전체 쓰레기를 주울 때까지.
        while (count < trashes.size()) {
            // 싸이클(=로봇) 증가
            cycle++;
            // 왼쪽 위에서 시작.
            int currentR = 0;
            int currentC = 0;

            for (int i = 0; i < trashes.size(); i++) {
                // 이미 주운 쓰레기라면 다음으로.
                if (visited[i])
                    continue;
                // 다음 순서의 쓰레기가 아직 줍지 않았고, 이전 쓰레기의 r, c보다 같거나 크다면
                else if (trashes.get(i).r >= currentR && trashes.get(i).c >= currentC) {
                    // 줍는다.
                    visited[i] = true;
                    // currentR, currentC 값 현재 위치 값으로 변경.
                    currentR = trashes.get(i).r;
                    currentC = trashes.get(i).c;
                    // 주운 쓰레기 수 증가.
                    count++;
                }
            }
        }
        // 전체 반복 횟수(=로봇의 개수) 출력.
        System.out.println(cycle);
    }
}