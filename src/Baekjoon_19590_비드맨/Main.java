/*
 Author : Ruel
 Problem : Baekjoon 19590번 비드맨
 Problem address : https://www.acmicpc.net/problem/19590
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_19590_비드맨;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // n종류의 구슬들에 대한 개수 정보가 주어진다.
        // 서로 다른 두 종류의 구슬들을 부딪치면 두 구슬은 사라진다고 한다.
        // 최대한 많은 구슬을 없애고자할 때, 남은 구슬의 수는?
        //
        // 애드 혹 문제
        // 가장 개수가 많은 한 구슬 종류와 나머지로 생각한다.
        // 가장 개수가 많은 한 구슬이 나머지 구슬들의 합보다 많다면
        // 차이 만큼 남는 경우가 최대한 많은 구슬을 없앤 경우이다.
        // 그렇지 않고, 가장 많은 한 구슬 종류보다 나머지 구슬들의 개수 합이 더 많은 경우
        // 결국 하나씩 하나씩 구슬들을 부딪쳐 없앤다면 모두 없애거나 하나만 남는 경우가 발생한다.
        // 이 경우는 총합의 개수가 짝수인지, 홀수인지에 따라 판별할 수 있다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 구슬의 종류
        int n = Integer.parseInt(br.readLine());
        
        // 가장 많은 개수의 구슬
        int max = 0;
        // 나머지 구슬 개수 총합
        long sum = 0;
        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(br.readLine());
            sum += num;
            max = Math.max(max, num);
        }
        sum -= max;

        // 가장 많은 한 종류의 구슬 개수가 나머지보다 같거나 크다면
        // 차이만큼이 남는다.
        if (max >= sum)
            System.out.println(max - sum);
        // 그 외의 경우 합이 짝수라면
        // 모두 없앨 수 있고
        else if ((sum + max) % 2 == 0)
            System.out.println(0);
        // 합이 홀수라면
        // 하나가 남는다.
        else
            System.out.println(1);
    }
}