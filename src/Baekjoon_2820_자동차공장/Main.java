/*
 Author : Ruel
 Problem : Baekjoon 2820번 자동차 공장
 Problem address : https://www.acmicpc.net/problem/2820
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2820_자동차공장;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int counter = 1;
    static int[] eulerians, endSubTree, fenwickTree;

    public static void main(String[] args) throws IOException {
        // n명의 임직원이 있는 공장이 있다.
        // 모든 직원은 자신의 모든 부하 직원들의 월급을 조정할 수 있다.
        // n명의 임직원의 임금과 상사의 번호가 주어진다.
        // 두 종류의 쿼리가 있는데
        // p a x -> a의 모든 부하의 월급을 x만큼 증가시킨다
        // u a -> a의 월급을 출력한다
        // m개의 쿼리를 처리한 결과를 출력하라
        //
        // 오일러 경로, 펜윅 트리
        // 상사와 부하 직원을 트리로 표현한다했을 때,
        // 최악의 편향트리일 경우, 탐색하는데만 시간 복잡도가 O(n)이 걸릴 수 있다.
        // 따라서 오일러 경로를 통해 서로 간에 인접한 배열로 표시할 수 있다.
        //                  1
        //          2       3       4
        //                              5
        // 와 같이 주어진다면
        // BFS 탐색을 통해 새로운 노드 번호와 각 서브트리의 마지막 노드 번호를 찾아낸다
        // 새로운 노드 번호는 [1, 2, 3, 4, 5]와 같이 주어지고
        // 서브트리의 마지막 노드 번호는 [5, 2, 3, 5, 5]로 계산된다.
        // 1번 노드가 루트인 서브트리는 5번이 마지막이고, 2번이 루트인 서브트리는 2, ...
        // 따라서 위와 같은 경우에는 펜윅 트리를 활용한 누적합으로 쿼리들을 처리할 수 있게 된다.
        // 1번 직원이 부하직원들의 월급을 x만큼 조절하면
        // 자신의의 뒷번호인 2번부터, 자신의 마지막 서브트리 노드 번호인 5번까지 영향을 받고, 6번부터는 영향을 받지 않는다.
        // 펜윅트리를 활용한 누적합으로 2번에 +x를 표시하고 6번에 -x를 표시하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 주어지는 원래 임금
        int[] salaries = new int[n + 1];
        salaries[1] = Integer.parseInt(br.readLine());

        List<List<Integer>> child = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            child.add(new ArrayList<>());
        for (int i = 2; i < salaries.length; i++) {
            st = new StringTokenizer(br.readLine());
            // i번 직원의 임금과 상사
            salaries[i] = Integer.parseInt(st.nextToken());
            child.get(Integer.parseInt(st.nextToken())).add(i);
        }
        
        // 오일러를 통한 새로운 노드 번호
        eulerians = new int[n + 1];
        // 자신이 루트인 서브트리의 마지막 노드 번호
        endSubTree = new int[n + 1];        
        allocateEulerian(1, child);
        
        // 1 ~ n번까지 활용하기 위해서는 -를 표시해줄 n + 1번도 필요하기 때문에
        // 총 n + 2의 공간을 선언
        fenwickTree = new int[n + 2];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            char order = st.nextToken().charAt(0);
            int a = Integer.parseInt(st.nextToken());
            // 임금을 조절하는 쿼리일 경우
            if (order == 'p') {
                int x = Integer.parseInt(st.nextToken());
                // 자신의 새로운 노드 번호 + 1번 부터
                // 서브 트리의 마지막 노드까지는 영향을 받고
                // 마지막 노드 + 1번부터는 영향을 받지 않으므로
                // 새로운 노드 번호 + 1에 +x
                inputValue(eulerians[a] + 1, x);
                // 서브 트리의 마지막 번호 + 1에 -x
                inputValue(endSubTree[a] + 1, -x);
            } else      // 임금을 출력하는 쿼리일 경우, 자신의 원래 임금 + 자신의 새로운 노드 번호까지의 누적합을 더해 출력한다.
                sb.append(salaries[a] + getSum(eulerians[a])).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }

    // 펜윅트리에서 n번의 값을 value만큼 조정
    static void inputValue(int n, int value) {
        while (n < fenwickTree.length) {
            fenwickTree[n] += value;
            n += (n & -n);
        }
    }
    
    // n번까지의 누적합 출력
    static int getSum(int n) {
        int sum = 0;
        while (n > 0) {
            sum += fenwickTree[n];
            n -= (n & -n);
        }
        return sum;
    }

    // 오일러 경로를 통해 새로운 노드 번호를 할당하고,
    // 자신을 루트로 갖는 서브 트리의 마지막 노드 번호를 기록한다.
    static int allocateEulerian(int n, List<List<Integer>> child) {
        eulerians[n] = counter++;
        
        // 단말 노드일 경우 자기 자신이 마지막 노드
        endSubTree[n] = eulerians[n];
        // 자식 노드들을 모두 탐색하며 가장 마지막 노드 번호를 가져온다.
        for (int c : child.get(n))
            endSubTree[n] = Math.max(endSubTree[n], allocateEulerian(c, child));
        // 자신의 서브트리 중 가장 마지막 노드 번호를 반환.
        return endSubTree[n];
    }
}