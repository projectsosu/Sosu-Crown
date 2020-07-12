package com.sosu.rest.crown.core.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

@Component
@Slf4j
public class KeyChecker {

    @Value("${security.private.key.file}")
    private String privateKeyFile;

    private Key privateKey;

    public static final String RSA_ALGORITHM = "RSA";

    public static final String DSA_ALGORITHM = "DSA";

    public String decrypt(byte[] data)
            throws NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(data));
    }

    @PostConstruct
    private void readKeyFromFile() {
        try {
            log.info("File resource: {}", new ClassPathResource(privateKeyFile).getPath());
            privateKey = readPrivateKey(Files.readAllBytes(new ClassPathResource(privateKeyFile).getFile().toPath()));
        } catch (Exception e) {
            log.error("Read private key error", e);
            throw new SecurityException(e);
        }
    }

    public static PrivateKey readPrivateKey(final byte[] bytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] data = bytes;
        if (((char) data[0]) == '-') {
            data = convertPEMToDER(new String(data));
        }
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(data);

        try {
            return KeyFactory.getInstance(DSA_ALGORITHM).generatePrivate(spec);
        } catch (InvalidKeySpecException e) {
            return KeyFactory.getInstance(RSA_ALGORITHM).generatePrivate(spec);
        }
    }

    public static byte[] convertPEMToDER(final String string) {
        List<String> lines = new LinkedList<>(Arrays.asList(StringUtils.split(string, "\n")));
        String header = lines.remove(0);
        String footer = lines.remove(lines.size() - 1);
        String type;

        if (header.startsWith("-----BEGIN ") && header.endsWith("-----\r")) {
            type = header;
            type = type.replace("\r", "");
            type = type.replace("-----BEGIN ", "");
            type = type.replace("-----", "");

            if (type.contains("ENCRYPTED")) {
                throw new IllegalArgumentException(
                        "Encrypted keys are not supported.");
            }

            if (footer.equals("-----END " + type + "-----")) {
                return Base64.getMimeDecoder().decode(
                        StringUtils.join(new LinkedList<Object>(lines), "\n"));
            } else {
                throw new IllegalArgumentException("Unexpected PEM footer '"
                        + footer + "'");
            }
        } else {
            throw new IllegalArgumentException("Unexpected PEM header '"
                    + header + "'");
        }
    }

}
