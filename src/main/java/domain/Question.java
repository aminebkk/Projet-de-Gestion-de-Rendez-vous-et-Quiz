package domain;

import jakarta.persistence.*;

@Entity
@NamedQuery(name = "Question.findByType",
        query = "SELECT q FROM Question q WHERE TYPE(q) = :type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Using single table inheritance
@DiscriminatorColumn(name = "question_type") // Column to differentiate types
public abstract class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    // Default constructor
    public Question() {}

    public Question(String content) {
        this.content = content;
    }

    // Getters and setters...
    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
