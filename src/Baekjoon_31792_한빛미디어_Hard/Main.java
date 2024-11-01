/*
 Author : Ruel
 Problem : Baekjoon 31792번 한빛미디어 (Hard)
 Problem address : https://www.acmicpc.net/problem/31792
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_31792_한빛미디어_Hard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) throws IOException {
        // 책에 관련된 데이베이스를 관리한다.
        // 책을 진열하는 하나의 페이지에는 가격이 두배 이상 차이 나는 책을 한 번에 진열할 수 없다.
        // 3000원인 책과 5000원인 책은 한 페이지에 진열할 수 있지만, 6000원인 책은 함께 진열할 수 없다.
        // q개의 쿼리가 주어지며
        // 1 s : 가격이 s인 책을 하나 추가한다.
        // 2 s : 가격이 s인 책을 하나 제거한다. 가격이 s인 책이 없다면 실행되지 않는다.
        // 3 : 책을 진열하기 위한 페이지의 최솟값을 출력한다. 책이 없다면 0개의 페이지로 모든 책을 진열할 수 있다.
        //
        // 트리 맵 문제
        // 자료 구조 중 잘 사용하지 않는 트리 맵을 사용해야하는 문제
        // 기본적인 map이나 트리 형태를 띄고 있어, 정렬이 된다는 특징을 갖고 있다.
        // 해쉬 맵에 비해, 값 추가, 제거가 느리나, 정렬이 된다는 이점을 갖고 있어 순차적인 값을 탐색할 때 유리하다.
        // 트리 맵을 통해 값을 정리하고 쿼리를 처리하면 되는 문제
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // q개의 쿼리
        int q = Integer.parseInt(br.readLine());

        StringTokenizer st;
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int o = Integer.parseInt(st.nextToken());
            // 1번 쿼리 책 추가
            if (o == 1) {
                int s = Integer.parseInt(st.nextToken());
                if (!treeMap.containsKey(s))
                    treeMap.put(s, 1);
                else
                    treeMap.put(s, treeMap.get(s) + 1);
            } else if (o == 2) {        // 2번 쿼리 책 제거
                int s = Integer.parseInt(st.nextToken());
                if (treeMap.containsKey(s)) {
                    if (treeMap.get(s) == 1)
                        treeMap.remove(s);
                    else
                        treeMap.put(s, treeMap.get(s) - 1);
                }
            } else {
                // 3번 쿼리
                // 만약 트리 맵이 비어있다면 답은 0
                if (treeMap.isEmpty())
                    sb.append(0).append("\n");
                else {
                    // 그렇지 않다면
                    // 가장 첫 키를 가져와 첫 페이지를 만들고
                    int count = 1;
                    int key = treeMap.firstKey();
                    // 해당 책과 한 페이지에 진열되는 책을 제외한 책 중 가격이 가장 싼 책을 찾는다.
                    // 트리맵에 higherKey를 사용한다.
                    // 즉, 현재 key값보다 값이 2배 이상 되는 책이 존재하는 경우
                    while (treeMap.higherKey(key * 2 - 1) != null) {
                        // key는 해당 값으로 변경
                        key = treeMap.higherKey(key * 2 - 1);
                        // 페이지 증가
                        count++;
                    }
                    // 답 기록
                    sb.append(count).append("\n");
                }
            }
        }
        // 전체 쿼리 처리 결과 출력
        System.out.print(sb);
    }
}