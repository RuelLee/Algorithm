/*
 Author : Ruel
 Problem : Baekjoon 26087번 피보나치와 마지막 수열과 쿼리
 Problem address : https://www.acmicpc.net/problem/26087
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_26087_피보나치와마지막수열과쿼리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static final int LIMIT = 1_000_000_007;
    static int[] parents, ranks, lastIdxes;

    public static void main(String[] args) throws IOException {
        // 피보나치 수는 1로 시작한다. 0번째 수는 1이고, 1번째 수도 1이다.
        // 이후 n번째 수는 n-1번째와 n-2번째 수의 합으로 나타낸다.
        // 수가 너무 커지므로, 피보나치 수를 10^9 + 7의 값으로 나눈 나머지로 한다.
        // 모든 값이 0인 길이 n의 수열이 주어진다.
        // 다음과 같은 쿼리 q개를 처리한다
        // l r : 수열의 l번째 위치부터 r번째 위치까지 피보나치 수를 첫번째부터 채워나간다.
        // 순서대로 쿼리들을 처리한 결과를 출력하라
        //
        // 분리 집합, 오프라인 쿼리 문제
        // 유사한 문제를 풀었던 경험으로 정렬과 우선순위큐를 통해 해결하려하였으나
        // n과 q가 최대 100만으로 주어져, 시간 초과가 났다.
        // 일단 쿼리들은 나중에 처리되는 것이 최종적으로 남아있는다.
        // 따라서 우선적으로 마지막 쿼리들부터 값을 채워나가며
        // 이미 값이 채워진 순서에 대해서는 값을 변경하지 않는다.
        // 이미 값이 채워진 순서에 대해서 분리집합을 통해 하나의 집합으로 묶고
        // 해당 집합의 마지막 순서를 관리하며, 빠르게 쿼리들을 처리하자
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 길이 n의 수열, q개의 쿼리
        int n = Integer.parseInt(br.readLine());
        int q = Integer.parseInt(br.readLine());
        
        // 쿼리 입력
        int[][] queries = new int[q][2];
        StringTokenizer st;
        for (int i = 0; i < queries.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < queries[i].length; j++)
                queries[i][j] = Integer.parseInt(st.nextToken());
        }

        // 피보나치 수들을 미리 구해놓는다.
        int[] fibonacci = new int[n + 1];
        fibonacci[0] = fibonacci[1] = 1;
        for (int i = 2; i < fibonacci.length; i++)
            fibonacci[i] = (fibonacci[i - 1] + fibonacci[i - 2]) % LIMIT;
        
        // 분리 집합을 위한 초기화
        parents = new int[n + 1];
        ranks = new int[n + 1];
        lastIdxes = new int[n + 1];
        for (int i = 1; i <= n; i++)
            parents[i] = lastIdxes[i] = i;
        
        // 답
        int[] answer = new int[n + 1];
        // 쿼리들을 역순으로 처리한다.
        for (int i = queries.length - 1; i >= 0; i--) {
            // 해당 쿼리의 시작 idx
            int idx = queries[i][0];
            // idx가 쿼리의 마지막 순서가 될 때까지
            while (idx <= queries[i][1]) {
                // 만약 이미 값이 들어있다면
                // 해당하는 집합의 마지막 idx + 1 위치로 idx를 옮긴다.
                if (answer[idx] != 0)
                    idx = lastIdxes[findParent(idx)] + 1;
                else {
                    // 값이 0인 경우는
                    // 해당 위치에 해당하는 피보나치 수를 대입하고
                    answer[idx] = fibonacci[idx - queries[i][0] + 1];
                    // 현재 처리하는 쿼리의 첫번째 순서의 집합과
                    // 현재 idx의 집합이 서로 다른 집합이라면 하나로 묶고
                    if (findParent(queries[i][0]) != findParent(idx))
                        union(queries[i][0], idx);
                    // idx 증가
                    idx++;
                }
            }
        }

        // 답안 작성
        StringBuilder sb = new StringBuilder();
        sb.append(answer[1]);
        for (int i = 2; i < answer.length; i++)
            sb.append(" ").append(answer[i]);
        // 전체 답안 출력
        System.out.println(sb);
    }

    // a와 b가 속한 집합을 하나로 묶는다.
    static void union(int a, int b) {
        // 두 집합의 대표
        int pa = findParent(a);
        int pb = findParent(b);

        // 두 집합의 대표 rank를 비교하여
        // 더 rank가 낮은 쪽을 높은 쪽에 속하게 한다.
        // 이 때, lastIdx값도 갱신
        if (ranks[pa] >= ranks[pb]) {
            lastIdxes[pa] = Math.max(lastIdxes[pa], lastIdxes[pb]);
            parents[pb] = pa;
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else {
            lastIdxes[pb] = Math.max(lastIdxes[pa], lastIdxes[pb]);
            parents[pa] = pb;
        }
    }

    // n이 속한 집합의 대표를 찾는다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }
}