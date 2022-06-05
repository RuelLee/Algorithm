/*
 Author : Ruel
 Problem : Baekjoon 11000번 강의실 배정
 Problem address : https://www.acmicpc.net/problem/11000
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11000_강의실배정;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Class {
    int s;
    int t;

    public Class(int s, int t) {
        this.s = s;
        this.t = t;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // s에 시작해서 t에 끝나는 n개의 수업이 주어진다
        // 수업이 끝난 직후에 다른 수업을 시작할 수 있다.
        // 모든 수업을 최소한의 강의실에 배정하려 한다. 이 때 필요한 강의실의 수는?
        //
        // 정렬과 우선순위큐를 사용하는 문제
        // 먼저 수업들을 시작 시간에 따라 정렬한다.
        // 그리고 현재 수업이 진행중인 수업들을 우선순위큐에 담는다.
        // 이 때 우선순위큐는 종료 시간에 따라 최소힙 정렬한다
        // 순차적으로 수업을 살펴보며, 해당 수업이 시작하기 전에 끝난 수업들은 우선순위큐에서 모두 제거해주고
        // 해당 수업을 우선순위큐에 담는다.
        // 그리고 필요한 강의실의 수는 우선순위큐의 크기가 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 각 수업들.
        Class[] classes = new Class[n];
        for (int i = 0; i < classes.length; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            classes[i] = new Class(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        // 수업 시작 시간에 따라 오름차순 정렬한다.
        Arrays.sort(classes, Comparator.comparingInt(o -> o.s));

        // 진행중인 수업을 담을 우선순위큐.
        // 종료 시간에 따라 최소힙을 사용한다.
        PriorityQueue<Class> inClass = new PriorityQueue<>(Comparator.comparingInt(o -> o.t));

        // 동시에 진행되는 수업들의 최대 값을 계산한다.
        int maxClassSameTime = 0;
        for (int i = 0; i < classes.length; i++) {
            // 우선순위큐가 비어있지 않으며, 현재 수업의 시작시간보다 이른 시간에
            // 끝나는 수업들은 우선순위큐에서 제거해준다.
            while (!inClass.isEmpty() &&
                    inClass.peek().t <= classes[i].s)
                inClass.poll();
            // 그 후, 해당 수업을 우선순위큐에 추가하고
            inClass.offer(classes[i]);
            // 진행중인 수업들의 최대값이 갱신되었는지 확인한다.
            maxClassSameTime = Math.max(maxClassSameTime, inClass.size());
        }
        // 동시에 진행된 수업들의 최댓값을 출력한다.
        System.out.println(maxClassSameTime);
    }
}