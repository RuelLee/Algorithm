/*
 Author : Ruel
 Problem : Baekjoon 15922번 아우으 우아으이야!!
 Problem address : https://www.acmicpc.net/problem/15922
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15922_아우으우아으이야;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 선분이 주어진다.
        // 선분을 겹치게 그리는 것도 가능하다.
        // 최종적으로 그려지는 선분들 길이의 총합은?
        //
        // 스위핑 문제
        // 이전 선분의 끝점이 어디인지에 따라
        // 중첩되어 사라지는 선분의 길이가 결정된다.
        // 선분은 정렬된 채 주어지므로, 위 사항에 유의하며 순서대로 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 선분
        int n = Integer.parseInt(br.readLine());

        // 선분들 길이의 합
        int sum = 0;
        // 이전 선분들 중 가장 큰 위치
        int preEnd = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 선분의 두 끝점
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            
            // 이전 선분의 끝점이 b보다 크거나 같다면
            // 이번 선분은 모두 중첩되기 때문에 더해지는 길이는 없다
            if (preEnd >= b)
                continue;
            else if (preEnd >= a) {     // 이전 선분의 끝점이 이번 선분 위의 한 곳에 있다면
                // a ~ preEnd까지의 길이는 생략되고
                // preEnd ~ b까지의 길이만 추가된다.
                sum += (b - preEnd);
                preEnd = b;
            } else {        // preEnd가 a보다 작아 겹치지 않는다면
                // (a ~ b)까지의 길이가 온전히 추가된다.
                sum += (b - a);
                preEnd = b;
            }
        }

        // 계산된 선분들 길이의 합을 출력한다.
        System.out.println(sum);
    }
}