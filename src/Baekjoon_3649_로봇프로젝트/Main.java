/*
 Author : Ruel
 Problem : Baekjoon 3649번 로봇 프로젝트
 Problem address : https://www.acmicpc.net/problem/3649
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3649_로봇프로젝트;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // x센티미터의 구멍을 2개의 레고로 막고 싶다.
        // 레고 조각의 수 n이 주어지고 각 레고의 나노미터 크기들이 주어질 때
        // 2개의 레고로 구멍을 정확히 막을 수 있는지 찾고,
        // 그렇다면 yes l1 l2
        // 그렇지 않다면 danger를 출력한다
        // 복수의 답이 존재한다면  | l1 - l2 |의 크기가 큰 값으로 한다.
        //
        // 두 포인터 문제
        // 두 개의 단위가 다름에만 유의하고 두 포인터를 사용하여 답을 찾아준다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        StringBuilder sb = new StringBuilder();
        // 테스트케이스가 계속 주어진다.
        // 입력이 있는 동안 계속 수행한다.
        while (input != null) {
            // 구멍의 크기
            int x = Integer.parseInt(input) * 10000000;
            // 레고의 수
            int n = Integer.parseInt(br.readLine());
            // 레고들
            int[] legos = new int[n];
            for (int i = 0; i < n; i++)
                legos[i] = Integer.parseInt(br.readLine());
            // 오름차순으로 정렬
            Arrays.sort(legos);

            // 앞의 포인터
            int i = 0;
            // 두의 포인터
            int j = legos.length - 1;
            // 두 포인터가 교차하기 전까지
            while (i < j) {
                // 만약 두 레고 크기의 합이 x보다 크다면 뒤 포인터를 하나씩 앞으로 당긴다.
                while (j > i && legos[i] + legos[j] > x)
                    j--;

                // 일치하는 크기를 찾았다면 종료.
                if (legos[i] + legos[j] == x)
                    break;
                // 그렇지 않다면 i를 하나 증가시키고 위의 과정을 반복한다.
                i++;
            }

            // 만약 두 포인터가 교차하지 않았고, 두 포인터들이 가르키는 값들의 합이
            // 구멍의 크기와 일치한다면
            // 답안 출력
            if (i < j && legos[i] + legos[j] == x)
                sb.append("yes ").append(legos[i]).append(" ").append(legos[j]).append("\n");
            // 그렇지 않다면 danger 출력
            else
                sb.append("danger").append("\n");
            // 다음 테스트케이스를 위해 새로운 줄을 입력받는다.
            input = br.readLine();
        }
        // 전체 답안 출력.
        System.out.println(sb);
    }
}