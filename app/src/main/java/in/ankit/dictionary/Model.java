package in.ankit.dictionary;

import java.io.Serializable;

/**
 * Created by Ankit Varia on 12/03/16.
 */
public class Model implements Serializable {

    long id;
    String word;
    long variant;
    String meaning;
    double ratio;
    String url;

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", variant=" + variant +
                ", meaning='" + meaning + '\'' +
                ", ratio=" + ratio +
                ", url='" + url + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public long getVariant() {
        return variant;
    }

    public void setVariant(long variant) {
        this.variant = variant;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }
}
