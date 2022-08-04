/*
 Author : Ruel
 Problem : Baekjoon 2602번 돌다리 건너기
 Problem address : https://www.acmicpc.net/problem/2602
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2602_돌다리건너기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int[][][] dp;
    static String scroll;
    static String[] bridges;

    public static void main(String[] args) throws IOException {
        // 마법의 두루마리와 두 다리가 주어진다
        // 마법의 두루마리에는 R I N G S 로 이루어진 문자열이 주어진다.
        // 두 다리 역시 각 위치마다 R I G N S 값이 주어진다.
        // 마법의 두루마리에 순서에 맞도록, 두 다리를 번갈아면서 딛고 나아가 도착지점에 도착하고 싶다.
        // 해당하는 가짓수는 몇 개인지 구하여라.
        // 어느 다리에서 먼저 시작하든 상관 X. 단 첫번째 돌을 디뎠다면, 두번째 이후 돌을 다음 번에 디뎌야한다.
        //
        // DP문제
        // 3차원 DP를 세우되, dp[다리종류][다리의순서][두루마리순서]로 DP를 세우자.
        // 그리고 bottom-up 방식으로 DP를 채워 나가도록 하자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력 처리.
        scroll = br.readLine();
        bridges = new String[2];
        bridges[0] = br.readLine();
        bridges[1] = br.readLine();

        // 다리의 종류 2가지
        // 다리의 길이는 주어진 문자열의 길이
        // 두루마리의 길이.
        dp = new int[2][bridges[0].length()][scroll.length()];
        // 그 후, 0 값이 초기값인지, 실제로 계산된 가짓수가 0인지 구분이 되지 않는다.
        // 초기값을 -1로 세팅하자.
        for (int[][] dynamic : dp) {
            for (int[] d : dynamic)
                Arrays.fill(d, -1);
        }

        // 0번 다리에서 0번 이후로 시작했을 때 다리를 건너갈 수 있는 가짓수를 찾는다
        findWay(0, 0, 0);
        // 1번 다리에서 0번 이후로 시작했을 때, 다리를 건너갈 수 있는 가짓수를 찾는다.
        findWay(0, 1, 0);

        // 최종적으로 dp[0][0][0] + dp[1][0][0]의 값이 건너갈 수 있는 방법의 수
        System.out.println(dp[0][0][0] + dp[1][0][0]);
    }

    // bottom-up 방식으로 dp를 채워 가짓수를 찾는다.
    static int findWay(int scrollOrder, int bridgeType, int bridgeOrder) {
        // 두루마리의 마지막 문자까지 진행이 됐다면 방법 1가지를 찾은 경우.
        // 1을 반환한다.
        if (scrollOrder == scroll.length())
            return 1;
        // 만약 두루마리의 마지막 문자까지 오지 못했지만, 다리가 끝난 경우
        // 도달할 수 있는 방법이 없는 경우. 0을 리턴한다.
        else if (bridgeOrder >= bridges[bridgeType].length())
            return 0;

        // 이미 계산된 결과값이 있는 경우.
        // 값만 바로 리턴해준다.
        if (dp[bridgeType][bridgeOrder][scrollOrder] != -1)
            return dp[bridgeType][bridgeOrder][scrollOrder];

        // 계산을 했다는 의미로 0값을 넣고 시작한다.
        dp[bridgeType][bridgeOrder][scrollOrder] = 0;
        for (int i = bridgeOrder; i < bridges[bridgeType].length(); i++) {
            // bridgeType의 bridgeOrder 이후로의 문자들 중 두루마리의 scrollOrder번째 글자와 같은 글자를 찾는다.
            // 발견을 했다면, 재귀적으로 findWay 메소드를 불러주되,
            // 두루마리의 다음 순서, 다른 타입의 다리, i + 1(i번 이후의 다리를 디뎌야하므로)로 메소드를 부른다.
            // 이 때 찾은 방법의 수를 dp[bridgeType][bridgeOrder][scrollOrder]에 더해준다.
            if (scroll.charAt(scrollOrder) == bridges[bridgeType].charAt(i))
                dp[bridgeType][bridgeOrder][scrollOrder]
                        += findWay(scrollOrder + 1, (bridgeType + 1) % 2, i + 1);
        }
        // 최종적으로 구해진 bridgeType 다리에서 bridgeOrder 이후로 찾은 두루마리의 scrollOrder번째 글자를 통해
        // 도착지에 도달할 수 있는 방법의 수가 계산되었고 해당 값을 반환한다.
        return dp[bridgeType][bridgeOrder][scrollOrder];
    }
}