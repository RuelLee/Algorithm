/*
 Author : Ruel
 Problem : Baekjoon 5624번 좋은 수
 Problem address : https://www.acmicpc.net/problem/5624
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5624_좋은수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수로 이루어진 수열 A가 주어진다.
        // i번째 수가 그 앞에 있는 수 세 개의 합으로 나타낼 수 있을 때, 그 수를 좋다고 한다.
        // (같은 위치에 있는 수를 반복하여 더해도 된다.)
        // 수열이 주어질 때, 좋은 수의 개수는?
        //
        // 해쉬 맵
        // n이 최대 5000으로 주어지기 때문에 세 수를 모두 돌리면 5000 ^ 3으로 시간 내 해결이 불가능.
        // a + b + c = k를 a + b = k - c로 바꿔 계산한다.
        // a + b를 해쉬맵으로 저장해두고, k - c는 5000 ^ 2으로 계산이 가능하다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 수
        int n = Integer.parseInt(br.readLine());
        
        // 수열
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] array = new int[n];
        for (int i = 0; i < n; i++)
            array[i] = Integer.parseInt(st.nextToken());

        int count = 0;
        HashSet<Integer> sum = new HashSet<>();
        for (int i = 0; i < array.length; i++) {
            boolean found = false;
            // array[i] - array[j]가 sum에 포함되어있는지 확인
            for (int j = i - 1; j >= 0 && !found; j--) {
                if (sum.contains(array[i] - array[j]))
                    found = true;
            }
            // 그러한 수를 찾았다면 count 증가
            count += found ? 1 : 0;
            // array[i]와 앞의 한 수를 더한 합을 sum에 저장
            for (int j = 0; j <= i; j++)
                sum.add(array[i] + array[j]);
        }
        // 답 출력
        System.out.println(count);
    }
}