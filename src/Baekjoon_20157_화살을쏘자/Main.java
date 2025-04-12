/*
 Author : Ruel
 Problem : Baekjoon 20157번 화살을 쏘자!
 Problem address : https://www.acmicpc.net/problem/20157
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20157_화살을쏘자;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 2차원 공간에 n개의 풍선의 위치가 주어진다.
        // (0, 0)에서 화살 한 발을 쏘는데,
        // 화살은 무한히 날아가며, 도중에 만나는 풍선을 모두 터뜨린다.
        // 최대로 터뜨릴 수 있는 풍선의 수는?
        //
        // 유클리드 호제법, 해시 맵 문제
        // (0, 0)에서 쏘는 화살은 직선이며, 이 직선에 위치하는 풍선이 몇 개인지 세면 된다.
        // 이 때, 기울기를 구한다면 사분면의 구분도 안되고, 소수점에 따른 오차가 발생할 수도 있다.
        // 따라서 유클리드 호제법을 통해, x와 y의 최대공약수를 구해, 최소값으로 해당 기울기를 대신하도록 한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 풍선
        int n = Integer.parseInt(br.readLine());
        
        HashMap<Integer, HashMap<Integer, Integer>> hashMap = new HashMap<>();
        int max = 0;
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            // 풍선은 좌표
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            
            // 최대공약수를 구해
            int gcd = getGCD(x, y);
            // 각각 나눠준다.
            x /= gcd;
            y /= gcd;

            // 해당하는 (x / gcd, y / gcd)값을 바탕으로 해시 맵에 추가.
            if (!hashMap.containsKey(x))
                hashMap.put(x, new HashMap<>());
            if (!hashMap.get(x).containsKey(y))
                hashMap.get(x).put(y, 1);
            else
                hashMap.get(x).put(y, hashMap.get(x).get(y) + 1);
            
            // 해당 직선 상의 풍선의 개수가 최대값을 갱신하는지 확인
            max = Math.max(max, hashMap.get(x).get(y));
        }
        // 답 출력
        System.out.println(max);
    }

    // a와 b의 최대공약수를 구한다.
    static int getGCD(int a, int b) {
        // 두 수가 음수일 수도 있으므로 절대값으로 대신 계산한다.
        int max = Math.max(Math.abs(a), Math.abs(b));
        int min = Math.min(Math.abs(a), Math.abs(b));
        while (min > 0) {
            int temp = max % min;
            max = min;
            min = temp;
        }
        // 최대공약수 반환.
        return max;
    }
}