/*
 Author : Ruel
 Problem : 
 Problem address : 
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 문제집;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        // class 없이 간단하게 푼 버젼.
        // 기본 내용은 Main 과 같다.
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        List<Integer>[] lists = new List[n + 1];
        for (int i = 1; i < lists.length; i++)
            lists[i] = new ArrayList<>();

        int[] inDegree = new int[n + 1];
        for (int i = 0; i < m; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            lists[a].add(b);
            inDegree[b]++;
        }

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for (int i = 1; i < inDegree.length; i++) {
            if (inDegree[i] == 0)
                priorityQueue.add(i);
        }

        StringBuilder sb = new StringBuilder();
        while (!priorityQueue.isEmpty()) {
            int current = priorityQueue.poll();
            sb.append(current).append(" ");
            for (int i : lists[current]) {
                if (--inDegree[i] == 0)
                    priorityQueue.add(i);
            }
        }
        System.out.println(sb);
    }
}