/*
 Author : Ruel
 Problem : Baekjoon 12048번 Sums of Sums (Small)
 Problem address : https://www.acmicpc.net/problem/12048
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package SumsOfSums_Small;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static long[] psum;
    static long[] qsum;

    public static void main(String[] args) throws IOException {
        // Sums of Sums (Large) (https://www.acmicpc.net/problem/14337) 보다 n이 작아 직접 subarray을 만들더라도 괜찮다
        // 하지만 이분 탐색, 구간합, 두 포인터를 사용해서 다시 풀어보자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int testCase = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int q = Integer.parseInt(st.nextToken());


            psum = new long[n + 1];     // 주어진 수열에 대한 누적합
            qsum = new long[n + 1];     // subarray에 대한 누적합을 계산할 용도.
            st = new StringTokenizer(br.readLine());
            // psum[i] = arr[i] + psum[i-1]
            // qsum[i] = (n - i + 1) * arr[i] + qsum[i-1]
            for (int i = 1; i < n + 1; i++)
                qsum[i] = ((psum[i] = Integer.parseInt(st.nextToken()) + psum[i - 1]) - psum[i - 1]) * (n - i + 1) + qsum[i - 1];

            sb.append("Case #").append(t + 1).append(":").append("\n");
            for (int i = 0; i < q; i++) {
                st = new StringTokenizer(br.readLine());
                long left = Long.parseLong(st.nextToken());
                long right = Long.parseLong(st.nextToken());

                sb.append(sumToNFromOne(right) - sumToNFromOne(left - 1)).append("\n");
            }
        }
        System.out.println(sb);
    }

    static long sumToNFromOne(long n) {
        long sum = 0;
        long num = findXthNumInSubarray(n);
        // 투 포인터를 활용하여
        int right = 1;
        for (int left = 1; left < psum.length; left++) {
            // left일 때, right를 하나씩 늘려가며 num보다 작은 수의 범위를 찾는다.
            while (right < psum.length && psum[right] - psum[left - 1] < num)
                right++;
            // 찾은 개수를 n에서 빼주고,
            n -= right-- - left;
            // subarray에서 left ~ right 구간의 합을 더해준다.
            sum += qsum[right] - qsum[left - 1] - (psum.length - 1 - right) * (psum[right] - psum[left - 1]);
        }
        // 아직 더해지지 않은 개수 n개는 num과 같은 수이다.
        // n * num을 더해주고 리턴해주자.
        return sum + n * num;
    }

    static long findXthNumInSubarray(long x) {      // subarray에서 x번째 수를 찾는다.
        // subarray에서 값의 범위를 start ~ end로 정한다
        // subarray에서 가장 큰 값은 원래 수열의 모든 수를 더한 값 psum[n]과 같다.
        long start = 0;
        long end = psum[psum.length - 1];
        while (start < end) {
            // mid값을 정하고
            long mid = (start + end) / 2;
            long count = 0;
            // 투 포인터를 활용하여
            int right = 1;
            for (int left = 1; left < psum.length; left++) {
                while (right < psum.length && psum[right] - psum[left - 1] <= mid)
                    right++;
                // left일 때 right를 하나씩 늘려가며 mid보다 작은 값의 개수를 count에 더해준다.
                count += right - left;
            }
            // 세어진 수가 x보다 작다면 x번째 수는 mid보다 크다.
            // start = mid + 1로 넘겨주자.
            if (count < x)
                start = mid + 1;
            // 그렇지 않다면 x번째 수는 x보다 작거나 같다
            // end = mid로 넘겨주자
            else
                end = mid;
        }
        // 최종적으로 얻은 start가 subarray에서의 x번째 수와 같다.
        return start;
    }
}