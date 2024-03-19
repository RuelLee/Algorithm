/*
 Author : Ruel
 Problem : Baekjoon 1512번 주기문으로 바꾸기
 Problem address : https://www.acmicpc.net/problem/1512
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1512_주기문으로바꾸기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 주기문은
        // 주기의 길이가 p라면, 0 ≤ i ≤ L-P-1 인 모든 i 대해서
        //i+p에 위치에 있는 문자와 같을 때를 말한다. 예를 들어, "CATCATC", "CATCAT", "ACTAC", "ACT"는 모두 길이가 3인 주기문이다.
        // 문자열 m이 주어질 때
        // m보다 작거나 같은 주기문으로 바꿀 때, 바꾸는 최소 문자열의 개수는?
        //
        // 브루트포스
        // m이하의 주기문으로 만들 때 바꿔야하는 문자의 개수를 모두 센다.
        // 각 주기별 위치에 있는 알파벳들을 각각 세고
        // 그 합에서 가장 큰 값을 하나 뺀 값만큼의 문자열들을 바꿔야한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // m이하의 주기문으로 만들고자 한다.
        int m = Integer.parseInt(br.readLine());
        // 문자열
        String s = br.readLine();

        // 문자열의 길이는 최대 3000이므로 3000보다 큰 값이 답으로 올 수 없다.
        int answer = 3000;
        // 각 주기에 위치하는 알파벳의 개수를 센다.
        int[] counts = new int[4];
        // 길이가 length인 주기문
        for (int length = 1; length <= m; length++) {
            int sum = 0;

            // length인 주기에서
            // 0 ~ length -1까지 시작점이 존재하고 각 위치에 해당하는 알파벳들의 개수를 센다.
            for (int i = 0; i < length; i++) {
                for (int j = 0; i + j * length < s.length(); j++) {
                    switch (s.charAt(i + j * length)) {
                        case 'A' -> counts[0]++;
                        case 'C' -> counts[1]++;
                        case 'G' -> counts[2]++;
                        case 'T' -> counts[3]++;
                    }
                }

                // 주기문의 길이가 length이며
                // 시작점이 i인 알파벳들의 개수의 합에서
                // 가장 큰 값을 뺀 만큼의 문자들을 가장 큰 값에 해당하는 알파벳으로 바꿔야한다.
                int partSum = 0;
                int partMax = 0;
                for (int count : counts) {
                    partSum += count;
                    partMax = Math.max(partMax, count);
                }
                // 바꿔야하는 문자의 개수를 누적.
                sum += partSum - partMax;
                Arrays.fill(counts, 0);
            }
            // sum은 length를 주기로 갖는 주기문으로 만들었을 때
            // 바꿔야하는 문자의 최소 개수
            // 이 개수가 answer의 최소값을 갱신하는지 확인한다.
            answer = Math.min(answer, sum);
        }

        // m이하의 주기문으로 바꿨을 때
        // 바꿔야하는 최소 문자 수는 answer
        System.out.println(answer);
    }
}