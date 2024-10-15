/*
 Author : Ruel
 Problem : Baekjoon 5430번 AC
 Problem address : https://www.acmicpc.net/problem/5430
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5430_AC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws IOException {
        // 새로운 언어 AC를 만들었다.
        // 두 가지 함수 R과 D가 있으며
        // R은 순서를 뒤집고, D는 가장 앞에 있는 수를 버린다.
        // 수행할 함수와 배열의 초기값이 주어질 때, 최종 결과를 구하라
        //
        // 문자열 파싱, 데크 문제
        // 함수가 문자열 형태로 주어지므로 이를 파싱하며 처리하며
        // 데크를 이용하여 명령어들을 처리해준다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 테스트케이스의 수
        int testCase = Integer.parseInt(br.readLine());
        for (int t = 0; t < testCase; t++) {
            // 명령어
            String p = br.readLine();
            
            // 수의 개수
            int n = Integer.parseInt(br.readLine());
            // 수
            String input = br.readLine();
            Deque<String> deque = new LinkedList<>();
            // n이 0이 아닌 경우에만 input을 파싱하여 deque에 담는다.
            if (n != 0) {
                String[] nums = input.substring(1, input.length() - 1).split(",");
                for (String num : nums)
                    deque.offerLast(num);
            }
            
            // 정방향
            boolean forward = true;
            // 명령어 처리 가능 여부
            boolean possible = true;
            for (int i = 0; i < p.length(); i++) {
                // 방향을 뒤집는 명령어
                if (p.charAt(i) == 'R')
                    forward = !forward;
                // 만약 데크가 비어있는데 D 명령어가 들어왔다면
                // 명령어 처리가 불가능한 경우
                // possible에 false 표시하 반복문 종료
                else if (deque.isEmpty()) {
                    possible = false;
                    break;
                } else if (forward)     // 정방향일 경우 앞에서 제거
                    deque.pollFirst();
                else        // 역방향일 경우 뒤에서 제거
                    deque.pollLast();
            }

            if (!possible) {        // 명령어 처리가 불가능한 경우
                System.out.println("error");
                continue;
            }

            // 처리가 된다면 남은 수를 통해 답안 작성
            StringBuilder sb = new StringBuilder();
            // 안 비어있는 경우
            if (!deque.isEmpty()) {
                // 정방향일 경우
                if (forward) {
                    while (!deque.isEmpty())
                        sb.append(deque.pollFirst()).append(',');
                    sb.deleteCharAt(sb.length() - 1);
                } else {        // 역방향일 경우
                    while (!deque.isEmpty())
                        sb.append(deque.pollLast()).append(',');
                    sb.deleteCharAt(sb.length() - 1);
                }
            }
            // 앞 뒤에 [ ] 추가
            sb.insert(0, '[').append(']');
            // 답 출력
            System.out.println(sb);
        }
    }
}