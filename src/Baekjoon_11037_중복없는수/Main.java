/*
 Author : Ruel
 Problem : Baekjoon 11037번 중복 없는 수
 Problem address : https://www.acmicpc.net/problem/11037
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11037_중복없는수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    static List<Integer> nums;

    public static void main(String[] args) throws IOException {
        // 중복 없는 수는 각 숫자 (1 ~ 9)가 최대 한 번씩 등장하고, 숫자 0은 포함하지 않는 수이다.
        // 예로는 9, 32, 489, 98761 등이 있다.
        // n이 주어질 때, n보단 크면서 가장 작은 중복 없는 수를 출력하라
        //
        // 브루트포스, 백트래킹, 이분 탐색 문제
        // 먼저 브루트포스와 백트래킹을 활용하여 999999999 이하의 중복없는 수를 모두 구한다.
        // 그 후 정렬하여, n이 주어질 때마다 이분탐색을 통해 n보다 크지만 가장 작은 중복 없는 수를 찾는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 중복 없는 수
        nums = new ArrayList<>();
        fillNums(0, 0, new boolean[10]);
        // 정렬
        Collections.sort(nums);

        String input = br.readLine();
        StringBuilder sb = new StringBuilder();
        while (input != null) {
            // n보단 크지만 가장 작은 중복 없는 수를 찾는다.
            int n = Integer.parseInt(input);
            
            // 이분 탐색
            int start = 0;
            int end = nums.size();
            while (start < end) {
                int mid = (start + end) / 2;
                // mid가 n보다 같거나 작다면
                // start 범위를 mid + 1로
                if (nums.get(mid) <= n)
                    start = mid + 1;
                else        // n보다 크다면 end를 mid로
                    end = mid;
            }
            // 만약 start가 nums의 크기를 벗어났다면 해당하는 수가 없는 경우.
            // 0 기록
            // 그 외에는 start가 가리키는 수를 기록한다.
            sb.append(start == nums.size() ? 0 : nums.get(start)).append("\n");
            // 다음 입력 받음
            input = br.readLine();
        }
        // 전체 답 출력
        System.out.print(sb);
    }
    
    // 백트래킹으로 중복 없는 수 찾기
    static void fillNums(int idx, int num, boolean[] used) {
        // 최대 9개의 수를 배치한다.
        if (idx >= 10)
            return;

        // num을 중복 없는 수로 담고
        nums.add(num);
        // 뒤에 1 ~ 9 중 아직 사용되지 않은 수를 덧붙여 새로운 수를 만든다.
        for (int i = 1; i < used.length; i++) {
            if (used[i])
                continue;
            
            // i가 사용되지 않았다면 사용 체크
            used[i] = true;
            // num * 10 + i로 탐색
            fillNums(idx + 1, num * 10 + i, used);
            // 탐색 후 사용 체크 해제
            used[i] = false;
        }
    }
}