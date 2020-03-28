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
                    ThreadLocalRandom.current().nextInt(0, 50),
                    ThreadLocalRandom.current().nextInt(1, 100));
        }

        return items;
    }

    public static Item[] generateItemsList() {
        Item[] items = new Item[7];

        items[0] = new Item("item1", 7, 2);
        items[1] = new Item("item1", 2, 3);
        items[2] = new Item("item1", 5, 5);
        items[3] = new Item("item1", 1, 6);
        items[4] = new Item("item1", 6, 4);
        items[5] = new Item("item1", 9, 2);
        items[6] = new Item("item1", 8, 3);

        return items;
    }
}
