/*
 Author : Ruel
 Problem : Baekjoon 17612번 쇼핑몰
 Problem address : https://www.acmicpc.net/problem/17612
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17612_쇼핑몰;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Customer {
    int id;
    int exitTIme;
    int cashier;

    public Customer(int id, int exitTIme, int cashier) {
        this.id = id;
        this.exitTIme = exitTIme;
        this.cashier = cashier;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 고객과 k개의 계산대가 있다.
        // n명의 고객에 대해서는 각각 고객id, 구매한 물건의 수가 주어진다
        // 각 고객은 비어있는 계산대로 이동하며, 여러개일 경우, 번호가 낮은 계산대로 이동한다
        // 각 물건을 계산하는 시간은 물건당 1분이 소요된다
        // 그리고 계산을 마친 고객은 마친 시간순으로 퇴장하되, 마친 시간이 같은 고객이 여러명일 경우
        // 출구와 가까운 번호가 높은 계산대 순으로 퇴장을 한다.
        // 고객의 퇴장 순서 * 고객 id를 모두 더해 출력하라
        //
        // 계산대에 진입하는 순서와, 퇴장하는 순서가 달라, 이를 고려해야하는 문제
        // 우선순위큐(힙)을 사용하여 풀었다.
        // 계산대가 비어있을 경우에는 번호가 낮은 순으로 고객을 할당하고
        // 가득차있을 경우에는, 가장 계산 시간이 이른 고객을 참고하여 다음 계산대를 할당한다
        // 계산이 끝난 고객은 다시 시간과 계산대 번호를 비교하는 우선순위큐에 삽입하여
        // 최종적으로 이에서 하나씩 꺼내며 답을 구한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 계산을 하려는 고객들.
        PriorityQueue<Customer> cashiers = new PriorityQueue<>((o1, o2) -> {
            // 퇴장 시간이 동일하다면, 번호가 낮은 계산대를 우선한다.
            if (o1.exitTIme == o2.exitTIme)
                return Integer.compare(o1.cashier, o2.cashier);
            // 그렇지 않다면 퇴장 시간 순을 우선한다.
            return Integer.compare(o1.exitTIme, o2.exitTIme);
        });

        // 퇴장한 고객들.
        PriorityQueue<Customer> exitCustomers = new PriorityQueue<>((o1, o2) -> {
            // 퇴장 시간이 같다면, 번호가 큰 계산대에서 계산한 손님이 우선적으로 퇴장한다.
            if (o1.exitTIme == o2.exitTIme)
                return Integer.compare(o2.cashier, o1.cashier);
            // 그렇지 않다면 퇴장 시간을 우선한다.
            return Integer.compare(o1.exitTIme, o2.exitTIme);
        });

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int id = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            // 계산대에 모두 고객이 배치 되지 않았다면, 작은 순서대로 할당한다.
            if (cashiers.size() < k)
                cashiers.offer(new Customer(id, w, cashiers.size() + 1));
            else {
                // 모두 고객이 배치되어있다면
                // 가장 우선적으로 계산이 끝나면서 가장 번호가 이른 계산대를 이용한 고객으 찾고
                Customer preCustomer = cashiers.poll();
                // 뒤이어 다음 손님의 정보를 preCustomer를 활용하여 생성 후, 삽입한다.
                cashiers.offer(new Customer(id, preCustomer.exitTIme + w, preCustomer.cashier));
                // 계산을 마친 손님은 exitCustomers에 삽입한다.
                exitCustomers.offer(preCustomer);
            }
        }
        // 계산대에 있는 모든 고객을 퇴장시킨다.
        while (!cashiers.isEmpty())
            exitCustomers.offer(cashiers.poll());

        long answer = 0;
        int orderCount = 1;
        // exitCustomers에는 퇴장 시간순, 계산대 번호가 큰 순으로, 손님들을 꺼낼 수 있다.
        // 손님들을 꺼내며 순서에 따라 순서 * id 값을 answer에 더해준다.
        // id * 순서 값이 Integer 범위를 벗어날 수 있음을 주의하자.
        while (!exitCustomers.isEmpty())
            answer += (long) orderCount++ * exitCustomers.poll().id;
        System.out.println(answer);
    }
}