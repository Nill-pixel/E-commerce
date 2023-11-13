package App.Web.Enums;

import lombok.Getter;

@Getter
public enum Categories {
    Burger(1),
    Pizza(2),
    Pasta(3),
    Fries(4);

    private final int value;

    Categories(int value) {
        this.value = value;
    }

}
