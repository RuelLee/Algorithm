/*
 Author : Ruel
 Problem : Baekjoon 3967번 매직 스타
 Problem address : https://www.acmicpc.net/problem/3967
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3967_매직스타;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    // 수들의 위치
    static int[] points = {4, 10, 12, 14, 16, 20, 24, 28, 30, 32, 34, 40};
    // 각 수들이 포함된 선분.
    static int[][] lines = new int[][]{{1, 3}, {4, 6}, {3, 4}, {1, 4}, {4, 5}, {3, 6}, {1, 5}, {2, 3}, {2, 6}, {2, 5}, {1, 2}, {5, 6}};
    // 주어져 고정이 된 수
    static boolean[] fixed = new boolean[45];
    // 이미 사용된 수
    static boolean[] usedNums = new boolean[13];
    // 각 선분들에 포함된 수들의 합
    static int[] sums = new int[7];
    // 주어지는 매직 스타
    static char[][] star;

    public static void main(String[] args) throws IOException {
        // 매직 스타는 1 ~ 12까지의 수가 헥사 그램에 채워져있는 모양으로 이루어져있다.
        // 각 줄에는 4개의 수가 포함되며 그 합이 26이 된다.
        // 매직 스타의 수가 일부가 채워져 주어질 때
        // 나머지 수를 채워 출력하라
        // 그러한 경우의 수가 여러가지라면, 사전순으로 가장 이른 방법을 출력한다.
        //
        // 브루트 포스 문제
        // 수를 모두 넣어 계산해볼 수 있다.
        // 하지만 조건 상 한 줄에 포함되는 수들의 합이 26을 넘어서는 안되는 점을 이용하여 가지치기를 하자
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 주어지는 매직 스타 형태
        star = new char[5][];
        for (int i = 0; i < star.length; i++)
            star[i] = br.readLine().toCharArray();

        // 이미 주어진 수를 파악한다.
        for (int i = 0; i < points.length; i++) {
            int row = points[i] / 9;
            int col = points[i] % 9;
            // 수가 주어진 경우
            if (star[row][col] >= 'A' && star[row][col] <= 'L') {
                // 해당 수 고정
                fixed[points[i]] = true;
                // 사용된 수 체크
                usedNums[star[row][col] - 'A' + 1] = true;
                // 해당 수가 포함된 선분의 총합에 해당 값 추가.
                for (int j = 0; j < 2; j++)
                    sums[lines[i][j]] += star[row][col] - 'A' + 1;
            }
        }
        // 0번부터 브루트포스
        bruteForce(0);
        
        // 답안 출력
        StringBuilder sb = new StringBuilder();
        for (char[] s : star) {
            for (char c : s) sb.append(c);
            sb.append("\n");
        }
        System.out.print(sb);
    }
    
    // 브루트포스
    static boolean bruteForce(int idx) {
        // 12번까지 도달했다면 가능한 경우를 찾았다.
        // true 반환
        if (idx == 12)
            return true;

        // 고정된 수라면
        // 새로운 수를 할당할 것 없이 다음 수의 차례로 넘긴다.
        if (fixed[points[idx]])
            return bruteForce(idx + 1);
        
        // idx 수의 위치
        int row = points[idx] / 9;
        int col = points[idx] % 9;
        
        // 1 ~ 12까지의 수를 살펴보며
        for (int i = 1; i < usedNums.length; i++) {
            // 이미 사용된 수라면 건너뛰고
            if (usedNums[i])
                continue;
            
            // i번째 수를 star[row][col]에 할당하더라도
            // 포함되는 선분에 총합이 26이 넘지 않음을 확인.
            if (sums[lines[idx][0]] + i <= 26 && sums[lines[idx][1]] + i <= 26) {
                // 그렇다면 선분 총합에 i 추가
                sums[lines[idx][0]] += i;
                sums[lines[idx][1]] += i;
                // 사용된 수 체크
                usedNums[i] = true;
                // star에 해당 알파벳 할당.
                star[row][col] = (char) ('A' + i - 1);
                // 그후 idx +1번째로 순서를 넘긴다.
                // 만약 true 반환이 온다면 해당하는 경우로 매직 스타를 채울 수 있다.
                // 그런 경우 true 반환
                if (bruteForce(idx + 1))
                    return true;

                // 매직 스타를 채우는게 실패한 경우
                // 선분 총합에서 i를 빼준다.
                sums[lines[idx][0]] -= i;
                sums[lines[idx][1]] -= i;
                // 사용된 수 체크도 해제
                usedNums[i] = false;
            }
        }
        // 1 ~ 12까지의 수를 살펴봤으나 가능한 경우가 없다면
        // 이전에 할당된 수들 중 잘못된 경우가 있는 경우이므로
        // false를 반환해 다른 수를 할당하여 다시 시도해본다.
        return false;
    }
}