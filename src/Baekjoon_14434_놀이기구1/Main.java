/*
 Author : Ruel
 Problem : Baekjoon 14434번 놀이기구1
 Problem address : https://www.acmicpc.net/problem/14434
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14434_놀이기구1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static List<List<Integer>> growingDays;

    public static void main(String[] args) throws IOException {
        // n명의 아이들, m개의 놀이기구에 대해 k기간 동안에 시도 q가 주어진다.
        // 각각 놀이기구는 키 제한이 있으며
        // 해당 놀이기구에 타려면 두 아이의 키 합이 키 제한보다 크거나 같아야한다.
        // 아이들은 모두 키가 0에서 시작하며
        // 하루에 한 명의 아이의 키가 1 성장하며, 키가 성장하는 아이의 번호가 주어진다.
        // 시도는
        // a b c 로 이루어지며
        // a와 b 두 아이가 k기간 동안 c번 놀이기구를 타려고 시도한다는 뜻이다.
        // 매일매일 아이들이 놀이기구에 총 몇번 타는지 출력하라
        //
        // 이분 탐색, 누적합 문제
        // 키가 줄어들지는 않으므로, 시도가 주어지면
        // 언제부터 놀이기구 탑승이 가능해지는지를 구해야한다.
        // 이를 빨리 구하기 위해서는
        // 각 아이들 별로 키가 성장하는 날을 구분하여 저장하고
        // 주어진 날짜에 대한 해당 아이의 키를 이분탐색으로 빠르게 구해야한다.
        // 또한 마지막 답안 작성할 때
        // 날마다 탑승한 놀이기구 수도 결국, 키가 성장함에 따라 탑승에 대한 시도들이 많이 성공으로 바뀌어나가므로
        // 누적합으로 답을 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 아이들
        int n = Integer.parseInt(st.nextToken());
        // m개의 놀이기구
        int m = Integer.parseInt(st.nextToken());
        // 주어지는 기간 k
        int k = Integer.parseInt(st.nextToken());
        // 놀이기구 탑승 시도 q
        int q = Integer.parseInt(st.nextToken());
        
        // 각 놀이기구의 키 제한
        int[] heightLimits = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 각 아이들의 성장을 분리하여 저장해둔다.
        growingDays = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            growingDays.add(new ArrayList<>());
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < k; i++)
            growingDays.get(Integer.parseInt(st.nextToken())).add(i + 1);
        
        // 각 날짜에 탑승가능한 놀이기구 수
        int[] psums = new int[k];
        for (int l = 0; l < q; l++) {
            st = new StringTokenizer(br.readLine());
            // i번, j번 아이가 num 놀이기구에 탑승하려고 한다.
            int i = Integer.parseInt(st.nextToken());
            int j = Integer.parseInt(st.nextToken());
            int num = Integer.parseInt(st.nextToken());

            // 1일부터 k + 1일 까지
            // k일 동안 두 아이가 해당 놀이기구에 탑승하지 못하는 경우도 생긴다.
            // 따라서 k+1일까지로 탐색 범위를 두고
            // k+1일이 결과로 나온다면 해당 놀이기구는 탑승하지 못하는 것이다.
            int start = 1;
            int end = k + 1;
            while (start < end) {
                int mid = (start + end) / 2;
                if (childHeight(i, mid) + childHeight(j, mid) < heightLimits[num - 1])
                    start = mid + 1;
                else
                    end = mid;
            }
            // 탑승이 가능할 경우
            // 해당하는 날짜에 +1
            if (start <= k)
                psums[start - 1]++;
        }
        
        // 누적합을 통해 답안 작성
        StringBuilder sb = new StringBuilder();
        sb.append(psums[0]).append("\n");
        for (int i = 1; i < psums.length; i++)
            sb.append(psums[i] += psums[i - 1]).append("\n");
        // 답 출력
        System.out.print(sb);
    }

    // 이분 탐색을 통해
    // child 아이가 day 날에 키가 얼마인지 계산한다.
    static int childHeight(int child, int day) {
        int start = 0;
        int end = growingDays.get(child).size() - 1;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (growingDays.get(child).get(mid) <= day)
                start = mid + 1;
            else
                end = mid - 1;
        }
        return end + 1;
    }
}