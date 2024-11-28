/*
 Author : Ruel
 Problem : Baekjoon 17954번 투튜브
 Problem address : https://www.acmicpc.net/problem/17954
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17954_투튜브;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws IOException {
        // 2 * n개의 사과가 2개의 튜브에 n개씩 담겨있다.
        // 튜브의 양 끝에는 구멍이 뚫려있어, 이 구멍으로만 사과를 꺼낼 수 있다.
        // 4개의 구멍에서 꺼낼 수 있는 사과들 중 가장 작은 사과를 꺼낸다.
        // 사과에는 부패도가 있어서, 크기 * 시간 만큼의 부패도를 갖고 이는 누적된다.
        // 모든 사과를 꺼낼 때, 부패도가 최소가 되게끔 하기 위해 사과를 배치하고자 한다.
        // 이 때의 부패도와 사과 배치를 출력하라
        //
        // 그리디, 데크 문제
        // 어떠한 방법을 취하더라도 상위 3개의 사과는 마지막에 꺼낼 수 밖에 없다.
        // 따라서 이 사과들을 배치하여 나머지 사과들의 순서를 조절해야한다.
        // 가장 큰 두 사과를 양 옆에 막고, 그 안에 1 ~ n-2의 크기를 갖는 사과들을 배치하게 된다면
        // 가장 1 ~ n-2번째 사과들을 최대한 늦게 꺼낼 수 있다.
        // 그리고 2*n-2와 2*n-3 사과를 통해 양 옆을 막고, 그 안에 n-1 ~ 2*n-4번째 사과를 배치한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        // 데크를 통해 튜브를 구현
        Deque<Integer> up = new LinkedList<>();
        Deque<Integer> down = new LinkedList<>();
        // n이 1인 경우는 4개의 사과를 통해 입구를 막는 경우와 다르므로
        // 따로 처리
        if (n == 1) {
            up.offerFirst(1);
            down.offerFirst(2);

        } else {
            // 그 외의 경우
            // 가장 큰 두 사과를 위 튜브의 양 옆을 막고
            // 안에 1 ~ n-2번째 사과들을 배치한다.
            // 가장 작은 사과가 마지막에 꺼낼 수 있도록 배치한다.
            up.offerFirst(2 * n);
            for (int i = 1; i <= n - 2; i++)
                up.offerLast(i);
            // 세번째, 네번째로 큰 사과로 양 옆을 막고,
            // 그 안에 나머지 사과들을 배치한다.
            up.offerLast(2 * n - 1);
            down.offerFirst(2 * n - 2);
            for (int i = n - 1; i <= 2 * n - 3; i++)
                down.offerLast(i);
        }
        
        // 현재 사과 배치 기록
        StringBuilder sb = new StringBuilder();
        for (int apple : up)
            sb.append(apple).append(" ");
        sb.deleteCharAt(sb.length() - 1).append("\n");
        for (int apple : down)
            sb.append(apple).append(" ");
        sb.deleteCharAt(sb.length() - 1);
        
        // 부패도 계산
        // 사과 크기 총합
        long sizeSum = (2 * n + 1) * n;
        // 누적 부패도
        long answer = 0;
        // 시간
        int turn = 0;
        while (up.size() + down.size() > 0) {
            int min = 0;
            // 왼쪽 위 사과가 가장 작은 경우
            if (!up.isEmpty() && up.peekFirst() <= up.peekLast() &&
                    up.peekFirst() < (down.isEmpty() ? Integer.MAX_VALUE : Math.min(down.peekFirst(), down.peekLast())))
                min = up.pollFirst();
            // 오른쪽 위 사과가 가장 작은 경우
            else if (!up.isEmpty() && up.peekLast() <= up.peekFirst() &&
                    up.peekLast() < (down.isEmpty() ? Integer.MAX_VALUE : Math.min(down.peekFirst(), down.peekLast())))
                min = up.pollLast();
            // 왼쪽 아래 사과가 가장 작은 경우
            else if (!down.isEmpty() && down.peekFirst() <= down.peekLast() &&
                    down.peekFirst() < (up.isEmpty() ? Integer.MAX_VALUE : Math.min(up.peekFirst(), up.peekLast())))
                min = down.pollFirst();
            // 오른쪽 아래 사과가 가장 작은 경우
            else
                min = down.pollLast();
            
            // 턴 마다의 누적 부패도를 합산
            answer += sizeSum * (turn++);
            // 사과를 꺼냄
            sizeSum -= min;
        }
        // 답 출력
        System.out.println(answer);
        System.out.println(sb);
    }
}