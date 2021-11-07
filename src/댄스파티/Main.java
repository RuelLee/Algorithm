/*
 Author : Ruel
 Problem : Baekjoon 2731번 댄스 파티
 Problem address : https://www.acmicpc.net/problem/2831
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 댄스파티;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 그리디문제
        // 남녀는 각각 자신의 키와 자신이 선호하는 키가 자신보다 큰 쪽인지, 작은 쪽인지에 대한 정보가 주어진다
        // 가능한 많은 커플을 만들려고 할 때, 그 수는?
        // 이런 문제는 어떻게 풀어야할지 쉽게 생각나지는 않는 것 같다
        // 남자 중 큰 쪽을 선호하는 경우, 작은 쪽을 선호하는 경우
        // 여자 중 큰 쪽을 선호하는 경우, 작은 쪽을 선호하는 경우 총 4가지로 나눠서 작은 순으로 정렬해두자
        // 남자 중 큰 쪽을 선호하는 경우에서부터 하나씩 차근차근 생각해보자
        // 남자 중 큰 쪽을 선호하는데 그 중 가장 작은 남자라면 작은 남자를 선호하는 여성들 중 커플 성사 가지수가 제일 많다.
        // 따라서 작은 남성을 선호하는 여성 중 이 남자보다 작다면 가지수가 없는 경우이다.
        // 그래서 이 남성과 작은 남성을 선호하는 여성 중 이 남성보단 크지만 가장 작은 여성을 택해 많은 가능성을 열어둔다.
        // 위와 같은 행동을 반복하며 커플을 성사시킨다
        // 자신보다 큰 남성을 선호하는 여성과 자신보다 작은 여성을 선호하는 남성의 경우도 마찬가지로 계산하여 더해, 총 커플의 수를 구한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        StringTokenizer st;
        PriorityQueue<Integer> menPlus = new PriorityQueue<>();     // 자신보다 키 큰 여성을 선호하는 남성들
        PriorityQueue<Integer> menMinus = new PriorityQueue<>();   // 자신보다 작은 키 여성을 선호하는 남성들
        st = new StringTokenizer(br.readLine());
        input(menPlus, menMinus, st);
        PriorityQueue<Integer> womenPlus = new PriorityQueue<>();       // 자신보다 키 큰 남성을 선호하는 여성들
        PriorityQueue<Integer> womenMinus = new PriorityQueue<>();      // 자신보다 작은 키 남성을 선호하는 여성들
        st = new StringTokenizer(br.readLine());
        input(womenPlus, womenMinus, st);

        int totalSum = 0;
        totalSum += makeCouple(menPlus, womenMinus);            // 자신보다 큰 키 여성을 선호하는 남성들과 자신보다 작은 키 남성을 선호하는 여성들을 계산
        totalSum += makeCouple(womenPlus, menMinus);            // 자신보다 큰 키 남성을 선호하는 여성들과 자신보다 작은 키 여성을 선호하는 남성들을 계산

        System.out.println(totalSum);
    }

    static int makeCouple(PriorityQueue<Integer> plus, PriorityQueue<Integer> minus) {
        int sum = 0;
        while (!plus.isEmpty()) {       // 큰 키를 선호하는 사람이 빌 때까지
            int a = plus.poll();
            while (!minus.isEmpty() && minus.peek() <= a)       // 작은 키 선호하는 사람이 현재 큰 키를 선호하는 사람보다 작다면 가지수가 없는 경우이다. 우선순위큐에서 제거해주자.
                minus.poll();
            if (!minus.isEmpty()) {     // 큐가 비지 않았다면 커플이 성사된 경우이다.
                minus.poll();       // 성사된 작은 키 선호하는 사람을 빼주고,
                sum++;      // 성사된 커플 수를 하나 증가시켜주자
            }
        }
        return sum;     // 총 커플의 수를 반환한다.
    }

    static void input(PriorityQueue<Integer> plus, PriorityQueue<Integer> minus, StringTokenizer st) {
        while (st.hasMoreTokens()) {
            int person = Integer.parseInt(st.nextToken());
            if (person > 0)     // 큰 키를 선호한다면
                plus.offer(Math.abs(person));           // 절대값으로 키만 넘겨준다.
            else            // 작은 키를 선호한다면
                minus.offer(Math.abs(person));
        }
    }
}