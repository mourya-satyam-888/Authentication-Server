package com.authentication.authenticationserver.util;

import com.authentication.authenticationserver.constants.EncryptionConstants;
import com.authentication.authenticationserver.constants.PathConstants;
import jakarta.persistence.AttributeConverter;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The type Encryption utils.
 */
@Component
@Log4j2
public class EncryptionUtils implements AttributeConverter<String, String> {

  /**
   * The constant PASSWORD.
   */
  @Value("${keystore.password}")
  private String password;
  private SecretKey key;

  @Override
  public String convertToDatabaseColumn(String attribute) {
    return encryptString(attribute);
  }

  @Override
  public String convertToEntityAttribute(String dbData) {
    return decryptString(dbData);
  }

  /**
   * Generate key secret key.
   *
   * @param encryptionType the encryption type
   * @return the secret key
   */
  private SecretKey generateKey(final String encryptionType) {
    try {
      final KeyGenerator keyGenerator = KeyGenerator.getInstance(encryptionType);
      return keyGenerator.generateKey();
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Write key to key store.
   *
   * @throws Exception the exception
   */
  private void writeKeyToKeyStore() {
    try {
      final File file = new File(PathConstants.KEYSTORE_PATH);
      final KeyStore javaKeyStore = KeyStore.getInstance(EncryptionConstants.KEYSTORE_TYPE);
      if (!file.exists()) {
        javaKeyStore.load(null, null);
      }
      javaKeyStore.setKeyEntry(EncryptionConstants.ALIAS, loadKey(), password.toCharArray(), null);
      final OutputStream write = Files.newOutputStream(Paths.get(PathConstants.KEYSTORE_PATH));
      javaKeyStore.store(write, password.toCharArray());
    } catch (Exception e) {
      log.error(e.getMessage());
    }

  }

  /**
   * Load key secret key.
   *
   * @return the secret key
   */
  private SecretKey loadKey() {
    if (key != null) {
      return key;
    }
    try {
      final KeyStore keyStore = KeyStore.getInstance(EncryptionConstants.KEYSTORE_TYPE);
      final InputStream inputStream = Files.newInputStream(Paths.get(PathConstants.KEYSTORE_PATH));
      keyStore.load(inputStream, password.toCharArray());
      key = (SecretKey) keyStore.getKey(EncryptionConstants.ALIAS, password.toCharArray());
      return key;
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Encrypt string string.
   *
   * @param plainText the plain text
   * @return the string
   */
  public String encryptString(final String plainText) {
    try {
      final byte[] byteText = plainText.getBytes(EncryptionConstants.FORMAT);
      final Cipher cipher = Cipher.getInstance(EncryptionConstants.ALGORITHM);
      cipher.init(Cipher.ENCRYPT_MODE, loadKey());
      return new String(Base64.getEncoder().encode(cipher.doFinal(byteText)));
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Decrypt string string.
   *
   * @param encryptedText the encrypted text
   * @return the string
   */
  public String decryptString(final String encryptedText) {
    try {
      final byte[] byteText = Base64.getDecoder()
          .decode(encryptedText.getBytes(EncryptionConstants.FORMAT));
      final Cipher cipher = Cipher.getInstance(EncryptionConstants.ALGORITHM);
      cipher.init(Cipher.DECRYPT_MODE, loadKey());
      return new String(cipher.doFinal(byteText));
    } catch (Exception e) {
      return null;
    }
  }
}
