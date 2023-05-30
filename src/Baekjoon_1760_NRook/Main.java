/*
 Author : Ruel
 Problem : Baekjoon 1760번 N-Rook
 Problem address : https://www.acmicpc.net/problem/1760
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1760_NRook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 체스판 위에 최대한 많은 룩을 배치하고자 한다.
        // 아무 조건이 없는 룩이라면 구하기 쉬우므로 몇가지 조건을 추가한다.
        // 두 룩 사이에 '벽'이 존재한다면 두 룩은 서로를 보지 못하므로 공격하지 못한다.
        // 두 룩 사이에 '구덩이'가 존재한다면 서로 공격을 시도하므로 마주보도록 배치해서는 안된다.
        // 벽과 구덩이에는 룩을 배치하지 못한다.
        // n * m 의 격자판이 주어질 때, 최대한 많은 룩을 배치하는 경우
        // 그 룩의 수는?
        //
        // 이분 매칭 문제
        // 조건으로 추가되는 벽으로 추가적인 룩의 배치가 가능해진다.
        // 원래 조건에서는 행과 열에 하나의 룩만 가능하지만, 벽이 존재한다면
        // 그 이상의 룩을 배치하는 것이 가능해진다.
        // 따라서 행과 열을 기준으로 각 행에 룩을 배치할 수 공간을 그룹으로 나눈다.
        // 그 후, 나눠지는 행 기준 그룹과 열 기준 그룹을 이분 매칭을 통해
        // 최대한 많은 매칭을 만들면, 그 수가 최대한 많은 룩을 배치한 수와 같다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 격자판
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 게임 보드
        int[][] board = new int[n][];
        for (int i = 0; i < board.length; i++)
            board[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 먼저 행을 기준으로 그룹을 나눈다.
        int[][] criterionByRow = new int[n][m];
        int counter = 1;
        boolean used = false;
        for (int i = 0; i < board.length; i++) {
            // 현재 counter 그룹에 그룹원이 존재할 경우
            // 행이 바뀌었으므로 다음 그룹으로 넘긴다.
            // 존재하지 않을 경우에는 해당 그룹에 배치한다.
            if (used) {
                counter++;
                used = false;
            }
            for (int j = 0; j < board[i].length; j++) {
                // 룩을 배치하는 것이 가능한 경우
                // 해당 격자를 현재 그룹에 넣는다.
                if (board[i][j] == 0) {
                    criterionByRow[i][j] = counter;
                    used = true;
                } else if (board[i][j] == 2 && used) {
                    // 벽이 등장한 경우.
                    // 벽 이후의 행에 또 하나의 룩을 배치하는 것이 가능하다.
                    // counter 그룹원이 존재한다면 다음 그룹으로 넘어가고
                    // 존재하지 않는다면 counter 그룹에 그룹원을 할당한다.
                    counter++;
                    used = false;
                }
            }
        }

        // 행을 기준으로 나뉜 그룹의 수만큼 리스트를 생성한다.
        // 이분 매칭을 시행할 때, 왼쪽 그룹에 해당한다.
        List<List<Integer>> connections = new ArrayList<>();
        for (int i = 0; i < (used ? counter + 1 : counter); i++)
            connections.add(new ArrayList<>());

        // 열에 대해서도 그룹에 대해 구분한다.
        int[][] criterionByCol = new int[n][m];
        counter = 1;
        used = false;
        for (int i = 0; i < m; i++) {
            if (used) {
                counter++;
                used = false;
            }
            for (int j = 0; j < n; j++) {
                if (board[j][i] == 0) {
                    criterionByCol[j][i] = counter;
                    used = true;
                } else if (board[j][i] == 2 && used) {
                    counter++;
                    used = false;
                }
            }
        }

        // 각 격자를 돌아다니며 행을 기준으로 나뉜 그룹과
        // 열을 기준으로 나뉜 그룹을 서로 연결한다.
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (criterionByRow[i][j] != 0)
                    connections.get(criterionByRow[i][j]).add(criterionByCol[i][j]);
            }
        }

        // 열 기준 그룹에
        // 할당된 행 기준 그룹 번호를 남겨둘 공간.
        int[] matched = new int[used ? counter + 1 : counter];
        // -1로 값 초기화
        Arrays.fill(matched, -1);
        // 할당된 룩의 수를 센다.
        counter = 0;
        
        // 열 기준 그룹을 모두 순회하며
        for (int i = 1; i < connections.size(); i++) {
            // 행 기준 그룹을 할당할 수 있는지 확인하고
            // 가능하다면 룩의 수를 증가시킨다.
            if (bipartiteMatching(i, matched, connections, new boolean[connections.size()]))
                counter++;
        }
        
        // 할당된 최대 룩의 개수 출력
        System.out.println(counter);
    }
    
    // 이분 매칭
    static boolean bipartiteMatching(int idx, int[] matched, List<List<Integer>> lists, boolean[] visited) {
        // 이분 방문한 행 기준 그룹이라면 무한 재귀가 발생하므로
        // false 반환.
        if (visited[idx])
            return false;
        
        // 방문 체크
        visited[idx] = true;
        // 현재의 행 기준 그룹(idx)에
        // 열 기준 그룹(place)을 할당할 수 있는지 확인한다.
        for (int place : lists.get(idx)) {
            // 만약 해당 place가 비어있거나
            // 할당되어있는 행 그룹을 다른 열 기준 그룹으로 옮길 수 있다면
            if (matched[place] == -1 || bipartiteMatching(matched[place], matched, lists, visited)) {
                // place에 idx를 할당하고
                // true 반환
                matched[place] = idx;
                return true;
            }
        }
        // 연결된 모든 열 기준 그룹을 확인했으나 할당하지 못했을 경우에는
        // false를 반환한다.
        return false;
    }
}