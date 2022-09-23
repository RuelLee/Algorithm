/*
 Author : Ruel
 Problem : Baekjoon 11058번 크리보드
 Problem address : https://www.acmicpc.net/problem/11058
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11058_크리보드;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 크리보드는 4개의 버튼이 있으며,
        // 1. 화면에 A 출력
        // 2. 화면 전체 선택
        // 3. 선택한 내용을 버퍼에 복사
        // 4. 화면에 출력된 문자열 뒤에 버퍼 내용 붙여넣기
        // 입력이 가능한 수 n이 주어질 때, 출력하는 A의 개수를 최대로 할 때 이 A의 개수는?
        //
        // DP문제
        // 코드 자체는 간단했지만 생각을 조금 요하는 문제였다.
        // 먼제 DP라는 점은 쉽게 이해했지만 이를 어떻게 구현해야하는가가 조금 고민됐다.
        // 만약 7이 주어졌을 때, 7을 채우는 방법은 1을 7번 누르는 방법(7), 1을 네번, 그리고 2,3,4를 누를 방법(8)
        // 1을 세번, 2, 3을 한번, 4를 두번 누르는 방법(9)가 있기 때문이다.
        // 따라서 각 입력 횟수에 따라 화면과 버퍼를 따로 저장해야하는가를 생각했다.
        // 하지만 n의 크기가 100이하로 크지 않기 때문에
        // i번 입력이 주어졌다면, dp[i - 3]의 내용을 한 번 복붙이 가능하고, dp[i - 4]의 내용은 두번 복붙이 가능하다.
        // 따라서 이런 방법으로 i보다 3이상 작은 수에 대해 일일이 계산을 하였다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        // 오버플로우 방지 long 타입.
        long[] dp = new long[n + 1];
        // 1번 버튼만 눌렀을 때.
        for (int i = 1; i < dp.length; i++)
            dp[i] = i;

        // 2, 3, 4번 버튼을 사용하기 위해서는 최소 3번 이상의 입력이 필요하다.
        for (int i = 4; i < dp.length; i++) {
            // 자신보다 3이상 작은 입력 횟수를 살펴보며
            // 자신보다 3 작을 때는 2배, 4 작을 때는 3배, 5 작을 때는 4배... 에 해당하는
            // 값을 복붙으로 얻을 수 있다.
            for (int j = 3; j < i; j++)
                dp[i] = Math.max(dp[i], dp[i - j] * (j - 1));
        }

        // 최종적으로 입력 n번이 주어질 때의 최대 개수를 출력한다.
        System.out.println(dp[n]);
    }
}