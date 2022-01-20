/*
 Author : Ruel
 Problem : Baekjoon 3343번 장미
 Problem address : https://www.acmicpc.net/problem/3343
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 장미;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Bundle {
    long roses;
    long price;

    public Bundle(long roses, long price) {
        this.roses = roses;
        this.price = price;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 장미를 각각 a개에 b원, c개에 d원씩 판매하고 있다
        // 장미를 n개 이상 구매할 때, 가장 싼 가격은 얼마인가
        // n 값이 매우 크기 때문에 DP를 사용할 수는 없다
        // 구현이 어려운 것은 아니었지만 개념을 생각하기가 어려웠다. 수학적 지식을 이용해야하는 문제였다
        // b / a(1개당 장미 구매 비용)이 d / c보다 싸다고 하자
        // 그리고 a와 b의 최소공배수를 e라 하자
        // 이렇게 한다면, e개의 장미를 구매할 때, a묶음으로 e개의 장미를 구매하는 것이 c묶음으로 e개의 장미를 구매하는 것보다 반드시 싸다
        // 따라서 n개의 장미를 a묶음으로 전부 구매한 값으로부터, c묶음을 (1 ~ a, c의 최소 공배수를 c로 나눈 값)개 넣었을 때 값을 비교하며 최소값으로 갱신해간다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        long n = Long.parseLong(st.nextToken());
        long a = Integer.parseInt(st.nextToken());
        long b = Integer.parseInt(st.nextToken());
        long c = Integer.parseInt(st.nextToken());
        long d = Integer.parseInt(st.nextToken());

        Bundle cheap;        // 싼 묶음
        Bundle expensive;    // 비싼 묶음

        if ((double) b / a < (double) d / c) {
            cheap = new Bundle(a, b);
            expensive = new Bundle(c, d);
        } else {
            cheap = new Bundle(c, d);
            expensive = new Bundle(a, b);
        }

        // a, c의 최소공배수 / expensive.roses (비싼 묶음보다 싼 묶음이 무조건 싸지는 개수)와
        //  n / expensive.roses(n개의 장미를 전부 비싼 묶음으로 샀을 때의 개수) 중 작은 값을 택한다.
        long expensiveMax = Math.min(cheap.roses / getGCD(cheap.roses, expensive.roses), (long) Math.ceil((double) n / expensive.roses));
        long price = Long.MAX_VALUE;
        // 0(전부 싼 묶음으로 샀을 때부터 가장 비싼 묶음으로 전부 채우거나, 둘의 최소공배수 장미를 비싼 묶음으로 채웠을 때의 값까지)
        // 장미값을 계산하여 최소값을 갱신한다.
        for (int i = 0; i <= expensiveMax; i++)
            price = Math.min(price, i * expensive.price + (long) Math.ceil((double) (n - expensive.roses * i) / cheap.roses) * cheap.price);

        System.out.println(price);
    }
    // 최대공약수를 구한다.
    static long getGCD(long a, long b) {
        long big = Math.max(a, b);
        long small = Math.min(a, b);
        long temp;
        while (small > 0) {
            temp = big % small;
            big = small;
            small = temp;
        }
        return big;
    }
}