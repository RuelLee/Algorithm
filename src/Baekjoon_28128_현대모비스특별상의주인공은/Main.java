/*
 Author : Ruel
 Problem : Baekjoon 28128번 현대모비스 특별상의 주인공은?
 Problem address : https://www.acmicpc.net/problem/28128
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28128_현대모비스특별상의주인공은;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] dr = {1, 2, 0, 0};
    static int[] dc = {0, 0, 1, 2};

    public static void main(String[] args) throws IOException {
        // n * m 크기의 추첨판이 주어진다.
        // 세로 a, 가로 b의 격자판이 주어지고, 이를 통해 추첨판을 덮었을 때,
        // 덮인 구역에 이름이 (a * b + 1) / 2 이상 등장한 학생은 선물을 받을 수 있다고 한다.
        // 이 때 가능한 학생들은?
        // 그러한 학생이 없다면 MANIPULATED를 출력한다
        //
        // 애드 혹 문제
        // 먼저 같은 이름이 연속하여 있는 경우, 1 * 2 크기의 격자판으로 추첨될 가능성이 있다.
        // 연속하지 않은데, 추첨될 가능성이 있는 경우는 하나 건너뛰어있는 경우
        // 1 * 3 크기의 격자판으로 추첨될 가능성이 존재한다.
        // 따라서 위, 경우를 계산하여, 가능성이 있는 학생들의 이름을 출력한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 격자
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] board = new int[n][m];
        
        // 이름에 번호를 할당하여 숫자로 저장
        HashMap<String, Integer> hashMap = new HashMap<>();
        List<String> names = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < board[i].length; j++) {
                String name = st.nextToken();
                if (!hashMap.containsKey(name)) {
                    hashMap.put(name, hashMap.size());
                    names.add(name);
                }
                board[i][j] = hashMap.get(name);
            }
        }
        
        // 추첨 가능성이 있는 학생들의 이름
        PriorityQueue<String> candidates = new PriorityQueue<>();
        // 이미 추첨 가능성 있다고 표시한 학생들
        boolean[] checked = new boolean[hashMap.size() + 1];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                // 이미 계산됐다면 건너뛰고
                if (checked[board[i][j]])
                    continue;

                // 아닌 경우, 오른쪽 칸과 2번째 오른쪽 칸, 아랫칸과 두번째 아랫칸에
                // 동일한 이름이 존재하는지 확인한다.
                for (int d = 0; d < 4; d++) {
                    int nextR = i + dr[d];
                    int nextC = j + dc[d];

                    if (nextR < board.length && nextC < board[nextR].length &&
                            board[i][j] == board[nextR][nextC] && !checked[board[i][j]]) {
                        checked[board[i][j]] = true;
                        candidates.offer(names.get(board[i][j]));
                        break;
                    }
                }
            }
        }
        
        // 답안 작성
        StringBuilder sb = new StringBuilder();
        if (candidates.isEmpty())
            sb.append("MANIPULATED").append("\n");
        while (!candidates.isEmpty())
            sb.append(candidates.poll()).append("\n");
        // 답 출력
        System.out.print(sb);
    }
}