/*
 Author : Ruel
 Problem : Baekjoon 1987번 알파벳
 Problem address : https://www.acmicpc.net/problem/1987
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 알파벳;

import java.util.HashSet;
import java.util.Scanner;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) {
        // 이전에 나왔던 문자를 다시 지나지 않으면서, 갈 수 있는 최대 거리를 구하는 문제
        // 해쉬셋과 DFS를 이용하여 풀었다
        Scanner sc = new Scanner(System.in);

        int r = sc.nextInt();
        int c = sc.nextInt();

        char[][] board = new char[r][c];
        sc.nextLine();
        for (int i = 0; i < r; i++)
            board[i] = sc.nextLine().toCharArray();

        System.out.println(dfs(0, 0, board, new HashSet<>(), 1));

    }

    static int dfs(int r, int c, char[][] board, HashSet<Character> hashSet, int turn) {
        int maxTurn = turn;     // 최대 턴
        hashSet.add(board[r][c]);       // 현재 위치의 알파벳을 해쉬셋에 넣는다.

        for (int d = 0; d < 4; d++) {       // 4방을 둘러보며
            int nextR = r + dr[d];
            int nextC = c + dc[d];

            if (checkArea(nextR, nextC, board) && !hashSet.contains(board[nextR][nextC]))       // 범위를 벗어나지 않으며, 해쉬셋에 담겨있지 않은 문자를 찾는다.
                maxTurn = Math.max(maxTurn, dfs(nextR, nextC, board, hashSet, turn + 1));       // 찾았다면, turn을 하나 증가 시킨 값으로 dfs를 불러 해당 칸으로 진행시 최대 갈 수 있는 칸의 수를 가져온다.
        }
        hashSet.remove(board[r][c]);        // 해쉬셋에 현재 위치의 문자를 지워준다.
        return maxTurn;     // 최대로 갔던 칸 수를 리턴한다.
    }

    static boolean checkArea(int r, int c, char[][] board) {
        return r >= 0 && r < board.length && c >= 0 && c < board[r].length;
    }
}