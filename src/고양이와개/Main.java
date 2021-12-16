/*
 Author : Ruel
 Problem : Baekjoon 2683번 고양이와 개
 Problem address : https://www.acmicpc.net/problem/3683
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 고양이와개;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

class Person {
    String pro;
    String con;

    public Person(String pro, String con) {
        this.pro = pro;
        this.con = con;
    }
}

public class Main {
    static List<List<Integer>> connection;
    static int[] matched;

    public static void main(String[] args) throws IOException {
        // 문제를 푸는 방법에 대한 개념이 상당히 흥미로운 문제
        // 고양이와 개가 주어지고, 각각의 사람은 고양이를 좋아하거나 개를 좋아한다
        // 각각은 투표를 하는데, 자신이 좋아하는 동물에서 다음 라운드로 진출할 하나를 투표하고,
        // 자신이 좋아하지 않는 동물에서 이번 라운드에서 떨어뜨릴 동물 하나를 투표한다
        // 자신의 투표가 반영된 결과가 아니라면 다음 라운드 시청을 안한다고 한다(자신이 원하는 진출과 탈락이 모두 이루어졌을 때만 시청)
        // 방송사에서 최대로 얻을 수 있는 시청자의 수는?
        // 고양이를 좋아하는 사람들은 서로 의견이 충돌하는 경우가 없다(= 탈락시킬 동물이 강아지이므로. 강아지를 좋아하는 사람도 마찬가지)
        // 따라서 고양이를 좋아하는 사람과 그 사람이 탈락시킬 강아지를 진출시키고자 하는 사람들과 간선으로 이어준다. (서로 의견 충돌이 일어나는 사람)
        // 그 후 서로 매칭을 시켜가며 숫자를 센다
        // 만약 강아지를 진출시키고자 하는 사람이 많다면 해당 강아지를 탈락시키고자 했던 사람들의 수가 세어질 것이고
        // 해당 강아지를 탈락시키고자 하는 사람이 많다면 강아지를 진출시키고자 했던 사람의 수가 세어질 것이다.
        // 이찌됐든 해당 의견 충돌에 대해 더 적은 인원의 사람이 세어진다
        // 전체 인원에서 해당 인원의 수를 빼면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            st = new StringTokenizer(br.readLine());

            int c = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            Person[] people = new Person[v];
            for (int i = 0; i < people.length; i++) {
                st = new StringTokenizer(br.readLine());
                people[i] = new Person(st.nextToken(), st.nextToken());
            }

            connection = new ArrayList<>();
            for (int i = 0; i < v; i++)
                connection.add(new ArrayList<>());

            // 의견 충돌이 발생하는 경우를 찾아 서로 연결해준다.
            for (int i = 0; i < v - 1; i++) {
                for (int j = i + 1; j < v; j++) {
                    if (people[i].pro.equals(people[j].con) || people[i].con.equals(people[j].pro)) {
                        if (people[i].pro.charAt(0) == 'C')     // 고양이든 강아지든 한쪽에 대해서만 센다.(의견 충돌이 발생하지 않는 다른 동물은 모두 진출시킬 것이므로)
                            connection.get(i).add(j);
                        else
                            connection.get(j).add(i);
                    }
                }
            }
            matched = new int[v];       // 연결이 된 경우, 누구랑 연결됐는지 표시.
            Arrays.fill(matched, -1);       // -1로 초기화
            int count = 0;      // 매칭을 진행하며 수를 센다.
            for (int i = 0; i < v; i++) {
                if (matching(i, new boolean[v]))
                    count++;
            }
            // 전체 인원에서 매칭이 성립된 수만큼을 빼주면 최대 시청자 수
            sb.append(v - count).append("\n");
        }
        System.out.println(sb);
    }

    static boolean matching(int a, boolean[] visited) {
        visited[a] = true;      // 해당 a에 대한 방문 표시
        for (int b : connection.get(a)) {
            // b가 아직 매칭이 안되어있거나, b에 매칭되어있는 matched[b]를 다른 값과 매칭시킬 수 있다면
            if (matched[b] == -1 || (!visited[matched[b]] && matching(matched[b], visited))) {
                matched[b] = a;     // b는 a랑 매칭.
                return true;
            }
        }
        // 매칭이 안된다면 false
        return false;
    }
}