/*
 Author : Ruel
 Problem : Baekjoon 32347번 시간을 돌리고 싶어
 Problem address : https://www.acmicpc.net/problem/32347
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_32347_시간을돌리고싶어;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 타임 머신을 최대 k회 사용할 수 있으며
        // 타임 머신을 사용하면 t일 전으로 돌아갈 수 있다.
        // t일이 1일 이전이라면 1일로 돌아간다.
        // i일에 타임머신을 사용하기 위해서는 i일에 전원이 공급되고 있어야한다.
        // 타임 머신을 타고 간 뒤, 과거에서 시간을 보내는 것도 가능하다.
        // 각 날짜별로 전원 공급이 되었는지에 대한 수열이 주어진다.
        // 1일로 돌아가기 위핸 t의 최소값을 구하라
        //
        // 이분 탐색 문제
        // 1 ~ n까지의 범위로 t를 이분 탐색한다
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n일, 타임 머신 사용 횟수 k회
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // i일에 전원이 공급되었는지 여부
        boolean[] powered = new boolean[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < powered.length; i++)
            powered[i] = (Integer.parseInt(st.nextToken()) == 1);
        
        // 이분 탐색
        int start = 1;
        int end = n;
        while (start < end) {
            int mid = (start + end) / 2;
            // t가 mid일 때, 1일로 돌아갈 수 있다면
            // 탐색 범위의 끝을 mid로 조정
            if (canTimeBack(mid, k, powered))
                end = mid;
            else    // 불가능하다면 탐색의 시작 범위를 mid + 1로 조정
                start = mid + 1;
        }
        // 최종 start가 t의 최소값
        System.out.println(start);
    }

    // 주어진 t와 k로 1일까지 돌아갈 수 있는지 확인한다.
    static boolean canTimeBack(int t, int k, boolean[] powered) {
        int idx = powered.length - 1;
        // k가 0보다 크고, 현재 날짜가 0일보다 크며
        // idx날에 전원이 공급된 경우
        while (k > 0 && idx > 0 && powered[idx]) {
            boolean canBack = false;
            // idx - t ~ idx -1일까지 중 전원이 공급된 가장 이른 날을 찾는다.
            for (int i = Math.max(0, idx - t); i < idx; i++) {
                // 찾거나, 첫번째 날로 돌아간 경우
                if (powered[i] || i == 0) {
                    // 해당 일로 바꾸고
                    idx = i;
                    // k 차감
                    k--;
                    // 이동 표시
                    canBack = true;
                    break;
                }
            }
            // 만약 더 이상 과거로 가지 못한다면 반복문을 종료한다.
            if (!canBack)
                break;
        }
        // 최대한 과거로 간 날이 첫번째 날인지 확인한다.
        return idx == 0;
    }
}