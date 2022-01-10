/*
 Author : Ruel
 Problem : Baekjoon 2812번 크게 만들기
 Problem address : https://www.acmicpc.net/problem/2812
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 크게만들기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n자리 수가 주어졌을 때, k개의 숫자를 지워 최대값을 만드는 문제
        // 앞에서부터 살펴가면서 가능한 큰 숫자를 앞에 오도록 만들어주면 된다.
        // -> 기본적인 내림차순 모노톤 스택이 될 것이다. 단 숫자를 지울 기회는 k번 뿐이다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        String number = br.readLine();

        // 스택을 활용할 수 있으나, 나중에 결과를 출력해야할 때는 역순이 아니라 정순으로 출력해야하므로
        // 둘 다 가능한 데크를 사용하자.
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < number.length(); i++) {
            // 데크가 비어있지 않고, k번의 기회가 있으면서, 데크 안에 마지막 숫자가 현재 숫자보다 작다면
            // 데크 안의 마지막 숫자를 빼준다.
            while (!deque.isEmpty() && k > 0 && number.charAt(deque.peekLast()) < number.charAt(i)) {
                deque.pollLast();
                k--;
            }
            // 그 후 현재 숫자를 넣어준다.
            deque.offerLast(i);
        }
        // 마지막 숫자까지 살펴보고도 k번의 기회가 남아있다면 이는 완전한 모노톤 스택의 형태를 띌 것이다
        // 가능한 큰 수는 앞쪽에 있고, 작은 숫자는 뒤쪽에 있는 형태
        // 따라서 k개 만큼의 숫자를 뒤에서부터 지워준다.
        while (k > 0) {
            deque.pollLast();
            k--;
        }

        // 최종적으로는 데크에서 순서대로 수를 뽑아내어 출력하면 끝.
        StringBuilder sb = new StringBuilder();
        while (!deque.isEmpty())
            sb.append(number.charAt(deque.pollFirst()));

        System.out.println(sb);
    }
}