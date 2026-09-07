/*
 Author : Ruel
 Problem : Jungol 21052번 사랑의 상처
 Problem address : https://jungol.co.kr/problem/21052
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_21052_사랑의상처;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n가지 색의 장미가 각각 무한히 있다.
        // 이 장미들로 꽃다발을 만드는데, 같은 색의 장미들끼리는 서로 상처를 하나씩 낸다.
        // 같은 색의 장미가 2송이라면 1, 3송이라면 3개의 상처가 난다.
        // 상처가 k개 이하이며 장미의 수가 m 이하인 최대 장미의 수는?
        //
        // 조합, 이분탐색 문제
        // 같은 색의 장미들끼리 상처를 내므로, j송이의 같은 색 장미가 있다면 jC2 개의 상처가 난다.
        // 1 ~ m 범위에 대해 이분탐색으로 상처의 개수를 계산해가며 가능한 가장 많은 수의 장미를 찾는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // q개의 테스트케이스
        int q = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < q; testCase++) {
            st = new StringTokenizer(br.readLine());
            // n종류의 장미
            int n = Integer.parseInt(st.nextToken());
            // 최대 장미의 수
            int m = Integer.parseInt(st.nextToken());
            // 최대 상처의 수
            long k = Long.parseLong(st.nextToken());

            // 이분 탐색
            int start = 1;
            int end = m;
            while (start <= end) {
                int mid = (start + end) / 2;
                if (possible(n, k, mid))
                    start = mid + 1;
                else
                    end = mid - 1;
            }
            // 답 기록
            sb.append(end).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }

    // n종류의 장미를 k개이하의 상처로 flowers송이 담는게 가능한가.
    static boolean possible(int n, long k, int flowers) {
        // 각 색의 장미가 최대한 비슷해야한다.
        // flowers / n개를 기준으로 삼고,
        // 남는 장미인 flowers - (flowsers / n * n)개의 장미를 하나씩 더 갖고 계산한다.
        int i = 0;
        // flowers % n 종류의 색에 대해 (flowers + n) / n 송이로 계산
        long flower = (flowers + n) / n;
        long needle = 0;
        for (; i < flowers % n; i++) {
            needle += (flower) * (flower - 1) / 2;
            if (needle > k)
                return false;
        }

        // 나머지 수는 -1 한 장미의 수로 계산
        flower--;
        for (; i < n; i++) {
            needle += (flower) * (flower - 1) / 2;
            if (needle > k)
                return false;
        }
        // 중간에 상처의 개수가 k 초과가 된다면 false 반환.
        // 그 외의 경우 true 반환
        return true;
    }
}