/*
 Author : Ruel
 Problem : Jungol 15779번 전화번호 목록
 Problem address : https://jungol.co.kr/problem/15779
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_15779_전화번호목록;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 서로 다른 길이의 전화번호들이 주어진다.
        // 이 때, 한 전화번호가 다른 전화번호의 접두사에 해당하는 경우
        // 순서대로 번호를 입력하므로, 포함하는 전화번호로 전화를 걸 수 없다.
        // t개의 테스트케이스마다 n이 주어지고, n개의 전화번호가 주어진다.
        // 해당 전화번호 목록이 일관성이 있는지 체크하라
        //
        // 정렬 문제
        // 사전순으로 정렬하면 항상 바로 뒤에 앞을 접두사로 포함할 가능성이 있는 전화번호가 온다.
        // 따라서 사전순으로 정렬한 뒤 바로 뒤 전화번호와의 관계만 체크하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        String[] numbers = new String[10_000];
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            // n개의 전화번호
            int n = Integer.parseInt(br.readLine());
            for (int i = 0; i < n; i++)
                numbers[i] = br.readLine();
            // 정렬
            Arrays.sort(numbers, 0, n);

            boolean reasonable = true;
            // 바로 뒤의 전화번호와 관계를 살펴본다.
            for (int i = 0; i < n - 1; i++) {
                if (numbers[i].length() < numbers[i + 1].length() &&
                        numbers[i].equals(numbers[i + 1].substring(0, numbers[i].length()))) {
                    // i번째 전화번호가 i+1번째 전화번호의 접두사인 경우
                    // 일관성이 없다.
                    reasonable = false;
                    break;
                }
            }
            // 답 기록
            sb.append(reasonable ? "YES" : "NO").append("\n");
        }
        // 출력
        System.out.print(sb);
    }
}