/*
 Author : Ruel
 Problem : Baekjoon 14391번 종이 조각
 Problem address : https://www.acmicpc.net/problem/14391
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14391_종이조각;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 숫자가 쓰여있는 직사각형 종이가 최대 크기 4 * 4로 주어진다
        // 이 때 직사각형을 겹치지 않는 조각으로 잘라, 수들로 만든 후, 그 합이 최대가 되고자한다.
        // 적절히 잘라 만든 조각들의 최대 합은?
        //
        // 백트래킹 문제
        // 각 칸으로부터, 오른쪽으로 수들을 이어붙여 자르는 경우, 아래로 이어붙여 자르는 경우를 생각한다.
        // 이전 순번의 조각들 중에서 세로로 자른 경우, 오른쪽으로 더 이상 수를 이어붙이지 못하는 경우가 생김에 유의하자.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 주어지는 직사각형 종이
        int[][] paper = new int[n][m];
        for (int i = 0; i < paper.length; i++) {
            String row = br.readLine();
            for (int j = 0; j < paper[i].length; j++)
                paper[i][j] = row.charAt(j) - '0';
        }
        System.out.println(findAnswer(0, paper, new boolean[n][m], 0));

    }

    // 백트래킹으로 답을 찾는다.
    static int findAnswer(int idx, int[][] paper, boolean[][] used, int sum) {
        // idx가 범위를 넘는다면 현재까지 구한 합을 반환한다.
        if (idx >= paper.length * paper[0].length)
            return sum;

        // idx를 통한 row, col
        int row = idx / paper[0].length;
        int col = idx % paper[0].length;

        // 현재 칸이 사용되었거나, 0이라면 다음 칸으로 순서를 넘긴다.
        if (used[row][col] || paper[row][col] == 0)
            return findAnswer(idx + 1, paper, used, sum);
        
        // 현재 칸에서 만들 수 있는 조각들의 최대 합
        int max = 0;
        // 현재 칸에서 만들 수 있는 조각의 최대 크기
        int num = 0;
        // 어디까지 이어붙일 것인지.
        int diff = 0;
        // 먼저 오른쪽으로 이어붙여나간다.
        while (col + diff < paper[row].length) {
            // 이미 해당 조각이 사용되었다면 종료.
            if (used[row][col + diff])
                break;

            // 그렇지않다면 현재까지의 값에 10을 곱한 뒤, 현재 칸의 값을 더해준다.
            num = num * 10 + paper[row][col + diff];
            // 사용 표시
            used[row][col + diff] = true;
            // 그리고 다음 순번으로 넘긴다.
            max = Math.max(max, findAnswer(idx + diff + 1, paper, used, sum + num));
            // diff 증가시켜 다음 오른쪽 수를 이어붙일 수 있는지 확인한다.
            diff++;
        }
        // 사용 표시들을 다시 복구.
        while (diff > 1)
            used[row][col + --diff] = false;
        
        // 한자리수에 대해서는 위에서 이미 계산했기 때문에 이를 고려해야 초기값 설정
        num = paper[row][col];
        // 아래쪽 조각들을 이어붙여본다.
        while (row + diff < paper.length) {
            // 이미 사용됐다면 종료
            if (used[row + diff][col])
                break;
            
            // 현재 수 * 10 + 현재 자리의 수
            // row, col에서 row + diff, col까지 세로로 이어붙인 수
            num = num * 10 + paper[row + diff][col];
            // 사용 표시.
            used[row + diff][col] = true;
            // 그리고 옆으로 한칸 순번을 넘긴다.
            max = Math.max(max, findAnswer(idx + 1, paper, used, sum + num));
            // diff를 증가시켜 아래 칸도 이어붙일 수 있는지 확인한다.
            diff++;
        }
        // 사용 표시 복구.
        while (diff > 0)
            used[row + --diff][col] = false;

        // 찾은 조각들의 최대합 반환.
        return max;
    }
}
