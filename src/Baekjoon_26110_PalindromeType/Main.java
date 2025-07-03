/*
 Author : Ruel
 Problem : Baekjoon 26110번 Palindrome Type
 Problem address : https://www.acmicpc.net/problem/26110
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_26110_PalindromeType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static char[] input;

    public static void main(String[] args) throws IOException {
        // 앞으로 읽으나, 뒤로 읽으나 같은 문자열을 팰린드롬이라고 한다.
        // 문자열이 주어졌을 때, k개의 문자를 제거하여 팰린들롬이 될 경우,
        // 타입-k 팰린드롬이라고 한다.
        // 문자열이 주어질 때, k가 4이상인 경우는 -1, 그 외의 경우는 k값을 출력하라
        //
        // 재귀 문제
        // k가 최대 3까지이므로 문자열의 길이가 10^5이더라도 큰 부담없이 할 수 있다.
        // 앞 포인터와 뒤 포인터를 두고, 두 문자열이 같다면
        // 안으로 한 칸씩 전진하고
        // 서로 다른 경우, 앞 문자를 지우는 경우와 뒷 문자를 지우는 경우 두 가지 경우가 존재한다.
        // 모두 최종적으로 비교하여 k가 최소인 값을 찾는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 입력된 문자열
        input = br.readLine().toCharArray();
        // 찾은 k
        int answer = findAnswer(0, input.length - 1, 0);
        // k가 4이상인 경우는 -1, 그 외의 경우 k값 출력
        System.out.println(answer >= 4 ? -1 : answer);
    }

    // 재귀
    // 앞 뒤 포인터와 현재까지 지운 문자의 수 k를 매개변수로 같는다.
    static int findAnswer(int idx1, int idx2, int k) {
        // k가 4이상이 되거나
        // idx1이 idx2보다 같거나 커지는 시점에서 해당 k값을 반환하고 종료
        if (k >= 4 || idx1 >= idx2)
            return k;
        
        // 그 외의 경우는 두 포인터가 가르키는 문자 비교
        int min = Integer.MAX_VALUE;
        // 같은 경우, 두 포인터는 안쪽으로 전진 k값은 그대로
        if (input[idx1] == input[idx2])
            min = Math.min(min, findAnswer(idx1 + 1, idx2 - 1, k));
        else    // 그 외의 경우, 앞 포인터나 뒷 포인터를 하나 전진, k값은 하나 증가하는 두 경우를 모두 계산
            min = Math.min(min,
                    Math.min(findAnswer(idx1 + 1, idx2, k + 1), findAnswer(idx1, idx2 - 1, k + 1)));
        // 찾은 최소 k값을 반환
        return min;
    }
}