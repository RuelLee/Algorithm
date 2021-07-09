/*
 Author : Ruel
 Problem : Baekjoon 2186번 문자판
 Problem address : https://www.acmicpc.net/problem/2186
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 문자판;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static char[][] board;
    static int[][][] memo;
    static String word;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) {
        // 처음엔 완전탐색 문제인 줄 알고, BFS로 풀었다 -> 메모리 펑!
        // 큐에 너무 많은 것들이 담기는 것 같다 -> DFS로 풀어보자 -> 시간 펑 !
        // 같은 작업을 반복하는 경우가 있을 경우 메모이제이션을 통해 시간을 줄일 수 있다 -> 성공
        // 메모이제션은 3차배열로 memo[r][c][nextCharIdx]이다.
        // r, c에 nextCharIdx로 들어왔다면 그에 해당하는 값을 계산하여 저장하거나 저장되어있다면 빠르게 참고한다.
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int M = sc.nextInt();
        int K = sc.nextInt();
        sc.nextLine();
        board = new char[N][];
        for (int i = 0; i < board.length; i++)
            board[i] = sc.nextLine().toCharArray();
        word = sc.nextLine();

        memo = new int[N][M][word.length()];
        for (int[][] me : memo) {
            for (int[] m : me)
                Arrays.fill(m, -1);
        }
        int answer = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == word.charAt(0))      // 각 초기 지점으로부터의 가짓수를 모두 더한다.
                    answer += dfs(i, j, 1, K);
            }
        }
        System.out.println(answer);
    }

    static int dfs(int r, int c, int nextCharIdx, int k) {
        if (nextCharIdx == word.length())       // word.length()로 숫자가 올라왔다는 것은 단어를 완성할 경로를 찾았다는 뜻! 1을 돌려주자!
            return 1;

        if (memo[r][c][nextCharIdx] != -1)      // 이미 계산한 적이 있던 경로라면 메모되어있는 값을 돌려주자.
            return memo[r][c][nextCharIdx];

        int count = 0;
        for (int multi = 1; multi <= k; multi++) {  // k값 만큼
            for (int d = 0; d < dr.length; d++) {   // 4방으로 길이가 늘어난 형태로 탐색
                int nextR = r + dr[d] * multi;
                int nextC = c + dc[d] * multi;

                if (checkArea(nextR, nextC) && board[nextR][nextC] == word.charAt(nextCharIdx))     // 조건이 일치한다면
                    count += dfs(nextR, nextC, nextCharIdx + 1, k);     // 그 값을 받고, count에 합산하여
            }
        }
        return memo[r][c][nextCharIdx] = count;     // 현재 상태를 메모해두자.
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < board.length && c >= 0 && c < board[r].length;
    }
}