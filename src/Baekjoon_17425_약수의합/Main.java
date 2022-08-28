/*
 Author : Ruel
 Problem : Baekjoon 17425번 약수의 합
 Problem address : https://www.acmicpc.net/problem/17425
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17425_약수의합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // f(x)는 x의 모든 약수의 합이라고 한다. 예를 들어 6일 경우, 1 + 2 + 3 + 6 = 12
        // g(y)는 y보다 같거나 작은 모든 자연수에 대한 f(x)의 합이라고 한다. g(3) = f(1) + f(2) + f(3)
        // t개의 수가 주어질 때, 각각의 g() 값을 출력하라.
        //
        // A = a * b라 할 때, A에 먼저 접근하는 것이 아니라
        // a와 b에 대해 먼저 접근했다.
        // 최대 x에 해당하는 값이 1,000,000까지 주어지므로 해당 값을 넘지 않게 배열을 만들고
        // 예를 들어, n의 배수들에 대해서 f(x)에 해당하는 값을 모두 n씩 더해주었다.
        // 그 후 g(y)에 대해서는 누적합을 구하고, 각각의 값을 출력해준다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 먼저 백만까지 f(x)를 모두 구한다.
        long[] fValues = new long[1_000_001];
        // i의 배수들을 살펴본다.
        for (int i = 1; i < fValues.length; i++) {
            // j값은 i * j 가 백만을 넘지 않는 범위까지
            for (int j = 1; i * j < fValues.length; j++)
                // 해당하는 배수는 i를 약수로 갖고 있기 때문에 i를 더해준다.
                fValues[i * j] += i;
        }

        // g(y)는 f(x)에 대한 누적합으로 구한다.
        long[] gValues = new long[1_000_001];
        for (int i = 1; i < gValues.length; i++)
            gValues[i] = gValues[i - 1] + fValues[i];

        // testCase 만큼의 수가 들어온다.
        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        // 해당 수에 해당하는 g(y) 값을 출력해준다.
        for (int t = 0; t < testCase; t++)
            sb.append(gValues[Integer.parseInt(br.readLine())]).append("\n");

        System.out.print(sb);
    }
}