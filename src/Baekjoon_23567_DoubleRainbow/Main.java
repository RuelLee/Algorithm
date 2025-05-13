/*
 Author : Ruel
 Problem : Baekjoon 23567번 Double Rainbow
 Problem address : https://www.acmicpc.net/problem/23567
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23567_DoubleRainbow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 일직선 상에 n개의 지점에 색이 지정되어있다.
        // 색 종류는 k개다.
        // 어느 연속적인 부분에 k개의 색이 포함되고, 해당 부분의 여집합에도 k개의 색이 존재한다면 double rainbow를 만든다고 한다.
        // 이러한 연속적인 부분의 최소 크기를 출력하라
        // 그러한 부분이 없다면 0을 출력한다.
        //
        // 두 포인터 문제
        // 두 포인터로 k개의 포함되는 최소 부분을 구하고
        // 여집합에도 k개의 색이 남아있는지 확인해주면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 지점과 총 색의 가짓수 k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 전체 색을 세어둔다.
        int[] fullCounts = new int[k + 1];
        // 각 지점의 색
        int[] colors = new int[n];
        for (int i = 0; i < colors.length; i++)
            fullCounts[colors[i] = Integer.parseInt(br.readLine())]++;
        
        // 오른쪽 포인터
        int right = -1;
        // 범위에 해당하는 각각 색의 수
        int[] rangeCounts = new int[k + 1];
        // 범위에 포함된 색의 종류
        int currentColors = 0;
        // double rainbow를 만드는 최소 길이
        int minLength = Integer.MAX_VALUE;
        for (int left = 0; left < colors.length; left++) {
            // k개의 색이 포함될 때까지 right 포인터를 전진.
            while (right + 1 < colors.length && currentColors < k) {
                if (rangeCounts[colors[++right]]++ == 0)
                    currentColors++;
            }
            
            // 현재 포함된 색이 k개이고
            if (currentColors == k) {
                boolean doubleRainbow = true;
                // 범위 밖의 색도 최소 하나 이상 존재할 경우 double rainbow
                for (int i = 1; i < fullCounts.length; i++) {
                    if (fullCounts[i] == rangeCounts[i]) {
                        doubleRainbow = false;
                        break;
                    }
                }
                // double rainbow일 경우, 해당 길이가 최솟값을 만족하는지 확인
                if (doubleRainbow)
                    minLength = Math.min(minLength, right - left + 1);
            }
            
            // left 포인터를 하나 전진시키므로
            // 범위 내의 해당하는 색의 개수 하나 차감
            // 차감하며 해당하는 색의 개수가 0이 된다면 색의 종류도 하나 차감
            if (rangeCounts[colors[left]]-- == 1)
                currentColors--;
        }
        // 구한 최소 길이 출력
        System.out.println(minLength == Integer.MAX_VALUE ? 0 : minLength);
    }
}