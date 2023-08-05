/*
 Author : Ruel
 Problem : Baekjoon 18291번 비요뜨의 징검다리 건너기
 Problem address : https://www.acmicpc.net/problem/18291
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18291_비요뜨의징검다리건너기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {
    static HashMap<Integer, Integer> hashMap;
    static final int LIMIT = 1000000007;

    public static void main(String[] args) throws IOException {
        // n개의 징검다리가 1 ~ n 번호를 갖고 있다.
        // 현재 비요뜨는 1번 위치에 서있으며 n번에 가고자 한다.
        // 징검다리를 건널 때는, 1 <= x <= n인 x만큼 이동할 수 있다.
        // n번 징검다리를 지나쳐선 안되고 정확히 도착해야한다.
        // n번 징검다리에 도달하는 수는?
        //
        // 분할 정복을 통한 거듭제곱 문제
        // n이 4여서, 1 -> 4로 이동한다고 하자
        // 총 3칸을 움직이며, 세칸을 움직이는 방법은
        // 1 + 1 + 1
        // 2 + 1
        // 1 + 2
        // 3
        // 네가지가 있다.
        // 이는 맨 처음 1 + 1 + 1 에서 +를 없애고 양쪽의 수를 더하는 경우의 수와 같다.
        // 즉 2개의 +가 있으므로, 이를 없애는 경우의 수는 2의 2제곱 4가지다.
        // 따라서 n이 주어지면 2의 (n-2)제곱을 구하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        hashMap = new HashMap<>();
        // 2의 0 제곱은 1
        hashMap.put(0, 1);
        // n이 1까지 주어지므로, 위 경우, 2^(-1)제곱을 찾게된다.
        // 해당 경우를 위해 -1에도 1을 넣어둔다.
        hashMap.put(-1, 1);
        
        // 테스트케이스의 수
        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            // 주어진 n
            int n = Integer.parseInt(br.readLine());
            // n-2 제곱을 구한다.
            sb.append(findAnswer(n - 2)).append("\n");
        }
        System.out.print(sb);
    }

    static int findAnswer(int n) {
        // n제곱 결과를 구한 적이 없다면
        if (!hashMap.containsKey(n)) {
            // 수를 계산하며 int 범위를 넘어갈 수 있음에 주의한다.
            // 짝수라면
            if (n % 2 == 0)
                // 2 ^(n/2) * 2^(n/2) 을 통해 2^n을 구한다.
                hashMap.put(n, (int) (((long) findAnswer(n / 2) * findAnswer(n / 2)) % LIMIT));
            else        // 홀수라면 2 ^(n/2) * 2^(n/2) * 2를 통해 구한다.
                hashMap.put(n, (int) (((long) findAnswer(n / 2) * findAnswer(n / 2) * 2) % LIMIT));
        }
        // 결과값 반환.
        return hashMap.get(n);
    }
}