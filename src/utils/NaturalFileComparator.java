package utils;

import java.io.File;
import java.util.Comparator;

public class NaturalFileComparator implements Comparator<File> {

    @Override
    public int compare(File a, File b) {
        return compareStrings(a.getName(), b.getName());
    }

    //implement natural sort order for file names treating embedded numbers as numerical values
    private int compareStrings(String a, String b) {
        int ia = 0, ib = 0;
        int maxa = a.length(), maxb = b.length();

        while (ia < maxa && ib < maxb) {
            char ca = a.charAt(ia);
            char cb = b.charAt(ib);
            int result;

            if (Character.isDigit(ca) && Character.isDigit(cb)) {
                result = compareNumerically(a, ia, b, ib);
            } else {
                result = Character.compare(ca, cb);
            }

            if (result != 0) {
                return result;
            }

            if (Character.isDigit(ca) && Character.isDigit(cb)) {
                //advance past the entire number segment
                ia = skipNumberSegment(a, ia);
                ib = skipNumberSegment(b, ib);
            } else {
                ia++;
                ib++;
            }
        }
        
        //shorter string comes first
        return maxa - maxb;
    }

    //compares the numeric segment
    private int compareNumerically(String s1, int i1, String s2, int i2) {
        long v1 = 0, v2 = 0;
        int i = i1;
        
        while (i < s1.length() && Character.isDigit(s1.charAt(i))) {
            v1 = v1 * 10 + (s1.charAt(i) - '0');
            i++;
        }
        i = i2;
        while (i < s2.length() && Character.isDigit(s2.charAt(i))) {
            v2 = v2 * 10 + (s2.charAt(i) - '0');
            i++;
        }

        return Long.compare(v1, v2);
    }

    //helper to find the end of a digit sequence
    private int skipNumberSegment(String s, int start) {
        int i = start;
        while (i < s.length() && Character.isDigit(s.charAt(i))) {
            i++;
        }
        return i;
    }
}
