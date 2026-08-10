/*
 Author : Ruel
 Problem : Jungol 5666번 4와 7 (SRETAN)
 Problem address : https://jungol.co.kr/problem/5666
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_5666_4와7_SRETAN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 4와 7로만 이루어진 수들 중 k번째 정수는?
        //
        // 진법 변환 문제
        // 길이가 하나인 경우의 수 4, 7
        // 길이가 둘인 경우의 수 44 47 74 77 ...
        // 2의 제곱수마다 길이가 늘어난다.
        // 따라서 k보다 커질 때까지 2의 제곱수 키워가며 길이를 센다.
        // 그 후 길이만큼 순서를 살펴보며
        // k가 현재 길이에서 반 이상을 넘는 경우 7, 그렇지 못한 경우 4를 기록해나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // k번째 수를 구한다.
        int k = Integer.parseInt(br.readLine());
        // k번째 수의 길이
        int length = 1;
        // 현재 길이에 해당하는 수의 총 개수
        // 2의 제곱수로 늘어남
        int size = 2;
        // size가 k보다 커질 때까지 size에 2를 곱함
        while (size < k) {
            k -= size;
            length++;
            size *= 2;
        }

        StringBuilder sb = new StringBuilder();
        // 길이만큼 반복
        for (int i = 0; i < length; i++) {
            // size를 반으로 줄이고
            size /= 2;
            // k가 size보다 큰 경우
            // 현재 길이의 수들 중 크기가 반 이상이라는 것이므로 가장 앞자리가 7이 와야됨
            if (k > size) {
                sb.append(7);
                // 그 후엔 size만큼을 빼줘서 다음 자리 계산으로 넘어감
                k -= size;
            } else      // 아닌 경우 4가 옴
                sb.append(4);
        }
        // 답 출력
        System.out.println(sb);
    }
}