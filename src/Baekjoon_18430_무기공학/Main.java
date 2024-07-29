/*
 Author : Ruel
 Problem : Baekjoon 18430번 무기 공학
 Problem address : https://www.acmicpc.net/problem/18430
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18430_무기공학;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int n, m;
    static int[][] components;

    public static void main(String[] args) throws IOException {
        // n * m 크기의 나무 재료가 주어진다.
        // 각 격자에는 강도가 주어진다.
        // 부메랑을 만드는데 부메랑은 다음과 같은 4가지 모양으로 만들 수 있다.
        // □        □■        □     ■□
        // ■□        □      □■     □
        // 부메랑의 중심이 되는 위치는 강도의 영향을 2배로 받는다.
        // 나무 재료의 크기와 각 칸의 강도가 주어질 때
        // 만들 수 있는 부메랑들으 ㅣ강호 합의 최대값을 출력하라
        //
        // 백트래킹 문제
        // 현재 부메랑으로 선택되어 점수과 된 격자들을 관리하며
        // 나머지 칸에 부메랑을 만들 수 있는지 계산하며 합을 구한다
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 재료
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        // 각 칸의 강도
        components = new int[n][];
        for (int i = 0; i < components.length; i++)
            components[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 백트래킹 결과 출력
        System.out.println(findAnswer(0, 0, new boolean[n][m]));
    }
    
    // idx 위치로부터 만들 수 있는 부메랑들을 계산한다.
    static int findAnswer(int idx, int sum, boolean[][] selected) {
        // idx가 범위를 벗어났다면
        // 여태까지 구한 sum 반환
        if (idx >= n * m)
            return sum;

        // idx의 row, col
        int row = idx / m;
        int col = idx % m;

        // 현재 칸이 이미 선택되어있다면
        // 옆칸으로 넘긴다.
        if (selected[row][col])
            return findAnswer(idx + 1, sum, selected);
        
        // 아직 선택되지 않았다면
        // 현재 칸을 선택하여 만들 수 있는 부메랑을 만들어본다.
        selected[row][col] = true;
        // 선택하지 않고 넘기는 경우를 초기값.
        int currentMaxSum = findAnswer(idx + 1, sum, selected);
        // ┐ 형태 부메랑을 만드는 경우
        if (col + 1 < m && row + 1 < n && !selected[row][col + 1] && !selected[row + 1][col + 1]) {
            selected[row][col + 1] = true;
            selected[row + 1][col + 1] = true;
            currentMaxSum = Math.max(currentMaxSum, findAnswer(idx + 2, sum + components[row][col] + components[row][col + 1] * 2 + components[row + 1][col + 1], selected));
            selected[row][col + 1] = false;
            selected[row + 1][col + 1] = false;
        }
        // ┘형태 부메랑을 만드는 경우
        if (row + 1 < n && col - 1 >= 0 && !selected[row + 1][col - 1] && !selected[row + 1][col]) {
            selected[row + 1][col - 1] = true;
            selected[row + 1][col] = true;
            currentMaxSum = Math.max(currentMaxSum, findAnswer(idx + 1, sum + components[row][col] + components[row + 1][col - 1] + components[row + 1][col] * 2, selected));
            selected[row + 1][col - 1] = false;
            selected[row + 1][col] = false;
        }
        // └ 형태 부메랑을 만드는 경우
        if (row + 1 < n && col + 1 < m && !selected[row + 1][col] && !selected[row + 1][col + 1]) {
            selected[row + 1][col] = true;
            selected[row + 1][col + 1] = true;
            currentMaxSum = Math.max(currentMaxSum, findAnswer(idx + 1, sum + components[row][col] + components[row + 1][col] * 2 + components[row + 1][col + 1], selected));
            selected[row + 1][col] = false;
            selected[row + 1][col + 1] = false;
        }
        // ┌ 형태 부메랑을 만드는 경우
        if (col + 1 < m && row + 1 < n && !selected[row][col + 1] && !selected[row + 1][col]) {
            selected[row][col + 1] = true;
            selected[row + 1][col] = true;
            currentMaxSum = Math.max(currentMaxSum, findAnswer(idx + 2, sum + components[row][col] * 2 + components[row][col + 1] + components[row + 1][col], selected));
            selected[row][col + 1] = false;
            selected[row + 1][col] = false;
        }
        selected[row][col] = false;
        // 찾은 최대값 반환
        return currentMaxSum;
    }
}