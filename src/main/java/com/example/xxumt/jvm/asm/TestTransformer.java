package com.example.xxumt.jvm.asm;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

/**
 * <类说明>
 *
 * @author mengting.xu@ucarinc.com
 * @date 2023/10/7 16:35
 * @since 1.0
 */
public class TestTransformer implements ClassFileTransformer {
  @Override
  public byte[] transform(
      ClassLoader loader,
      String className,
      Class<?> classBeingRedefined,
      ProtectionDomain protectionDomain,
      byte[] classfileBuffer) {
    System.out.println("Transforming: " + className);
    try {

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
