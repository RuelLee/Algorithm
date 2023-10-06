/*
 Author : Ruel
 Problem : Baekjoon 28706번 럭키 세븐
 Problem address : https://www.acmicpc.net/problem/28706
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28706_럭키세븐;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 정수 k = 1 을 갖고 있다.
        // 이를 n개 턴 동안 두 개의 선택지 중 하나를 고른다.
        // 각 선택지는 + v or * v 형태로 주어진다.
        // 모든 선택지를 고른 후, k를 7의 배수로 만들 수 있는가?
        //
        // DP 문제
        // 최종 질문이 7의 배수인가? 를 때지는 문제이므로
        // 값의 범위를 크게 가져갈 필요없이 모듈러 7의 값으로 생각하면 된다.
        // 따라서 dp를
        // dp[턴][나머지]로 생각하고 문제를 푼다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 테스트케이스의 수
        int testCase = Integer.parseInt(br.readLine());
        
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            // n개의 턴
            int n = Integer.parseInt(br.readLine());

            // 각 턴 마다 만들 수 있는 나머지
            boolean[][] possible = new boolean[n + 1][7];
            // 처음에 갖고 있는 나머지는 1
            possible[0][1] = true;

            for (int i = 0; i < possible.length - 1; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                
                // 두 선택지
                char firstOperator = st.nextToken().charAt(0);
                int firstOperand = Integer.parseInt(st.nextToken());
                char secondOperator = st.nextToken().charAt(0);
                int secondOperand = Integer.parseInt(st.nextToken());

                // i번째 턴에 가능한 j값들을 살펴본다.
                for (int j = 0; j < possible[i].length; j++) {
                    // 불가능한 경우는 건너 뛴다.
                    if (!possible[i][j])
                        continue;

                    // j 값에 두 선택지에 따라 가능한 나머지들을 계산해서
                    // 결과를 i+1 턴에 기록한다.
                    possible[i + 1][operation(j, firstOperator, firstOperand)] = true;
                    possible[i + 1][operation(j, secondOperator, secondOperand)] = true;
                }
            }
            // 최종적으로 n개의 연산을 모두 한 후,
            // 7의 배수(나머지가 0인) 값이 존재할 수 있는지 확인하고
            // 답안 기록
            sb.append(possible[n][0] ? "LUCKY" : "UNLUCKY").append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }

    // 값과 연산자, 피연산자를 받아, 연산을 하고 % 7한 값을 반환한다.
    static int operation(int num, char operator, int operand) {
        int answer = num;
        switch (operator) {
            case '+' -> answer += operand;
            case '*' -> answer *= operand;
        }
        return answer % 7;
    }
}