/*
 Author : Ruel
 Problem : Baekjoon 20495번 수열과 헌팅
 Problem address : https://www.acmicpc.net/problem/20495
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20495_수열과헌팅;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수에 대해 ai, bi가 주어진다.
        // 해당 값은 ai +- bi를 뜻한다.
        // 위 값들을 정렬했을 때, 가능한 순서의 범위를 출력하라
        //
        // 정렬 문제
        // 먼저 각 수에 대해 가장 먼저 올 수 있는 경우 ai - bi 순서대로 살펴보며
        // 우선순위큐엔 ai + bi 순서대로 담아 오름차순으로 퇴장시켜나간다.
        // ai - bi 보다 이른 ai + bi 가 있다면 두 수 간의 순서는 전자가 무조건 후자보다 늦을 수 밖에 없다.
        // 따라서 현재 앞 서 등장한 ai + bi의 개수를 관리하며
        // 이번 ai - bi 의 최초 등장 순서는 앞서 등장한 ai + bi의 개수 1이 되게 되고
        // 최후 등장 순서는 ai + bi의 개수 + 현재 담겨있는 우선순위큐의 개수 + 1이 된다.
        // 현재 큐에 같이 담겨있는 수들의 범위가 최상단의 있는 수와 겹치게 되므로, 최상단의 있는 수보다 먼저 나갈 가능성도 있기 때문.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        // 주어지는 n개의 수
        int[][] array = new int[n][2];
        // ai - bi로 먼저 등장하는 순서대로 꺼낼 수 있게끔 우선순위큐에 담는다.
        PriorityQueue<Integer> first = new PriorityQueue<>(Comparator.comparingInt(o -> array[o][0] - array[o][1]));
        StringTokenizer st;
        for (int i = 0; i < array.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < array[i].length; j++)
                array[i][j] = Integer.parseInt(st.nextToken());
            first.offer(i);
        }
        
        // 각각의 수의 최초, 최후 등장 순서
        int[][] answer = new int[n][2];
        // ai + bi로 관리하여, 더 이상 등장할 수 없는 수를 관리한다.
        PriorityQueue<Integer> last = new PriorityQueue<>(Comparator.comparingInt(o -> array[o][0] + array[o][1]));
        // 순서.
        // 범위가 지나가 제거된 ai + bi의 개수. 편의상 1부터 시작
        int count = 1;
        while (!first.isEmpty()) {
            // 현재 수
            int current = first.poll();
            // 현재 등장한 수의 ai - bi보다 더 적은 ai + bi 값을 갖고 있어
            // current보다 무조건 먼저 등장할 수 밖에 없는 수들을 제거해나간다.
            // 제거하며, 현재 last 우선순위큐에 담긴 수만큼이 최후 등장 순서가 된다.
            while (!last.isEmpty() && array[last.peek()][0] + array[last.peek()][1] < array[current][0] - array[current][1])
                answer[last.poll()][1] = count++ + last.size();
            
            // current보다 먼저 등장하는 수들을 모두 제거했다.
            // 현재 count가 current가 등장할 수 있는 최초 순서
            answer[current][0] = count;
            // 그리고 우선순위큐에 추가
            last.offer(current);
        }
        // 아직 제거되지 않은 큐 안의 값을 모두 꺼내며
        // 최후 순서들의 값을 채워나간다.
        while (!last.isEmpty())
            answer[last.poll()][1] = count++ + last.size();
        
        // 답안 작성
        StringBuilder sb = new StringBuilder();
        for (int[] a : answer)
            sb.append(a[0]).append(" ").append(a[1]).append("\n");
        // 출력
        System.out.print(sb);
    }
}