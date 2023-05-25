/*
 Author : Ruel
 Problem : Baekjoon 17251번 힘 겨루기
 Problem address : https://www.acmicpc.net/problem/17251
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17251_힘겨루기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 힘 겨루기 대회에 n명의 선수가 출전한다
        // 이 선수들의 명단이 주어지고, 기준이 되는 두 선수를 경계로 삼아
        // 왼쪽을 홍팀, 오른쪽을 청팀으로 나눈다.
        // 게임은 단판으로 진행되며, 각 팀에서 가장 힘이 강한 선수가 나와 겨룬다.
        // 힘이 센 선수가 이기며, 같을 경우 승패가 결정나지 않는다.
        // 도박사는 승률이 높은 팀에 걸고자한다.
        // 어느 팀에 거는 것이 유리한가
        //
        // DP.. 문제?
        // 두 선수를 기준으로 왼쪽과 오른쪽 팀으로 나뉘므로
        // 왼쪽과 오른쪽에서 각각 가장 강한 선수들을 기록한 뒤
        // 두 팀에서 선출되는 선수들을 비교하여 승패를 정한다.
        // 그 후, 더 승리가 많은 팀을 계산한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 선수의 수
        int n = Integer.parseInt(br.readLine());
        
        // 선수들의 힘
        int[] players = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 각 양 끝에서부터 현재까지의 플레이어 중 가장 강한 선수의 전투력
        // max[i][0] = 0 ~ i까지 중 가장 강한 선수의 힘
        // max[i][1] = i ~ n - 1까지 중 강한 선수의 힘
        int[][] max = new int[n][2];
        // 초기값 설정
        max[0][0] = players[0];
        max[n - 1][1] = players[n - 1];
        for (int i = 1; i < n; i++) {
            // i번째 선수와 (0 ~ i - 1)까지의 기록과 비교하여 더 강한 선수의 힘을
            // max[i][0]에 기록
            max[i][0] = Math.max(max[i - 1][0], players[i]);
            
            // for문을 한번만 쓰기 위해 반대편에서도 동시에 계산
            // 왼쪽은 0 ~ i로 계산할 때
            // 오른쪽은 (n - 1) ~ (n - 1 - i)까지 계산.
            // 마찬가지로 오른쪽 팀에서 가장 강한 선수의 힘 기록
            max[n - 1 - i][1] = Math.max(max[n - i][1], players[n - 1 - i]);
        }
        
        // i번째 선수까지를 홍팀, i + 1부터 청팀으로 정할 때의 승패
        int leftWin = 0;
        int rightWin = 0;
        for (int i = 0; i < max.length - 1; i++) {
            // 홍팀이 승리하는 경우
            if (max[i][0] > max[i + 1][1])
                leftWin++;
            // 청팀이 승리하는 경우
            else if (max[i][0] < max[i + 1][1])
                rightWin++;
        }

        // 계산된 값에 따라 결과 출력.
        System.out.println(leftWin > rightWin ? 'R' : (leftWin == rightWin ? 'X' : 'B'));
    }
}