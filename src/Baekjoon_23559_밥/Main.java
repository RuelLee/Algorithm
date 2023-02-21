/*
 Author : Ruel
 Problem : Baekjoon 23559번 밥
 Problem address : https://www.acmicpc.net/problem/23559
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23559_밥;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 학생식당엔 두 개의 메뉴가 있으며, A메뉴는 5000원, B메뉴는 1000원이다.
        // n일 동안 각 메뉴를 먹었을 때의 만족도가 주어지며, n일 동안 매일 식사를 하되, x원 이하로 사용한다할 때
        // 얻을 수 있는 최대 만족도는?
        //
        // 그리디 문제
        // 매일 식사로 A 혹은 B메뉴 중 하나는 먹어야한다.
        // 따라서 A메뉴가 B메뉴보다 만족도가 큰 순서대로 정렬을 한다.
        // 그 후, A메뉴가 B메뉴가 만족도보다 크고, 남은 일 동안 모두 B메뉴를 먹을 수 있다면
        // 우선적으로 A메뉴를 먹고
        // B메뉴가 만족도가 더 크거나, 소지금이 부족하다면 B메뉴를 먹어나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 주어지는 일자와 소지금
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());

        // 각 날짜의 메뉴 별 만족도
        int[][] days = new int[n][];
        for (int i = 0; i < days.length; i++)
            days[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 날짜 자체가 중요하진 않다
        // A가 B보다 만족도가 큰 순으로 정렬한다.
        Arrays.sort(days, (o1, o2) -> Integer.compare(o2[0] - o2[1], o1[0] - o1[1]));

        int sum = 0;
        for (int i = 0; i < days.length; i++) {
            // A메뉴가 B메뉴보다 만족도가 크면서
            // 남은 일 동안 모두 B메뉴를 먹을 수 있는 소지금이 된다면
            // A메뉴를 먹는다.
            if (days[i][0] > days[i][1] && x - 5000 >= (n - (i + 1)) * 1000) {
                x -= 5000;
                sum += days[i][0];
            } else {        // 그렇지 않다면, B메뉴를 먹는다.
                x -= 1000;
                sum += days[i][1];
            }
        }

        // 얻은 총 만족도를 출력한다.
        System.out.println(sum);
    }
}