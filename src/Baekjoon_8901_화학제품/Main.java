/*
 Author : Ruel
 Problem : Baekjoon 8901번 화학 제품
 Problem address : https://www.acmicpc.net/problem/8901
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_8901_화학제품;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // a, b, c 세 개의 화학물질이 있고 이 물질들을 섞어
        // ab, bc, ca의 혼합물을 얻을 수 있으며 각 가치들이 주어진다
        // 혼합물들로 얻을 수 있는 최대 가치는 얼마인가
        //
        // 브루트 포스 문제
        // 모든 정수들이 최대 1000까지 주어진다
        // 처음에는 메모이제이션 문제인 줄 알고 풀려했으나, 1000 짜리가 3개라 메모리 초과가 났다.
        // 다음으로 생각해보면 ab, bc의 개수가 정해지면, ca는 개수를 생각할 필요없이 남은 재료 모두를 ca로 만드는 것이 좋다
        // 따라서 ab, bc의 두 가지만 고려하는 것으로 범위를 좁힐 수 있다.
        // ab, bc에 대해 모든 가짓수들을 살펴보며, 남은 재료들에 대해서는 ca를 모두 채워 가치를 계산해나가는 방법을 취했다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 주어지는 화학 물질의 양
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            
            // 혼합물의 가치
            int[] values = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            
            // 최대 얻을 수 있는 가치
            int max = 0;
            // a, b 중 더 적은 양만큼 ab 혼합물을 만들 수 있다.
            for (int i = 0; i <= Math.min(a, b); i++) {
                // 마찬가지로 b, c 중 더 적은 양만큼 bc 혼합물을 만들 수 있다.
                for (int j = 0; j <= Math.min(b, c); j++) {
                    // 만약 두 혼합물의 겹치는 b 물질이 양을 초과한다면 반복문 종료
                    if (i + j > b)
                        break;
                    // 그렇지 않다면 기존 최대 가치와
                    // i만큼의 ab 혼합물, j만큼의 bc 혼합물
                    // 그리고 남은 a, b로 모두 ca 혼합물을 만들었을 때의 가치와 비교한다.
                    max = Math.max(max,
                            i * values[0] + j * values[1] + Math.min(a - i, c - j) * values[2]);
                }
            }
            // 구해진 최대 혼합물 가치를 기록한다.
            sb.append(max).append("\n");
        }
        // 전체 답안 출력.
        System.out.print(sb);
    }
}