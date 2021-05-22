package 큰수만들기;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Collectors;

public class Solution {
    public static void main(String[] args) {
        // k개의 숫자를 지워 만들 수 있는 수 중 가장 큰 수를 구하여라
        String number = "13333";
        int k = 2;

        Deque<Character> deque = new ArrayDeque<>();

        int i = 0;
        int count = 0;
        for (; i < number.length(); i++) {
            if (count >= k)
                break;

            if (deque.isEmpty())    // dequeue 가 비어있다면 값을 넣고,
                deque.addLast(number.charAt(i));
            else if (deque.peekLast() < number.charAt(i)) {     // 그렇지 않다면 마지막 수와 현재 수를 비교하여
                while (count < k && !deque.isEmpty() && deque.peekLast() < number.charAt(i)) {      // 현재 수가 더 크다면 계속해서 dequeue 의 수를 빼주자.
                    deque.pollLast();
                    count++;
                }
                deque.addLast(number.charAt(i));
            } else      // 현재의 수가 dequeue 의 마지막 수보다 값이 작거나 같다면 그냥 넣어주자.
                deque.add(number.charAt(i));
        }
        // dequeue 에 들어간 수들과 남은 수들의 합쳐서 하나의 String 으로 만들어주자.
        String answer = deque.stream().map(n -> String.valueOf(n)).collect(Collectors.joining()) + number.substring(i);
        if (count != k)     // 혹시 k개의 숫자를 다 지우지 못했다면, (k - count) 개 만큼 뒤의 수를 지워주자.
            answer = answer.substring(0, answer.length() - (k - count));
        System.out.println(answer);
    }
}