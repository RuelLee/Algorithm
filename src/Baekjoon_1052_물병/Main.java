/*
 Author : Ruel
 Problem : Baekjoon 1052번 물병
 Problem address : https://www.acmicpc.net/problem/1052
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1052_물병;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 물이 1리터씩 담겨있는 n개의 물병이 주어진다.
        // 같은 양의 물이 담겨있는 물병을 한 물병으로 물을 모아
        // 병의 개수를 최종적으로 k개 이하로 만들고자 한다.
        // 이 때 추가해야하는 1리터 물병의 최소 개수는?
        //
        // 그리디 문제
        // 두 물병을 한쪽으로 모으므로, 물병에 담겨있는 물의 양은 항상 2의 제곱이다.
        // 따라서 처음 주어지는 1L 물병의 개수를 모두 2의 제곱 수로 바꿔준다.
        // 용량이 적은 물병부터 살펴나가며
        // 해당하는 물병이 존재한다면, 같은 용량의 물병을 추가해, 상위 물병으로 바꿔가며 물병의 수를 줄여나간다.
        // 위 과정을 총 물병의 수가 k이하가 될 때까지 반복한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 처음 주어지는 물병의 수 n
        int n = Integer.parseInt(st.nextToken());
        // 만들고자하는 물병의 수 k
        int k = Integer.parseInt(st.nextToken());
        
        // bottles[i] = 2^i이 담긴 물병의 수
        int[] bottles = new int[25];
        // 1리터 물병 n개
        bottles[0] = n;

        int sum = 0;
        // 1리터 물병들을 상위 물병들로 교환.
        for (int i = 0; i < bottles.length - 1; i++) {
            bottles[i + 1] += bottles[i] / 2;
            bottles[i] = bottles[i] % 2 == 0 ? 0 : 1;
            sum += bottles[i];
        }

        int count = 0;
        for (int i = 0; i < bottles.length - 1; i++) {
            // 물병의 수가 k 이하가 되었다면 종료
            if (sum <= k)
                break;
            
            // 2^i의 물병이 하나 존재한다면
            if (bottles[i] == 1) {
                // 해당하는 물병을 하나 더 추가하여
                count += Math.pow(2, i);
                // 2^(i+1)의 물병의 개수를 추가한다.
                bottles[i + 1]++;
                // 2^i 물병을 사라지며
                bottles[i] = 0;
                // 총 물병의 수는 변화 없다.
            } else if (bottles[i] == 2) {
                // 만약 2^i에 해당하는 물병이 2개 존재한다면
                // 추가 없이 상위 물병으로 교환.
                bottles[i + 1]++;
                // 2^i이 담긴 물병은 사라지며
                bottles[i] = 0;
                // 총 물병의 수는 1 감소한다.
                sum--;
            }
        }
        // 추가한 물병의 수 출력
        System.out.println(count);
    }
}