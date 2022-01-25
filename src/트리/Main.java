/*
 Author : Ruel
 Problem : Baekjoon 4256번 트리
 Problem address : https://www.acmicpc.net/problem/4256
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 트리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[] preorder;
    static int[] inorder;
    static int order;
    static Queue<Integer> queue;

    public static void main(String[] args) throws IOException {
        // 전위순회와 중위순회 값이 주어질 때, 후위순회 값을 찾아라.
        // 전위순회는 부모 노드 -> 왼쪽 자식 노드 -> 오른쪽 자식 노드
        // 중위순회는 왼쪽 자식 노드 -> 부모 노드 -> 오른쪽 자식 노드
        // 후위순회는 왼쪽 자식 노드 -> 오른쪽 자식 노드 -> 부모 노드
        // 전위순회와 중위순회가 주어지므로, 전위순회에 먼저 나온 값을 통해, 중위순회에서 왼쪽 자식 트리와 오른쪽 자식 트리 그리고 부모노드로 구분할 수 있다
        // 따라서 전위순회한 값을 차례대로 찾아가면서, 해당 값이 중위순회에서 나온 순서를 기점으로 왼쪽과 오른쪽으로 나눈다.
        // 그리고 왼쪽 자식트리에 대해서 재귀적으로 찾고, 오른쪽 자식 트리에 대해서 재귀적으로 찾고, 마지막으로 부모노드를 붙이는 방식으로 후위순회를 구해나갈 수 있다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringBuilder answer = new StringBuilder();
        int testCase = Integer.parseInt(br.readLine());
        for (int t = 0; t < testCase; t++) {
            int n = Integer.parseInt(br.readLine());
            preorder = new int[n + 1];
            inorder = new int[n + 1];

            StringTokenizer st = new StringTokenizer(br.readLine());
            // 전위순회를 순서대로 담는다.
            for (int i = 1; i < preorder.length; i++)
                preorder[i] = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            // 중위순회는 해당 숫자가 몇번째로 나오는지 순서를 담는다.
            for (int i = 1; i < n + 1; i++)
                inorder[Integer.parseInt(st.nextToken())] = i;

            order = 1;      // 순서는 1부터.
            queue = new LinkedList<>();     // queue에 담는다.
            getPostorder(1, n);         // 처음 범위는 1부터 n까지.
            while (!queue.isEmpty())            // 쌓인 queue의 숫자를 차례대로 뽑아내면 정답.
                answer.append(queue.poll()).append(" ");
            answer.append("\n");
        }
        System.out.println(answer);
    }

    static void getPostorder(int start, int end) {
        if (order >= preorder.length || start > end)        // 만약 order가 n범위를 벗어나거나, 범위가 start가 end보다 큰 값이 주어진다면 그대로 종료.
            return;

        // 그렇지 않다면 이번 순서는 preorder[order]의 차례.
        // 부모 노드는 num(= preorder[order])이다.
        int num = preorder[order++];
        // 해당 노드를 기준으로 start ~ end를 둘로 나눈다
        // start ~ inorder[num] - 1까지는 왼쪽 서브 트리. 해당 구간에 대해 후위 순회 값을 구한다.
        // 왼쪽 서브트리가 없다면, start > end인 값이 가게 될 것이다.
        getPostorder(start, inorder[num] - 1);
        // inorder[num] + 1부터 end까지는 오른쪽 서브 트리. 역시 마찬가지.
        // 오른쪽 서브 트리가 없다면 start > end인 값이 가게 될 것이다.
        getPostorder(inorder[num] + 1, end);
        // 최종적으로 부모노드를 queue에 담아준다.
        queue.offer(num);
    }
}