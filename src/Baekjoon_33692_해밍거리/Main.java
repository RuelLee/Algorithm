/*
 Author : Ruel
 Problem : Baekjoon 33692번 해밍 거리
 Problem address : https://www.acmicpc.net/problem/33692
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_33692_해밍거리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 두 수의 해밍 거리는 각 수를 이진법으로 나타냈을 때,
        // 서로 다른 비트의 개수를 말한다.
        // 1<= A < B <= 10^18의 A, B가 주어질 때
        // 해밍 거리가 최대인 두 정수를 구하라
        // 그러한 쌍이 여러개라면 아무거나 출력한다.
        //
        // 애드 혹
        // B가 A보다 크므로 두 수를 이진법으로 나타내고, 큰 자리부터 살펴볼 경우
        // 처음 값이 달라지는 지점에서 B는 반드시 1이고, A는 반드시 0이다.
        // 이 처음 값이 달라지는 지점 이후로 A는 1을, B는 0을 채워나가면
        // A에서 만들어지는 값은 A보다 같거나 크며, B보다 작은 값이 되고
        // B에서 만들어지는 값은 B보다 같거나 작으며 A보다 큰 값이 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 주어지는 값 a, b
        long a = Long.parseLong(st.nextToken());
        long b = Long.parseLong(st.nextToken());

        // 각각을 이진법으로 나타낸다.
        // 값의 범위에 따라 최대 64개의 자리를 갖는다.
        // 사실은 음의 값은 표현하지 않으므로 63개
        int[] binaryA = new int[64];
        int[] binaryB = new int[64];
        int idx = 63;
        while (a > 0 || b > 0) {
            binaryA[idx] = (int) (a % 2);
            binaryB[idx] = (int) (b % 2);
            a /= 2;
            b /= 2;
            idx--;
        }

        // 값이 달라지는 처음 위치를 찾는다.
        boolean foundFirstDifference = false;
        for (int i = 0; i < binaryA.length; i++) {
            // 아직 값이 서로 다른 자리를 못 찾았는데
            if (!foundFirstDifference) {
                // 이번 자리에 값이 달라진 경우
                // 달라진 것을 표시
                if (binaryA[i] != binaryB[i])
                    foundFirstDifference = true;
            } else {
                // 값이 달라진 자리를 찾았다면 그 이후로는
                // a에는 1을
                // b에는 0을 채운다.
                binaryA[i] = 1;
                binaryB[i] = 0;
            }
        }
        
        // 이진법으로 표현된 수를 다시 십진법으로 만들어 주고
        long pow = 1;
        for (int i = binaryA.length - 1; i >= 0; i--) {
            if (binaryA[i] == 1)
                a += pow;
            if (binaryB[i] == 1)
                b += pow;
            pow *= 2;
        }
        // 값 출력
        System.out.println(a + " " + b);
    }
}