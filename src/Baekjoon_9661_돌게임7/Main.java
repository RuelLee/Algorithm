/*
 Author : Ruel
 Problem : Baekjoon 9661번 돌 게임 7
 Problem address : https://www.acmicpc.net/problem/9661
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9661_돌게임7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 두 명이서 돌 게임을 한다.
        // n개의 돌이 있으며 SK와 CY은 돌을 각각 4^x 개 만큼 가져갈 수 있으며,
        // 더 이상 돌을 가져갈 방법이 없는 사람이 지게 된다.
        // 두 사람이 완벽히 게임을 했을 때, 이기는 사람을 구하라. SK부터 시작.
        //
        // 게임 이론
        // 1차적으로 4의 제곱수 만큼의 돌이 있을 경우, SK가 바로 모든 돌을 가져가므로, CY가 진다는 점은 보인다.
        // 두 사람 모두 게임을 완벽히(자신이 유리하도록) 플레이하므로
        // 먼저, n이 4의 제곱수 인지 여부를 확인하고
        // 그 다음으로는 자신이 가져갈 수 있는 돌을 모두 시도해보며, 상대가 필패가 되는 경우가 있는지 확인한다.
        // 필패가 아니라면 상대방도 최선의 플레이를 함으로 상대방이 이기는 플레이를 할 것이다.
        // 위와 같은 과정을 메모이제이션을 활용하여 풀려고 했으나, n이 최대 1조까지 주어지므로 위의 방법으로는 시간 내 풀이가 불가능했다.
        // 해당 문제는 규칙을 찾아 정말 간결한 코드로만 푸는 문제였다.
        // 먼저 n이 1 ~ 10까지 경우를 생각해보면
        // 1 : SK가 1개를 모두 가져감     SK
        // 2 : SK가 1개, CY가 한 개      CY
        // 3 : SK 1, CY 1, SK 1          SK
        // 4 : SK 4                      SK
        // 5 : SK가 1개 혹은 4개 가져갈 경우, 남는 돌은 4개 or 1, CY가 무조건 이김
        // 6 : SK 4, CY 1, SK 1          SK
        // 7 : SK개 1개 혹은 4개를 가져갈 경우, 남는 돌은 6개 or 3개, 무조건 CY가 이김
        // 8 : SK 1, CY가 7의 경우에 해당하여, SK가 이김
        // 9 : SK 4, CY가 5의 경우에 해당하여 SK가 이김
        // 10 : SK가 1개 or 4개를 가져갈 경우, 남는 돌은 9, 6개, 무조건 CY가 이김.
        // SK CY SK SK CY, SK CY SK SK CY
        // 위와 같은 결과가 반복되어, mod 연산을 통해 큰 값의 게임 결과를 빠르게 알 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        long n = Long.parseLong(br.readLine());

        // 5개의 결과값이 반복됨을 알고 있으므로
        // mod연산을 통해 몇번째 결과를 적용할 지 계산한다.
        int mod = (int) (n % 5);
        // 나머지가 1, 3, 4라면 SK, 0, 2라면 CY가 이긴다.
        System.out.println(mod == 1 || mod == 3 || mod == 4 ? "SK" : "CY");
    }
}