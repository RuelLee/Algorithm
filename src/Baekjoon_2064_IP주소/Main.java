/*
 Author : Ruel
 Problem : Baekjoon 2064번 IP 주소
 Problem address : https://www.acmicpc.net/problem/2064
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2064_IP주소;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 네트워크 주소가 주어질 때
        // 해당 네트워크의 주소와 마스크를 구하라
        // 답이 여러개인 경우 가장 작은(포함되는 ip 주소가 가장 적은) 네트워크를 구한다.
        //
        // 비트마스킹 문제
        // ip주소는 앞의 32-m개의 자리가 임의의 0 혹은 1로 채워져있고, 나머지 m개의 자리가 0으로 채워진 형태다.
        // 마스크는 앞의 32-m개의 자리가 1로 채워져있고, 뒤의 m개의 자리가 0으로 채워진다.
        // 따라서 ip주소끼리 & 연산을 하여주면, 전체 아이피에 모두 1인 비트만 골라낼 수 있다.
        // 네트워크 마스크는 0인 비트에 내가 1을 줄 수 있는 값들이다.
        // 따라서 네트워크 주소 + 네트워크 마스크를 반전시킨 값이 해당 네트워크의 마지막 주소 값이다.
        // 모든 아이피가 해당 주소에 포함되는 값을 찾는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[][] answer = new int[2][4];
        // 하나의 값인 경우
        // 다른 값을 사용할 필요가 없으므로 비트마스크를 1111 1111을 줘버릴 수 있다.
        // 여러개의 값인 경우, 하위 주소들은 0 ~ 255까지의 값을 모두 가져야하므로 이하는 0을 줘야한다.
        boolean[] oneValue = new boolean[4];
        Arrays.fill(oneValue, true);
        int[] max = new int[4];

        // 처음 ip 값
        String[] input = br.readLine().split("\\.");
        for (int i = 0; i < 4; i++)
            max[i] = answer[0][i] = Integer.parseInt(input[i]);

        // 값 처리
        for (int i = 0; i < n - 1; i++) {
            input = br.readLine().split("\\.");
            // 문자열로 주어지는 주소는 . 3개로 4부분으로 구성
            for (int j = 0; j < 4; j++) {
                // 각 부분에 따라
                int num = Integer.parseInt(input[j]);
                // 각 부분의 최댓값
                max[j] = Math.max(max[j], num);
                // 값이 하나인지 여부 체크
                if (num != answer[0][j])
                    oneValue[j] = false;
                // & 연산으로 공통된 1 비트를 걸러냄
                answer[0][j] &= num;
            }
        }

        // 네트워크 주소에서 하나의 값이 아닌 부분이 발견된다면
        // 이하는 모두 0으로 채움
        for (int i = 0; i < 3; i++) {
            if (!oneValue[i])
                Arrays.fill(answer[0], i + 1, 4, 0);
        }

        // 네트워크 마스크
        for (int i = 0; i < 4; i++) {
            // 하나의 값인 경우 255
            if (oneValue[i])
                answer[1][i] = 255;
            else {
                // 그 외의 경우
                for (int j = 0; j <= 8; j++) {
                    // 뒤에서부터 1을 채워나가며
                    int mask = (1 << j) - 1;
                    answer[1][i] = (255 - mask);
                    // 해당 값과 네트워크 주소와 | 연산을 한 값이 모든 아이피 범위를 포함하고 있다면
                    // 반복문 종료
                    if ((answer[0][i] | mask) > max[i])
                        break;
                }
                break;
            }
        }
        // 답안 작성
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < answer.length; i++) {
            sb.append(answer[i][0]);
            for (int j = 1; j < 4; j++)
                sb.append(".").append(answer[i][j]);
            sb.append("\n");
        }
        // 출력
        System.out.print(sb);
    }
}