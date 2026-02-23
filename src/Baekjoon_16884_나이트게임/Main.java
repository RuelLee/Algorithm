/*
 Author : Ruel
 Problem : Baekjoon 16884번 나이트 게임
 Problem address : https://www.acmicpc.net/problem/16884
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16884_나이트게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * n 크기의 체스판이 주어진다.
        // koosaga와 cubelover는 번갈아가면서 나이트를 둔다.
        // 나이트는 이미 나이트가 올려진 칸에 놓을 수 없고, 다른 나이트에게 잡히는 위치에도 둘 수 없다.
        // 더 이상 나이트를 놓을 수 없는 사람이 진다.
        // 승자를 구하라
        //
        // 애드 혹 문제
        // n이 홀수라면, 선공이 정중앙에 나이트를 배치한 뒤, 후공이 두는 대칭 위치에 선공이 두기만 하면 이길 수 있다.
        // n이 짝수라면 정중앙이 없으므로, 선공이 놓는 위치의 대칭되는 곳에 후공이 따라놓게 되면 후공이 이긴다.
        // 단순히 홀짝으로 결과가 끝난다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            // n * n 크기의 체스판
            int n = Integer.parseInt(br.readLine());
            // 짝수일 경우 후공이
            // 홀수일 경우 선공이 승리한다.
            sb.append(n % 2 == 0 ? "cubelover" : "koosaga").append("\n");
        }
        System.out.print(sb);
    }
}