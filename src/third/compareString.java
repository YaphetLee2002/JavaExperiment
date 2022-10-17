package third;

import java.io.*;
import java.util.*;

    @Override
    public int compare(String o1, String o2) {
        o1 = o1.toLowerCase();
        o2 = o2.toUpperCase();
        return o2.compareTo(o1);
    }
}

