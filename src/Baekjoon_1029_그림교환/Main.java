/*
 Author : Ruel
 Problem : Baekjoon 1029번 그림 교환
 Problem address : https://www.acmicpc.net/problem/1029
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1029_그림교환;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

class State {
    int owner;
    int bitmask;

    public State(int owner, int bitmask) {
        this.owner = owner;
        this.bitmask = bitmask;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 최대가 15명인 n명의 사람이 주어진다. 0번 사람은 항상 0원에 그림을 산다고 한다
        // n * n 행렬이 주어지는데, i행 j열이 의미하는 바는 i번 사람의 그림을 j번 사람이 살 때, 행렬의 값을 지불한다는 뜻이다.
        // 그림은 항상 같거나 더 높은 가격으로만 팔린다고 한다.
        // 그림이 최대한 많은 사람을 거쳐가려고 할 때, 그 사람의 수는?
        //
        // 최대 15명 -> 비트마스킹이 가능하다
        // minCost[owner][bitmask]으로 하자
        // bitmask는 현재까지 해당 그림을 구매했던 사람이고, owner는 현재 그림을 소유한 사람이다.
        // 그리고 dp의 값은 그 때의 최소값으로 설정하자.
        // 0번 사람이 0원에 살 때부터 시작하여, 다른 사람들이 이를 구매했을 때를 고려해간다.
        // 최종적으로 초기값이 아닌 비트마스킹 값을 비교해가며 가장 많은 사람을 거쳐간 값을 찾는다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[][] costMatrix = new int[n][n];
        for (int i = 0; i < costMatrix.length; i++) {
            String row = br.readLine();
            for (int j = 0; j < costMatrix[i].length; j++)
                costMatrix[i][j] = row.charAt(j) - '0';
        }

        // owner와 bitmask을 통한 메모이제이션
        int[][] minCost = new int[n][1 << n + 1];
        // 최대값으로 초기 세팅해준다.
        for (int[] mc : minCost)
            Arrays.fill(mc, Integer.MAX_VALUE);
        // 처음, 0번 사람이 소유했을 때 가격은 0원
        minCost[0][1] = 0;
        // 해당 상태를 큐에 담아준다.
        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(0, 1));
        while (!queue.isEmpty()) {
            State current = queue.poll();

            // 다른 사람들을 살펴본다
            for (int i = 0; i < costMatrix.length; i++) {
                // 비트마스킹에 표시되지 않은 사람이고,
                // owner에게서 i가 구매하는 값이 i가 구매한 값 이상인지 확인하고
                // 기존에 메모됐던 값보다 더 작은 값이라면 값을 갱신해주고, 큐에 담아준다.
                if ((current.bitmask & (1 << i)) == 0 &&
                        minCost[current.owner][current.bitmask] <= costMatrix[current.owner][i] &&
                        minCost[i][current.bitmask | (1 << i)] > costMatrix[current.owner][i]) {
                    minCost[i][current.bitmask | (1 << i)] = costMatrix[current.owner][i];
                    queue.offer(new State(i, current.bitmask | (1 << i)));
                }
            }
        }

        // 비트마스킹 필드를 탐색한다.
        int max = 0;
        for (int i = 0; i < minCost.length; i++) {
            for (int j = 0; j < minCost[i].length; j++) {
                // 값이 초기값이 아니라면 해당 값이 유효한 값이다.
                // 그 때 비트마스킹 값을 통해 몇 명이 거쳐갔는지 확인하고 최대값을 갱신한다.
                if (minCost[i][j] != Integer.MAX_VALUE)
                    max = Math.max(max, bitmaskToPeople(j));
            }
        }
        System.out.println(max);
    }

    // 비트마스킹 값을 토대로 몇명이 표시되었는지 확인한다.
    static int bitmaskToPeople(int bitmask) {
        int count = 0;
        // bitmask가 0보다 큰 동안
        while (bitmask > 0) {
            // 가장 오른쪽 비트값이 1인지 확인하고, 맞다면 한명을 세어준다.
            if (bitmask % 2 == 1)
                count++;
            // 그리고 2를 나눠(= 오른쪽으로 비트들을 한칸씩 밀어)준다.
            bitmask /= 2;
        }
        return count;
    }
}