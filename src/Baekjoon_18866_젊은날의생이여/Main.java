/*
 Author : Ruel
 Problem : Baekjoon 18866번 젊은 날의 생이여
 Problem address : https://www.acmicpc.net/problem/18866
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18866_젊은날의생이여;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 년에 대해 행복도와 피로도가 주어진다.
        // 젊은 날과 늙은 날은 다음의 조건을 만족한다.
        // 임의의 젊은 날의 행복도는 임의의 늙은 날의 행복도보다 높다.
        // 임의의 젊은 날의 피로도는 임의의 늙은 날의 피로도보다 낮다.
        // 데이터 중 일부가 누락되어 0으로 주어질 때
        // 가능한 젊은 날의 최대값은?
        //
        // DP 문제
        // 왼쪽에서부터 가능한 행복의 최소값과 피로도의 최대값
        // 오른쪽에서부터 가능한 행복의 최대값과 피로도의 최소값을 구한다.
        // 그 후, 왼쪽에서의 행복의 최소값이 오른쪽의 행복의 최대값보다 크고
        // 왼쪽에서의 피로도의 최대값보다 오른쪽의 프로도의 최소값이 더 큰 경우들 중 가장 큰 값을 찾는다

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 연도에 대한 행복도와 피로도
        int n = Integer.parseInt(br.readLine());
        int[][] years = new int[n][];
        for (int i = 0; i < years.length; i++)
            years[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 왼쪽에서부터 등장한 행복의 최소값, 피로도의 최대값을 기록해나간다.
        int[][] fromLeft = new int[n][2];
        fromLeft[0][0] = (years[0][0] == 0 ? Integer.MAX_VALUE : years[0][0]);
        fromLeft[0][1] = (years[0][1] == 0 ? Integer.MIN_VALUE : years[0][1]);
        for (int i = 1; i < fromLeft.length; i++) {
            fromLeft[i][0] = Math.min(fromLeft[i - 1][0], (years[i][0] == 0 ? Integer.MAX_VALUE : years[i][0]));
            fromLeft[i][1] = Math.max(fromLeft[i - 1][1], (years[i][1] == 0 ? Integer.MIN_VALUE : years[i][1]));
        }

        // 오른쪽에서부터의 행복도와 최소값은 구해나가면서
        // 왼쪽에서부터의 값과 비교한다.
        int year = n - 2;
        int happy = 0;
        int fatigue = Integer.MAX_VALUE;
        while (year >= 0) {
            // year + 1 이후의 최대 행복값
            happy = Math.max(happy, years[year + 1][0]);
            // year + 1 이후의 최소 피로도
            fatigue = Math.min(fatigue, (years[year + 1][1] == 0 ? Integer.MAX_VALUE : years[year + 1][1]));
            // year까지의 최소 행복값이 year + 1 이후의 최대 행복보다 크고
            // year까지의 최대 피로도가 year + 1 이후의 최소 피로도보다 작은 경우
            // 값을 찾았으므로 종료.
            if (fromLeft[year][0] > happy && fromLeft[year][1] < fatigue)
                break;
            // 아니라면 더 낮은 연도에 대해 계산한다.
            year--;
        }

        // year가 -1까지 내려온 경우, 불가능한 경우이므로 -1 출력
        // 그 외의 경우는 year + 1(0년부터 시작했으므로)를 출력한다.
        System.out.println(year == -1 ? -1 : year + 1);
    }
}