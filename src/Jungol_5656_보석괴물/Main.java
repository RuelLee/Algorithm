/*
 Author : Ruel
 Problem : Jungol 5656번 보석 괴물
 Problem address : https://jungol.co.kr/problem/5656
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_5656_보석괴물;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        // 보석 괴물의 몸에 0 ~ 9의 숫자가 적혀있다.
        // 구간의 합과 길이가 일치하는 경우, 소원을 이루어준다고 한다.
        // 소원을 이루어주는 경우의 수를 구하라
        //
        // 누적합, 조합 문제
        // 누적합을 구하되, 처음부터 현재까지의 길이 - 수의 누적합으로 구한다
        // 해당 값이 같은 두 값을 선택하여 구간을 선택하면 해당 구간의 합과 길이가 일치하게 된다.
        // 서로 같은 값을 뽑아 조합으로 경우의 수를 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 길이 n의 괴물
        int n = Integer.parseInt(br.readLine());
        char[] input = br.readLine().toCharArray();

        // 누적합
        // 을 구하되 길이 - 수의 누적합으로 값을 구한다.
        int[] psums = new int[n + 1];
        for (int i = 1; i <= n; i++)
            psums[i] = psums[i - 1] + 1 - input[i - 1] + '0';

        // 값의 개수를 센다.
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 1; i <= n; i++)
            hashMap.put(psums[i], hashMap.getOrDefault(psums[i], 0) + 1);

        // 값이 0이 경우. 길이가 1, 값이1인 상태로 단일로도 소원을 이룰 수 있는 경우.
        long ans = hashMap.getOrDefault(0, 0);
        // 값이 같은 두 곳을 뽑는 경우의 수를 합산한다.
        for (int key : hashMap.keySet())
            ans += (long) hashMap.get(key) * (hashMap.get(key) - 1) / 2;
        // 답 출력
        System.out.println(ans);
    }
}