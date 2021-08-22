/*
 Author : Ruel
 Problem : Baekjoon 2263번 트리의 순회
 Problem address : https://www.acmicpc.net/problem/2263
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 트리의순회;

import java.util.Scanner;

public class Main {
    static StringBuilder sb;
    static int[] inorder;
    static int[] postorder;
    static int[] inorderIdx;

    public static void main(String[] args) {
        // 트리에서 중위순회, 후위순회가 주어질 때, 전위순회를 구하여라!
        // 후위순회에서 마지막 노드가 루트 노드인 점과, 중위 순회에서 루트 노드 앞쪽이 왼쪽 자식 트리인 점을 이용하자!
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        inorder = new int[n];
        postorder = new int[n];
        inorderIdx = new int[n + 1];
        sb = new StringBuilder();

        for (int i = 0; i < n; i++)
            inorderIdx[inorder[i] = sc.nextInt()] = i;
        for (int i = 0; i < n; i++)
            postorder[i] = sc.nextInt();

        findPreorder(0, n - 1, 0, n - 1);
        System.out.println(sb);
    }

    static void findPreorder(int inorderFirst, int inorderLast, int postorderFirst, int postorderLast) {
        if (inorderFirst > inorderLast || postorderFirst > postorderLast)   // 범위가 벗어나는 순간 아웃
            return;

        sb.append(postorder[postorderLast]).append(" ");    // 루트 노드!
        int inorderRootIdx = inorderIdx[postorder[postorderLast]];  // 중위순회에서 루트 노드의 위치를 가져오자.
        int leftNodeNum = inorderRootIdx - inorderFirst;        // 그 때 왼쪽 자식 트리의 노드의 개수

        // inorderFirst 부터, inorderRootIdx -1 까지가 중위 순회에서 왼쪽 자식 트리
        // postorderFirst부터, postorderFirst + 왼족 자식 트리의 노드 개수 - 1 까지가 후위 순회에서 왼쪽 자식 트리
        // 여기가 왼쪽 자식 트리
        findPreorder(inorderFirst, inorderRootIdx - 1, postorderFirst, postorderFirst + leftNodeNum - 1);

        // inorderRootIdx + 1부터, inorderLast까지가 후위 순회에서 오른쪽 자식 트리
        // postorderFirst + leftNodeNum 부터, postorderLast - 1 까지가 후위 순회에서 오른쪽 자식 트리
        // 여기가 오른쪽 자식 트리
        findPreorder(inorderRootIdx + 1, inorderLast, postorderFirst + leftNodeNum, postorderLast - 1);
    }
}