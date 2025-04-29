/*
 Author : Ruel
 Problem : Baekjoon 25542번 약속 장소
 Problem address : https://www.acmicpc.net/problem/25542
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25542_약속장소;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static char[][] names;
    static List<List<Character>> list;

    public static void main(String[] args) throws IOException {
        // 길이가 l인 이름 n개가 주어진다.
        // 이들의 이름과 많아야 한 글자만 다른 가게 이름을 찾아보자.
        //
        // 브루트 포스 문제
        // 주어진 이름들과 다른 글자가 많지 않은 이름을 찾는 것이므로
        // 주어진 이름의 순서에 해당하는 알파벳들을 찾고, 그 알파벳들로 만들 수 있는
        // 이름을 브루트 포스로 계산한다.
        // 한 순서의 알파벳을 선택할 때, 그 알파벳과 다른 알파벳을 갖는 이름을 기억해두고
        // 다른 순서의 알파벳을 선택할 때, 해당 사항을 반영하여, 두 글자 이상이 다른지 판별한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 길이 l의 n개의 이름들
        int n = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());
        
        // 이름
        names = new char[n][];
        for (int i = 0; i < names.length; i++)
            names[i] = br.readLine().toCharArray();

        // 각 순서에 오는 알파벳들
        list = new ArrayList<>();
        for (int i = 0; i < l; i++)
            list.add(new ArrayList<>());

        for (int i = 0; i < l; i++) {
            for (int j = 0; j < n; j++) {
                if (!list.get(i).contains(names[j][i]))
                    list.get(i).add(names[j][i]);
            }
        }
        
        // 답이 저장될 공간
        char[] answer = new char[l];
        // 가능 여부
        boolean possible = bruteForce(0, new int[n], answer);

        StringBuilder sb = new StringBuilder();
        // 가능하다면 해당 이름 기록
        if (possible) {
            for (char c : answer)
                sb.append(c);
        } else      // 불가능하다면 CALL FRIEND 기록
            sb.append("CALL FRIEND");
        // 답 출력
        System.out.println(sb);
    }
    
    // 현재 순서 idx, 선택한 알파벳과 다른 알파벳을 갖고 있던 이름 diffCount, 답 후보 answer
    static boolean bruteForce(int idx, int[] diffCount, char[] answer) {
        // idx가 끝까지 도달했다면 가능한 경우이므로 true 반환
        if (idx == list.size())
            return true;
        
        // idx 순서에 올 알파벳을 선정
        for (char c : list.get(idx)) {
            boolean possible = true;
            // c와 다른 알파벳을 갖고 있는 이름들을 찾는다.
            int[] count = new int[names.length];
            for (int j = 0; j < names.length; j++) {
                // j의 idx번째 알파벳이 c와 다르다면
                if (c != names[j][idx]) {
                    // 만약 이전에 선택할 알파벳이 이미 j번째 이름과 달랐던 경우
                    // 많아야 한 글자만 다르다는 조건을 위반하므로
                    // possible false, 후 반복문 종료
                    if (diffCount[j] + count[j] >= 1) {
                        possible = false;
                        break;
                    } else      // 이번이 처음 다른 거라면 그냥 count[j] 하나 증가
                        count[j]++;
                }
            }
            
            // c 알파벳으로 하는 것이 가능하다면
            if (possible) {
                // count에 diffCount를 합쳐
                // 전체 다른 개수를 누적시킨다.
                for (int j = 0; j < count.length; j++)
                    count[j] += diffCount[j];
                // idx번째에 c 기록
                answer[idx] = c;
                // 해당 변수들로 다시 bruteForce 함수를 불러
                // 결과값이 true가 돌아온다면 true 반환.
                if (bruteForce(idx + 1, count, answer))
                    return true;
            }
        }
        // 위에서 true로 함수가 끝나지 못했다면
        // false 반환
        return false;
    }
}