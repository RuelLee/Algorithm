/*
 Author : Ruel
 Problem : Baekjoon 2343번 기타 레슨
 Problem address : https://www.acmicpc.net/problem/2343
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2343_기타레슨;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 동영상이 주어지고, m개의 블루레이 디스크에 담으려고 한다.
        // 모든 디스크의 크기를 같은 것으로 한다.
        // 각 동영상의 크기가 주어질 때, 디스크의 최소 크기는?
        //
        // 이분 탐색 문제
        // 이분 탐색을 통해 가능한 디스크의 최소 크기를 찾으면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 동영상과 이를 담을 m개의 디스크
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        // 영상들
        int[] bluRay = new int[n];
        int sum = 0;
        for (int i = 0; i < bluRay.length; i++)
            sum += (bluRay[i] = Integer.parseInt(st.nextToken()));
        
        // m개의 디스크에 나눠담기 때문에
        // 정말 최적으로 잘 담긴다면, 최소 크기는 sum / n
        int start = sum / m;
        // 최악의 경우, 하나에 디스크에 모두를 담는 경우
        int end = sum;
        // 이분 탐색
        while (start < end) {
            int mid = (start + end) / 2;
            if (possible(mid, m, bluRay))
                end = mid;
            else
                start = mid + 1;
        }
        // 답 출력
        System.out.println(start);
    }
    
    // 한 디스크를 size 크기로, division개에 다 담을 수 있는가
    static boolean possible(int size, int division, int[] bluRay) {
        int sum = 0;
        int count = 1;
        for (int i = 0; i < bluRay.length; i++) {
            // 동영상 하나의 크기가 size를 넘는다면 불가능
            if (size < bluRay[i])
                return false;
            
            // 이전 디스크에 이번 영상을 추가하는 경우
            if (sum + bluRay[i] <= size)
                sum += bluRay[i];
            else {
                // 새 디스크가 필요한 경우
                sum = bluRay[i];
                count++;
            }
            
            // 개수가 division개를 넘어, 불가능해진 경우
            if (count > division)
                return false;
        }
        // division개에 모두 담았다면 true 반환
        return true;
    }
}