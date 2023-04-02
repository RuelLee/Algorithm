/*
 Author : Ruel
 Problem : Baekjoon 2651번 자동차경주대회
 Problem address : https://www.acmicpc.net/problem/2651
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2651_자동차경주대회;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 자동차 경주 대회가 열리고 있다.
        // 규정 상 일정 거리마다 정비소를 반드시 들려야한다
        // n개의 정비소와 각 정비소 간의 떨어진 거리 그리고 각 정비소의 정비 시간이 주어질 때
        // 도착지점까지 최소 정비시간으로 도달하는 방법은?
        //
        // DP 문제
        // 한번 들린 정비소에서 다음 번에 들릴 수 있는 정비소들을 계산하며
        // 각 정비소에서 걸린 시간의 합을 구해나간다.
        // 들리는 정비소의 개수와 지점들도 출력해야하므로, 이전에 들렸던 정비소도 기록해두자.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 정비소 없이 갈 수 있는 최대 거리 
        int maxDistance = Integer.parseInt(br.readLine());
        // 정비소들의 개수
        int garages = Integer.parseInt(br.readLine());
        // 정비소들의 위치
        // 정비소들의 거리 차이로 주어지지만 누적합을 통해 출발 지점에서 떨어진 거리로 계산해두자.
        int[] garageLocations = new int[garages + 2];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i < garageLocations.length; i++)
            garageLocations[i] = Integer.parseInt(st.nextToken()) + garageLocations[i - 1];
        // 각 정비소의 정비 시간
        int[] repairTimes = new int[garages + 2];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < repairTimes.length - 1; i++)
            repairTimes[i] = Integer.parseInt(st.nextToken());
        
        // int의 최대값 이하로 주어지므로, 최대값을 int로 설정하면 계산 오류가 날 수 있음에 유의한다.
        // 따라서 long 값으로 계산
        long[] minDistances = new long[garages + 2];
        // 이전에 들렸던 정비소
        int[] preLocations = new int[garages + 2];
        // 값 초기화
        for (int i = 0; i < minDistances.length; i++) {
            minDistances[i] = Long.MAX_VALUE;
            preLocations[i] = -1;
        }
        minDistances[0] = 0;
        
        // 출발 지점에서부터 시작하여
        for (int i = 0; i < garageLocations.length - 1; i++) {
            // i 지점에서 다음에 들릴 수 있는 모든 정비소를 체크한다.
            for (int j = i + 1; j < garageLocations.length && garageLocations[j] - garageLocations[i] <= maxDistance; j++) {
                // i -> j지점으로 도달하는 경우가 기존에 계산되어있던 정비 시간보다
                // 더 적은 시간을 갖고 있다면
                // 최소 정비 시간과 이전 정비소 값을 고쳐준다.
                if (minDistances[j] > minDistances[i] + repairTimes[j]) {
                    minDistances[j] = minDistances[i] + repairTimes[j];
                    preLocations[j] = i;
                }
            }
        }

        // 모든 계산이 끝났으므로 답안을 작성한다.
        StringBuilder sb = new StringBuilder();
        // 도착 지점에서부터 stack을 통해 들렸던 정비소의 개수와 정비소들을 찾아간다.
        sb.append(minDistances[minDistances.length - 1]).append("\n");
        Stack<Integer> stack = new Stack<>();
        stack.push(preLocations[preLocations.length - 1]);
        // 이전 정비소의 값이 -1(= 출발지점)이 나올 때까지
        // 도착지점에서부터 거꾸로 거슬러 올라간다.
        while (preLocations[stack.peek()] != -1)
            stack.push(preLocations[stack.peek()]);
        // 출발지점 제외
        stack.pop();
        
        // 경유한 정비소들의 개수
        sb.append(stack.size()).append("\n");
        // 모든 정비소들을 스택에서 뽑아내며(= 정비소들을 들렸던 순서) 기록한다.
        while (!stack.isEmpty())
            sb.append(stack.pop()).append(" ");
        sb.deleteCharAt(sb.length() - 1);

        // 전체 답안 출력.
        System.out.println(sb);
    }
}