/*
 Author : Ruel
 Problem : Baekjoon 2465번 줄 세우기
 Problem address : https://www.acmicpc.net/problem/2465
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2465_줄세우기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int[] segmentTree;
    static int n;

    public static void main(String[] args) throws IOException {
        // 줄을 서 있는 사람들의 키와
        // 자신보다 앞에 있는 사람들 중 자신보다 키가 같거나 작은 사람의 수가 수열S로 순서대로 주어진다.
        // 이 때 줄을 서 있는 사람들의 키를 줄을 선 순서대로 출력하라
        //
        // 세그먼트 트리 문제
        // S를 통해 줄을 서있는 순서를 구해야한다.
        // 자신보다 앞 사람의 개수만 영향을 받기 때문에 앞에서부터 순차적으로 구하려면
        // 가짓수가 너무 많다.(자신보다 뒤에 나올 사람들도 고려해야하기 때문)
        // 따라서 맨 뒤에서부터 차례대로 계산해나가면 한번에 계산할 수 있다.
        // 가령 S가 0 1 0 0 3 2 6 7 4 6 9 4으로 주어진다면
        // 마지막 4는 모든 사람들 중 자신이 5번째라는 의미이고
        // 그 다음 9는 위의 5번째를 제외한 순서에서 자신이 9번째라는 의미가 된다.
        // 따라서 세그먼트 트리를 활용하여 아직 순서가 정해지지 않은 사람들 중 n번째 사람을 찾아내면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // n명
        n = Integer.parseInt(br.readLine());
        // 줄을 서있는 사람들의 키
        int[] row = new int[n];
        for (int i = 0; i < row.length; i++)
            row[i] = Integer.parseInt(br.readLine());
        // 오름차순으로 정렬한다.
        Arrays.sort(row);
        
        // 세그먼트 트리
        segmentTree = new int[(int) Math.pow(2, Math.ceil(Math.log(n) / Math.log(2)) + 1)];
        // 1 ~ n까지의 세그먼트 트리를 만든다.
        // 부모 노드는 자식 노드들의 합으로 각 범위 내에 계산되지 않은 사람의 수를 표현한다.
        init(1, 1, n);
        // 자신보다 앞에 줄을 선 사람들 중 자신보다 키가 같거나 작은 사람의 수
        int[] array = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 답안
        
        int[] answer = new int[n];
        // S의 역순으로 찾아나간다
        for (int i = array.length - 1; i >= 0; i--)
            answer[i] = row[findAndDelete(array[i] + 1) - 1];
        
        // 답안 작성 및 출력
        StringBuilder sb = new StringBuilder();
        for (int height : answer)
            sb.append(height).append("\n");
        System.out.print(sb);
    }

    // 현재 계산되지 않은 사람들 중 order번째 사람을 찾는다.
    static int findAndDelete(int order) {
        int loc = 1;
        int start = 1;
        int end = n;

        // 범위를 좁혀나가 한 사람이 특정될 때까지 한다.
        while (start < end) {
            // 왼쪽 자식 노드의 수가 order보다 작다면
            // 오른쪽 자식 노드를 타고 내려간다.
            if (segmentTree[loc * 2] < order) {
                // 오른쪽 자식 노드에서의 순서는 왼쪽 자식 노드의 수만큼을 제한다.
                order -= segmentTree[loc * 2];
                loc = loc * 2 + 1;
                start = (start + end) / 2 + 1;
            } else {        // 그렇지 않다면 왼쪽 자식 노드를 타고 내려간다.
                loc = loc * 2;
                end = (start + end) / 2;
            }
        }

        // 사람이 특정되었음으로 해당 사람을 이제 계산되었다고 표시하고
        // 순서에 포함되지 않게하기 위해 0을 채운다.
        segmentTree[loc] = 0;
        // 거꾸로 부모노드로 거슬러 올라가면서 세그먼트 트리 값들을 갱신한다.
        loc /= 2;
        while (loc > 0) {
            segmentTree[loc] = segmentTree[loc * 2] + segmentTree[loc * 2 + 1];
            loc /= 2;
        }
        // 특정한 사람의 순서를 반환한다.
        return start;
    }

    // 세그먼트 트리 초기화.
    // start ~ end에 해당하는 단말 노드에 1을 채우고
    // 부모 노드들엔 자식 노드들의 합을 값으로 준다.
    static int init(int loc, int start, int end) {
        if (start == end)
            return segmentTree[loc] = 1;
        return segmentTree[loc] = init(loc * 2, start, (start + end) / 2) +
                init(loc * 2 + 1, (start + end) / 2 + 1, end);
    }
}