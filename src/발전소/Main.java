/*
 Author : Ruel
 Problem : Baekjoon 1102번 발전소
 Problem address : https://www.acmicpc.net/problem/1102
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 발전소;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class State {
    int numOfWorkingStation;
    int bitmask;
    int costSum;

    public State(int numOfWorkingStation, int bitmask, int costSum) {
        this.numOfWorkingStation = numOfWorkingStation;
        this.bitmask = bitmask;
        this.costSum = costSum;
    }
}

public class Main {
    public static void main(String[] args) {
        // 카카오 블라인드 코테에도 나왔던 비트마스킹
        // 적은 수의 항목에 대해 두 가지의 상태가 있다면 비트마스킹으로 나타낼 수 있다.
        // 각 발전소의 on/off 두 가지 상태를 비트마스킹을 통해 나타내자.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[][] costs = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                costs[i][j] = sc.nextInt();
        }
        int bitmask = 0;
        int count = 0;
        sc.nextLine();
        String line = sc.nextLine();
        for (int i = 0; i < n; i++) {
            if (line.charAt(i) == 'Y') {
                bitmask |= 1 << i;
                count++;
            }
        }
        int p = sc.nextInt();

        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(count, bitmask, 0));
        int answer = Integer.MAX_VALUE;
        int[] minCosts = new int[(int) Math.pow(2, n)];     // 비트마스킹 상태에 따른 최소 비용을 저장해준다.
        Arrays.fill(minCosts, Integer.MAX_VALUE);
        minCosts[bitmask] = 0;
        while (!queue.isEmpty()) {
            State current = queue.poll();
            if (current.numOfWorkingStation >= p)           // p 개 이상의 발전소가 켜져있다면 최소비용인지 확인
                answer = Math.min(answer, current.costSum);

            if (current.numOfWorkingStation > p)        // p 초과하는 발전소가 켜져있다면 다른 발전소를 더 켜는 경우는 생각하지 않는다.
                continue;

            for (int i = 0; i < n; i++) {       // i는 켜진 발전소
                if ((current.bitmask & 1 << i) != 0) {
                    for (int j = 0; j < n; j++) {           // j는 꺼진 발전소
                        if (((current.bitmask & 1 << j) == 0) && minCosts[current.bitmask | 1 << j] > current.costSum + costs[i][j]) {      // 다음 상태에 도달하는 최소비용이 갱신된다면
                            queue.offer(new State(current.numOfWorkingStation + 1, current.bitmask | 1 << j, current.costSum + costs[i][j]));       // queue에 다음 상태를 삽입
                            minCosts[current.bitmask | 1 << j] = current.costSum + costs[i][j];         // 최소비용 갱신
                        }
                    }
                }
            }
        }
        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }
}