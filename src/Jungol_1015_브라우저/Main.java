/*
 Author : Ruel
 Problem : Jungol 1015번 브라우저
 Problem address : https://jungol.co.kr/problem/1015
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1015_브라우저;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 브라우저가 주어지며 다음 세 개의 명령을 처리한다.
        // 처음 위치는 http://www.acm.org/ 이다
        // BACK : 현재 페이지를 forward 스택에 넣고, backward에 담긴 페이지를 가져온다. backward가 비어있다면 무시한다.
        // FORWARD : 현재 페이지를 backward 스택에 넣고, forward에 담긴 페이지를 가져온다.
        // VIST : 현재 페이지를 backward에 넣고, 해당 페이지로 이동하며, forward를 비운다.
        // QUIT : 프로그램 종료
        // 매 명령마다 화면에 떠있는 페이지를 출력한다.
        //
        // 스택 문제
        // 스택을 통해 backward와 forward를 구현해주면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        //스택
        Stack<String> back = new Stack<>();
        Stack<String> forward = new Stack<>();
        StringBuilder sb = new StringBuilder();

        StringTokenizer st;
        // 현재 주소
        String current = "http://www.acm.org/";
        while (true) {
            st = new StringTokenizer(br.readLine());
            String order = st.nextToken();
            // QUIT일 경우 종료
            if (order.equals("QUIT"))
                break;

            switch (order.charAt(0)) {
                // BACK인 경우
                case 'B' -> {
                    if (back.isEmpty())
                        sb.append("Ignored").append("\n");
                    else {
                        forward.push(current);
                        current = back.pop();
                        sb.append(current).append("\n");
                    }
                }
                // FORWARD인 경우
                case 'F' -> {
                    if (forward.isEmpty())
                        sb.append("Ignored").append("\n");
                    else {
                        back.push(current);
                        current = forward.pop();
                        sb.append(current).append("\n");
                    }
                }
                // VISIT인 경우
                case 'V' -> {
                    String address = st.nextToken();
                    back.push(current);
                    current = address;
                    forward.clear();
                    sb.append(current).append("\n");
                }
            }
        }
        // 답 출력
        System.out.print(sb);
    }
}
