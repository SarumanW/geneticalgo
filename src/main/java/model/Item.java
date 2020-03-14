package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private String name;
    private int value;
    private int fitness;

    public static Item[] generateItemsList(int listSize) {
        Item[] items = new Item[listSize];

        for (int i = 0; i < listSize; i++) {
            items[i] = new Item("item" + i,
                    ThreadLocalRandom.current().nextInt(0, 11),
                    ThreadLocalRandom.current().nextInt(1, 6));
        }

        return items;
    }
}
