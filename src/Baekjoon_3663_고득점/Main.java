/*
 Author : Ruel
 Problem : Baekjoon 3663번 고득점
 Problem address : https://www.acmicpc.net/problem/3663
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3663_고득점;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 화면에 이름을 입력하고자 한다.
        // 입력하고 싶은 이름과 같은 길이에 A가 가득차있는 문자열이 주어진다.
        // 처음 커서는 맨 앞 문자를 가르키고 있다.
        // 커서 한번의 이동으로 앞 문자 혹은 뒷 문자로 이동할 수 있다. 맨 앞 문자와 맨 뒷 문자는 연결되어있다.
        // 문자 변경을 통해, 인접한 알파벳으로 변경할 수 있다. A와 Z는 연결되어있다.
        // 최소의 조작으로 원하는 이름으로 바꾸고자할 때, 조작의 횟수는?
        //
        // 그리디 문제
        // 먼저, 이름을 알파벳 순서로 바꾸고, 이를 정순으로 변경하여 원하는 알파벳으로 만들지,
        // 역순으로 변경하여 만들지를 더 적은 조작 횟수를 갖는 조작으로 계산한다.
        // 그리고 입력할 이름에 A가 들어가있어 조작이 필요하지 않은 경우
        // 지금까지 입력한 것과 반대 방향으로 돌아가며 입력하는 것이 유리한 경우도 있다.
        // 방향을 두 번 반대로 바꾸는 경우는 최소 횟수가 되지 않으므로 생각하지 않고,
        // 한 번, 지금까지 입력한 방향을 바꿔 남은 문자를 전부 변경하는 횟수를 구해
        // 커서의 이동과 문자열 변경의 최소 조작 횟수를 더해 답은 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            // 원하는 이름
            String input = br.readLine();
            // 알파벳의 순서로 저장
            // A는 0, Z는 25
            int[] array = new int[input.length()];
            for (int i = 0; i < input.length(); i++)
                array[i] = input.charAt(i) - 'A';

            // 각 문자의 변경 횟수 합.
            int cnt = 0;
            for (int i = 0; i < array.length; i++)
                cnt += Math.min(array[i], 26 - array[i]);

            // 이동 횟수
            int minMove = Integer.MAX_VALUE;
            // 먼저 정순으로 입력하다, 역순으로 돌아가는 경우
            // idx는 i보다 크지만 가장 작은 이후에 입력해야할 문자의 번호.
            // i까지 입력후 역순으로 돌아가므로 사실상 마지막 변경 문자의 위치
            int idx = 0;
            for (int i = 0; i < array.length; i++) {
                // 마지막 변경 문자의 위치 계산
                if (idx <= i) {
                    idx = i + 1;
                    while (idx < array.length && array[idx] == 0)
                        idx++;
                }

                // idx가 문자열의 길이와 같아졌다면
                // 모든 문자의 입력이 끝난 경우.
                // 그냥 현재까지의 이동 횟수를 계산.
                if (idx == array.length)
                    minMove = Math.min(minMove, i);
                else        // 그 외의 경우. 현재까지의 이동 + 역순으로 이동
                    minMove = Math.min(minMove, i * 2 + array.length - idx);
            }

            // 역순으로 진행하다 정순으로 진행하는 경우
            // 마찬가지로 idx는 마지막 변경 문자의 위치
            idx = array.length - 1;
            for (int i = array.length - 1; i > 0; i--) {
                if (idx >= i) {
                    idx = i - 1;
                    while (idx > 0 && array[idx] == 0)
                        idx--;
                }

                if (idx == 0)
                    minMove = Math.min(minMove, array.length - i);
                else
                    minMove = Math.min(minMove, (array.length - i) * 2 + idx);
            }
            // 답 기록
            sb.append(cnt + minMove).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}