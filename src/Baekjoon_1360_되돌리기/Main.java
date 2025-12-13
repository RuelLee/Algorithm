/*
 Author : Ruel
 Problem : Baekjoon 1360번 되돌리기
 Problem address : https://www.acmicpc.net/problem/1360
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1360_되돌리기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) throws IOException {
        // 다음 두 명령어가 주어진다.
        // type c p -> p 시각에 c를 덧붙인다.
        // undo t p -> p 시각에 이전 t초 동안 진행된 명령어들을 되돌린다.
        // n개의 명령어가 주어질 때, 모든 작업이 끝난 뒤 남은 텍스트를 출력하라
        //
        // 구현 문제
        // n이 크지 않기 때문에, 명령어가 들어오는 시점마다 현재 상태를 String 형태로 저장해둔다.
        // undo는 해당 시각만큼의 명령어를 무시하는 것이기 때문에, 해당 시각 이전에 생성된 텍스트를 가져온다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 명령어
        int n = Integer.parseInt(br.readLine());

        // 트리맵을 통해 편하게 접근
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        // 초기값은 빈 문자열
        treeMap.put(Integer.MIN_VALUE, "");
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            String o = st.nextToken();
            // 뒤에 덧붙이는 명령어인 경우
            if (o.equals("type")) {
                char c = st.nextToken().charAt(0);
                int inputTime = Integer.parseInt(st.nextToken());
                // 해당 시각에 c를 덧붙여 값 추가
                treeMap.put(inputTime, treeMap.get(treeMap.lowerKey(inputTime)) + c);
            } else {        // 되돌리는 명령어인 경우
                int t = Integer.parseInt(st.nextToken());
                int inputTime = Integer.parseInt(st.nextToken());
                // 만약 inputTime - t 이후로 입력된 명령어가 없는 경우
                // 되돌릴 명령어가 없기 때문에 무시해도 된다.
                if (treeMap.floorKey(inputTime - t) == null)
                    continue;
                else        // 명령어가 있는 경우. inputTime - t보다 작은 가장 가까운 시각의 텍스트를 가져온다.
                    treeMap.put(inputTime, treeMap.get(treeMap.lowerKey(inputTime - t)));
            }
        }
        // 마지막 시점의 텍스트를 출력
        System.out.println(treeMap.lastEntry().getValue());
    }
}