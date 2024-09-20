/*
 Author : Ruel
 Problem : Baekjoon 23830번 제기차기
 Problem address : https://www.acmicpc.net/problem/23830
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23830_제기차기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int p, q, r;
    static int[] scores, psums;
    static long s, sum;

    public static void main(String[] args) throws IOException {
        // 기준이 되는 양의 정수 k를 정한다.
        // 어떤 학생의 점수가 k+r 초과라면 그 학생 점수에서 p점을 뺀다
        // 어떤 학생의 점수가 k 미만이라면 그 학생 점수에서 q점을 더한다.
        // 보정된 전체 학생 점수의 합이 s 미만이라면 태영이에게 체육관 청소를 시킨다.
        // 청소를 하지 않아도 되는 최소 k를 구하라
        // 어떠한 경우에도 청소를 하게 된다면 -1을 출력한다
        //
        // 이분 탐색, 누적합 문제
        // n과 학생들의 점수가 최대 10만
        // p와 q는 최대 5천
        // r은 최대 1만 미만,
        // s는 2* 10^10으로 주어진다.
        // k를 이분 탐색을 통해 찾아나간다.
        // 학생들의 점수에 따라 누적합을 구하고, 해당 점수 이하의 학생이 몇명인지를 누적합으로 빠르게 구한다.
        // k가 최대 10만이 될 수 있음을 주의한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n명의 학생
        int n = Integer.parseInt(br.readLine());
        
        // 점수들
        scores = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 점수에 따른 누적합
        psums = new int[100_001];
        // 보정되기 전 점수 총합
        sum = 0;
        for (int score : scores) {
            psums[score]++;
            sum += score;
        }
        for (int i = 1; i < psums.length; i++)
            psums[i] += psums[i - 1];

        StringTokenizer st = new StringTokenizer(br.readLine());
        p = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());
        s = Long.parseLong(st.nextToken());

        // 이분 탐색을 통해 가능한 k를 찾는다.
        int start = 1;
        int end = 100_002;
        while (start < end) {
            int mid = (start + end) / 2;
            if (possible(mid))
                end = mid;
            else
                start = mid + 1;
        }
        
        // k가 100_001보다 커졌다면 체육관 청소를 할 수밖에 없는 경우이므로 -1을 출력
        // 그 외의 경우 start 출력
        System.out.println(start > 100_001 ? -1 : start);
    }

    static boolean possible(int k) {
        // k점 초과인 학생의 수
        long over = psums[100_000] - psums[Math.min(k + r, 100_000)];
        // k점 미만의 학생의 수
        long under = psums[k - 1];
        
        // 보정된 학생들의 점수 총합이 s 이상인지 확인
        return (sum - over * p + under * q) >= s;
    }
}