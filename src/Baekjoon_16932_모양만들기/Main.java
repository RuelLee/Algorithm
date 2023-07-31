/*
 Author : Ruel
 Problem : Baekjoon 16932번 모양 만들기
 Problem address : https://www.acmicpc.net/problem/16932
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16932_모양만들기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static int[][] board;

    public static void main(String[] args) throws IOException {
        // n * m 칸에 0 또는 1이 들어있다.
        // 1이 인접한 칸끼리 연결했을 때, 연결 요소를 모양이라고 부른다.
        // 모양의 크기는 연결된 1의 개수이다.
        // 0인 칸 중 하나를 1로 바꿀 때, 만들 수 있는 모양의 최대 크기는?
        //
        // 그래프 탐색 문제
        // 0일 때 인접한 모양의 크기들을 합쳐 최대 크기의 개수를 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        board = new int[n][];
        for (int i = 0; i < board.length; i++)
            board[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 각 칸이 속한 그룹
        int[][] group = new int[n][m];
        int groupCounter = 0;
        // 모양의 최대 크기
        int max = 0;
        // 모양의 크기
        HashMap<Integer, Integer> groupMembers = new HashMap<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                // 1일 때는 건너뛰낟.
                if (board[i][j] == 1)
                    continue;
                
                // 0일 때는 해당 칸을 1로 바꾸고
                int sum = 1;
                HashSet<Integer> hashSet = new HashSet<>();
                // 인접한 칸들에 1이 있는지 확인한다.
                for (int d = 0; d < 4; d++) {
                    int nearR = i + dr[d];
                    int nearC = j + dc[d];

                    // 인접한 칸이 1이라면 해당 모양의 크기를 찾는다.
                    if (checkArea(nearR, nearC) && board[nearR][nearC] == 1) {
                        // 처음 만난 모양이라면 크기를 계산한다.
                        if (group[nearR][nearC] == 0) {
                            // 새로운 그룹 번호
                            group[nearR][nearC] = ++groupCounter;
                            // BFS를 통해 인접한 1들의 개수를 찾는다.
                            Queue<Integer> queue = new LinkedList<>();
                            queue.offer(nearR * m + nearC);
                            int counter = 1;
                            while (!queue.isEmpty()) {
                                int row = queue.peek() / m;
                                int col = queue.poll() % m;

                                for (int delta = 0; delta < 4; delta++) {
                                    int nextR = row + dr[delta];
                                    int nextC = col + dc[delta];

                                    if (checkArea(nextR, nextC) && board[nextR][nextC] == 1
                                            && group[nextR][nextC] == 0) {
                                        group[nextR][nextC] = groupCounter;
                                        queue.offer(nextR * m + nextC);
                                        counter++;
                                    }
                                }
                            }
                            // 찾은 모양의 크기를 넣는다.
                            groupMembers.put(groupCounter, counter);
                        }

                        // (i, j)에서 4방향을 확인하지만, 같은 모양이 또 나타날 수 있다.
                        // 이를 막기 위해 해쉬셋을 통해 그룹 번호(모양 번호)는 중복되지 않게 한다.
                        if (!hashSet.contains(group[nearR][nearC])) {
                            sum += groupMembers.get(group[nearR][nearC]);
                            hashSet.add(group[nearR][nearC]);
                        }
                    }
                }
                // (i, j)를 1로 바꿨을 때, 최대 모양의 크기는 sum
                // max를 갱신하는지 확인
                max = Math.max(max, sum);
            }
        }
        // 찾은 최대 모양의 크기를 출력한다.
        System.out.println(max);
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < board.length && c >= 0 && c < board[r].length;
    }
}