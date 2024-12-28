/*
 Author : Ruel
 Problem : Baekjoon 23888번 등차수열과 쿼리
 Problem address : https://www.acmicpc.net/problem/23888
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23888_등차수열과쿼리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 초항이 a, 공차가 d인 등차 수열이 주어진다.
        // q개의 쿼리를 처리해야한다.
        // 쿼리 형식은 다음과 같다.
        // 1 l r : Al ~ Ar의 합을 출력한다.
        // 2 l r : Al ~ Ar의 최대공약수를 출력한다.
        //
        // 유클리드 호제법 문제
        // 1번 쿼리는 등차수열의 합으로 간단하게 풀 수 있다.
        // 2번이 생각을 해봐야하는데, 유클리드 호제법으로 연속한 두 항을 생각해보자.
        // a + (n-1) * d와 a + n * d라 했을 때
        // 유클리드 호제법을 진행하면 두 수는
        // a + (n - 1) * d 와 d가 되고 한 번 더 진행하면
        // a 와 d가 된다.
        // 이는 연속한 모든 수에 대해 성립하므로
        // l == r이 아닌 조건 하에서는 a와 d의 최대공약수가 답이 된다.
        // l == r인 조건에는 해당하는 수 자체가 최대공약수가 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 초항 a, 공차 d인 등차수열
        int a = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());
        
        // 같지 않은, 연속한 수열에서의 최대공약수
        // a와 d의 최대공약수가 답
        int answer2 = getGCD(a, d);
        
        // q개의 쿼리 처리
        int q = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int o = Integer.parseInt(st.nextToken());
            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());

            // 1번 쿼리일 경우
            // 등차수열의 합을 통해 간단하게 풀 수 있다.
            if (o == 1)
                sb.append((r - l + 1) * (a + (long) (r - 1) * d + a + (long) (l - 1) * d) / 2);
            else if (l == r)        // 2번 쿼리인데, l == r인 경우, 해당하는 수 자체가 최대공약수
                sb.append(a + (long) (r - 1) * d);
            else        // 2번 쿼리이며, 연속한 수열일 경우. 미리 구해둔 a와 d의 최대공약수가 답.
                sb.append(answer2);
            sb.append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }

    // 유클리드 호제법을 사용하여 a와 b의 최대공약수를 구한다.
    static int getGCD(int a, int b) {
        int max = Math.max(a, b);
        int min = Math.min(a, b);
        while (min > 0) {
            int temp = max % min;
            max = min;
            min = temp;
        }
        return max;
    }
}