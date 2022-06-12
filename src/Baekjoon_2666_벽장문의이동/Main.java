/*
 Author : Ruel
 Problem : Baekjoon 2666번 벽장문의 이동
 Problem address : https://www.acmicpc.net/problem/2666
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2666_벽장문의이동;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class State {
    int turn;
    int l;
    int r;

    public State(int turn, int l, int r) {
        this.turn = turn;
        this.l = l;
        this.r = r;
    }
}

public class Main {
    static final int MAX = 20 * 20;

    public static void main(String[] args) throws IOException {
        // 최대 n개의 벽장(최대 20)이 주어지고, 그 앞에 n - 2개의 문이 붙어있다(즉, 2곳은 비어있다.)
        // 벽장문은 서로 옆으로 밀 수 있다.
        // 벽장을 m번에 걸쳐 이용하려고 한다
        // 이 때 벽장문의 이동을 최소화하려고 한다. 그 횟수는?
        //
        // DP문제
        // dp로 m개의 행동에 대해, 현재 앞이 열려있는 벽장 2곳을 표시했다
        // dp[turn][왼쪽열린벽장번호][오른쪽열린벽장번호] = 최소 벽장문 이동 횟수.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 전체 벽장의 개수
        int n = Integer.parseInt(br.readLine());
        // 현재 열려있는 벽장문 2개
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 벽장을 이용하는 횟수.
        int order = Integer.parseInt(br.readLine());
        int[] orders = new int[order + 1];
        // 이용하려는 벽장의 번호는 orders[1]부터 차근차근 저장한다.
        for (int i = 1; i < orders.length; i++)
            orders[i] = Integer.parseInt(br.readLine());

        // DP.
        // minMoves[turn][l][r] = 벽장l과 r이 열린 상태로 turn번째 벽장 이용을 한 최소 횟수.
        int[][][] minMoves = new int[order + 1][n + 1][n + 1];
        // 초기값은 MAX로 설정.
        for (int[][] mm : minMoves)
            for (int[] m : mm)
                Arrays.fill(m, MAX);

        Queue<State> queue = new LinkedList<>();
        // 1번째 명령부터 큐에 넣는다.
        // 초기 상태는 l과 r의 벽장이 열려있다.
        queue.offer(new State(1, Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        // 초기 상태의 이동 횟수는 0
        minMoves[0][queue.peek().l][queue.peek().r] = 0;
        // 모든 이용을 마쳤을 때 최소 이동을 센다.
        int minMove = MAX;
        while (!queue.isEmpty()) {
            State current = queue.poll();
            // 모든 이용을 마쳤다면
            if (current.turn > order) {
                // 최소 이동 횟수를 계산한다.
                minMove = Math.min(minMove, minMoves[current.turn - 1][current.l][current.r]);
                continue;
            }

            // 이번에 열려야하는 벽장 번호.
            int toBeOpen = orders[current.turn];
            // 상황에 따라 l이나 r의 빈공간을 toBeOpen으로 이동시키고 해당 벽장문의 이동이 최소값을 갱신시키는지 확인한다.

            // 열려야하는 벽장이 l보다 작거나 같다면
            if (toBeOpen <= current.l) {
                // l번에 있는 빈 공간을 toBeOpen으로 이동시켰을 때를 계산한다.
                if (minMoves[current.turn][toBeOpen][current.r] >
                        minMoves[current.turn - 1][current.l][current.r] + current.l - toBeOpen) {
                    minMoves[current.turn][toBeOpen][current.r] =
                            minMoves[current.turn - 1][current.l][current.r] + current.l - toBeOpen;
                    queue.offer(new State(current.turn + 1, toBeOpen, current.r));
                }
                // 열려야하는 벽장이 r보다 크거나 같다면
            } else if (toBeOpen >= current.r) {
                // r번에 있는 열린 공간을 toBeOpen으로 이동시켰을 때를 계산한다.
                if (minMoves[current.turn][current.l][toBeOpen] >
                        minMoves[current.turn - 1][current.l][current.r] + toBeOpen - current.r) {
                    minMoves[current.turn][current.l][toBeOpen] =
                            minMoves[current.turn - 1][current.l][current.r] + toBeOpen - current.r;
                    queue.offer(new State(current.turn + 1, current.l, toBeOpen));
                }
                // 열려야하는 벽장이 l과 r 사이에 있다면
            } else {
                // l의 빈공간,
                if (minMoves[current.turn][toBeOpen][current.r] >
                        minMoves[current.turn - 1][current.l][current.r] + toBeOpen - current.l) {
                    minMoves[current.turn][toBeOpen][current.r] =
                            minMoves[current.turn - 1][current.l][current.r] + toBeOpen - current.l;
                    queue.offer(new State(current.turn + 1, toBeOpen, current.r));
                }
                // r의 빈공간을 toBeOpen으로 이동시켰을 때를 각각 계산한다.
                if (minMoves[current.turn][current.l][toBeOpen] >
                        minMoves[current.turn - 1][current.l][current.r] + current.r - toBeOpen) {
                    minMoves[current.turn][current.l][toBeOpen] =
                            minMoves[current.turn - 1][current.l][current.r] + current.r - toBeOpen;
                    queue.offer(new State(current.turn + 1, current.l, toBeOpen));
                }
            }
        }
        System.out.println(minMove);
    }
}