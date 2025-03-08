/*
 Author : Ruel
 Problem : Baekjoon 30237번 합집합
 Problem address : https://www.acmicpc.net/problem/30237
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30237_합집합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n(1<= n <= 50)개의 집합이 주어진다.
        // 각 집합은 최대 50개의 원소를 갖으며, 각 원소는 1보다 크거나 같고, 50보다 작거나 같다.
        // 집합 중 몇 개를 골라, 합집합을 했을 때, 전체 집합의 합집합과 원소의 개수는 다르지만
        // 최대인 합집합의 원소의 개수를 구하라
        //
        // 브루트 포스
        // 각 집합 별로 합집합에 포함되는가 안되는가를 일일이 계산할 경우, 2^50으로 너무너무 경우의 수가 많다.
        // 따라서 원소가 50 종류 이하이므로, 하나씩 각 원소가 포함되지 않는 최대 합집합의 원소 개수를 계산해나간다.
        // 먼저, 모든 원소의 개수를 세고, 해당 원소가 어느 집합에 속해있는지 표시한다.
        // 그 후, 각 원소마다, 해당 원소를 제외하려면, 어느 집합들을 제외해야하는지 살펴보고,
        // 해당 집합들의 원소들을 전체 합집합에서 모두 제거한 후
        // 남아있는 원소의 종류의 개수를 센다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        for (int testCase = 0; testCase < t; testCase++) {
            // n개의 집합
            int n = Integer.parseInt(br.readLine());

            int[][] arrays = new int[n][];
            // 각 원소의 개수 및 포함된 집합
            long[][] totalCounts = new long[51][2];
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                arrays[i] = new int[Integer.parseInt(st.nextToken())];
                // 비트마스크로 포함된 집합을 표시한다.
                long bit = 1L << i;
                for (int j = 0; j < arrays[i].length; j++) {
                    // 포함된 원소의 개수 증가
                    totalCounts[arrays[i][j] = Integer.parseInt(st.nextToken())][0]++;
                    // 비트마스크 표시
                    totalCounts[arrays[i][j]][1] |= bit;
                }
            }

            int max = 0;
            for (int i = 1; i < totalCounts.length; i++) {
                // 전체 합집합에서 원소의 개수가 0개라면 건너뛴다.
                if (totalCounts[i][0] == 0)
                    continue;

                // i 원소의 개수가 0개가 아닐 경우
                // 전체 합집합에서 i 원소를 제거하는 경우를 살펴본다.
                int[] counts = new int[51];
                for (int j = 0; j < arrays.length; j++) {
                    // i 원소가 j 집합에 속해있다면 
                    // j 집합의 원소를 모두 counts에 세어둔다.
                    if (((1L << j) & totalCounts[i][1]) != 0) {
                        for (int k = 0; k < arrays[j].length; k++)
                            counts[arrays[j][k]]++;
                    }
                }
                
                // 전체 합집합에서 i 원소가 포함된 집합들을 제거했을 때
                // 남은 원소의 개수
                int count = 0;
                for (int j = 0; j < totalCounts.length; j++) {
                    // 전체 합집합에서의 원소 수가 
                    // 제거된 집합들에서의 원소 수보다 많다면
                    // 해당 원소는 현재 구하는 합집합에 포함되므로 count 증가
                    if (totalCounts[j][0] > counts[j])
                        count++;
                }
                // max에 count가 최댓값을 갱신했는지 여부 확인
                max = Math.max(max, count);
            }
            // max 값 기록
            sb.append(max).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}