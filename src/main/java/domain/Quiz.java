package domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("QUIZ")
public class Quiz extends Question {
    @ElementCollection  // Adding this annotation for the List<String> options
    private List<String> options;

    public Quiz() {}

    public Quiz(String content, List<String> options) {
        super(content);
        this.options = options;
    }

    // Getters and setters...
    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
