package com.nolnol.useminserver.domain.member;

public class RandomNicknameGeneratorTest {

    public static void main(String[] args) {
        for (int i = 0; i < 30; i++) {
            System.out.println(RandomNicknameGenerator.generate());
        }
    }
}
