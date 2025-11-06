package com.semihsahinoglu.todo_app.dto;

public record TodoResponse(
        Long id,
        String title,
        String description,
        Boolean completed,
        String username
) {
    public static class Builder {
        private Long id;
        private String title;
        private String description;
        private Boolean completed;
        private String username;

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

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public TodoResponse build() {
            return new TodoResponse(id, title, description, completed, username);
        }

    }

    public static Builder builder() {
        return new Builder();
    }
}
