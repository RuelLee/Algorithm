/*
 Author : Ruel
 Problem : Baekjoon 9525번 룩 배치하기
 Problem address : https://www.acmicpc.net/problem/9525
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9525_룩배치하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static List<List<Integer>> connections;

    public static void main(String[] args) throws IOException {
        // N x N 크기의 체스판에 최대란 룩을 많이 배치하려고 한다
        // 이럴 경우 대각선으로 룩을 배치하면 되지만, 폰이 중간 중간 있다면 다른 문제가 된다
        // N x N 체스판과 폰의 위치가 주어질 때, 최대한 배치할 수 있는 룩의 개수는?
        //
        // NQueen의 경우 백트래킹을 활용해서 풀 수 있다
        // 하지만 룩의 경우에는, 퀸과 다르게 대각선을 고려하지 않아도 된다
        // 다만 원래의 경우 하나의 열과 하나의 행에는 하나의 룩만 위치할 수 있지만
        // 폰이 있는 경우, 폰을 기준으로 좌우, 상하로 2개의 룩을 배치하는 것이 가능해진다
        // 따라서 가로와 세로로 룩을 놓을 수 있는 영역을 나눈다
        // 예를 들어
        // X....
        // X....
        // ..X..
        // .X...
        // ....X 과 같이 주어진다면
        //
        // X1111        X2477
        // X2222        X2467
        // 33X44        12X67
        // 5X666        1X567
        // 7777X 과     1356X 와 같이 나눈다
        // 그리고 이분 매칭을 통해 하나의 가로 영역 당 하나의 세로 영역을 선택해 룩을 배치하면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        char[][] board = new char[n][];
        for (int i = 0; i < board.length; i++)
            board[i] = br.readLine().toCharArray();

        int[][] colSplit = new int[n][n];       // 각 행을 폰을 기준으로 영역을 나눈다.
        int count = 1;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 'X')     // X일 경우 영역을 나눈다. count 증가
                    count++;
                else        // 해당하는 영역을 배열에 찍어준다.
                    colSplit[i][j] = count;
            }
            // 이번 행이 끝났다면 count 증가
            count++;
        }

        connections = new ArrayList<>();
        for (int i = 0; i <= count; i++)
            connections.add(new ArrayList<>());

        // 각 열을 폰을 기준으로 영역을 나눠 해당하는 행의 영역에 연결해준다.
        count = 1;
        for (int i = 0; i < board[0].length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[j][i] == 'X')     // X일 경우 영역 분리. count 증가
                    count++;
                else        // 해당하는 행의 영역에 추가시켜준다.
                    connections.get(colSplit[j][i]).add(count);
            }
            // 이번 열이 끝났다면 count 증가
            count++;
        }

        // 열의 영역에 해당하는 행의 영역 번호를 적어준다.
        int[] matched = new int[count + 1];
        count = 0;
        // 행의 영역 1번부터, 매칭되는 열의 영역에 매칭시켜준다.
        for (int i = 1; i < connections.size(); i++) {
            // 매칭이 된다면 count 증가
            if (bipartiteMatching(i, new boolean[matched.length], matched))
                count++;
        }
        // 최종적으로 완성된 count가 놓여진 룩의 개수.
        System.out.println(count);
    }

    // 이분 매칭
    static boolean bipartiteMatching(int rook, boolean[] visited, int[] matched) {
        if (visited[rook])
            return false;

        // 방문 체크
        visited[rook] = true;
        // rook에 해당하는 행의 영역에 놓을 수 있는 열의 영역을 살펴본다.
        for (int block : connections.get(rook)) {
            // 열의 영역에 아직 할당된 행이 없거나, 다른 행으로 옮기는게 가능하다면
            if (matched[block] == 0 || bipartiteMatching(matched[block], visited, matched)) {
                // block에 해당하는 열의 영역에 rook 행을 할당한다.
                matched[block] = rook;
                return true;
            }
        }
        return false;
    }
}