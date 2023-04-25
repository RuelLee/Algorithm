/*
 Author : Ruel
 Problem : Baekjoon 22984번 반짝반짝 2
 Problem address : https://www.acmicpc.net/problem/22984
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_22984_반짝반짝2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 전구가 켜질 확률이 주어진다.
        // 각각의 전구들 사이에는 추가 전구가 있어서, 양쪽의 전구들 중 하나만 켜졌을 때, 불이 들어온다고 한다.
        // 전체 전구들에서 불이 들어오는 전구의 기댓값을 구하라
        //
        // 기댓값의 선형성...? 문제?
        // 먼저 일반 전구에 대해서는 각각의 전구들이 켜질 확률을 더한다.
        // 그 후, 추가 전구는 양쪽의 전구들 중 하나만 켜질 때, 추가 전구 1개가 켜지는 것이므로
        // 왼쪽이 켜지고, 오른쪽이 꺼질 때
        // 왼쪽이 꺼지고, 오른쪽이 켜질 때 확률을 곱해 더한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 전체 전구의 수
        int n = Integer.parseInt(br.readLine());
        // 각각의 전구가 들어올 확률
        double[] rates = Arrays.stream(br.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();

        // 먼저 전체 전구가 켜질 확률을 더한다.
        // 이는 일반 전구들이 불이 들어올 기댓값이 된다.
        double sum = Arrays.stream(rates).sum();

        // 그 후, 전구들 사이사이에 있는 추가 전구들에 대한 기대값을 계산해서 더한다.
        // i번째 전구가 켜지고, i + 1번째 전구가 꺼질 확률 +
        // i번째 전구가 꺼지고, i + 1번째 전구가 켜질 때 확률
        // * 1개의 전구
        for (int i = 0; i < rates.length - 1; i++)
            sum += rates[i] * (1 - rates[i + 1]) + (1 - rates[i]) * rates[i + 1];

        // 전체 전구들에서 켜지는 전구들의 기댓값을 출력한다.
        System.out.println(sum);
    }
}