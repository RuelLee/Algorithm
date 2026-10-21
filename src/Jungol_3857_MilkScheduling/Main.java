/*
 Author : Ruel
 Problem : Jungol 3857번 Milk Scheduling
 Problem address : https://jungol.co.kr/problem/3857
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_3857_MilkScheduling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    static int[] toLeft;

    public static void main(String[] args) throws IOException {
        // n마리의 소가 주어지고 각 소에게서 우유를 짜는데 단위 시간이 걸린다.
        // 각 소에게서 짤 수 있는 우유의 양과 제한 시간이 주어진다.
        // 최대로 짤 수 있는 우유의 양은?
        //
        // 정렬, 그리디 문제
        // 소들을 생산할 수 있는 우유의 양에 따라 정렬한다.
        // 그 뒤 생산 우유가 큰 순서대로, 배치할 수 있는 가장 늦은 시간에 배치해나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n 마리의 소
        int n = Integer.parseInt(br.readLine());
        // 각 소의 생산 우유량과 마감 시간
        int[][] cows = new int[n][2];
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            cows[i][0] = Integer.parseInt(st.nextToken());
            cows[i][1] = Integer.parseInt(st.nextToken());
        }
        // 우유량에 따라 정렬
        Arrays.sort(cows, Comparator.comparingInt(o -> o[0]));

        // idx보다 같거나 이른 비어있는 시간을 가르킨다.
        toLeft = new int[10_001];
        // 초기화
        for (int i = 1; i < toLeft.length; i++)
            toLeft[i] = i;

        // 총 생산량
        int sum = 0;
        for (int i = cows.length - 1; i >= 0; i--) {
            // 범위 내 가장 느린 시각
            int idx = findLeft(cows[i][1]);
            // 0이 아닌 경우에는 해당 시간에 우유를 짠다.
            if (idx != 0) {
                sum += cows[i][0];
                // idx가 가르키는 시각을 하나 앞당김
                toLeft[idx] = idx - 1;
            }
        }
        // 총 생산량 출력
        System.out.println(sum);
    }

    // idx보다 같거나 이른 시간 중 비어있는 가장 느린 시간을 반환
    static int findLeft(int idx) {
        if (toLeft[idx] == idx)
            return idx;
        return toLeft[idx] = findLeft(toLeft[idx]);
    }
}