package demos.android.com.craneo.temporal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by crane on 10/14/2016.
 */

public class DataProvider {
    public static List<Kid> kidsList = new ArrayList<>();


    static {

        saveKid("Gisel",
                "Beatriz",
                "student_1");

        saveKid("Ariatna",
                "Montes",
                "student_2");
    }

    private static void saveKid(String name, String lastName, String photo) {
        Kid kid = new Kid(name, lastName, photo);
        kidsList.add(kid);
    }

}