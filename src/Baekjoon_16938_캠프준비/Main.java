/*
 Author : Ruel
 Problem : Baekjoon 16938번 캠프 준비
 Problem address : https://www.acmicpc.net/problem/16938
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16938_캠프준비;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int n, l, r, x;
    static int[] problems;

    public static void main(String[] args) throws IOException {
        // n개의 문제가 있다.
        // 두 문제 이상의 문제를 골라, 난이도의 합이 l 이상, r 이하가 되고,
        // 가장 쉬운 문제와 어려운 문제의 난이도 차이가 x 이상이 되도록하려고 한다.
        // 고를 수 있는 문제들의 가짓수는 모두 몇 가지인가.
        //
        // 백트래킹을 활용한 조합
        // n이 최대 15이므로, 약 3만 2천가지이므로, 직접 계산해도 괜찮다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 입력으로 주어지는 조건
        n = Integer.parseInt(st.nextToken());
        l = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken());

        // 문제는 오름차순으로 정렬한다.
        problems = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        Arrays.sort(problems);

        // 가능한 가짓수를 센다.
        int count = 0;
        for (int i = 0; i < problems.length; i++) {
            // 난이도가 r이하인 경우에만 가짓수 찾기를 시작한다.
            if (problems[i] <= r)
                count += countCases(i, problems[i], problems[i]);
        }
        // 찾은 가짓수를 출력한다.
        System.out.println(count);
    }

    // 조합 가능한 가짓수를 센다.
    // 매개 변수로 현재 문제의 idx, 문제들의 난이도 합, 문제들 중 최소 난이도
    static int countCases(int idx, int sum, int min) {
        // 여태까지의 문제들로 가짓수 중 하나로 셀수 있다면
        // (= 현재까지 난이도의 합이 l 이상이고, 난이도 차이가 x 이상일 때)
        // 1 아니라면 0
        int count = (sum >= l && problems[idx] - min >= x) ? 1 : 0;
        // 다음 문제들을 추가시켜나간다.
        for (int i = idx + 1; i < problems.length; i++) {
            // (i번째 문제를 추가했을 때 난이도 합이 r보다 같거나 작을 때만)
            // 문제를 재귀적으로 호출하며 센 가짓수를 count에 더해나간다.
            if (sum + problems[i] <= r)
                count += countCases(i, sum + problems[i], min);
        }
        // 센 가짓수를 반환.
        return count;
    }
}