/*
 Author : Ruel
 Problem : Baekjoon 1414번 불우이웃돕기
 Problem address : https://www.acmicpc.net/problem/1414
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1414_불우이웃돕기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    // 집에 n개의 컴퓨터가 있다.
    // 컴퓨터들은 직간접적으로 연결되어있다면 서로 통신이 된다.
    // 컴퓨터들이 서로 연결되어있는 관계와 길이가 주어질 때,
    // 모든 컴퓨터를 연결하는 최소 길이의 랜선만 남겨두고 기부하려고 한다.
    // 기부할 수 있는 랜선의 최대 길이를 출력하라
    // 랜선의 길이는 a부터 z는 1부터 26을 나타내고, A부터 Z는 27부터 52를 나타낸다.
    //
    // 최소 스패닝 트리
    // 길이를 비용으로 보며 최소한의 비용으로 모든 컴퓨터를 연결하고 남은 길이를 기부하자.

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 인접행렬
        int[][] adjMatrix = new int[n][n];
        // 갖고 있는 랜선 길이의 총합
        int sum = 0;
        for (int i = 0; i < adjMatrix.length; i++) {
            String input = br.readLine();
            for (int j = 0; j < adjMatrix[i].length; j++) {
                adjMatrix[i][j] = charToInt(input.charAt(j));
                sum += adjMatrix[i][j] != Integer.MAX_VALUE ? adjMatrix[i][j] : 0;
            }
        }

        // 각 컴퓨터를 연결하는 최소비용
        int[] costs = new int[n];
        Arrays.fill(costs, Integer.MAX_VALUE);
        costs[0] = 0;

        // 우선순위큐를 통해 현재 네트워크에 새로운 컴퓨터를 추가하는 비용이 적은 순으로 살펴본다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(value -> costs[value]));
        priorityQueue.offer(0);
        // 네트워크 연결 여부
        boolean[] connected = new boolean[n];
        // 연결된 컴퓨터 수
        int count = 0;
        // 연결 비용
        int totalCost = 0;
        while (!priorityQueue.isEmpty()) {
            int current = priorityQueue.poll();
            if (connected[current])
                continue;

            // current에서 연결 가능한 컴퓨터들을 살펴본다.
            for (int next = 0; next < adjMatrix[current].length; next++) {
                if (current == next || connected[next])
                    continue;

                // i -> j, j -> i 모두 같은 연결이지만
                // 입력으로는 한번만 주어지므로 양쪽 값을 모두 살펴본다.
                int cost = Math.min(adjMatrix[current][next], adjMatrix[next][current]);
                // 아직 네트워크에 연결안됐고, 네트워크에 추가하는 비용이 최소값을 갱신했다면
                if (cost != Integer.MAX_VALUE && costs[next] > cost) {
                    // 값 갱신
                    costs[next] = cost;
                    // 우선순위큐 추가
                    priorityQueue.remove(next);
                    priorityQueue.offer(next);
                }
            }
            // currnet는 연결되었음.
            connected[current] = true;
            // count 증가
            count++;
            // 비용 추가
            totalCost += costs[current];
        }

        // 모든 컴퓨터들이 연결되었다면 총 랜선 길이에서 연결 비용을 제한 만큼을 기부.
        // 모든 컴퓨터들이 연결되지 않았다면 -1 출력.
        System.out.println(n == count ? (sum - totalCost) : -1);
    }

    static int charToInt(char c) {
        return c >= 'a' ? c - 'a' + 1 : (c >= 'A' ? c - 'A' + 27 : Integer.MAX_VALUE);
    }
}