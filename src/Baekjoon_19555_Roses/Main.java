/*
 Author : Ruel
 Problem : Baekjoon 19555번 Roses
 Problem address : https://www.acmicpc.net/problem/19555
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_19555_Roses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    // n송이의 장미를 사려한다.
    // 첫번째 가게는 a송이마다 b원에 팔고
    // 두번째 가게는 c송이마다 d원에 팔고 있다.
    // n송이 혹은 그 이상을 사는데 필요한 최소 비용을 구하라
    //
    // 수학 문제
    // 배낭 문제...인듯 싶었지만 값의 범위가 매우 커 직접 계산하면 시간, 메모리가 초과한다.
    // 단순하게 모든 경우의 수를 따지되 그 경우의 수를 줄이는 것이 중요.
    // 먼저 단순히 한 가게에서 모두 사는 경우를 따진다.
    // 그 후, 이제 두 가게에서 적절히 섞어 사는 경우를 따지는데
    // 첫번째 가게에서 살 수 있는 장미의 수는 a송이, 2 * a 송이, ... 로 늘어난다.
    // 이 때, n * a송이가 c의 배수가 되는 경우, 해당 수만큼을 두번째 가게에서 살 수 있다는 뜻이 된다.
    // 위와 같이 구매할 때 이점은, 한송이당 가격은 첫번째 가게가 더 비싸지만 개수를 더 적게 살 수 있어
    // n송이에 모두 근접하게 사면서 싸게 사는 경우이다. 그런데 n * a 송이가 c의 배수가 되는 경우부터는 해당 의미가 사라지게 된다.
    // 따라서 n * a가 c의 배수가 되기 전까지 살펴보면 된다.
    // 마찬가지로 두번째 가게는 m * c가 a의 배수가 되기 전까지 살펴보면 된다.

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 필요한 장미의 수 n
        long n = Long.parseLong(st.nextToken());
        // 첫번째 가게에서 a송이를 b 가격에 판다.
        long a = Long.parseLong(st.nextToken());
        long b = Long.parseLong(st.nextToken());
        // 두번째 가게에서 c송이를 d 가격에 판다.
        long c = Long.parseLong(st.nextToken());
        long d = Long.parseLong(st.nextToken());
        
        // 한 가게에서 모든 장미를 사는 경우
        long sum = Math.min(((n - 1) / a + 1) * b, ((n - 1) / c + 1) * d);
        // 첫번째 가게에서 i묶음을 사고, 나머지를 두번째 가게에서 사는 경우
        // i * a가 c의 배수가 되기 전까지만 계산하면 된다.
        for (int i = 1; i * a % c != 0 && i * a <= n; i++)
            sum = Math.min(sum, i * b + ((n - a * i - 1) / c + 1) * d);
        // 두번째 가게에서 i묶음을 사고, 나머지를 첫번째 가게에서 사는 경우
        for (int i = 1; i * c % a != 0 && i * c <= n; i++)
            sum = Math.min(sum, i * d + ((n - c * i - 1) / a + 1) * b);
        
        // 최소 비용 출력
        System.out.println(sum);
    }
}