/*
 Author : Ruel
 Problem : Baekjoon 11376번 열혈강호 2
 Problem address : https://www.acmicpc.net/problem/11376
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 열혈강호2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Worker {
    int num;
    int capacity = 2;
    int allocated = 0;
    List<Integer> ableWorks;

    public Worker(int num, List<Integer> ableWorks) {
        this.num = num;
        this.ableWorks = ableWorks;
    }
}

public class Main {
    static int[] works;
    static Worker[] workers;

    public static void main(String[] args) {
        // 열혈강호 문제와 기본적으론 동일하나, 한 사람 당 맡을 수 있는 일이 2개라 생각할 것들이 있었다.
        // 일을 할당하는 것과 바꾸는 것 메소드를 분리할 것이냐, 합친다면 어떻게 만들 것인가
        // 일을 바꿀 때 재귀를 태워보내는데, 한 사람에게 한번만 일을 바꾸게 할 수 있는가 아니면 여러번 바꾸게 할 수 있는가
        // 맡은 일이 2개 이므로, 2번까지 일을 바꾸게 참조해도 된다
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();

        workers = new Worker[n + 1];
        for (int i = 1; i < workers.length; i++) {
            int count = sc.nextInt();
            List<Integer> works = new ArrayList<>();
            for (int j = 0; j < count; j++)
                works.add(sc.nextInt());
            workers[i] = new Worker(i, works);
        }
        works = new int[m + 1];
        for (int i = 1; i < workers.length; i++)
            allocateWork(i, -1, new int[n + 1]);        // 일을 바꾸는게 아니라 할당만 할 땐 -1

        int count = 0;
        for (int i = 1; i < works.length; i++) {
            if (works[i] != 0)
                count++;
        }
        System.out.println(count);
    }

    static boolean allocateWork(int worker, int toSwitchWork, int[] check) {
        if (toSwitchWork != -1)     // 일을 바꿔야한다면 할당된 일을 개수를 줄여주자.
            workers[worker].allocated--;

        for (int work : workers[worker].ableWorks) {
            if (workers[worker].capacity == workers[worker].allocated)
                break;

            if (works[work] == 0) {
                works[work] = worker;
                workers[worker].allocated++;
            }
        }
        if (workers[worker].capacity == workers[worker].allocated)      // 용량을 채웠다면 무조건 true
            return true;

        check[worker]++;    // 현재 worker를 한번 참조한 셈이므로 check[worker] 값을 하나 상승
        for (int work : workers[worker].ableWorks) {
            if (workers[worker].capacity == workers[worker].allocated)      // 가득 채웠다면 종료
                break;

            if (work == toSwitchWork)       // 이번에 바꿔야하는 일이라면 패쓰
                continue;

            if (check[works[work]] < 2) {   // 아직 2번 참조를 안했다면
                check[works[work]]++;   // 하나 증가시켜주고
                if (allocateWork(works[work], work, check)) {   // works[work]가 갖고 있는 work를 다른 일로 할당하도록 한다. 성공했다면
                    works[work] = worker;       // works[work]에는 새로 worker로 할당해주고
                    workers[worker].allocated++;        // worker의 할당된 일의 개수 증가
                    if (toSwitchWork != -1)     // 혹시 worker의 toSwitchWork 일을 바꿔야하는 것이었다면 성공했으므로 true 반환
                        return true;
                }
            }
        }
        return workers[worker].capacity == workers[worker].allocated;
    }
}