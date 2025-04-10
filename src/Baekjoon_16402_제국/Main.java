/*
 Author : Ruel
 Problem : Baekjoon 16402번 제국
 Problem address : https://www.acmicpc.net/problem/16402
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16402_제국;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int[] parents;

    public static void main(String[] args) throws IOException {
        // n개의 왕국과 m번의 전쟁이 있었다.
        // 전쟁에서 진 국가는 전쟁에서 이긴 국가의 속국이 된다.
        // 국가 간의 전쟁에서는 종주국과 속국을 포함한 연합 전체가 싸우게 되며
        // 진 연합은 이긴 연합의 속국으로 들어가게 된다.
        // 또한 예외로 속국이 종주국에 전쟁을 벌여, 승리할 경우, 해당 속국이 종주국이 되게 된다.
        // 모든 전쟁이 끝난 후, 속국이 아닌 국가의 이름을 출력하라
        //
        // 분리 집합 문제
        // 보통 분리 집합 문제의 경우, ranks를 사용하여 연산을 줄여주었지만
        // 이 경우, 집합의 대표를 임의 설정하는 것이 아닌, 종주국으로 설정해야하므로 해당 방법을 사용하진 않는다.
        // 그 외에는 문자열 파싱, 해쉬 맵으로 왕국의 이름과 자연수를 매칭 등을 해주면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 국가, m개의 전쟁
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 각 국가 연합의 종주국
        parents = new int[n];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        
        // 국가를 자연수와 매칭
        HashMap<String, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < n; i++)
            hashMap.put(nameOnly(br.readLine()), hashMap.size());
        
        // 전쟁
        for (int i = 0; i < m; i++) {
            String[] input = br.readLine().split(",");
            // 앞 국가가 승리할 경우
            if (input[2].equals("1"))
                aWin(hashMap.get(nameOnly(input[0])), hashMap.get(nameOnly(input[1])));
            else        // 뒤의 국가가 승리할 경우
                aWin(hashMap.get(nameOnly(input[1])), hashMap.get(nameOnly(input[0])));
        }

        // 종주국인 나라를 찾아 우선 순위 큐에 담는다.
        PriorityQueue<String> priorityQueue = new PriorityQueue<>();
        for (String key : hashMap.keySet()) {
            if (findParent(hashMap.get(key)) == hashMap.get(key))
                priorityQueue.offer(key);
        }

        StringBuilder sb = new StringBuilder();
        // 종주국의 수
        sb.append(priorityQueue.size()).append("\n");
        // 우선 순위 큐에서 꺼내며 ASCII 사전순으로 정렬된 국가 이름을 기록
        while (!priorityQueue.isEmpty())
            sb.append("Kingdom of ").append(priorityQueue.poll()).append("\n");
        // 답 출력
        System.out.print(sb);
    }

    // 문자열에서 국가의 이름만 꺼낸다.
    static String nameOnly(String s) {
        String[] split = s.split(" ");
        return split[split.length - 1];
    }

    // a와 b 연합 간의 전쟁에서 a 국가가 이겼을 때
    // 발생하는 속국과 종주국의 관계를 만든다.
    static void aWin(int a, int b) {
        int pa = findParent(a);
        int pb = findParent(b);

        // 두 국가가 서로 다른 연합일 경우
        // b의 종주국이 a의 속국이 된다.
        if (pa != pb)
            parents[pb] = pa;
        else {
            // 같은 연합일 경우
            // 종주국이 a로 바뀐다.
            parents[pb] = a;
            parents[a] = a;
        }
    }

    // 각 연합의 종주국을 찾는다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }
}