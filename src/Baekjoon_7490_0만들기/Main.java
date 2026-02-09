/*
 Author : Ruel
 Problem : Baekjoon 7490번 0 만들기
 Problem address : https://www.acmicpc.net/problem/7490
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_7490_0만들기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static StringBuilder sb;
    static char[] temp;

    public static void main(String[] args) throws IOException {
        // 1부터 n까지의 수를
        // '+', '-', ' ' 으로 식을 만들어 합이 0인 수식을 만들어, 결과를 아스키 코드 값의 오름차순으로 출력한다.
        // +는 앞 뒤 수의 더하기, -는 앞 뒤 수의 빼기, ' '은 앞 뒤 수를 이어 붙이는 것을 뜻한다.
        //
        // 브루트포스, 백트래킹 문제
        // 직접 일일이 모든 경우의 수를 해보며 합이 0이 되는 경우를 찾으면 된다.
        // + 혹은 -가 나오는 경우는, 해당 수 이전의 수들과의 분할되어 하나의 결과가 되고
        // ' '이 나오는 경우는 이전 수 * 10 + 현재 수로 하나의 수가 아직 이어짐을 뜻하는 걸 유의하자

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());
        // 연산기호를 저장해둘 공간
        temp = new char[9];
        sb = new StringBuilder();
        for (int t = 0; t < testCase - 1; t++) {
            int n = Integer.parseInt(br.readLine());
            // 첫 수는 1, 크기는 n, 현재 합은 0, 마지막 수는 1
            findAnswer(1, n, 0, 1);
            sb.append("\n");
        }
        findAnswer(1, Integer.parseInt(br.readLine()), 0, 1);
        // 답 출력
        System.out.print(sb);
    }

    // 현재 수 num, 수의 범위 n
    // 현재까지의 합 sum, 마지막 수 lastNum
    static void findAnswer(int num, int n, int sum, int lastNum) {
        // 마지막 수까지 도달한 경우
        if (num == n) {
            // lastNum을 sum에 더한 값이 0인지 확인
            if (sum + lastNum == 0) {
                // 그러하다면 temp에 기록된 연산자를 반영하여 수식 작성
                for (int i = 1; i < n; i++)
                    sb.append(i).append(temp[i]);
                sb.append(n).append("\n");
            }
            // 그 외의 경우 그냥 종료
            return;
        }

        // 아스키 코드 상 공백 -> + -> -
        // 공백인 경우
        temp[num] = ' ';
        // 다음 수로 넘어가되, sum은 그대로, lastNum은 현재 값 * 10 + 다음 수. 다만 음수인 경우도 고려
        findAnswer(num + 1, n, sum, lastNum * 10 + (lastNum < 0 ? -1 : 1) * (num + 1));
        // + 인 경우
        // 이전 lastNum을 sum에 누적시키고
        // lastNum에 num + 1을 대입
        temp[num] = '+';
        findAnswer(num + 1, n, sum + lastNum, num + 1);
        // - 인 경우
        // +와 마찬가지이지만 lastNum에 해당 값의 음수를 대입
        temp[num] = '-';
        findAnswer(num + 1, n, sum + lastNum, -(num + 1));
    }
}