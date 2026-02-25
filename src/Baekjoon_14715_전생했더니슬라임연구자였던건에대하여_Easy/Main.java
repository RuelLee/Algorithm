/*
 Author : Ruel
 Problem : Baekjoon 14715번 전생했더니 슬라임 연구자였던 건에 대하여 (Easy)
 Problem address : https://www.acmicpc.net/problem/14715
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14715_전생했더니슬라임연구자였던건에대하여_Easy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 슬라임은 에너지와 흠집이 있다.
        // 슬라임은 두 개의 슬라임으로 분할하며, 갖고 있는 에너지는 분할 후 두 슬라임 에너지의 곱과 같다.
        // 흠집은 분할을 한번씩 진행할 때마다 1개씩 늘어난다.
        // 초기에 에너지가 k이고 흠집이 0인 슬라임이 주어진다.
        // 해당 슬라임이 더 이상 분할을 할 수 없을 때까지 분할을 진행하는데
        // 흠집의 개수가 가장 많은 슬라임의 흠집 개수가 최소가 되게끔하고자 한다.
        // 그 개수는?
        //
        // 소인수분해 문제
        // 생각해보면 두 슬라임의 곱으로 나누어지므로
        // 소인수분해를 통해 두 슬라임을 최대한 소인수의 개수가 같도록 분할해나가면
        // 분할 단계를 최소화할 수 있다

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int k = Integer.parseInt(br.readLine());

        // 소인수의 개수 세기
        int factors = 0;
        for (int i = 2; i * i <= k; i++) {
            while (k % i == 0) {
                factors++;
                k /= i;
            }
        }
        // k가 1 초과일 경우, 남은 k 자체가 소수
        if (k > 1)
            factors++;

        // 흉터의 개수
        int count = 0;
        // 소인수의 개수가 1개 초과인 경우
        while (factors > 1) {
            // 소인수가 균등하게끔 두 슬라임으로 분할한다.
            // 홀수인 경우, 균등하게 되지 않는데, int 의 나눗셈 상 소수점 자리가 버려지게 되므로
            // +1을 한 값을 2로 나눈다.
            factors = (factors + 1) / 2;
            count++;
        }
        // 흉터의 개수 출력
        System.out.println(count);
    }
}