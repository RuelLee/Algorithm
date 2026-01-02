/*
 Author : Ruel
 Problem : Baekjoon 31963번 두 배
 Problem address : https://www.acmicpc.net/problem/31963
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_31963_두배;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수로 이루어진 수열이 주어진다.
        // 어떤 한 수를 두 배를 만드는 연산을 할 수 있다.
        // 최소한의 연산을 통해 수열을 비내람차순으로 만들고자 할 때, 연산의 횟수는?
        //
        // 그리디 문제
        // 직접 수를 두배로 만들면 오버플로우가 난다.
        // 2배를 하는 경우, 이진법으로 나타내면 뒤에 0이 하나 추가되는 형태이다.
        // 뒤에 0이 아무리 많이 붙든, 값의 의미가 있는 부분은 앞의 원래 수에 해당하는 부분만이다.
        // 따라서 각 수를 모두 2진법으로 나타내고, 이전 수와 동일한 이진법의 길이를 맞춘다.
        // 그리고 앞의 자리부터 수를 비교하며, 현재 수가 이전 수보다 더 큰지 여부를 판단한다.
        // 작다고 판단된다면 연산을 사용하여, 뒤에 0을 하나 붙이는 과정을 한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // n개의 수로 이루어진 수열
        int n = Integer.parseInt(br.readLine());
        String[] array = new String[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            array[i] = Integer.toBinaryString(Integer.parseInt(st.nextToken()));

        // 2를 곱한 횟수
        long[] changes = new long[n];
        long answer = 0;
        for (int i = 1; i < n; i++) {
            // i번째 수가 i-1번째 수보다 길이가 작은 경우.
            // (i번째 수가 더 길다면 당연히 값이 더 크므로 계산하지 않는다)
            if (changes[i - 1] + array[i - 1].length() >= array[i].length()) {
                // 두 수의 길이를 맞춰줌
                changes[i] += array[i - 1].length() + changes[i - 1] - array[i].length();

                boolean noMoreChange = true;
                // 앞의 자리부터 수를 비교
                for (int j = 0; j < Math.max(array[i].length(), array[i - 1].length()) && noMoreChange; j++) {
                    int current = (j < array[i].length() ? array[i].charAt(j) - '0' : 0);
                    int pre = (j < array[i - 1].length() ? array[i - 1].charAt(j) - '0' : 0);
                    if (current == pre)
                        continue;
                    // i가 1이고 i-1번째가 0인 지점이 나온다면 더 이상은 연산을 하지 않아도 i번쨰수가 더 크다.
                    else if (current > pre)
                        break;
                    else    // 반대인 경우는 연산을 하여 0을 더 붙여줘야한다.
                        noMoreChange = false;
                }

                // i번째 수가 더 작은 경우, 연산을 하여 0을 더 붙여준다.
                if (!noMoreChange)
                    changes[i]++;
            }
            // answer에 연산의 횟수 누적
            answer += changes[i];
        }
        // 답 출력
        System.out.println(answer);
    }
}