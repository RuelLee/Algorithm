/*
 Author : Ruel
 Problem : Baekjoon 28435번 배수 피하기
 Problem address : https://www.acmicpc.net/problem/28435
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28435_배수피하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static final int LIMIT = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        // 크기가 n인 집합 A가 주어진다.
        // A의 부분집합 s가 좋은 집합이라는 것은
        // S의 크기는 2 이상이다.
        // s의 임의의 두 원소의 합이 k의 배수가 되는 경우가 존재하지 않는다.
        // 좋은 집합의 개수를 구하라
        //
        // 조합 문제
        // 임의의 두 원소의 합이 k가 되지 않는다는 점에 주목하면
        // 수의 값 자체는 중요하지 않고
        // 각 수를 k로 나눈 나머지 값이 중요하다.
        // 따라서 각 수를 모듈러 연산한 값으로 구분하여 개수를 센다.
        // 그 후, 만약 나머지가 a인 수의 개수를 Ca라 할 때
        // 나머지 a인 수가 좋은 집합에 포함되는 경우의 수는 Ca^2 -1이다
        // 또한 나머지가 k-a인 수는 Ck-2 ^ 2 - 1이다.
        // 나머지가 a인 수와 나머지가 k -a인 수는 동시에 포함될 수 없으므로
        // 두 경우의 곱하지 않고 더한다.
        // 모든 경우에 대해 위의 과정을 반복하며 개수를 센다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 크기 n인 집합 a, 좋은 집합이 되기 위해선 합이 k의 배수가 되는 두 원소가 있어서는 안된다.
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 값이 커지므로 답을 1_000_000_007으로 나눈 나머지를 출력한다.
        // 2의 제곱도 미리 구해둔다.
        int[] pows = new int[n + 1];
        pows[0] = 1;
        for (int i = 1; i < pows.length; i++)
            pows[i] = (pows[i - 1] * 2) % LIMIT;

        st = new StringTokenizer(br.readLine());
        // k로 나눈 나머지 별로 수를 구분한다.
        int[] counts = new int[k];
        for (int i = 0; i < n; i++)
            counts[Integer.parseInt(st.nextToken()) % k]++;

        // 나머지가 0인 경우는 한 원소 자체로 k의 배수인 경우.
        // 해당하는 수는 여러개 속하면 두 원소의 합이 k의 배수가 되므로
        // 하나만 속할 수 있다.
        // 하나도 속하지 않는 경우 + 각 원소가 속하는 경우의 수 = counts[0] + 1
        long count = counts[0] + 1;
        // 나머지가 i인 경우, 가능한 경우의 수는 2^(counts[i])개 이고
        // 동시에 같이 살펴봐야하는 경우가
        // 나머지가 k-i인 경우이다. 이 경우의 수는 2^(counts[k-i])이다.
        // 두 경우는 동시에 존재할 수 없고, 두 경우 중 하나의 경우만 발생해야하므로
        // 두 경루를 곱하지 않고 더한다.
        // 양쪽 모두 공집합인 경우가 속해 공집합인 경우가 2개 발생하므로 -1을 해준다.
        for (int i = 1; i < (k + 1) / 2; i++)
            count = (count * (pows[counts[i]] + pows[counts[k - i]] - 1)) % LIMIT;

        // k가 짝수인 경우
        // 나머지가 k/2인 수도 하나의 원소만 속해야한다.
        // 따라서 counts[k/2] + 1(공집합 포함)한 값을 곱한다.
        if (k % 2 == 0)
            count = (count * (counts[k / 2] + 1)) % LIMIT;

        // 이제 전체 중에서 공집합과 원소가 하나인 경우를 제외한다.
        count = (count + LIMIT - n - 1) % LIMIT;
        // 답 출력
        System.out.println(count);
    }
}