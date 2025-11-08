package com.semihsahinoglu.todo_app.dto;

import java.time.LocalDateTime;

public record TodoResponse(
        Long id,
        String title,
        String description,
        Boolean completed,
        String createdBy,
        LocalDateTime createdDate
) {
    public static class Builder {
        private Long id;
        private String title;
        private String description;
        private Boolean completed;
        private String createdBy;
        private LocalDateTime createdDate;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder completed(Boolean completed) {
            this.completed = completed;
            return this;
        }

        public Builder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Builder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public TodoResponse build() {
            return new TodoResponse(id, title, description, completed, createdBy, createdDate);
        }

    }

    public static Builder builder() {
        return new Builder();
    }
}
