/*
 Author : Ruel
 Problem : Baekjoon 16404번 주식회사 승범이네
 Problem address : https://www.acmicpc.net/problem/16404
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16404_주식회사승범이네;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static List<List<Integer>> child;
    static int[] newIdx, lastSubTreeIdx, fenwickTree;
    static int cnt = 1;

    public static void main(String[] args) throws IOException {
        // n명으로 이루어진 회사가 주어진다.
        // 각각은 사수를 갖고 있으며, 1번 직원은 사수가 없는 직원이다.
        // 어떤 직원이 i의 이익 혹은 손해가 발생할 경우, 부하 직원들 또한 같이 i의 이익 혹은 손해가 발생한다.
        // m개의 다음 두 쿼리를 처리하라
        // 1 i w : 직원 i가 w만큼 이익 혹은 손해를 본다.
        // 2 i : 직원 i의 이익과 손해 합을 출력한다.
        //
        // 오일러 경로 테크닉, 펜윅 트리
        // 오랜만에 풀어본 오일러 경로 테크닉 복습
        // 직접적 연관이 있는 사수와 부사수를 연속한 형태로 만들면
        // 이를 펜윅 트리를 통해 누적합 처리로 빠르게 처리할 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 직원, m개의 쿼리
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 부하 직원
        child = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            child.add(new ArrayList<>());

        st = new StringTokenizer(br.readLine());
        st.nextToken();
        // 부하 직원 입력
        for (int i = 1; i < n; i++)
            child.get(Integer.parseInt(st.nextToken())).add(i + 1);

        // 오일러 경로 테크틱
        // 펜윅 트리에서의 idx
        newIdx = new int[n + 1];
        // 자신의 부하 직원의 마지막 idx
        lastSubTreeIdx = new int[n + 1];
        allocateNewIdx(1);

        fenwickTree = new int[n + 2];
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < m; j++) {
            st = new StringTokenizer(br.readLine());
            int o = Integer.parseInt(st.nextToken());
            int i = Integer.parseInt(st.nextToken());
            // 1번 쿼리인 경우
            if (o == 1) {
                int w = Integer.parseInt(st.nextToken());
                // 자신부터, 마지막 부하 직원까지 모두 +w
                addValue(newIdx[i], w);
                // 마지막 부하 직원 +1에는 -w를 하여, 이후에는 반영되지 않도록 함
                addValue(lastSubTreeIdx[i] + 1, -w);
            } else  // 2번 쿼리인 경우. i번 직원에 해당하는 누적합을 기록
                sb.append(getSum(newIdx[i])).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }

    // 펜윅 트리, 누적합 값 가져오기
    static int getSum(int idx) {
        int sum = 0;
        while (idx > 0) {
            sum += fenwickTree[idx];
            idx -= (idx & -idx);
        }
        return sum;
    }

    // 펜윅트리, idx에 val 만큼 값 추가
    static void addValue(int idx, int val) {
        while (idx < fenwickTree.length) {
            fenwickTree[idx] += val;
            idx += (idx & -idx);
        }
    }

    // 오일러 경로 테크틱
    // dfs로 자신과 자신을 루트로 하는 서브트리 노드들에게 연속된 값을 배정.
    static int allocateNewIdx(int oldIdx) {
        newIdx[oldIdx] = lastSubTreeIdx[oldIdx] = cnt++;

        for (int next : child.get(oldIdx))
            lastSubTreeIdx[oldIdx] = Math.max(lastSubTreeIdx[oldIdx], allocateNewIdx(next));
        return lastSubTreeIdx[oldIdx];
    }
}