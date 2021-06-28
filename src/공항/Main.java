package 공항;

import java.util.Scanner;

public class Main {
    static int[] ports;

    public static void main(String[] args) {
        // 게이트와 비행기에는 각자 번호가 있다.
        // 비행기는 자신과 같거나 작은 번호의 게이트로만 도킹할 수 있다.
        // 게이트의 수와, 비행기에 대한 배열이 주어질 때 최대로 입항할 수 있는 비행기의 수는?
        // 보다 많은 수의 비행기가 입항하기 위해서는 최대한 자신과 같거나 작은 번호 중 가장 큰 번호의 게이트에 입항해야한다.
        // 게이트를 int 배열로 짜, 자기보다 작지만 그 중 가장 큰 사용가능 게이트 번호를 저장하자(초기 값은 자기 자신)
        // 그리고 i 비행기가 온다면, i번째 게이트에 접근하여, 해당 게이트가 비어있는지, 아니라면 어느 게이트에 접근해야하는지 확인 후,
        // 게이트에 도킹하고, i번째 게이트와, 도킹한 게이트에 도킹한 게이트-1 값을 저장해주자.
        Scanner sc = new Scanner(System.in);

        int g = sc.nextInt();
        int p = sc.nextInt();

        ports = new int[g + 1];
        int[] flights = new int[p];
        for (int i = 0; i < flights.length; i++)
            flights[i] = sc.nextInt();

        init();         // ports 에 자기 자신 번호로 초기세팅.
        int answer = 0;
        for (int i = 0; i < flights.length; i++) {
            int emptyPort = findEmptyPort(flights[i]);      // 해당 비행기로 접근할 수 있는 게이트 중 가장 큰 값을 찾는다.

            if (emptyPort == 0) {       // 0이라는 값이 나왔다면, 모든 게이트가 사용중이다. 공항 폐쇄.
                answer = i;
                break;
            } else      // 그렇지 않다면 emptyPort 에 도킹하자. 그 후, flight[i] 게이트와 emptyPort 게이트에는 emptyPort-1 값을 저장해주자.
                ports[flights[i]] = ports[emptyPort] = ports[emptyPort - 1];
        }
        // answer 값이 0이라면 모든 비행기가 도킹 -> 답은 p
        // 0이 아니라면 중간에 폐쇄. 이 때 answer 값을 출력.
        System.out.println(answer == 0 ? p : answer);
    }

    static void init() {
        for (int i = 1; i < ports.length; i++)
            ports[i] = i;
    }

    static int findEmptyPort(int n) {
        // 자기 자신의 값을 갖고 있다면 해당 게이트는 비어있다. n값을 그대로 리턴
        if (ports[n] == n)
            return n;
        // 그렇지 않다면 비어있는 게이트(ports[n]의 값이 n인)를 찾아가야한다.
        // 재귀적으로 찾아가며, 중간에 있는 ports 들의 값도 최종적으로 비어있는 ports 의 값으로 갱신해주자.
        return ports[n] = findEmptyPort(ports[n]);
    }
}