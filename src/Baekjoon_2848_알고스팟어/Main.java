/*
 Author : Ruel
 Problem : Baekjoon 2848번 알고스팟어
 Problem address : https://www.acmicpc.net/problem/2848
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2848_알고스팟어;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 영어 알파벳을 사용하지만, 영어와 알파벳 순서가 다른 언어가 주어진다.
        // n개의 단어가 사전순 정렬 순서로 주어질 때
        // 알파벳 순서를 출력하라
        // 올바른 순서가 없다면 !, 가능한 순서가 두 개 이상이라면 ?을 출력한다.
        //
        // 위상 정렬 문제
        // 순차적으로 등장하는 단어들을 비교하여, 달라지는 지점의 알파벳의 순서를 알아낼 수 있다.
        // 이를 통해 위상정렬로 알파벳들의 순서를 구한다.
        // 각 단계에서 알파벳은 하나여야지만 각 알파벳끼리의 순서가 명확해진다.
        // 복수의 알파벳이 동일 단계에 주어진다면, 해당 알파벳끼리는 순서가 모호하므로 ?를 출력해야한다.
        // 또한 순서가 순환하거나, aa a와 같이 올바르지 않은 순서가 들어오면 !을 출력한다

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 단어
        int n = Integer.parseInt(br.readLine());
        String[] words = new String[n];
        // 알파벳의 등장 여부
        boolean[] appeared = new boolean[26];
        for (int i = 0; i < n; i++) {
            words[i] = br.readLine();
            for (int j = 0; j < words[i].length(); j++)
                appeared[words[i].charAt(j) - 'a'] = true;
        }

        // 위상 정렬을 위한 초기화
        // 진입 차수
        int[] indegree = new int[26];
        List<List<Integer>> lists = new ArrayList<>();
        for (int i = 0; i < 26; i++)
            lists.add(new ArrayList<>());

        boolean possible = true;
        // n개의 단어를 앞뒤로 살펴본다.
        for (int i = 0; i < n - 1 && possible; i++) {
            boolean allSame = true;
            // i번째 단어와 i+1번째 단어를 더 짧은 길이의 단어만큼, 두 단어들을 비교한다.
            for (int j = 0; j < Math.min(words[i].length(), words[i + 1].length()); j++) {
                // 알파벳이 달라지는 지점
                if (words[i].charAt(j) != words[i + 1].charAt(j)) {
                    int pre = words[i].charAt(j) - 'a';
                    int after = words[i + 1].charAt(j) - 'a';
                    allSame = false;

                    // 만약 after에 pre가 등록되어있다면, 서로 순환하는 관계이므로 불가능하다.
                    if (lists.get(after).contains(pre))
                        possible = false;
                    else {
                        // 그 외에는 위상정렬을 위해 값을 추가
                        lists.get(pre).add(after);
                        indegree[after]++;
                    }
                    // 이후로는 살펴볼 필요 없으므로 break
                    break;
                }
            }
            // 만약 살펴본 범위 내의 알파벳들이 모두 일치하는데, i번째 단어가 i+1번째 단어보다 더 길다면
            // 불가능한 경우
            if (allSame && words[i].length() > words[i + 1].length())
                possible = false;
        }

        // 불가능한 경우 ! 출력
        if (!possible)
            System.out.println("!");
        else {
            // 위상 정렬
            Queue<Integer> queue = new LinkedList<>();
            // 등장한 알파벳 중, 진입 차수가 0인 알파벳들을 추가
            for (int i = 0; i < 26; i++)
                if (appeared[i] && indegree[i] == 0)
                    queue.offer(i);
            boolean calculable = true;
            StringBuilder sb = new StringBuilder();
            // 큐에서 제거하며 순서를 기록
            while (!queue.isEmpty()) {
                // 만약 큐의 사이즈가 2 이상이라면
                // 해당하는 알파벳끼리의 순서는 모호한 상태.
                // ! 출력
                if (queue.size() > 1) {
                    calculable = false;
                    break;
                }

                // 그 외의 경우는 위상 정렬을 계속
                int current = queue.poll();
                sb.append((char) (current + 'a'));
                for (int next : lists.get(current)) {
                    if (--indegree[next] == 0)
                        queue.offer(next);
                }
            }
            // 모든 단계에 알파벳이 하나씩으로 순서들을 계산할 수 있다면
            // StringBuilder에 기록된 알파벳들을 출력
            if (calculable)
                System.out.println(sb);
            else    // 중간에 큐에 2개 이상이 들어가 순서가 모호한 알파벳들이 존재한다면 ? 출력
                System.out.println("?");
        }
    }
}