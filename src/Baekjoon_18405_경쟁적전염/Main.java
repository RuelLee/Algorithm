/*
 Author : Ruel
 Problem : Baekjoon 18405번 경쟁적 전염
 Problem address : https://www.acmicpc.net/problem/18405
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18405_경쟁적전염;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * n 크기의 시험관이 주어지며 모든 바이러스는 k이하의 자연수 번호를 갖는다.
        // 각 바이러스는 1초마다 상하좌우로 퍼져나가며, 이미 다른 바이러스가 자리한 경우 퍼져나가지 못한다.
        // (1초 동안 번호가 작은 바이러스가 우선적으로 퍼져나간다)
        // s초가 지난 뒤 x, y에 자리하고 있는 바이러스를 출력하라.
        //
        // 너비우선탐색 문제.
        // 1초마다 번호가 작은 바이러스부터 너비우선탐색을 통해 퍼뜨려나가면 된다.
        // 이를 위해 우선순위큐를 통해 번호가 작은 바이러스를 우선적으로 탐색했다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int[][] testBlocks = new int[n][n];
        // 우선순위큐를 위한 Comparator
        // 위치에 해당하는 idx값을 통해 row, col를 계산하고 바이러스 번호를 비교하여
        // 최소힙을 통해 오름차순으로 탐색한다.
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                int o1Row = o1 / n;
                int o1Col = o1 % n;

                int o2Row = o2 / n;
                int o2Col = o2 % n;

                return Integer.compare(testBlocks[o1Row][o1Col], testBlocks[o2Row][o2Col]);
            }
        };

        // 이번 1초에 사용될 PQ
        PriorityQueue<Integer> currentPQ = new PriorityQueue<>(comparator);
        // 다음 1초에 사용될 PQ
        PriorityQueue<Integer> nextPQ = new PriorityQueue<>(comparator);
        for (int i = 0; i < testBlocks.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < testBlocks[i].length; j++) {
                testBlocks[i][j] = Integer.parseInt(st.nextToken());
                // 바이러스가 존재한다면 nextPQ에 삽입한다.
                if (testBlocks[i][j] != 0)
                    nextPQ.offer(i * n + j);
            }
        }

        // 입력으로 주어지는 값.
        st = new StringTokenizer(br.readLine());
        int s = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());

        // s값을 하나씩 줄여가며 0보다 큰 순간까지.
        while (s-- > 0) {
            // currentPQ에 nextPQ로 대체하고
            currentPQ = nextPQ;
            // nextPQ는 새로 선언해준다.
            nextPQ = new PriorityQueue<>(comparator);
            // currentPQ에 들어있는 모든 바이러스들을 사방으로 퍼뜨린다.
            while (!currentPQ.isEmpty()) {
                int current = currentPQ.poll();
                int row = current / n;
                int col = current % n;

                for (int d = 0; d < 4; d++) {
                    int nextRow = row + dr[d];
                    int nextCol = col + dc[d];

                    // 시험관 안이며, 아직 다음 칸에 바이러스가 존재하지 않다면
                    if (checkArea(nextRow, nextCol, n) && testBlocks[nextRow][nextCol] == 0) {
                        // 바이러스를 퍼뜨리고
                        testBlocks[nextRow][nextCol] = testBlocks[row][col];
                        // nextPQ에 삽입한다.
                        nextPQ.offer(nextRow * n + nextCol);
                    }
                }
            }
        }

        // 최종적으로 x, y에 존재하는 바이러스를 출력한다.
        System.out.println(testBlocks[x - 1][y - 1]);
    }

    static boolean checkArea(int r, int c, int n) {
        return r >= 0 && r < n && c >= 0 && c < n;
    }
}