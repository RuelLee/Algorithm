/*
 Author : Ruel
 Problem : Jungol 2183번 가장 큰값 만들기
 Problem address : https://jungol.co.kr/problem/2183
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_2183_가장큰값만들기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n;
    static int[] nums;

    public static void main(String[] args) throws IOException {
        // 1이상 100이하의 수 n(2이상 5이하)개가 주어진다.
        // 수들을 연이어붙영 하나의 수를 만들 때, 가장 큰 값은?
        //
        // 브루트 포스 문제
        // n이 크지 않고 작으므로 모든 경우의 수에 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 수
        n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        nums = new int[n];
        for (int i = 0; i < n; i++)
            nums[i] = Integer.parseInt(st.nextToken());

        // 모든 경우의 수로 수를 조합한다.
        int ans = findMax(0, 0, new boolean[n]);
        System.out.println(ans);
    }

    // 브루트 포스로 모든 경우의 수를 계산한다.
    static int findMax(int selected, int value, boolean[] used) {
        // n개의 수를 모두 조합한 경우 값을 반환한다.
        if (selected == n)
            return value;

        // 파생되는 경우들의 최댓값을 구한다.
        int max = value;
        for (int i = 0; i < nums.length; i++) {
            // 아직 사용하지 않은 수인 경우
            if (!used[i]) {
                // value에서 nums[i]의 자리만큼 10의 제곱수를 곱해준다.
                int pow = 10;
                while (pow <= nums[i])
                    pow *= 10;
                // 사용 표시
                used[i] = true;
                // 사용된 수 증가, value에 pow를 곱해 nums[i]의 자리를 만들고, 더해준 값을 넘긴다.
                // 만들 수 있는 경우들을 계산해 max에 반영한다.
                max = Math.max(max, findMax(selected + 1, value * pow + nums[i], used));
                // 계산이 끝나면 false 체크
                used[i] = false;
            }
        }
        // 현재 경우에서 파생되는 최댓값을 반환한다.
        return max;
    }
}