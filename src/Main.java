import entities.enums.PreSelectionStatuses;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ArrayList<PreSelectionStatuses> preSelectionStatuses = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            preSelectionStatuses.add(i,PreSelectionStatuses.LOW_SCORE);
        }


        for (PreSelectionStatuses preSelectionStatus: preSelectionStatuses) {
            System.out.println(preSelectionStatus);
        }
    }
}
