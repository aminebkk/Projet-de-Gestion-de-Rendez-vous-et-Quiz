package domain;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("SHORT")
public class ShortQuestion extends Question {
    private String answer;

    public ShortQuestion() {}

    public ShortQuestion(String content, String answer) {
        super(content);
        this.answer = answer;
    }

    // Getters and setters...
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
