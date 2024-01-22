/*
 Author : Ruel
 Problem : Baekjoon 1897번 토달기
 Problem address : https://www.acmicpc.net/problem/1897
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1897_토달기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class Main {
    public static void main(String[] args) throws IOException {
        // 사전에 d개의 단어가 수록되어있다.
        // 선생님은 세글자 단어를 말한다.
        // 이 단어에 어디든 한글자를 추가하여 다음 단어를 찾아갈 때
        // 찾을 수 있는 가장 긴 단어는?
        //
        // 그래프 탐색 문제
        // 단어들을 길이에 따라 구별하여 저장한 뒤
        // 하나씩 길이를 늘려가며 이전 단어보다 한 글자를 제외한 다른 글자들이 일치하는지
        // 여부를 판별해나간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        // d개의 단어
        int d = Integer.parseInt(st.nextToken());
        String start = st.nextToken();
        // 해쉬맵과 리스트를 통해 단어들을 길이 별로 저장한다.
        HashMap<Integer, List<String>> words = new HashMap<>();
        for (int i = 0; i < d; i++) {
            String word = br.readLine();
            if (!words.containsKey(word.length()))
                words.put(word.length(), new ArrayList<>());
            words.get(word.length()).add(word);
        }

        // 큐를 통해 dfs 탐색
        Queue<String> queue = new LinkedList<>();
        // 방문 체크
        HashSet<String> visited = new HashSet<>();
        // 초기값 세팅
        queue.offer(start);
        visited.add(start);
        String maxWord = start;
        while (!queue.isEmpty()) {
            // 현재 단어
            String current = queue.poll();
            
            // 현재 단어보다 길이가 1 긴 단어가 존재한다면
            if (words.containsKey(current.length() + 1)) {
                // 해당 단어들을 살펴본다.
                for (String next : words.get(current.length() + 1)) {
                    // 이미 추가된 단어라면 살펴보지 않는다.
                    if (visited.contains(next))
                        continue;

                    // 한 글자만 추가되므로, 한글자에 한해서 한번 점프가 가능하다고 볼 수 있다.
                    boolean jumped = false;
                    boolean possible = true;
                    for (int i = 0; i < current.length(); i++) {
                        // i번째 글자가 일치하는지 살펴본다.
                        // next에 한해서는 jump가 사용됐다면 i+1번째 글자와 비교한다.
                        // 일치하지 않는다면
                        if (current.charAt(i) != next.charAt(i + (jumped ? 1 : 0))) {
                            // 점프가 아직 사용되지 않았고, i+1번째 글자와 일치한다면
                            // 점프를 사용
                            if (!jumped && current.charAt(i) == next.charAt(i + 1))
                                jumped = true;
                            else {      // 위 경우가 아닌 경우에는 이어나갈 수 없는 경우
                                // possible를 false로 바꾸고 글자 비교 종료.
                                possible = false;
                                break;
                            }
                        }
                    }
                    
                    // next로 다음 단어로 선택하는 것이 가능하다면
                    if (possible) {
                        // 길이를 비교해 길이가 더 긴 단어를 남겨두고
                        maxWord = next.length() > maxWord.length() ? next : maxWord;
                        // 큐에 추가
                        queue.offer(next);
                        // 방문 체크
                        visited.add(next);
                    }
                }
            }
        }
        // 찾을 수 있는 가장 긴 단어를 출력한다.
        System.out.println(maxWord);
    }
}