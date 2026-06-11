/*
 Author : Ruel
 Problem : Jungol 1618번 3제곱수의 조합
 Problem address : https://jungol.co.kr/problem/1618
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1618_3제곱수의조합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 3의 제곱수를 최대 한 번씩 더해 수열을 만들어 오름차순으로 정렬한다할 때
        // n번째(최대 2^31 - 1) 수는?
        //
        // 수학 문제
        // 3의 제곱수를 최대 한 번씩만 더해 순서를 만든다고 생각하면
        // n을 이진수로 표현하면 간단해진다.
        // 각 번째의 수를 더했느냐 안 더했느냐를 나타내므로
        // 따라서 각 비트를 확인하며 해당하는 수를 3^i꼴로 더해주면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 수열의 순서
        int n = Integer.parseInt(br.readLine());
        // 3^0부터 시작한다.
        long pow = 1;
        // 총합
        long ans = 0;
        // n이 0보다 큰 동안
        while (n > 0) {
            // 마지막 비트가 1이라면 해당하는 3^i을 더해준다.
            if (n % 2 == 1)
                ans += pow;
            // 비트 쉬프트
            n >>= 1;
            // pow에 3을 곱해 다음 제곱을 만든다.
            pow *= 3;
        }
        // 답 출력
        System.out.println(ans);
    }
}