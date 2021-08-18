/*
 Author : Ruel
 Problem : Baekjoon 2357번 최솟값과 최댓값
 Problem address : https://www.acmicpc.net/problem/2357
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 최솟값과최댓값;

import java.util.Scanner;

public class Main {
    static int[] maxTree;
    static int[] minTree;
    static final int MAX = 1_000_000_001;

    public static void main(String[] args) {
        // 세그먼트 트리를 이용하여 최소, 최댓값을 구하는 문제!
        // 두개의 트리를 만들어서 하나에는 최솟값, 최댓값을 저장해주자.

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        int size = (int) Math.pow(2, Math.ceil(Math.log(n) / Math.log(2)) + 1);
        maxTree = new int[size];
        minTree = new int[size];

        for (int i = 0; i < n; i++)
            inputValue(1, 0, n - 1, i, sc.nextInt());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            int[] answer = getValue(1, 0, n - 1, sc.nextInt() - 1, sc.nextInt() - 1);
            sb.append(answer[0]).append(" ").append(answer[1]).append("\n");
        }
        System.out.println(sb);
    }

    static int[] inputValue(int node, int start, int end, int target, int value) {      // 값을 저장하는 메소드!
        if (start == end) {         // 원하는 노드에 도착했다면 값을 저장하고
            maxTree[node] = minTree[node] = value;
            return new int[]{value, value};     // 최솟값, 최댓값 모두 자신을 넣어 보낸다.
        }
        int middle = (start + end) / 2;

        if (target <= middle) {     // target이 중간값보다 작은 경우 -> 왼쪽 자식 노드를 타자
            int[] returned = inputValue(node * 2, start, middle, target, value);    // 왼쪽 자식 노드에서 돌아온 값
            minTree[node] = Math.min(returned[0], minTree[node * 2 + 1]);   // 왼쪽 자식 노드에서 돌아온 값과, 오른쪽 자식 노드의 값 중 작은 값을 minTree에 저장!
            maxTree[node] = Math.max(returned[1], maxTree[node * 2 + 1]);   // 마찬가지로 큰 값을 maxTree에 저장
        } else {        // target이 중간값보다 큰 경우 -> 오른쪽 자식 노드를 타자!
            int[] returned = inputValue(node * 2 + 1, middle + 1, end, target, value);      // 위와 같은 연산!
            minTree[node] = Math.min(returned[0], minTree[node * 2]);
            maxTree[node] = Math.max(returned[1], maxTree[node * 2]);
        }
        return new int[]{minTree[node], maxTree[node]};     // 마지막으로 현재 노드의 최솟값, 최댓값을 반환
    }

    static int[] getValue(int node, int start, int end, int targetStart, int targetEnd) {
        if (start == targetStart && end == targetEnd)       // 범위가 일치한다면 현재 노드의 값 반환!
            return new int[]{minTree[node], maxTree[node]};

        int middle = (start + end) / 2;

        if (targetStart > middle)       // 목적지의 start가 중간값보다 큰 경우 오른쪽 자식 노드를 타자!
            return getValue(node * 2 + 1, middle + 1, end, targetStart, targetEnd);
        else if (targetEnd <= middle)           // 반대인 경우는 왼쪽 자식 노드!
            return getValue(node * 2, start, middle, targetStart, targetEnd);
        else {      // 둘 다 아닌 경우는, 중간값을 기준으로 분할해서 값을 받자
            int[] leftReturned = getValue(node * 2, start, middle, targetStart, middle);        // targetStart ~ middle 까지의 최솟값, 최댓값을 받고
            int[] rightReturned = getValue(node * 2 + 1, middle + 1, end, middle + 1, targetEnd);       // middle ~ targetEnd까지의 최솟값 최댓값을 받아
            return new int[]{Math.min(leftReturned[0], rightReturned[0]), Math.max(leftReturned[1], rightReturned[1])};         // 두 최솟값 중 더 작은 값을, 두 최댓값 중 더 큰 값을 반환해주자.
        }
    }
}