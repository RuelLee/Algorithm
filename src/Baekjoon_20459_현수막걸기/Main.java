/*
 Author : Ruel
 Problem : Baekjoon 30459번 현수막 걸기
 Problem address : https://www.acmicpc.net/problem/30459
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20459_현수막걸기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 말뚝과 m개의 깃대가 있다.
        // n개의 말뚝 중 두 개를 고른 후, 정중앙에 깃대를 세워 삼각형 모양으로 현수막을 건다.
        // 현수막의 최대 넓이는 r로 정해져있다.
        // 걸 수 있는 현수막의 최대 넓이는?
        //
        // 이분 탐색
        // n은 최대 2000, m개의 최대 40000으로 주어진다.
        // 따라서 모든 말뚝, 모든 깃대에 대해서 계산은 불가능.
        // 말뚝은 모든 말뚝에 대해 하되, 너비에 관해서는 방문체크하여 중복 계산이 없도록 한다.
        // 깃대에 대해서는 오름차순 정렬하여, 이분탐색을 통해 연산을 줄인다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 말뚝, m개의 깃대, 최대 넓이 r
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());
        
        // 말뚝
        int[] piles = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < piles.length; i++)
            piles[i] = Integer.parseInt(st.nextToken());
        Arrays.sort(piles);
        
        // 깃대
        int[] poles = new int[m];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < poles.length; i++)
            poles[i] = Integer.parseInt(st.nextToken());
        Arrays.sort(poles);
        
        // 방문 체크
        HashSet<Integer> hashSet = new HashSet<>();
        double max = 0;
        for (int i = 0; i < piles.length; i++) {
            for (int j = i + 1; j < piles.length; j++) {
                // 이미 계산했던 너비라면 건너뜀.
                if (hashSet.contains(piles[j] - piles[i]))
                    continue;

                // 이분 탐색으로
                // 가능한 가장 긴 깃대를 찾는다.
                int start = 0;
                int end = m - 1;
                while (start <= end) {
                    int mid = (start + end) / 2;
                    if ((piles[j] - piles[i]) * (double) poles[mid] / 2 > r)
                        end = mid - 1;
                    else
                        start = mid + 1;
                }

                // 가장 작은 깃대조차 최대 넓이 r을 넘을수도 있다.
                // 따라서 사용가능한 깃대인지 확인 후
                // 최대값 갱신 여부 확인.
                if (end >= 0)
                    max = Math.max(max, (piles[j] - piles[i]) * (double) poles[end] / 2);
                
                // 너비에 대한 방문 체크
                hashSet.add(piles[j] - piles[i]);
            }
        }
        // max가 초기값이라면 현수막을 걸지 못한다. 따라서 -1 출력
        // 그 외의 경우 max를 소수점 첫째자리까지 출력
        System.out.println(max == 0 ? -1 : String.format("%.1f", max));
    }
}