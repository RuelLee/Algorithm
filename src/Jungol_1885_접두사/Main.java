/*
 Author : Ruel
 Problem : Jungol 1885번 접두사
 Problem address : https://jungol.co.kr/problem/1885
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1885_접두사;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // t개의 테스트케이스가 주어진다.
        // 각 테스트케이스마다 n이 주어지며 n개의 단어가 주어진다.
        // 이 때, 한 단어가 다른 단어의 접두사가 되는 경우 NO를
        // 그러한 경우가 없다면 YES를 출력한다
        //
        // 정렬 문제
        // 단어들을 정렬하고, 인접한 단어들만 비교하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        // 단어들을 입력 받을 공간
        String[] inputs = new String[10_000];
        for (int testCase = 0; testCase < t; testCase++) {
            // n개의 단어
            int n = Integer.parseInt(br.readLine().trim());
            // 입력 받고
            for (int i = 0; i < n; i++)
                inputs[i] = br.readLine().trim();
            // 정렬
            Arrays.sort(inputs, 0, n);

            // 인접한 두 단어를 비교하며
            // 앞 단어가 뒷단어의 접두사인지 판별한다
            //
            boolean answer = false;
            for (int i = 0; i < n - 1 && !answer; i++) {
                boolean flag = true;
                // 앞 단어의 길이만큼
                // 앞 단어가 뒷 단어보다 긴 경우가 생기는데
                // 이 때는 공통된 부분까지는 진행되나,
                // 달라지는 부분부터는 break를 통해 더 이상 진행되지 않으므로 상관없다
                for (int j = 0; j < inputs[i].length(); j++) {
                    if (inputs[i].charAt(j) != inputs[i + 1].charAt(j)) {
                        flag = false;
                        break;
                    }
                }

                // 접두사가 되는 단어를 찾은 경우
                if (flag)
                    answer = true;
            }
            // 답 기록
            sb.append(answer ? "NO" : "YES").append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}