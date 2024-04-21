/*
 Author : Ruel
 Problem : Baekjoon 1490번 자리수로 나누기
 Problem address : https://www.acmicpc.net/problem/1490
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1490_자리수로나누기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 어떤 수 n이 주어질 때
        // 접두사를 n을 갖으며, n의 0이 아닌 모든 자리수로 나누어 떨어지는 수 중 가장 작은 수를 출력하라
        //
        // 브루트포스 문제
        // n을 접두사로 갖기 때문에 뒤에 붙는 수에 대해 모든 경우의 수를 계산하면 된다.
        // n부터 시작하여
        // n0 n1 ~ n9
        // n00 n01 ~ n99 ....
        // 같은 경우를 모두 계산해준다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        // n에 곱할 10의 제곱 수
        int digit = 1;
        // 찾을 때까지 반복
        boolean found = false;
        while (!found) {
            // 뒤에 digit 이하의 수를 더해준다.
            for (int i = 0; i < digit; i++) {
                // 생성된 수
                long current = (long) n * digit + i;
                // 해당 수가, n에 해당하는 모든 자리수에 대해 나누어떨어지는지 확인한다.
                if (dividable(current, n)) {
                    // 그러하다면 값 출력 후 종료.
                    System.out.println(current);
                    found = true;
                    break;
                }
            }
            // 값을 찾지 못했다면
            // digit에 10을 곱해 다음 자릿수에 해당하는 모든 수에 대해 다시 계산한다.
            digit *= 10;
        }
    }

    // current가 original의 모든 자리수에 대해 나누어 떨어지는지 확인한다.
    static boolean dividable(long current, int original) {
        while (original > 0) {
            // 자리수가 0이 아니며, 나누어 떨어지지 않는다면
            // false 리턴
            if ((original % 10) != 0 && current % (original % 10) != 0)
                return false;
            original /= 10;
        }
        // 모든 자리수에 대해 나누어떨어진다면 true 리턴.
        return true;
    }
}