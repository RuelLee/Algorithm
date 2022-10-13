/*
 Author : Ruel
 Problem : Baekjoon 16928번 뱀과 사다리 게임
 Problem address : https://www.acmicpc.net/problem/16928
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16928_뱀과사다리게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 뱀과 사다리 게임을 한다.
        // n개의 사다리, m개의 뱀이 주어지고, 1 ~ 6 값을 갖는 주사위가 주어진다.
        // 원하는 주사위 값을 뽑을 수 있다고 할 때, 100번 칸에 도달하는 최소 주사위 던짐 횟수는?
        //
        // BFS 문제
        // 뱀이나 사다리를 만나면 해당 위치로 반드시 이동해야하므로
        // 이동하는 위치에서의 최소 주사위값은 기록하지 않고, 도착한 곳의 최소 주사위값을 계산한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 사다리의 수
        int n = Integer.parseInt(st.nextToken());
        // 뱀의 수
        int m = Integer.parseInt(st.nextToken());

        // 뱀과 사다리를 구분할 필요는 없으므로 한번에 배열로 입력 받는다.
        int[] connections = new int[101];
        for (int i = 0; i < n + m; i++) {
            st = new StringTokenizer(br.readLine());
            connections[Integer.parseInt(st.nextToken())] = Integer.parseInt(st.nextToken());
        }

        // 각 칸에 이르는 최소 주사위 수.
        int[] minDice = new int[101];
        Arrays.fill(minDice, Integer.MAX_VALUE);
        // 시작 위치 초기값.
        minDice[1] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        while (!queue.isEmpty()) {
            // 현재 위치.
            int current = queue.poll();
            
            // 1 ~ 6까지의 주사위값
            for (int i = 1; i <= 6; i++) {
                // 현재 위치 + 주사위 눈의 값이 100을 넘을 수 없다.
                if (current + i > 100)
                    break;

                // 이동하는 위치에 사다리나 뱀이 있다면, 해당하는 위치로,
                // 없다면 원래 위치가 도착지.
                int end = connections[current + i] == 0 ? current + i : connections[current + i];
                // 도착지의 최소 주사위 수가 갱신된다면
                if (minDice[end] > minDice[current] + 1) {
                    // 최소 주사위 값 갱신.
                    minDice[end] = minDice[current] + 1;
                    // 큐 추가.
                    queue.offer(end);
                }
            }
        }

        // 모든 계산이 끝났다면, 100번째 칸에 도달하는 최소 주사위 수를 출력한다.
        System.out.println(minDice[100]);
    }
}