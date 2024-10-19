/*
 Author : Ruel
 Problem : Baekjoon 26092번 수학적인 최소 공통 조상
 Problem address : https://www.acmicpc.net/problem/26092
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_26092_수학적인최소공통조상;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 10^12개의 정점으로 이루어진 트리가 주어진다.
        // 이 트리는 특이한 성질이 있는데
        // * 2 이상 n 이하의 정수 x에 대해 x의 가장 작은 소인수를 p라 하자.
        // * x의 부모 노드는 x / p번 정점이다.
        // 두 정수 a와 b가 주어질 때, 가장 가까운 공통 조상의 번호를 구하라
        //
        // 수학 문제
        // a와 b를 비교하며, 서로 다른 경우
        // 더 큰 값에 대해 조상 노드로 올라가면 된다.
        // 위 과정을 같아질 때까지 반복한다.
        // a와 b가 최대 10^12 까지 주어지므로, 전체를 다 살펴봐서는 안되고
        // 약수를 찾을 때, 제곱근의 범위 내에서 찾으면 되므로, 이를 활용하자

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 두 정수 a, b
        long a = Long.parseLong(st.nextToken());
        long b = Long.parseLong(st.nextToken());

        // 두 정수가 다른 경우
        // 반복문을 반복하며, 더 큰 수를 부모 노드로 옮긴다.
        while (a != b) {
            if (a > b)
                a = findParent(a);
            else
                b = findParent(b);
        }

        // 최종적으로 두 수가 같아지면 반복문을 종료하게 되며
        // 그 때의 같아진 값을 출력한다.
        System.out.println(a);
    }

    // n의 조상 노드를 찾는다.
    static long findParent(long n) {
        // 2부터 최대 100만까지 나누어떨어지는 가장 작은 수를 찾고
        // 찾아진다면 그 수로 나눈 값을 반환한다.
        for (int i = 2; i < 1_000_001; i++) {
            if (n % i == 0)
                return n / i;
        }
        // 만약 찾지 못한다면 소수인 경우이므로
        // 1을 반환한다.
        return 1;
    }
}