/*
 Author : Ruel
 Problem : Jungol 2024번 타일밟기
 Problem address : https://jungol.co.kr/problem/2024
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_2024_타일밟기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 3000개 이하의 타일에 100만 이하의 자연수 값이 적혀있다.
        // 타일에 적힌 숫자가 일정한 자연수만큼 증가하도록 밟고자 한다.
        // 연속적으로 밟는 타일의 수는 최소 3개 이상여야한다.
        // 연속적으로 밟는 타일에 적힌 자연수 합의 최대인 것은?
        //
        // 브루트 포스 문제
        // 개수가 3000개 이하로 주어지므로
        // 일일이 두 쌍을 만들어, 3번째 이상의 타일이 존재하는지 확인하고 값을 더해나가며 찾는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 타일
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 타일에 적힌 수
        int[] tiles = new int[n];
        boolean[] existences = new boolean[1_000_001];
        for (int i = 0; i < n; i++)
            existences[tiles[i] = Integer.parseInt(st.nextToken())] = true;

        // 연속으로 밟는 타일들의 최대 합
        int max = 0;
        // 첫번째 타일
        for (int i = 0; i < n; i++) {
            // 두번째 타일
            for (int j = i + 1; j < n; j++) {
                // 간격
                int diff = tiles[j] - tiles[i];
                // 세번째 타일이 존재하는가
                if (tiles[j] + diff <= 1_000_000 && existences[tiles[j] + diff]) {
                    // 존재한다면 합 계산
                    int sum = tiles[i] + tiles[j];
                    // 세번째 타일 이후는 반복문으로 처리
                    int next = tiles[j] + diff;
                    while (next <= 1_000_000 && existences[next]) {
                        sum += next;
                        next += diff;
                    }
                    // 최댓값 갱신
                    max = Math.max(max, sum);
                }
            }
        }
        // 답 출력
        System.out.println(max);
    }
}