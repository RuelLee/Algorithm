/*
 Author : Ruel
 Problem : Baekjoon 1201번 NMK
 Problem address : https://www.acmicpc.net/problem/1201
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1201_NMK;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // 1 ~ n 까지의 숫자를 사용하여, 가장 긴 증가하는 부분 수열의 길이가 m이고
        // 가장 긴 감소하는 부분 수열의 길이가 k인 수열을 출력하는 문제
        // 어떻게 풀어야할지 조금 고민을 해야했다
        // 가장 긴 증가하는 부분수열을 만들기 위해서 숫자를 m개의 그룹으로 나누고
        // 하나 이상의 그룹에 k개의 원소를 넣어준다
        // 그 후 각 그룹의 숫자를 역순으로 출력하면 위 조건을 만족하는 수열을 뽑을 수 있다
        // 증가하는 수열은 각 그룹에서 하나의 숫자를 선택하게 될 것이고
        // 감소하는 수열은 그룹들 중 하나에서 만족하는 경우가 나온다.
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int k = sc.nextInt();

        // 최소 하나의 k개의 원소를 갖는 그룹과 하나 이상의 원소를 갖는 m-1 개의 그룹이 만들어져야한다
        // 따라서 최소 n은 k + m - 1 보다 같거나 커야한다
        // 또한 최대일 때는 k개씩 m개의 그룹일 때이므로, n * k보다는 같거나 작아야한다.
        if (m + k - 1 > n || n > m * k)
            System.out.println(-1);

        else {
            List<PriorityQueue<Integer>> list = new ArrayList<>();
            for (int i = 0; i < m; i++)
                list.add(new PriorityQueue<>((o1, o2) -> Integer.compare(o2, o1)));

            int num = 0;
            for (int i = 0; i < list.size(); i++) {
                int remainNum = n - num + 1;        // 남은 숫자의 개수
                int remainGroup = list.size() - i;      // 남은 그룹의 개수
                // 이번 그룹에 할당될 숫자의 개수는 남은 숫자에서 남은 그룹을 뺀 값과 k 값 중 작은 값이다.
                int count = Math.min(k, remainNum - remainGroup);
                for (int j = 0; j < count; j++)
                    list.get(i).offer(++num);
            }

            StringBuilder sb = new StringBuilder();
            for (PriorityQueue<Integer> pq : list) {     // 우선순위큐를 모두 돌면서 숫자를 뽑아 출력해준다.
                while (!pq.isEmpty())
                    sb.append(pq.poll()).append(" ");
            }
            System.out.println(sb);
        }
    }
}