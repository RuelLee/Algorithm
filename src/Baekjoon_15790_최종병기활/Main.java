/*
 Author : Ruel
 Problem : Baekjoon 15790번 최종병기 활
 Problem address : https://www.acmicpc.net/problem/15790
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15790_최종병기활;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n 길이의 원형 고무줄이 주어진다.
        // m개의 홈의 위치가 주어지며, 해당 부분에서 고무줄을 자를 수 있다.
        // 잘라진 고무줄들을 k개 겹칠 때, 최소 길이 중 가장 긴 길이는?
        //
        // 이분 탐색 문제
        // 이분 탐색을 통해 최소 길이 이상을 k개 이상 만들 수 있는지 계산한다.
        // 시작 위치에 따라 가능할 수도, 가능하지 않을 수도 있기 때문에
        // 모든 홈에 대해서 시작하는 경우를 고려해야한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 길이 n, m개의 홈, k겹
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 홈의 위치
        int[] grooves = new int[m];
        for (int i = 0; i < grooves.length; i++)
            grooves[i] = Integer.parseInt(br.readLine());
        
        // 이분 탐색
        int start = 0;
        int end = n;
        while (start <= end) {
            int mid = (start + end) / 2;
            // mid 길이로 k개 만드는 것이 가능하다면
            // start = mid + 1로 범위 축소
            if (cut(mid, grooves, n, k))
                start = mid + 1;
            else         // 불가능하다면 end = mid - 1로 범위 축소
                end = mid - 1;
        }
        // 답 출력
        System.out.println(end == 0 ? -1 : end);
    }
    
    // 이분 탐색
    // 길이 length, 홈의 위치 grooves, 전체 길이 n, k겹
    static boolean cut(int length, int[] grooves, int n, int k) {
        // 시작하는 홈의 위치에 따라서 가능할 수도 않을수도 있기 때문에
        // 모든 홈에서 시작하는 경우를 전부 계산.
        for (int start = 0; start < grooves.length; start++) {
            // 고무줄의 개수
            int count = 0;
            // 시작 위치
            int preIdx = start;
            for (int i = start + 1; i < grooves.length; i++) {
                // 이전에 잘라진 위치로부터 현재 위치까지의 길이가 legnth 이상일 경우
                // count 증가, preIdx 에 현 위치 기록
                if (grooves[i] - grooves[preIdx] >= length) {
                    count++;
                    preIdx = i;
                }
            }
            // n인 위치를 넘어가는 경우.
            // 길이에 대한 보정이 필요.
            for (int i = 0; i <= start; i++) {
                if ((preIdx >= i ? n : 0) + grooves[i] - grooves[preIdx] >= length) {
                    count++;
                    preIdx = i;
                }
            }
            // 총 k개 이상의 고무줄이 만들어진다면
            // true 반환.
            if (count >= k)
                return true;
        }
        // 모든 시작 홈에 대해서 계산했지만
        // 찾지 못했다면 false 반환.
        return false;
    }
}