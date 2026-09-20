package tobyspring.splearn.domain.course;

import jakarta.persistence.Entity;
import lombok.*;
import tobyspring.splearn.domain.AbstractEntity;

import java.time.LocalDateTime;

@Getter
@ToString(callSuper = true, exclude = {})
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseDetail extends AbstractEntity {

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime publishedAt;

    private LocalDateTime archivedAt;

    CourseDetail(String description){
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }

    void publish() {
        this.publishedAt = LocalDateTime.now();
    }

    void archive() {
        this.archivedAt = LocalDateTime.now();
    }

    void updateInfo(CourseUpdateInfo updateInfo) {
        this.description = updateInfo.description();
    }
}
