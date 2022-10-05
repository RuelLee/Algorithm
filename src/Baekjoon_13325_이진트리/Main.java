/*
 Author : Ruel
 Problem : Baekjoon 13325번 이진 트리
 Problem address : https://www.acmicpc.net/problem/13325
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13325_이진트리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] nodes;

    public static void main(String[] args) throws IOException {
        // k 높이의  포화 이진 트리가 주어진다.
        // 모든 에지들의 가중치가 주어질 때,
        // 루트 노드로부터 단말 노드까지의 거리가 모두 같으면서 가중치가 최소가 되도록 하고 싶다.
        // 이 때 가중치들의 합은?
        //
        // 트리에서의 다이나믹프로그래밍 문제
        // 2 2 2 1 1 3 이라는 값이 주어진다면
        //          0
        //      2       2
        //    2   1   1   3
        // 과 같은 트리형태를 띈다.
        // 이 때 가중치를 같게 만들어주기 위해서는
        //          0
        //      3       2
        //    2   2   3   3 가 된다.
        // 결국 각 서브트리에서, 왼쪽 서브트리와 오른쪽 서브트리 각각의 가장 먼 거리를 계산하고
        // 이 두 값을 같게 만들어주기 위해 더 작은 값을 같는 서브트리 엣지에 가중치를 추가해주면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 높이 k의 트리
        int k = Integer.parseInt(br.readLine());
        // 엣지라고 간선에 가중치가 주어졌지만, 사실상 노드에 값을 주고 계산이 가능.
        nodes = new int[(int) Math.pow(2, k + 1)];

        // 값 초기화.
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 2; i < nodes.length; i++)
            nodes[i] = Integer.parseInt(st.nextToken());

        // 1에서부터 각 서브트리의 최대 거리를 계산해나간다.
        maxDistance(1);
        // 모든 가중치가 nodes에 계산되고, 이 값을 합하면 된다.
        System.out.println(Arrays.stream(nodes).sum());
    }

    // 왼쪽 오른쪽 서브 트리의 최대 거리를 node에 가중치를 추가하여 같게하며
    // 최대 길이를 반환하는 메소드
    static long maxDistance(int loc) {
        // 만약 자식 노드가 없다면 0을 리턴한다.
        if (loc * 2 >= nodes.length)
            return 0;

        // 왼쪽 서브 트리에서의 최대 길이 + 간선의 가중치
        long leftMax = maxDistance(loc * 2) + nodes[loc * 2];
        // 오른쪽 서브 트리에서의 최대 길이 + 간선의 가중치
        long rightMax = maxDistance(loc * 2 + 1) + nodes[loc * 2 + 1];

        // 만약 왼쪽 서브 트리의 최대 길이가 더 길다면
        // 오른쪽 엣지에 차이만큼의 가중치를 더해준다.
        if (leftMax >= rightMax)
            nodes[loc * 2 + 1] += leftMax - rightMax;
        // 오른쪽 서브 트리의 최대 길이가 더 길다면
        // 왼쪽 엣지에 차이만큼의 가중치를 더해준다.
        else
            nodes[loc * 2] += rightMax - leftMax;
        // 왼쪽 서브 트리와 오른쪽 서브 트리의 최대 길이 중 더 긴 것을 반환한다.
        return Math.max(leftMax, rightMax);
    }
}