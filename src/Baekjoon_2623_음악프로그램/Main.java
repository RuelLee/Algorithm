/*
 Author : Ruel
 Problem : Baekjoon 2623번 음악프로그램
 Problem address : https://www.acmicpc.net/problem/2623
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2623_음악프로그램;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        // n명의 가수와 m명의 pd가 주어진다
        // 각각의 pd들은 원하는 가수들의 출연 순서를 정해온다(반드시 모든 가수들의 순서를 정하는 것은 아니다)
        // 모든 pd들의 의견을 반영한 출연 순서를 정할 수 있다면 순서를 출력하고, 아니라면 0을 출력한다
        // 여러 가지가 존재할 경우 아무거나 출력.
        //
        // 위상 정렬 문제
        // pd들이 주어지는 순서에 따라 뒤의 가수는 앞에 나온 가수가 나와야 출연이 가능하다는 전제를 준다
        // 그리고 위상 정렬을 한 뒤, 모든 가수가 출연하는 것이 불가능하다면, 0을 출력, 가능하다면 순서를 출력하자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[] inDegree = new int[n + 1];        // 진입 차수
        List<List<Integer>> postSingers = new ArrayList<>();        // 후행 가수
        for (int i = 0; i < n + 1; i++)
            postSingers.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int nums = Integer.parseInt(st.nextToken());
            int preSinger = Integer.parseInt(st.nextToken());       // 선행 가수. 초기값은 첫번째 가수
            for (int j = 0; j < nums - 1; j++) {
                int postSinger = Integer.parseInt(st.nextToken());      // 후행 가수
                inDegree[postSinger]++;     // 진입 차수 증가
                postSingers.get(preSinger).add(postSinger);     // 후행 가수 등록.
                preSinger = postSinger;     // 다음 가수를 위해 선행 가수에 postSinger 할당.
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i < inDegree.length; i++) {
            if (inDegree[i] == 0)       // 진입 차수가 0인 가수들을 모음.
                queue.offer(i);
        }

        int count = 0;      // 출연한 가수 수 카운트
        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            int current = queue.poll();
            sb.append(current).append("\n");
            count++;

            for (int next : postSingers.get(current)) {
                if (--inDegree[next] == 0)      // 후행 가수들의 진입 차수를 낮추면서 0이 됐는지 확인.
                    queue.offer(next);      // 0이 됐다면 큐에 삽입.
            }
        }
        // n명 모두 출연했다면 출연 순서 출력. 아니라면 0 출력.
        System.out.println(count == n ? sb : 0);
    }
}