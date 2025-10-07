/*
 Author : Ruel
 Problem : Baekjoon 30688번 카르텔 님 게임
 Problem address : https://www.acmicpc.net/problem/30688
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30688_카르텔님게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // a, b, c 셋이 n개의 돌로 님 게임을 한다.
        // a와 b가 팀이고, c는 혼자서 플레이한다.
        // a와 b는 각각 1 ~ k / 2개의 돌을 가져갈 수 있고, c는 1 ~ k개의 돌을 가져갈 수 있다.
        // 마지막 돌을 가져가는 사람이 승리하며, 각각 최선의 전략으로 플레이할 때
        // a와 b가 이기는지 혹은 c가 이기는지 출력하라
        //
        // 게임 이론, 슬라이딩 윈도우 문제
        // 보통은 두 명이서 번갈아가면서 게임을 하지만
        // 이번엔 세 명의 플레이어 중 두 명이 한 팀으로 플레이하는 경우다.
        // 그러면 두 명의 플레이어가 게임할 때와 달라지는 점을 보면
        // n개의 돌이 있을 때, 두 명의 플레이어라면 a가 하나만 가져가고, c가 n-1개의 돌인 경우도 가능하지만
        // 세 명의 플레이어기 때문에 a와 b가 각각 최소 1개씩을 가져가야하기 때문에 n-2개의 돌부터 시작하게 된다.
        // 따라서
        // canWin[팀][남은 돌의 수] = 현재 팀이 이길 수 있는가로 계산한다.
        // 먼저 a/b팀이 i개의 돌이 남았을 때는 최소 2개에서 k개의 돌을 가져갈 수 있다.
        // 그렇다면 c팀 턴의 i-k ~ i-2개의 범위 내에 c팀이 지는 경우가 하나라도 있는지 살펴보면 되고
        // c팀이 i개의 돌이 남았을 때는 최소 1개부터 k개의 돌을 가져갈 수 있으므로
        // a/b팀의 i-k ~ i-1까지의 범위 내에 a/b팀이 지는 경우가 하나라도 있는지를 살펴보면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 돌, 한번에 c가 가져갈 수 있는 돌의 개수 k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // canWin[팀][남은 돌의 수] = 현재 팀이 이길 수 있는가
        boolean[][] canWin = new boolean[2][n + 1];
        // 각각의 팀이 k개 이하의 돌인 경우 각 팀이 승리 가능.
        for (int i = 1; i <= k; i++)
            canWin[0][i] = canWin[1][i] = true;
        
        int[] count = new int[2];
        // a/b팀의 i-k ~ i-1까지 돌이 남았을 때, a/b팀이 이기는 경우의 수
        count[0] = k;
        // c팀이 i-k ~ i-2까지의 돌이 남았을 때, c팀이 이기는 경우의 수
        count[1] = k - 1;

        for (int i = k + 1; i < n + 1; i++) {
            // a/b팀의 차례이고 돌이 i개 남아있다면
            // c팀의 돌의 개수가 i-k ~ i-2까지의 범위 내에 c팀이 지는 경우가 하나라도 있다면
            // a/b팀은 그 개수로 돌을 가져갈 것이다. 그러면 a/b팀이 승리 가능
            if (count[1] < k - 1)
                canWin[0][i] = true;
            // 마찬가지로 c의 차례이고 돌이 i개 남았다면
            // a/b팀의 돌의 개수가 i-k ~ i-1까지의 범위 내에 a/b팀이 지는 경우가 하나라도 있다면
            // c는 그 개수로 돌을 가져갈 것이고, 그러면 c가 승리한다.
            if (count[0] < k)
                canWin[1][i] = true;

            // i+1개의 돌일 때를 계산하기 위해
            // counts의 값들 조정해준다.
            count[0] += (canWin[0][i] ? 1 : 0) - (canWin[0][i - k] ? 1 : 0);
            count[1] += (canWin[1][i - 1] ? 1 : 0) - (canWin[1][i - k] ? 1 : 0);
        }
        
        // 처음 시작은 a/b팀의 턴에 n개의 돌로 시작하므로
        // 해당 값이 true라면 a/b팀의 승리, 아닌 경우 c의 승리
        System.out.println(canWin[0][n] ? "A and B win" : "C win");
    }
}