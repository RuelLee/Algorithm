/*
 Author : Ruel
 Problem : Baekjoon 18869번 멀티버스 Ⅱ
 Problem address : https://www.acmicpc.net/problem/18869
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18869_멀티버스Ⅱ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // m개의 우주가 있고, n개의 행성이 주어진다.
        // 행성의 크기를 알고 있을 때, 균등한 우주 쌍이 몇 개인지 세려한다.
        // 균등한 우주는
        // Ai < Aj → Bi < Bj
        // Ai = Aj → Bi = Bj
        // Ai > Aj → Bi > Bj
        // 다음 조건을 만족한다고 한다.
        //
        // 좌표 압축 문제
        // m이 최대 100
        // n이 최대 10,000
        // 행성의 크기가 최대 100만으로 주어진다.
        // 우리가 필요한 것은 정확한 크기가 아니라 상대적 크기들에 대한 순서 비교이다.
        // 따라서 좌표압축을 통해 최대 크기가 1만인 값들로 바꾸고 이들이 순서가 어떻게 되는지 비교하면 된다.
        // 물론 좌표 압축을 하고 나서도 우주 간의 비교를 한번에 끝내기 위해 String 값으로 바꿔준다
        // 이 때 그대로 int 값을 쭉 이어붙여서는 중복되는 1 + 12 와 11 + 2 와 같은 경우가 생길 수 있기 때문에 주의하자
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 주어지는 우주와 행성의 개수
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        
        // 각 우주를 표현한 하나의 문자열
        String[] universes = new String[m];
        for (int i = 0; i < m; i++) {
            // 별들을 입력 받는다.
            int[] stars = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            // 우선순위큐를 통해 좌표 압축
            PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
            for (int star : stars)
                priorityQueue.offer(star);
            // 해쉬 맵에 해당하는 값의 순서를 지정한다.
            HashMap<Integer, Integer> hashMap = new HashMap<>();
            while (!priorityQueue.isEmpty()) {
                if (!hashMap.containsKey(priorityQueue.peek()))
                    hashMap.put(priorityQueue.peek(), hashMap.size() + 1);
                priorityQueue.poll();
            }

            // 다시 순서대로 별들을 살펴보며 압축된 값을 통해 하나의 문자열로 만든다.
            StringBuilder sb = new StringBuilder();
            for (int star : stars) {
                int order = hashMap.get(star);
                sb.append(order).append('_');
            }
            universes[i] = sb.toString();
        }

        // 우주들을 서로 비교하며 같은 우주 쌍이 몇 개 있는지 센다.
        int answer = 0;
        for (int i = 0; i < universes.length; i++) {
            for (int j = i + 1; j < universes.length; j++) {
                if (universes[i].equals(universes[j]))
                    answer++;
            }
        }
        // 답안 출력
        System.out.println(answer);
    }
}