/*
 Author : Ruel
 Problem : Baekjoon 25970번 현대 모비스 에어 서스펜션
 Problem address : https://www.acmicpc.net/problem/25970
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25970_현대모비스에어서스펜션;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) throws IOException {
        // 차고의 높고, 낮음을 판단하는 2진 데이터가 b개(최대 500개)씩 주어진다.
        // 각 데이터는 3개 이상 50개 이하의 비트로 이루어져있따.
        // 실시간 데이터 n개(최대 1000개)가 주어진다.
        // 실시간 데이터의 길이는 최대 250이며, 판단 데이터보다 같거나 길다.
        // 실시간 데이터에 포함된 높고 낮음의 데이터 개수를 세어
        // 다음 차고를 얼마나 조정해야할지 출력하라
        //
        // 비트마스킹, 해쉬 셋 문제
        // 판단 데이터가 최대 길이 50이며, 1혹은 0 값이므로 long 타입으로 표현이 가능하다.
        // 따라서 판단 데이터들을 모두 long으로 바꿔 해쉬셋에 담는다.
        // 단, 수와 다르게 앞에 0이 올 수도 있으므로, 길이 정보도 함께 담는다.
        // 그 후, 실시간 데이터에서 가능한 길이들을 모두 만들어 판단 데이터가 몇 개 포함되어있는지 센다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // b쌍의 판단 데이터
        int b = Integer.parseInt(br.readLine());
        HashSet<Long> lows = new HashSet<>();
        for (int i = 0; i < b; i++)
            lows.add(stringToLong(br.readLine()));
        HashSet<Long> highs = new HashSet<>();
        for (int i = 0; i < b; i++)
            highs.add(stringToLong(br.readLine()));
        
        // n개의 실시간 데이터
        int n = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            String data = br.readLine();
            // 차고의 높이
            int c = 0;
            for (int j = 0; j < data.length() - 2; j++) {
                long bit = 0;
                // 최저 길이는 3이므로, 처음 두 비트에 대해 값을 미리 만들어둔다.
                for (int k = j; k < j + 2; k++) {
                    if (data.charAt(k) == '1')
                        bit |= (1L << (k - j));
                }
                // 3번째 비트부터 값을 추가하며, 해당 비트가 판단 데이터에 포함되는지 판단한다.
                for (int k = j + 2; k < Math.min(j + 50, data.length()); k++) {
                    if (data.charAt(k) == '1')
                        bit |= (1L << (k - j));

                    if (lows.contains(bit * 51 + (k - j + 1)))
                        c--;
                    else if (highs.contains(bit * 51 + (k - j + 1)))
                        c++;
                }
            }

            // 차고가 높다면 c만큼 낮추고
            if (c > 0)
                sb.append("LOW ").append(c);
            else if (c < 0)     // 낮다면 c만큼 높이고
                sb.append("HIGH ").append(-c);
            else        // 0이라면 GOOD을 기록
                sb.append("GOOD");
            sb.append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }

    // 문자열을 long 타입 수로 바꾼다.
    static long stringToLong(String s) {
        long sum = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1')
                sum |= (1L << i);
        }

        // 길이가 최대 50이기 때문에
        // 길이 정보를 담기 위해 기존 비트마스크에 51를 곱한 후
        // 길이를 더한다.
        return sum * 51 + s.length();
    }
}