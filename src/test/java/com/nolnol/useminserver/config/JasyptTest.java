package com.nolnol.useminserver.config;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JasyptTest {

    private PooledPBEStringEncryptor jasyptStringEncryptor;

    private static final String password = "secret";
    private static final String defaultRawText = "test";
    private static final String defaultEncryptedText = "m78evd5y2JX0pBudsk2Dsw==";

    @BeforeEach
    public void setUp() {
        jasyptStringEncryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password); // 암호화할 때 사용하는 키
        config.setAlgorithm("PBEWithMD5AndDES"); // 암호화 알고리즘
        config.setKeyObtentionIterations("1000"); // 반복할 해싱 회수
        config.setPoolSize("1"); // 인스턴스 pool
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator"); // salt 생성 클래스
        config.setStringOutputType("base64"); //인코딩 방식
        jasyptStringEncryptor.setConfig(config);
    }

    @DisplayName("복호화 테스트")
    @Test
    public void decryptTest() {
        String decryptedText = jasyptStringEncryptor.decrypt(defaultEncryptedText);

        assertThat(decryptedText).isEqualTo(defaultRawText);
    }

    @DisplayName("암호화 테스트")
    @Test
    public void encryptTest() {
        String encryptedText = jasyptStringEncryptor.encrypt(defaultRawText);
        System.out.println(encryptedText);

        assertThat(jasyptStringEncryptor.decrypt(encryptedText)).isEqualTo(defaultRawText);
    }

    @DisplayName("암호화된 문자열 생성 테스트")
    @Test
    public void test() {
        String plainText = "test";

        System.out.println(jasyptStringEncryptor.encrypt(plainText));
    }
}