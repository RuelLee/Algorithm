/*
 Author : Ruel
 Problem : Baekjoon 1174번 줄어드는 수
 Problem address : https://www.acmicpc.net/problem/1174
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1174_줄어드는수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    static List<Long> list;

    public static void main(String[] args) throws IOException {
        // 음이 아닌 정수를 십진법으로 표기했을 때, 왼쪽에서부터 자리수가 감소할 때, 그 수를 줄어드는 수라고 한다.
        // n번째 줄어드는 수를 구하라
        // 그러한 수가 없다면 -1을 출력한다
        //
        // 브루트 포스 문제
        // 왼쪽자리부터 감소하므로 가장 큰 줄어드는 수는 9876543210이 될 수 밖에 없다.
        // 줄어드는 수의 개수가 적으므로 브루트포스로 모든 수를 찾아도 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n번째 줄어드는 수
        int n = Integer.parseInt(br.readLine());

        // 리스트에 모두 담는다.
        list = new ArrayList<>();
        // 가장 왼쪽 자리가 i부터 시작하는 줄어드는 수를
        // 모두 찾는다.
        for (int i = 0; i < 10; i++)
            bruteForce(i);
        // 정렬
        Collections.sort(list);
        
        // n번째 수가 존재한다면 출력. 그렇지 않다면 -1 출력
        System.out.println(list.size() >= n ? list.get(n - 1) : -1);
    }
    
    // 브루트 포스
    static void bruteForce(long n) {
        // 현재까지의 n 또한 줄어드는 수
        // 리스트에 추가
        list.add(n);

        // 만약 마지막 자리 수가 0이라면
        // 더 이상 진행이 불가능한 경우.
        // 종료
        if (n % 10 == 0)
            return;

        // 마지막 자리 수보다 작은 수를 오른쪽에 추가한다.
        for (int i = 0; i < (n % 10); i++)
            bruteForce(n * 10 + i);
    }
}