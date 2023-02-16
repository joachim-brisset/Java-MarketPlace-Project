package project.gui.models.utils;

import java.util.Date;
import java.util.Random;

public class Generator {
    private static final Random random = new Random();

    static {
        random.setSeed(new Date().getTime());
    }

    public static final StringRandomizer LOWER = new StringRandomizer(
            new char[] {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'}, 10);
    public static final StringRandomizer UPPER = new StringRandomizer(
            new char[] {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'}, 10);
    public static final StringRandomizer UPPER_WITH_NUMBER = new StringRandomizer(
            new char[] {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9'}, 10);

    public static class StringRandomizer {
        private final char[] set;
        private final int length;

        public StringRandomizer(char[] set, int length) {
            this.set = set;
            this.length = length;
        }

        public String generate() {
            StringBuilder buffer = new StringBuilder(this.length);
            for (int i = 0; i < this.length; i++) {
                buffer.append( set[Math.round(random.nextFloat() * (set.length -1))]);
            }
            return buffer.toString();
        }
    }
}
