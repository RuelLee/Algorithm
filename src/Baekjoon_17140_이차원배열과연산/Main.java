/*
 Author : Ruel
 Problem : Baekjoon 17140번 이차원 배열과 연산
 Problem address : https://www.acmicpc.net/problem/17140
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17140_이차원배열과연산;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 크기 3 * 3 크기의 배열이 주어지고, 1초가 지날 때마다 해당 연산이 적용된다.
        // 1. 행의 개수 >= 열의 개수인 경우, 모든 행에 대해 정렬을 수행.
        // 2. 행의 개수 < 열의 개수인 경우, 모든 열에 대해 정렬을 수행
        // 여기서 말하는 정렬이란
        // 현재 배열에 등장하는 수의 개수가 커지는 순으로, 수의 개수가 같다면 수 자체가 커지는 순으로 정렬하는 것을 말한다.
        // 예를 들어 3 1 1이라면
        // 3이 1번, 1이 2번이므로, 3 1 1 2가 된다.
        // 다시 한번 연산을 적용하면 2가 1번, 3이 1번, 1이 2번이므로 2 1 3 1 1 2가 된다.
        // 전체 배열의 크기는 행과 열 모두 100이고, 100을 넘어설 경우, 앞의 100개의 수만 다룬다.
        // (r, c)의 값이 k가 되는 최소 시간을 구하라
        // 100초가 넘어서도 k가 되지 않는다면 -1을 출력한다
        //
        // 정렬, 시뮬레이션 문제
        // 매 초마다 행의 개수와 열의 개수를 비교하여 연산을 적용해나가면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 목표하는 (r, c)의 값은 k
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 최대 100행, 100열까지만 계산한다.
        int[][] board = new int[100][100];
        for (int i = 0; i < 3; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++)
                board[i][j] = Integer.parseInt(st.nextToken());
        }
        
        // 시간과 현재 열과 행의 크기
        int time = 0;
        int row = 3;
        int col = 3;
        while (time <= 100 && board[r - 1][c - 1] != k) {
            // 시간 증가
            time++;
            // 첫번째 연산이 적용되는 경우.
            if (row >= col) {
                // 길이를 초기화하고,
                // 새로 생성되는 배열의 길이를 비교하여 큰 값을 남겨둔다.
                col = 0;
                // 모든 행에 대해
                for (int i = 0; i < row; i++) {
                    // 수의 개수를 센다.
                    int[] counts = new int[110];
                    for (int j = 0; j < board[i].length; j++)
                        counts[board[i][j]]++;

                    // 우선 순위큐를 통해
                    // 등장하는 개수가 적은 순, 개수가 같다면 값이 적은 순으로 정렬한다.
                    PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((o1, o2) -> {
                        if (o1[1] == o2[1])
                            return Integer.compare(o1[0], o2[0]);
                        return Integer.compare(o1[1], o2[1]);
                    });
                    for (int j = 1; j < counts.length; j++) {
                        if (counts[j] != 0)
                            priorityQueue.offer(new int[]{j, counts[j]});
                    }

                    int idx = 0;
                    // 우선순위큐가 아직 비지 않고, idx가 100보다 작은 경우동안 계속한다.
                    while (!priorityQueue.isEmpty() && idx < 100) {
                        // 수를 꺼내
                        int[] current = priorityQueue.poll();
                        // idx에는 현재 수의 값
                        board[i][idx++] = current[0];
                        // idx + 1에는 현재 수의 개수를 등록한다.
                        board[i][idx++] = current[1];
                        // 각각 ++ 연산을 했으므로 처음 idx에 비해 +2가 된 상태.
                    }
                    // 혹시라도 길이가 짧아지며 값이 남아있을 수 있으므로 나머지 공간은 0으로 채운다.
                    // 1 1 1 같은 경우 -> 1 3으로 짧아진다.
                    for (int j = idx; j < board[i].length; j++)
                        board[i][j] = 0;
                    // idx와 col을 비교하여 더 큰 수를 남겨둔다.
                    col = Math.max(col, idx);
                }
            } else {        // 열의 개수가 더 많은 경우. 열에 대해 동일한 연산을 한다.
                row = 0;
                for (int i = 0; i < col; i++) {
                    int[] counts = new int[110];
                    for (int j = 0; j < board.length; j++)
                        counts[board[j][i]]++;

                    PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((o1, o2) -> {
                        if (o1[1] == o2[1])
                            return Integer.compare(o1[0], o2[0]);
                        return Integer.compare(o1[1], o2[1]);
                    });
                    for (int j = 1; j < counts.length; j++) {
                        if (counts[j] != 0)
                            priorityQueue.offer(new int[]{j, counts[j]});
                    }

                    int idx = 0;
                    while (!priorityQueue.isEmpty() && idx < 100) {
                        int[] current = priorityQueue.poll();
                        board[idx++][i] = current[0];
                        board[idx++][i] = current[1];
                    }
                    for (int j = idx; j < board.length; j++)
                        board[j][i] = 0;
                    row = Math.max(row, idx);
                }
            }
        }
        // 최종적으로 시간이 101이 되었다면 불가능한 경우이므로 -1을 출력하고
        // 그 외의 경우 time을 출력한다.
        System.out.println(time == 101 ? -1 : time);
    }
}