/*
 Author : Ruel
 Problem : Baekjoon 13273번 로마숫자
 Problem address : https://www.acmicpc.net/problem/13273
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13273_로마숫자;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        // 로마 숫자를 -> 아라비아 숫자
        // 아라비아 숫자를 -> 로마 숫자로 변환하라
        // M은 1000, CM은 900, D는 500, CD는 400, C는 100, XC는 90, L은 50, XL은 40, X는 10, IX는 9, V는 5, IV는 4, I는 1이다.
        //
        // 구현 문제
        // 먼저, 주어진 아바리아 숫자와 로마 수를 매칭해둔다.
        // 그 후, 아라비아 수가 들어올 경우
        // 로마 수를 큰 수부터 살펴보면서, 로마수보다 큰 경우, 해당 로마수를 적고, 해당 값만큼을 아라비아 수에서 빼준다.
        // 위 과정을 아라비아 수가 0이 될 때까지 반복
        // 로마 수가 들어온 경우
        // 앞에서부터 두글자씩 살펴보고, 해당하는 두 글자가 없다면, 한글자만 읽어 값으로 누적시킨다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        // 로마 수와 그 값
        String[] romeNums = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] values = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        // 해쉬맵을 통해 로마 수와 아라비아 수를 매칭
        HashMap<String, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < romeNums.length; i++)
            hashMap.put(romeNums[i], values[i]);

        for (int testCase = 0; testCase < t; testCase++) {
            String input = br.readLine();
            // 아라비아 수가 들어온 경우
            if (input.charAt(0) >= '0' && input.charAt(0) <= '9') {
                int num = Integer.parseInt(input);
                // 로마 수를 큰 수부터 살펴보며
                for (int i = 0; i < values.length; i++) {
                    if (values[i] > num)
                        continue;

                    // 해당하는 로마수보다 num이 같거나 큰 경우, 아리비아 수가 더 작아질 때까지
                    // 해당 로마 수를 적으며 빼준다.
                    while (num >= values[i]) {
                        sb.append(romeNums[i]);
                        num -= values[i];
                    }
                }
                sb.append("\n");
            } else {
                // 로마 수가 들어온 경우
                int sum = 0;
                for (int i = 0; i < input.length(); ) {
                    // 뒤에 한 글자가 더 있고,
                    // 해당하는 수가 있는 경우
                    // 해당 아라비아 값을 누적하고, +2 위치로 이동
                    if (i + 1 < input.length() && hashMap.containsKey(input.substring(i, i + 2))) {
                        sum += hashMap.get(input.substring(i, i + 2));
                        i += 2;
                    } else {
                        // 그 외의 경우
                        // 한 글자에 해당하는 수만큼 누적시키고 +1 위치로 이동
                        sum += hashMap.get(input.substring(i, i + 1));
                        i += 1;
                    }
                }
                // 답 기록
                sb.append(sum).append("\n");
            }
        }
        // 전체 답 출력
        System.out.println(sb);
    }
}