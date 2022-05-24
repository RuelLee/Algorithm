/*
 Author : Ruel
 Problem : Baekjoon 1799번 비숍
 Problem address : https://www.acmicpc.net/problem/1799
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1799_비숍;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    static int n;
    static List<List<Integer>> connections;
    static int[] matched;

    public static void main(String[] args) throws IOException {
        // n * n의 보드판에 비숍을 놓을 수 있는 위치가 주어진다
        // 놓을 있는 위치에 비숍을 위치하되, 비숍끼리 서로 잡을 수 있는 위치에 두어서는 안된다
        // 최대한 많은 비숍을 배치한다면 몇 개의 비숍을 배치할 수 있는가
        //
        // 위 문제는 n의 크기가 10이하 작아 백트래킹을 사용할 수 있다
        // 하지만 룩 배치하기(https://www.acmicpc.net/problem/9525) 문제와 같이 이분 매칭을 통해서 풀 수도 있다
        // 비숍이 잡을 수 있는 범위는 왼쪽 아래에서 오른쪽 위(편의상 방향1), 왼쪽 위에서 오른쪽 아래(방향 2)와 같이 대각선 방향으로 잡을 수 있기 때문에
        // 비숍을 놓을 수 있는 위치를 방문하며 방향1과 방향2를 구해 이분 그래프를 만들고 이를 통해 이분매칭을 시켜주면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());
        int[][] board = new int[n][];
        for (int i = 0; i < n; i++)
            board[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        connections = new ArrayList<>();
        for (int i = 0; i < 2 * n; i++)
            connections.add(new ArrayList<>());

        // 위치할 수 있는 지점들을 방문하며 이분그래프를 만든다.
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++)
                if (board[i][j] == 1)       // 비숍을 놓을 수 있는 위치라면 방향1과 방향2를 연결시켜준다.
                    connections.get(toRightUpIdx(i, j)).add(toRightDownIdx(i, j));
        }
        // 방향2에 매칭된 방향1을 기록할 공간.
        matched = new int[2 * n];
        Arrays.fill(matched, -1);

        int sum = 0;
        // 방향1값에 따른 방향2를 매칭시킨다.
        for (int i = 0; i < 2 * n; i++) {
            if (bipartiteMatching(i, new boolean[2 * n]))       // 방향1과 방향2를 매칭하는 것이 성공했다면
                sum++;      // 놓은 비숍의 수 증가
        }
        // 최대 비숍의 수 출력.
        System.out.println(sum);
    }

    static boolean bipartiteMatching(int n, boolean[] visited) {
        // 방문 체크
        visited[n] = true;

        // 방향 1과 연결된 모든 방향2들에 대해서
        for (int rightDownIdx : connections.get(n)) {
            // 아직 매칭이 안됐거나, 매칭된 1을 옮길 수 있다면 옮기고
            if (matched[rightDownIdx] == -1 ||
                    (!visited[matched[rightDownIdx]] && bipartiteMatching(matched[rightDownIdx], visited))) {
                // 해당 자리에 방향1을 매칭시킨다.
                matched[rightDownIdx] = n;
                // 매칭이 완료되었으므로 true 리턴.
                return true;
            }
        }
        // 매칭이 되지 않는다면 false 리턴.
        return false;
    }


    static int toRightUpIdx(int r, int c) {
        return r + c;
    }

    static int toRightDownIdx(int r, int c) {
        return r - c + n - 1;
    }
}