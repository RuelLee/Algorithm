/*
 Author : Ruel
 Problem : Baekjoon 17178번 줄서기
 Problem address : https://www.acmicpc.net/problem/17178
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17178_줄서기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.StringTokenizer;

class Ticket {
    char alphabet;
    int number;

    public Ticket(char alphabet, int number) {
        this.alphabet = alphabet;
        this.number = number;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 각 사람은 티켓을 갖고 있으며 알파벳과 숫자로 이루어져있다.
        // 5명씩 서 있는 n개의 줄이 주어진다.
        // 각 줄의 인원은 순서대로 대기공간에 입장이 가능하고
        // 대기 공간에서 콘서트홀로 진입이 가능하다.
        // 대기 공간에서는 마지막으로 들어온 사람 순서대로 나갈 수 있다.
        // 콘서트홀로 입장하는 순서를 티켓순으로 입장하고자 한다.
        // 그러한 경우가 가능한지 계산하라
        //
        // 스택 문제
        // 먼저 티켓에 따른 입장 순서를 모두 계산해두자.
        // 그 후, 줄의 순서대로 살펴보며, 현재 순서가 아닌 사람들은 스택에 담는다.
        // 스택 최상단에 순서에 맞는 사람이 들어올 경우, 순차적으로 스택에서 꺼낸다.
        // 모든 사람들을 해당 순서로 정렬하여 입장할 수 있는지를 살펴본다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        // 티켓 순서를 최소힙 우선순위큐를 통해 계산하자.
        PriorityQueue<Ticket> priorityQueue = new PriorityQueue<>((o1, o2) -> {
            if (o1.alphabet == o2.alphabet)
                return Integer.compare(o1.number, o2.number);
            return Character.compare(o1.alphabet, o2.alphabet);
        });
        
        // 티켓
        Ticket[][] tickets = new Ticket[n][5];
        for (int i = 0; i < tickets.length; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < tickets[i].length; j++) {
                String s = st.nextToken();
                // 해당 위치에 티켓을 저장하고, 우선순위큐에 추가
                tickets[i][j] = new Ticket(s.charAt(0), Integer.parseInt(s.substring(2)));
                priorityQueue.offer(tickets[i][j]);
            }
        }

        /// 해쉬맵을 통해 티켓에 따른 순서를 저장해두자.
        HashMap<Ticket, Integer> orders = new HashMap<>();
        while (!priorityQueue.isEmpty())
            orders.put(priorityQueue.poll(), n * 5 - priorityQueue.size());
        
        // 대기 공간에 해당하는 스택
        Stack<Integer> stack = new Stack<>();
        // 현재 입장해야하는 순서
        int currentOrder = 1;
        // 모든 사람들을 살펴본다.
        for (int i = 0; i < n * 5; i++) {
            int row = i / 5;
            int col = i % 5;
            
            // 현재 살펴보는 사람의 입장 순서
            int targetOrder = orders.get(tickets[row][col]);
            // 만약 i번째 사람의 입장 순서가 currentOrder보다 적다면
            // i번째 사람이 입장할 수가 없다.
            // 따라서 반복문 종료
            if (targetOrder < currentOrder)
                break;
            
            // 그 외 같거나 큰 경우에는 스택에 추가
            stack.push(targetOrder);
            // 스택이 비어있지 않고, 최상단에 현재 순서와 일치하는 사람이 존재한다면
            while (!stack.isEmpty() && currentOrder == stack.peek()) {
                // 스택에서 꺼내며 다음 입장 순서로 넘긴다.
                currentOrder++;
                stack.pop();
            }
        }

        // currentOrder가 n * 5 + 1을 가르키고 있다면 모든 사람을 입장시킨 경우.
        // GOOOD 출력
        // 그렇지 않다면 BAD 출력
        System.out.println(currentOrder == n * 5 + 1 ? "GOOD" : "BAD");
    }
}