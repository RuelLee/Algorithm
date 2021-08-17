/*
 Author : Ruel
 Problem : Baekjoon 11505번 구간 곱 구하기
 Problem address : https://www.acmicpc.net/problem/11505
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 구간곱구하기;

import java.util.Scanner;

public class Main {
    static final long mod = 1_000_000_007;
    static long[] tree;

    public static void main(String[] args) {
        // 세그먼트 트리에 관한 문제
        // 1. 세그먼트 트리에 대한 공간
        // 2. 세그먼트 트리에 값을 입력하는 방법
        // 3. 세그먼트 트리에서 값을 가져오는 방법
        // 1 -> 트리 형태로 구현하여, 말단 노드에 각 값을 넣고, 부모 노드에 자식 노드들의 계산값을 저장한다
        // 말단 노드의 개수가 입력되는 값의 개수보다 크지만 그 중 가장 작은 2의 제곱값을 구해야한다.
        // 따라서 입력 값의 개수를 log2를 취한 뒤 올림을 해주면 트리의 레벨이 나온다. 그 후 배열은 이에 1을 더해 2의 제곱한 값이 된다.
        // 2 -> 말단 노드까지 값을 찾아가야한다!
        // 현재 node, start, end 값을 갖고서 target 위치까지 찾아가야한다
        // 왼쪽 자식 노드를 탄다면(node * 2 ) end 범위가 (start + end) / 2로 바뀌게 되고,
        // 오른쪽 자식 노드를 탄다면(node * 2 + 1) start 범위가 (start + end) / 2 + 1 값으로 바뀌게 된다.
        // 반복하여 start == end == target 이 되는 node를 찾고 그 노드에 값을 넣어준다.
        // 넣으면서 현재 값을 리턴하며, 왼쪽 노드를 탔다면 오른쪽 노드와의 곱을, 오른쪽 노드를 탔다면 왼쪽 노드와의 곱을 노드마다 저장해준다.
        // 3 -> 범위가 주어진다. 이 때 이 범위는 이전에 계산해두었던 값일 수도 있고, 아닐 수도 있다.
        // 가령
        //                  24
        //          2               12
        //      1       2       3       4
        // 라는 세그먼트리를 만들었을 때,
        // 1 ~ 2 범위나 3 ~ 4, 1 ~ 4 범위는 이미 계산이 되어있지만
        // 2 ~ 4 범위는 구하지 않았다. 이는 2 * 3 ~ 4 로 쪼개서 계산해야한다!
        // 원하는 시작 범위(targetStart)가 현재 노드의 시작(start)과 끝(end)의 중간 위치 (start + end) / 2보다 크다면
        // 오른쪽 자식 노드를 타면 되고
        // 원하는 끝 범위가(targetEnd)가 현재 (start + end) / 2 보다 같거나 작다면 왼쪽 노드를 타면 된다.
        // 하지만 두 경우 모두 아니라면?
        // 왼쪽 노드에는 targetStart ~ (start + end) / 2까지의 값을 계산해서 얻어내고
        // 오른족 노드에서는 (start + end) / 2 + 1 ~ targetEnd 까지의 값을 계산해서 얻어내 곱해주면 된다!

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        int k = sc.nextInt();

        tree = new long[(int) Math.pow(2, Math.ceil(Math.log(n) / Math.log(2)) + 1)];

        for (int i = 0; i < n; i++)
            inputValue(i, sc.nextInt(), 1, 0, n - 1);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m + k; i++) {
            int input = sc.nextInt();
            if (input == 1)
                inputValue(sc.nextInt() - 1, sc.nextInt(), 1, 0, n - 1);
            else if (input == 2)
                sb.append(getValue(1, 0, n - 1, sc.nextInt() - 1, sc.nextInt() - 1)).append("\n");
        }
        System.out.println(sb);
    }

    static long inputValue(int target, int value, int node, int start, int end) {       // 값을 저장!
        if (start == end)       // 원하는 지점에 도착했다면
            return tree[node] = value;      // 값 삽입
        else if (target > (start + end) / 2) {      // 원하는 값이 현재 값의 범위의 중간보다 크다면
            long returned = inputValue(target, value, node * 2 + 1, (start + end) / 2 + 1, end);        // 오른쪽 자식 노드를 태워 보내고
            return tree[node] = (returned * tree[node * 2]) % mod;      // 그 후 얻은 값과 왼쪽 자식 노드를 곱하여 값을 저장해둔다.
        } else {        // 반대라면
            long returned = inputValue(target, value, node * 2, start, (start + end) / 2);      // 왼쪽 자식 노드를 태워 보낸다.
            return tree[node] = (returned * tree[node * 2 + 1]) % mod;      // 그 후 얻은 값을 오른쪽 자식 노드와 곱하여 값을 저장해둔다.
        }
    }

    static long getValue(int node, int start, int end, int targetStart, int targetEnd) {
        if (start == targetStart && end == targetEnd)       // 원하는 범위가 일치한다면
            return tree[node];      // 현재 노드의 값 반환

        if (targetStart > (start + end) / 2)        // 원하는 start 범위가 현재 값의 범위의 중간값보다 크다면
            return getValue(node * 2 + 1, (start + end) / 2 + 1, end, targetStart, targetEnd);  // 오른쪽 자식 노드를 태워 보내고
        else if (targetEnd <= (start + end) / 2)        // 원하는 end의 범위가 현재 값의 범위의 중간값보다 작다면
            return getValue(node * 2, start, (start + end) / 2, targetStart, targetEnd);        // 왼족 자식 노드를 태워 보낸다.
        else        // 두 경우 모두 아니라면 분할해서 값을 계산해야한다
            return getValue(node * 2, start, (start + end) / 2, targetStart, (start + end) / 2)     // 왼쪽 자식 노드에게는 targetStart ~ (start + end) / 2까지의 값의 계산을 맡기고
                    * getValue(node * 2 + 1, (start + end) / 2 + 1, end, (start + end) / 2 + 1, targetEnd) % mod;       // 오른쪽 자식 노드에게는 (start + end) / 2 + 1 ~ targetEnd 까지의 값의 계산을 맡긴다.
    }
}