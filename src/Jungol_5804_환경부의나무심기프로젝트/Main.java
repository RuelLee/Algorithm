/*
 Author : Ruel
 Problem : Jungol 5804번 환경부의 나무 심기 프로젝트
 Problem address : https://jungol.co.kr/problem/5804
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_5804_환경부의나무심기프로젝트;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int k;
    static int[] x;

    public static void main(String[] args) throws IOException {
        // n개의 나무를 심을 수 있는 위치가 주어진다.
        // k 그루의 나무를 최대한 서로 멀리 떨어져 심으려고할 때
        // 가장 가까운 나무 사이의 거리는?
        //
        // 이분탐색
        // 이분 탐색으로 값의 범위와 k값에 따른 최대 간격을 범위로 두고 탐색한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 나무를 심을 수 있는 위치, 나무의 수 k 그루
        int n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        // 위치들을 입력 받아 정렬
        x = new int[n];
        for (int i = 0; i < n; i++)
            x[i] = Integer.parseInt(br.readLine().trim());
        Arrays.sort(x);

        // 이분 탐색.
        // 최소 mid 간격으로 나무들을 심을 수 있는지 확인
        int start = 1;
        int end = 1000000001;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (minDistance(mid))
                start = mid + 1;
            else
                end = mid - 1;
        }
        // 답 출력
        System.out.println(end);
    }

    // 최소 distance 간격으로 k그루의 나무들을 심을 수 있는지 확인
    static boolean minDistance(int distance) {
        // 심은 나무의 수
        int cnt = 0;
        // 이전 나무의 위치
        int pre = -1000000000;
        for (int i = 0; i < x.length; i++) {
            // 이전 나무와 간격이 distance 이상인 경우
            // 해당 위치에 심음
            if (x[i] - pre >= distance) {
                pre = x[i];
                // 모두 심은 경우 true 반환
                if (++cnt == k)
                    return true;
            }
        }
        // 모두 못 심은 경우 false 반환
        return false;
    }
}