/*
 Author : Ruel
 Problem : Baekjoon 12931번 두 배 더하기
 Problem address : https://www.acmicpc.net/problem/12931
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12931_두배더하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 0으로 채워진 길이 n의 배열 a와, 같은 길이의 배열 b가 주어진다.
        // 두 개의 명령이 주어진다
        // 1. 배열에 있는 값을 하나 증가 시킨다.
        // 2. 배열에 있는 모든 값을 두 배 시킨다.
        // 최소의 명령으로 a배열을 b배열로 만들려고 할 때
        // 명령의 필요 개수를 구하라
        //
        // 그리디한 문제
        // 두 배 명령을 사용할 수 있을 때는 무조건 사용해야 명령의 개수를 줄일 수 있다.
        // 따라서 b 배열의 상태부터 하나씩 따져가며
        // 모든 수가 짝수라면 2번 명령을 사용했다고 생각하고 모든 수를 나누기 2를 한다
        // 만약 하나라도 홀수가 있다면 1번 명령을 사용한했다 생각하고 그 수를 1 감소시킨다.
        // 이러한 작업을 반복하며 그 횟수를 계산한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        // b배열
        int[] array = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 명령의 사용 개수를 센다.
        int count = 0;
        // 배열의 모든 수가 0이 되기 전까지.
        while (Arrays.stream(array).sum() != 0) {
            // 모든 수가 짝수인지 확인한다.
            boolean allEvenNumbers = true;
            for (int i = 0; i < array.length; i++) {
                // 하나라도 짝수가 아니면
                if (array[i] % 2 == 1) {
                    // 해당 수를 감소시킨다.
                    array[i]--;
                    allEvenNumbers = false;
                    break;
                }
            }

            // 만약 모든 수가 짝수라면
            if (allEvenNumbers) {
                // 모든 수를 나누기 2 해준다.
                for (int i = 0; i < array.length; i++)
                    array[i] /= 2;
            }
            // 명령의 개수 하나 증가.
            count++;
        }
        // 총 사용한 명령의 개수를 출력한다.
        System.out.println(count);
    }
}