/*
 Author : Ruel
 Problem : Baekjoon 1574번 룩 어택
 Problem address : https://www.acmicpc.net/problem/1574
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1574_룩어택;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // r, c 크기의 체스판이 주어진다.
        // n개의 칸에는 말을 놓을 수 없다고 한다.
        // 이 때 최대 몇 개의 룩을 서로 공격하지 않게 놓을 수 있는지 구하라
        //
        // 이분 매칭 문제
        // row, col을 따로 생각하는 이분매칭문제.
        // 룩이므로, 각 row, col에 해당하는 룩은 단 하나만 놓을 수 있다.
        // 따라서 말을 놓을 수 없는 위치인지, 이미 해당 row나 col에 룩이 있는지 확인하며
        // 이분매칭을 돌리면 된다!
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 조건 입력
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        
        // 말을 놓을 수 없는 위치
        boolean[][] blanks = new boolean[r][c];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int row = Integer.parseInt(st.nextToken());
            int col = Integer.parseInt(st.nextToken());

            blanks[row - 1][col - 1] = true;
        }

        // 해당하는 col에 룩을 둔 row 값을 저장한다.
        int[] matchedCol = new int[c];
        // -1로 초기화
        Arrays.fill(matchedCol, -1);
        // 놓은 룩의 개수를 센다.
        int count = 0;
        // 각 row에는 하나의 룩만 배치가 가능하다.
        // 따라서 row를 하나씩 살펴보며 룩을 배치하자.
        for (int i = 0; i < r; i++) {
            // i번째 row에 룩을 배치하는 것을 성공했다면 count 증가
            if (bipartiteMatching(i, new boolean[r], matchedCol, blanks))
                count++;
        }
        // 전체 룩의 개수(= count) 출력
        System.out.println(count);
    }

    // 이분 매칭
    // 해당하는 row와, 이미 방문했던 row, 그리고 해당 col에 룩이 놓인 row, 룩을 놓을 수 없는 공간들을
    // 매개 변수로 넘겨준다.
    static boolean bipartiteMatching(int r, boolean[] visitedRow, int[] matchedCol, boolean[][] blanks) {
        // 이미 방문한 row를 재방문한 것이라면, 다른 방법이 없고, 무한 재귀가 생길 수 있다.
        // false 반환.
        if (visitedRow[r])
            return false;
        // 아니라면 방문 체크
        visitedRow[r] = true;

        // 해당 r에서 룩을 놓을 수 있는 col이 있는지 살펴본다.
        for (int i = 0; i < blanks[r].length; i++) {
            // 가장 먼저 룩을 놓을 수 없는 공간이어서는 안된다.
            // i번째 col이 비어있거나, 현재 i번째 col에 놓은 룩을 다른 위치로 변경하는 것이 가능하다면
            // i번째 col에는 r을 매칭시켜준다.
            if (!blanks[r][i] && (matchedCol[i] == -1 || bipartiteMatching(matchedCol[i], visitedRow, matchedCol, blanks))) {
                matchedCol[i] = r;
                return true;
            }
        }
        // true로 탈출하지않고 반복문을 모두 돌았다면 r에 룩을 배치하는 것이 불가능한 경우.
        // false를 반환한다.
        return false;
    }
}