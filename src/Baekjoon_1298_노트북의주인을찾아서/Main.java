/*
 Author : Ruel
 Problem : Baekjoon 1298번 노트북의 주인을 찾아서
 Problem address : https://www.acmicpc.net/problem/1298
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1298_노트북의주인을찾아서;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static List<List<Integer>> students;
    static int[] laptops;
    static boolean[] allocated;

    public static void main(String[] args) throws IOException {
        // n명의 학생들은 자신의 노트북을 갖고 있다.
        // 그런데 노트북이 서로 섞였고, n명의 학생들은 자신의 노트북인 것 같은 노트북들을 제시한다.
        // 최대한 많은 학생들에게 노트북들을 할당하고자할 때, 그 개수는?
        // 이분 매칭 문제
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        students = new ArrayList<>();       // 각 학생이 주장하는 노트북들을 저장한다.
        for (int i = 0; i < n + 1; i++)
            students.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            students.get(Integer.parseInt(st.nextToken())).add(Integer.parseInt(st.nextToken()));
        }

        laptops = new int[n + 1];       // 노트북에 할당된 학생들의 번호를 저장한다.
        allocated = new boolean[n + 1];     // 해당 학생에게 노트북이 할당됐는지 체크한다.
        int count = 0;      // 할당된 노트북의 개수.
        for (int i = 1; i < n + 1; i++) {
            // i번 학생이 노트북 할당이 안됐고, allocateLaptop 메소드로 노트북 할당이 된다면, count를 하나씩 늘린다.
            if (!allocated[i] && allocateLaptop(i, new boolean[n + 1]))
                count++;
        }
        System.out.println(count);
    }

    static boolean allocateLaptop(int n, boolean[] visited) {       // 이분 매칭
        visited[n] = true;      // 학생에 대해 방문 체크를 한다.
        for (int laptop : students.get(n)) {        // n번 학생이 자신이 주장하는 laptop들에 대해
            // laptop 노트북에 학생이 할당안됐거나, laptop에 할당된 학생을 다른 노트북으로 할당하고, n번 학생이 laptop 할당받을 수 있다면
            if (laptops[laptop] == 0 || (!visited[laptops[laptop]] && allocateLaptop(laptops[laptop], visited))) {
                // laptop 노트북을 n번 학생에 할당하고
                laptops[laptop] = n;
                // n번 학생이 노트북 할당됐다고 체크 후 true 리턴.
                return allocated[n] = true;
            }
        }
        // 할당이 안 된다면 false 리턴.
        return false;
    }
}