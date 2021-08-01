/*
 Author : Ruel
 Problem : Baekjoon 3665번 최종 순위
 Problem address : https://www.acmicpc.net/problem/3665
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 최종순위;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // 위상 정렬 문제
        // 팀 간의 랭킹 연관도 때문에 각 팀을 등록할 때 이후 팀에 대해 모두 연관성을 만들어줘야한다
        // 1 2 3이 주어질때, 1은 -> 2, 3 ...
        // 순서가 뒤바뀐 경우에는 작년 랭킹을 참고하여 진입차수를 변경해주고, 연관관계를 뒤집어준다.
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int tc = 0; tc < t; tc++) {
            int n = sc.nextInt();

            int[] lastYearRank = new int[n + 1];
            int[] inDegree = new int[n + 1];
            List<Integer>[] nextNums = new List[n + 1];
            for (int i = 1; i < nextNums.length; i++)
                nextNums[i] = new ArrayList<>();

            List<Integer> temp = new ArrayList<>();     // 순차적으로 앞 팀들을 모두 모을 것이다.
            for (int i = 0; i < n; i++) {
                int current = sc.nextInt();
                lastYearRank[current] = i + 1;
                for (int pre : temp)    // 이전 팀들에 대해 current 팀의 연관관계를 만들어준다.
                    nextNums[pre].add(current);
                inDegree[current] = temp.size();        // 진입차수는 temp 크기만큼!
                temp.add(current);
            }

            int m = sc.nextInt();
            for (int i = 0; i < m; i++) {
                int a = sc.nextInt();
                int b = sc.nextInt();

                if (lastYearRank[a] > lastYearRank[b]) {        // 앞 숫자가 원래 더 낮은 순위였다면
                    nextNums[b].remove(Integer.valueOf(a));
                    nextNums[a].add(b);
                    inDegree[a]--;
                    inDegree[b]++;
                } else {            // 뒷 숫자가 더 낮은 순위였다면
                    nextNums[a].remove(Integer.valueOf(b));
                    nextNums[b].add(a);
                    inDegree[a]++;
                    inDegree[b]--;
                }
            }

            Queue<Integer> queue = new LinkedList<>();
            for (int i = 1; i < inDegree.length; i++) {
                if (inDegree[i] == 0)
                    queue.add(i);
            }
            StringBuilder sb = new StringBuilder();
            int count = 0;
            while (queue.size() == 1) {     //큐 사이즈가 1보다 커진다면 두 숫자 간의 순서를 알 수 없는 상태!
                int current = queue.poll();

                for (int next : nextNums[current]) {
                    if (--inDegree[next] == 0)
                        queue.add(next);
                }
                sb.append(current).append(" ");
                count++;
            }

            if (queue.size() > 1)       // 두 숫자의 순서를 알 수 없다
                sb = new StringBuilder("?");
            else if (count != n)        // 모든 숫자의 순위가 정해지지 않았는데 다음 차례가 없다.
                sb = new StringBuilder("IMPOSSIBLE");
            System.out.println(sb);
        }
    }
}