/*
 Author : Ruel
 Problem : Baekjoon 25429번 노엣지 피자
 Problem address : https://www.acmicpc.net/problem/25429
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25429_노엣지피자;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Pizza {
    int idx;
    int topping;

    public Pizza(int idx, int topping) {
        this.idx = idx;
        this.topping = topping;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n조각으로 이루어진 피자를 파는데, 한 번에 l조각을 먹어야한다.
        // 매 번 피자를 먹을 때마다 토핑의 합은 같아야한다.
        // 각 피자 조각에는 토핑을 하나만 올릴 수 있다.
        // q개의 쿼리를 통해 각 지정된 번호의 피자 조각에 토핑을 올리거나, 변경하거나, 제거할 수 있다.
        // 토핑이 올려지지 않은 조각은 요리사가 마음대로 토핑을 올릴 수 있다.
        // 한 번에 먹는 피자의 토핑 합을 최소로 하고자할 때, 그 값은?
        //
        // 해쉬 맵, 우선순위큐, 누적합 문제
        // 먼저 n, l이 최대 10억으로 매우 큰데, q는 10만으로, 값 압축이 필요하다. 이에 해쉬맵을 사용한다.
        // 매번 l조각을 한번에 먹는데, 토핑의 합이 같아야하므로
        // 한 번 먹을 때, i % l, i+1 % l, ... + (i + l -1) % l 번의 피자들을 먹게 된다.
        // 즉 어디부터 시작하더라도 l조각의 토핑 합이 같기 위해서는 각 번호 mod l의 값이 유일해야한다.
        // 유일하지 않다면 어디서부터 조각을 잘라내냐에 따라 한번 먹을 때의 토핑 합이 달라질 수 있다.
        // 따라서 각 피자 조각의 토핑을 나머지 처리하여, 세되, 해당 나머지에 속하는 피자 조각이 몇 개인지를 센다.
        // 하나라도 2개 이상이 된다면 알맞은 피자가 되지 못한다.
        // 2개 이상인지 여부만 중요하므로, 이를 우선순위큐에 담아 내림차순으로 살펴본다. 가장 앞의 값이 1보다 크다면 무조건 불가능한 경우이다.
        // 또 그 때의 한 번에 먹는 피자의 토핑 합을 출력해야하므로
        // 정해진 토핑의 합을 모두 누적시킨다. 어차피 각 mod의 값이 유일하지 않다면 누적합 값이 이용되지 않고
        // 유일할 때만 이용되기 때문
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n조각의 피자, 한번에 먹는 조각의 수 l, q개의 쿼리
        int n = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        
        // 정해진 번호에 올라간 토핑
        HashMap<Integer, Integer> pizzas = new HashMap<>();
        // 각 mod l에 속하는 피자 조각의 수
        HashMap<Integer, Integer> modCount = new HashMap<>();
        // mod l에 속하는 피자 조각의 수를 우선순위큐를 통해 내림차순으로 본다.
        // 가장 앞의 값이 1보다 크다면 무조건 불가능한 경우.
        PriorityQueue<Pizza> counts = new PriorityQueue<>((o1, o2) -> Integer.compare(o2.topping, o1.topping));
        // 현재 토핑이 올라간 조각의 토핑 합.
        long sum = 0;

        StringBuilder sb = new StringBuilder();
        // q개의 쿼리 처리
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            // 1번 쿼리인 경우
            if (Integer.parseInt(st.nextToken()) == 1) {
                // x번 조각에 토핑을 t만큼 올린다.
                int x = Integer.parseInt(st.nextToken());
                int t = Integer.parseInt(st.nextToken());
                
                // 만약 x번 조각에 토핑이 올라가있지 않다면 
                if (pizzas.getOrDefault(x, 0) == 0) {
                    // 누적합 처리
                    sum += t;
                    // 토핑 값 추가
                    pizzas.put(x, t);
                    // x mod l에 개수 추가
                    modCount.put(x % l, modCount.getOrDefault(x % l, 0) + 1);
                    // 해당 개수를 반영하여 우선순위큐에 추가.
                    counts.offer(new Pizza(x % l, modCount.get(x % l)));
                } else if (pizzas.get(x) != t) {        // 만약 토핑이 올라가있는데 t값과 다른 경우
                    // 토핑의 값이 변경된다.
                    // 누적합도 변경
                    sum += t - pizzas.get(x);
                    pizzas.put(x, t);
                    // modCount나 우선순위큐에 값은 변하지 않는다.
                }       // 그 외의 경우 x번 조각에 이미 t만큼 토핑이 올라가있는 경우. 무시
            } else {
                // 2번 쿼리인 경우
                // x번 조각에 토핑을 제거한다.
                int x = Integer.parseInt(st.nextToken());
                // 누적합
                sum -= pizzas.get(x);
                // 피자의 토핑 제거
                pizzas.put(x, 0);
                // modCount의 개수 차감
                modCount.put(x % l, modCount.get(x % l) - 1);
            }
            
            // 그 후, 우선순위큐가 비어있지 않다면
            // 가장 앞의 값이 유효한지 따진다. 
            // 토핑이 제거되거나 변경된 경우, 해당 값이 유효하지 않으므로 제거
            while (!counts.isEmpty() && counts.peek().topping > modCount.get(counts.peek().idx))
                counts.poll();
            
            // 우선순위큐가 비어있지 않은데, 가장 앞의 값이 1보다 크다면 불가능한 경우
            // NO 기록
            if (!counts.isEmpty() && counts.peek().topping > 1)
                sb.append("NO");
            else        // 비어있거나, 가장 앞의 값이 1보다 같거나 작다면 가능한 경우. 누적합 값 기록
                sb.append("YES ").append(sum);
            sb.append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}