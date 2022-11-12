/*
 Author : Ruel
 Problem : Baekjoon 3980번 선발 명단
 Problem address : https://www.acmicpc.net/problem/3980
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3980_선발명단;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 11명의 선수와 11개의 포지션이 주어진다
        // 각 선수는 각 포지션의 적합도가 주어진다.
        // 적합도가 0일 경우, 해당 포지션을 맡을 수 없다.
        // 모든 선수를 배치할 때 적합도 합이 최대가 되게끔할 때 그 값은?
        //
        // 백트래킹 문제
        // 각 선수에게 적합한 포지션을 모두 할당해가며 그 적합도 합을 비교해나간다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 테스트케이스의 수
        int c = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < c; t++) {
            // 각 선수들의 포지션에 따른 적합도들
            int[][] abilities = new int[11][];
            for (int i = 0; i < 11; i++)
                abilities[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            // 백트래킹으로 적합도 합을 찾아 기록한다.
            sb.append(backTracking(0, new boolean[11], abilities)).append("\n");
        }
        // 전체 답안들 출력.
        System.out.println(sb);
    }

    static int backTracking(int player, boolean[] positions, int[][] abilities) {
        // 선수가 11명이므로, 11이 되었다면 모든 선수들을 할당한 경우.
        // 재귀 탈출 조건.
        if (player == 11)
            return 0;
        
        // player 이후의 선수들을 모두 포지션에 할당했을 때 적합도의 합
        // 가장 작은 음수값으로 초기화하는 이유는 모든 선수가 각 포지션에 할당되어야하므로
        // player가 포지션에 할당되지 않았을 때, 그 값은 고려되지않게 하기 위해서이다.
        int maxSum = Integer.MIN_VALUE;
        for (int i = 0; i < abilities[player].length; i++) {
            // player가 i 포지션에 대해 적합도가 존재하고, i포지션이 아직 할당되지 않았다면
            if (abilities[player][i] != 0 && !positions[i]) {
                // 할당 체크
                positions[i] = true;
                // player 이후 선수들을 다른 포지션에 할당했을 때, 최대값이 backTracking에 리턴되어 돌아온다.
                // 해당 적합도 합과 player의 i 포지션 적합도를 합한 값이 player를 다른 포지션에 할당했을 때의 값과 비교하여 더 큰지 확인한 후
                // maxSum에 최대값을 남긴다.
                maxSum = Math.max(maxSum, backTracking(player + 1, positions, abilities) + abilities[player][i]);
                // player를 i포지션에서 뺀다.
                positions[i] = false;
            }
        }
        // 구해진 최대값을 리턴한다.
        // 만약 player를 포지션에 할당하지 못했다면 매우 작은 음수값이 maxSum에 기록되어 리턴될 것이다.
        // 그리고 그 값은 사라질 앞의 선수들의 적합도 합이 가장 크다해도 maxSum의 음수값으로 인해 버려질 것이다.
        return maxSum;
    }
}