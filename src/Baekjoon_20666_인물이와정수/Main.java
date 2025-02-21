/*
 Author : Ruel
 Problem : Baekjoon 20666번 인물이와 정수
 Problem address : https://www.acmicpc.net/problem/20666
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20666_인물이와정수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Tip {
    int a;
    int b;
    int t;

    public Tip(int a, int b, int t) {
        this.a = a;
        this.b = b;
        this.t = t;
    }
}

class Monster {
    int num;
    long difficulty;

    public Monster(int num, long difficulty) {
        this.num = num;
        this.difficulty = difficulty;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 게임에 n 종류의 몬스터가 있고, 이 중 m 종류를 잡게되면 게임을 클리어하게 된다.
        // a번 몬스터를 잡으면 a번 아이템을 얻을 수 있다.
        // 게임을 많이 한 고인물은 모든 아이템을 갖고 있고, 이 때의 몬스터 난이도가 주어진다.
        // p개의 팁이 주어진다.
        // 팁은 a b t 형태를 갖고 있고, a번 아이템이 없이 b번 몬스터를 잡으면 난이도 t만큼 상승함을 알려준다.
        // 게임을 클리어할 때까지 잡게되는 몬스터들의 난이도 중 최댓값을 클리어 난이도라 할 때
        // 클리어 난이도를 최소화하여 게임을 클리어하고자 할 때, 그 난이도는?
        //
        // 그리디, 우선순위큐 문제
        // 먼저 팁에 따라, 처음에는 아무런 아이템도 없으므로 각 몬스터의 난이도에 팁에 해당하는 만큼의 난이도를
        // 더해둔다.
        // 그 후, 현재 잡을 수 있는 몬스터들 중 가장 난이도가 낮은 몬스터를 잡아가며
        // 해당 아이템을 획득 시, 연관된 몬스터들의 난이도를 낮춰가는 방식으로 차근차근 m마리를 잡아나가면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 총 n종류의 몬스터들 중 m 종류를 잡으면 클리어
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 각 몬스터들의 난이도
        long[] difficulties = new long[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < difficulties.length; i++)
            difficulties[i] = Integer.parseInt(st.nextToken());
        
        // 팁 정보
        List<List<Tip>> tips = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            tips.add(new ArrayList<>());
        int p = Integer.parseInt(br.readLine());

        for (int i = 0; i < p; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());

            // a번 아이템 없이 b번 몬스터를 잡으면 t만큼 난이도가 상승한다.
            tips.get(a).add(new Tip(a, b, t));
            difficulties[b] += t;
        }

        PriorityQueue<Monster> priorityQueue = new PriorityQueue<>(Comparator.comparingLong(o -> o.difficulty));
        for (int i = 1; i < difficulties.length; i++)
            priorityQueue.offer(new Monster(i, difficulties[i]));

        long answer = 0;
        boolean[] captured = new boolean[n + 1];
        // 총 m마리를 잡는다.
        for (int i = 0; i < m; i++) {
            // 이미 잡은 몬스터라면 우선순위큐에서 제거
            while (!priorityQueue.isEmpty() && captured[priorityQueue.peek().num])
                priorityQueue.poll();
            
            // 지금 잡을 몬스터
            Monster current = priorityQueue.poll();
            // 난이도 최댓값 갱신
            answer = Math.max(answer, current.difficulty);
            // 잡았는지를 boolean 배열로 체크
            captured[current.num] = true;
            // current.num에 해당하는 아이템을 획득했으므로
            // 연관된 몬스터들의 난이도 하락.
            // 그 후, 해당 몬스터들을 바뀐 난이도로 우선순위큐에 추가
            for (Tip t : tips.get(current.num)) {
                difficulties[t.b] -= t.t;
                priorityQueue.offer(new Monster(t.b, difficulties[t.b]));
            }
        }
        // 답 출력
        System.out.println(answer);
    }
}