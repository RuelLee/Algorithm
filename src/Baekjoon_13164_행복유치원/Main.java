/*
 Author : Ruel
 Problem : Baekjoon 13164번 행복 유치원
 Problem address : https://www.acmicpc.net/problem/13164
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13164_행복유치원;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 원생들을 일렬로 줄을 세우고, k개의 그룹으로 나누어, 조마다 단체 티셔츠를 맞추려고 한다.
        // 티셔츠의 가격은 각 조의 가장 키가 큰 원생과 가장 키가 작은 원생의 차이만큼 든다.
        // 티셔츠 비용을 최소화하려고 할 때 그 비용은?
        //
        // 그리디 문제
        // 처음 문제를 봤을 때는 이분 탐색을 사용해야하는건가 라고 생각했다
        // 하지만 생각을 좀 더 하면
        // n명의 원생에 대해 그룹을 나누지 않았다면 비용은 n번 원생의 키 - 1번 원생의 키가 될 것이고
        // 1 ~ i번째, (i + 1) ~ n번째 그룹으로 나눈다면 줄어드는 비용은
        // i ~ (i + 1) 번째 원생의 키 만큼이 줄어든다.
        // 따라서 이웃한 원생들 간의 키 차이를 최대힙으로 저장하고,
        // 나누려는 조의 수 - 1번 만큼 n번째 원생 - 1번째 원생 키 값에서 빼주면 된다.
        // 구현이나 난이도는 낮지만 아이디어가 생각보다 재밌었다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 전체 원생의 수
        int n = Integer.parseInt(st.nextToken());
        // 나누려는 조의 수
        int k = Integer.parseInt(st.nextToken());

        // 원생들.
        int[] kids = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 최대힙 우선순위큐
        PriorityQueue<Integer> diffMaxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        // 이웃한 원생들 간의 키 차이를 최대힙 우선순위큐에 저장한다.
        for (int i = 0; i < kids.length - 1; i++)
            diffMaxHeap.offer(kids[i + 1] - kids[i]);

        // 조를 나누지 않았을 때의 티셔츠 비용.
        int answer = kids[kids.length - 1] - kids[0];
        // (k - 1)개의 이웃한 키 차이 비용을 빼준다.
        for (int i = 0; i < k - 1; i++)
            answer -= diffMaxHeap.poll();

        System.out.println(answer);
    }
}