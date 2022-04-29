/*
 Author : Ruel
 Problem : Baekjoon 1806번 부분합
 Problem address : https://www.acmicpc.net/problem/1806
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1806_부분합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수들과 s가 주어진다.
        // n개의 수들 중 연속한 합이 s 이상인 경우들 중 가장 짧은 길이를 구하여라.
        //
        // 두 포인터 문제
        // 시작 지점을 나타내는 포인터는 하나씩 증가시면서
        // 각 시점 지점마다 마지막 지점을 나타내는 포인터는 합이 s이상이 될 때까지 증가시킨다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());

        int[] nums = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < nums.length; i++)
            nums[i] = Integer.parseInt(st.nextToken());

        // 수들의 합
        int sum = 0;
        // 마지막 지점
        int end = 1;
        // sum이 s이상이 되는 가장 큰 최대값은 nums의 길이
        int minLength = nums.length;

        // 시작 지점은 하나씩 증가시키고
        for (int start = 1; start < nums.length; start++) {
            // 그 때마다 범위에서 제외되는 수들은 sum에서 빼준다
            sum -= nums[start - 1];
            // nums 범위 내에서 sum이 s이상 될 때까지 늘려준다.
            while (sum < s && end < nums.length)
                sum += nums[end++];
            
            // 만약 끝 지점이 마지막에 도달했음에도 sum이 s보다 작아져버렸다면 더 이상 가능한 경우가 없다
            // break로 끝내주자.
            if (sum < s && end == nums.length)
                break;
            // sum > s 이상은 경우라면 이 때의 수의 최소 개수를 갱신해준다
            minLength = Math.min(minLength, end - start);
        }
        // 최종적으로 minLength가 nums의 길이만큼이라면 가능한 경우가 없다. 0 출력
        // 그 외에는 minLength 출력
        System.out.println(minLength == nums.length ? 0 : minLength);
    }
}