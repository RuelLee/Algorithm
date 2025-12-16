/*
 Author : Ruel
 Problem : Baekjoon 14268번 회사 문화 2
 Problem address : https://www.acmicpc.net/problem/14268
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14268_회사문화2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int counter = 1;
    static int[] eulerians, lastSubtreeNode, fenwickTree;

    public static void main(String[] args) throws IOException {
        // n명의 사원과 m개의 쿼리가 주어진다
        // 이 회사에는 내리 칭찬 문화가 있어 자신이 칭찬을 받으면 직속 부하들을 연쇄적으로 칭찬한다.
        // 쿼리의 형태는
        // 1 i w : i번째 직원이 w만큼 칭찬을 받는다
        // 2 i : i번째 직원이 칭찬 받은 정도를 출력한다
        //
        // 오일러 경로 + 펜윅 트리 문제
        // 어제 처음 풀어본 오일러 경로 문제를 잊기 전에 다시 한번 풀어보았다.
        // 오일러 경로 테크닉의 핵심은
        // 비연속적인 서브 트리 노드들을
        // 연속적으로 바꿈으로써, 펜윅 트리와 같은 누적합을 사용할 수 있게 만드는데 있었다.
        // 이번 문제 역시 오일러 경로 테크닉을 통해 새로운 노드 번호를 할당하고
        // 이를 바탕으로 펜윅 트리를 만들어 누적합으로 칭찬 정도를 처리할 것이다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 자식 노드들을 관리할 리스트
        List<List<Integer>> child = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            child.add(new ArrayList<>());

        st = new StringTokenizer(br.readLine());
        st.nextToken();
        // i 사원의 직속 상사가 주어진다.
        for (int i = 1; i < n; i++)
            child.get(Integer.parseInt(st.nextToken())).add(i + 1);
        
        // 할당되는 새로운 노드 번호
        eulerians = new int[n + 1];
        // 자신이 루트인 서브트리의 마지막 노드 번호
        lastSubtreeNode = new int[n + 1];
        // 새로운 노드 번호와 서브 트리의 마지막 노드 번호 계산
        allocateEulerian(1, child);
        
        // 펜윅 트리를 통한 누적합 처리
        fenwickTree = new int[n + 2];

        StringBuilder sb = new StringBuilder();
        // 쿼리 처리
        for (int j = 0; j < m; j++) {
            st = new StringTokenizer(br.readLine());
            int order = Integer.parseInt(st.nextToken());
            int i = Integer.parseInt(st.nextToken());
            // 칭찬을 받는 쿼리라면
            if (order == 1) {
                int w = Integer.parseInt(st.nextToken());
                // i사원의 새로운 노드 번호에 해당하는 곳에 w 칭찬
                inputValue(eulerians[i], w);
                // 자신을 루트를 갖는 서브 트리의 마지막 번호 + 1에 -w
                inputValue(lastSubtreeNode[i] + 1, -w);
            } else      // i번 사원이 받은 총 칭찬 출력
                sb.append(getSum(eulerians[i])).append("\n");
        }
        System.out.print(sb);
    }
    
    // 펜윅 트리의 1 ~ n까지의 누적합 출력
    static int getSum(int n) {
        int sum = 0;
        while (n > 0) {
            sum += fenwickTree[n];
            n -= (n & -n);
        }
        return sum;
    }

    // n번에 값을 추가한다.
    static void inputValue(int n, int value) {
        while (n < fenwickTree.length) {
            fenwickTree[n] += value;
            n += (n & -n);
        }
    }

    // DFS를 통해 각 노드에 새로운 번호를 할당하고
    // 각 노드를 루트로 갖는 서브 트리의 마지막 노드 번호를 계산한다.
    static int allocateEulerian(int n, List<List<Integer>> child) {
        eulerians[n] = counter++;

        lastSubtreeNode[n] = eulerians[n];
        for (int c : child.get(n))
            lastSubtreeNode[n] = Math.max(lastSubtreeNode[n], allocateEulerian(c, child));

        return lastSubtreeNode[n];
    }
}