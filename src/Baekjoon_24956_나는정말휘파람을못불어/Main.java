/*
 Author : Ruel
 Problem : Baekjoon 24956번 나는 정말 휘파람을 못 불어
 Problem address : https://www.acmicpc.net/problem/24956
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_24956_나는정말휘파람을못불어;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static final int LIMIT = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        // WHEE는 유사 휘파람 문자열이다
        // 유사 휘파람 문자열 뒤에 E를 붙인 것 또한 유사 휘파람 문자열이다.
        // 문자열 s가 주어질 때, 유사 휘파람 문자열인 부분 수열의 개수를 구하라
        //
        // DP 문제
        // S의 문자들을 순서대로 살펴보며, 부분 문자열의 수를 계산하면 된다.
        // E가 연속하여 계속 붙더라도 유사 휘파람 문자열이 되므로 이를 고려해야한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 문자열의 길이 n, 문자열 s
        int n = Integer.parseInt(br.readLine());
        String s = br.readLine();
        
        // dp
        int[] whistle = new int[4];
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                // W일 경우 whistle[0]에 1 추가
                case 'W' -> whistle[0]++;
                case 'H' -> {       // H일 경우, whistle[1]에 whistle[0] 개수만큼 추가
                    whistle[1] += whistle[0];
                    whistle[1] %= LIMIT;
                }
                case 'E' -> {
                    // E일 경우
                    // 이 E가 1, 2, 3+번째 E가 될 수 있다.
                    // 3+ 번째 E인 경우, whistle[3]의 개수만큼 추가되고
                    // 2번째 E인 경우, whistle[2]만큼
                    // 1번째 E인 경우, whistle[1]만큼 개수가 추가된다.
                    
                    // 3+번째 E인 경우
                    // whistle[3]에 그대로 누적시킨다. 따라서 2배.
                    whistle[3] *= 2;
                    whistle[3] %= LIMIT;
                    // 2번째 E인 경우
                    // 1E의 개수만큼 누적
                    whistle[3] += whistle[2];
                    whistle[3] %= LIMIT;
                    // 1번째 E는 H의 개수만큼 누적
                    whistle[2] += whistle[1];
                    whistle[2] %= LIMIT;
                }
            }
        }
        // 마지막 E의 개수가
        // 가능한 모든 유사 휘파람 문자열인 부분 수열의 개수
        System.out.println(whistle[3]);
    }
}