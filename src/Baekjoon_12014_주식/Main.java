/*
 Author : Ruel
 Problem : Baekjoon 12014번 주식
 Problem address : https://www.acmicpc.net/problem/12014
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12014_주식;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 날에 대한 주가를 알고 있다.
        // 이 중 k개의 날에 주가를 사되, 첫 구매를 제외하고는 모두 전보다 주가가 올랐을 때 구매한다.
        // 구매가 가능하다면 1, 불가능하다면 0을 출력한다.
        //
        // 최장 증가 수열
        // 최장 증가 수열의 길이를 구하고 이것이 k이상인지 확인한다.
        // n이 최대 1만이나, t가 100까지 주어지므로 당연히 이분탐색을 활용하는 것이 좋다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // n개의 날짜와 구매하는 날의 개수 k
            int n = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());

            // 주어지는 주가들
            int[] stocks = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            // 최장 증가 수열에 각 순서에 따른
            // 가장 작은 값을 저장해둔다.
            int[] minValue = new int[n];
            Arrays.fill(minValue, Integer.MAX_VALUE);
            
            // 최장 증가 수열의 길이
            int maxOrder = 0;
            for (int stock : stocks) {
                // start의 시작값은 0
                int start = 0;
                // end는 현재의 길이보다 하나 증가할 수 있으므로
                // maxOrder + 1
                int end = maxOrder + 1;

                // stock[i]가 해당하는 순서를 찾아간다.
                while (start < end) {
                    int mid = (start + end) / 2;
                    if (minValue[mid] >= stock)
                        end = mid;
                    else
                        start = mid + 1;
                }
                // 최장 증가 수열의 길이가 갱신됐는지 확인.
                maxOrder = Math.max(maxOrder, end);
                // 자신의 순서에 해당하는 곳의 최소값을 갱신했는지 확인.
                minValue[end] = Math.min(minValue[end], stock);
            }
            
            // 현재 테스트케이스에서 길이가 k 이상을 만족하는지 확인하고 답안 기록
            sb.append("Case #").append(t + 1).append("\n");
            sb.append(maxOrder + 1 >= k ? 1 : 0).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}