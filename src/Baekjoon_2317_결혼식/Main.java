/*
 Author : Ruel
 Problem : Baekjoon 2317번 결혼식
 Problem address : https://www.acmicpc.net/problem/2317
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2317_결혼식;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // k마리의 사자 가족과 n - k명의 사람이 기차놀이를 한다.
        // 사자 가족과 사람 모두 각각 키가 주어진다.
        // 사자 가족은 서열이 낮은 사자가가 서열이 높은 사자보다 앞에 위치하면 안된다.
        // 기차 놀이를 할 때, 이웃한 사자나 인간끼리의 키 차이의 합이 적을 수록 아름답게 보인다고 한다.
        // 가장 아름다운 기차 놀이일 때, 키 차이의 합의 값은?
        //
        // 그리디 문제
        // 사자 가족의 위치는 변경할 수 없다.
        // 그럼 사자들의 위치는 고정된 상태로 사람들의 위치만 이동할 수 있다.
        // 사람들을 배치할 때, 양 옆에 위치한 사자들의 키 사이에 해당하는 사람을 배치하면
        // 키 차이의 합의 값에 추가되는 값이 0으로 값을 최소화할 수 있다.
        // 그렇다면 사자들의 키 중 최댓값과 최솟값을 벗어나는 사람들에 한해 추가적으로 키 차이 합이 계산된다.
        // 가장 가장자리인 맨 왼쪽 혹은 오른쪽에 배치할 경우
        // 가장 자리에 위치한 사자와의 차이만 계산하면 되고
        // 사자들 사이에 배치할 경우, 원래 두 사자들 사이의 차이 합에 추가되는 값만 계산해주면 된다.
        // 해당 경우들을 고려하여 최솟값을 구하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 전체 인원 n, 사자 k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 사자들의 키
        int[] lions = new int[k];
        lions[0] = Integer.parseInt(br.readLine());
        // 사자들의 최소, 최대 키
        int min = lions[0];
        int max = lions[0];
        // 인근 사자들 사이의 키 차이 합
        int diffSum = 0;
        for (int i = 1; i < lions.length; i++) {
            lions[i] = Integer.parseInt(br.readLine());
            min = Math.min(lions[i], min);
            max = Math.max(lions[i], max);
            diffSum += Math.abs(lions[i] - lions[i - 1]);
        }
        
        // 사람의 최소 최대 키
        int humanMin = Integer.MAX_VALUE;
        int humanMax = Integer.MIN_VALUE;
        for (int i = 0; i < n - k; i++) {
            int height = Integer.parseInt(br.readLine());
            // 사람의 키가 사자들 키의 최소 최대값 사이에 들어간다면
            // 해당 사람을 그 사이에 배치할 경우, 추가되는 키 차이를 0으로 만들 수 있으므로 무시
            if (height > min && height < max)
                continue;
            // 최소 최대키 값 반영
            humanMin = Math.min(humanMin, height);
            humanMax = Math.max(humanMax, height);
        }
        
        // 만약, 모든 사람의 키가 사자들 키 사이에 들어가는 경우를 제외한 경우
        if (humanMin != Integer.MAX_VALUE) {
            // 가장자리에 최소키인 사람을 배치하는 경우
            int diff1 = Math.min(Math.abs(humanMin - lions[0]), Math.abs(humanMin - lions[k - 1]));
            // 가장자리에 최대키인 사람을 배치하는 경우
            int diff2 = Math.min(Math.abs(humanMax - lions[0]), Math.abs(humanMax - lions[k - 1]));
            
            // i와 i+1번째 사이에 최소, 최대키 사람을 배치하는 경우
            // 이미 사자들끼리의 키 차이는 더했으므로
            // 사이에 사람을 배치할 경우, 인근한 사자들 중 키 차이가 더 적은 사자와의 값 *2만큼이 추가로 값이 늘어난다.
            // 그 중 최솟값을 구한다.
            for (int i = 0; i < k - 1; i++) {
                diff1 = Math.min(diff1, Math.min(Math.abs(lions[i] - humanMin), Math.abs(lions[i + 1] - humanMin)) * 2);
                diff2 = Math.min(diff2, Math.min(Math.abs(lions[i] - humanMax), Math.abs(lions[i + 1] - humanMax)) * 2);
            }
            
            // 최소키인 경우를 늘어나는 값 차이를 더하고
            diffSum += diff1;
            // 사람의 최소, 최대키가 서로 다른 값인 경우
            // 최대키인 경우도 더한다.
            if (humanMin != humanMax)
                diffSum += diff2;
        }
        // 답 출력
        System.out.println(diffSum);
    }
}