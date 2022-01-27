/*
 Author : Ruel
 Problem : Baekjoon 1644번 소수의 연속합
 Problem address : https://www.acmicpc.net/problem/1644
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 소수의연속합;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<Integer> primeNumbers;

    public static void main(String[] args) {
        // n이 주어진다
        // 이 n이 연속한 소수의 합으로 표현되는지 확인하고, 그러한 경우가 몇 개인지 출력하라
        // 예를 들어 41인 경우, 2 + 3 + 5 + 7 + 11 + 13, 11 + 13 + 17, 41 3가지로 표현이 가능하다.
        // 소수를 구해놓고 이에 대해 연속한 구간의 합을 구하도록하자
        // 소수를 구하는 방법 중 '에라토스테네스의 체'를 활용해서 소수들을 구해두자
        // 에라토스테네스의 체는 2부터 하나씩 수를 키워가며, 배수들을 모두 소수가 아님을 체크해두는 것이다.
        // 최종적으로 체크되지 않은 수들만이 소수이다
        // 그리고 구간합을 구할 때는 투 포인터를 활용해보도록 하자.
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        // 에라토스테네스의 체
        // n은 최대 4백만.
        boolean[] numbers = new boolean[4_000_001];
        for (int i = 2; i < numbers.length; i++) {
            // 이미 i가 소수가 아니라 되어있다면, i의 약수에 의해 체크된 경우.
            // 건너뛰어주자.
            if (numbers[i])
                continue;

            // 배수들만 소수가 아님을 체크해두자.
            for (int j = 2; i * j < numbers.length; j++)
                numbers[i * j] = true;
        }
        primeNumbers = new ArrayList<>();
        for (int i = 2; i < numbers.length; i++) {
            if (!numbers[i])        // 체크가 안된 수들은 소수.
                primeNumbers.add(i);
        }

        // j에서부터 i까지의 합을 number로 나타낼 것이다.
        int j = 0;
        int number = 0;
        int count = 0;
        for (Integer primeNumber : primeNumbers) {
            // i값이 하나 증가할 때마다, i번째 소수를 더한다.
            number += primeNumber;
            // number가 우리가 원하는 n보다 크다면, j번째 소수를 빼고, j값을 늘려간다.
            while (number > n)
                number -= primeNumbers.get(j++);

            // number와 n이 값이 같다면, 가짓수 중 하나로 세준다.
            if (number == n)
                count++;
        }
        // 최종적으로 센 가짓수를 출력.
        System.out.println(count);
    }
}