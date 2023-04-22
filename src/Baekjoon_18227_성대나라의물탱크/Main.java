/*
 Author : Ruel
 Problem : Baekjoon 18227번 성대나라의 물탱크
 Problem address : https://www.acmicpc.net/problem/18227
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18227_성대나라의물탱크;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int[] eulerianNum, lastSubtreeNum, ranks, fenwickTree;
    static int counter = 1;

    public static void main(String[] args) throws IOException {
        // 트리 형태의 나라가 주어진다.
        // a번 도시에 물을 채운다면, 수도부터 a도시까지 잇는 직선 경로에
        // 차례대로 1l, 2l, ..., 물이 채워진다.
        // 5번 도시에 물을 채우고, 1 - 2 - 5 과 같이 이어져있다면
        // 1번 도시에 1l, 2번 도시에 2l, 5번 도시에 3l가 채워지는 식이다.
        // q개의 쿼리가 주어진다.
        // 1 a -> a 도시에 물을 채운다.
        // 2 a -> 현재 a 도시에 얼마만큼의 물이 채워져있는지 출력하라.
        //
        // 오일러 경로 테크닉, 세그먼트 트리
        // 자신을 포함한 서브 트리의 노드들이 총 몇번 1번 쿼리에 대상이 되었는지를 계산하고
        // 자신의 깊이 만큼을 곱해주면 해당 도시에 물이 채워져있는 양이 나온다.
        // 따라서 서브 트리와 관련이 있다면 -> 오일러 경로 테크닉을 통해
        // 서브 트리에 해당하는 노드들을 인접한 배열로 표현하고, 펜윅트리를 통해 누적합 처리를 해주자!
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 도시와
        int n = Integer.parseInt(st.nextToken());
        // 루트 노드 c
        int c = Integer.parseInt(st.nextToken());
        
        // 트리 간선 입력
        List<List<Integer>> child = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            child.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            child.get(x).add(y);
            child.get(y).add(x);
        }

        // 새로 할당되는 오일러 경로 번호.
        // DFS를 통해 할당한다.
        eulerianNum = new int[n + 1];
        // 자신이 루트인 서브 트리의 마지막 노드의 오일러 번호.
        lastSubtreeNum = new int[n + 1];
        // 깊이
        ranks = new int[n + 1];
        ranks[c] = 1;

        // 모든 노드에 오일러 경로 번호를 할당하고,
        // 서브 트리의 마지막 노드의 오일러 경로 번호와
        // 깊이를 계산한다.
        allocateEulerian(c, child, new boolean[n + 1]);
        
        //펜윅 트리로 누적합 처리
        fenwickTree = new int[n + 2];
        
        // q개의 쿼리
        int q = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int order = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());

            // 1번 쿼리라면
            // a에 해당하는 오일러 경로 번호에 1을 추가.
            if (order == 1)
                inputValue(eulerianNum[a], 1);
            else {
                // a에 쌓여있는 물의 양을 출력해야한다면
                // a가 루트인 서브 트리에서 
                // a부터 마지막 노드까지의 노드들의 호출된 합을 구한 뒤
                // a의 깊이를 곱해준 값이 현재 a에 쌓여있는 물의 양
                long sum = (long) (getSum(lastSubtreeNum[a]) - getSum(eulerianNum[a] - 1)) * ranks[a];
                sb.append(sum).append("\n");
            }
        }
        // 전체 답안 출력
        System.out.print(sb);
    }

    // 펜윅트리에서 1 ~ n까지의 합을 구한다.
    static int getSum(int n) {
        int sum = 0;
        while (n > 0) {
            sum += fenwickTree[n];
            n -= (n & -n);
        }
        return sum;
    }

    // 펜윅 트리의 n번에 value만큼의 값을 추가한다.
    static void inputValue(int n, int value) {
        while (n < fenwickTree.length) {
            fenwickTree[n] += value;
            n += (n & -n);
        }
    }

    // 오일러 경로 번호를 할당하고
    // n을 루트로 갖는 서브트리에서 마지막 노드 번호와
    // 깊이를 계산한다.
    static int allocateEulerian(int n, List<List<Integer>> child, boolean[] visited) {
        // 방문 처리
        visited[n] = true;
        // 오일러 경로 번호 할당
        eulerianNum[n] = counter++;

        // n을 루트로 서브트리의 마지막 노드 계산.
        lastSubtreeNum[n] = eulerianNum[n];
        for (int ch : child.get(n)) {
            // n의 자식 노드일 경우
            if (!visited[ch]) {
                // ch는 n보다 1 더 큰 깊이.
                ranks[ch] = ranks[n] + 1;
                // ch를 루트로 갖는 서브 트리에서 가장 노드 번호와
                // n을 루트로 갖는 서브 트리에서 가장 큰 값과 비교 더 큰 값을
                // lastSubtreeNum[n]에 저장
                lastSubtreeNum[n] = Math.max(lastSubtreeNum[n], allocateEulerian(ch, child, visited));
            }
        }
        // n을 루트로 갖는 서브 트리에서 가장 큰 값 반환.
        return lastSubtreeNum[n];
    }
}