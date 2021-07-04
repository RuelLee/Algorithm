/*
 Author : Ruel
 Problem : Baekjoon 9935번 문자열 폭발
 Problem address : https://www.acmicpc.net/problem/9935
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 문자열폭발;

import java.util.Scanner;
import java.util.Stack;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // 들어오는 문자열의 길이가 매우 길다. substring 으로 String 계속 할당할 경우 메모리가 매우 커진다.
        // Stack 을 이용해서 풀어보자.
        Scanner sc = new Scanner(System.in);

        char[] input = sc.nextLine().toCharArray();
        char[] bomb = sc.nextLine().toCharArray();

        Stack<Character> string = new Stack<>();
        Stack<Integer> stack = new Stack<>();
        int idx = 0;            // bomb 의 위치를 나타낼 것이고, 연속된 경우에만 값이 올라갈 것이다.
        for (int i = 0; i < input.length; i++) {

            if (input[i] == bomb[idx]) {        // 현재 input 의 값과, bomb[idx] 값이 일치하는 경우
                string.push(input[i]);      // string Stack 에는 문자를
                stack.push(idx++);      // stack Stack 에는 idx 값을 넣어주고 증가시킨다.
                if (idx == bomb.length) {   // bomb 의 해당하는 문자열이 모두 담겼다면(idx 가 bomb.length 와 같아졌다면)
                    for (int j = 0; j < bomb.length; j++) {     // bomb 에 해당하는 갯수를 string 과 stack 모두 빼준다.
                        string.pop();
                        stack.pop();
                    }
                    idx = stack.isEmpty() ? 0 : stack.peek() + 1;   // 그 후 idx 값은, stack 이 비어있다면 0, 그렇지 않고, stack 안에 값이 들어있다면 그 값의 +1로 해서 연속적으로 체크해준다.
                }
            } else if (input[i] == bomb[0]) {       // 만약 이번 문자가 bomb 의 첫글자와 일치한다면
                string.push(input[i]);
                stack.push(0);          // stack 에 0을 넣어주고,
                idx = 1;        // idx 는 0에서 연속하는 다음 값인 1을 넣어준다.
            } else {        // 그 밖에 bomb[idx] 값과도, bomb[0] 값과도 일치하지 않는다면
                string.push(input[i]);      // -1 값을 넣어준다. ( 38번째 줄과 맞물려, 이 뒤에 bomb 으로 삭제되고, peek()로 해당 글자를 살펴봤을 때, -1의 다음인 0 값을 줄 수 있을 것이다)
                stack.push(-1);
                idx = 0;    // 이번 글자로 bomb 의 연속성이 끊겼기 때문에, 다음 차례에는 다시 0부터 시작해야한다.
            }
        }
        // string 스택이 비어있다면 FRULA 를, 그렇지 않다면, String 으로 만들어 출력한다.
        System.out.println(string.isEmpty() ? "FRULA" : string.stream().map(String::valueOf).collect(Collectors.joining()));
    }
}