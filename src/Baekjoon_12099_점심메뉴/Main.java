/*
 Author : Ruel
 Problem : Baekjoon 12099번 점심메뉴
 Problem address : https://www.acmicpc.net/problem/12099
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12099_점심메뉴;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 승관이와 영우는 q일 동안 같이 점심을 먹는다.
        // n개의 메뉴가 주어지고, 각 메뉴는 매운맛 수치 a와 단맛 수치 b가 주어진다.
        // 매일 승관이가 원하는 매운맛 수치의 범위 u v, 영우가 원하는 단맛 수치 x y가 주어진다.
        // 매일 두 사람이 같이 먹을 수 있는 메뉴의 개수는?
        //
        // 이분 탐색
        // 메뉴들에 대해 매운맛 수치에 대해 오름차순 정렬 후,
        // 매일 승관이가 원하는 매운맛 수치에 해당하는 범위를 찾는다.
        // 그 후, 그 중에서 영우가 원하는 단맛을 원하는 메뉴가 몇 개 있는지 세어나간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 메뉴, 같이 점심을 먹는 q일
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        
        // 메뉴들
        int[][] menus = new int[n][2];
        for (int i = 0; i < menus.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < menus[i].length; j++)
                menus[i][j] = Integer.parseInt(st.nextToken());
        }
        // 매운맛 수치에 따른 오름차순 정렬
        Arrays.sort(menus, Comparator.comparingInt(o -> o[0]));
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            // 승관이가 원하는 매운맛의 범위 u v
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            // 영우가 원하는 단맛의 범위 x y
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            // 승관이가 원하는 매운맛 범위의 최소값 u
            // 보다 같거나 큰 제일 작은 메뉴를 찾는다.
            int start = 0;
            int end = menus.length - 1;
            while (start < end) {
                int mid = (start + end) / 2;
                if (menus[mid][0] >= u)
                    end = mid;
                else
                    start = mid + 1;
            }
            int left = end;

            // 승관이가 원하는 매운맛 범위의 최대값 v
            // 보다 같거나 작은 제일 큰 메뉴를 찾는다.
            start = 0;
            end = menus.length - 1;
            while (start <= end) {
                int mid = (start + end) / 2;
                if (menus[mid][0] > v)
                    end = mid - 1;
                else
                    start = mid + 1;
            }
            int right = end;

            // 범위 내에 영우가 원하는 단맛 범위를 만족하는 메뉴가
            // 몇 개인지 센다.
            int count = 0;
            while (left <= right) {
                if (menus[left][1] >= x && menus[left][1] <= y)
                    count++;
                left++;
            }
            // 답 기록
            sb.append(count).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}