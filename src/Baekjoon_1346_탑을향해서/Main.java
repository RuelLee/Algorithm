/*
 Author : Ruel
 Problem : Baekjoon 1346번 탑을 향해서
 Problem address : https://www.acmicpc.net/problem/1346
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1346_탑을향해서;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[][] stairs;
    static int[] group, ancestors;
    static int k, nodeCnt = 1, groupCnt = 1;

    public static void main(String[] args) throws IOException {
        // n개의 계단이 주어진다.
        // 각 계단에 놓여있는 캔디의 수, 왼쪽 끝 x좌표, y좌표, 계단의 길이가 주어진다.
        // 같은 끝 점을 공유하는 계단은 없다.
        // 계단은 서로 거리가 k이하인 경우, 건너뛸 수 있으며, y가 같거나 더 큰 계단으로만 이동이 가능하다.
        // 처음 좌표는 (0, 0)이고, 첫 점프는 자유롭게 x축 위에서 이동하며 할 수 있다.
        // 얻을 수 있는 최대 사탕의 수는?
        //
        // 강한 연결 요소, DP 문제
        // 먼저 y가 같으면서, 거리가 k 이하인 계단 사이에는 자유롭게 이동이 가능하다.
        // 따라서, 강한 연결 요소를 이용하여 자유롭게 이동이 가능한 계단들을 하나의 그룹으로 묶는다.
        // 그룹 중 하나의 계단에 닿는다면, 해당 그룹의 사탕을 모두 얻을 수 있다.
        // 그 후
        // y가 k이하인 계단들에서만 시작이 가능하며
        // 얻을 수 있는 최대 사탕의 수를 dp를 통해 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 계단, 이동 가능한 거리 k
        int n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        
        // 계단
        stairs = new int[n][4];
        for (int i = 0; i < stairs.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < stairs[i].length; j++)
                stairs[i][j] = Integer.parseInt(st.nextToken());
        }
        // 편의상 y좌표에 따라 오름차순 정렬
        Arrays.sort(stairs, (o1, o2) -> {
            if (o1[2] == o2[2])
                return Integer.compare(o1[1], o2[1]);
            return Integer.compare(o1[2], o2[2]);
        });
        
        // 강한 연결 요소를 위한 초기화
        // 각 계단이 속한 그룹
        group = new int[n];
        // 각 계단의 이동 가능한 최소 노드 번호
        ancestors = new int[n];
        Stack<Integer> stack = new Stack<>();
        // tarjan 알고리즘.
        // 아직 그룹이 결정되지 않은 계단을에 대해 모두 시행
        for (int i = 0; i < n; i++) {
            if (group[i] == 0) {
                stack.clear();
                tarjan(i, stack);
            }
        }
        
        // 각 그룹에 해당하는 계단들의 사탕 합
        int[] groupSums = new int[groupCnt];
        for (int i = 0; i < stairs.length; i++)
            groupSums[group[i]] += stairs[i][0];
        
        // 얻을 수 있는 최대 사탕의 수
        int answer = 0;
        // 초기 시작점에서 닿을 수 있는 그룹
        boolean[] reachable = new boolean[groupCnt];
        // dp
        int[] maxCandies = new int[groupCnt];
        // 계단들 중, 시작점에서 닿을 수 있는 계단이 속한 그룹들에 대해
        // 사탕의 값 갱신 및 reachable 표시
        for (int i = 0; i < stairs.length; i++) {
            if (stairs[i][2] <= k) {
                answer = Math.max(answer, maxCandies[group[i]] = groupSums[group[i]]);
                reachable[group[i]] = true;
            }
        }
        
        // 계단들에 대해 이동하는 경우들을 계산
        for (int i = 0; i < n; i++) {
            // 닿을 수 없는 그룹의 계단은 건너뜀.
            if (!reachable[group[i]])
                continue;
            
            // i와 j의 거리가 k 이하이고, j 계단이 i 계단보다 더 높은 경우
            for (int j = i + 1; j < n; j++) {
                if (stairs[j][2] > stairs[i][2] && distancePow(i, j) <= k * k) {
                    // j가 속한 그룹이 얻을 수 있는 최대 사탕의 수와
                    // 현재 i가 속한 그룹이 얻은 최대 사탕의 수 + j 그룹의 사탕의 수를 비교하여 더 큰 값을 남김
                    answer = Math.max(answer, maxCandies[group[j]] = Math.max(maxCandies[group[j]], maxCandies[group[i]] + groupSums[group[j]]));
                    // 닿을 수 있음 체크
                    reachable[group[j]] = true;
                }
            }
        }
        // 답 출력
        System.out.println(answer);
    }
    
    // tarjan 알고리즘
    static int tarjan(int idx, Stack<Integer> stack) {
        // 현재 노드의 방문 순서와 도달 가능 최소 조상를 초기화
        int nodeNum = ancestors[idx] = nodeCnt++;
        stack.push(idx);

        for (int i = 0; i < stairs.length; i++) {
            // 이미 강한 연결 요소를 찾은 계단은 건너뜀.
            if (group[i] != 0)
                continue;
            // 그 외의 경우들 중 거리가 k이하이고, idx보다 y 좌표가 더 큰 i에 대해서만
            // 이미 방문했다면 ancestors[i], 미방문이라면 tarjan을 재귀적으로 시행하여 방문 가능 최소 조상의 번호를 가져옴
            else if (distancePow(idx, i) <= k * k && stairs[idx][2] <= stairs[i][2])
                ancestors[idx] = Math.min(ancestors[idx], (ancestors[i] == 0 ? tarjan(i, stack) : ancestors[i]));
        }
        
        // 자신이 방문 가능한 최소 조상인 경우
        // stack에 자신과 상위에 있는 노드들을 하나의 그룹으로 묶음
        if (nodeNum == ancestors[idx]) {
            while (stack.peek() != idx)
                group[stack.pop()] = groupCnt;
            group[stack.pop()] = groupCnt++;
        }
        // 자신의 도달 가능 최소 조상 반환
        return ancestors[idx];
    }
    
    // a와 b 사이의 거리 계산
    static int distancePow(int a, int b) {
        int xDiff = 0;
        // x좌표 상으로 두 계단이 떨어져있는 경우
        // a 계단이 b계단보다 왼쪽에 있는 경우
        if (stairs[a][1] + stairs[a][3] < stairs[b][1])
            xDiff = stairs[b][1] - (stairs[a][1] + stairs[a][3]);
        // b계단이 a계단보다 왼쪽에 있는 경우
        else if (stairs[b][1] + stairs[b][3] < stairs[a][1])
            xDiff = stairs[a][1] - (stairs[b][1] + stairs[b][3]);
        // 두 계단의 x 좌표가 일치하는 구간이 있다면 거리는 0
        
        // y좌표의 차이
        int yDiff = Math.abs(stairs[a][2] - stairs[b][2]);
        // 각각의 제곱의 합을 반환
        return xDiff * xDiff + yDiff * yDiff;
    }
}