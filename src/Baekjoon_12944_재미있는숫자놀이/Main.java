/*
 Author : Ruel
 Problem : Baekjoon 12944번 재미있는 숫자 놀이
 Problem address : https://www.acmicpc.net/problem/12944
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12944_재미있는숫자놀이;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int n;
    static int[] cards;

    public static void main(String[] args) throws IOException {
        // k개의 카드를 갖고 있으며 각각의 카드에는 10억 이하의 수가 적혀있다.
        // 1 ~ n 범위의 수들중 손에 들고 있는 카드 중 적어도 한 개로 나누어떨어지는 수의 개수를 알고자한다.
        // 그 개수는?
        //
        // 포함 배제, 조합, 유클리드 호제법 문제
        // 먼저 1 ~ n까지의 범위이므로 하나의 수일 경우
        // 단순히 해당 카드의 수만큼으로 나눈 개수가 해당 카드로 나누어떨어지는 수의 개수다.
        // 하지만 복수의 카드가 되면 중복하는 경우가 나오는데 이 때 포함 배제의 원리를 사용하여
        // 복수 카드의 최소 공배수로 해당 범위를 나눈 만큼의 개수가 중복되는 수의 개수가 된다.
        // 조합을 이용하여 복수 카드를 선택하고, 유클리드 호제법을 통해 최소 공배수를 구해
        // 포함 배제의 원리를 사용하여 답을 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 1 ~ n까지의 범위
        n = Integer.parseInt(st.nextToken());
        // k개의 카드
        int k = Integer.parseInt(st.nextToken());
        cards = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        long sum = 0;
        // 홀수 개의 카드를 선택할 땐 포함
        // 짝수 개의 카드를 선택할 때는 배제
        for (int i = 1; i <= k; i++)
            sum += (i % 2 == 1) ? selectCards(0, 0, i, new int[i]) : -selectCards(0, 0, i, new int[i]);
        System.out.println(sum);
    }

    // k개의 카드는 num개의 카드를 조합을 통해 선택한다.
    static long selectCards(int idx, int pick, int num, int[] selected) {
        if (pick == num) {
            // num개의 카드를 모두 고른 경우
            // 최소 공배수를 구한다.
            long mcd = 1;
            for (int card : selected) {
                mcd = getMCD(mcd, card);
                // 만약 최소 공배수가 n을 넘어간다면 더 이상 계산할 필요 없이
                // 0을 반환한다.
                if (mcd > n)
                    return 0;
            }
            return n / mcd;
        } else if (idx >= cards.length)     // num개의 카드를 고르지 못한 경우. 0 반환.
            return 0;

        long sum = 0;
        // idx번째 카드를 선택하는 경우.
        selected[pick] = cards[idx];
        sum += selectCards(idx + 1, pick + 1, num, selected);
        // idx번째 카드를 선택하지 않는 경우.
        sum += selectCards(idx + 1, pick, num, selected);

        // 두 경우의 수의 합을 반환.
        return sum;
    }
    
    // 최소 공배수
    static long getMCD(long a, long b) {
        return a / getGCD(a, b) * b;
    }
    // 최대 공약수
    static long getGCD(long a, long b) {
        long max = Math.max(a, b);
        long min = Math.min(a, b);
        while (min > 0) {
            long temp = max % min;
            max = min;
            min = temp;
        }
        return max;
    }
}