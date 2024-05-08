/*
 Author : Ruel
 Problem : Baekjoon 14204번 표 정렬
 Problem address : https://www.acmicpc.net/problem/14204
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14204_표정렬;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * m 크기의 표가 주어진다.
        // 표의 각 칸에는 1 ~ n * m 중의 수가 한 번씩만 등장한다.
        // 행 우선 순서를 이용해 만든 수열을 값 수열이라고 한다.
        // 표를 수정할 수 있는 방법은 두 가지가 있는데
        // 임의의 두 행을 서로 교체하거나, 임의의 두 열을 교체하는 방법이다.
        // 값 수열을 오름차순으로 만들 수 있는지 계산하라
        //
        // 애드 혹
        // 열에 따라 정렬을 하되, 해당하는 순서가
        // 모든 행에 대해 적용되는지, 또한 행 내에서 서로 간의 값 범위를 넘나들진 않는지 확인해야한다.
        // 따라서
        // 우선순위큐로 모든 칸의 idx를 넣고, 총 n * m번을 꺼내는데
        // m번 꺼내는 동안 같은 행의 수만 나와야하고, 나오는 열의 순서가 항상 동일해야만
        // 값 수열을 오름차순으로 만들 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 표
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] table = new int[n][];
        for (int i = 0; i < table.length; i++)
            table[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 우선순위큐로 모든 idx를 담는다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> table[o / m][o % m]));
        for (int i = 0; i < n * m; i++)
            priorityQueue.offer(i);

        // 먼저 순서를 정한다.
        // 가장 작은 값부터 m개의 값을 꺼내 같은 행인지 확인하고
        // 해당 열의 순서를 기록한다.
        int[] order = new int[m];
        int first = priorityQueue.poll();
        int row = first / m;
        order[0] = first % m;
        boolean possible = true;
        for (int i = 1; i < order.length; i++) {
            if (row != priorityQueue.peek() / m) {
                possible = false;
                break;
            }
            order[i] = priorityQueue.poll() % m;
        }

        // 순서를 정하는 동안 같은 행의 값만 나왔다면
        // 다른 행에 대해서도 같은 열 순서를 유지하는지 확인한다.
        if (possible) {
            while (!priorityQueue.isEmpty() && possible) {
                // 이번에 순서가 될 열
                row = priorityQueue.peek() / m;
                // m개의 수에 대해 열 순서를 유지하는지 확인한다.
                for (int i = 0; i < m; i++) {
                    if (row != priorityQueue.peek() / m ||
                            order[i] != priorityQueue.poll() % m) {
                        possible = false;
                        break;
                    }
                }
            }
        }
        // 모두 통과했다면 가능한 경우이므로 1을 출력
        // 그렇지 않다면 0을 출력
        System.out.println(possible ? 1 : 0);
    }
}