/*
 Author : Ruel
 Problem : Baekjoon 13306번 트리
 Problem address : https://www.acmicpc.net/problem/13306
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13306_트리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    static int[] parents;
    static int[] tempParents;

    public static void main(String[] args) throws IOException {
        // n개의 정점과 q개의 질의 그리고 각 정점과 부모 노드 간의 연결을 끊는 명령 n-1개가 주어진다
        // n개의 정점들은 트리 형태를 이루고 있으며, 1번은 루트 노드이다.
        // 질의와 명령은 섞여서 주어진다
        // 그 형태는 0 b, 1 c d 형태로 주어진다
        // 0 b는 b와 b의 부모 노드 간의 연결은 끊는 명령이고
        // 1 c d는 c와 d가 직간접적으로 연결되어있는지를 물어보는 질의이다
        //
        // 분리 집합 문제인데, 연결을 끊어가는 순서대로 진핼할 수는 없다
        // 연결을 끊어가려면 경로 압축을 사용할 수 없고, 그럴 경우 동일 작업 반복을 통한 시간 복잡도가 많이 증가한다.
        // 이 문제에서 키가 되는 부분은 결국 모든 노드들은 모두 부모 노드와 연결이 끊겨진다는 점이다
        // 따라서 질의와 명령을 스택에 담아 역순으로 처리해나가면서
        // 끊는 명령을 거꾸로 잇는 명령으로 바꿔서 처리하면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        init(n);
        // 부모 노드의 값들을 저장.
        for (int i = 0; i < n - 1; i++)
            tempParents[i + 2] = Integer.parseInt(br.readLine());

        // 명령과 질의를 스택에 담아 역순으로 처리한다.
        Stack<String> stack = new Stack<>();
        for (int i = 0; i < n + q - 1; i++)
            stack.push(br.readLine());

        // 질의에 대한 답 또한 처리되는 역순으로 출력해야한다.
        Stack<String> answer = new Stack<>();
        while (!stack.isEmpty()) {
            st = new StringTokenizer(stack.pop());

            int x = Integer.parseInt(st.nextToken());
            // 부모 노드와 연결을 끊는 명령이지만 역순으로 처리하기 떄문에
            // 거꾸로 잇는 명령으로 바꿔준다.
            if (x == 0) {
                int b = Integer.parseInt(st.nextToken());
                // b의 부모 노드에 원래 값을 넣는다.
                parents[b] = tempParents[b];
            } else {        // c와 d가 직간접적으로 연결되어있는지 물어보는 질의.
                int c = Integer.parseInt(st.nextToken());
                int d = Integer.parseInt(st.nextToken());

                // c와 d가 같은 최초 조상 노드를 갖는다면, 두 노드는 연결되어 있다 볼 수 있다.
                if (findParent(c) == findParent(d))
                    answer.push("YES");
                // 그렇지 않다면 연결되어 있지 않은 경우.
                else
                    answer.push("NO");
            }
        }
        StringBuilder sb = new StringBuilder();
        while (!answer.isEmpty())
            sb.append(answer.pop()).append("\n");
        System.out.println(sb);
    }

    static void init(int n) {
        parents = new int[n + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        tempParents = new int[n + 1];
    }

    static int findParent(int n) {
        if (n == parents[n])
            return n;
        return parents[n] = findParent(parents[n]);
    }
}