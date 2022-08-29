/*
 Author : Ruel
 Problem : Baekjoon 2023번 신기한 소수
 Problem address : https://www.acmicpc.net/problem/2023
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2023_신기한소수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class Main {
    static PriorityQueue<Integer> answers;

    public static void main(String[] args) throws IOException {
        // 예를 들어 7331의 수가 주어진다
        // 이 수는 7, 73, 733, 7331 모두 소수이다. 이러한 수를 신기한 소수라고 부른다.
        // n이 주어질 때, n자리의 신기한 소수들을 모두 구하여라.
        //
        // 백트래킹과 소수 판별을 이용한 문제
        // 하나씩 뒤의 수를 붙여나가며, 소수인지 확인해나간다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        // 신기한 소수들을 최소힙을 통해 오름차순 정렬.
        answers = new PriorityQueue<>();
        // n자리의 신기한 수를 answers에 저장한다.
        findPrimeNumbers(n, 0);

        StringBuilder sb = new StringBuilder();
        // 저장된 신기한 소수들을 출력.
        while (!answers.isEmpty())
            sb.append(answers.poll()).append("\n");
        System.out.print(sb);
    }

    // n자리의 신기한 소수들을 찾는다.
    static void findPrimeNumbers(int n, int number) {
        // n이 0이고, 찾아진 수가 소수라면
        if (n == 0 && isPrimeNumber(number)) {
            // 답안을 저장
            answers.offer(number);
            return;
        }

        // n이 0이 아니라면 뒤에 수를 하나씩 붙인다.
        for (int i = 0; i < 10; i++) {
            int nextNumber = number * 10 + i;
            // nextNumber가 소수라면, 메소드를 재귀적으로 호출하여
            // 신기한 소수를 찾아간다.
            if (isPrimeNumber(nextNumber))
                findPrimeNumbers(n - 1, nextNumber);
        }
    }

    // 소수인지 확인한다.
    static boolean isPrimeNumber(int n) {
        // 0, 1은 소수가 아니다.
        if (n < 2)
            return false;

        // n이 2 ~ √n 까지의 수의 배수인지 확인한다.
        for (int i = 2; i <= Math.sqrt(n); i++) {
            // 그렇다면 소수가 아니고
            if (n % i == 0)
                return false;
        }
        // 그렇지 않다면 소수.
        return true;
    }
}