/*
 Author : Ruel
 Problem : Baekjoon 10975번 데크 소트 2
 Problem address : https://www.acmicpc.net/problem/10975
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_32029_데크소트2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수가 주어졌을 때, n개의 수에 대해 순서대로 세 가지 중 한 가지 방법을 사용하여 데크에 넣는다.
        // 1. 수를 존재하는 데크 중 하나의 맨 앞에 넣는다.
        // 2. 수를 존재하는 데크 중 하나의 맨 뒤에 넣는다.
        // 3. 새로운 데크를 만들어 수를 담는다.
        // 위 방법을 통해 만들어진 데크들을 이어붙여 오름차순으로 만들고자할 때
        // 필요한 최소 데크의 수는?
        //
        // 그리디, 정렬 문제
        // 데크를 통해 수를 추가할 때, 가장 앞 혹은 가장 뒤에 추가할 수 있다.
        // 하지만 결국 오름차순으로 정렬하기 위해서는
        // 앞이나 뒤에 자신보다 정렬 순서가 하나 앞서거나 하나 뒤인 수가 다른 수들보다 먼저 와야한다.
        // 그렇지 않다면 데크가 하나 추가된다.
        // 따라서 각 수를 정렬했을 때의 순서를 비교하며
        // 지금 등장한 수보다 하나 앞서거나 뒷 순서인 수가 먼저 등장했는지 여부를 따진다.
        // 그랬다면 데크를 추가할 필요없이 해당 수가 속한 데크의 앞 혹은 뒤에 추가하면 되고
        // 그렇지 않다면 데크를 추가해야한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 수
        int n = Integer.parseInt(br.readLine());

        int[] nums = new int[n];
        // 우선순위큐로 정렬
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < n; i++)
            priorityQueue.offer(nums[i] = Integer.parseInt(br.readLine()));
        
        // 각 순의 순서
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        while (!priorityQueue.isEmpty())
            hashMap.put(priorityQueue.poll(), hashMap.size());
        
        // 해당 순서의 수가 등장했는지를 체크
        boolean[] checked = new boolean[n];
        int answer = 0;
        for (int i = 0; i < nums.length; i++) {
            // nums[i]의 정렬 순서
            int order = hashMap.get(nums[i]);

            // 자신보다 하나 앞 선 순서의 수 혹은
            // 바로 뒤의 수가 이미 등장한 것이 아니라면 데크를 추가해야한다.
            if ((order == 0 || !checked[order - 1]) &&
                    (order == n - 1 || !checked[order + 1]))
                answer++;
            // nums[i]의 순서 체크
            checked[order] = true;
        }
        // 답 출력
        System.out.println(answer);
    }
}