package it.polimi.db2.fakeEntities;

import java.net.MalformedURLException;
import java.net.URL;

public class Product {

    private final String name;
    private final URL imgUrl;

    public Product(String name, String imgUrl) throws MalformedURLException {
        this.name = name;
        this.imgUrl = new URL(imgUrl);
    }

    public String getName() {
        return name;
    }

    public URL getImgUrl() {
        return imgUrl;
    }
}
