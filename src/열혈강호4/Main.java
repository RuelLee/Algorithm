/*
 Author : Ruel
 Problem : Baekjoon 11378번 열혈강호 4
 Problem address : https://www.acmicpc.net/problem/11378
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 열혈강호4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<List<Integer>> canHandle;
    static int[] works;

    public static void main(String[] args) {
        // 이분매칭 문제!
        // 네트워크 유량으로도 풀수 있다고 하는데 이는 다음 번에!
        // 특이한 점이 있다면 패널티가 있는 한 패널티 개수만큼 추가적인 일을 맡을 수 있다.
        // 일단 한번 모두에게 일을 할당한 뒤
        // 패널티가 있는 한 추가적인 일을 할당하도록 한다.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        int k = sc.nextInt();

        canHandle = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            canHandle.add(new ArrayList<>());
        works = new int[m + 1];

        for (int i = 0; i < n; i++) {
            int can = sc.nextInt();
            for (int j = 0; j < can; j++)
                canHandle.get(i + 1).add(sc.nextInt());
        }

        int allocatedWorks = 0;     // 할당된 일의 개수를 체크
        boolean[] visited = new boolean[m + 1];
        for (int i = 1; i < n + 1; i++) {
            if (allocateWork(i, visited))
                allocatedWorks++;
        }

        boolean diff = true;        // 변화가 생기는지 체크한다
        while (k > 0 && diff) {     // 패널티가 남아있고 변화가 있는 한 반복
            diff = false;       // 변화 초기화
            visited = new boolean[m + 1];   // 한번 접근했던 일에 대해 다음 번엔 접근하지 않아도 된다! 이를 체크해줄 visited 배열
            for (int i = 1; i < n + 1; i++) {
                if (k == 0)     // 패널티가 0이라면 스톱
                    break;

                if (allocateWork(i, visited)) {     // 추가일을 할당하는데 성공했다면
                    k--;        // 패널티 줄이고
                    diff = true;        // 변화가 생겼다고 체크
                    allocatedWorks++;       // 일 할당 개수 1 추가
                }
            }
        }
        System.out.println(allocatedWorks);     // 할당된 일의 개수 출력
    }

    static boolean allocateWork(int worker, boolean[] visited) {
        for (int work : canHandle.get(worker)) {
            if (works[work] == 0) {     // 할당되지 않은 일이라면
                works[work] = worker;       // worker를 할당하고 true 반환
                return true;
            }
        }
        for (int work : canHandle.get(worker)) {
            if (!visited[work]) {       // 이번에 체크할 건 다른 worker가 할당된 일. 방문하지 않았던 work여야하고
                visited[work] = true;
                if (allocateWork(works[work], visited)) {       // 해당 일을 맡은 사람을 다른 일로 돌리는데 성공했다면
                    works[work] = worker;       // work는 worker가 맡고, true 반환.
                    return true;
                }
            }
        }
        return false;
    }
}