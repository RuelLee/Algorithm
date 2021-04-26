package 다단계칫솔판매;

import java.util.Arrays;
import java.util.HashMap;

class Person {
    String name;
    Person parent;
    int money;

    public Person(String name, Person parent, int money) {
        this.name = name;
        this.parent = parent;
        this.money = money;
    }

    void getReward(int i) {
        int moneyToParent = (int) (i * 0.1);
        this.money += i - moneyToParent;
        if (this.parent != null)
            this.parent.getReward(moneyToParent);
    }
}

public class Solution {
    public static void main(String[] args) {
        // 각자의 회원들은 자신의 추천인이 존재하고, 자신이 얻은 리워드에 10%를 추천인에게 돌려줘야한다.
        // 따라서 Person 클래스를 만들어서 파라미터로 추천인 Person을 가리키게 하고
        // getReward 메소드를 만들어 10%에 해당하는 금액을 Parent에게 돌려주도록 하자.

        String[] enroll = {"john", "mary", "edward", "sam", "emily", "jaimie", "tod", "young"};
        String[] referral = {"-", "-", "mary", "edward", "mary", "mary", "jaimie", "edward"};
        String[] seller = {"young", "john", "tod", "emily", "mary"};
        int[] amount = {12, 4, 2, 5, 10};

        HashMap<String, Person> personHashMap = new HashMap<>();
        for (int i = 0; i < enroll.length; i++) {
            if (referral[i].equals("-"))
                personHashMap.put(enroll[i], new Person(enroll[i], null, 0));
            else
                personHashMap.put(enroll[i], new Person(enroll[i], personHashMap.get(referral[i]), 0));
        }

        for (int i = 0; i < seller.length; i++)
            personHashMap.get(seller[i]).getReward(amount[i] * 100);

        int[] result = new int[enroll.length];

        for (int i = 0; i < result.length; i++)
            result[i] = personHashMap.get(enroll[i]).money;

        System.out.println(Arrays.toString(result));
    }
}