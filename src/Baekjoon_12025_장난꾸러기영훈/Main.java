/*
 Author : Ruel
 Problem : Baekjoon 12025번 장난꾸러기 영훈
 Problem address : https://www.acmicpc.net/problem/12025
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12025_장난꾸러기영훈;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 최대 60글자의 0 ~ 9까지의 수로 이루어진 비밀번호가 주어진다.
        // 해당 비밀번호에서 1과 6을 모두 1로, 2와 7을 모두 2로 바꾼 비밀번호와
        // 1과 6을 모두 6으로, 2와 7을 모두 7로 바꾼 비밀번호 사이에
        // 사전순 n번째 비밀번호가 무엇인지 출력하라.
        //
        // 경우의 수 문제
        // 먼저 바꿀 수 있는 수들의 개수를 모두 센다.
        // 만약 바꿀 수 있는 수가 3개라면
        // 첫번째 바꿀 수 있는 수에서 작은 수를 택하면 순서는 증가하지 않는다.
        // 만약 큰 수를 택한다면, 뒤의 모든 바꿀 수 있는 수들에 대한
        // 경우의 수 + 1에 해당하는 만큼 순서가 증가한다.
        // 이를 유의하며 n이 1이 될 때까지 수를 줄여나간다.
        // 만약 1이 되지 않았다면 불가능한 경우이므로 -1을 출력한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String password = br.readLine();
        // 최대 2^63 - 1까지의 수가 주어지므로 long 타입으로 받는다.
        long n = Long.parseLong(br.readLine());

        // 바꿀 수 있는 수의 개수를 센다.
        int count = 0;
        for (int i = 0; i < password.length(); i++)
            if (doubtChar(password.charAt(i)))
                count++;

        // 답안 작성용 StringBuilder
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            // 바꿀 수 없는 수라면 그대로 기록하고 넘긴다.
            if (!doubtChar(password.charAt(i))) {
                sb.append(password.charAt(i));
                continue;
            }

            // 만약 n이 현재 i번째 수를 큰 수를 할당하더라도
            // n이 이보다 커 수가 남는다면
            // i번째 수에 큰 수를 할당하고, 해당하는 경우의 수만큼을 n에서 빼준다.
            if (n >= Math.pow(2, count - 1) + 1) {
                // 해당하는 경우의 수만큼을 제해준다.
                n -= (long) Math.pow(2, count - 1);
                // 만약 1이거나 6이었다면 6으로
                if (password.charAt(i) == '1' || password.charAt(i) == '6')
                    sb.append(6);
                // 2이거나 7이었다면 7로
                else
                    sb.append(7);
            } else { // n이 작은 경우라면 작은 수를 선택한다.
                if (password.charAt(i) == '1' || password.charAt(i) == '6')
                    sb.append(1);
                else
                    sb.append(2);
            }
            // 바꿀 수 있는 수 중 하나를 처리했으므로
            // count를 하나 감소시킨다.
            count--;
        }
        // 최종적으로 1보다 큰 수가 남았다면 불가능한 경우이므로 -1 출력
        // 1이 남았다면 현재 값 출력
        System.out.println(n > 1 ? -1 : sb);
    }

    static boolean doubtChar(char c) {
        return c == '1' || c == '2' || c == '6' || c == '7';
    }
}