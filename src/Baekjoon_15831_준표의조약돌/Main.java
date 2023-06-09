/*
 Author : Ruel
 Problem : Baekjoon 15831번 준표의 조약돌
 Problem address : https://www.acmicpc.net/problem/15831
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15831_준표의조약돌;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 산책길에 n개의 조약돌이 놓여있다.
        // 조약돌을 연속하여 줍되, 까만 조약돌은 B개 이하, 하얀 조약돌은 W개 이상 줍고 싶다.
        // 산책 구간 중 가장 긴 길이는?
        //
        // 두 포인터 문제
        // 두 포인터를 사용하여, 두 포인 내의 까만 조약돌을 B개 이하로 하며
        // 얻을 수 있는 최대한의 많은 하얀 조약돌을 포함시켜 산책 구간의 최대 길이를 구한다
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 조약돌
        int n = Integer.parseInt(st.nextToken());
        // b개 이하의 검정 조약돌
        int b = Integer.parseInt(st.nextToken());
        // w개 이상의 하얀 조약돌
        int w = Integer.parseInt(st.nextToken());

        String stones = br.readLine();
        // 두 포인터 내의 하얀 조약돌의 수
        int whiteCount = 0;
        // 검정 조약돌의 수
        int blackCount = 0;
        // 현재까지 찾은 산책 구간의 최대 길이
        int maxLength = 0;
        // 끝 부분 포인터
        int end = -1;
        for (int start = 0; start < stones.length(); start++) {
            // 만약 시작 포인터가 끝 포인터를 넘어섰다면
            // end 포인터를 start 포인터 위치로 이동시키고
            // 두 포인터가 가르키는 돌을 포함시킨다.
            if (end < start) {
                if (stones.charAt(start) == 'B')
                    blackCount++;
                else
                    whiteCount++;
                end = start;
            }

            // end 포인터를 더 증가시킬 수 있는지
            // (= 하얀 조약돌 내지는 b개 이하의 검정 조약돌을 더 추가할 수 있는지)
            // 살펴본다.
            while (end < stones.length() - 1) {
                if (stones.charAt(end + 1) == 'B') {
                    if (blackCount >= b)
                        break;
                    blackCount++;
                } else
                    whiteCount++;
                end++;
            }

            // 검정 조약돌이 b개 이하, 하얀 조약돌이 w개 이상을 만족한다면
            // 현재의 길이가 찾은 산책 구간의 최대 길이를 갱신하는지 보고, 그렇다면 값을 갱신한다.
            if (blackCount <= b && whiteCount >= w)
                maxLength = Math.max(maxLength, end - start + 1);

            // start 포인터가 start + 1로 이동하므로
            // 현재 start가 가르키는 돌을 제외시킨다.
            if (stones.charAt(start) == 'B')
                blackCount--;
            else
                whiteCount--;
        }
        
        // 찾은 산책 구간의 최대 길이 출력
        System.out.println(maxLength);
    }
}