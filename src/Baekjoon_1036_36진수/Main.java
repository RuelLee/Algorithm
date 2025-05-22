/*
 Author : Ruel
 Problem : Baekjoon 1036번 36진수
 Problem address : https://www.acmicpc.net/problem/1036
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1036_36진수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 36진법의 수, n개가 주어진다.
        // 그리고 36진법의 수 36개 중 k개를 골라 해당 수를 Z로 바꾼다.
        // 모든 수의 합을 최대화하고자 할 때, 그 값은?
        //
        // 그리디. 큰 수 연산
        // 수가 아주 아주 크다는 걸 빼면 어렵지 않은 문제
        // n개의 수에 대해 각 숫자가 영향을 끼치는 정도를 계산하여준다.
        // 11의 경우, 36^0의 자리에 1개, 36^1의 자리에 한 개 있다.
        // 이는 36^0 * 1 + 36^1 * 1 로 세어준다.
        // 이렇게 세어
        // 해당 수가 Z(=35)로 바뀔 경우, 얻어지는 이익의 크기를 숫자 별로 비교하여
        // 큰 순서대로 k개를 변환한다.
        // 그 후, 모든 수를 누적시키고, 36진법으로 변환시켜주면 끝.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        // 36의 제곱수들을 미리 계산
        // 값이 매우 크므로 BigInteger 타입을 사용
        BigInteger[] pow = new BigInteger[53];
        pow[0] = BigInteger.ONE;
        for (int i = 1; i < pow.length; i++)
            pow[i] = pow[i - 1].multiply(BigInteger.valueOf(36));

        // 각 숫자가 합에 영향을 끼치는 정도.
        BigInteger[] nums = new BigInteger[36];
        Arrays.fill(nums, BigInteger.ZERO);

        for (int i = 0; i < n; i++) {
            String num = br.readLine();
            for (int j = 0; j < num.length(); j++) {
                // 뒤에서 부터 36^j 자리의 수
                char c = num.charAt(num.length() - 1 - j);
                // 직관적으로 0 ~ 35 값으로 변환
                int value = (c >= 65 ? c - 'A' + 10 : c - '0');
                
                // 해당 숫자가 끼치는 영향을 누적 
                nums[value] = nums[value].add(pow[j]);
            }
        }
        
        // Z로 변환할 수 있는 k개의 숫자
        int k = Integer.parseInt(br.readLine());
        
        // 0 ~ 35의 수를 Z로 변환할 때, 얻을 수 있는 이익을
        // 우선순위큐를 통해 내림차순 정렬
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> nums[o2].multiply(BigInteger.valueOf(35).subtract(BigInteger.valueOf(o2))).compareTo(nums[o1].multiply(BigInteger.valueOf(35).subtract(BigInteger.valueOf(o1)))));
        for (int i = 0; i < nums.length; i++) {
            if (nums[i].equals(BigInteger.ZERO))
                continue;
            priorityQueue.offer(i);
        }
        
        // Z로 변환할 k개의 수 선정
        HashSet<Integer> hashSet = new HashSet<>();
        while (!priorityQueue.isEmpty() && hashSet.size() < k)
            hashSet.add(priorityQueue.poll());
        
        // 전체 합
        BigInteger answer = BigInteger.ZERO;
        // 변환된 수라면 35로 계산하고, 아니라면 그대로 누적
        for (int i = 0; i < nums.length; i++)
            answer = answer.add((hashSet.contains(i) ? BigInteger.valueOf(35) : BigInteger.valueOf(i)).multiply(nums[i]));

        StringBuilder sb = new StringBuilder();
        // 만약 전체 합이 0이라면 0을 그대로 출력해줘야한다.
        if (answer.equals(BigInteger.ZERO))
            sb.append('0');
        else {
            // 그 외의 경우
            // 36^0의 자리부터 계산하여 올라간다.
            Stack<Character> stack = new Stack<>();
            // answer가 0보다 큰 동안
            while (!answer.equals(BigInteger.ZERO)) {
                // 이번 자리에 올 수는 36진법이므로 answer를 36으로 나눈 나머지와 같다.
                int num = answer.mod(BigInteger.valueOf(36)).intValue();
                // 해당 수를 진법을 고려해여 stack에 담는다.
                stack.push((char) (num >= 10 ? num - 10 + 'A' : num + '0'));
                // answer는 36으로 나눠, 다음 자릿수로 넘어간다.
                answer = answer.divide(BigInteger.valueOf(36));
            }
            
            // 스택에 꺼내며 답안 작성
            while (!stack.isEmpty())
                sb.append(stack.pop());
        }
        // 답 출력
        System.out.println(sb);
    }
}