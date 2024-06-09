/*
 Author : Ruel
 Problem : Baekjoon 2014번 소수의 곱
 Problem address : https://www.acmicpc.net/problem/2014
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2014_소수의곱;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // k개의 소수가 주어진다.
        // 이 소수들을 중복을 허용하여 곱한다.
        // 원래 소수 또한 포함하여 n번째 수는 무엇인지 답하라
        // 2 5 7이 주어진다면
        // 2 4 5 7 8 10 14 16 20 25 28 32 35... 가 된다.
        //
        // 우선순위큐 문제
        // 처음에는 우선순위큐 + 해쉬맵을 통해 해결하려했으나
        // 자꾸 메모리 초과가 나, 할 수 없었다.
        // 결국 중복되는 경우를 쳐내 최소한의 시간과 메모리로 계산해야했다.
        // 중복되는 경우를 생각해보면
        //      2   5   7
        //  2   4  10  14
        //  5  10  25  35
        //  7  14  35  49
        // 표로 나타내어 보면 i = j인 대각선을 기준으로 서로 동일한 경우를 계산하게된다.
        // 따라서 현재 꺼낸 수가 곱하려는 소수를 약수로 갖고 있다면 더 이상 진행하지 않아도 된다.
        // 어차피 뒤에 나오는 수에 의해 계산되어지므로.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // k개의 소수, 찾는 수는 n번째 수.
        int k = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        // 주어지는 소수
        int[] primeNumbers = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 우선순위큐를 통해 오름차순으로 살펴본다.
        PriorityQueue<Long> asc = new PriorityQueue<>();
        // 값의 범위가 int 범위를 넘어갈 수 있음에 주의.
        for (int pn : primeNumbers)
            asc.offer((long) pn);

        // 아래 과정을 n-1번 반복하면
        // 우선순위큐의 최상단에는 n번째 수가 올라와있다.
        for (int i = 0; i < n - 1; i++) {
            // 순서인 수를 꺼내
            long current = asc.poll();

            // 소수들과 곱한다.
            for (int pn : primeNumbers) {
                long newNum = current * pn;
                // 새로운 수가 int 범위를 넘어간다면 더 이상 진행하지 않고 종료.
                // 소수가 오름차순 정렬되어있기 때문에 뒤에는 더 큰 수가 나온다.
                if (newNum >= Integer.MAX_VALUE)
                    break;

                // 새로운 수 우선순위큐에 추가
                asc.offer(newNum);
                // 만약 현재 꺼낸 수가 소수의 배수라면
                // 꺼낸 수의 턴을 종료하고 다음 수를 꺼낸다.
                if (current % pn == 0)
                    break;
            }
        }
        // 우선순위큐 최상단의 수 출력
        System.out.println(asc.peek());
    }
}