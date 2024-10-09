/*
 Author : Ruel
 Problem : Baekjoon 12945번 재미있는 박스 정리
 Problem address : https://www.acmicpc.net/problem/12945
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12945_재미있는박스정리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int[] boxes;

    public static void main(String[] args) throws IOException {
        // n개의 박스와 각각의 크기가 주어진다.
        // 한 박스는 자신의 크기보다 두배 이상되는 박스에 담을 수 있다.
        // 다른 박스를 담은 박스는 다른 박스에 담을 수 없다.
        // 박스를 정리하여 보이는 박스의 개수를 최소화하고자 할 때, 그 수는?
        //
        // 그리디 문제
        // 먼저 박스를 입력받아 정렬한다.
        // 그 후, 가장 큰 박스에, 담을 수 있는 가장 큰 박스를 담는다.
        // 그러되, 주의할 점으로 박스의 개수가 n일 경우, 모든 박스를 잘 담을 경우
        // n이 짝수일 경우 n / 2 , n이 홀수일 경우, n/2 +1개가 된다.
        // 따라서 담기는 박스의 위치는 n / 2 -1부터 살펴본다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 박스
        int n = Integer.parseInt(br.readLine());
        boxes = new int[n];
        for (int i = 0; i < boxes.length; i++)
            boxes[i] = Integer.parseInt(br.readLine());
        // 정렬
        Arrays.sort(boxes);
        
        // 담는 박스의 위치
        int outBoxIdx = n - 1;
        // 담긴 박스의 수
        int count = 0;
        
        // 담기는 박스의 위치 i는 n / 2 - 1부터 살펴본다.
        for (int i = n / 2 - 1; i >= 0; i--) {
            // i번째 박스가 담길 수 있는 경우
            if (boxes[i] * 2 <= boxes[outBoxIdx]) {
                // 담는 박스의 위치 하나 감소
                outBoxIdx--;
                // 담긴 박스의 수 증가
                count++;
            }
        }

        // 전체 개수 n개에서 담긴 박스의 수만큼이 사라진 개수의 박스가 보인다.
        System.out.println(n - count);
    }
}