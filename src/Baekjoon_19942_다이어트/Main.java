/*
 Author : Ruel
 Problem : Baekjoon 19942번 다이어트
 Problem address : https://www.acmicpc.net/problem/19942
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_19942_다이어트;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

// 결과를 위한 클래스
class Result implements Comparable<Result> {
    // 값과 선택한 식재료들을 표시할 boolean 배열
    int cost;
    boolean[] selected;

    public Result(int cost, boolean[] selected) {
        this.cost = cost;
        this.selected = selected;
    }

    // 결과값 비교를 위해 Comaprable 인터페이스를 implements 하고
    // compareTo 메소드를 오버라이딩.
    @Override
    public int compareTo(Result o) {
        if (cost == o.cost) {       // 만약 값이 같다면
            if (cost == Integer.MAX_VALUE)      // 둘 다 큰 값이라면 비교할 것 없다.
                return -1;

            // 아니라면 사전순으로 이른 것을 반환해야한다.
            // 식재료에 선택 유무에 따른 수들로 직접 String을 만들어버리고 비교해버리자.
            StringBuilder a = new StringBuilder();
            StringBuilder b = new StringBuilder();
            for (int i = 0; i < selected.length; i++) {
                if (selected[i])
                    a.append(i);
                if (o.selected[i])
                    b.append(i);
            }
            // 만들어진 a, b 문자열에 대한 비교값을 넘긴다.
            return a.compareTo(b);
        }
        // 값이 다르다면 더 적은 값을 우선하도록 한다.
        return Integer.compare(cost, o.cost);
    }
}

public class Main {
    static int n;
    static int[] minimumLine;
    static int[][] ingredients;

    public static void main(String[] args) throws IOException {
        // n개의 식재료와 해당 식재료의 단백질, 지방, 탄수화물, 비타민, 가격이 주어진다.
        // 최저 영양소 기준이 주어질 때, 최소 비용으로 기준을 만족하는 조합을 만들고자 할 때
        // 다만 같은 비용을 갖는 집합이 여러개라면 사전순으로 빠른 것을 출력한다.
        // 해당 조합의 비용과 식재료 집합을 출력하라.
        //
        // 백트래킹
        // n이 최대 15개로 작으므로, 직접 모든 경우의 수를 조합해보는 것이 가능하다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력 처리
        n = Integer.parseInt(br.readLine());
        // 최소 영양소 기준
        minimumLine = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 식재료들
        ingredients = new int[n][];
        for (int i = 0; i < ingredients.length; i++)
            ingredients[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 백트래킹으로 만족하는 조합을 찾는다.
        Result result = backTracking(0, new boolean[n], new int[5]);
        StringBuilder sb = new StringBuilder();
        // 없다면 -1 출력
        if (result.cost == Integer.MAX_VALUE)
            sb.append(-1);
        else {      // 존재한다면 가격과 식재료 집합 출력.
            sb.append(result.cost).append("\n");
            for (int i = 0; i < result.selected.length; i++) {
                if (result.selected[i])
                    sb.append(i + 1).append(" ");
            }
            sb.deleteCharAt(sb.length() - 1);
        }

        System.out.println(sb);
    }

    // 백트래킹을 활용하여 모든 조합을 찾는다.
    static Result backTracking(int idx, boolean[] selected, int[] sums) {
        // n번 식재료까지 선택하여 1가지 조합 탐색이 끝났다면
        if (idx >= n) {
            // 최저 영양소를 만족하는지 확인
            for (int i = 0; i < minimumLine.length; i++) {
                if (sums[i] < minimumLine[i])   // 그렇지 않다면 값으로 큰 값을 리턴.
                    return new Result(Integer.MAX_VALUE, null);
            }
            // 만족한다면 해당 결과값 리턴.
            return new Result(sums[4], selected.clone());
        }

        // idx 식재료를 선택한 경우.
        // 식재료 선택 체크
        selected[idx] = true;
        // 총합 영양소 반영
        for (int i = 0; i < sums.length; i++)
            sums[i] += ingredients[idx][i];
        // 다음 식재료 선택
        Result a = backTracking(idx + 1, selected, sums);
        
        // idx 식재료를 선택하지 않은 경우.
        // 위에서 선택한 식재료와 더한 영양소들 복구.
        selected[idx] = false;
        for (int i = 0; i < sums.length; i++)
            sums[i] -= ingredients[idx][i];
        // 다음 식재료 선택
        Result b = backTracking(idx + 1, selected, sums);

        // 두 결과 중 더 값이 적거나, 같다면 사전순으로 이른 것을 리턴.
        return a.compareTo(b) < 0 ? a : b;
    }
}