/*
 Author : Ruel
 Problem : Jungol 3334번 회문
 Problem address : https://jungol.co.kr/problem/3334
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_3334_회문;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 앞 뒤 방향으로 볼 때 같은 순서의 문자로 구성된 문자열을 회문이라고 한다.
        // abba는 회문인다.
        // abbca와 같이 한 글자만 제거하여 회문이 되는 경우를 유사회문이라고 하자
        // t개의 문자열이 주어질 때
        // 각 문자열이 회문인지, 유사회문인지, 어떤 것도 아닌지를 판별하라
        //
        // 두 포인터
        // 앞 뒤에서부터 포인터를 가지고 시작하여 서로 문자를 비교하며 나아간다.
        // 불일치하는 문자들을 만났을 때, 앞글자 혹은 뒷글자 하나 지웠을 때, 현재 위치와 다음 위치까지 일치하는지 확인한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < t; i++) {
            // 문자열
            String input = br.readLine();
            int ans = 0;

            // 두 포인터
            int f = 0;
            int e = input.length() - 1;
            // 서로 교차하기 전까지
            while (f < e) {
                // 일치한다면 포인터 서로 하나씩 전진
                if (input.charAt(f) == input.charAt(e)) {
                    f++;
                    e--;
                } else if (ans == 0) {  // 일치하지 않지만 문자를 하나 지울 수 있는 경우
                    // 앞 문자를 지워서 일치하게 되는 경우
                    if (input.charAt(f + 1) == input.charAt(e) && (f + 2 >= e - 1 || input.charAt(f + 2) == input.charAt(e - 1))) {
                        f++;
                        ans = 1;
                    } else if (input.charAt(f) == input.charAt(e - 1) && (f + 1 >= e - 2 || input.charAt(f + 1) == input.charAt(e - 2))) {
                        // 뒤 문자를 지워서 일치하게 되는 경우
                        e--;
                        ans = 1;
                    } else {
                        // 둘 다 아닌 경우
                        ans = 2;
                        break;
                    }
                } else {
                    // 일치하지 않고 문자도 못 지우는 경우
                    ans = 2;
                    break;
                }
            }
            // 답 기록
            sb.append(ans).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}