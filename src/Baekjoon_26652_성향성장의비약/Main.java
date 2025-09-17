/*
 Author : Ruel
 Problem : Baekjoon 26652번 성향 성장의 비약
 Problem address : https://www.acmicpc.net/problem/26652
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_26652_성향성장의비약;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 성향이 주어진다.
        // x인 성향을 x + 1로 올리기 위해서는 x개만큼의 성향 아이템이 필요하다.
        // 또한 성향을 올리는데는 다른 방법도 있는데 성향 성장의 비약을 사용하면 어떤 성향이든 1을 상승시킬 수 있다.
        // m개의 성장의 비약과
        // n개의 성향의 현재 값과 각 성향에 대한 성향 아이템의 수가 주어진다.
        // 정n각형 형태로 성향을 만들고자할 때, 가능한 가장 높은 레벨 값은?
        //
        // 이분탐색 문제
        // 이분 탐색을 통해 먼저, 각 성향 아이템으로 올릴 수 있는 성향의 최대값을 구한다.
        // 그리고 다시 이분 탐색을 통해 정n각형중 가장 높은 레벨을 만들며, 부족한만큼 성향 성장의 비약을 사용한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 성향, m개의 성장 비약
        int n = Integer.parseInt(st.nextToken());
        long m = Long.parseLong(st.nextToken());
        
        // 현재 성향들의 값
        long[] l = new long[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < l.length; i++)
            l[i] = Integer.parseInt(st.nextToken());
        
        // 각 성향 아이템의 개수
        long[] a = new long[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < a.length; i++)
            a[i] = Long.parseLong(st.nextToken());
        
        // 성장 비약 없이 만들 수 있는 최대 성향 레벨
        long[] withoutM = new long[n];
        for (int i = 0; i < l.length; i++) {
            long start = l[i];
            long end = Integer.MAX_VALUE;
            
            // 등차수열의 합과 이분 탐색을 활용하여 계산
            while (start <= end) {
                long mid = (start + end) / 2;
                if (2 * a[i] >= (mid - l[i]) * (l[i] - 1 + mid))
                    start = mid + 1;
                else
                    end = mid - 1;
            }
            withoutM[i] = end;
        }
        
        // 가능한 높은 레벨의 정n각형을 만든다.
        // 현재 성향의 레벨들 중 가장 높은 값보다 더 큰 정n각형이 만들어져야한다.
        long max = 0;
        for (int i = 0; i < l.length; i++)
            max = Math.max(max, l[i]);
        // 시작은 max
        long start = max;
        // 성장의 비약이 최대 10^12개 주어지므로, end는 넉넉하게 10^13
        long end = 10_000_000_000_000L;
        // 가능한 높은 레벨을 이분 탐색
        while (start <= end) {
            long mid = (start + end) / 2;
            if (possibleTarget(l, withoutM, m, mid))
                start = mid + 1;
            else
                end = mid - 1;
        }
        
        // 만약 end가 max보다 낮은 값을 갖는다면
        // max보다 같거나 큰 정n각형을 만들 수 없는 경우
        // 성향을 줄일 수는 없으므로 불가능한 경우. -1 출력
        // 그 외에는 end값 출력
        System.out.println(end < max ? -1 : end);
    }

    // 성장의 비약 m개로 모든 성향을 target으로 만들 수 있는지 확인.
    static boolean possibleTarget(long[] l, long[] withoutM, long m, long target) {
        for (int i = 0; i < l.length; i++) {
            // 만약 성장 아이템으로 만들 수 있는 레벨이 target보다 낮다면
            // 부족한 만큼 성장의 비약으로 채워야한다.
            if (withoutM[i] < target) {
                if (m >= target - withoutM[i])
                    m -= (target - withoutM[i]);
                else        // 만약 개수가 부족하다면 불가능한 경우이므로 false 반환
                    return false;
            }
        }
        // 여기까지 잘 내려왔다면 가능한 경우이므로 true 반환.
        return true;
    }
}