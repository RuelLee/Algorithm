/*
 Author : Ruel
 Problem : Baekjoon 2517번 달리기
 Problem address : https://www.acmicpc.net/problem/2517
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2517_달리기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Main {
    static int[] fenwickTree;

    public static void main(String[] args) throws IOException {
        // 각 선수들의 실력과 현재 순위가 주어질 때
        // 현재 자신보다 순위는 앞서있지만 실력이 낮다면 추월이 가능하다고 한다.
        // 각 선수의 가능한 가장 좋은 성적을 나타내라
        // 선수는 최대 50만, 실력은 최대 10억이므로, 실력의 범위를 줄여서 계산해야한다.
        // 18870번 좌표 압축(https://www.acmicpc.net/problem/18870)에서 처럼 순서만 기록해버리자.
        // 그 후 세그먼트 트리에 현재 순위 대로 값을 넣으면서, 자신보다 낮은 실력을 갖고 있는 선수들을 제칠 때 가능한 순위를 기록하자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] players = new int[n];
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> players[o]));
        for (int i = 0; i < n; i++) {
            players[i] = Integer.parseInt(br.readLine());
            priorityQueue.offer(i);
        }

        int count = 1;
        while (!priorityQueue.isEmpty())
            players[priorityQueue.poll()] = count++;

        fenwickTree = new int[n + 1];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < players.length; i++) {      // 현재 순위 순서대로
            // 펜윅 트리에(=자신 보다 현재 앞선 순위의) 자신보다 낮은 실력의 선수가 몇 명있는지 확인
            // 그 선수들을 모두 추월할 경우의 순위는 현재의 순위(i + 1)에서 해당 선수들의 수를 빼준다.
            sb.append((i + 1 - getSlowerPlayer(players[i]))).append("\n");
            // 그리고 펜윅 트리에 현재 선수 추가.
            input(players[i]);
        }
        System.out.println(sb);
    }

    static void input(int n) {
        while (n < fenwickTree.length) {
            fenwickTree[n]++;
            n += (n & -n);
        }
    }

    static int getSlowerPlayer(int n) {
        int sum = 0;
        while (n > 0) {
            sum += fenwickTree[n];
            n -= (n & -n);
        }
        return sum;
    }
}