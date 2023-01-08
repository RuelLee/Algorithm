/*
 Author : Ruel
 Problem : Baekjoon 2414번 게시판 구멍 막기
 Problem address : https://www.acmicpc.net/problem/2414
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2414_게시판구멍막기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * m 크기의 게시판에 구멍이 뚫려있다.
        // 폭이 1이고, 길이가 무한대인 테이프를 최소한으로 끊어 모든 구멍을 막으려고 한다.
        // 단 구멍이 뚫리지 않은 곳을 테이프로 막아서는 안된다.
        // 최소한의 테이프를 끊어내는 횟수는 몇 회인가.
        //
        // 이분 매칭 문제.
        // 예전에 풀었던 돌멩이 제거(https://www.acmicpc.net/problem/1867) 문제와 유사한 문제
        // 차이점은 돌멩이 제거는 행이나 열을 선택해 걸어가면서 돌멩이를 주으므로
        // 구멍이 뚫리지 않은 곳을 막지 않는다는 조건이 없는 것과 같다.
        // 당시에도 해당 row에 있는 돌멩이들의 col을 표시하며
        // row마다 하나씩 col을 할당해나간다.
        // 해당 하는 row에 속하는 돌멩이들이 서로 다른 매칭을 갖고 있다면 각 매칭들은 세로로 작용하며
        // 매칭이 없는 돌멩이가 존재한다면 하나의 매칭의 가로로 작용한다.
        // 대신 이번 문제는 구멍이 없는 곳을 메워서는 안되기 때문에
        // 단순한 가로, 세로로 나누어서는 안된다.
        // 연속한 가로 구멍들을 하나로, 연속한 세로 구멍들을 하나로 인덱스처리해주고, 이를 매칭해나가면 된다!
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 주어지는 게시판
        char[][] board = new char[n][];
        for (int i = 0; i < board.length; i++)
            board[i] = br.readLine().toCharArray();

        // 연속한 가로 구멍들로 인덱스 처리한다.
        int[][] rowIdxes = new int[n][m];
        int counter = 1;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == '*' && rowIdxes[i][j] == 0) {
                    int col = j;
                    while (col < board[i].length && board[i][col] == '*')
                        rowIdxes[i][col++] = counter;
                    counter++;
                }
            }
        }

        // row 인덱스들과 매칭되는 col 인덱스들의 정보를 저장할 리스트.
        List<HashSet<Integer>> links = new ArrayList<>();
        for (int i = 0; i < counter; i++)
            links.add(new HashSet<>());

        // 연속한 세로 구멍들로 인덱스 처리한다.
        int[][] colIdxes = new int[n][m];
        counter = 1;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == '*' && colIdxes[i][j] == 0) {
                    int row = i;
                    while (row < board.length && board[row][j] == '*')
                        colIdxes[row++][j] = counter;
                    counter++;
                }
            }
        }

        // 각 row와 col 인덱스들이 서로 연결된 정보들을 읽는다.
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (rowIdxes[i][j] != 0)
                    links.get(rowIdxes[i][j]).add(colIdxes[i][j]);
            }
        }

        // 해당 col 인덱스들에 매칭된 row 인덱스 정보를 기록한다.
        int[] matchedRowIdx = new int[counter];
        // 1번부터 모든 row 인덱스들에게 가능한 col 인덱스들을 매칭한다.
        for (int i = 1; i < links.size(); i++)
            bipartiteMatching(i, matchedRowIdx, new boolean[links.size()], links);

        // 매칭이 완료된 row 인덱스들의 개수를 센다.
        // 해당 개수가 필요한 테이프의 개수.
        System.out.println(Arrays.stream(matchedRowIdx).filter(value -> value != 0).count());
    }
    
    // 이분 매칭
    static boolean bipartiteMatching(int rowIdx, int[] matchedRowIdx, boolean[] visited, List<HashSet<Integer>> links) {
        if (visited[rowIdx])
            return false;

        // 방문 체크
        visited[rowIdx] = true;
        // 해당 row idx에 매칭 가능한 col idx 들을 살펴보며, 매칭한다.
        for (int colIdx : links.get(rowIdx)) {
            // 해당 col idx가 아직 매칭이 안됐거나
            // col idx에 매칭된 row idx를 다른 값으로 바꿀 수 있다면
            if (matchedRowIdx[colIdx] == 0 ||
                    bipartiteMatching(matchedRowIdx[colIdx], matchedRowIdx, visited, links)) {
                // 이번 colIdx에 rowIdx를 매칭하고 true 반환
                matchedRowIdx[colIdx] = rowIdx;
                return true;
            }
        }
        // 매칭이 불가능하다면 false 반환.
        return false;
    }
}