package com.nolnol.useminserver.domain.member;

public class RandomNicknameGenerator {

    private static final String[] adjectives = {"용감한", "민첩한", "곤란한", "어설픈", "단호한", "느긋한", "상냥한", "관대한", "사려 깊은", "검소한", "고결한", "현명한"};
    private static final String[] nouns = {"돌하르방", "감귤", "한라봉", "한라산", "땅콩", "동백꽃", "성산일출봉", "제주갈치", "섭지코지", "고등어회"};


    public static String generate() {
        StringBuilder word = new StringBuilder();

        int random = (int) ((Math.random() * adjectives.length));
        word.append(adjectives[random])
            .append(" ");

        random = (int) ((Math.random() * nouns.length));
        word.append(nouns[random]);

        return word.toString();
    }
}
