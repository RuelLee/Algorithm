/*
 Author : Ruel
 Problem : Baekjoon 11438번 LCA 2
 Problem address : https://www.acmicpc.net/problem/11438
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11438_LCA2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int[] levels;            // 각 노드의 level을 저장할 공간
    static int[][] sparseArray;     // 각 노드의 조상에 대한 희소배열을 기록해둘 공간
    static List<List<Integer>> connection;          // 연결 상태를 저장할 리스트
    static int[] pow2;              // 2의 제곱의 중복연산을 막기 위해 값을 미리 계산해두자.

    public static void main(String[] args) throws IOException {
        // 최소 공통 조상 찾기 + 희소배열 문제
        // 시간이 매우 빡박하다. 심지어 Scanner를 쓰면 안되고 BufferedReader를 사용해야만 했다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        init(n);

        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            connection.get(a).add(b);
            connection.get(b).add(a);
        }
        allocateLevel(1, 1);
        fillSparseArray();

        int m = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            sb.append(findLCA(a, b)).append("\n");
        }
        System.out.println(sb);
    }

    static int findLCA(int a, int b) {
        while (levels[a] != levels[b]) {    // 두 노드의 level이 다르면
            int diff = levels[a] > levels[b] ? levels[a] - levels[b] : levels[b] - levels[a];
            int jump = 0;
            while (pow2[jump + 1] < diff)   // 차이보다 작지만 그 중 가장 큰 2의 제곱을 구하여
                jump++;
            // 그 제곱만큼 조상 노드의 값으로 바꿔준다.
            if (levels[a] > levels[b])
                a = sparseArray[a][jump];
            else
                b = sparseArray[b][jump];
        }
        while (a != b) {        // 두 수의 값이 같아질 때까지(최소 공통 조상이 나올 때까지)
            int jump = sparseArray[0].length - 1;
            while (sparseArray[a][jump] == sparseArray[b][jump] && jump > 0)    // 다른 조상을 갖는 최저 level의 조상으로
                jump--;
            // 점프.
            // 마지막에는 sparseArray[a][0] == sparseArray[b][0]이 같아질테고 이 값으로 바뀔 것이다.
            a = sparseArray[a][jump];
            b = sparseArray[b][jump];
        }
        return a;
    }

    static void fillSparseArray() {
        for (int pow = 1; pow < sparseArray[0].length; pow++) {
            for (int node = 1; node < sparseArray.length; node++) {
                if (levels[node] <= pow2[pow])
                    continue;
                sparseArray[node][pow] = sparseArray[sparseArray[node][pow - 1]][pow - 1];      // node의 2^pow번째 조상은 node의 2^(pow-1)번째 조상의 2^(pow-1)번째 조상과 같다
            }
        }
    }

    static void allocateLevel(int current, int level) {
        levels[current] = level;

        for (int sub : connection.get(current)) {
            if (levels[sub] != 0)       // 자신의 조상이라면 패스
                continue;
            sparseArray[sub][0] = current;      // 자신의 부모노드에 current를 저장하고
            allocateLevel(sub, level + 1);  // 재귀적으로 탐색
        }
    }

    static void init(int n) {
        levels = new int[n + 1];

        pow2 = new int[18];     // 최대 10만의 노드가 있을 수 있으므로, 2^17의 값까지 저장해두자.
        pow2[0] = 1;
        int count = 0;
        for (int i = 1; i < pow2.length; i++) {
            pow2[i] = pow2[i - 1] * 2;
            if (pow2[i] < n)    // 들어온 입력보다 작은 최대 2^count을 구하자
                count = i;
        }
        sparseArray = new int[n + 1][count + 2];        // count+1값까지 저장해야하므로 count+2 까지 할당.
        connection = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connection.add(new ArrayList<>());
    }
}