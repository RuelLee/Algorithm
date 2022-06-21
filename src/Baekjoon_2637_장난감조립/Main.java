/*
 Author : Ruel
 Problem : Baekjoon 2637번 장난감 조립
 Problem address : https://www.acmicpc.net/problem/2637
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2637_장난감조립;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Part {
    int num;
    int value;

    public Part(int num, int value) {
        this.num = num;
        this.value = value;
    }
}

public class Main {
    static HashMap<Integer, HashMap<Integer, Integer>> memo;
    static List<List<Part>> recipes;

    public static void main(String[] args) throws IOException {
        // n개의 번호가 주어지고, 1 ~ n-1까지는 기본 부품이나 중간 부품, n은 완제품을 나타낸다
        // m개의 줄에는 어떤 부품을 완성하는데 필요한 부품들 간의 관계가 x, y, k로 주어진다
        // x라는 부품을 만들기 위해서는 y라는 부품이 k개 필요하다는 뜻이다.
        // 완제품을 만드는데 필요한 기본 부품(중간 부품 출력 x)들의 개수를 부품 번호 오름차순으로 출력하라
        //
        // 위상정렬 혹은 메모이제이션을 활용해서 풀 수 있는 문제
        // 메모이제이션을 활용하여 bottom up 방식으로 풀었다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());

        // 해당 부품을 만드는데 필요한 하위 부품들을 저장할 공간.
        recipes = new ArrayList<>(n + 1);
        for (int i = 0; i < n + 1; i++)
            recipes.add(new ArrayList<>());

        // 필요한 하위 부품들을 저장.
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            recipes.get(Integer.parseInt(st.nextToken()))
                    .add(new Part(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }

        // 메모이제이션으로 해당 번호의 부품을 만들기 위해 필요한 기본 부품들을 저장한다.
        memo = new HashMap<>();
        // 완제품을 만드는 기본 부품들을 list로 받는다.
        List<Part> list = calcSubParts(n, 1);
        // 부품 번호로 오름차순 정렬.
        list.sort(Comparator.comparing(part -> part.num));
        // 출력.
        StringBuilder sb = new StringBuilder();
        for (Part p : list)
            sb.append(p.num).append(" ").append(p.value).append("\n");
        System.out.print(sb);
    }

    // n번 제품을 value개 만드는 기본 부품의 개수를 구한다.
    static List<Part> calcSubParts(int n, int value) {
        // 메모이제이션이 되어있다면 값을 참고한다.
        if (memo.containsKey(n)) {
            List<Part> parts = new ArrayList<>(memo.get(n).size());
            // 각 기본 부품들을 value개씩 곱해 list로 만들어 리턴해주자.
            for (int part : memo.get(n).keySet())
                parts.add(new Part(part, memo.get(n).get(part) * value));
            return parts;
        }

        // 메모이제이션이 안된 경우.
        // 필요한 기본 부품들을 해쉬맵을 통해 elements에 저장하자.
        HashMap<Integer, Integer> elements = new HashMap<>();
        // n번 부품을 만들기 위해서는 p.num 부품이 필요하다.
        for (Part p : recipes.get(n)) {
            // p.num 부품이 기본 부품이라면
            if (recipes.get(p.num).isEmpty()) {
                // 이미 해쉬맵에 저장되어있는 부품이라면 필요 개수를 증가시켜준다.
                if (elements.containsKey(p.num))
                    elements.put(p.num, elements.get(p.num) + p.value);
                // 그렇지 않다면 해당 개수만큼으로 해쉬맵에 추가.
                else
                    elements.put(p.num, p.value);
            } else {
                // 기본 부품이 아니라면 재귀적으로 메소드를 불러 기본 부품들로 받자.
                // p.num 부품을 p.value개 만드는데 필요한 기본 부품들의 리스트.
                List<Part> subList = calcSubParts(p.num, p.value);
                for (Part pa : subList) {
                    // 각 부품들이 해쉬맵에 이미 기록되어있는 부품인지 확인하고
                    if (elements.containsKey(pa.num))
                        elements.put(pa.num, elements.get(pa.num) + pa.value);
                    // 그렇다면 값을 더해주고, 아니라면 해당 값을 새로 추가해주자.
                    else
                        elements.put(pa.num, pa.value);
                }
            }
        }

        // elements에는 n부품 하나를 만드는데 필요한 기본 부품들이 기록되어있다.
        // 해당 해쉬맵을 메모해주고,
        memo.put(n, elements);
        // 해당 부품들의 리스트를 만들고, 필요한 개수인 value개씩 곱해 리턴해주자.
        List<Part> list = new ArrayList<>(elements.size());
        for (int part : elements.keySet())
            list.add(new Part(part, elements.get(part) * value));
        return list;
    }
}