/*
 Author : Ruel
 Problem : Baekjoon 1467번 수 지우기
 Problem address : https://www.acmicpc.net/problem/1467
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 수지우기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws IOException {
        // 수와 지우려는 숫자가 공백 없이 주어진다.
        // 수에서 지우려는 숫자들을 모두 지운다했을 때 만들 수 있는 최대의 수를 구하여라
        // 예를 들어 25255 와 25가 주어진다면 가장 앞에 있는 2를 지우고, 두번째 5는 지워서는 안되며, 뒤에 있는 5를 하나 지워 525가 정답이 된다.
        // 그렇다면 어떻게 문제를 해결해야할까
        // 뒤의 숫자가 앞의 숫자보다 크고, 앞의 숫자를 지울 횟수가 남아있을 경우 지우고 넘어가는 방식으로 문제를 풀어보았다.
        // -> 마지막 숫자까지 체크하더라도 지워야할 숫자가 남아 최대 숫자가 다른 경우가 발생했다
        // 2312, 23이라고 한다면, 312에, 3이 하나 남아 12가 된다. 하지만 답은 앞의 2를 지우는 것이 아닌 뒤의 2를 지운 21이 답이다.
        //
        // 따라서 숫자들을 지워갈 때 해당 숫자의 남은 개수와 해당 숫자를 지울 수 있는 남은 횟수를 비교하여 진행하여야한다
        // 해당 숫자의 남은 개수와 지울 횟수가 같게 남은 경우, 무조건 지워야하며, 남은 개수가 더 많을 경우에는 지우거나 지우지 않거나 선택을 할 수 있다.
        // 1. 순서대로 수의 숫자를 하나씩 살펴간다.
        // 2. 해당 숫자의 남은 개수와 지울 횟수를 비교한다.
        // 3. 같게 남았다면 무조건 지우고 다음 숫자로 넘어간다
        // 4. 남은 숫자의 개수가 더 많을 경우, 이전에 선택되어 남아있는 숫자를 살펴본다
        //  이전 숫자가 더 작은 경우, 이전 숫자의 지울 횟수가 남아있는지 살펴보고, 이전 숫자의 지울 횟수가 남아있다면 이전 숫자를 지운다.
        //  지울 횟수가 없거나, 이번 숫자가 더 작을 경우에는 그대로 스택에 담아준다.
        // 5. 스택의 역순으로 꺼내면 최대값이 완성된다
        // 앞에서 꺼내는 경우, 뒤에서 꺼내는 경우 모두 필요하므로 스택을 데크로 대체할 경우 더 간단하게 구현이 가능하다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String number = br.readLine();
        int[] numsRemain = new int[10];
        for (int i = 0; i < number.length(); i++)       // 주어진 수에서 각 숫자의 개수를 센다.
            numsRemain[number.charAt(i) - '0']++;

        String input = br.readLine();
        int[] numsToDelete = new int[10];
        for (int i = 0; i < input.length(); i++)        // 지워야할 숫자의 개수를 센다.
            numsToDelete[input.charAt(i) - '0']++;

        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < number.length(); i++) {
            int currentNum = number.charAt(i) - '0';        // 숫자로 변환.
            if (numsToDelete[currentNum] == numsRemain[currentNum]) {       // 만약 남은 개수와 지워야할 개수가 일치한다면 무조건 지워야한다.
                numsRemain[currentNum]--;       // 남은 개수를 하나 차감.
                numsToDelete[currentNum]--;     // 지울 개수도 하나 차감.
                continue;       // 다음 숫자로 넘어간다.
            }

            // 데크의 마지막 숫자를 살펴본다. 데크가 비어있다면 넘어간다
            // 데크의 마지막 숫자가 현재 숫자보다 작고, 해당 숫자를 지울 횟수가 남아있다면
            // 데크의 마지막 숫자를 지워준다.
            while (!deque.isEmpty() && deque.peekLast() < currentNum && numsToDelete[deque.peekLast()] > 0) {
                numsToDelete[deque.peekLast()]--;       // 횟수 차감.
                deque.pollLast();       // 데크에서 제외.
            }
            // 현재 데크는
            // 1. 비어있거나
            // 2. deque.peekLast()가 currentNum보다 크거나 (= 현재 숫자보다 데크의 마지막 숫자가 크거나)
            // 3. numsToDelete[deque.peekLast()] == 0인 경우이다.        (= 마지막 숫자를 지울 횟수가 없는 경우)
            // 따라서 해당 마지막 숫자는 반드시 안고가야하는 숫자이므로 남겨두고,
            // 현재 숫자를 데크의 마지막에 넣어준다.
            deque.offerLast(currentNum);
            numsRemain[currentNum]--;
        }
        StringBuilder sb = new StringBuilder();
        while (!deque.isEmpty())
            sb.append(deque.pollFirst());
        System.out.println(sb);
    }
}