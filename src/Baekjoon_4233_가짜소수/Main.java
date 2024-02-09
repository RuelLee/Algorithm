/*
 Author : Ruel
 Problem : Baekjoon 4233번 가짜소수
 Problem address : https://www.acmicpc.net/problem/4233
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_4233_가짜소수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    static HashMap<Integer, Long> memo;

    public static void main(String[] args) throws IOException {
        // 페르마의 소정리 (Fermat's little theorem)의 내용은 다음과 같다.
        // p가 소수일 때, 임의의 정수 a>1에 대해서, a^p == a (mod p)가 성립한다.
        // p가 소수가 아님에도 어떤 정수 a에 대해 위의 식을 만족하는 경우가 있고, 이를 가짜소수라 부른다.
        // p와 a가 주어질 때, 가짜소수인지 판별하라
        //
        // 소수 판별, 분할 정복, 메모이제이션
        // 문제 자체는 간단하나 여러가지 알고리즘이 필요하다.
        // 먼저 가짜 소수는 소수여서는 안되므로, 소수가 아닌지 확인한다.
        // 그 후, 제곱이 매우 크므로 이를 분할 정복을 통해 구한다.
        // 또한 중복 연산을 막기 위해 메모이제이션을 사용한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        StringBuilder sb = new StringBuilder();
        while (input != null) {
            StringTokenizer st = new StringTokenizer(input);
            // 주어지는 p와 a
            int p = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            if (p == 0 && a == 0)
                break;
            
            // 메모이 제이션
            memo = new HashMap<>();
            // p가 소수가 아니고, a에 대해 페르마의 소정리를 만족한다면
            // 가짜 소수
            if (!isPrime(p) && pow(p, a, p) == a)
                sb.append("yes");
            else        // 아닐 경우 no
                sb.append("no");
            sb.append("\n");
            input = br.readLine();
        }
        // 전체 답안 출력
        System.out.print(sb);
    }

    // n이 소수인지 판별한다.
    static boolean isPrime(int n) {
        // i의 제곱이 n보다 같거나 작은 시점까지
        // i가 n의 약수인지 확인한다.
        for (int i = 2; i * i <= n; i++)
            if (n % i == 0)
                return false;
        return true;
    }

    // a의 p제곱 값을 모듈러 p한 값
    static long pow(int p, int a, int originP) {
        // 0제곱일 경우에는 1
        if (p == 0)
            return 1;
        else if (p == 1)    // 1제곱일 경우에는 a
            return a;

        // 분할 정복과 메모이제이션을 통해 제곱을 구한다.
        // 값이 너무 커지는 걸 막기 위해 처음 p로 모듈려 연산하여 값을 줄여준다.
        if (!memo.containsKey(p))
            memo.put(p, ((pow(p / 2, a, originP) * pow(p / 2, a, originP) % originP) * (p % 2 == 0 ? 1 : a)) % originP);

        // a의 p제곱을 모듈러 p한 값
        return memo.get(p);
    }
}